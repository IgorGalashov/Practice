import pygame
import pygame.sprite
import os
import random
import csv
import button
pygame.init()
# создание игрового окна
SCREEN_WIDTH = 800
SCREEN_HEIGT = int(SCREEN_WIDTH*0.8)
screen = pygame.display.set_mode((SCREEN_WIDTH,SCREEN_HEIGT))
pygame.display.set_caption("Game")
clock = pygame.time.Clock()
FPS = 60
GRAVITY = 0.75
SCROLL_THRESH = 200
screen_scroll = 0
bg_scroll = 0
level = 1
start_game = False
ROWS = 16
COLS = 150
TILE_SIZE = SCREEN_HEIGT // ROWS
TILE_TYPES = 10

ai_moving_left = False
ai_moving_right = False
moving_left = False
moving_right = False
shoot = False
gran = False
gran_thrown = False
BG = (0,0,0)
RED = (255,0,0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
BLACK = (0, 0, 0)
img_list = []
for x in range(TILE_TYPES):
    img = pygame.image.load(f'img/tile/{x}.png')
    img = pygame.transform.scale(img, (TILE_SIZE, TILE_SIZE))
    img_list.append(img)
start_img = pygame.image.load('img/start.png').convert_alpha()
exit_img = pygame.image.load('img/exit.png').convert_alpha()
rest_img = pygame.image.load('img/rest.png').convert_alpha()
fon = pygame.image.load('img/fon.png').convert_alpha()
bullet_img = pygame.image.load('img/bullet.png').convert_alpha()
heal_box_img = pygame.image.load('img/hil1.png').convert_alpha()
gren_box_img = pygame.image.load('img/hil.png').convert_alpha()
ammo_box_img = pygame.image.load('img/ammo.png').convert_alpha()

item_boxes = {
    'Health' : heal_box_img,
    'Ammo'   : ammo_box_img,
    'Grenade' : gren_box_img
}

font = pygame.font.SysFont('Futura', 30)
def draw_text(text, font, text_col, x, y):
    img = font.render(text, True, text_col)
    screen.blit(img,(x,y))
def draw_bg():
    screen.fill(BG)
    width = fon.get_width()
    for x in range(4):
        screen.blit(fon,((x * width) - bg_scroll, 0))

def reset_level():
    enemy_group.empty()
    bullet_group.empty()
    gran_group.empty()
    explosion_group.empty()
    item_box_group.empty()
    water_group.empty()

    data = []
    for row in range(ROWS):
        r = [-1] * COLS
        data.append(r)
    return data
class Robot(pygame.sprite.Sprite):
    def __init__ (self, char_tipe, x, y, scale, screen, speed, ammo, grenades):
        pygame.sprite.Sprite.__init__(self)
        self.screen = screen
        self.char_tipe = char_tipe
        self.speed = speed
        self.ammo = ammo
        self.start_ammo = ammo
        self.soot_cooldown = 0
        self.grenades = grenades
        self.health = 100
        self.max_health = self.health
        self.direction = 1
        self.flip = False
        self.animation_list = []
        self.frame_index = 0
        self.action = 0
        self.alive = True
        self.jump = False
        self.in_air = True
        self.vel_y = 0
        self.update_time = pygame.time.get_ticks()

        self.move_counter = 0
        self.idle = False
        self.idle_counter = 0
        self.vision = pygame.Rect(0, 0, 150, 20)

        animation_tipes = ['idle', 'move', 'shot', 'death']
        for animation in animation_tipes:
            temp_list = []
            num_od_frames = len(os.listdir(f'img/{self.char_tipe}/{animation}'))
            for i in range(num_od_frames):
                img = pygame.image.load(f'img/{self.char_tipe}/{animation}/{i}.png').convert_alpha()
                img = pygame.transform.scale(img, (TILE_SIZE, TILE_SIZE))
                temp_list.append(img)
            self.animation_list.append(temp_list)

        self.image = self.animation_list[self.action][self.frame_index]
        self.rect = self.image.get_rect()
        self.rect.center = (x, y)
        self.width = self.image.get_width()
        self.height = self.image.get_height()
    def update(self):
        self.update_animation()
        self.check_alive()
        if self.soot_cooldown > 0:
            self.soot_cooldown -= 1

    def move(self, moving_left, moving_right):
        screen_scroll = 0
        dx = 0
        dy = 0
        if moving_left:
            dx -= self.speed
            self.flip = True
            self.direction = -1
        if moving_right:
            dx = self.speed
            self.flip = False
            self.direction = 1
        if self.jump and self.in_air == False:
            self.vel_y = -11
            self.jump = False
            self.in_air = True
        self.vel_y += GRAVITY
        if self.vel_y > 10:
            self.vel_y
        dy += self.vel_y
        for tile in world.obstacle_list:
            if tile[1].colliderect(self.rect.x + dx, self.rect.y, self.width, self.height):
                dx = 0
            if tile[1].colliderect(self.rect.x, self.rect.y + dy, self.width, self.height):
                if self.vel_y < 0:
                    self.vel_y = 0
                    dy = tile[1].bottom - self.rect.top
                elif self.vel_y >= 0:
                    self.vel_y = 0
                    self.in_air = False
                    dy = tile[1].top - self.rect.bottom

        if pygame.sprite.spritecollide(self, water_group, False):
            self.health = 0


        if self.rect.bottom > SCREEN_HEIGT:
            self.health = 0

        if self.char_tipe == 'player':
            if self.rect.left + dx < 0 or self.rect.right + dx > SCREEN_WIDTH:
                dx = 0
        self.rect.x += dx
        self.rect.y += dy

        if self.char_tipe == 'player':
            if self.rect.right > SCREEN_WIDTH - SCROLL_THRESH or self.rect.left < SCROLL_THRESH:
                self.rect.x -= dx
                screen_scroll -= dx
        return screen_scroll

    def soot(self):
        if self.soot_cooldown == 0 and self.ammo > 0:
            self.soot_cooldown = 20
            bullet = Bullet(self.rect.centerx + (1.2 * self.rect.size[0] * self.direction), self.rect.centery,self.direction)
            bullet_group.add(bullet)
            self.ammo -= 1

    def ai(self):
        if self.alive and player.alive:
            if self.idle == False and random.randint(1,200) == 1:
                self.update_action(0)
                self.idle = True
                self.idle_counter = 50
            if self.vision.colliderect(player.rect):
                self.update_action(2)
                self.soot()
            else:
                if self.idle == False:
                    if self.direction == 1:
                        ai_moving_right = True
                    else:
                        ai_moving_right = False
                    ai_moving_left = not ai_moving_right
                    self.move(ai_moving_left, ai_moving_right)
                    self.update_action(1)
                    self.move_counter += 1

                    self.vision.center = (self.rect.centerx + 75 * self.direction, self.rect.centery)
                    if self.move_counter > TILE_SIZE:
                        self.direction *= -1
                        self.move_counter *= -1
                else:
                    self.idle_counter -= 1
                    if self.idle_counter <= 0:
                        self.idle = False
        self.rect.x += screen_scroll
    def update_animation(self):
        ANIM_COOLDOWN = 170
        self.image = self.animation_list[self.action][self.frame_index]
        if pygame.time.get_ticks() - self.update_time > ANIM_COOLDOWN:
            self.update_time = pygame.time.get_ticks()
            self.frame_index += 1
        if self.frame_index >= len(self.animation_list[self.action]):
            if self.action == 3:
                self.frame_index = len(self.animation_list[self.action]) - 1
            else:
                self.frame_index = 0

    def update_action(self,new_action):
        if new_action != self.action:
            self.action = new_action
            self.frame_index = 0
            self.update_time = pygame.time.get_ticks()

    def check_alive(self):
        if self.health <= 0:
            self.health = 0
            self.speed = 0
            self.alive = False
            self.update_action(3)

    def draw(self):
        self.screen.blit(pygame.transform.flip(self.image, self.flip,False),self.rect)


class World():
    def __init__(self):
        self.obstacle_list = []
    def process_data(self,data):
        for y, row in enumerate(data):
            for x, tile in enumerate(row):
                if tile >= 0:
                    img = img_list[tile]
                    img_rect = img.get_rect()
                    img_rect.x = x * TILE_SIZE
                    img_rect.y = y * TILE_SIZE
                    tile_data = (img, img_rect)
                    if tile >= 0 and tile <= 2:
                        self.obstacle_list.append(tile_data)
                    elif tile == 3:
                        water = Water(img,  x * TILE_SIZE, y * TILE_SIZE)
                        water_group.add(water)
                    elif tile == 7:
                        player = Robot('player', x * TILE_SIZE, y * TILE_SIZE, 2, screen, 4, 20, 5)
                        health_bar = HealthBar(10, 10, player.health, player.health)
                    elif tile == 8:
                        enemy = Robot('enemy', x * TILE_SIZE, y * TILE_SIZE, 2, screen, 2, 20, 0)
                        enemy_group.add(enemy)
                    elif  tile == 4:
                        item_box = ItemBox('Grenade', x * TILE_SIZE, y * TILE_SIZE)
                        item_box_group.add(item_box)
                    elif tile == 5:
                        item_box = ItemBox('Health', x * TILE_SIZE, y * TILE_SIZE)
                        item_box_group.add(item_box)
                    elif tile == 7:
                        item_box = ItemBox('Ammo', x * TILE_SIZE, y * TILE_SIZE)
                        item_box_group.add(item_box)
        return player, health_bar
    def draw(self):
        for tile in  self.obstacle_list:
            tile[1][0] += screen_scroll
            screen.blit(tile[0], tile[1])

class Water(pygame.sprite.Sprite):
    def __init__(self, img, x, y):
        pygame.sprite.Sprite.__init__(self)
        self.image = img
        self.rect = self.image.get_rect()
        self.rect.midtop = (x + TILE_SIZE // 2, y + (TILE_SIZE - self.image.get_height()))
    def update(self,):
        self.rect.x += screen_scroll


class ItemBox(pygame.sprite.Sprite):
    def __init__(self, item_type, x, y):
        pygame.sprite.Sprite.__init__(self)
        self.item_type = item_type
        self.image = item_boxes[self.item_type]
        self.rect = self.image.get_rect()
        self.rect.midtop = (x + TILE_SIZE // 2, y + (TILE_SIZE - self.image.get_height()))
    def update(self):
        if pygame.sprite.collide_rect(self,player):
            if self.item_type == 'Health':
                player.health += 25
                if player.health > player.max_health:
                    player.health = player.max_health
            elif self.item_type == 'Ammo':
                player.ammo += 15
            elif self.item_type == 'Grenade':
                player.grenades += 5
            self.kill()
        self.rect.x += screen_scroll

class HealthBar():
    def __init__(self, x, y, health, max_health):
        self.x = x
        self.y = y
        self.health = health
        self.max_health = max_health

    def draw(self,health):
        self.health = health
        ratio = self.health / self.max_health
        pygame.draw.rect(screen, BLACK, (self.x - 2, self.y - 2, 154, 24))
        pygame.draw.rect(screen, RED, (self.x, self.y, 150, 20))
        pygame.draw.rect(screen,GREEN,(self.x, self.y, 150 * ratio, 20))

class Bullet(pygame.sprite.Sprite):
    def __init__(self, x, y, direction):
        pygame.sprite.Sprite.__init__(self)
        self.speed = 10
        self.image = pygame.image.load('img/bullet.png').convert_alpha()
        self.image = pygame.transform.scale(self.image,(int(self.image.get_width()), int(self.image.get_height())))
        self.rect = self.image.get_rect()
        self.rect.center = (x, y)
        self.direction = direction
    def update(self):
        self.rect.x += (self.direction * self.speed) + screen_scroll
        if self.rect.right < 0 or self.rect.left > SCREEN_WIDTH:
            self.kill()
        for tile in world.obstacle_list:
            if tile[1].colliderect(self.rect):
                self.kill()
        if pygame.sprite.spritecollide(player,bullet_group,False):
            if player.alive:
                player.health -= 5
                self.kill()
        for enemy in enemy_group:
            if pygame.sprite.spritecollide(enemy,bullet_group,False):
                if enemy.alive:
                    enemy.health -= 25
                    self.kill()

class Gran(pygame.sprite.Sprite):
    def __init__(self, x, y, direction):
        pygame.sprite.Sprite.__init__(self)
        self.timer = 100
        self.speed = 7
        self.vel_y = -11
        self.image = pygame.image.load('img/gran.png').convert_alpha()
        self.image = pygame.transform.scale(self.image,(int(self.image.get_width()/7), int(self.image.get_height()/7)))
        self.rect = self.image.get_rect()
        self.rect.center = (x, y)
        self.direction = direction
        self.width = self.image.get_width()
        self.height = self.image.get_height()
    def update(self):
        self.vel_y += GRAVITY
        dx = self.direction * self.speed
        dy = self.vel_y

        for tile in world.obstacle_list:
            if tile[1].colliderect(self.rect.x + dx, self.rect.y, self.width, self.height):
                self.direction *= -1
                dx = self.direction * self.speed
            if tile[1].colliderect(self.rect.x, self.rect.y + dy, self.width, self.height):
                self.speed = 0
                if self.vel_y < 0:
                    self.vel_y = 0
                    dy = tile[1].bottom - self.rect.top
                elif self.vel_y >= 0:
                    self.vel_y = 0
                    dy = tile[1].top - self.rect.bottom



        self.rect.x += dx + screen_scroll
        self.rect.y += dy

        self.timer -= 1
        if self.timer <= 0:
            self.kill()
            explosion = Explosion(self.rect.x, self.rect.y, 0.5)
            explosion_group.add(explosion)
            if abs(self.rect.centerx - player.rect.centerx) < TILE_SIZE * 2 and \
                abs(self.rect.centery - player.rect.centery) < TILE_SIZE:
                player.health -= 50
            for enemy in enemy_group:
                if abs(self.rect.centerx - enemy.rect.centerx) < TILE_SIZE * 2 and \
                    abs(self.rect.centery - enemy.rect.centery) < TILE_SIZE:
                    enemy.health -= 50




class Explosion(pygame.sprite.Sprite):
    def __init__(self, x, y, scale):
        pygame.sprite.Sprite.__init__(self)
        self.images = []
        for i in range(7):
            img = pygame.image.load(f'img/fire/{i}.png').convert_alpha().convert_alpha()
            img = pygame.transform.scale(img,(int(img.get_width()*scale), int(img.get_height()*scale)))
            self.images.append(img)
        self.frame_index = 0
        self.image = self.images[self.frame_index]
        self.rect = self.image.get_rect()
        self.rect.center = (x, y)
        self.counter = 0

    def update(self):
        self.rect.x += screen_scroll
        EXPLOSION_SPEED = 4
        self.counter += 1
        if self.counter >= EXPLOSION_SPEED:
            self.counter = 0
            self.frame_index += 1
            if self.frame_index >= len(self.images):
                self.kill()
            else:
                self.image = self.images[self.frame_index]




start_button = button.Button(SCREEN_WIDTH // 2 - 130, SCREEN_HEIGT // 2 - 200, start_img, 1)
restart_button = button.Button(SCREEN_WIDTH // 2 - 130, SCREEN_HEIGT // 2 - 125, rest_img, 1)
exit_button = button.Button(SCREEN_WIDTH // 2 - 130, SCREEN_HEIGT // 2 - 125, exit_img, 1)
enemy_group = pygame.sprite.Group()
bullet_group = pygame.sprite.Group()
gran_group = pygame.sprite.Group()
explosion_group = pygame.sprite.Group()
item_box_group = pygame.sprite.Group()
water_group = pygame.sprite.Group()






world_data = []
for row in range(ROWS):
    r = [-1] * COLS
    world_data.append(r)
with open(f'level{level}_data.csv', newline='') as csvfile:
    reader = csv.reader(csvfile, delimiter=',')
    for x, row in enumerate(reader):
        for y, tile in enumerate(row):
            world_data[x][y] = int(tile)


world = World()
player, health_bar = world.process_data(world_data)

run = True
while run:
    clock.tick(FPS)

    if start_game == False:
        screen.fill(BG)
        if start_button.draw(screen):
            start_game = True
        if exit_button.draw(screen):
            run = False

    else:
        draw_bg()
        world.draw()

        health_bar.draw(player.health)
        draw_text(f'Пули: {player.ammo}', font, WHITE, 10, 70)
        draw_text(f'Гранаты: {player.grenades}', font, WHITE, 10, 105)
        player.draw()
        player.update()
        for enemy in enemy_group:
            enemy.ai()
            enemy.draw()
            enemy.update()

        bullet_group.update()
        bullet_group.draw(screen)

        item_box_group.update()
        item_box_group.draw(screen)

        water_group.update()
        water_group.draw(screen)

        gran_group.update()
        gran_group.draw(screen)

        explosion_group.update()
        explosion_group.draw(screen)



        if player.alive:
            if shoot:
                player.soot()
                player.update_action(2)
            elif gran and gran_thrown == False and player.grenades > 0:
                gran = Gran(player.rect.centerx+(0.5 * player.rect.size[0] * player.direction), player.rect.top,player.direction)
                gran_group.add(gran)
                gran_thrown = True
                player.grenades -= 1
            if moving_left or moving_right:
                player.update_action(1)
            else:
                player.update_action(0)
            screen_scroll = player.move(moving_left, moving_right)
            bg_scroll -= screen_scroll
        else:
            screen_scroll = 0
            if restart_button.draw(screen):
                bg_scroll = 0
                world_data = reset_level()
                with open(f'level{level}_data.csv', newline='') as csvfile:
                    reader = csv.reader(csvfile, delimiter=',')
                    for x, row in enumerate(reader):
                        for y, tile in enumerate(row):
                            world_data[x][y] = int(tile)
                world = World()
                player, health_bar = world.process_data(world_data)
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            run = False
        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_a:
                moving_left = True
            if event.key == pygame.K_d:
                moving_right = True
            if event.key == pygame.K_SPACE:
               shoot = True
            if event.key == pygame.K_g:
               gran = True
            if event.key == pygame.K_w and player.alive:
                player.jump = True
            if event.key == pygame.K_ESCAPE:
               start_game = False

        if event.type == pygame.KEYUP:
            if event.key == pygame.K_a:
                moving_left = False
            if event.key == pygame.K_d:
                moving_right = False
            if event.key == pygame.K_SPACE:
               shoot = False
            if event.key == pygame.K_g:
               gran = False
               gran_thrown = False

    pygame.display.update()
pygame.quit()


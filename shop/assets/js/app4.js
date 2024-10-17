document.querySelector('.order_cena').textContent = localStorage.getItem('cena');
const btn_prof = document.querySelector('#prof');
const reg = document.querySelector('.reg');
const vkl = document.querySelector('#vkl');
const vkl1 = document.querySelector('#vkl1');
const cont = document.querySelector('.container1');
const cont1 = document.querySelector('.container2');
const close = document.querySelector('.close');
const search = document.querySelector('.search');
const registr = document.querySelector('#registr');
const vhod = document.querySelector('#vhod');
const exit = document.querySelector('#exit');
const btn_oform = document.querySelector('.btn_oform');



btn_prof.addEventListener('click',function(){
    reg.style.display='block';
});
close.addEventListener('click',function(){
    reg.style.display='none';
});
vkl.addEventListener('click',function(){
    cont1.style.display='block';
    cont.style.display='none';
});
vkl1.addEventListener('click',function(){
    cont.style.display='block';
    cont1.style.display='none';
});
if (localStorage.getItem('log_ex')=='false')
{
    document.querySelector('.container').style.display='none';
    document.querySelector('.container3').style.display='block';
    document.querySelector('#name_log').textContent =localStorage.getItem('pols_pos') ;
}

else
    {registr.addEventListener('click',function(){
        let arr_reg = [];
        const login = document.querySelector('#email').value;
        const pas = document.querySelector('#pas').value;
        const name_pols = document.querySelector('#name_pols').value;
        const fam_pols = document.querySelector('#fam_pols').value;
        arr_reg.push(login,pas,name_pols,fam_pols);
       
        localStorage.setItem(login,arr_reg);
        document.querySelector('.container').style.display='none';
        document.querySelector('.container3').style.display='block';
        document.querySelector('#name_log').textContent = login;
        alert('Добро пожаловать '+ fam_pols +' '+ name_pols + ' !!!');
        localStorage.setItem('pols_pos',login);
        localStorage.setItem('log_ex','false');
        
    });
    vhod.addEventListener('click',function(){
        const login = document.querySelector('#email_v').value;
        const pas = document.querySelector('#pas_v').value;
        let arr_reg = localStorage.getItem(login)
        arr_reg = arr_reg.split(',');
        
        if ((arr_reg[0] == login)&&(arr_reg[1]==pas))
            {
            alert('Добро пожаловать '+ arr_reg[2] +' '+ arr_reg[3] + ' !!!');
            document.querySelector('.container').style.display='none';
            document.querySelector('.container3').style.display='block';
            document.querySelector('#name_log').textContent = login;
            localStorage.setItem('log_ex','false');
            localStorage.setItem('pols_pos',login);
                location.reload();
            }
        else 
           { 
               alert('не правильный логин или пароль');
               alert(arr_reg);
       
           }

    });}

exit.addEventListener('click',function(){
    document.querySelector('.container').style.display='block';
    document.querySelector('.container3').style.display='none';
    localStorage.setItem('log_ex','true');
    location.reload();
    
});
if (localStorage.getItem('log_ex')=='false')    
{let log = localStorage.getItem('pols_pos');
    let data = localStorage.getItem(log);
    data = data.split(',');
    document.querySelector('.ch_fam').value = data[2];
    document.querySelector('.ch_name').value = data[3];
    document.querySelector('.email').value = data[0];
 
}
btn_oform.addEventListener('click',function(){
    alert('Заказ оформлен и будет доставлен по адресу '+document.querySelector('.adr').value+'. '+'Оплата производится курьеру');
});

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


search.addEventListener('click',function(){
    document.querySelector(".cont_gl").style.display='none';
    document.querySelector(".cont_search").style.display='block';
    const tovar = document.querySelectorAll('.about_item_1');
    const sear = document.querySelector('#search').value;
    const name = document.querySelectorAll('#name');
    let arr = sear;
    
    arr = arr.split(' ');
    for(let i=0; i<tovar.length;i++){
        let arr1 = name[i].innerHTML;
        arr1 = arr1.split(' ');
        let inter = arr1.filter((item) => 
        arr.includes(item));
        if (inter.length >= 3){
            tovar[i].style.display='block';
                   }
        else {tovar[i].style.display='none';}
        
    }
    console.log(name);
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
            }
        else 
           { 
               alert('не правильный логин или пароль');
               
       
           }

    });}


exit.addEventListener('click',function(){
    document.querySelector('.container').style.display='block';
    document.querySelector('.container3').style.display='none';
    localStorage.setItem('log_ex','true');
    location.reload();
    
});



let arr = localStorage.getItem('111');
arr = arr.split(',');
let arr_id = [];
const id = document.querySelectorAll('.id');
const in_bask = document.querySelectorAll(' .btn_cena1');
for(let i of id){
    arr_id.push(i.textContent);
}
for(let j=0; j<arr.length;j++)
    for(let i=0; i<arr_id.length;i++)
        if (arr_id[i] == arr[j])
            in_bask[i].style.display = 'none';
for(let i=0; i < in_bask.length; i++ )
    in_bask[i].addEventListener('click',function(){
        in_bask[i].style.display = 'none';
        arr.push(id[i].textContent);
        localStorage.setItem('111',arr);
        });

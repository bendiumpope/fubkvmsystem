const menuBtn = document.querySelector(".menu-btn");
const nav = document.querySelector("nav");
const logoImage = document.querySelector(".logo img");

menuBtn.addEventListener("click", function(){
    menuBtn.classList.toggle("active");
    nav.classList.toggle("active");
})

window.addEventListener("scroll", function(){
    if(window.pageYOffset > 4){
        nav.classList.add("scrolled")
    }else{
        nav.classList.remove("scrolled");
    }
})
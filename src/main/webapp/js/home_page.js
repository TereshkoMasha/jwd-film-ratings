// INTERACTIVE MENU
let links = document.querySelectorAll('.menu a'),
    ind = document.querySelector('.indicator')


// start position
hideInd()

links.forEach(item => {
    item.addEventListener('mouseenter', (e) => {
        moveInd(e.target)
    })
})

function moveInd(element) {
    ind.style.width = `${element.offsetWidth}px`
    ind.style.left = `${element.offsetLeft}px`
}

let menu = document.querySelector('.menu')

menu.addEventListener('mouseleave', (e) => {
    hideInd()
})

function hideInd() {
    ind.style.width = `0px`
    ind.style.left = `${links[0].offsetLeft}px`
}
window.onscroll = function () {
    myFunction()
};

let header = document.getElementById("menu");
let sticky = header.offsetTop;

function myFunction() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
        ind.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
        ind.classList.remove("sticky");
    }
}
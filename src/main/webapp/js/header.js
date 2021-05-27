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

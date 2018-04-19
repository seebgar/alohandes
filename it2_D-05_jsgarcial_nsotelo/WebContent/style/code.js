/* JQUERY */




/*+++++++++++++++++++++++++++++++++++*/
/* PARALLAX EFFECT */
/*+++++++++++++++++++++++++++++++++++*/




$(document).ready(function () {
    // jquery goes here

    $(window).scroll(function () {
        /* PARALLAX BACKGOUND EFFECT*/
        parallax();
    });
});




function parallax() {

    let scroll_num = $(window).scrollTop(); // numero que representa el movimeinto de scroll
    const scroll_factor = 0.35;
    let parallax = scroll_num * scroll_factor;

    $(".parallax").css({
        'background-position': 'center ' + (parallax) + 'px'
    });

};

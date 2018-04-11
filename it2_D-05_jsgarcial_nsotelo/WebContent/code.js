

function change() {
    const ims = document.querySelectorAll( '.ce' );
    let im_arr = Array.from(ims);
    let prev = -1;
    
    if ( random_piker(im_arr.length) < im_arr.length) {
        let nuevo = random_piker(im_arr.length);
        if ( nuevo !== prev ) {
            im_arr[nuevo].innerHTML = `<img src="/resources/${random_piker_im(14)}.jpg" alt="food">`;
        }
        prev = nuevo;
    }
    /*
    im_arr.forEach( curr => {
        curr.innerHTML = `<img src="/resources/${random_piker_im(14)}.jpg" alt="food">`;
    } );
    */
}

function random_piker( size ) {
    return Math.floor( Math.random() * size );
}

function random_piker_im( size ) {
    return Math.floor( Math.random() * size ) + 1;
}


setInterval('change();', 2000);






/* JQUERY */




/*+++++++++++++++++++++++++++++++++++*/
/* PARALLAX EFFECT */
/*+++++++++++++++++++++++++++++++++++*/




$(document).ready( function() {
    // jquery goes here
    
    $(window).scroll( function() {
        /* PARALLAX BACKGOUND EFFECT*/
        parallax( );
    } );
    
    
    /* FLOATING MENU */
    
    $('.js_sec_2').waypoint( function( direction ) {
        if ( direction === 'down' ) 
            $('nav').addClass('stick');
        else if ( direction === 'up' )
            $('nav').removeClass('stick');
        
    } , {
        offset: '40px;'
    } );

    
    
    
    /* SCROLL TO SECTION QHEN BTN PRESSED*/
    
    $('.scroll_plan').click( function() {
        // scrolls to the top is the section witha speed of 1 se
        $('html, body').animate({scrollTop: $('.js_plan').offset().top}, 1500);
    } );
    
    
    $('.scroll_sec2').click( function() {
        $('html, body').animate( {scrollTop: $('.js_sec_2').offset().top} , 1000  );
    } );
    
    
    
    /* SCROLL TO SECTION WITH ID */
    /* the triggerer or button has an href="#name_of_section_id"*/
    
    
    // Select all links with hashes
    $('a[href*="#"]').click(function(event) {
        // On-page links
        if (
          location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '') 
          && 
          location.hostname == this.hostname
                ) {
          // Figure out element to scroll to
          var target = $(this.hash);
          target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');
          // Does a scroll target exist?
          if (target.length) {
            // Only prevent default if animation is actually gonna happen
            event.preventDefault();
            $('html, body').animate({
              scrollTop: target.offset().top
            }, 1000 ); 
              return false;
          }
        }
      });
    
    
    
    /* ANIMATIONS */
    
    
    
    
    $( '.js_plan' ).waypoint( function( direction ) {
        $('.anim_prem').addClass("animated pulse");
    } , {
        offset : '35%;'
    });
    
    
    
    
    /*  MOBILE VERSION MENU */
    
    
    $('.js_menu_mobile').click( function() {
        const menu = $('.js_menu_nav');
        const menu_icon = $('.menu_icon');
        
        // time of animation duration
        menu.slideToggle( 200 );
        
        if ( menu_icon.hasClass('ion-android-close') ) {
            menu_icon.removeClass('ion-android-close');
            menu_icon.addClass('ion-drag'); 
        } else {
            menu_icon.removeClass('ion-drag'); 
            menu_icon.addClass('ion-android-close');
        }
    
    } );
    
  
    
    
    
    
    
    /* GOOGLE MAPS */
    
    let map = new GMaps({
        div: '.map', 
        lat: 4.598893,
        lng: -74.012626, 
        zoom: 13
    });
    
    map.addMarker({ 
        lat: 4.603121,
        lng: -74.065583, 
        draggable: false,
        title: 'Omnifood Central',
        click: function(event) {
            alert('Hope to see you soon!');
        }
    });
    

    
} );




function parallax( ) {
    
    let scroll_num = $(window).scrollTop(); // numero que representa el movimeinto de scroll
    const scroll_factor = 0.35;
    let parallax = scroll_num * scroll_factor;
    
    $(".parallax").css({
        'background-position' : 'center ' + ( parallax ) + 'px'
    });
    
};









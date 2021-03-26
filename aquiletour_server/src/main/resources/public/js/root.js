window.onload = function(){
    $('.navbar-nav>li>a').on('click', function(){
        $('.navbar-collapse').collapse('hide');
    });

    /*

    $('a').on('click', function(e){
        
        document.getElementById('page-loader').style.display = 'block';
        e.preventDefault();
        var destination = this.href;
        setTimeout(function() {
            window.location = destination;
        },1000);

        $('<iframe>').hide().appendTo('body').load(function() {
            window.location = destination;
        }).attr('src', destination);

        
    });
    */

    /*
    document.getElementById('website-directory').innerHTML = window.location.pathname;
    console.log(window.location.pathname);
    */
}

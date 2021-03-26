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
    
    

    // from: https://stackoverflow.com/questions/3522090/event-when-window-location-href-changes
    var oldHref = document.location.href;

    window.onload = function() {

        var bodyList = document.querySelector("body")

        var observer = new MutationObserver(function(mutations) {

                mutations.forEach(function(mutation) {

                    if (oldHref != document.location.href) {

                        oldHref = document.location.href;

                        console.log("TEST");

                    }

                });

            });

        var config = {
            childList: true,
            subtree: true
        };

        observer.observe(bodyList, config);

    };
}

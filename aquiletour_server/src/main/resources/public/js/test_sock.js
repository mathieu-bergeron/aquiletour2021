var sock = null;
$(function(){
    console.log("SOCK");

    sock = new SockJS('http://localhost:8080/_socket/messages');

    sock.onopen = function(){
        console.log('open');
        sock.send('asdf');
    }

    sock.onclose = function(){
        console.log('close');
    }

    sock.onmessage = function(e){
        console.log(e.data);
    }
});



window.onload = function(){
<<<<<<< HEAD
    
        $(function() {
            
            $( "#appointment-list" ).sortable({
                handle:'.handle',
                update: function(event, ui){
                    

                    const appointment = $(ui.item);
                    const nextAppointment = $(appointment.next());
                    const prevAppointment = $(appointment.prev());

                    const appointmentId = appointment.attr("id");
                    const nextAppointmentId = nextAppointment.attr("id");
                    const prevAppointmentId = prevAppointment.attr("id");


                    if (nextAppointment.length > 0) {
                        
                        //window.location = window.location.pathname + "?moveId=" + appointmentId + "&beforeId=" + nextAppointmentId;
                        history.pushState({
                            id: 'queue'
                          }, 'Queue', window.location.pathname + "?moveId=" + appointmentId + "&beforeId=" + nextAppointmentId);
                        
                    } else if (prevAppointment.length > 0) {
                    
                        //window.location = window.location.pathname + "?moveId=" + appointmentId + "&afterId=" + prevAppointmentId;

                        history.pushState({
                            id: 'queue'
                          }, 'Queue', window.location.pathname + "?moveId=" + appointmentId + "&afterId=" + prevAppointmentId);
                    }

                    console.log(nextAppointment);
                }
            
            });
            
            
        });
    
=======
    //for table
    function deleteRow(r) {
      var i = r.parentNode.parentNode.rowIndex;
      document.getElementById("queueTable").deleteRow(i);
    }

    //for list-group
    function fnDelete(obj) {
        obj.closest("li").remove()
    }

    $(function() {
        $( "#appointment-list" ).sortable({
            handle:'.handle',
            update: function(event, ui){
                window.location = "/moveId=&beforeId=";
            }
           
        });
    } );
>>>>>>> main
}


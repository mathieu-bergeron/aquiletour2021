
window.onload = function(){
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
                

                const appointment = $(ui.item);
                const nextAppointment = $(appointment.next());
                const prevAppointment = $(appointment.prev());

                const appointmentId = appointment.attr("id");
                const nextAppointmentId = nextAppointment.attr("id");
                const prevAppointmentId = prevAppointment.attr("id");


                if (nextAppointment.length > 0) {
                    
                    window.location = window.location.pathname + "?moveId=" + appointmentId + "&beforeId=" + nextAppointmentId;
                    
                } else if (prevAppointment.length > 0) {
                 
                    window.location = window.location.pathname + "?moveId=" + appointmentId + "&afterId=" + prevAppointmentId;
                }

                console.log(nextAppointment);
            }
           
        });

        
    });
$(function() {
    var $appointmentList = $('#appointment-list');

    $appointmentList.touch({
        // Turn on document tracking for smoother dragging.
        trackDocument: true,

        // Set drag threshold to zero for maximum drag sensitivity.
            dragThreshold: 0,

        // Set drag delay to zero for fastest drag response.
            dragDelay: 0,

        // Delegate touch events to items.
            delegateSelector: '.item',

        // Lower tap and hold delay.
            tapAndHoldDelay: 150,

        // Prevent default events for drag events.
            preventDefault: {
                drag: true
            }
    });
});
    


    
}


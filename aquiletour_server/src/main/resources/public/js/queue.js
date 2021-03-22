function initializeQueue(viewRootElement){

    const appointmentList = viewRootElement.find("#appointment-list");

    appointmentList.sortable({
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
        }
    });
}

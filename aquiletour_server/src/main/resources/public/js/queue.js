function initializeQueue(viewRootElement, jSweet){

    console.log(jSweet);

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

                if(jSweet){
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?moveId=" + appointmentId + "&beforeId=" + nextAppointmentId);

                }else{

                    window.location = window.location.pathname + "?moveId=" + appointmentId + "&beforeId=" + nextAppointmentId;
                }
                
                
            } else if (prevAppointment.length > 0) {

                if(jSweet){
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?moveId=" + appointmentId + "&afterId=" + prevAppointmentId);

                }else{

                    window.location = window.location.pathname + "?moveId=" + appointmentId + "&afterId=" + prevAppointmentId;
                }
            }
        }
    });
}

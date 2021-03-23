function initializeQueue(viewRootElement, jSweet){

    const appointmentList = viewRootElement.find("#appointment-list");

    appointmentList.sortable({
        handle:'.handle',
        update: function(event, ui){

            function trimId(fullId){
                return fullId.replace("appointment-","");
            }
            

            const appointment = $(ui.item);
            const nextAppointment = $(appointment.next());
            const prevAppointment = $(appointment.prev());

            const appointmentId = trimId(appointment.attr("id"));

            if (nextAppointment.length > 0) {

                const nextAppointmentId = trimId(nextAppointment.attr("id"));

                if(jSweet){
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?move=" + appointmentId + "&before=" + nextAppointmentId);

                }else{

                    window.location = window.location.pathname + "?move=" + appointmentId + "&before=" + nextAppointmentId;
                }
                
                
            } else if (prevAppointment.length > 0) {

                const prevAppointmentId = trimId(prevAppointment.attr("id"));

                if(jSweet){
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?move=" + appointmentId + "&after=" + prevAppointmentId);

                }else{

                    window.location = window.location.pathname + "?move=" + appointmentId + "&after=" + prevAppointmentId;
                }
            }
        }
    });
}

function initializeQueue(viewRootElement, jSweet){

    const appointmentList = viewRootElement.find("#appointment-list");

    appointmentList.sortable({
        handle:'.handle'
    });

    return;

    appointmentList.sortable({
        handle:'.handle',
        update: function(event, ui){

            function trimId(fullId){
                return fullId.replace("appointment-","");
            }

            const queueIdElement = viewRootElement.find("#queue-id");
            const queueId = queueIdElement.html();

            const appointment = $(ui.item);
            const nextAppointment = $(appointment.next());
            const prevAppointment = $(appointment.prev());

            const appointmentId = trimId(appointment.attr("id"));

            if (nextAppointment.length > 0) {

                const nextAppointmentId = trimId(nextAppointment.attr("id"));

                if(jSweet){

                    let moveAppointmentMessage = (ca.ntro.services.Ntro.messages().create(ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage));
                    moveAppointmentMessage.setCourseId(queueId);
                    moveAppointmentMessage.setAppointmentId(appointmentId);
                    moveAppointmentMessage.setDestinationId(nextAppointmentId);
                    moveAppointmentMessage.setBeforeOrAfter("before");
                    ca.ntro.services.Ntro.messages().send(moveAppointmentMessage);


                    /*
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?move=" + appointmentId + "&before=" + nextAppointmentId);
                    */

                }else{

                    window.location = window.location.pathname + "?move=" + appointmentId + "&before=" + nextAppointmentId;
                }
                
                
            } else if (prevAppointment.length > 0) {

                const prevAppointmentId = trimId(prevAppointment.attr("id"));

                if(jSweet){

                    let moveAppointmentMessage = (ca.ntro.services.Ntro.messages().create(ca.aquiletour.core.pages.queue.teacher.messages.MoveAppointmentMessage));
                    moveAppointmentMessage.setCourseId(queueId);
                    moveAppointmentMessage.setAppointmentId(appointmentId);
                    moveAppointmentMessage.setDestinationId(prevAppointmentId);
                    moveAppointmentMessage.setBeforeOrAfter("after");
                    ca.ntro.services.Ntro.messages().send(moveAppointmentMessage);

                    /*
                    history.pushState({
                        id: 'queue'
                      }, 'Queue', window.location.pathname + "?move=" + appointmentId + "&after=" + prevAppointmentId);
                   */

                }else{

                    window.location = window.location.pathname + "?move=" + appointmentId + "&after=" + prevAppointmentId;
                }
            }
        }
    });
}

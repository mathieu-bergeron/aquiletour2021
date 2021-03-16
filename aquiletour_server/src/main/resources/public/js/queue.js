

window.onload = function(){
    
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
                       /* let addCourseMessage = (ca.ntro.services.Ntro.messages().create(ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage));
                        addCourseMessage.setCourse(new ca.aquiletour.core.pages.dashboards.values.CourseSummary(courseTitle, courseId, false, false, 0));
                        addCourseMessage.setUser(user);
                        ca.ntro.services.Ntro.messages().send(addCourseMessage);*/
                        
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
    
}


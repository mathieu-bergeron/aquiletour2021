/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            class Constants {
            }
            Constants.LANG = "fr";
            core.Constants = Constants;
            Constants["__class"] = "ca.aquiletour.core.Constants";
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class QuitMessage extends ca.ntro.messages.NtroMessage {
                        constructor() {
                            super();
                        }
                    }
                    root.QuitMessage = QuitMessage;
                    QuitMessage["__class"] = "ca.aquiletour.core.pages.root.QuitMessage";
                    QuitMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                })(root = pages.root || (pages.root = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class RootViewHandler extends ca.ntro.core.mvc.WindowViewHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroWindow} window
                         * @param {*} view
                         */
                        handle(window, view) {
                            ca.ntro.core.system.trace.T.call(this);
                            window.installRootView(view);
                        }
                    }
                    root.RootViewHandler = RootViewHandler;
                    RootViewHandler["__class"] = "ca.aquiletour.core.pages.root.RootViewHandler";
                    RootViewHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(root = pages.root || (pages.root = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class QuitMessageHandler extends ca.ntro.core.mvc.MessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.root.QuitMessage} message
                         */
                        handle(message) {
                            ca.ntro.core.system.trace.T.call(this);
                            ca.ntro.core.Ntro.appCloser().close();
                        }
                    }
                    root.QuitMessageHandler = QuitMessageHandler;
                    QuitMessageHandler["__class"] = "ca.aquiletour.core.pages.root.QuitMessageHandler";
                    QuitMessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(root = pages.root || (pages.root = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class ShowLoginDialogMessage extends ca.ntro.messages.NtroMessage {
                        constructor() {
                            super();
                        }
                    }
                    root.ShowLoginDialogMessage = ShowLoginDialogMessage;
                    ShowLoginDialogMessage["__class"] = "ca.aquiletour.core.pages.root.ShowLoginDialogMessage";
                    ShowLoginDialogMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                })(root = pages.root || (pages.root = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class RootController extends ca.ntro.core.mvc.NtroRootController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setViewLoader("ca.aquiletour.core.pages.root.RootView", this.currentContext().getLang());
                            this.addSubController(ca.aquiletour.core.pages.dashboards.student.StudentDashboardController, "mescours");
                            this.addSubController(ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardController, "mescours");
                            this.addSubController(ca.aquiletour.core.pages.queues.QueuesController, "billetteries");
                            this.addSubController(ca.aquiletour.core.pages.queue.QueueController, "billetterie");
                            this.addSubController(ca.aquiletour.core.pages.users.UsersController, "usagers");
                            this.addSubController(ca.aquiletour.core.pages.login.LoginController, "connexion");
                            this.addSubController(ca.aquiletour.core.pages.home.HomeController, "accueil");
                            this.addWindowViewHandler(new ca.aquiletour.core.pages.root.RootViewHandler());
                            this.addMessageHandler(ca.aquiletour.core.pages.root.QuitMessage, new ca.aquiletour.core.pages.root.QuitMessageHandler());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    root.RootController = RootController;
                    RootController["__class"] = "ca.aquiletour.core.pages.root.RootController";
                    RootController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(root = pages.root || (pages.root = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var activity;
                (function (activity) {
                    class ActivityController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    activity.ActivityController = ActivityController;
                    ActivityController["__class"] = "ca.aquiletour.core.pages.activity.ActivityController";
                    ActivityController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(activity = pages.activity || (pages.activity = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var course;
                (function (course) {
                    class CourseController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    course.CourseController = CourseController;
                    CourseController["__class"] = "ca.aquiletour.core.pages.course.CourseController";
                    CourseController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(course = pages.course || (pages.course = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var values;
                    (function (values) {
                        class Appointment extends ca.ntro.core.models.properties.NtroModelValue {
                            constructor() {
                                super();
                                if (this.id === undefined)
                                    this.id = null;
                                if (this.studentId === undefined)
                                    this.studentId = null;
                                if (this.studentName === undefined)
                                    this.studentName = null;
                                if (this.studentSurame === undefined)
                                    this.studentSurame = null;
                            }
                            getAppointmentId() {
                                return this.id;
                            }
                            setAppointmentId(id) {
                                this.id = id;
                            }
                            getStudentId() {
                                return this.studentId;
                            }
                            setStudentId(studentId) {
                                this.studentId = studentId;
                            }
                            getStudentName() {
                                return this.studentName;
                            }
                            setStudentName(studentName) {
                                this.studentName = studentName;
                            }
                            getStudentSurname() {
                                return this.studentSurame;
                            }
                            setStudentSurname(studentSurame) {
                                this.studentSurame = studentSurame;
                            }
                        }
                        values.Appointment = Appointment;
                        Appointment["__class"] = "ca.aquiletour.core.pages.queue.values.Appointment";
                        Appointment["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                    })(values = queue.values || (queue.values = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var values;
                    (function (values) {
                        class ObservableAppointmentList extends ca.ntro.core.models.properties.observable.list.ObservableList {
                            constructor(value) {
                                if (((value != null && (value instanceof Array)) || value === null)) {
                                    let __args = arguments;
                                    super(value);
                                }
                                else if (value === undefined) {
                                    let __args = arguments;
                                    super(([]));
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            /**
                             *
                             * @return {*}
                             */
                            valueType() {
                                ca.ntro.core.system.trace.T.call(this);
                                return "java.util.List";
                            }
                        }
                        values.ObservableAppointmentList = ObservableAppointmentList;
                        ObservableAppointmentList["__class"] = "ca.aquiletour.core.pages.queue.values.ObservableAppointmentList";
                        ObservableAppointmentList["__interfaces"] = ["java.io.Serializable"];
                    })(values = queue.values || (queue.values = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var values;
                    (function (values) {
                        class ObservableAppointmentMap extends ca.ntro.core.models.properties.observable.map.ObservableMap {
                            constructor(map) {
                                if (((map != null && (map instanceof Object)) || map === null)) {
                                    let __args = arguments;
                                    super(map);
                                }
                                else if (map === undefined) {
                                    let __args = arguments;
                                    super(({}));
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            /**
                             *
                             * @return {*}
                             */
                            valueType() {
                                ca.ntro.core.system.trace.T.call(this);
                                return ca.ntro.core.models.properties.NtroModelValue;
                            }
                        }
                        values.ObservableAppointmentMap = ObservableAppointmentMap;
                        ObservableAppointmentMap["__class"] = "ca.aquiletour.core.pages.queue.values.ObservableAppointmentMap";
                        ObservableAppointmentMap["__interfaces"] = ["java.io.Serializable"];
                    })(values = queue.values || (queue.values = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    class QueueController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setModelLoader(new ca.ntro.core.models.EmptyModelLoader());
                            this.setViewLoader("ca.aquiletour.core.pages.queue.QueueView", this.currentContext().getLang());
                            this.addSubViewLoader("ca.aquiletour.core.pages.queue.AppointmentView", this.currentContext().getLang());
                            this.addModelViewSubViewHandler("ca.aquiletour.core.pages.queue.AppointmentView", new ca.aquiletour.core.pages.queue.handlers.QueueViewModel());
                            this.addControllerMessageHandler(ca.aquiletour.core.pages.queue.messages.ShowQueueMessage, new ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                            ca.ntro.core.system.trace.T.call(this);
                            if (!previousContext.hasSameLang(this.currentContext())) {
                                this.setViewLoader("ca.aquiletour.core.pages.queue.QueueView", this.currentContext().getLang());
                                this.addSubViewLoader("ca.aquiletour.core.pages.queue.AppointmentView", this.currentContext().getLang());
                            }
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                        }
                    }
                    queue.QueueController = QueueController;
                    QueueController["__class"] = "ca.aquiletour.core.pages.queue.QueueController";
                    QueueController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    class QueueModel extends ca.ntro.core.models.NtroModel {
                        constructor() {
                            super();
                            /*private*/ this.appointments = new ca.aquiletour.core.pages.queue.values.ObservableAppointmentMap();
                            /*private*/ this.studentIds = ([]);
                            if (this.maxId === undefined)
                                this.maxId = 0;
                        }
                        /**
                         *
                         */
                        initializeStoredValues() {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        addAppointment(appointment) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setMaxId(this.getMaxId() + 1);
                            let appointmenId = ('' + (this.getMaxId()));
                            appointment.setAppointmentId(appointmenId);
                            this.appointments.addEntry(appointmenId, appointment);
                        }
                        deleteAppointment(appointmentId) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.appointments.removeEntry(appointmentId);
                        }
                        getAppointments() {
                            ca.ntro.core.system.trace.T.call(this);
                            return this.appointments;
                        }
                        setAppointments(appointments) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.appointments = appointments;
                        }
                        getMaxId() {
                            return this.maxId;
                        }
                        setMaxId(maxId) {
                            this.maxId = maxId;
                        }
                        addStudentId(studentId) {
                            ca.ntro.core.system.trace.T.call(this);
                            /* add */ (this.studentIds.push(studentId) > 0);
                        }
                        deleteStudent(studentId) {
                            ca.ntro.core.system.trace.T.call(this);
                            /* remove */ (a => { let index = a.indexOf(studentId); if (index >= 0) {
                                a.splice(index, 1);
                                return true;
                            }
                            else {
                                return false;
                            } })(this.studentIds);
                        }
                        getStudentIds() {
                            ca.ntro.core.system.trace.T.call(this);
                            return this.studentIds;
                        }
                        setStudentIds(studentIds) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.studentIds = studentIds;
                        }
                    }
                    queue.QueueModel = QueueModel;
                    QueueModel["__class"] = "ca.aquiletour.core.pages.queue.QueueModel";
                    QueueModel["__interfaces"] = ["java.io.Serializable"];
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var handlers;
                    (function (handlers) {
                        class AddAppointmentHandler extends ca.ntro.core.mvc.ModelMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.queue.QueueModel} model
                             * @param {ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage} message
                             */
                            handle(model, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                model.addAppointment(message.getAppointment());
                                model.save();
                            }
                        }
                        handlers.AddAppointmentHandler = AddAppointmentHandler;
                        AddAppointmentHandler["__class"] = "ca.aquiletour.core.pages.queue.handlers.AddAppointmentHandler";
                        AddAppointmentHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(handlers = queue.handlers || (queue.handlers = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var handlers;
                    (function (handlers) {
                        class DeleteAppointmentHandler extends ca.ntro.core.mvc.ModelMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.queue.QueueModel} model
                             * @param {ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage} message
                             */
                            handle(model, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                model.deleteAppointment(message.getAppointmentId());
                                model.save();
                            }
                        }
                        handlers.DeleteAppointmentHandler = DeleteAppointmentHandler;
                        DeleteAppointmentHandler["__class"] = "ca.aquiletour.core.pages.queue.handlers.DeleteAppointmentHandler";
                        DeleteAppointmentHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(handlers = queue.handlers || (queue.handlers = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var handlers;
                    (function (handlers) {
                        class QueueViewModel extends ca.ntro.core.mvc.ModelViewSubViewHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.queue.QueueModel} model
                             * @param {*} view
                             * @param {ca.ntro.core.mvc.ViewLoader} subViewLoader
                             */
                            handle(model, view, subViewLoader) {
                                ca.ntro.core.system.trace.T.call(this);
                                model.getAppointments().observe(new QueueViewModel.QueueViewModel$0(this, subViewLoader, view));
                            }
                        }
                        handlers.QueueViewModel = QueueViewModel;
                        QueueViewModel["__class"] = "ca.aquiletour.core.pages.queue.handlers.QueueViewModel";
                        QueueViewModel["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                        (function (QueueViewModel) {
                            class QueueViewModel$0 {
                                constructor(__parent, subViewLoader, view) {
                                    this.subViewLoader = subViewLoader;
                                    this.view = view;
                                    this.__parent = __parent;
                                }
                                /**
                                 *
                                 * @param {*} oldValue
                                 * @param {*} value
                                 */
                                onValueChanged(oldValue, value) {
                                }
                                /**
                                 *
                                 * @param {*} value
                                 */
                                onValue(value) {
                                }
                                /**
                                 *
                                 * @param {*} lastValue
                                 */
                                onDeleted(lastValue) {
                                }
                                /**
                                 *
                                 * @param {string} key
                                 * @param {ca.aquiletour.core.pages.queue.values.Appointment} value
                                 */
                                onEntryAdded(key, value) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let appointmentView = this.subViewLoader.createView();
                                    appointmentView.displayAppointement(value);
                                    this.view.appendAppointement(value, appointmentView);
                                }
                                /**
                                 *
                                 * @param {string} key
                                 * @param {ca.aquiletour.core.pages.queue.values.Appointment} value
                                 */
                                onEntryRemoved(key, value) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.view.deleteAppointment(key);
                                }
                            }
                            QueueViewModel.QueueViewModel$0 = QueueViewModel$0;
                            QueueViewModel$0["__interfaces"] = ["ca.ntro.core.models.properties.observable.map.MapObserver", "ca.ntro.core.models.properties.observable.simple.ValueObserver", "ca.ntro.core.models.properties.observable.simple.ValueListener", "ca.ntro.core.models.properties.observable.simple.DeletionListener"];
                        })(QueueViewModel = handlers.QueueViewModel || (handlers.QueueViewModel = {}));
                    })(handlers = queue.handlers || (queue.handlers = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var handlers;
                    (function (handlers) {
                        class ShowQueueHandler extends ca.ntro.core.mvc.ControllerMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.queue.QueueController} currentController
                             * @param {*} currentView
                             * @param {ca.aquiletour.core.pages.queue.messages.ShowQueueMessage} message
                             */
                            handle(currentController, currentView, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                let courseId = message.getCourseId();
                                let authToken = currentController.currentContext().getUser().getAuthToken();
                                currentController.setModelLoader(ca.ntro.core.services.stores.NetworkStore.getLoader(ca.aquiletour.core.pages.queue.QueueModel, authToken, courseId));
                                let rootView = currentController.getParentController().getView();
                                rootView.showQueue(currentView);
                            }
                        }
                        handlers.ShowQueueHandler = ShowQueueHandler;
                        ShowQueueHandler["__class"] = "ca.aquiletour.core.pages.queue.handlers.ShowQueueHandler";
                        ShowQueueHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(handlers = queue.handlers || (queue.handlers = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var messages;
                    (function (messages) {
                        class AddAppointmentMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                                if (this.appointment === undefined)
                                    this.appointment = null;
                                if (this.user === undefined)
                                    this.user = null;
                                if (this.courseId === undefined)
                                    this.courseId = null;
                            }
                            setAppointment(appointment) {
                                ca.ntro.core.system.trace.T.call(this);
                                this.appointment = appointment;
                            }
                            getAppointment() {
                                ca.ntro.core.system.trace.T.call(this);
                                return this.appointment;
                            }
                            getUser() {
                                return this.user;
                            }
                            setUser(user) {
                                this.user = user;
                            }
                            getCourseId() {
                                return this.courseId;
                            }
                            setCourseId(courseId) {
                                this.courseId = courseId;
                            }
                        }
                        messages.AddAppointmentMessage = AddAppointmentMessage;
                        AddAppointmentMessage["__class"] = "ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage";
                        AddAppointmentMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = queue.messages || (queue.messages = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var messages;
                    (function (messages) {
                        class DeleteAppointmentMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                                if (this.appointmentId === undefined)
                                    this.appointmentId = null;
                                if (this.user === undefined)
                                    this.user = null;
                                if (this.courseId === undefined)
                                    this.courseId = null;
                            }
                            getAppointmentId() {
                                ca.ntro.core.system.trace.T.call(this);
                                return this.appointmentId;
                            }
                            setAppointmentId(appointmentId) {
                                ca.ntro.core.system.trace.T.call(this);
                                this.appointmentId = appointmentId;
                            }
                            getUser() {
                                return this.user;
                            }
                            setUser(user) {
                                this.user = user;
                            }
                            getCourseId() {
                                return this.courseId;
                            }
                            setCourseId(courseId) {
                                this.courseId = courseId;
                            }
                        }
                        messages.DeleteAppointmentMessage = DeleteAppointmentMessage;
                        DeleteAppointmentMessage["__class"] = "ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage";
                        DeleteAppointmentMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = queue.messages || (queue.messages = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    var messages;
                    (function (messages) {
                        class ShowQueueMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                                if (this.courseId === undefined)
                                    this.courseId = null;
                            }
                            getCourseId() {
                                return this.courseId;
                            }
                            setCourseId(courseId) {
                                this.courseId = courseId;
                            }
                        }
                        messages.ShowQueueMessage = ShowQueueMessage;
                        ShowQueueMessage["__class"] = "ca.aquiletour.core.pages.queue.messages.ShowQueueMessage";
                        ShowQueueMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = queue.messages || (queue.messages = {}));
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var login;
                (function (login) {
                    class ShowLoginHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {*} parentView
                         * @param {*} currentView
                         * @param {ca.aquiletour.core.pages.login.ShowLoginMessage} message
                         */
                        handle(parentView, currentView, message) {
                            ca.ntro.core.system.trace.T.call(this);
                            parentView.showLogin(currentView);
                        }
                    }
                    login.ShowLoginHandler = ShowLoginHandler;
                    ShowLoginHandler["__class"] = "ca.aquiletour.core.pages.login.ShowLoginHandler";
                    ShowLoginHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(login = pages.login || (pages.login = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var login;
                (function (login) {
                    class LoginController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setViewLoader("ca.aquiletour.core.pages.login.LoginView", "fr");
                            this.addParentViewMessageHandler(ca.aquiletour.core.pages.login.ShowLoginMessage, new ca.aquiletour.core.pages.login.ShowLoginHandler());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    login.LoginController = LoginController;
                    LoginController["__class"] = "ca.aquiletour.core.pages.login.LoginController";
                    LoginController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(login = pages.login || (pages.login = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var login;
                (function (login) {
                    class ShowLoginMessage extends ca.ntro.messages.NtroMessage {
                        constructor() {
                            super();
                        }
                    }
                    login.ShowLoginMessage = ShowLoginMessage;
                    ShowLoginMessage["__class"] = "ca.aquiletour.core.pages.login.ShowLoginMessage";
                    ShowLoginMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                })(login = pages.login || (pages.login = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class AddUserHandler extends ca.ntro.core.mvc.ModelMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.users.UsersModel} model
                             * @param {ca.aquiletour.core.pages.users.messages.AddUserMessage} message
                             */
                            handle(model, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                model.addUser(message.getUser());
                                model.save();
                            }
                        }
                        messages.AddUserHandler = AddUserHandler;
                        AddUserHandler["__class"] = "ca.aquiletour.core.pages.users.messages.AddUserHandler";
                        AddUserHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class DeleteUserHandler extends ca.ntro.core.mvc.ModelMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.users.UsersModel} model
                             * @param {ca.aquiletour.core.pages.users.messages.DeleteUserMessage} message
                             */
                            handle(model, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                model.deleteUser(message.getUserId());
                                model.save();
                            }
                        }
                        messages.DeleteUserHandler = DeleteUserHandler;
                        DeleteUserHandler["__class"] = "ca.aquiletour.core.pages.users.messages.DeleteUserHandler";
                        DeleteUserHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class DeleteUserMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                                if (this.userId === undefined)
                                    this.userId = null;
                            }
                            getUserId() {
                                ca.ntro.core.system.trace.T.call(this);
                                return this.userId;
                            }
                            setUserId(userId) {
                                ca.ntro.core.system.trace.T.call(this);
                                this.userId = userId;
                            }
                        }
                        messages.DeleteUserMessage = DeleteUserMessage;
                        DeleteUserMessage["__class"] = "ca.aquiletour.core.pages.users.messages.DeleteUserMessage";
                        DeleteUserMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class ShowUsersHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {*} parentView
                             * @param {*} currentView
                             * @param {ca.aquiletour.core.pages.users.messages.ShowUsersMessage} message
                             */
                            handle(parentView, currentView, message) {
                                ca.ntro.core.system.trace.T.call(this);
                                parentView.showUsers(currentView);
                            }
                        }
                        messages.ShowUsersHandler = ShowUsersHandler;
                        ShowUsersHandler["__class"] = "ca.aquiletour.core.pages.users.messages.ShowUsersHandler";
                        ShowUsersHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class ShowUsersMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                            }
                        }
                        messages.ShowUsersMessage = ShowUsersMessage;
                        ShowUsersMessage["__class"] = "ca.aquiletour.core.pages.users.messages.ShowUsersMessage";
                        ShowUsersMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var messages;
                    (function (messages) {
                        class AddUserMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                                if (this.user === undefined)
                                    this.user = null;
                            }
                            setUser(user) {
                                ca.ntro.core.system.trace.T.call(this);
                                this.user = user;
                            }
                            getUser() {
                                ca.ntro.core.system.trace.T.call(this);
                                return this.user;
                            }
                        }
                        messages.AddUserMessage = AddUserMessage;
                        AddUserMessage["__class"] = "ca.aquiletour.core.pages.users.messages.AddUserMessage";
                        AddUserMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = users.messages || (users.messages = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    var values;
                    (function (values) {
                        class ObservableUserMap extends ca.ntro.core.models.properties.observable.map.ObservableMap {
                            constructor(map) {
                                if (((map != null && (map instanceof Object)) || map === null)) {
                                    let __args = arguments;
                                    super(map);
                                }
                                else if (map === undefined) {
                                    let __args = arguments;
                                    super(({}));
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            /**
                             *
                             * @return {*}
                             */
                            valueType() {
                                ca.ntro.core.system.trace.T.call(this);
                                return ca.ntro.core.models.properties.NtroModelValue;
                            }
                            isUserValid(userId, authToken) {
                                return ((m, k) => m[k] === undefined ? null : m[k])(this.getValue(), userId).isValid(authToken);
                            }
                        }
                        values.ObservableUserMap = ObservableUserMap;
                        ObservableUserMap["__class"] = "ca.aquiletour.core.pages.users.values.ObservableUserMap";
                        ObservableUserMap["__interfaces"] = ["java.io.Serializable"];
                    })(values = users.values || (users.values = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    class UsersController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            this.setViewLoader("ca.aquiletour.core.pages.users.UsersView", "fr");
                            this.setModelLoader(ca.ntro.core.services.stores.NetworkStore.getLoader(ca.aquiletour.core.pages.users.UsersModel, "TODO", "allUsers"));
                            this.addParentViewMessageHandler(ca.aquiletour.core.pages.users.messages.ShowUsersMessage, new ca.aquiletour.core.pages.users.messages.ShowUsersHandler());
                            this.addModelMessageHandler(ca.aquiletour.core.pages.users.messages.AddUserMessage, new ca.aquiletour.core.pages.users.messages.AddUserHandler());
                            this.addModelMessageHandler(ca.aquiletour.core.pages.users.messages.DeleteUserMessage, new ca.aquiletour.core.pages.users.messages.DeleteUserHandler());
                            this.addSubViewLoader("ca.aquiletour.core.pages.users.UserView", "fr");
                            this.addModelViewSubViewHandler("ca.aquiletour.core.pages.users.UserView", new ca.aquiletour.core.pages.users.UsersViewModel());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                        }
                    }
                    users.UsersController = UsersController;
                    UsersController["__class"] = "ca.aquiletour.core.pages.users.UsersController";
                    UsersController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users_1) {
                    class UsersModel extends ca.ntro.core.models.NtroModel {
                        constructor() {
                            super();
                            /*private*/ this.users = new ca.aquiletour.core.pages.users.values.ObservableUserMap();
                        }
                        /**
                         *
                         */
                        initializeStoredValues() {
                        }
                        getUsers() {
                            ca.ntro.core.system.trace.T.call(this);
                            return this.users;
                        }
                        setUsers(users) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.users = users;
                        }
                        isUserValid(userId, authToken) {
                            ca.ntro.core.system.trace.T.call(this);
                            return this.users.isUserValid(userId, authToken);
                        }
                        addUser(user) {
                            ca.ntro.core.system.trace.T.call(this);
                            let userId = ('' + (this.users.size()));
                            user.setId(userId);
                            this.users.addEntry(userId, user);
                        }
                        deleteUser(userId) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.users.removeEntry(userId);
                        }
                    }
                    users_1.UsersModel = UsersModel;
                    UsersModel["__class"] = "ca.aquiletour.core.pages.users.UsersModel";
                    UsersModel["__interfaces"] = ["java.io.Serializable"];
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    class UsersViewModel extends ca.ntro.core.mvc.ModelViewSubViewHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.users.UsersModel} model
                         * @param {*} view
                         * @param {ca.ntro.core.mvc.ViewLoader} subViewLoader
                         */
                        handle(model, view, subViewLoader) {
                            ca.ntro.core.system.trace.T.call(this);
                            model.getUsers().observe(new UsersViewModel.UsersViewModel$0(this, subViewLoader, view));
                        }
                    }
                    users.UsersViewModel = UsersViewModel;
                    UsersViewModel["__class"] = "ca.aquiletour.core.pages.users.UsersViewModel";
                    UsersViewModel["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    (function (UsersViewModel) {
                        class UsersViewModel$0 {
                            constructor(__parent, subViewLoader, view) {
                                this.subViewLoader = subViewLoader;
                                this.view = view;
                                this.__parent = __parent;
                            }
                            /**
                             *
                             * @param {*} oldValue
                             * @param {*} value
                             */
                            onValueChanged(oldValue, value) {
                            }
                            /**
                             *
                             * @param {*} value
                             */
                            onValue(value) {
                            }
                            /**
                             *
                             * @param {*} lastValue
                             */
                            onDeleted(lastValue) {
                            }
                            /**
                             *
                             * @param {string} key
                             * @param {ca.aquiletour.core.models.users.User} value
                             */
                            onEntryAdded(key, value) {
                                ca.ntro.core.system.trace.T.call(this);
                                let userView = this.subViewLoader.createView();
                                userView.displayUser(value);
                                this.view.appendUser(value, userView);
                            }
                            /**
                             *
                             * @param {string} key
                             * @param {ca.aquiletour.core.models.users.User} value
                             */
                            onEntryRemoved(key, value) {
                                ca.ntro.core.system.trace.T.call(this);
                                this.view.deleteUser(key);
                            }
                        }
                        UsersViewModel.UsersViewModel$0 = UsersViewModel$0;
                        UsersViewModel$0["__interfaces"] = ["ca.ntro.core.models.properties.observable.map.MapObserver", "ca.ntro.core.models.properties.observable.simple.ValueObserver", "ca.ntro.core.models.properties.observable.simple.ValueListener", "ca.ntro.core.models.properties.observable.simple.DeletionListener"];
                    })(UsersViewModel = users.UsersViewModel || (users.UsersViewModel = {}));
                })(users = pages.users || (pages.users = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var student;
                    (function (student) {
                        var messages;
                        (function (messages) {
                            class ShowStudentDashboardMessage extends ca.ntro.messages.NtroMessage {
                                constructor() {
                                    super();
                                }
                            }
                            messages.ShowStudentDashboardMessage = ShowStudentDashboardMessage;
                            ShowStudentDashboardMessage["__class"] = "ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage";
                            ShowStudentDashboardMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                        })(messages = student.messages || (student.messages = {}));
                    })(student = dashboards.student || (dashboards.student = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var student;
                    (function (student) {
                        var messages;
                        (function (messages) {
                            class ShowStudentDashboardHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                                constructor() {
                                    super();
                                }
                                /**
                                 *
                                 * @param {*} parentView
                                 * @param {*} currentView
                                 * @param {ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage} message
                                 */
                                handle(parentView, currentView, message) {
                                    parentView.showDashboard(currentView);
                                }
                            }
                            messages.ShowStudentDashboardHandler = ShowStudentDashboardHandler;
                            ShowStudentDashboardHandler["__class"] = "ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardHandler";
                            ShowStudentDashboardHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                        })(messages = student.messages || (student.messages = {}));
                    })(student = dashboards.student || (dashboards.student = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var teacher;
                    (function (teacher) {
                        var messages;
                        (function (messages) {
                            class ShowTeacherDashboardHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                                constructor() {
                                    super();
                                }
                                /**
                                 *
                                 * @param {*} parentView
                                 * @param {*} currentView
                                 * @param {ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage} message
                                 */
                                handle(parentView, currentView, message) {
                                    parentView.showDashboard(currentView);
                                }
                            }
                            messages.ShowTeacherDashboardHandler = ShowTeacherDashboardHandler;
                            ShowTeacherDashboardHandler["__class"] = "ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardHandler";
                            ShowTeacherDashboardHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                        })(messages = teacher.messages || (teacher.messages = {}));
                    })(teacher = dashboards.teacher || (dashboards.teacher = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var teacher;
                    (function (teacher) {
                        var messages;
                        (function (messages) {
                            class ShowTeacherDashboardMessage extends ca.ntro.messages.NtroMessage {
                                constructor() {
                                    super();
                                }
                            }
                            messages.ShowTeacherDashboardMessage = ShowTeacherDashboardMessage;
                            ShowTeacherDashboardMessage["__class"] = "ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage";
                            ShowTeacherDashboardMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                        })(messages = teacher.messages || (teacher.messages = {}));
                    })(teacher = dashboards.teacher || (dashboards.teacher = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var teacher;
                    (function (teacher) {
                        var messages;
                        (function (messages) {
                            class AddCourseMessage extends ca.ntro.messages.NtroMessage {
                                constructor() {
                                    super();
                                    if (this.course === undefined)
                                        this.course = null;
                                    if (this.user === undefined)
                                        this.user = null;
                                }
                                setCourse(course) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.course = course;
                                }
                                getCourse() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return this.course;
                                }
                                getUser() {
                                    return this.user;
                                }
                                setUser(user) {
                                    this.user = user;
                                }
                            }
                            messages.AddCourseMessage = AddCourseMessage;
                            AddCourseMessage["__class"] = "ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage";
                            AddCourseMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                        })(messages = teacher.messages || (teacher.messages = {}));
                    })(teacher = dashboards.teacher || (dashboards.teacher = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var teacher;
                    (function (teacher) {
                        var messages;
                        (function (messages) {
                            class DeleteCourseMessage extends ca.ntro.messages.NtroMessage {
                                constructor() {
                                    super();
                                }
                            }
                            messages.DeleteCourseMessage = DeleteCourseMessage;
                            DeleteCourseMessage["__class"] = "ca.aquiletour.core.pages.dashboards.teacher.messages.DeleteCourseMessage";
                            DeleteCourseMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                        })(messages = teacher.messages || (teacher.messages = {}));
                    })(teacher = dashboards.teacher || (dashboards.teacher = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var values;
                    (function (values) {
                        class CourseSummary extends ca.ntro.core.models.properties.NtroModelValue {
                            constructor(title, courseId, isQueueOpen, myAppointment, numberOfAppointments) {
                                if (((typeof title === 'string') || title === null) && ((typeof courseId === 'string') || courseId === null) && ((typeof isQueueOpen === 'boolean') || isQueueOpen === null) && ((typeof myAppointment === 'boolean') || myAppointment === null) && ((typeof numberOfAppointments === 'number') || numberOfAppointments === null)) {
                                    let __args = arguments;
                                    super();
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                    (() => {
                                        ca.ntro.core.system.trace.T.call(this);
                                        this.title = title;
                                        this.courseId = courseId;
                                        this.isQueueOpen = isQueueOpen;
                                        this.myAppointment = myAppointment;
                                        this.numberOfAppointments = numberOfAppointments;
                                    })();
                                }
                                else if (((typeof title === 'string') || title === null) && courseId === undefined && isQueueOpen === undefined && myAppointment === undefined && numberOfAppointments === undefined) {
                                    let __args = arguments;
                                    super();
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                    (() => {
                                        this.title = title;
                                    })();
                                }
                                else if (title === undefined && courseId === undefined && isQueueOpen === undefined && myAppointment === undefined && numberOfAppointments === undefined) {
                                    let __args = arguments;
                                    super();
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                    if (this.title === undefined)
                                        this.title = null;
                                    if (this.courseId === undefined)
                                        this.courseId = null;
                                    if (this.isQueueOpen === undefined)
                                        this.isQueueOpen = null;
                                    if (this.myAppointment === undefined)
                                        this.myAppointment = null;
                                    if (this.numberOfAppointments === undefined)
                                        this.numberOfAppointments = 0;
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            getTitle() {
                                return this.title;
                            }
                            setTitle(title) {
                                this.title = title;
                            }
                            getCourseId() {
                                return this.courseId;
                            }
                            setCourseId(courseId) {
                                this.courseId = courseId;
                            }
                            getIsQueueOpen() {
                                return this.isQueueOpen;
                            }
                            setIsQueueOpen(isQueueOpen) {
                                this.isQueueOpen = isQueueOpen;
                            }
                            getMyAppointment() {
                                return this.myAppointment;
                            }
                            setMyAppointment(myAppointment) {
                                this.myAppointment = myAppointment;
                            }
                            getNumberOfAppointments() {
                                return this.numberOfAppointments;
                            }
                            setNumberOfAppointments(numberOfAppointments) {
                                this.numberOfAppointments = numberOfAppointments;
                            }
                        }
                        values.CourseSummary = CourseSummary;
                        CourseSummary["__class"] = "ca.aquiletour.core.pages.dashboards.values.CourseSummary";
                        CourseSummary["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                    })(values = dashboards.values || (dashboards.values = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var values;
                    (function (values) {
                        class ObservableCourseList extends ca.ntro.core.models.properties.observable.list.ObservableList {
                            constructor(value) {
                                if (((value != null && (value instanceof Array)) || value === null)) {
                                    let __args = arguments;
                                    super(value);
                                    (() => {
                                        ca.ntro.core.system.trace.T.call(this);
                                    })();
                                }
                                else if (value === undefined) {
                                    let __args = arguments;
                                    super(([]));
                                    (() => {
                                        ca.ntro.core.system.trace.T.call(this);
                                    })();
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            /**
                             *
                             * @return {*}
                             */
                            valueType() {
                                ca.ntro.core.system.trace.T.call(this);
                                return ca.ntro.core.models.properties.NtroModelValue;
                            }
                        }
                        ObservableCourseList.serialVersionUID = -6055373964369299983;
                        values.ObservableCourseList = ObservableCourseList;
                        ObservableCourseList["__class"] = "ca.aquiletour.core.pages.dashboards.values.ObservableCourseList";
                        ObservableCourseList["__interfaces"] = ["java.io.Serializable"];
                    })(values = dashboards.values || (dashboards.values = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    class DashboardController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setViewLoader(this.viewClass(), this.currentContext().getLang());
                            this.setModelLoader(ca.ntro.core.services.stores.NetworkStore.getLoader(ca.aquiletour.core.pages.dashboards.DashboardModel, this.currentContext().getUser().getAuthToken(), this.currentContext().getUser().getId()));
                            this.installParentViewMessageHandler();
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    dashboards.DashboardController = DashboardController;
                    DashboardController["__class"] = "ca.aquiletour.core.pages.dashboards.DashboardController";
                    DashboardController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    class DashboardModel extends ca.ntro.core.models.NtroModel {
                        constructor() {
                            super();
                            /*private*/ this.courses = new ca.aquiletour.core.pages.dashboards.values.ObservableCourseList(([]));
                        }
                        /**
                         *
                         */
                        initializeStoredValues() {
                        }
                        addCourse(course) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.courses.addItem(course);
                        }
                        getCourses() {
                            return this.courses;
                        }
                        setCourses(courses) {
                            this.courses = courses;
                        }
                        updateNbAppointmentOfCourse(courseId, nbAppointment) {
                            for (let i = 0; i < this.courses.size(); i++) {
                                {
                                    let currentCourse = this.courses.getItem(i);
                                    if (((o1, o2) => { if (o1 && o1.equals) {
                                        return o1.equals(o2);
                                    }
                                    else {
                                        return o1 === o2;
                                    } })(currentCourse.getTitle(), courseId)) {
                                        ca.ntro.core.system.trace.T.here();
                                        currentCourse.setNumberOfAppointments(nbAppointment);
                                    }
                                }
                                ;
                            }
                        }
                        updateMyAppointment(courseId, state) {
                            for (let i = 0; i < this.courses.size(); i++) {
                                {
                                    let currentCourse = this.courses.getItem(i);
                                    if (((o1, o2) => { if (o1 && o1.equals) {
                                        return o1.equals(o2);
                                    }
                                    else {
                                        return o1 === o2;
                                    } })(currentCourse.getTitle(), courseId)) {
                                        ca.ntro.core.system.trace.T.here();
                                        currentCourse.setMyAppointment(state);
                                    }
                                }
                                ;
                            }
                        }
                    }
                    DashboardModel.serialVersionUID = -7945536015118582973;
                    dashboards.DashboardModel = DashboardModel;
                    DashboardModel["__class"] = "ca.aquiletour.core.pages.dashboards.DashboardModel";
                    DashboardModel["__interfaces"] = ["java.io.Serializable"];
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    class DashboardViewModel extends ca.ntro.core.mvc.ModelViewSubViewHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.dashboards.DashboardModel} model
                         * @param {*} view
                         * @param {ca.ntro.core.mvc.ViewLoader} subViewLoader
                         */
                        handle(model, view, subViewLoader) {
                            ca.ntro.core.system.trace.T.call(this);
                            model.getCourses().observe(new DashboardViewModel.DashboardViewModel$0(this, subViewLoader, view));
                        }
                    }
                    dashboards.DashboardViewModel = DashboardViewModel;
                    DashboardViewModel["__class"] = "ca.aquiletour.core.pages.dashboards.DashboardViewModel";
                    DashboardViewModel["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    (function (DashboardViewModel) {
                        class DashboardViewModel$0 {
                            constructor(__parent, subViewLoader, view) {
                                this.subViewLoader = subViewLoader;
                                this.view = view;
                                this.__parent = __parent;
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary[]} oldValue
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary[]} value
                             */
                            onValueChanged(oldValue, value) {
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary[]} value
                             */
                            onValue(value) {
                            }
                            /**
                             *
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary[]} lastValue
                             */
                            onDeleted(lastValue) {
                            }
                            /**
                             *
                             * @param {number} index
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} item
                             */
                            onItemAdded(index, item) {
                                ca.ntro.core.system.trace.T.call(this);
                                let courseView = this.subViewLoader.createView();
                                courseView.displaySummary(item);
                                this.view.appendCourse(courseView);
                            }
                            /**
                             *
                             * @param {number} index
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} item
                             */
                            onItemUpdated(index, item) {
                            }
                            /**
                             *
                             * @param {number} index
                             * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} item
                             */
                            onItemRemoved(index, item) {
                            }
                        }
                        DashboardViewModel.DashboardViewModel$0 = DashboardViewModel$0;
                        DashboardViewModel$0["__interfaces"] = ["ca.ntro.core.models.properties.observable.simple.ValueObserver", "ca.ntro.core.models.properties.observable.list.ItemAddedListener", "ca.ntro.core.models.properties.observable.list.ListObserver", "ca.ntro.core.models.properties.observable.list.ItemUpdatedListener", "ca.ntro.core.models.properties.observable.simple.ValueListener", "ca.ntro.core.models.properties.observable.simple.DeletionListener", "ca.ntro.core.models.properties.observable.list.ItemRemovedListener"];
                    })(DashboardViewModel = dashboards.DashboardViewModel || (dashboards.DashboardViewModel = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    class QueuesModel extends ca.ntro.core.models.NtroModel {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initializeStoredValues() {
                        }
                    }
                    queues.QueuesModel = QueuesModel;
                    QueuesModel["__class"] = "ca.aquiletour.core.pages.queues.QueuesModel";
                    QueuesModel["__interfaces"] = ["java.io.Serializable"];
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    var messages;
                    (function (messages) {
                        class ShowQueuesHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @param {*} parentView
                             * @param {*} currentView
                             * @param {ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage} message
                             */
                            handle(parentView, currentView, message) {
                                parentView.showQueues(currentView);
                            }
                        }
                        messages.ShowQueuesHandler = ShowQueuesHandler;
                        ShowQueuesHandler["__class"] = "ca.aquiletour.core.pages.queues.messages.ShowQueuesHandler";
                        ShowQueuesHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(messages = queues.messages || (queues.messages = {}));
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    var messages;
                    (function (messages) {
                        class ShowQueuesMessage extends ca.ntro.messages.NtroMessage {
                            constructor() {
                                super();
                            }
                        }
                        messages.ShowQueuesMessage = ShowQueuesMessage;
                        ShowQueuesMessage["__class"] = "ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage";
                        ShowQueuesMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                    })(messages = queues.messages || (queues.messages = {}));
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    var values;
                    (function (values) {
                        class QueueSummary extends ca.ntro.core.models.properties.NtroModelValue {
                            constructor() {
                                super();
                                if (this.teacherName === undefined)
                                    this.teacherName = null;
                                if (this.teacherSurname === undefined)
                                    this.teacherSurname = null;
                                if (this.numberOfAppointments === undefined)
                                    this.numberOfAppointments = 0;
                            }
                            getTeacherName() {
                                return this.teacherName;
                            }
                            setTeacherName(teacherName) {
                                this.teacherName = teacherName;
                            }
                            getTeacherSurname() {
                                return this.teacherSurname;
                            }
                            setTeacherSurname(teacherSurname) {
                                this.teacherSurname = teacherSurname;
                            }
                            getNumberOfAppointments() {
                                return this.numberOfAppointments;
                            }
                            setNumberOfAppointments(numberOfAppointments) {
                                this.numberOfAppointments = numberOfAppointments;
                            }
                        }
                        values.QueueSummary = QueueSummary;
                        QueueSummary["__class"] = "ca.aquiletour.core.pages.queues.values.QueueSummary";
                        QueueSummary["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                    })(values = queues.values || (queues.values = {}));
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    var values;
                    (function (values) {
                        class ObservableQueueList extends ca.ntro.core.models.properties.observable.list.ObservableList {
                            constructor(value) {
                                if (((value != null && (value instanceof Array)) || value === null)) {
                                    let __args = arguments;
                                    super(value);
                                }
                                else if (value === undefined) {
                                    let __args = arguments;
                                    super(([]));
                                }
                                else
                                    throw new Error('invalid overload');
                            }
                            /**
                             *
                             * @return {*}
                             */
                            valueType() {
                                ca.ntro.core.system.trace.T.call(this);
                                return ca.ntro.core.models.properties.NtroModelValue;
                            }
                        }
                        values.ObservableQueueList = ObservableQueueList;
                        ObservableQueueList["__class"] = "ca.aquiletour.core.pages.queues.values.ObservableQueueList";
                        ObservableQueueList["__interfaces"] = ["java.io.Serializable"];
                    })(values = queues.values || (queues.values = {}));
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    class QueuesController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setViewLoader("ca.aquiletour.core.pages.queues.QueuesView", "fr");
                            this.setModelLoader(ca.ntro.core.services.stores.NetworkStore.getLoader(ca.aquiletour.core.pages.queues.QueuesModel, "TODO", "allQueues"));
                            this.addParentViewMessageHandler(ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage, new ca.aquiletour.core.pages.queues.messages.ShowQueuesHandler());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                        }
                    }
                    queues.QueuesController = QueuesController;
                    QueuesController["__class"] = "ca.aquiletour.core.pages.queues.QueuesController";
                    QueuesController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var home;
                (function (home) {
                    class HomeController extends ca.ntro.core.mvc.NtroController {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        onCreate() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.setViewLoader("ca.aquiletour.core.pages.home.HomeView", "fr");
                            this.addParentViewMessageHandler(ca.aquiletour.core.pages.home.ShowHomeMessage, new ca.aquiletour.core.pages.home.ShowHomeHandler());
                        }
                        /**
                         *
                         * @param {ca.ntro.core.mvc.NtroContext} previousContext
                         */
                        onChangeContext(previousContext) {
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    home.HomeController = HomeController;
                    HomeController["__class"] = "ca.aquiletour.core.pages.home.HomeController";
                    HomeController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(home = pages.home || (pages.home = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var home;
                (function (home) {
                    class ShowHomeHandler extends ca.ntro.core.mvc.ParentViewMessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {*} parentView
                         * @param {*} currentView
                         * @param {ca.aquiletour.core.pages.home.ShowHomeMessage} message
                         */
                        handle(parentView, currentView, message) {
                            ca.ntro.core.system.trace.T.call(this);
                            parentView.showHome(currentView);
                        }
                    }
                    home.ShowHomeHandler = ShowHomeHandler;
                    ShowHomeHandler["__class"] = "ca.aquiletour.core.pages.home.ShowHomeHandler";
                    ShowHomeHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(home = pages.home || (pages.home = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var home;
                (function (home) {
                    class ShowHomeMessage extends ca.ntro.messages.NtroMessage {
                        constructor() {
                            super();
                        }
                    }
                    home.ShowHomeMessage = ShowHomeMessage;
                    ShowHomeMessage["__class"] = "ca.aquiletour.core.pages.home.ShowHomeMessage";
                    ShowHomeMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                })(home = pages.home || (pages.home = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                var handlers;
                (function (handlers) {
                    class AddCourseHandler extends ca.ntro.core.mvc.MessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage} message
                         */
                        handle(message) {
                            ca.ntro.core.system.trace.T.call(this);
                            let fromUser = message.getUser();
                            let courseId = message.getCourse().getTitle();
                            let dashboardModel = (this.getController().getModel(ca.aquiletour.core.pages.dashboards.DashboardModel, fromUser.getAuthToken(), fromUser.getId()));
                            if (dashboardModel != null) {
                                dashboardModel.addCourse(message.getCourse());
                                dashboardModel.save();
                                let queueModel = (this.getController().getModel(ca.aquiletour.core.pages.queue.QueueModel, fromUser.getAuthToken(), courseId));
                                queueModel.save();
                            }
                            else {
                            }
                        }
                    }
                    handlers.AddCourseHandler = AddCourseHandler;
                    AddCourseHandler["__class"] = "ca.aquiletour.core.backend.handlers.AddCourseHandler";
                    AddCourseHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(handlers = backend.handlers || (backend.handlers = {}));
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                var handlers;
                (function (handlers) {
                    class AddAppointmentHandler extends ca.ntro.core.mvc.MessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage} message
                         */
                        handle(message) {
                            ca.ntro.core.system.trace.T.call(this);
                            let requestingUser = message.getUser();
                            let courseId = message.getCourseId();
                            let queueModel = (this.getController().getModel(ca.aquiletour.core.pages.queue.QueueModel, requestingUser.getAuthToken(), courseId));
                            if (queueModel != null) {
                                queueModel.addAppointment(message.getAppointment());
                                queueModel.save();
                                let studentIds = queueModel.getStudentIds();
                                for (let index191 = 0; index191 < studentIds.length; index191++) {
                                    let studentId = studentIds[index191];
                                    {
                                        let nbAppointment = queueModel.getAppointments().size();
                                        let dashboardModel = (this.getController().getModel(ca.aquiletour.core.pages.dashboards.DashboardModel, "admin", studentId));
                                        if (dashboardModel != null) {
                                            dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
                                            if (((o1, o2) => { if (o1 && o1.equals) {
                                                return o1.equals(o2);
                                            }
                                            else {
                                                return o1 === o2;
                                            } })(requestingUser.getId(), studentId)) {
                                                dashboardModel.updateMyAppointment(courseId, true);
                                            }
                                            dashboardModel.save();
                                        }
                                    }
                                }
                            }
                            else {
                            }
                        }
                    }
                    handlers.AddAppointmentHandler = AddAppointmentHandler;
                    AddAppointmentHandler["__class"] = "ca.aquiletour.core.backend.handlers.AddAppointmentHandler";
                    AddAppointmentHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(handlers = backend.handlers || (backend.handlers = {}));
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                var handlers;
                (function (handlers) {
                    class DeleteAppointmentHandler extends ca.ntro.core.mvc.MessageHandler {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage} message
                         */
                        handle(message) {
                            ca.ntro.core.system.trace.T.call(this);
                            let requestingUser = message.getUser();
                            let courseId = message.getCourseId();
                            let queueModel = (this.getController().getModel(ca.aquiletour.core.pages.queue.QueueModel, requestingUser.getAuthToken(), courseId));
                            if (queueModel != null) {
                                queueModel.deleteAppointment(message.getAppointmentId());
                                queueModel.save();
                                let studentIds = queueModel.getStudentIds();
                                for (let index192 = 0; index192 < studentIds.length; index192++) {
                                    let studentId = studentIds[index192];
                                    {
                                        let nbAppointment = queueModel.getAppointments().size();
                                        let dashboardModel = (this.getController().getModel(ca.aquiletour.core.pages.dashboards.DashboardModel, "admin", studentId));
                                        if (dashboardModel != null) {
                                            dashboardModel.updateNbAppointmentOfCourse(courseId, nbAppointment);
                                            if (((o1, o2) => { if (o1 && o1.equals) {
                                                return o1.equals(o2);
                                            }
                                            else {
                                                return o1 === o2;
                                            } })(requestingUser.getId(), studentId)) {
                                                dashboardModel.updateMyAppointment(courseId, false);
                                            }
                                            dashboardModel.save();
                                        }
                                    }
                                }
                            }
                            else {
                            }
                        }
                    }
                    handlers.DeleteAppointmentHandler = DeleteAppointmentHandler;
                    DeleteAppointmentHandler["__class"] = "ca.aquiletour.core.backend.handlers.DeleteAppointmentHandler";
                    DeleteAppointmentHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                })(handlers = backend.handlers || (backend.handlers = {}));
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                class RootBackendController extends ca.ntro.core.mvc.BackendRootController {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    onCreate() {
                        this.addSubController(ca.aquiletour.core.backend.DashboardBackendController, "mescours");
                        this.addSubController(ca.aquiletour.core.backend.QueueBackendController, "billeterie");
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                backend.RootBackendController = RootBackendController;
                RootBackendController["__class"] = "ca.aquiletour.core.backend.RootBackendController";
                RootBackendController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                class DashboardBackendController extends ca.ntro.core.mvc.BackendController {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    onCreate() {
                        this.addMessageHandler(ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage, new ca.aquiletour.core.backend.handlers.AddCourseHandler());
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                backend.DashboardBackendController = DashboardBackendController;
                DashboardBackendController["__class"] = "ca.aquiletour.core.backend.DashboardBackendController";
                DashboardBackendController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var backend;
            (function (backend) {
                class QueueBackendController extends ca.ntro.core.mvc.BackendController {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    onCreate() {
                        this.addMessageHandler(ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage, new ca.aquiletour.core.backend.handlers.AddAppointmentHandler());
                        this.addMessageHandler(ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage, new ca.aquiletour.core.backend.handlers.DeleteAppointmentHandler());
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                backend.QueueBackendController = QueueBackendController;
                QueueBackendController["__class"] = "ca.aquiletour.core.backend.QueueBackendController";
                QueueBackendController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(backend = core.backend || (core.backend = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var users;
                (function (users) {
                    class User extends ca.ntro.core.NtroUser {
                        constructor(email, password) {
                            if (((typeof email === 'string') || email === null) && ((typeof password === 'string') || password === null)) {
                                let __args = arguments;
                                super();
                                if (this.name === undefined)
                                    this.name = null;
                                if (this.surname === undefined)
                                    this.surname = null;
                                if (this.userEmail === undefined)
                                    this.userEmail = null;
                                if (this.userPassword === undefined)
                                    this.userPassword = null;
                                if (this.name === undefined)
                                    this.name = null;
                                if (this.surname === undefined)
                                    this.surname = null;
                                if (this.userEmail === undefined)
                                    this.userEmail = null;
                                if (this.userPassword === undefined)
                                    this.userPassword = null;
                                (() => {
                                    this.userEmail = email;
                                    this.userPassword = password;
                                })();
                            }
                            else if (email === undefined && password === undefined) {
                                let __args = arguments;
                                super();
                                if (this.name === undefined)
                                    this.name = null;
                                if (this.surname === undefined)
                                    this.surname = null;
                                if (this.userEmail === undefined)
                                    this.userEmail = null;
                                if (this.userPassword === undefined)
                                    this.userPassword = null;
                                if (this.name === undefined)
                                    this.name = null;
                                if (this.surname === undefined)
                                    this.surname = null;
                                if (this.userEmail === undefined)
                                    this.userEmail = null;
                                if (this.userPassword === undefined)
                                    this.userPassword = null;
                            }
                            else
                                throw new Error('invalid overload');
                        }
                        getName() {
                            return this.name;
                        }
                        setName(name) {
                            this.name = name;
                        }
                        getSurname() {
                            return this.surname;
                        }
                        setSurname(surname) {
                            this.surname = surname;
                        }
                        getUserEmail() {
                            return this.userEmail;
                        }
                        setUserEmail(userEmail) {
                            this.userEmail = userEmail;
                        }
                        getUserPassword() {
                            return this.userPassword;
                        }
                        setUserPassword(userPassword) {
                            this.userPassword = userPassword;
                        }
                    }
                    users.User = User;
                    User["__class"] = "ca.aquiletour.core.models.users.User";
                    User["__interfaces"] = ["java.io.Serializable"];
                })(users = models.users || (models.users = {}));
            })(models = core.models || (core.models = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            class AquiletourMain extends ca.ntro.core.tasks.NtroTaskSync {
                constructor() {
                    super();
                }
                /**
                 *
                 */
                runTask() {
                    ca.ntro.core.system.trace.T.call(this);
                    ca.aquiletour.core.Constants.LANG = this.getPreviousTask(ca.ntro.core.initialization.NtroInitializationTask, "initializationTask").getOption("lang");
                    ca.aquiletour.core.Constants.LANG = "fr";
                    let context = (new ca.ntro.core.mvc.NtroContext());
                    context.setUser(new ca.aquiletour.core.models.users.AnonUser());
                    context.setLang(ca.aquiletour.core.Constants.LANG);
                    this.registerViewLoaders();
                    let rootController = (ca.ntro.core.mvc.ControllerFactory.createRootController(ca.aquiletour.core.pages.root.RootController, "*", this.getWindow(), context));
                    rootController.execute();
                }
                /**
                 *
                 * @param {Error} e
                 */
                onFailure(e) {
                    console.error("[FATAL] Initialization error");
                    console.error(e.message, e);
                }
            }
            core.AquiletourMain = AquiletourMain;
            AquiletourMain["__class"] = "ca.aquiletour.core.AquiletourMain";
            AquiletourMain["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class DashboardViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {*} courseView
                         */
                        appendCourse(courseView) {
                            ca.ntro.core.system.trace.T.call(this);
                            let container = this.getRootElement().children("#courses-container").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(container);
                            let courseViewWeb = courseView;
                            container.appendElement(courseViewWeb.getRootElement());
                        }
                    }
                    dashboard.DashboardViewWeb = DashboardViewWeb;
                    DashboardViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.DashboardViewWeb";
                    DashboardViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.DashboardView"];
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class CourseSummaryViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} course
                         */
                        displaySummary(course) {
                            ca.ntro.core.system.trace.T.call(this);
                            let title = this.getRootElement().children("#course-title").get(0);
                            let courseId = this.getRootElement().children("#courseId").get(0);
                            let nbAppointment = this.getRootElement().children("#nbAppointment").get(0);
                            let makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
                            let myAppointment = this.getRootElement().children("#myAppointment").get(0);
                            let isQueueOpen = this.getRootElement().children("#isQueueOpen").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(title);
                            ca.ntro.core.system.assertions.MustNot.beNull(courseId);
                            ca.ntro.core.system.assertions.MustNot.beNull(nbAppointment);
                            ca.ntro.core.system.assertions.MustNot.beNull(makeAppointmentLink);
                            ca.ntro.core.system.assertions.MustNot.beNull(myAppointment);
                            ca.ntro.core.system.assertions.MustNot.beNull(isQueueOpen);
                        }
                    }
                    dashboard.CourseSummaryViewWeb = CourseSummaryViewWeb;
                    CourseSummaryViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb";
                    CourseSummaryViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.CourseSummaryView"];
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var root;
                (function (root) {
                    class RootViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                            ca.ntro.core.system.trace.T.call(this);
                            let dashboardLink = this.getRootElement().find("#dashboard-link").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(dashboardLink);
                            dashboardLink.addEventListener("click", new RootViewWeb.RootViewWeb$0(this));
                        }
                        /**
                         *
                         * @param {*} dashboardView
                         */
                        showDashboard(dashboardView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(dashboardView);
                        }
                        /*private*/ showSubView(view) {
                            ca.ntro.core.system.trace.T.call(this);
                            let viewWeb = view;
                            let container = this.getRootElement().children("#page-container").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(container);
                            let subViewElement = viewWeb.getRootElement();
                            container.clearChildren();
                            container.appendElement(subViewElement);
                        }
                        /**
                         *
                         * @param {*} queueView
                         */
                        showQueue(queueView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(queueView);
                        }
                        /**
                         *
                         * @param {*} usersView
                         */
                        showUsers(usersView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(usersView);
                        }
                        /**
                         *
                         * @param {*} loginView
                         */
                        showLogin(loginView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(loginView);
                        }
                        /**
                         *
                         * @param {*} currentView
                         */
                        showQueues(currentView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(currentView);
                        }
                        /**
                         *
                         * @param {*} homeView
                         */
                        showHome(homeView) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.showSubView(homeView);
                        }
                    }
                    root.RootViewWeb = RootViewWeb;
                    RootViewWeb["__class"] = "ca.aquiletour.web.pages.root.RootViewWeb";
                    RootViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.root.RootView", "ca.ntro.core.mvc.NtroView"];
                    (function (RootViewWeb) {
                        class RootViewWeb$0 {
                            constructor(__parent) {
                                this.__parent = __parent;
                            }
                            /**
                             *
                             */
                            onEvent() {
                                ca.ntro.core.system.trace.T.call(this);
                                let showDashboardMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage));
                                showDashboardMessage.sendMessage();
                            }
                        }
                        RootViewWeb.RootViewWeb$0 = RootViewWeb$0;
                        RootViewWeb$0["__interfaces"] = ["ca.ntro.web.dom.HtmlEventListener"];
                    })(RootViewWeb = root.RootViewWeb || (root.RootViewWeb = {}));
                })(root = pages.root || (pages.root = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    class QueueViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.queue.values.Appointment} appointment
                         * @param {*} appointmentView
                         */
                        appendAppointement(appointment, appointmentView) {
                            ca.ntro.core.system.trace.T.call(this);
                            let container = this.getRootElement().children("#appointment-list").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(container);
                            let appointmentViewWeb = appointmentView;
                            container.appendElement(appointmentViewWeb.getRootElement());
                        }
                        /**
                         *
                         * @param {string} appointmentId
                         */
                        deleteAppointment(appointmentId) {
                            ca.ntro.core.system.trace.T.call(this);
                            let container = this.getRootElement().children("#appointment-list").get(0);
                            let selector = "#appointment-" + appointmentId;
                            let appointmentElement = container.children(selector).get(0);
                            appointmentElement.remove();
                        }
                    }
                    queue.QueueViewWeb = QueueViewWeb;
                    QueueViewWeb["__class"] = "ca.aquiletour.web.pages.queue.QueueViewWeb";
                    QueueViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.queue.QueueView", "ca.ntro.core.mvc.NtroView"];
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var queue;
                (function (queue) {
                    class AppointmentViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.queue.values.Appointment} appointment
                         */
                        displayAppointement(appointment) {
                            ca.ntro.core.system.trace.T.call(this);
                            let studentId = this.getRootElement().children("#studentId").get(0);
                            let studentSurname = this.getRootElement().children("#studentSurname").get(0);
                            let studentName = this.getRootElement().children("#studentName").get(0);
                            let ids = this.getRootElement().find(".appointmentId");
                            for (let i = 0; i < ids.size(); i++) {
                                {
                                    let id = ids.get(i);
                                    id.appendHtml(appointment.getAppointmentId());
                                    id.setAttribute("value", appointment.getAppointmentId());
                                }
                                ;
                            }
                            ca.ntro.core.system.assertions.MustNot.beNull(studentId);
                            ca.ntro.core.system.assertions.MustNot.beNull(studentSurname);
                            ca.ntro.core.system.assertions.MustNot.beNull(studentName);
                            studentId.appendHtml(appointment.getStudentId());
                            studentSurname.appendHtml(appointment.getStudentSurname());
                            studentName.appendHtml(appointment.getStudentName());
                            this.getRootElement().setAttribute("id", "appointment-" + appointment.getAppointmentId());
                        }
                    }
                    queue.AppointmentViewWeb = AppointmentViewWeb;
                    AppointmentViewWeb["__class"] = "ca.aquiletour.web.pages.queue.AppointmentViewWeb";
                    AppointmentViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.queue.AppointmentView", "ca.ntro.core.mvc.NtroView"];
                })(queue = pages.queue || (pages.queue = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var login;
                (function (login) {
                    class LoginViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                    }
                    login.LoginViewWeb = LoginViewWeb;
                    LoginViewWeb["__class"] = "ca.aquiletour.web.pages.login.LoginViewWeb";
                    LoginViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.login.LoginView"];
                })(login = pages.login || (pages.login = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    class UserViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.models.users.User} user
                         */
                        displayUser(user) {
                            ca.ntro.core.system.trace.T.call(this);
                            let userId = this.getRootElement().children("#userId").get(0);
                            let close = this.getRootElement().children("#close").get(0);
                            let userEmail = this.getRootElement().children("#userEmail").get(0);
                            let userPassword = this.getRootElement().children("#userPassword").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(userId);
                            ca.ntro.core.system.assertions.MustNot.beNull(close);
                            ca.ntro.core.system.assertions.MustNot.beNull(userEmail);
                            ca.ntro.core.system.assertions.MustNot.beNull(userPassword);
                            userId.appendHtml(user.getId());
                            userEmail.appendHtml(user.getUserEmail());
                            userPassword.appendHtml(user.getUserPassword());
                            close.setAttribute("href", "/usagers?deleteUser=" + user.getId());
                            this.getRootElement().setAttribute("id", "user-" + user.getId());
                        }
                    }
                    users.UserViewWeb = UserViewWeb;
                    UserViewWeb["__class"] = "ca.aquiletour.web.pages.users.UserViewWeb";
                    UserViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.users.UserView"];
                })(users = pages.users || (pages.users = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var users;
                (function (users) {
                    class UsersViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.models.users.User} user
                         * @param {*} userView
                         */
                        appendUser(user, userView) {
                            ca.ntro.core.system.trace.T.call(this);
                            ca.ntro.core.system.trace.T.here();
                            let container = this.getRootElement().children("#showUsers-container").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(container);
                            let userViewWeb = userView;
                            container.appendElement(userViewWeb.getRootElement());
                        }
                        /**
                         *
                         * @param {string} userId
                         */
                        deleteUser(userId) {
                            ca.ntro.core.system.trace.T.call(this);
                            let container = this.getRootElement().children("#showUsers-container").get(0);
                            let selector = "#user-" + userId;
                            let userElement = container.children(selector).get(0);
                            userElement.remove();
                        }
                    }
                    users.UsersViewWeb = UsersViewWeb;
                    UsersViewWeb["__class"] = "ca.aquiletour.web.pages.users.UsersViewWeb";
                    UsersViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.users.UsersView", "ca.ntro.core.mvc.NtroView"];
                })(users = pages.users || (pages.users = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    class QueueSummaryViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                    }
                    queues.QueueSummaryViewWeb = QueueSummaryViewWeb;
                    QueueSummaryViewWeb["__class"] = "ca.aquiletour.web.pages.queues.QueueSummaryViewWeb";
                    QueueSummaryViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.queues.QueueSummaryView"];
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var queues;
                (function (queues) {
                    class QueuesViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                    }
                    queues.QueuesViewWeb = QueuesViewWeb;
                    QueuesViewWeb["__class"] = "ca.aquiletour.web.pages.queues.QueuesViewWeb";
                    QueuesViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.queues.QueuesView", "ca.ntro.core.mvc.NtroView"];
                })(queues = pages.queues || (pages.queues = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var home;
                (function (home) {
                    class HomeViewWeb extends ca.ntro.web.mvc.NtroViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                    }
                    home.HomeViewWeb = HomeViewWeb;
                    HomeViewWeb["__class"] = "ca.aquiletour.web.pages.home.HomeViewWeb";
                    HomeViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.home.HomeView"];
                })(home = pages.home || (pages.home = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            class AquiletourBackendRequestHandler {
                static sendMessages(context, path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                    if (path.startsWith("mescours")) {
                        AquiletourBackendRequestHandler.sendDashboardMessages(path.subPath(1), parameters, context.getUser());
                    }
                    else if (path.startsWith("billetteries")) {
                        AquiletourBackendRequestHandler.sendQueuesMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("billetterie")) {
                        AquiletourBackendRequestHandler.sendQueueMessages(path.subPath(1), parameters, context.getUser());
                    }
                    else if (path.startsWith("usagers")) {
                        AquiletourBackendRequestHandler.sendUsersMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("connexion")) {
                        AquiletourBackendRequestHandler.sendLoginMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("home")) {
                        AquiletourBackendRequestHandler.sendHomeMessages(path.subPath(1), parameters);
                    }
                }
                /*private*/ static sendHomeMessages(subPath, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                }
                /*private*/ static sendLoginMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                }
                /*private*/ static sendDashboardMessages(path, parameters, user) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                    if (parameters.hasOwnProperty("title")) {
                        let courseTitle = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "title")[0];
                        let courseId = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "title")[0];
                        let addCourseMessage = new ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage();
                        addCourseMessage.setCourse(new ca.aquiletour.core.pages.dashboards.values.CourseSummary(courseTitle, courseId, true, null, 100));
                        addCourseMessage.setUser(user);
                        ca.ntro.core.Ntro.backendService().sendMessageToBackend(addCourseMessage);
                    }
                }
                /*private*/ static sendQueuesMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                }
                /*private*/ static sendQueueMessages(path, parameters, user) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                    if (path.size() >= 1) {
                        AquiletourBackendRequestHandler.sendAppointmentMessages(parameters, user, path.getName(0));
                    }
                }
                /*private*/ static sendAppointmentMessages(parameters, user, courseId) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                    if (parameters.hasOwnProperty("makeAppointment")) {
                        let addAppointmentMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.queue.messages.AddAppointmentMessage));
                        let newAppointment = new ca.aquiletour.core.pages.queue.values.Appointment();
                        newAppointment.setStudentId(user.getId());
                        newAppointment.setStudentName(user.getName());
                        newAppointment.setStudentSurname(user.getSurname());
                        addAppointmentMessage.setAppointment(newAppointment);
                        addAppointmentMessage.setUser(user);
                        addAppointmentMessage.setCourseId(courseId);
                        addAppointmentMessage.sendMessage();
                    }
                    else if (parameters.hasOwnProperty("deleteAppointment")) {
                        let deleteAppointmentMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.queue.messages.DeleteAppointmentMessage));
                        let appointmentId = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "deleteAppointment")[0];
                        deleteAppointmentMessage.setAppointmentId(appointmentId);
                        deleteAppointmentMessage.setUser(user);
                        deleteAppointmentMessage.setCourseId(courseId);
                        deleteAppointmentMessage.sendMessage();
                    }
                }
                /*private*/ static sendUsersMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourBackendRequestHandler);
                    if (parameters.hasOwnProperty("email") && parameters.hasOwnProperty("password")) {
                        let email = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "email")[0];
                        let password = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "password")[0];
                        let addUserMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.users.messages.AddUserMessage));
                        let newUser = new ca.aquiletour.core.models.users.User();
                        newUser.setUserEmail(email);
                        newUser.setUserPassword(password);
                        addUserMessage.setUser(newUser);
                        addUserMessage.sendMessage();
                    }
                    else if (parameters.hasOwnProperty("deleteUser")) {
                        let deleteUserMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.users.messages.DeleteUserMessage));
                        let userId = ((m, k) => m[k] === undefined ? null : m[k])(parameters, "deleteUser")[0];
                        deleteUserMessage.setUserId(userId);
                        deleteUserMessage.sendMessage();
                    }
                }
            }
            web.AquiletourBackendRequestHandler = AquiletourBackendRequestHandler;
            AquiletourBackendRequestHandler["__class"] = "ca.aquiletour.web.AquiletourBackendRequestHandler";
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            class AquiletourRequestHandler {
                static sendMessages(context, path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    if (path.startsWith("mescours")) {
                        AquiletourRequestHandler.sendDashboardMessages(path.subPath(1), parameters, context.getUser());
                    }
                    else if (path.startsWith("billetteries")) {
                        AquiletourRequestHandler.sendQueuesMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("billetterie")) {
                        AquiletourRequestHandler.sendQueueMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("usagers")) {
                        AquiletourRequestHandler.sendUsersMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("connexion")) {
                        AquiletourRequestHandler.sendLoginMessages(path.subPath(1), parameters);
                    }
                    else if (path.startsWith("accueil")) {
                        AquiletourRequestHandler.sendHomeMessages(path.subPath(1), parameters);
                    }
                }
                /*private*/ static sendHomeMessages(subPath, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    let showHomeMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.home.ShowHomeMessage));
                    showHomeMessage.sendMessage();
                }
                /*private*/ static sendLoginMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    let showLoginMessage = new ca.aquiletour.core.pages.login.ShowLoginMessage();
                    ca.ntro.core.Ntro.messageService().sendMessage(showLoginMessage);
                }
                /*private*/ static sendDashboardMessages(path, parameters, user) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    if (user != null && user instanceof ca.aquiletour.core.models.users.Teacher) {
                        let showTeacherDashboardMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage));
                        showTeacherDashboardMessage.sendMessage();
                    }
                    else if (user != null && user instanceof ca.aquiletour.core.models.users.Student) {
                        let showStudentDashboardMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage));
                        showStudentDashboardMessage.sendMessage();
                    }
                    else {
                        let showLoginDialogMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.root.ShowLoginDialogMessage));
                        showLoginDialogMessage.sendMessage();
                    }
                }
                /*private*/ static sendQueuesMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    let showQueuesMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.queues.messages.ShowQueuesMessage));
                    showQueuesMessage.sendMessage();
                }
                /*private*/ static sendQueueMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    if (path.size() >= 1) {
                        let courseId = path.getName(0);
                        let showQueueMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.queue.messages.ShowQueueMessage));
                        showQueueMessage.setCourseId(courseId);
                        showQueueMessage.sendMessage();
                    }
                }
                /*private*/ static sendUsersMessages(path, parameters) {
                    ca.ntro.core.system.trace.T.call(AquiletourRequestHandler);
                    let showUsersMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.users.messages.ShowUsersMessage));
                    showUsersMessage.sendMessage();
                }
            }
            web.AquiletourRequestHandler = AquiletourRequestHandler;
            AquiletourRequestHandler["__class"] = "ca.aquiletour.web.AquiletourRequestHandler";
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            class ViewLoaderRegistrationWeb {
                static registerViewLoaders() {
                    ca.ntro.core.system.trace.T.call(ViewLoaderRegistrationWeb);
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.root.RootView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/root/root.html").setCssUrl("/views/root/root.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.root.RootViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/dashboards/teacher_dashboard/teacher_dashboard.html").setCssUrl("/views/dashboards/teacher_dashboard/teacher_dashboard.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.dashboard.TeacherDashboardViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.dashboards.student.StudentDashboardView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/dashboards/student_dashboard/student_dashboard.html").setCssUrl("/views/dashboards/student_dashboard/student_dashboard.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.dashboard.StudentDashboardViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/course_summaries/teacher_course_summary/course_summary.html").setCssUrl("/views/course_summaries/teacher_course_summary/course_summary.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.dashboard.TeacherCourseSummaryViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/course_summaries/student_course_summary/course_summary.html").setCssUrl("/views/course_summaries/student_course_summary/course_summary.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.dashboard.StudentCourseSummaryViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.queue.QueueView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/queue/queue.html").setCssUrl("/views/queue/queue.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.queue.QueueViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.queues.QueuesView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/queues/queues.html").setCssUrl("/views/queues/queues.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.queues.QueuesViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.queues.QueueSummaryView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/queue_summary/queue_summary.html").setCssUrl("/views/queue_summary/queue_summary.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.queues.QueueSummaryViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.queue.AppointmentView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/appointment/appointment.html").setCssUrl("/views/appointment/appointment.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.queue.AppointmentViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.users.UsersView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/users/users.html").setCssUrl("/views/users/users.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.users.UsersViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.users.UserView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/user/user.html").setCssUrl("/views/user/user.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.users.UserViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.login.LoginView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/login/login.html").setCssUrl("/views/login/login.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.login.LoginViewWeb));
                    ca.ntro.core.mvc.ViewLoaders.registerViewLoader("ca.aquiletour.core.pages.home.HomeView", "fr", ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("/views/home/home.html").setCssUrl("/views/home/home.css").setTranslationsUrl("/i18n/fr/string.json").setTargetClass(ca.aquiletour.web.pages.home.HomeViewWeb));
                }
            }
            web.ViewLoaderRegistrationWeb = ViewLoaderRegistrationWeb;
            ViewLoaderRegistrationWeb["__class"] = "ca.aquiletour.web.ViewLoaderRegistrationWeb";
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var student;
                    (function (student) {
                        class StudentDashboardController extends ca.aquiletour.core.pages.dashboards.DashboardController {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @return {*}
                             */
                            viewClass() {
                                ca.ntro.core.system.trace.T.call(this);
                                return "ca.aquiletour.core.pages.dashboards.student.StudentDashboardView";
                            }
                            /**
                             *
                             */
                            installParentViewMessageHandler() {
                                ca.ntro.core.system.trace.T.call(this);
                                this.addParentViewMessageHandler(ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardMessage, new ca.aquiletour.core.pages.dashboards.student.messages.ShowStudentDashboardHandler());
                                this.addSubViewLoader("ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView", this.currentContext().getLang());
                                this.addModelViewSubViewHandler("ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView", new ca.aquiletour.core.pages.dashboards.DashboardViewModel());
                            }
                        }
                        student.StudentDashboardController = StudentDashboardController;
                        StudentDashboardController["__class"] = "ca.aquiletour.core.pages.dashboards.student.StudentDashboardController";
                        StudentDashboardController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(student = dashboards.student || (dashboards.student = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var pages;
            (function (pages) {
                var dashboards;
                (function (dashboards) {
                    var teacher;
                    (function (teacher) {
                        class TeacherDashboardController extends ca.aquiletour.core.pages.dashboards.DashboardController {
                            constructor() {
                                super();
                            }
                            /**
                             *
                             * @return {*}
                             */
                            viewClass() {
                                ca.ntro.core.system.trace.T.call(this);
                                return "ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView";
                            }
                            /**
                             *
                             */
                            installParentViewMessageHandler() {
                                ca.ntro.core.system.trace.T.call(this);
                                this.addParentViewMessageHandler(ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardMessage, new ca.aquiletour.core.pages.dashboards.teacher.messages.ShowTeacherDashboardHandler());
                                this.addSubViewLoader("ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView", this.currentContext().getLang());
                                this.addModelViewSubViewHandler("ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView", new ca.aquiletour.core.pages.dashboards.DashboardViewModel());
                            }
                        }
                        teacher.TeacherDashboardController = TeacherDashboardController;
                        TeacherDashboardController["__class"] = "ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardController";
                        TeacherDashboardController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
                    })(teacher = dashboards.teacher || (dashboards.teacher = {}));
                })(dashboards = pages.dashboards || (pages.dashboards = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var users;
                (function (users) {
                    class AnonUser extends ca.aquiletour.core.models.users.User {
                        constructor() {
                            super();
                            ca.ntro.core.system.trace.T.call(this);
                            this.setId("__anon");
                            this.setAuthToken("__anon");
                        }
                    }
                    users.AnonUser = AnonUser;
                    AnonUser["__class"] = "ca.aquiletour.core.models.users.AnonUser";
                    AnonUser["__interfaces"] = ["java.io.Serializable"];
                })(users = models.users || (models.users = {}));
            })(models = core.models || (core.models = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var users;
                (function (users) {
                    class Student extends ca.aquiletour.core.models.users.User {
                        constructor() {
                            super();
                            if (this.registrationId === undefined)
                                this.registrationId = null;
                        }
                        getRegistrationId() {
                            return this.registrationId;
                        }
                        setRegistrationId(registrationId) {
                            this.registrationId = registrationId;
                        }
                    }
                    users.Student = Student;
                    Student["__class"] = "ca.aquiletour.core.models.users.Student";
                    Student["__interfaces"] = ["java.io.Serializable"];
                })(users = models.users || (models.users = {}));
            })(models = core.models || (core.models = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var users;
                (function (users) {
                    class Teacher extends ca.aquiletour.core.models.users.User {
                        constructor() {
                            super();
                        }
                    }
                    users.Teacher = Teacher;
                    Teacher["__class"] = "ca.aquiletour.core.models.users.Teacher";
                    Teacher["__interfaces"] = ["java.io.Serializable"];
                })(users = models.users || (models.users = {}));
            })(models = core.models || (core.models = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var users;
                (function (users) {
                    class SuperUser extends ca.aquiletour.core.models.users.User {
                        constructor() {
                            super();
                            ca.ntro.core.system.trace.T.call(this);
                            this.setId("__root");
                            this.setAuthToken("__root");
                        }
                    }
                    users.SuperUser = SuperUser;
                    SuperUser["__class"] = "ca.aquiletour.core.models.users.SuperUser";
                    SuperUser["__interfaces"] = ["java.io.Serializable"];
                })(users = models.users || (models.users = {}));
            })(models = core.models || (core.models = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class StudentDashboardViewWeb extends ca.aquiletour.web.pages.dashboard.DashboardViewWeb {
                        constructor() {
                            super();
                        }
                    }
                    dashboard.StudentDashboardViewWeb = StudentDashboardViewWeb;
                    StudentDashboardViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.StudentDashboardViewWeb";
                    StudentDashboardViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.DashboardView", "ca.aquiletour.core.pages.dashboards.student.StudentDashboardView"];
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class TeacherDashboardViewWeb extends ca.aquiletour.web.pages.dashboard.DashboardViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                            super.initialize();
                            ca.ntro.core.system.trace.T.call(this);
                            let addCourseButton = this.getRootElement().find("#add-course-submit-button").get(0);
                            let addCourseTitleInput = this.getRootElement().find("#add-course-title-input").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(addCourseButton);
                            ca.ntro.core.system.assertions.MustNot.beNull(addCourseTitleInput);
                            addCourseButton.addEventListener("click", new TeacherDashboardViewWeb.TeacherDashboardViewWeb$0(this, addCourseTitleInput));
                        }
                    }
                    dashboard.TeacherDashboardViewWeb = TeacherDashboardViewWeb;
                    TeacherDashboardViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.TeacherDashboardViewWeb";
                    TeacherDashboardViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.dashboards.teacher.TeacherDashboardView", "ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.DashboardView"];
                    (function (TeacherDashboardViewWeb) {
                        class TeacherDashboardViewWeb$0 {
                            constructor(__parent, addCourseTitleInput) {
                                this.addCourseTitleInput = addCourseTitleInput;
                                this.__parent = __parent;
                            }
                            /**
                             *
                             */
                            onEvent() {
                                ca.ntro.core.system.trace.T.call(this);
                                let addCourseMessage = (ca.ntro.messages.MessageFactory.getOutgoingMessage(ca.aquiletour.core.pages.dashboards.teacher.messages.AddCourseMessage));
                                addCourseMessage.setCourse(new ca.aquiletour.core.pages.dashboards.values.CourseSummary(this.addCourseTitleInput.getValue()));
                                addCourseMessage.sendMessage();
                            }
                        }
                        TeacherDashboardViewWeb.TeacherDashboardViewWeb$0 = TeacherDashboardViewWeb$0;
                        TeacherDashboardViewWeb$0["__interfaces"] = ["ca.ntro.web.dom.HtmlEventListener"];
                    })(TeacherDashboardViewWeb = dashboard.TeacherDashboardViewWeb || (dashboard.TeacherDashboardViewWeb = {}));
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class StudentCourseSummaryViewWeb extends ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} course
                         */
                        displaySummary(course) {
                            ca.ntro.core.system.trace.T.call(this);
                            ca.ntro.core.system.trace.T.here();
                            let title = this.getRootElement().children("#course-title").get(0);
                            let courseId = this.getRootElement().children("#courseId").get(0);
                            let makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
                            let teacherAvailable = this.getRootElement().children("#buttonAvailable").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(title);
                            ca.ntro.core.system.assertions.MustNot.beNull(courseId);
                            ca.ntro.core.system.assertions.MustNot.beNull(makeAppointmentLink);
                            ca.ntro.core.system.assertions.MustNot.beNull(teacherAvailable);
                            ca.ntro.core.system.trace.T.values(course.getTitle());
                            title.appendHtml(course.getTitle());
                            if (course.getMyAppointment() != null && course.getIsQueueOpen() != null) {
                                if (course.getMyAppointment()) {
                                    teacherAvailable.appendHtml("j\'ai d\u00e9j\u00e0 un rendez-vous");
                                }
                                else if (course.getIsQueueOpen()) {
                                    teacherAvailable.appendHtml("prof disponible");
                                }
                                else {
                                    teacherAvailable.appendHtml("prof non-disponible");
                                }
                            }
                            makeAppointmentLink.setAttribute("href", "billetterie/" + course.getTitle() + "?makeAppointment");
                        }
                    }
                    dashboard.StudentCourseSummaryViewWeb = StudentCourseSummaryViewWeb;
                    StudentCourseSummaryViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.StudentCourseSummaryViewWeb";
                    StudentCourseSummaryViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.dashboards.student.StudentCourseSummaryView", "ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.CourseSummaryView"];
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            var pages;
            (function (pages) {
                var dashboard;
                (function (dashboard) {
                    class TeacherCourseSummaryViewWeb extends ca.aquiletour.web.pages.dashboard.CourseSummaryViewWeb {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         */
                        initialize() {
                        }
                        /**
                         *
                         * @param {ca.aquiletour.core.pages.dashboards.values.CourseSummary} course
                         */
                        displaySummary(course) {
                            ca.ntro.core.system.trace.T.call(this);
                            ca.ntro.core.system.trace.T.here();
                            let title = this.getRootElement().children("#course-title").get(0);
                            let courseId = this.getRootElement().children("#courseId").get(0);
                            let nbAppointment = this.getRootElement().children("#nbAppointment").get(0);
                            let makeAppointmentLink = this.getRootElement().children("#availableLink").get(0);
                            ca.ntro.core.system.assertions.MustNot.beNull(title);
                            ca.ntro.core.system.assertions.MustNot.beNull(courseId);
                            ca.ntro.core.system.assertions.MustNot.beNull(nbAppointment);
                            ca.ntro.core.system.assertions.MustNot.beNull(makeAppointmentLink);
                            ca.ntro.core.system.trace.T.values(course.getTitle());
                            title.appendHtml(course.getTitle());
                            nbAppointment.appendHtml(/* toString */ ('' + (course.getNumberOfAppointments())));
                            makeAppointmentLink.setAttribute("href", "billetterie/" + course.getTitle() + "?makeAppointment");
                        }
                    }
                    dashboard.TeacherCourseSummaryViewWeb = TeacherCourseSummaryViewWeb;
                    TeacherCourseSummaryViewWeb["__class"] = "ca.aquiletour.web.pages.dashboard.TeacherCourseSummaryViewWeb";
                    TeacherCourseSummaryViewWeb["__interfaces"] = ["ca.aquiletour.core.pages.dashboards.teacher.TeacherCourseSummaryView", "ca.ntro.core.mvc.NtroView", "ca.aquiletour.core.pages.dashboards.CourseSummaryView"];
                })(dashboard = pages.dashboard || (pages.dashboard = {}));
            })(pages = web.pages || (web.pages = {}));
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
//# sourceMappingURL=bundle.js.map
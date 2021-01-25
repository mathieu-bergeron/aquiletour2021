/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var wrappers;
            (function (wrappers) {
                var options;
                (function (options) {
                    class None {
                    }
                    options.None = None;
                    None["__class"] = "ca.ntro.core.wrappers.options.None";
                })(options = wrappers.options || (wrappers.options = {}));
            })(wrappers = core.wrappers || (core.wrappers = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var wrappers;
            (function (wrappers) {
                var options;
                (function (options) {
                    class EmptyOptionException extends Error {
                        constructor() {
                            super();
                            Object.setPrototypeOf(this, EmptyOptionException.prototype);
                        }
                    }
                    EmptyOptionException.serialVersionUID = -5086580751854707928;
                    options.EmptyOptionException = EmptyOptionException;
                    EmptyOptionException["__class"] = "ca.ntro.core.wrappers.options.EmptyOptionException";
                    EmptyOptionException["__interfaces"] = ["java.io.Serializable"];
                })(options = wrappers.options || (wrappers.options = {}));
            })(wrappers = core.wrappers || (core.wrappers = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var wrappers;
            (function (wrappers) {
                var options;
                (function (options) {
                    var State;
                    (function (State) {
                        State[State["SOME"] = 0] = "SOME";
                        State[State["NONE"] = 1] = "NONE";
                    })(State = options.State || (options.State = {}));
                })(options = wrappers.options || (wrappers.options = {}));
            })(wrappers = core.wrappers || (core.wrappers = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var wrappers;
            (function (wrappers) {
                var options;
                (function (options) {
                    class Optionnal {
                        constructor(value) {
                            /*private*/ this.state = ca.ntro.core.wrappers.options.State.NONE;
                            if (this.value === undefined)
                                this.value = null;
                            this.set(value);
                        }
                        isEmpty() {
                            return this.state === ca.ntro.core.wrappers.options.State.NONE;
                        }
                        get() {
                            if (this.state === ca.ntro.core.wrappers.options.State.NONE) {
                                throw new ca.ntro.core.wrappers.options.EmptyOptionException();
                            }
                            return this.value;
                        }
                        set(value) {
                            this.value = value;
                            this.state = ca.ntro.core.wrappers.options.State.SOME;
                        }
                    }
                    options.Optionnal = Optionnal;
                    Optionnal["__class"] = "ca.ntro.core.wrappers.options.Optionnal";
                })(options = wrappers.options || (wrappers.options = {}));
            })(wrappers = core.wrappers || (core.wrappers = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            class Ntro {
                static zzz_registerResourceLoader(resourceLoader) {
                    Ntro.__resourceLoader = resourceLoader;
                }
                static __registerIntrospector(introspector) {
                    Ntro.__introspector = introspector;
                }
                static __registerLogger(logger) {
                    ca.ntro.core.system.trace.__T.call(Ntro, "registerLogger");
                    Ntro.__logger = logger;
                }
                static __registerAppCloser(appCloser) {
                    ca.ntro.core.system.trace.__T.call(Ntro, "registerAppCloser");
                    Ntro.__appCloser = appCloser;
                }
                static __registerRegEx(regEx) {
                    ca.ntro.core.system.trace.__T.call(Ntro, "registerRegEx");
                    Ntro.__regEx = regEx;
                }
                static introspector() {
                    console.info("#T.call (Ntro.java) >> Ntro.introspector");
                    if (Ntro.__introspector == null) {
                        console.error("#FATAL | Introspector not registered");
                    }
                    return Ntro.__introspector;
                }
                static regEx() {
                    ca.ntro.core.system.trace.__T.call(Ntro, "regEx");
                    if (Ntro.__regEx == null) {
                        console.error("#FATAL | RegEx not registered");
                    }
                    return Ntro.__regEx;
                }
                static logger() {
                    ca.ntro.core.system.trace.__T.call(Ntro, "logger");
                    if (Ntro.__logger == null) {
                        console.error("#FATAL | Logger not registered");
                    }
                    return Ntro.__logger;
                }
                static appCloser() {
                    ca.ntro.core.system.trace.__T.call(Ntro, "appCloser");
                    if (Ntro.__appCloser == null) {
                        console.error("#FATAL | AppCloser not registered");
                    }
                    return Ntro.__appCloser;
                }
                static resourceLoader() {
                    ca.ntro.core.system.trace.__T.call(Ntro, "resourceLoader");
                    if (Ntro.__resourceLoader == null) {
                        console.error("#FATAL | ResourceLoader not registered");
                    }
                    return Ntro.__resourceLoader;
                }
                static viewLoaderWeb() {
                    return new ca.ntro.core.mvc.view.ViewLoaderWeb();
                }
            }
            Ntro.__introspector = null;
            Ntro.__logger = null;
            Ntro.__appCloser = null;
            Ntro.__regEx = null;
            Ntro.__resourceLoader = null;
            core.Ntro = Ntro;
            Ntro["__class"] = "ca.ntro.core.Ntro";
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class LabelFormater {
                        static format(builder, className, methodName, message, location) {
                            /* append */ (sb => { sb.str = sb.str.concat("#"); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(className); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat("."); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(methodName); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(" | "); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(message); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(" ("); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(location.toString()); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(")"); return sb; })(builder);
                        }
                    }
                    trace.LabelFormater = LabelFormater;
                    LabelFormater["__class"] = "ca.ntro.core.system.trace.LabelFormater";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class TraceFilter {
                    }
                    trace.TraceFilter = TraceFilter;
                    TraceFilter["__class"] = "ca.ntro.core.system.trace.TraceFilter";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var stack;
                (function (stack) {
                    class StackAnalyzer {
                        getTracedFrame(className, stackOffset) {
                            stackOffset++;
                            let result = null;
                            let finalStackOffset = this.getInitialStackOffset() + stackOffset;
                            result = this.getTracedFrameImpl(className, finalStackOffset);
                            return result;
                        }
                    }
                    stack.StackAnalyzer = StackAnalyzer;
                    StackAnalyzer["__class"] = "ca.ntro.core.system.stack.StackAnalyzer";
                })(stack = system.stack || (system.stack = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var stack;
                (function (stack) {
                    class StackFrame {
                        constructor(className, methodName, location) {
                            if (this.className === undefined)
                                this.className = null;
                            if (this.methodName === undefined)
                                this.methodName = null;
                            if (this.location === undefined)
                                this.location = null;
                            this.className = className;
                            this.methodName = methodName;
                            this.location = location;
                        }
                        printFrame(builder) {
                            this.printSourceLocation(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(" >> "); return sb; })(builder);
                            if (this.className != null) {
                                /* append */ (sb => { sb.str = sb.str.concat(this.className); return sb; })(builder);
                            }
                            else {
                                this.location.formatClassName(builder);
                            }
                            /* append */ (sb => { sb.str = sb.str.concat("."); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(this.methodName); return sb; })(builder);
                        }
                        printSourceLocation(builder) {
                            /* append */ (sb => { sb.str = sb.str.concat(" ("); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(this.location); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(")"); return sb; })(builder);
                        }
                    }
                    stack.StackFrame = StackFrame;
                    StackFrame["__class"] = "ca.ntro.core.system.stack.StackFrame";
                })(stack = system.stack || (system.stack = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var log;
                (function (log) {
                    class Log {
                        static warning(messages) {
                        }
                        static error(messages) {
                        }
                        static fatalError(message, ...causedBy) {
                            let currentDepth = 1;
                            let stackAnalyzer = ca.ntro.core.__Ntro.stackAnalyzer();
                            if (stackAnalyzer != null) {
                                let tracedFrame = stackAnalyzer.getTracedFrame(null, currentDepth);
                                let builder = { str: "", toString: function () { return this.str; } };
                                /* append */ (sb => { sb.str = sb.str.concat("#FATAL | "); return sb; })(builder);
                                /* append */ (sb => { sb.str = sb.str.concat(message); return sb; })(builder);
                                /* append */ (sb => { sb.str = sb.str.concat(" ("); return sb; })(builder);
                                tracedFrame.printSourceLocation(builder);
                                /* append */ (sb => { sb.str = sb.str.concat(")"); return sb; })(builder);
                                if (causedBy != null && causedBy.length > 0) {
                                    /* append */ (sb => { sb.str = sb.str.concat("\ncaused by\n"); return sb; })(builder);
                                    console.info(/* toString */ builder.str);
                                    console.error(causedBy[0].message, causedBy[0]);
                                }
                                else {
                                    console.info(/* toString */ builder.str);
                                }
                                ca.ntro.core.Ntro.appCloser().close();
                            }
                        }
                    }
                    log.Log = Log;
                    Log["__class"] = "ca.ntro.core.system.log.Log";
                })(log = system.log || (system.log = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var assertions;
                (function (assertions) {
                    class MustNot {
                        static beNull(value) {
                            if (value == null) {
                                let builder = { str: "", toString: function () { return this.str; } };
                                let stackOffset = 1;
                                let stackAnalyzer = ca.ntro.core.__Ntro.stackAnalyzer();
                                if (stackAnalyzer != null) {
                                    let tracedFrame = ca.ntro.core.__Ntro.stackAnalyzer().getTracedFrame(null, stackOffset);
                                    tracedFrame.printSourceLocation(builder);
                                    ca.ntro.core.system.log.Log.fatalError("null value [" + builder.str + "]  ");
                                }
                            }
                        }
                        static beTrue(expr) {
                            let value = expr.evaluate();
                            if (!((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(value, true)) {
                                ca.ntro.core.system.log.Log.fatalError(expr.toString());
                            }
                        }
                    }
                    assertions.MustNot = MustNot;
                    MustNot["__class"] = "ca.ntro.core.system.assertions.MustNot";
                })(assertions = system.assertions || (system.assertions = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var assertions;
                (function (assertions) {
                    class AssertExpr {
                        static value(o) {
                            return new ca.ntro.core.system.assertions.ValueExpr(o);
                        }
                    }
                    assertions.AssertExpr = AssertExpr;
                    AssertExpr["__class"] = "ca.ntro.core.system.assertions.AssertExpr";
                })(assertions = system.assertions || (system.assertions = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var source;
                (function (source) {
                    class SourceFileLocation {
                        constructor(rawFilePath, line) {
                            if (this.fileName === undefined)
                                this.fileName = null;
                            if (this.line === undefined)
                                this.line = 0;
                            this.fileName = this.extractFilename(rawFilePath);
                            this.line = line;
                        }
                        /*private*/ extractFilename(rawFilePath) {
                            let pathSplits = rawFilePath.split(SourceFileLocation.PATH_DELEMITOR);
                            return pathSplits[pathSplits.length - 1];
                        }
                        toString() {
                            return this.fileName + ":" + this.line;
                        }
                        formatClassName(builder) {
                            /* append */ (sb => { sb.str = sb.str.concat(this.fileName); return sb; })(builder);
                        }
                    }
                    SourceFileLocation.PATH_DELEMITOR = "/";
                    source.SourceFileLocation = SourceFileLocation;
                    SourceFileLocation["__class"] = "ca.ntro.core.system.source.SourceFileLocation";
                })(source = system.source || (system.source = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var tasks;
            (function (tasks) {
                var State;
                (function (State) {
                    State[State["INITIALIZING"] = 0] = "INITIALIZING";
                    State[State["EXECUTE_PREVIOUS_TASKS"] = 1] = "EXECUTE_PREVIOUS_TASKS";
                    State[State["EXECUTE_SUB_TASKS"] = 2] = "EXECUTE_SUB_TASKS";
                    State[State["EXECUTE_CURRENT_TASK"] = 3] = "EXECUTE_CURRENT_TASK";
                    State[State["EXECUTE_NEXT_TASKS"] = 4] = "EXECUTE_NEXT_TASKS";
                    State[State["DONE"] = 5] = "DONE";
                })(State = tasks.State || (tasks.State = {}));
            })(tasks = core.tasks || (core.tasks = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var tasks;
            (function (tasks) {
                class NtroTask {
                    constructor() {
                        /*private*/ this.previousTasks = ({});
                        /*private*/ this.finishedPreviousTasks = 0;
                        /*private*/ this.subTasks = ({});
                        /*private*/ this.finishedSubTasks = 0;
                        /*private*/ this.nextTasks = ([]);
                        /*private*/ this.state = ca.ntro.core.tasks.State.INITIALIZING;
                        if (this.parentTask === undefined)
                            this.parentTask = null;
                    }
                    execute() {
                        if (this.state === ca.ntro.core.tasks.State.INITIALIZING) {
                            this.startExecution();
                        }
                    }
                    getState() {
                        return this.state;
                    }
                    /*private*/ startExecution() {
                        if (this.parentTask != null && this.parentTask.getState() === ca.ntro.core.tasks.State.INITIALIZING) {
                            this.parentTask.execute();
                        }
                        else {
                            this.state = ca.ntro.core.tasks.State.EXECUTE_PREVIOUS_TASKS;
                            this.resumeExecution();
                        }
                    }
                    setParentTask(parentTask) {
                        if (this.state === ca.ntro.core.tasks.State.INITIALIZING) {
                            this.parentTask = parentTask;
                        }
                        else {
                            throw Object.defineProperty(new Error("Task.setParentTask called on state " + this.state), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.IllegalStateException', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                        }
                    }
                    /*private*/ resumeExecution() {
                        switch ((this.state)) {
                            case ca.ntro.core.tasks.State.EXECUTE_PREVIOUS_TASKS:
                                if (this.ifShouldExecutePreviousTasks()) {
                                    this.executePreviousTasks();
                                }
                                else {
                                    this.state = ca.ntro.core.tasks.State.EXECUTE_SUB_TASKS;
                                    this.resumeExecution();
                                }
                                break;
                            case ca.ntro.core.tasks.State.EXECUTE_SUB_TASKS:
                                if (this.ifShouldExecuteSubTasks()) {
                                    this.executeSubTasks();
                                }
                                else {
                                    this.state = ca.ntro.core.tasks.State.EXECUTE_CURRENT_TASK;
                                    this.resumeExecution();
                                }
                                break;
                            case ca.ntro.core.tasks.State.EXECUTE_CURRENT_TASK:
                                this.runTask();
                                break;
                            case ca.ntro.core.tasks.State.EXECUTE_NEXT_TASKS:
                                this.executeNextTasks();
                                this.state = ca.ntro.core.tasks.State.DONE;
                                break;
                            case ca.ntro.core.tasks.State.DONE:
                            default:
                                break;
                        }
                    }
                    /*private*/ executePreviousTasks() {
                        {
                            let array122 = (obj => Object.keys(obj).map(key => obj[key]))(this.previousTasks);
                            for (let index121 = 0; index121 < array122.length; index121++) {
                                let previousTask = array122[index121];
                                {
                                    previousTask.execute();
                                }
                            }
                        }
                    }
                    /*private*/ ifShouldExecutePreviousTasks() {
                        return this.finishedPreviousTasks < Object.keys(this.previousTasks).length;
                    }
                    notifySomePreviousTaskFinished() {
                        this.finishedPreviousTasks++;
                        if (this.state === ca.ntro.core.tasks.State.EXECUTE_PREVIOUS_TASKS) {
                            this.resumeExecution();
                        }
                    }
                    notifyTaskFinished() {
                        if (this.parentTask != null) {
                            this.parentTask.notifySomeSubTaskFinished();
                        }
                        this.state = ca.ntro.core.tasks.State.EXECUTE_NEXT_TASKS;
                        this.resumeExecution();
                    }
                    notifySomeSubTaskFinished() {
                        this.finishedSubTasks++;
                        if (this.finishedSubTasks === Object.keys(this.subTasks).length) {
                            this.state = ca.ntro.core.tasks.State.EXECUTE_CURRENT_TASK;
                            this.resumeExecution();
                        }
                    }
                    /*private*/ executeNextTasks() {
                        for (let index123 = 0; index123 < this.nextTasks.length; index123++) {
                            let nextTask = this.nextTasks[index123];
                            {
                                nextTask.notifySomePreviousTaskFinished();
                                if (nextTask.getState() === ca.ntro.core.tasks.State.INITIALIZING) {
                                    nextTask.execute();
                                }
                            }
                        }
                    }
                    getPreviousTask$java_lang_Class(taskClass) {
                        return (this.getPreviousTask$java_lang_Class$java_lang_String(taskClass, /* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(taskClass)));
                    }
                    getPreviousTask$java_lang_Class$java_lang_String(taskClass, id) {
                        return ((m, k) => m[k] === undefined ? null : m[k])(this.previousTasks, id);
                    }
                    getPreviousTask(taskClass, id) {
                        if (((taskClass != null) || taskClass === null) && ((typeof id === 'string') || id === null)) {
                            return this.getPreviousTask$java_lang_Class$java_lang_String(taskClass, id);
                        }
                        else if (((taskClass != null) || taskClass === null) && id === undefined) {
                            return this.getPreviousTask$java_lang_Class(taskClass);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    getSubTask$java_lang_Class(taskClass) {
                        return (this.getSubTask$java_lang_Class$java_lang_String(taskClass, this.defaultIdFromClass(taskClass)));
                    }
                    getSubTask$java_lang_Class$java_lang_String(taskClass, id) {
                        return ((m, k) => m[k] === undefined ? null : m[k])(this.subTasks, id);
                    }
                    getSubTask(taskClass, id) {
                        if (((taskClass != null) || taskClass === null) && ((typeof id === 'string') || id === null)) {
                            return this.getSubTask$java_lang_Class$java_lang_String(taskClass, id);
                        }
                        else if (((taskClass != null) || taskClass === null) && id === undefined) {
                            return this.getSubTask$java_lang_Class(taskClass);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    getFinishedTask$java_lang_Class(taskClass) {
                        return (this.getFinishedTask$java_lang_Class$java_lang_String(taskClass, /* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(taskClass)));
                    }
                    getFinishedTask$java_lang_Class$java_lang_String(taskClass, id) {
                        let finishedTask = ((m, k) => m[k] === undefined ? null : m[k])(this.subTasks, id);
                        if (finishedTask == null) {
                            finishedTask = ((m, k) => m[k] === undefined ? null : m[k])(this.previousTasks, id);
                        }
                        return finishedTask;
                    }
                    getFinishedTask(taskClass, id) {
                        if (((taskClass != null) || taskClass === null) && ((typeof id === 'string') || id === null)) {
                            return this.getFinishedTask$java_lang_Class$java_lang_String(taskClass, id);
                        }
                        else if (((taskClass != null) || taskClass === null) && id === undefined) {
                            return this.getFinishedTask$java_lang_Class(taskClass);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /*private*/ defaultId(task) {
                        return this.defaultIdFromClass(task.constructor);
                    }
                    /*private*/ defaultIdFromClass(taskClass) {
                        return (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(taskClass);
                    }
                    addPreviousTask$ca_ntro_core_tasks_NtroTask(task) {
                        return this.addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, this.defaultId(task));
                    }
                    addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId) {
                        if (this.state === ca.ntro.core.tasks.State.INITIALIZING) {
                            if (!this.previousTasks.hasOwnProperty(taskId)) {
                                /* put */ (this.previousTasks[taskId] = task);
                                task.addNextTask(this);
                            }
                        }
                        else {
                            throw Object.defineProperty(new Error("Task.addPreviousTask called on state " + this.state), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.IllegalStateException', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                        }
                        return this;
                    }
                    addPreviousTask(task, taskId) {
                        if (((task != null && task instanceof ca.ntro.core.tasks.NtroTask) || task === null) && ((typeof taskId === 'string') || taskId === null)) {
                            return this.addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId);
                        }
                        else if (((task != null && task instanceof ca.ntro.core.tasks.NtroTask) || task === null) && taskId === undefined) {
                            return this.addPreviousTask$ca_ntro_core_tasks_NtroTask(task);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    addNextTask(task) {
                        if (this.state === ca.ntro.core.tasks.State.INITIALIZING) {
                            if (!(this.nextTasks.indexOf((task)) >= 0)) {
                                /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(this.nextTasks, task);
                                task.addPreviousTask$ca_ntro_core_tasks_NtroTask(this);
                            }
                        }
                        else {
                            throw Object.defineProperty(new Error("Task.addPreviousTask called on state " + this.state), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.IllegalStateException', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                        }
                        return this;
                    }
                    /*private*/ ifShouldExecuteSubTasks() {
                        return this.finishedSubTasks < Object.keys(this.subTasks).length;
                    }
                    /*private*/ executeSubTasks() {
                        {
                            let array125 = (obj => Object.keys(obj).map(key => obj[key]))(this.subTasks);
                            for (let index124 = 0; index124 < array125.length; index124++) {
                                let subTask = array125[index124];
                                {
                                    subTask.execute();
                                }
                            }
                        }
                    }
                    addSubTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId) {
                        if (this.state === ca.ntro.core.tasks.State.INITIALIZING) {
                            task.setParentTask(this);
                            /* put */ (this.subTasks[taskId] = task);
                        }
                        else {
                            throw Object.defineProperty(new Error("Task.addSubTask called on state " + this.state), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.IllegalStateException', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                        }
                    }
                    addSubTask(task, taskId) {
                        if (((task != null && task instanceof ca.ntro.core.tasks.NtroTask) || task === null) && ((typeof taskId === 'string') || taskId === null)) {
                            return this.addSubTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId);
                        }
                        else if (((task != null && task instanceof ca.ntro.core.tasks.NtroTask) || task === null) && taskId === undefined) {
                            return this.addSubTask$ca_ntro_core_tasks_NtroTask(task);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    addSubTask$ca_ntro_core_tasks_NtroTask(task) {
                        this.addSubTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, this.defaultId(task));
                    }
                }
                tasks.NtroTask = NtroTask;
                NtroTask["__class"] = "ca.ntro.core.tasks.NtroTask";
            })(tasks = core.tasks || (core.tasks = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            class Constants {
            }
            Constants.RESOURCES_URL_PREFIX = "_R";
            core.Constants = Constants;
            Constants["__class"] = "ca.ntro.core.Constants";
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            class __Ntro {
                static registerStackAnalyzer(stackAnalyzer) {
                    ca.ntro.core.system.trace.__T.call(__Ntro, "registerStackAnalyzer");
                    __Ntro.__stackAnalyzer = stackAnalyzer;
                }
                static stackAnalyzer() {
                    ca.ntro.core.system.trace.__T.call(__Ntro, "stackAnalyzer");
                    return __Ntro.__stackAnalyzer;
                }
            }
            __Ntro.__stackAnalyzer = null;
            core.__Ntro = __Ntro;
            __Ntro["__class"] = "ca.ntro.core.__Ntro";
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class FieldSignature {
                    constructor(name, type, modifiers) {
                        /*private*/ this.modifiers = ([]);
                        if (this.name === undefined)
                            this.name = null;
                        if (this.type === undefined)
                            this.type = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.name = name;
                        this.type = type;
                        this.modifiers = modifiers;
                    }
                    toString() {
                        ca.ntro.core.system.trace.T.call(this);
                        let builder = { str: "", toString: function () { return this.str; } };
                        for (let index126 = 0; index126 < this.modifiers.length; index126++) {
                            let modifier = this.modifiers[index126];
                            {
                                /* append */ (sb => { sb.str = sb.str.concat(modifier); return sb; })(builder);
                                /* append */ (sb => { sb.str = sb.str.concat(" "); return sb; })(builder);
                            }
                        }
                        /* append */ (sb => { sb.str = sb.str.concat(this.type); return sb; })(builder);
                        /* append */ (sb => { sb.str = sb.str.concat(" "); return sb; })(builder);
                        /* append */ (sb => { sb.str = sb.str.concat(this.name); return sb; })(builder);
                        return builder.str;
                    }
                    getName() {
                        return this.name;
                    }
                    setName(name) {
                        this.name = name;
                    }
                    static fromString(signatureString) {
                        ca.ntro.core.system.trace.T.call(FieldSignature);
                        let modifiers = ([]);
                        let name = null;
                        let type = null;
                        let elements = signatureString.split(" ");
                        for (let i = 0; i < elements.length - 2; i++) {
                            {
                                /* add */ (modifiers.push(elements[i]) > 0);
                            }
                            ;
                        }
                        type = elements[elements.length - 2];
                        name = elements[elements.length - 1];
                        return new FieldSignature(name, type, modifiers);
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return ((o) => { if (o.hashCode) {
                            return o.hashCode();
                        }
                        else {
                            return o.toString().split('').reduce((prevHash, currVal) => (((prevHash << 5) - prevHash) + currVal.charCodeAt(0)) | 0, 0);
                        } })(this.toString());
                    }
                    /**
                     *
                     * @param {*} obj
                     * @return {boolean}
                     */
                    equals(obj) {
                        if (obj != null && obj instanceof ca.ntro.core.introspection.FieldSignature) {
                            let other = obj;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(other.toString(), this.toString());
                        }
                        else {
                            return false;
                        }
                    }
                    getModifiers() {
                        return this.modifiers;
                    }
                    setModifiers(modifiers) {
                        this.modifiers = modifiers;
                    }
                    getType() {
                        return this.type;
                    }
                    setType(type) {
                        this.type = type;
                    }
                    isPrivate() {
                        return (this.modifiers.indexOf(("private")) >= 0);
                    }
                }
                introspection.FieldSignature = FieldSignature;
                FieldSignature["__class"] = "ca.ntro.core.introspection.FieldSignature";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class ClassSignature {
                }
                introspection.ClassSignature = ClassSignature;
                ClassSignature["__class"] = "ca.ntro.core.introspection.ClassSignature";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class ProcedureSignature {
                    constructor(name, argumentTypes, modifiers) {
                        /*private*/ this.argumentTypes = ([]);
                        /*private*/ this.modifiers = ([]);
                        if (this.name === undefined)
                            this.name = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.name = name;
                        this.argumentTypes = argumentTypes;
                        this.modifiers = modifiers;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    toString() {
                        ca.ntro.core.system.trace.T.call(this);
                        let builder = { str: "", toString: function () { return this.str; } };
                        this.formatModifiers(builder);
                        this.formatName(builder);
                        this.formatArguments(builder);
                        return builder.str;
                    }
                    formatArguments(builder) {
                        ca.ntro.core.system.trace.T.call(this);
                        /* append */ (sb => { sb.str = sb.str.concat("("); return sb; })(builder);
                        if (this.argumentTypes.length > 0) {
                            /* append */ (sb => { sb.str = sb.str.concat(this.argumentTypes[0].toString()); return sb; })(builder);
                            for (let i = 1; i < this.argumentTypes.length; i++) {
                                {
                                    /* append */ (sb => { sb.str = sb.str.concat(", "); return sb; })(builder);
                                    /* append */ (sb => { sb.str = sb.str.concat(this.argumentTypes[i].toString()); return sb; })(builder);
                                }
                                ;
                            }
                        }
                        /* append */ (sb => { sb.str = sb.str.concat(")"); return sb; })(builder);
                    }
                    formatName(builder) {
                        ca.ntro.core.system.trace.T.call(this);
                        /* append */ (sb => { sb.str = sb.str.concat(" "); return sb; })(builder);
                        /* append */ (sb => { sb.str = sb.str.concat(this.name); return sb; })(builder);
                    }
                    formatModifiers(builder) {
                        ca.ntro.core.system.trace.T.call(this);
                        for (let index127 = 0; index127 < this.modifiers.length; index127++) {
                            let modifier = this.modifiers[index127];
                            {
                                /* append */ (sb => { sb.str = sb.str.concat(modifier); return sb; })(builder);
                                /* append */ (sb => { sb.str = sb.str.concat(" "); return sb; })(builder);
                            }
                        }
                    }
                    getName() {
                        return this.name;
                    }
                    setName(name) {
                        this.name = name;
                    }
                    getArgumentTypes() {
                        return this.argumentTypes;
                    }
                    setArgumentTypes(argumentTypes) {
                        this.argumentTypes = argumentTypes;
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return ((o) => { if (o.hashCode) {
                            return o.hashCode();
                        }
                        else {
                            return o.toString().split('').reduce((prevHash, currVal) => (((prevHash << 5) - prevHash) + currVal.charCodeAt(0)) | 0, 0);
                        } })(this.toString());
                    }
                    /**
                     *
                     * @param {*} obj
                     * @return {boolean}
                     */
                    equals(obj) {
                        if (obj != null && obj instanceof ca.ntro.core.introspection.ProcedureSignature) {
                            let other = obj;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(other.toString(), this.toString());
                        }
                        else {
                            return false;
                        }
                    }
                    getModifiers() {
                        return this.modifiers;
                    }
                    setModifiers(modifiers) {
                        this.modifiers = modifiers;
                    }
                    isPrivate() {
                        return (this.modifiers.indexOf(("private")) >= 0);
                    }
                }
                introspection.ProcedureSignature = ProcedureSignature;
                ProcedureSignature["__class"] = "ca.ntro.core.introspection.ProcedureSignature";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class Factory {
                    static newInstance(instanceType) {
                        let instance = null;
                        try {
                            instance = new (instanceType)();
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError("FATAL cannot instantiate " + (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(instanceType), e);
                        }
                        ;
                        return instance;
                    }
                }
                introspection.Factory = Factory;
                Factory["__class"] = "ca.ntro.core.introspection.Factory";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class Introspector {
                    findMethodBySignature(currentClass, methodSignature) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let result = null;
                        {
                            let array129 = this.userDefinedMethodsFromClass(currentClass);
                            for (let index128 = 0; index128 < array129.length; index128++) {
                                let candidate = array129[index128];
                                {
                                    let candidateSignature = this.methodSignature(candidate);
                                    if (candidateSignature.equals(methodSignature)) {
                                        result = candidate;
                                        break;
                                    }
                                }
                            }
                        }
                        return result;
                    }
                    findMethodByName(_class, methodName) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let result = null;
                        {
                            let array131 = this.userDefinedMethodsFromClass(_class);
                            for (let index130 = 0; index130 < array131.length; index130++) {
                                let method = array131[index130];
                                {
                                    if (((o1, o2) => { if (o1 && o1.equals) {
                                        return o1.equals(o2);
                                    }
                                    else {
                                        return o1 === o2;
                                    } })(/* getName */ method.name, methodName)) {
                                        result = method;
                                        break;
                                    }
                                }
                            }
                        }
                        return result;
                    }
                    getClassFromName(className) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let _class = null;
                        try {
                            _class = eval(className);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError("Cannot find class " + className, e);
                        }
                        ;
                        return _class;
                    }
                    /*private*/ setterName(fieldName) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        return "set" + this.capitalize(fieldName);
                    }
                    /*private*/ capitalize(value) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        return value.substring(0, 1).toUpperCase() + value.substring(1);
                    }
                    /*private*/ unCapitalize(value) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        return value.substring(0, 1).toLowerCase() + value.substring(1);
                    }
                    fieldNameForGetter(method) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let methodName = method.name;
                        let fieldName = methodName.substring(3);
                        fieldName = this.unCapitalize(fieldName);
                        return fieldName;
                    }
                    findSetter(_class, fieldName) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let result = null;
                        let setterName = this.setterName(fieldName);
                        result = this.findMethodByName(_class, setterName);
                        return result;
                    }
                    isAGetter(method) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let isNotGetClass = !((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(/* getName */ method.name, "getClass");
                        return this.isASetterOrSetter(method, "get") && isNotGetClass;
                    }
                    isASetter(method) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        return this.isASetterOrSetter(method, "set");
                    }
                    /*private*/ isASetterOrSetter(method, prefix) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let methodName = method.name;
                        let methodStartsWithPrefix = ((str, searchString, position = 0) => str.substr(position, searchString.length) === searchString)(methodName, prefix);
                        let upperCaseAfterPrefix = false;
                        if (methodName.length > prefix.length) {
                            let letterAfterGet = methodName.substring(prefix.length, prefix.length + 1);
                            upperCaseAfterPrefix = letterAfterGet != null && letterAfterGet !== "" && ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(letterAfterGet.toUpperCase(), letterAfterGet);
                        }
                        return methodStartsWithPrefix && upperCaseAfterPrefix;
                    }
                    userDefinedMethodsFromObject(object) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        return this.userDefinedMethodsFromClass(object.constructor);
                    }
                    userDefinedSetters(object) {
                        let allSetters = ([]);
                        {
                            let array133 = this.userDefinedMethodsFromObject(object);
                            for (let index132 = 0; index132 < array133.length; index132++) {
                                let method = array133[index132];
                                {
                                    console.info("method: " + method.name);
                                    if (this.isASetter(method)) {
                                        console.info("method/setter: " + method.name);
                                        /* add */ (allSetters.push(method) > 0);
                                    }
                                }
                            }
                        }
                        return allSetters;
                    }
                    userDefinedGetters(object) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let allGetters = ([]);
                        {
                            let array135 = this.userDefinedMethodsFromObject(object);
                            for (let index134 = 0; index134 < array135.length; index134++) {
                                let method = array135[index134];
                                {
                                    if (this.isAGetter(method)) {
                                        /* add */ (allGetters.push(method) > 0);
                                    }
                                }
                            }
                        }
                        return allGetters;
                    }
                }
                Introspector.instance = null;
                introspection.Introspector = Introspector;
                Introspector["__class"] = "ca.ntro.core.introspection.Introspector";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class AppCloser {
                }
                services.AppCloser = AppCloser;
                AppCloser["__class"] = "ca.ntro.core.services.AppCloser";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class ValueFormatter {
                    static initialize(instance) {
                        ca.ntro.core.system.trace.T.call(ValueFormatter);
                        ValueFormatter.instance = instance;
                    }
                    static setIsHtml(isHtml) {
                        ca.ntro.core.system.trace.T.call(ValueFormatter);
                        ValueFormatter.isHtml = isHtml;
                    }
                    static format(builder, ...values) {
                        ca.ntro.core.system.trace.T.call(ValueFormatter);
                        try {
                            (o => o.formatImpl.apply(o, [builder, ValueFormatter.isHtml].concat(values)))(ValueFormatter.instance);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(ValueFormatter) + " must be initialized");
                        }
                        ;
                    }
                }
                ValueFormatter.instance = null;
                ValueFormatter.isHtml = false;
                services.ValueFormatter = ValueFormatter;
                ValueFormatter["__class"] = "ca.ntro.core.services.ValueFormatter";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class ResourceLoader {
                }
                services.ResourceLoader = ResourceLoader;
                ResourceLoader["__class"] = "ca.ntro.core.services.ResourceLoader";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class Logger {
                    text(text) {
                        console.info(text);
                    }
                }
                services.Logger = Logger;
                Logger["__class"] = "ca.ntro.core.services.Logger";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class NtroCollections {
                    static initialize(instance) {
                        ca.ntro.core.system.trace.T.call(NtroCollections);
                        NtroCollections.instance = instance;
                    }
                    static synchronizedList(elements) {
                        ca.ntro.core.system.trace.T.call(NtroCollections);
                        let synchronizedList = null;
                        try {
                            synchronizedList = NtroCollections.instance.synchronizedListImpl(elements);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(NtroCollections) + " must be initialized");
                        }
                        ;
                        return synchronizedList;
                    }
                    static concurrentHashMap(elements) {
                        ca.ntro.core.system.trace.T.call(NtroCollections);
                        let concurrentHashMap = null;
                        try {
                            concurrentHashMap = NtroCollections.instance.concurrentHashMapImpl(elements);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(NtroCollections) + " must be initialized");
                        }
                        ;
                        return concurrentHashMap;
                    }
                }
                NtroCollections.instance = null;
                services.NtroCollections = NtroCollections;
                NtroCollections["__class"] = "ca.ntro.core.services.NtroCollections";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var regex;
            (function (regex) {
                class RegEx {
                }
                regex.RegEx = RegEx;
                RegEx["__class"] = "ca.ntro.core.regex.RegEx";
            })(regex = core.regex || (core.regex = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var regex;
            (function (regex) {
                class Pattern {
                }
                regex.Pattern = Pattern;
                Pattern["__class"] = "ca.ntro.core.regex.Pattern";
            })(regex = core.regex || (core.regex = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var regex;
            (function (regex) {
                class Matcher {
                }
                regex.Matcher = Matcher;
                Matcher["__class"] = "ca.ntro.core.regex.Matcher";
            })(regex = core.regex || (core.regex = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var mvc;
            (function (mvc) {
                class NtroWindow {
                }
                mvc.NtroWindow = NtroWindow;
                NtroWindow["__class"] = "ca.ntro.core.mvc.NtroWindow";
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class TraceFilterStrings extends ca.ntro.core.system.trace.TraceFilter {
                        constructor(exclude, include) {
                            super();
                            if (this.exclude === undefined)
                                this.exclude = null;
                            if (this.include === undefined)
                                this.include = null;
                            this.exclude = exclude;
                            this.include = include;
                        }
                        /**
                         *
                         * @param {string} cannonicalClassName
                         * @param {string} methodName
                         * @param {string} fileName
                         * @param {number} lineNumber
                         * @return {boolean}
                         */
                        shouldDisplayTrace(cannonicalClassName, methodName, fileName, lineNumber) {
                            let shouldExcludeClassName = false;
                            if (this.exclude != null) {
                                shouldExcludeClassName = (cannonicalClassName.indexOf(this.exclude) != -1);
                            }
                            let shouldIncludeClassName = true;
                            if (this.include != null) {
                                shouldIncludeClassName = (cannonicalClassName.indexOf(this.include) != -1);
                            }
                            return !shouldExcludeClassName && shouldIncludeClassName;
                        }
                    }
                    trace.TraceFilterStrings = TraceFilterStrings;
                    TraceFilterStrings["__class"] = "ca.ntro.core.system.trace.TraceFilterStrings";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class TraceFilterRegEx extends ca.ntro.core.system.trace.TraceFilter {
                        constructor(exclude, include) {
                            super();
                            if (this.exclude === undefined)
                                this.exclude = null;
                            if (this.include === undefined)
                                this.include = null;
                            ca.ntro.core.system.trace.__T.call(this, "<init>");
                            this.exclude = ca.ntro.core.Ntro.regEx().compilePattern(exclude);
                            this.include = ca.ntro.core.Ntro.regEx().compilePattern(include);
                        }
                        /**
                         *
                         * @param {string} cannonicalClassName
                         * @param {string} methodName
                         * @param {string} fileName
                         * @param {number} lineNumber
                         * @return {boolean}
                         */
                        shouldDisplayTrace(cannonicalClassName, methodName, fileName, lineNumber) {
                            let shouldExcludeClassName = this.exclude.matcher(cannonicalClassName).matches();
                            let shouldIncludeClassName = this.include.matcher(cannonicalClassName).matches();
                            return !shouldExcludeClassName && shouldIncludeClassName;
                        }
                    }
                    trace.TraceFilterRegEx = TraceFilterRegEx;
                    TraceFilterRegEx["__class"] = "ca.ntro.core.system.trace.TraceFilterRegEx";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var assertions;
                (function (assertions) {
                    class ContainsExpr extends ca.ntro.core.system.assertions.AssertExpr {
                        constructor(element, collection) {
                            super();
                            if (this.element === undefined)
                                this.element = null;
                            if (this.collection === undefined)
                                this.collection = null;
                            this.element = element;
                            this.collection = collection;
                        }
                        /**
                         *
                         * @return {*}
                         */
                        evaluate() {
                            let result = true;
                            let elementJava = this.element.evaluate();
                            let collectionJava = this.collection.evaluate();
                            return result;
                        }
                    }
                    assertions.ContainsExpr = ContainsExpr;
                    ContainsExpr["__class"] = "ca.ntro.core.system.assertions.ContainsExpr";
                })(assertions = system.assertions || (system.assertions = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var assertions;
                (function (assertions) {
                    class ValueExpr extends ca.ntro.core.system.assertions.AssertExpr {
                        constructor(javaValue) {
                            super();
                            if (this.javaValue === undefined)
                                this.javaValue = null;
                            this.javaValue = javaValue;
                        }
                        contains(o) {
                            let arg = null;
                            if (o != null && o instanceof ca.ntro.core.system.assertions.AssertExpr) {
                                arg = o;
                            }
                            else {
                                arg = assertions.AssertExpr.value(o);
                            }
                            return new ca.ntro.core.system.assertions.ContainsExpr(this, arg);
                        }
                        /**
                         *
                         * @return {*}
                         */
                        evaluate() {
                            return this.javaValue;
                        }
                    }
                    assertions.ValueExpr = ValueExpr;
                    ValueExpr["__class"] = "ca.ntro.core.system.assertions.ValueExpr";
                })(assertions = system.assertions || (system.assertions = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var tasks;
            (function (tasks) {
                class ContainerTask extends ca.ntro.core.tasks.NtroTask {
                    /**
                     *
                     */
                    runTask() {
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                tasks.ContainerTask = ContainerTask;
                ContainerTask["__class"] = "ca.ntro.core.tasks.ContainerTask";
            })(tasks = core.tasks || (core.tasks = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var tasks;
            (function (tasks) {
                class SyncTask extends ca.ntro.core.tasks.NtroTask {
                    constructor() {
                        super();
                        if (this.returnValue === undefined)
                            this.returnValue = null;
                    }
                    getReturnValue() {
                        return this.returnValue;
                    }
                    /**
                     *
                     */
                    runTask() {
                        this.returnValue = this.runSyncTask();
                        this.notifyTaskFinished();
                    }
                }
                tasks.SyncTask = SyncTask;
                SyncTask["__class"] = "ca.ntro.core.tasks.SyncTask";
            })(tasks = core.tasks || (core.tasks = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var services;
            (function (services) {
                class ResourceLoaderTask extends ca.ntro.core.tasks.NtroTask {
                    constructor(resourcePath) {
                        super();
                        if (this.resourcePath === undefined)
                            this.resourcePath = null;
                        this.resourcePath = resourcePath;
                    }
                    getResourcePath() {
                        return this.resourcePath;
                    }
                }
                services.ResourceLoaderTask = ResourceLoaderTask;
                ResourceLoaderTask["__class"] = "ca.ntro.core.services.ResourceLoaderTask";
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var mvc;
            (function (mvc) {
                var view;
                (function (view) {
                    class ViewLoader extends ca.ntro.core.tasks.NtroTask {
                        constructor() {
                            super();
                            if (this.view === undefined)
                                this.view = null;
                        }
                        getView() {
                            return this.view;
                        }
                    }
                    view.ViewLoader = ViewLoader;
                    ViewLoader["__class"] = "ca.ntro.core.mvc.view.ViewLoader";
                })(view = mvc.view || (mvc.view = {}));
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class MethodSignature extends ca.ntro.core.introspection.ProcedureSignature {
                    constructor(name, argumentTypes, returnType, modifiers) {
                        super(name, argumentTypes, modifiers);
                        if (this.returnType === undefined)
                            this.returnType = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.returnType = returnType;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    toString() {
                        ca.ntro.core.system.trace.T.call(this);
                        let builder = { str: "", toString: function () { return this.str; } };
                        this.formatModifiers(builder);
                        /* append */ (sb => { sb.str = sb.str.concat(this.returnType.toString()); return sb; })(builder);
                        this.formatName(builder);
                        this.formatArguments(builder);
                        return builder.str;
                    }
                    static fromString(signatureString) {
                        ca.ntro.core.system.trace.T.call(MethodSignature);
                        let modifiers = ([]);
                        let splitName = signatureString.split("\\(");
                        let modifiersReturnTypeName = splitName[0];
                        let modifiersReturnTypeNameSplit = modifiersReturnTypeName.split(" ");
                        for (let i = 0; i < modifiersReturnTypeNameSplit.length - 2; i++) {
                            {
                                /* add */ (modifiers.push(modifiersReturnTypeNameSplit[i]) > 0);
                            }
                            ;
                        }
                        let returnType = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 2].split(" ").join("");
                        let name = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 1].split(" ").join("");
                        let args = splitName[1];
                        args = args.split(")").join("");
                        let argTypes = args.split(", ");
                        let argumentTypes = ([]);
                        for (let index136 = 0; index136 < argTypes.length; index136++) {
                            let argType = argTypes[index136];
                            {
                                /* add */ (argumentTypes.push(/* replace */ argType.split(" ").join("")) > 0);
                            }
                        }
                        return new MethodSignature(name, argumentTypes, returnType, modifiers);
                    }
                    getReturnType() {
                        return this.returnType;
                    }
                    setReturnType(returnType) {
                        this.returnType = returnType;
                    }
                }
                MethodSignature.serialVersionUID = -375163358194142628;
                introspection.MethodSignature = MethodSignature;
                MethodSignature["__class"] = "ca.ntro.core.introspection.MethodSignature";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var introspection;
            (function (introspection) {
                class ConstructorSignature extends ca.ntro.core.introspection.ProcedureSignature {
                    constructor(name, argumentTypes, modifiers) {
                        super(name, argumentTypes, modifiers);
                        ca.ntro.core.system.trace.T.call(this);
                    }
                    static fromString(signatureString) {
                        ca.ntro.core.system.trace.T.call(ConstructorSignature);
                        let modifiers = ([]);
                        let splitName = signatureString.split("\\(");
                        let modifiersReturnTypeName = splitName[0];
                        let modifiersReturnTypeNameSplit = modifiersReturnTypeName.split(" ");
                        for (let i = 0; i < modifiersReturnTypeNameSplit.length - 1; i++) {
                            {
                                /* add */ (modifiers.push(modifiersReturnTypeNameSplit[i]) > 0);
                            }
                            ;
                        }
                        let name = modifiersReturnTypeNameSplit[modifiersReturnTypeNameSplit.length - 1].split(" ").join("");
                        let args = splitName[1];
                        args = args.split(")").join("");
                        let argTypes = args.split(", ");
                        let argumentTypes = ([]);
                        for (let index137 = 0; index137 < argTypes.length; index137++) {
                            let argType = argTypes[index137];
                            {
                                /* add */ (argumentTypes.push(/* replace */ argType.split(" ").join("")) > 0);
                            }
                        }
                        return new ConstructorSignature(name, argumentTypes, modifiers);
                    }
                }
                ConstructorSignature.serialVersionUID = -5864624951077473662;
                introspection.ConstructorSignature = ConstructorSignature;
                ConstructorSignature["__class"] = "ca.ntro.core.introspection.ConstructorSignature";
            })(introspection = core.introspection || (core.introspection = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var web;
            (function (web) {
                class NtroWindowWeb extends ca.ntro.core.mvc.NtroWindow {
                    constructor() {
                        super();
                        if (this.document === undefined)
                            this.document = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.document = this.getDocument();
                    }
                    writeHtml(out) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.document.writeHtml(out);
                    }
                    installRootView$ca_ntro_core_mvc_view_ViewLoaderWeb(viewLoader) {
                        ca.ntro.core.system.trace.T.call(this);
                    }
                    /**
                     *
                     * @param {ca.ntro.core.mvc.view.ViewLoaderWeb} viewLoader
                     */
                    installRootView(viewLoader) {
                        if (((viewLoader != null && viewLoader instanceof ca.ntro.core.mvc.view.ViewLoaderWeb) || viewLoader === null)) {
                            return this.installRootView$ca_ntro_core_mvc_view_ViewLoaderWeb(viewLoader);
                        }
                        else if (((viewLoader != null) || viewLoader === null)) {
                            throw new Error('cannot invoke abstract overloaded method... check your argument(s) type(s)');
                        }
                        else
                            throw new Error('invalid overload');
                    }
                }
                web.NtroWindowWeb = NtroWindowWeb;
                NtroWindowWeb["__class"] = "ca.ntro.core.web.NtroWindowWeb";
            })(web = core.web || (core.web = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class TraceLevel {
                        static NONE_$LI$() { if (TraceLevel.NONE == null)
                            TraceLevel.NONE = new ca.ntro.core.system.trace.TraceFilterStrings("", null); return TraceLevel.NONE; }
                        ;
                        static APP_$LI$() { if (TraceLevel.APP == null)
                            TraceLevel.APP = new ca.ntro.core.system.trace.TraceFilterStrings("ca.ntro", ""); return TraceLevel.APP; }
                        ;
                        static NTRO_$LI$() { if (TraceLevel.NTRO == null)
                            TraceLevel.NTRO = new ca.ntro.core.system.trace.TraceFilterStrings("ca.ntro.core.trace", "ca.ntro"); return TraceLevel.NTRO; }
                        ;
                        static NTRO_ALL_$LI$() { if (TraceLevel.NTRO_ALL == null)
                            TraceLevel.NTRO_ALL = new ca.ntro.core.system.trace.TraceFilterStrings(null, "ca.ntro"); return TraceLevel.NTRO_ALL; }
                        ;
                        static ALL_$LI$() { if (TraceLevel.ALL == null)
                            TraceLevel.ALL = new ca.ntro.core.system.trace.TraceFilterStrings(null, ""); return TraceLevel.ALL; }
                        ;
                    }
                    trace.TraceLevel = TraceLevel;
                    TraceLevel["__class"] = "ca.ntro.core.system.trace.TraceLevel";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var initialization;
            (function (initialization) {
                class NtroInitializationTask extends ca.ntro.core.tasks.ContainerTask {
                    constructor() {
                        super(...arguments);
                        /*private*/ this.options = ({});
                    }
                    getOption(key) {
                        return ((m, k) => m[k] === undefined ? null : m[k])(this.options, key);
                    }
                    setOptions(options) {
                        return this;
                    }
                }
                initialization.NtroInitializationTask = NtroInitializationTask;
                NtroInitializationTask["__class"] = "ca.ntro.core.initialization.NtroInitializationTask";
            })(initialization = core.initialization || (core.initialization = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var initialization;
            (function (initialization) {
                class InitializationTask extends ca.ntro.core.tasks.SyncTask {
                    /**
                     *
                     * @return {ca.ntro.core.wrappers.options.None}
                     */
                    runSyncTask() {
                        ca.ntro.core.system.trace.__T.call(InitializationTask, "runSyncTask");
                        this.performInitialization();
                        return null;
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    /*private*/ performInitialization() {
                        let introspector = this.provideIntrospector();
                        ca.ntro.core.system.trace.T.__registerIntrospector(introspector);
                        ca.ntro.core.Ntro.__registerIntrospector(introspector);
                        let logger = this.provideLogger();
                        ca.ntro.core.system.trace.T.__registerLogger(logger);
                        ca.ntro.core.Ntro.__registerLogger(logger);
                        ca.ntro.core.Ntro.__registerAppCloser(this.provideAppCloser());
                        ca.ntro.core.Ntro.__registerRegEx(this.provideRegEx());
                        let stackAnalyzer = this.provideStackAnalyzer();
                        ca.ntro.core.system.trace.T.__registerStackAnalyzer(stackAnalyzer);
                        ca.ntro.core.__Ntro.registerStackAnalyzer(stackAnalyzer);
                        ca.ntro.core.Ntro.zzz_registerResourceLoader(this.provideResourceLoader());
                        ca.ntro.core.services.ValueFormatter.initialize(this.provideValueFormatter());
                        ca.ntro.core.services.NtroCollections.initialize(this.provideNtroCollections());
                    }
                }
                initialization.InitializationTask = InitializationTask;
                InitializationTask["__class"] = "ca.ntro.core.initialization.InitializationTask";
            })(initialization = core.initialization || (core.initialization = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var mvc;
            (function (mvc) {
                var view;
                (function (view) {
                    class ViewLoaderWeb extends ca.ntro.core.mvc.view.ViewLoader {
                        /**
                         *
                         */
                        runTask() {
                            ca.ntro.core.system.trace.T.call(this);
                            this.notifyTaskFinished();
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                        }
                        setHtmlUrl(string) {
                            return this;
                        }
                        setCssUrl(string) {
                            return this;
                        }
                        setTranslationsUrl(string) {
                            return this;
                        }
                    }
                    view.ViewLoaderWeb = ViewLoaderWeb;
                    ViewLoaderWeb["__class"] = "ca.ntro.core.mvc.view.ViewLoaderWeb";
                })(view = mvc.view || (mvc.view = {}));
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class __T {
                        static traceFilter_$LI$() { if (__T.traceFilter == null)
                            __T.traceFilter = ca.ntro.core.system.trace.TraceLevel.APP_$LI$(); return __T.traceFilter; }
                        ;
                        static setTraceLevel(traceFilter) {
                            __T.traceFilter = traceFilter;
                        }
                        static call(called, methodName) {
                            let calledClass = ca.ntro.core.system.trace.T.getCalledClass(called);
                            if (__T.traceFilter_$LI$() != null && __T.traceFilter_$LI$().shouldDisplayTrace(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(calledClass), "", "", 0)) {
                                console.info("#T.call | " + (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(calledClass) + "." + methodName);
                            }
                        }
                    }
                    trace.__T = __T;
                    __T["__class"] = "ca.ntro.core.system.trace.__T";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var system;
            (function (system) {
                var trace;
                (function (trace) {
                    class T {
                        static previousFilter_$LI$() { if (T.previousFilter == null)
                            T.previousFilter = ca.ntro.core.system.trace.TraceLevel.APP_$LI$(); return T.previousFilter; }
                        ;
                        static traceFilter_$LI$() { if (T.traceFilter == null)
                            T.traceFilter = ca.ntro.core.system.trace.TraceLevel.APP_$LI$(); return T.traceFilter; }
                        ;
                        static __registerIntrospector(introspector) {
                            T.introspector = introspector;
                        }
                        static __registerStackAnalyzer(stackAnalyzer) {
                            ca.ntro.core.system.trace.__T.call(T, "registerStackAnalyzer");
                            T.stackAnalyzer = stackAnalyzer;
                        }
                        static __registerLogger(logger) {
                            ca.ntro.core.system.trace.__T.call(T, "registerLogger");
                            T.logger = logger;
                        }
                        static setTraceLevel(traceFilter) {
                            T.previousFilter = T.traceFilter;
                            T.traceFilter = traceFilter;
                            ca.ntro.core.system.trace.__T.setTraceLevel(traceFilter);
                        }
                        static revertToPreviousLevel() {
                            T.traceFilter = T.previousFilter;
                            ca.ntro.core.system.trace.__T.setTraceLevel(T.previousFilter_$LI$());
                        }
                        static getCalledClass(called) {
                            let calledClass = called.constructor;
                            if (((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(T.constructor), "Function")) {
                                if (((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(calledClass), "Function")) {
                                    calledClass = called;
                                }
                            }
                            else if (called != null) {
                                calledClass = called;
                            }
                            return calledClass;
                        }
                        static call(called) {
                            let stackOffset = 1;
                            T.traceCall(T.getCalledClass(called), stackOffset);
                        }
                        /*private*/ static traceCall(calledClass, stackOffset) {
                            stackOffset++;
                            if (T.traceFilter_$LI$().shouldDisplayTrace(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(calledClass), "", "", 0)) {
                                let className = (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(calledClass);
                                try {
                                    let tracedFrame = T.stackAnalyzer.getTracedFrame(className, stackOffset);
                                    T.printTrace(tracedFrame);
                                }
                                catch (e) {
                                    console.info("#T.call (" + className + ".java)");
                                }
                                ;
                            }
                        }
                        /*private*/ static printTrace(tracedFrame) {
                            let builder = { str: "", toString: function () { return this.str; } };
                            /* append */ (sb => { sb.str = sb.str.concat("#T.call"); return sb; })(builder);
                            tracedFrame.printFrame(builder);
                            try {
                                T.logger.text(/* toString */ builder.str);
                            }
                            catch (e) {
                                console.info(/* toString */ builder.str);
                            }
                            ;
                        }
                        static here() {
                            ca.ntro.core.system.trace.__T.call(T, "here");
                            let stackOffset = 1;
                            try {
                                let tracedFrame = T.stackAnalyzer.getTracedFrame(null, stackOffset);
                                let builder = { str: "", toString: function () { return this.str; } };
                                /* append */ (sb => { sb.str = sb.str.concat("#T.here"); return sb; })(builder);
                                tracedFrame.printSourceLocation(builder);
                                T.logger.text(/* toString */ builder.str);
                            }
                            catch (e) {
                                try {
                                    T.logger.text("#T.here");
                                }
                                catch (e2) {
                                    console.info("#T.here");
                                }
                                ;
                            }
                            ;
                        }
                        static values(...values) {
                            ca.ntro.core.system.trace.__T.call(T, "values");
                            let stackOffset = 1;
                            let prefix = { str: "", toString: function () { return this.str; } };
                            /* append */ (sb => { sb.str = sb.str.concat("#T.vals"); return sb; })(prefix);
                            try {
                                let tracedFrame = T.stackAnalyzer.getTracedFrame(null, stackOffset);
                                tracedFrame.printSourceLocation(prefix);
                            }
                            catch (e) {
                            }
                            ;
                            /* append */ (sb => { sb.str = sb.str.concat(" >> "); return sb; })(prefix);
                            /* append */ (sb => { sb.str = sb.str.concat(values.length); return sb; })(prefix);
                            /* append */ (sb => { sb.str = sb.str.concat(" values:"); return sb; })(prefix);
                            try {
                                T.logger.text(/* toString */ prefix.str);
                            }
                            catch (e) {
                                console.info(/* toString */ prefix.str);
                            }
                            ;
                            for (let i = 0; i < values.length; i++) {
                                {
                                    try {
                                        T.logger.value(values[i]);
                                    }
                                    catch (e) {
                                        console.info(values[i]);
                                    }
                                    ;
                                }
                                ;
                            }
                        }
                    }
                    T.introspector = null;
                    T.stackAnalyzer = null;
                    T.logger = null;
                    trace.T = T;
                    T["__class"] = "ca.ntro.core.system.trace.T";
                })(trace = system.trace || (system.trace = {}));
            })(system = core.system || (core.system = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
ca.ntro.core.system.trace.T.traceFilter_$LI$();
ca.ntro.core.system.trace.T.previousFilter_$LI$();
ca.ntro.core.system.trace.__T.traceFilter_$LI$();
ca.ntro.core.system.trace.TraceLevel.ALL_$LI$();
ca.ntro.core.system.trace.TraceLevel.NTRO_ALL_$LI$();
ca.ntro.core.system.trace.TraceLevel.NTRO_$LI$();
ca.ntro.core.system.trace.TraceLevel.APP_$LI$();
ca.ntro.core.system.trace.TraceLevel.NONE_$LI$();
//# sourceMappingURL=bundle.js.map
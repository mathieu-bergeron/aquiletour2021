/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
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
                        for (let index121 = 0; index121 < this.modifiers.length; index121++) {
                            let modifier = this.modifiers[index121];
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
                        for (let index122 = 0; index122 < this.modifiers.length; index122++) {
                            let modifier = this.modifiers[index122];
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
                            ca.ntro.core.system.log.Log.fatalError("FATAL cannot instantiate " + ca.ntro.core.Ntro.introspector().getSimpleNameForClass(instanceType), e);
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
                    constructor() {
                        /*private*/ this.serializableClasses = ({});
                    }
                    registerSerializableClass(_class) {
                        /* put */ (this.serializableClasses[this.getSimpleNameForClass(_class)] = _class);
                    }
                    getSerializableClass(className) {
                        return ((m, k) => m[k] === undefined ? null : m[k])(this.serializableClasses, className);
                    }
                    findMethodBySignature(currentClass, methodSignature) {
                        ca.ntro.core.system.trace.T.call(Introspector);
                        let result = null;
                        {
                            let array124 = this.userDefinedMethodsFromClass(currentClass);
                            for (let index123 = 0; index123 < array124.length; index123++) {
                                let candidate = array124[index123];
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
                            let array126 = this.userDefinedMethodsFromClass(_class);
                            for (let index125 = 0; index125 < array126.length; index125++) {
                                let method = array126[index125];
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
                        if (this.serializableClasses.hasOwnProperty(className)) {
                            _class = ((m, k) => m[k] === undefined ? null : m[k])(this.serializableClasses, className);
                        }
                        else {
                            try {
                                _class = eval(className);
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError("Cannot find class " + className, e);
                            }
                            ;
                        }
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
                    appointmentDefinedSetters(object) {
                        let allSetters = ([]);
                        {
                            let array128 = this.userDefinedMethodsFromObject(object);
                            for (let index127 = 0; index127 < array128.length; index127++) {
                                let method = array128[index127];
                                {
                                    if (this.isASetter(method)) {
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
                            let array130 = this.userDefinedMethodsFromObject(object);
                            for (let index129 = 0; index129 < array130.length; index129++) {
                                let method = array130[index129];
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
            var tasks;
            (function (tasks) {
                class TaskWrapperImpl {
                    constructor(task) {
                        if (this.task === undefined)
                            this.task = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.task = task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                }
                tasks.TaskWrapperImpl = TaskWrapperImpl;
                TaskWrapperImpl["__class"] = "ca.ntro.core.tasks.TaskWrapperImpl";
                TaskWrapperImpl["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
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
                class GraphDescriptionImpl {
                    constructor() {
                        /*private*/ this.nodes = ([]);
                        /*private*/ this.edges = ([]);
                    }
                    /**
                     *
                     * @param {*} node
                     */
                    addNode(node) {
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.nodes, node);
                    }
                    /**
                     *
                     * @param {*} from
                     * @param {*} to
                     */
                    addEdge(from, to) {
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.edges, new ca.ntro.core.tasks.EdgeDescriptionImpl(from, to));
                    }
                    /*private*/ writeNode(writer, visitedNodes, node) {
                        if ((visitedNodes.indexOf((node)) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, node);
                        if (node.getParentNode() != null) {
                            this.writeNode(writer, visitedNodes, node.getParentNode());
                        }
                        node.writeNode(writer);
                    }
                    /**
                     *
                     * @param {*} writer
                     */
                    write(writer) {
                        let visitedNodes = ([]);
                        for (let index131 = 0; index131 < this.nodes.length; index131++) {
                            let node = this.nodes[index131];
                            {
                                this.writeNode(writer, visitedNodes, node);
                            }
                        }
                        this.edges.forEach((e) => e.write(writer));
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return 0;
                    }
                    /**
                     *
                     * @param {*} other
                     * @return {boolean}
                     */
                    equals(other) {
                        if (this === other)
                            return true;
                        if (other == null)
                            return false;
                        if (other != null && other instanceof ca.ntro.core.tasks.GraphDescriptionImpl) {
                            let otherGraph = other;
                            return ((a1, a2) => { if (a1 == null && a2 == null)
                                return true; if (a1 == null || a2 == null)
                                return false; if (a1.length != a2.length)
                                return false; for (let i = 0; i < a1.length; i++) {
                                if (a1[i] != a2[i])
                                    return false;
                            } return true; })(this.nodes, otherGraph.nodes) && ((a1, a2) => { if (a1 == null && a2 == null)
                                return true; if (a1 == null || a2 == null)
                                return false; if (a1.length != a2.length)
                                return false; for (let i = 0; i < a1.length; i++) {
                                if (a1[i] != a2[i])
                                    return false;
                            } return true; })(this.edges, otherGraph.edges);
                        }
                        return false;
                    }
                }
                tasks.GraphDescriptionImpl = GraphDescriptionImpl;
                GraphDescriptionImpl["__class"] = "ca.ntro.core.tasks.GraphDescriptionImpl";
                GraphDescriptionImpl["__interfaces"] = ["ca.ntro.core.tasks.GraphDescription"];
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
                class GraphTraceImpl {
                    constructor() {
                        /*private*/ this.listeners = ([]);
                        /*private*/ this.writers = ([]);
                        /*private*/ this.graphs = ([]);
                        /*private*/ this.tasks = ([]);
                    }
                    /**
                     *
                     * @param {*} writer
                     */
                    addGraphWriter(writer) {
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.writers, writer);
                        for (let i = 0; i < this.graphs.length; i++) {
                            {
                                writer.write(i, /* get */ this.graphs[i]);
                            }
                            ;
                        }
                    }
                    /**
                     *
                     * @param {*} graph
                     * @param {*} task
                     * @return {boolean}
                     */
                    append(graph, task) {
                        if (task.getState() === ca.ntro.core.tasks.TaskState.DONE || this.graphs.length === 0) {
                        }
                        else {
                            return false;
                        }
                        let cycleDetected = false;
                        if ((this.graphs.indexOf((graph)) >= 0)) {
                            cycleDetected = true;
                        }
                        for (let index132 = 0; index132 < this.writers.length; index132++) {
                            let writer = this.writers[index132];
                            {
                                writer.write(/* size */ this.graphs.length, graph);
                            }
                        }
                        for (let index133 = 0; index133 < this.listeners.length; index133++) {
                            let listener = this.listeners[index133];
                            {
                                listener.onNewTaskState(task);
                            }
                        }
                        /* add */ (this.tasks.push(task) > 0);
                        /* add */ (this.graphs.push(graph) > 0);
                        return cycleDetected;
                    }
                    /**
                     *
                     * @param {*} listener
                     */
                    addTaskStateListener(listener) {
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(this.listeners, listener);
                        for (let index134 = 0; index134 < this.tasks.length; index134++) {
                            let taskStateDescription = this.tasks[index134];
                            {
                                listener.onNewTaskState(taskStateDescription);
                            }
                        }
                    }
                }
                tasks.GraphTraceImpl = GraphTraceImpl;
                GraphTraceImpl["__class"] = "ca.ntro.core.tasks.GraphTraceImpl";
                GraphTraceImpl["__interfaces"] = ["ca.ntro.core.tasks.GraphTraceConnector", "ca.ntro.core.tasks.GraphTrace"];
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
                class GraphWriterMemory {
                    constructor() {
                    }
                    /**
                     *
                     * @param {*} cluster
                     */
                    addRootCluster(cluster) {
                    }
                    /**
                     *
                     * @param {*} node
                     */
                    addRootNode(node) {
                    }
                    /**
                     *
                     * @param {*} cluster
                     * @param {*} subCluster
                     */
                    addSubCluster(cluster, subCluster) {
                    }
                    /**
                     *
                     * @param {*} cluster
                     * @param {*} subNode
                     */
                    addSubNode(cluster, subNode) {
                    }
                    /**
                     *
                     * @param {*} from
                     * @param {*} to
                     */
                    addEdge(from, to) {
                    }
                }
                tasks.GraphWriterMemory = GraphWriterMemory;
                GraphWriterMemory["__class"] = "ca.ntro.core.tasks.GraphWriterMemory";
                GraphWriterMemory["__interfaces"] = ["ca.ntro.core.tasks.GraphWriter"];
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
                class NodeDescriptionImpl {
                    constructor(id, label, isRoot, isCluster, isStartNode, parentNode) {
                        if (this.id === undefined)
                            this.id = null;
                        if (this.label === undefined)
                            this.label = null;
                        if (this.__isRoot === undefined)
                            this.__isRoot = false;
                        if (this.__isCluster === undefined)
                            this.__isCluster = false;
                        if (this.__isStartNode === undefined)
                            this.__isStartNode = false;
                        if (this.parentNode === undefined)
                            this.parentNode = null;
                        this.id = id;
                        this.label = label;
                        this.__isRoot = isRoot;
                        this.__isCluster = isCluster;
                        this.__isStartNode = isStartNode;
                        this.parentNode = parentNode;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getNodeId() {
                        return this.id;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getLabel() {
                        return this.label;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRoot() {
                        return this.__isRoot;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isNode() {
                        return !this.__isCluster;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isCluster() {
                        return this.__isCluster;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRootNode() {
                        return this.isRoot() && this.isNode();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRootCluster() {
                        return this.isRoot() && this.isCluster();
                    }
                    /*private*/ hasParent() {
                        return this.getParentNode() != null;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isSubNode() {
                        return this.hasParent() && this.isNode();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isSubCluster() {
                        return this.hasParent() && this.isCluster();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isStartNode() {
                        return this.__isStartNode;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getParentNode() {
                        return this.parentNode;
                    }
                    /**
                     *
                     * @param {*} writer
                     */
                    writeNode(writer) {
                        if (this.isRootNode()) {
                            writer.addRootNode(this);
                        }
                        else if (this.isRootCluster()) {
                            writer.addRootCluster(this);
                        }
                        else if (this.isSubNode()) {
                            writer.addSubNode(this.getParentNode(), this);
                        }
                        else if (this.isSubCluster()) {
                            writer.addSubCluster(this.getParentNode(), this);
                        }
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return 0;
                    }
                    /**
                     *
                     * @param {*} other
                     * @return {boolean}
                     */
                    equals(other) {
                        if (this === other)
                            return true;
                        if (other == null)
                            return false;
                        if (other != null && other instanceof ca.ntro.core.tasks.NodeDescriptionImpl) {
                            let otherNode = other;
                            let parentEqual = false;
                            if (this.parentNode == null && otherNode.parentNode == null) {
                                parentEqual = true;
                            }
                            else if (this.parentNode != null) {
                                parentEqual = ((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(this.parentNode, otherNode.parentNode);
                            }
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.id, otherNode.id) && ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.label, otherNode.label) && parentEqual;
                        }
                        return false;
                    }
                }
                tasks.NodeDescriptionImpl = NodeDescriptionImpl;
                NodeDescriptionImpl["__class"] = "ca.ntro.core.tasks.NodeDescriptionImpl";
                NodeDescriptionImpl["__interfaces"] = ["ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable"];
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
                class NtroTaskImpl {
                    constructor(taskId) {
                        /*private*/ this.state = ca.ntro.core.tasks.TaskState.INIT;
                        /*private*/ this.previousTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                        /*private*/ this.subTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                        /*private*/ this.nextTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                        if (((typeof taskId === 'string') || taskId === null)) {
                            let __args = arguments;
                            if (this.taskId === undefined)
                                this.taskId = null;
                            if (this.parentTask === undefined)
                                this.parentTask = null;
                            if (this.trace === undefined)
                                this.trace = null;
                            this.state = ca.ntro.core.tasks.TaskState.INIT;
                            this.previousTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            this.subTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            this.nextTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            if (this.taskId === undefined)
                                this.taskId = null;
                            if (this.parentTask === undefined)
                                this.parentTask = null;
                            if (this.trace === undefined)
                                this.trace = null;
                            (() => {
                                this.taskId = taskId;
                            })();
                        }
                        else if (taskId === undefined) {
                            let __args = arguments;
                            if (this.taskId === undefined)
                                this.taskId = null;
                            if (this.parentTask === undefined)
                                this.parentTask = null;
                            if (this.trace === undefined)
                                this.trace = null;
                            this.state = ca.ntro.core.tasks.TaskState.INIT;
                            this.previousTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            this.subTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            this.nextTasks = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            if (this.taskId === undefined)
                                this.taskId = null;
                            if (this.parentTask === undefined)
                                this.parentTask = null;
                            if (this.trace === undefined)
                                this.trace = null;
                            (() => {
                                this.taskId = (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(this.constructor);
                            })();
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    static classIds_$LI$() { if (NtroTaskImpl.classIds == null)
                        NtroTaskImpl.classIds = ({}); return NtroTaskImpl.classIds; }
                    ;
                    /**
                     *
                     * @return {string}
                     */
                    getTaskId() {
                        return this.taskId;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getLabel() {
                        let state = "WAIT";
                        if (this.state === ca.ntro.core.tasks.TaskState.DONE) {
                            state = ca.ntro.core.tasks.TaskState[this.state];
                        }
                        return this.taskId + "\n" + state;
                    }
                    /**
                     *
                     * @param {string} taskId
                     */
                    setTaskId(taskId) {
                        this.taskId = taskId;
                    }
                    /**
                     *
                     * @param {*} parentTask
                     */
                    setParentTask(parentTask) {
                        this.parentTask = parentTask;
                    }
                    getParentTask() {
                        return this.parentTask;
                    }
                    addSubTask$ca_ntro_core_tasks_NtroTask(task) {
                        task.setParentTask(this);
                        this.forEachNode({ execute: (n) => {
                                if (((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(n.getNodeId(), task.asNode().getNodeId())) {
                                    throw Object.defineProperty(new Error("Adding task " + task.getTaskId() + " would result in non-unique nodeId " + n.getNodeId()), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                                }
                            } });
                        if (this.subTasks.hasOwnProperty(task.getTaskId())) {
                            throw Object.defineProperty(new Error("Adding task " + task.getTaskId() + " would result in non-unique subTask"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                        }
                        /* put */ (this.subTasks[task.getTaskId()] = task);
                        return this;
                    }
                    addSubTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId) {
                        task.setTaskId(taskId);
                        this.addSubTask$ca_ntro_core_tasks_NtroTask(task);
                        return this;
                    }
                    /**
                     *
                     * @param {*} task
                     * @param {string} taskId
                     * @return {*}
                     */
                    addSubTask(task, taskId) {
                        if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && ((typeof taskId === 'string') || taskId === null)) {
                            return this.addSubTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId);
                        }
                        else if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && taskId === undefined) {
                            return this.addSubTask$ca_ntro_core_tasks_NtroTask(task);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    getPreviousTask(taskClass, taskId) {
                        let previousTask = ((m, k) => m[k] === undefined ? null : m[k])(this.previousTasks, taskId);
                        return previousTask;
                    }
                    getSubTask(taskClass, taskId) {
                        let subTask = ((m, k) => m[k] === undefined ? null : m[k])(this.subTasks, taskId);
                        return subTask;
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachStartNode(lambda) {
                        this.forEachNode({ execute: (n) => {
                                if (n.isStartNode()) {
                                    lambda.execute(n);
                                }
                            } });
                    }
                    visitAllNodes(visitedNodes, lambda) {
                        if ((visitedNodes.indexOf((this.getNodeId())) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this.getNodeId());
                        lambda.execute(this);
                        if (this.parentTask != null) {
                            this.parentTask.visitAllNodes(visitedNodes, lambda);
                        }
                        this.forEachPreviousTask({ execute: (pt) => pt.visitAllNodes(visitedNodes, lambda) });
                        this.forEachSubTask({ execute: (st) => st.visitAllNodes(visitedNodes, lambda) });
                        this.forEachNextTask({ execute: (nt) => nt.visitAllNodes(visitedNodes, lambda) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachNode(lambda) {
                        let visitedNodes = ([]);
                        this.visitAllNodes(visitedNodes, lambda);
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachSubNode(lambda) {
                        this.forEachSubTask({ execute: (st) => lambda.execute(st.asNode()) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachSubNodeTransitive(lambda) {
                        let visitedNodes = ([]);
                        this.forEachSubTask({ execute: ((visitedNodes) => {
                                return (st) => st.visitSubNodes(visitedNodes, lambda);
                            })(visitedNodes) });
                    }
                    visitSubNodes(visitedNodes, lambda) {
                        if ((visitedNodes.indexOf((this.getNodeId())) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this.getNodeId());
                        lambda.execute(this);
                        this.forEachSubTask({ execute: (st) => st.visitSubNodes(visitedNodes, lambda) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachNextNode(lambda) {
                        this.forEachNextTask({ execute: (nt) => lambda.execute(nt.asNode()) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachNextNodeTransitive(lambda) {
                        let visitedNodes = ([]);
                        this.forEachNextTask({ execute: ((visitedNodes) => {
                                return (st) => st.visitNextNodes(visitedNodes, lambda);
                            })(visitedNodes) });
                    }
                    visitNextNodes(visitedNodes, lambda) {
                        if ((visitedNodes.indexOf((this.getNodeId())) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this.getNodeId());
                        lambda.execute(this);
                        this.forEachNextTask({ execute: (st) => st.visitNextNodes(visitedNodes, lambda) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachReachableNode(lambda) {
                        this.forEachNextNode(lambda);
                        this.forEachSubNode(lambda);
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachReachableNodeTransitive(lambda) {
                        let visitedNodes = ([]);
                        this.forEachSubTask({ execute: ((visitedNodes) => {
                                return (st) => st.visitReachableNodes(visitedNodes, lambda);
                            })(visitedNodes) });
                        this.forEachNextTask({ execute: ((visitedNodes) => {
                                return (nt) => nt.visitReachableNodes(visitedNodes, lambda);
                            })(visitedNodes) });
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getParentNode() {
                        if (this.parentTask != null)
                            return this.parentTask.asNode();
                        return null;
                    }
                    visitReachableNodes(visitedNodes, lambda) {
                        if ((visitedNodes.indexOf((this.getNodeId())) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this.getNodeId());
                        lambda.execute(this);
                        this.forEachSubTask({ execute: (st) => st.visitReachableNodes(visitedNodes, lambda) });
                        this.forEachNextTask({ execute: (nt) => nt.visitReachableNodes(visitedNodes, lambda) });
                    }
                    /**
                     *
                     * @param {*} lambda
                     */
                    forEachEdge(lambda) {
                        this.forEachNode({ execute: (n) => {
                                n.forEachNextNode({ execute: (nn) => {
                                        lambda.execute(n, nn);
                                    } });
                            } });
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isSubCluster() {
                        return !this.isRoot() && this.isCluster();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isCluster() {
                        return this.hasSubTasks();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isSubNode() {
                        return !this.isRoot() && this.isNode();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isNode() {
                        return !this.hasSubTasks();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRootCluster() {
                        return this.isRoot() && this.isCluster();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRootNode() {
                        return this.isRoot() && this.isNode();
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isRoot() {
                        return !this.hasParent();
                    }
                    hasParent() {
                        return this.parentTask != null;
                    }
                    hasPreviousTasks() {
                        return Object.keys(this.previousTasks).length > 0;
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    isStartNode() {
                        return this.isRoot() && !this.hasPreviousTasks();
                    }
                    forEachSubTask(lambda) {
                        {
                            {
                                let array136 = (obj => Object.keys(obj).map(key => obj[key]))(this.subTasks);
                                for (let index135 = 0; index135 < array136.length; index135++) {
                                    let subTask = array136[index135];
                                    {
                                        lambda.execute(subTask);
                                    }
                                }
                            }
                        }
                        ;
                    }
                    forEachPreviousTask(lambda) {
                        {
                            {
                                let array138 = (obj => Object.keys(obj).map(key => obj[key]))(this.previousTasks);
                                for (let index137 = 0; index137 < array138.length; index137++) {
                                    let previousTask = array138[index137];
                                    {
                                        lambda.execute(previousTask);
                                    }
                                }
                            }
                        }
                        ;
                    }
                    forEachNextTask(lambda) {
                        {
                            {
                                let array140 = (obj => Object.keys(obj).map(key => obj[key]))(this.nextTasks);
                                for (let index139 = 0; index139 < array140.length; index139++) {
                                    let nextTask = array140[index139];
                                    {
                                        lambda.execute(nextTask);
                                    }
                                }
                            }
                        }
                        ;
                    }
                    hasSubTasks() {
                        return Object.keys(this.subTasks).length > 0;
                    }
                    addNextTask$ca_ntro_core_tasks_NtroTask(task) {
                        if (!this.haveNextTask(task)) {
                            /* put */ (this.nextTasks[task.getTaskId()] = task);
                            task['addPreviousTask$ca_ntro_core_tasks_NtroTask'](this);
                        }
                        return this;
                    }
                    haveNextTask(task) {
                        return this.nextTasks.hasOwnProperty(task.getTaskId());
                    }
                    addNextTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId) {
                        task.setTaskId(taskId);
                        this.addNextTask$ca_ntro_core_tasks_NtroTask(task);
                        return this;
                    }
                    /**
                     *
                     * @param {*} task
                     * @param {string} taskId
                     * @return {*}
                     */
                    addNextTask(task, taskId) {
                        if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && ((typeof taskId === 'string') || taskId === null)) {
                            return this.addNextTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId);
                        }
                        else if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && taskId === undefined) {
                            return this.addNextTask$ca_ntro_core_tasks_NtroTask(task);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    addPreviousTask$ca_ntro_core_tasks_NtroTask(task) {
                        if (!this.havePreviousTask(task)) {
                            /* put */ (this.previousTasks[task.getTaskId()] = task);
                            task['addNextTask$ca_ntro_core_tasks_NtroTask'](this);
                        }
                        return this;
                    }
                    havePreviousTask(task) {
                        return this.previousTasks.hasOwnProperty(task.getTaskId());
                    }
                    addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId) {
                        task.setTaskId(taskId);
                        this.addPreviousTask$ca_ntro_core_tasks_NtroTask(task);
                        return this;
                    }
                    /**
                     *
                     * @param {*} task
                     * @param {string} taskId
                     * @return {*}
                     */
                    addPreviousTask(task, taskId) {
                        if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && ((typeof taskId === 'string') || taskId === null)) {
                            return this.addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String(task, taskId);
                        }
                        else if (((task != null && (task["__interfaces"] != null && task["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || task.constructor != null && task.constructor["__interfaces"] != null && task.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || task === null) && taskId === undefined) {
                            return this.addPreviousTask$ca_ntro_core_tasks_NtroTask(task);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /**
                     *
                     * @return {*}
                     */
                    asGraph() {
                        return this;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    asNode() {
                        return this;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    asTask() {
                        return this;
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        let subTasks = ([]);
                        this.forEachSubTask({ execute: ((subTasks) => {
                                return (st) => ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(subTasks, st);
                            })(subTasks) });
                        return ((o) => { if (o.hashCode) {
                            return o.hashCode();
                        }
                        else {
                            return o.toString().split('').reduce((prevHash, currVal) => (((prevHash << 5) - prevHash) + currVal.charCodeAt(0)) | 0, 0);
                        } })(this.getTaskId()) + 0;
                    }
                    /**
                     *
                     * @param {*} otherObject
                     * @return {boolean}
                     */
                    equals(otherObject) {
                        if (this === otherObject)
                            return true;
                        if (otherObject == null)
                            return false;
                        if (otherObject != null && otherObject instanceof ca.ntro.core.tasks.NtroTaskImpl) {
                            let otherTask = otherObject;
                            if (!((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.getTaskId(), otherTask.getTaskId()))
                                return false;
                            let mySubTasks = ([]);
                            this.forEachSubTask({ execute: ((mySubTasks) => {
                                    return (st) => ((s, e) => { if (s.indexOf(e) == -1) {
                                        s.push(e);
                                        return true;
                                    }
                                    else {
                                        return false;
                                    } })(mySubTasks, st);
                                })(mySubTasks) });
                            let otherSubTasks = ([]);
                            otherTask.forEachSubTask({ execute: ((otherSubTasks) => {
                                    return (st) => ((s, e) => { if (s.indexOf(e) == -1) {
                                        s.push(e);
                                        return true;
                                    }
                                    else {
                                        return false;
                                    } })(otherSubTasks, st);
                                })(otherSubTasks) });
                            return ((a1, a2) => { if (a1 == null && a2 == null)
                                return true; if (a1 == null || a2 == null)
                                return false; if (a1.length != a2.length)
                                return false; for (let i = 0; i < a1.length; i++) {
                                if (a1[i] != a2[i])
                                    return false;
                            } return true; })(mySubTasks, otherSubTasks);
                        }
                        return false;
                    }
                    /**
                     *
                     * @param {*} otherGraph
                     * @return {boolean}
                     */
                    isSameGraphAs(otherGraph) {
                        let myNodes = ([]);
                        this.asGraph().forEachNode({ execute: ((myNodes) => {
                                return (n) => ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(myNodes, n);
                            })(myNodes) });
                        let otherNodes = ([]);
                        otherGraph.forEachNode({ execute: ((otherNodes) => {
                                return (n) => ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(otherNodes, n);
                            })(otherNodes) });
                        if (!((a1, a2) => { if (a1 == null && a2 == null)
                            return true; if (a1 == null || a2 == null)
                            return false; if (a1.length != a2.length)
                            return false; for (let i = 0; i < a1.length; i++) {
                            if (a1[i] != a2[i])
                                return false;
                        } return true; })(myNodes, otherNodes))
                            return false;
                        let myEdges = ([]);
                        this.asGraph().forEachEdge({ execute: ((myEdges) => {
                                return (from, to) => ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(myEdges, new NtroTaskImpl.Edge(this, from, to));
                            })(myEdges) });
                        let otherEdges = ([]);
                        otherGraph.forEachEdge({ execute: ((otherEdges) => {
                                return (from, to) => ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(otherEdges, new NtroTaskImpl.Edge(this, from, to));
                            })(otherEdges) });
                        return ((a1, a2) => { if (a1 == null && a2 == null)
                            return true; if (a1 == null || a2 == null)
                            return false; if (a1.length != a2.length)
                            return false; for (let i = 0; i < a1.length; i++) {
                            if (a1[i] != a2[i])
                                return false;
                        } return true; })(myEdges, otherEdges);
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getNodeId() {
                        let nodeId;
                        if (this.parentTask != null) {
                            nodeId = this.parentTask.getNodeId() + "_" + this.getTaskId();
                        }
                        else {
                            nodeId = this.getTaskId();
                        }
                        return nodeId;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getNodeDescription() {
                        return new ca.ntro.core.tasks.NodeDescriptionImpl(this.getNodeId(), this.getLabel(), this.isRoot(), this.isCluster(), this.isStartNode(), this.parentTask != null ? this.parentTask.asNode().getNodeDescription() : null);
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getGraphDescription() {
                        let description = new ca.ntro.core.tasks.GraphDescriptionImpl();
                        this.asGraph().forEachNode({ execute: ((description) => {
                                return (n) => description.addNode(n.getNodeDescription());
                            })(description) });
                        this.asGraph().forEachEdge({ execute: ((description) => {
                                return (from, to) => description.addEdge(from.getNodeDescription(), to.getNodeDescription());
                            })(description) });
                        return description;
                    }
                    getTaskStateDescription() {
                        return new ca.ntro.core.tasks.TaskStateDescriptionImpl(this.getNodeId(), this.state);
                    }
                    appendCurrentStateToTrace(trace, task) {
                        return trace.append(this.getGraphDescription(), task.getTaskStateDescription());
                    }
                    execute$() {
                        if (this.trace == null) {
                            this.trace = new ca.ntro.core.tasks.GraphTraceImpl();
                        }
                        this.execute$ca_ntro_core_tasks_GraphTraceImpl(this.trace);
                        return this.trace;
                    }
                    execute$ca_ntro_core_tasks_GraphTraceImpl(trace) {
                        this.appendCurrentStateToTrace(trace, this);
                        let allTasks = ({});
                        this.forEachNode({ execute: ((allTasks) => {
                                return (n) => (allTasks[n.getNodeId()] = n.asTask());
                            })(allTasks) });
                        let ifShouldContinueExecution;
                        do {
                            {
                                ifShouldContinueExecution = false;
                                {
                                    let array142 = (obj => Object.keys(obj).map(key => obj[key]))(allTasks);
                                    for (let index141 = 0; index141 < array142.length; index141++) {
                                        let task = array142[index141];
                                        {
                                            let oldState = task.state;
                                            task.resumeExecutionIfPossible(trace);
                                            let newState = task.state;
                                            let ifTaskChangedState = (oldState !== newState);
                                            if (ifTaskChangedState) {
                                                let cycleDetected = this.appendCurrentStateToTrace(trace, task);
                                                if (cycleDetected) {
                                                    ca.ntro.core.system.log.Log.warning("Cycle detected in task graph at node " + this.getNodeId());
                                                }
                                                ifShouldContinueExecution = true;
                                            }
                                        }
                                    }
                                }
                            }
                        } while ((ifShouldContinueExecution));
                    }
                    execute(trace) {
                        if (((trace != null && trace instanceof ca.ntro.core.tasks.GraphTraceImpl) || trace === null)) {
                            return this.execute$ca_ntro_core_tasks_GraphTraceImpl(trace);
                        }
                        else if (trace === undefined) {
                            return this.execute$();
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    resumeExecutionIfPossible(trace) {
                        this.trace = trace;
                        switch ((this.state)) {
                            case ca.ntro.core.tasks.TaskState.INIT:
                                if (this.isStartNode()) {
                                    this.state = ca.ntro.core.tasks.TaskState.LAUNCHING_ENTRY_TASK;
                                }
                                else if (this.allNextTasksAreWaitingForPreviousTasks() && this.isRoot()) {
                                    this.state = ca.ntro.core.tasks.TaskState.WAITING_FOR_PREVIOUS_TASKS;
                                }
                                else if (this.allNextTasksAreWaitingForPreviousTasks() && this.parentTask.state === ca.ntro.core.tasks.TaskState.WAITING_FOR_SUB_TASKS) {
                                    this.state = ca.ntro.core.tasks.TaskState.WAITING_FOR_PREVIOUS_TASKS;
                                }
                                break;
                            case ca.ntro.core.tasks.TaskState.WAITING_FOR_PREVIOUS_TASKS:
                                if (this.ifAllPreviousTasksAreDone()) {
                                    this.state = ca.ntro.core.tasks.TaskState.LAUNCHING_ENTRY_TASK;
                                }
                                break;
                            case ca.ntro.core.tasks.TaskState.LAUNCHING_ENTRY_TASK:
                                this.state = ca.ntro.core.tasks.TaskState.WAITING_FOR_ENTRY_TASK;
                                this.runEntryTaskAsync();
                                break;
                            case ca.ntro.core.tasks.TaskState.WAITING_FOR_ENTRY_TASK:
                                break;
                            case ca.ntro.core.tasks.TaskState.WAITING_FOR_SUB_TASKS:
                                if (this.ifAllSubTasksAreDone()) {
                                    this.state = ca.ntro.core.tasks.TaskState.LAUNCHING_EXIT_TASK;
                                }
                                break;
                            case ca.ntro.core.tasks.TaskState.LAUNCHING_EXIT_TASK:
                                this.state = ca.ntro.core.tasks.TaskState.WAITING_FOR_EXIT_TASK;
                                this.runExitTaskAsync();
                                break;
                            case ca.ntro.core.tasks.TaskState.WAITING_FOR_EXIT_TASK:
                                break;
                            case ca.ntro.core.tasks.TaskState.DONE:
                                break;
                            case ca.ntro.core.tasks.TaskState.DELETED:
                                break;
                        }
                    }
                    allNextTasksAreWaitingForPreviousTasks() {
                        let allWaiting = true;
                        {
                            {
                                let array144 = (obj => Object.keys(obj).map(key => obj[key]))(this.nextTasks);
                                for (let index143 = 0; index143 < array144.length; index143++) {
                                    let nextTask = array144[index143];
                                    {
                                        if (nextTask.state !== ca.ntro.core.tasks.TaskState.WAITING_FOR_PREVIOUS_TASKS) {
                                            allWaiting = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        ;
                        return allWaiting;
                    }
                    ifAllPreviousTasksAreDone() {
                        let allDone = true;
                        {
                            {
                                let array146 = (obj => Object.keys(obj).map(key => obj[key]))(this.previousTasks);
                                for (let index145 = 0; index145 < array146.length; index145++) {
                                    let previousTask = array146[index145];
                                    {
                                        if (previousTask.state !== ca.ntro.core.tasks.TaskState.DONE) {
                                            allDone = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        ;
                        return allDone;
                    }
                    ifAllSubTasksAreDone() {
                        let allDone = true;
                        {
                            {
                                let array148 = (obj => Object.keys(obj).map(key => obj[key]))(this.subTasks);
                                for (let index147 = 0; index147 < array148.length; index147++) {
                                    let subTask = array148[index147];
                                    {
                                        if (subTask.state !== ca.ntro.core.tasks.TaskState.DONE) {
                                            allDone = false;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        ;
                        return allDone;
                    }
                    /**
                     *
                     */
                    notifyEntryTaskFinished() {
                        if (this.state === ca.ntro.core.tasks.TaskState.WAITING_FOR_ENTRY_TASK) {
                            this.state = ca.ntro.core.tasks.TaskState.WAITING_FOR_SUB_TASKS;
                        }
                    }
                    /**
                     *
                     */
                    notifyExitTaskFinished() {
                        if (this.state === ca.ntro.core.tasks.TaskState.WAITING_FOR_EXIT_TASK) {
                            this.state = ca.ntro.core.tasks.TaskState.DONE;
                            this.notifyTaskFinished();
                            this.execute();
                        }
                    }
                    notifyTaskFinished() {
                        if (this.parentTask != null) {
                            this.parentTask.onSomeSubTaskFinished(this.getTaskId(), this);
                        }
                        this.forEachNextTask({ execute: (nt) => nt.onSomePreviousTaskFinished(this.getTaskId(), this) });
                    }
                    /**
                     *
                     */
                    resetGraph() {
                        this.forEachStartNode({ execute: (sn) => sn.resetNodeTransitive() });
                    }
                    /**
                     *
                     */
                    resetNodeTransitive() {
                        this.asTask().resetTask();
                        this.forEachReachableNodeTransitive({ execute: (n) => n.asTask().resetTask() });
                    }
                    /**
                     *
                     */
                    resetTask() {
                        this.state = ca.ntro.core.tasks.TaskState.INIT;
                    }
                    /**
                     *
                     * @param {*} replacementTask
                     */
                    replaceWith(replacementTask) {
                        if (!((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.taskId, replacementTask.taskId)) {
                            throw Object.defineProperty(new Error("replacementTask must have same taskId as replaced task"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.IllegalArgumentException', 'java.lang.Exception'] });
                        }
                        let shouldExecuteReplacement = (this.state !== ca.ntro.core.tasks.TaskState.INIT);
                        let trace = this.trace;
                        replacementTask.resetTask();
                        this.deleteSubTasksTransitive();
                        if (this.parentTask != null) {
                            this.parentTask.replaceSubTaskWith(this.getTaskId(), replacementTask);
                            replacementTask.setParentTask(this.parentTask);
                        }
                        let finishedPreviousTasks = ([]);
                        this.forEachPreviousTask({ execute: ((finishedPreviousTasks) => {
                                return (pt) => {
                                    pt.replaceNextTaskWith(this.getTaskId(), replacementTask);
                                    replacementTask['addPreviousTask$ca_ntro_core_tasks_NtroTask$java_lang_String'](pt, pt.getTaskId());
                                    if (pt.state === ca.ntro.core.tasks.TaskState.DONE) {
                                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                                            s.push(e);
                                            return true;
                                        }
                                        else {
                                            return false;
                                        } })(finishedPreviousTasks, pt);
                                    }
                                };
                            })(finishedPreviousTasks) });
                        this.forEachNextTask({ execute: (nt) => {
                                nt.replacePreviousTaskWith(this.getTaskId(), replacementTask);
                                nt.state = ca.ntro.core.tasks.TaskState.INIT;
                                replacementTask['addNextTask$ca_ntro_core_tasks_NtroTask$java_lang_String'](nt, nt.getTaskId());
                            } });
                        this.deleteTask();
                        replacementTask.forEachNodeUpAndForward({ execute: (n) => n.updateState() });
                        if (shouldExecuteReplacement) {
                            finishedPreviousTasks.forEach((ft) => replacementTask.onSomePreviousTaskFinished(ft.getTaskId(), ft));
                            replacementTask.execute$ca_ntro_core_tasks_GraphTraceImpl(trace);
                        }
                    }
                    forEachNodeUpAndForward(lambda) {
                        let visitedNodes = ([]);
                        this.visitNodesUpAndForward(visitedNodes, lambda);
                    }
                    visitNodesUpAndForward(visitedNodes, lambda) {
                        if ((visitedNodes.indexOf((this.getNodeId())) >= 0))
                            return;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this.getNodeId());
                        lambda.execute(this);
                        if (this.parentTask != null) {
                            this.parentTask.visitNodesUpAndForward(visitedNodes, lambda);
                        }
                        this.forEachNextTask({ execute: (nt) => nt.visitNodesUpAndForward(visitedNodes, lambda) });
                    }
                    deleteSubTasksTransitive() {
                        this.forEachSubTask({ execute: (st) => {
                                st.forEachPreviousTask({ execute: (pt) => pt.deleteNextTask(st.getTaskId()) });
                                st.forEachNextTask({ execute: (nt) => nt.deletePreviousTask(st.getTaskId()) });
                                st.deleteSubTasksTransitive();
                            } });
                    }
                    deleteTask() {
                        this.parentTask = null;
                        this.subTasks = null;
                        this.nextTasks = null;
                        this.previousTasks = null;
                        this.trace = null;
                        this.state = ca.ntro.core.tasks.TaskState.DELETED;
                    }
                    updateState() {
                        if (this.hasPreviousTasks() && !this.ifAllPreviousTasksAreDone()) {
                            this.state = ca.ntro.core.tasks.TaskState.INIT;
                        }
                        else if (this.hasSubTasks() && !this.ifAllSubTasksAreDone()) {
                            this.state = ca.ntro.core.tasks.TaskState.INIT;
                        }
                    }
                    replaceSubTaskWith(id, replacementTask) {
                        /* put */ (this.subTasks[id] = replacementTask);
                    }
                    replaceNextTaskWith(id, replacementTask) {
                        /* put */ (this.nextTasks[id] = replacementTask);
                    }
                    replacePreviousTaskWith(id, replacementTask) {
                        /* put */ (this.previousTasks[id] = replacementTask);
                    }
                    deletePreviousTask(id) {
                        /* remove */ (map => { let deleted = this.previousTasks[id]; delete this.previousTasks[id]; return deleted; })(this.previousTasks);
                    }
                    deleteNextTask(id) {
                        /* remove */ (map => { let deleted = this.nextTasks[id]; delete this.nextTasks[id]; return deleted; })(this.nextTasks);
                    }
                    findNodeById$java_lang_String(id) {
                        let visitedNodes = ([]);
                        return this.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                    }
                    findNodeById$java_lang_String$java_util_Set(id, visitedNodes) {
                        if ((visitedNodes.indexOf((this)) >= 0))
                            return null;
                        /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                            s.push(e);
                            return true;
                        }
                        else {
                            return false;
                        } })(visitedNodes, this);
                        let foundNode = null;
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.getTaskId(), id)) {
                            foundNode = this;
                        }
                        if (foundNode == null && this.parentTask != null) {
                            foundNode = this.parentTask.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                        }
                        if (foundNode == null) {
                            {
                                {
                                    let array150 = (obj => Object.keys(obj).map(key => obj[key]))(this.previousTasks);
                                    for (let index149 = 0; index149 < array150.length; index149++) {
                                        let previousTask = array150[index149];
                                        {
                                            foundNode = previousTask.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                                            if (foundNode != null)
                                                break;
                                        }
                                    }
                                }
                            }
                            ;
                        }
                        if (foundNode == null) {
                            {
                                {
                                    let array152 = (obj => Object.keys(obj).map(key => obj[key]))(this.subTasks);
                                    for (let index151 = 0; index151 < array152.length; index151++) {
                                        let subTask = array152[index151];
                                        {
                                            foundNode = subTask.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                                            if (foundNode != null)
                                                break;
                                        }
                                    }
                                }
                            }
                            ;
                        }
                        if (foundNode == null) {
                            {
                                {
                                    let array154 = (obj => Object.keys(obj).map(key => obj[key]))(this.nextTasks);
                                    for (let index153 = 0; index153 < array154.length; index153++) {
                                        let nextTask = array154[index153];
                                        {
                                            foundNode = nextTask.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                                            if (foundNode != null)
                                                break;
                                        }
                                    }
                                }
                            }
                            ;
                        }
                        return foundNode;
                    }
                    findNodeById(id, visitedNodes) {
                        if (((typeof id === 'string') || id === null) && ((visitedNodes != null && (visitedNodes instanceof Array)) || visitedNodes === null)) {
                            return this.findNodeById$java_lang_String$java_util_Set(id, visitedNodes);
                        }
                        else if (((typeof id === 'string') || id === null) && visitedNodes === undefined) {
                            return this.findNodeById$java_lang_String(id);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /**
                     *
                     * @param {*} writer
                     */
                    writeNode(writer) {
                        this.getNodeDescription().writeNode(writer);
                    }
                }
                tasks.NtroTaskImpl = NtroTaskImpl;
                NtroTaskImpl["__class"] = "ca.ntro.core.tasks.NtroTaskImpl";
                NtroTaskImpl["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
                (function (NtroTaskImpl) {
                    class Edge {
                        constructor(__parent, from, to) {
                            this.__parent = __parent;
                            if (this.from === undefined)
                                this.from = null;
                            if (this.to === undefined)
                                this.to = null;
                            this.from = from;
                            this.to = to;
                        }
                        /**
                         *
                         * @return {number}
                         */
                        hashCode() {
                            return 0;
                        }
                        /**
                         *
                         * @param {*} otherObject
                         * @return {boolean}
                         */
                        equals(otherObject) {
                            if (otherObject == null)
                                return false;
                            if (this === otherObject)
                                return true;
                            if (otherObject != null && otherObject instanceof ca.ntro.core.tasks.NtroTaskImpl.Edge) {
                                let otherEdge = otherObject;
                                return ((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(this.from, otherEdge.from) && ((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(this.to, otherEdge.to);
                            }
                            return false;
                        }
                    }
                    NtroTaskImpl.Edge = Edge;
                    Edge["__class"] = "ca.ntro.core.tasks.NtroTaskImpl.Edge";
                })(NtroTaskImpl = tasks.NtroTaskImpl || (tasks.NtroTaskImpl = {}));
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
                var TaskState;
                (function (TaskState) {
                    TaskState[TaskState["INIT"] = 0] = "INIT";
                    TaskState[TaskState["WAITING_FOR_PREVIOUS_TASKS"] = 1] = "WAITING_FOR_PREVIOUS_TASKS";
                    TaskState[TaskState["LAUNCHING_ENTRY_TASK"] = 2] = "LAUNCHING_ENTRY_TASK";
                    TaskState[TaskState["WAITING_FOR_ENTRY_TASK"] = 3] = "WAITING_FOR_ENTRY_TASK";
                    TaskState[TaskState["WAITING_FOR_SUB_TASKS"] = 4] = "WAITING_FOR_SUB_TASKS";
                    TaskState[TaskState["LAUNCHING_EXIT_TASK"] = 5] = "LAUNCHING_EXIT_TASK";
                    TaskState[TaskState["WAITING_FOR_EXIT_TASK"] = 6] = "WAITING_FOR_EXIT_TASK";
                    TaskState[TaskState["DONE"] = 7] = "DONE";
                    TaskState[TaskState["DELETED"] = 8] = "DELETED";
                })(TaskState = tasks.TaskState || (tasks.TaskState = {}));
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
                class TaskStateDescriptionImpl {
                    constructor(id, state) {
                        if (this.id === undefined)
                            this.id = null;
                        if (this.state === undefined)
                            this.state = null;
                        this.id = id;
                        this.state = state;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getId() {
                        return this.id;
                    }
                    /**
                     *
                     * @return {ca.ntro.core.tasks.TaskState}
                     */
                    getState() {
                        return this.state;
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return 0;
                    }
                    /**
                     *
                     * @param {*} other
                     * @return {boolean}
                     */
                    equals(other) {
                        if (other == null)
                            return false;
                        if (other === this)
                            return true;
                        if (other != null && (other["__interfaces"] != null && other["__interfaces"].indexOf("ca.ntro.core.tasks.TaskStateDescription") >= 0 || other.constructor != null && other.constructor["__interfaces"] != null && other.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.TaskStateDescription") >= 0)) {
                            let otherState = other;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.id, otherState.getId()) && ((this.state) === (otherState.getState()));
                        }
                        return false;
                    }
                }
                tasks.TaskStateDescriptionImpl = TaskStateDescriptionImpl;
                TaskStateDescriptionImpl["__class"] = "ca.ntro.core.tasks.TaskStateDescriptionImpl";
                TaskStateDescriptionImpl["__interfaces"] = ["ca.ntro.core.tasks.TaskStateDescription", "ca.ntro.core.tasks.Identifiable"];
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
                class EdgeDescriptionImpl {
                    constructor(from, to) {
                        if (((from != null && (from["__interfaces"] != null && from["__interfaces"].indexOf("ca.ntro.core.tasks.NodeDescription") >= 0 || from.constructor != null && from.constructor["__interfaces"] != null && from.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NodeDescription") >= 0)) || from === null) && ((to != null && (to["__interfaces"] != null && to["__interfaces"].indexOf("ca.ntro.core.tasks.NodeDescription") >= 0 || to.constructor != null && to.constructor["__interfaces"] != null && to.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NodeDescription") >= 0)) || to === null)) {
                            let __args = arguments;
                            if (this.from === undefined)
                                this.from = null;
                            if (this.to === undefined)
                                this.to = null;
                            if (this.from === undefined)
                                this.from = null;
                            if (this.to === undefined)
                                this.to = null;
                            (() => {
                                this.from = from;
                                this.to = to;
                            })();
                        }
                        else if (from === undefined && to === undefined) {
                            let __args = arguments;
                            if (this.from === undefined)
                                this.from = null;
                            if (this.to === undefined)
                                this.to = null;
                            if (this.from === undefined)
                                this.from = null;
                            if (this.to === undefined)
                                this.to = null;
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getFrom() {
                        return this.from;
                    }
                    setFrom(from) {
                        this.from = from;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTo() {
                        return this.to;
                    }
                    setTo(to) {
                        this.to = to;
                    }
                    /**
                     *
                     * @param {*} writer
                     */
                    write(writer) {
                        writer.addEdge(this.from, this.to);
                    }
                    /**
                     *
                     * @return {number}
                     */
                    hashCode() {
                        return 0;
                    }
                    /**
                     *
                     * @param {*} other
                     * @return {boolean}
                     */
                    equals(other) {
                        if (other == null)
                            return false;
                        if (this === other)
                            return true;
                        if (other != null && other instanceof ca.ntro.core.tasks.EdgeDescriptionImpl) {
                            let otherEdge = other;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.from, otherEdge.from) && ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.to, otherEdge.to);
                        }
                        return false;
                    }
                }
                tasks.EdgeDescriptionImpl = EdgeDescriptionImpl;
                EdgeDescriptionImpl["__class"] = "ca.ntro.core.tasks.EdgeDescriptionImpl";
                EdgeDescriptionImpl["__interfaces"] = ["ca.ntro.core.tasks.EdgeDescription"];
            })(tasks = core.tasks || (core.tasks = {}));
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
                var stores;
                (function (stores) {
                    class ValuePath {
                        constructor(collection) {
                            /*private*/ this.fieldPath = ([]);
                            if (this.__collection === undefined)
                                this.__collection = null;
                            if (this.__documentId === undefined)
                                this.__documentId = null;
                            this.__collection = collection;
                        }
                        static collection(collection) {
                            ca.ntro.core.system.trace.T.call(ValuePath);
                            return new ValuePath(collection);
                        }
                        documentId(documentId) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.__documentId = documentId;
                            return this;
                        }
                        field(...fieldPath) {
                            ca.ntro.core.system.trace.T.call(this);
                            for (let index155 = 0; index155 < fieldPath.length; index155++) {
                                let fieldName = fieldPath[index155];
                                {
                                    /* add */ (this.fieldPath.push(fieldName) > 0);
                                }
                            }
                            return this;
                        }
                        extractDocumentPath() {
                            ca.ntro.core.system.trace.T.call(this);
                            return new ca.ntro.core.services.stores.DocumentPath(this.__collection, this.__documentId);
                        }
                        addFieldName(fieldName) {
                            ca.ntro.core.system.trace.T.call(this);
                            /* add */ (this.fieldPath.push(fieldName) > 0);
                        }
                        updateObject(storedObject, value) {
                            ca.ntro.core.system.trace.T.call(this);
                            return storedObject.updateValue(this.fieldPath, value);
                        }
                        getValueFrom(storedObject) {
                            ca.ntro.core.system.trace.T.call(this);
                            return storedObject.getValue(this.fieldPath);
                        }
                    }
                    stores.ValuePath = ValuePath;
                    ValuePath["__class"] = "ca.ntro.core.services.stores.ValuePath";
                })(stores = services.stores || (services.stores = {}));
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
                var stores;
                (function (stores) {
                    class DocumentPath {
                        constructor(collection, firstPathName, ...pathRemainder) {
                            if (((typeof collection === 'string') || collection === null) && ((typeof firstPathName === 'string') || firstPathName === null) && ((pathRemainder != null && pathRemainder instanceof Array && (pathRemainder.length == 0 || pathRemainder[0] == null || (typeof pathRemainder[0] === 'string'))) || pathRemainder === null)) {
                                let __args = arguments;
                                if (this.collection === undefined)
                                    this.collection = null;
                                if (this.documentId === undefined)
                                    this.documentId = null;
                                if (this.collection === undefined)
                                    this.collection = null;
                                if (this.documentId === undefined)
                                    this.documentId = null;
                                (() => {
                                    this.collection = collection;
                                    this.documentId = firstPathName;
                                    for (let i = 0; i < pathRemainder.length; i++) {
                                        {
                                            this.documentId += "__" + pathRemainder[i];
                                        }
                                        ;
                                    }
                                })();
                            }
                            else if (((typeof collection === 'string') || collection === null) && ((typeof firstPathName === 'string') || firstPathName === null) && pathRemainder === undefined) {
                                let __args = arguments;
                                let documentId = __args[1];
                                if (this.collection === undefined)
                                    this.collection = null;
                                if (this.documentId === undefined)
                                    this.documentId = null;
                                if (this.collection === undefined)
                                    this.collection = null;
                                if (this.documentId === undefined)
                                    this.documentId = null;
                                (() => {
                                    this.collection = collection;
                                    this.documentId = documentId;
                                })();
                            }
                            else
                                throw new Error('invalid overload');
                        }
                        getCollection() {
                            return this.collection;
                        }
                        getId() {
                            return this.documentId;
                        }
                    }
                    stores.DocumentPath = DocumentPath;
                    DocumentPath["__class"] = "ca.ntro.core.services.stores.DocumentPath";
                })(stores = services.stores || (services.stores = {}));
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
                var stores;
                (function (stores) {
                    class LocalStore {
                        static initialize(instance) {
                            LocalStore.instance = instance;
                        }
                        static getLoader(modelClass, authToken, firstPathName, ...pathRemainder) {
                            let modelLoader = null;
                            try {
                                modelLoader = (o => o.getLoaderImpl.apply(o, [modelClass, authToken, firstPathName].concat(pathRemainder)))(LocalStore.instance);
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(LocalStore) + " must be initialized", e);
                            }
                            ;
                            return modelLoader;
                        }
                        static close() {
                            ca.ntro.core.system.trace.T.call(LocalStore);
                            try {
                                LocalStore.instance.close();
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(LocalStore) + " must be initialized", e);
                            }
                            ;
                        }
                    }
                    LocalStore.instance = null;
                    stores.LocalStore = LocalStore;
                    LocalStore["__class"] = "ca.ntro.core.services.stores.LocalStore";
                })(stores = services.stores || (services.stores = {}));
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
                var stores;
                (function (stores) {
                    class NetworkStore {
                        static initialize(instance) {
                            NetworkStore.instance = instance;
                        }
                        static getLoader(modelClass, authToken, firstPathName, ...pathRemainder) {
                            let modelLoader = null;
                            try {
                                modelLoader = (o => o.getLoaderImpl.apply(o, [modelClass, authToken, firstPathName].concat(pathRemainder)))(NetworkStore.instance);
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(ca.ntro.core.services.stores.LocalStore) + " must be initialized", e);
                            }
                            ;
                            return modelLoader;
                        }
                        static close() {
                            ca.ntro.core.system.trace.T.call(ca.ntro.core.services.stores.LocalStore);
                            try {
                                NetworkStore.instance.close();
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(ca.ntro.core.services.stores.LocalStore) + " must be initialized", e);
                            }
                            ;
                        }
                    }
                    NetworkStore.instance = null;
                    stores.NetworkStore = NetworkStore;
                    NetworkStore["__class"] = "ca.ntro.core.services.stores.NetworkStore";
                })(stores = services.stores || (services.stores = {}));
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
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(NtroCollections) + " must be initialized");
                        }
                        ;
                        return synchronizedList;
                    }
                    static concurrentMap(elements) {
                        ca.ntro.core.system.trace.T.call(NtroCollections);
                        let concurrentHashMap = null;
                        try {
                            concurrentHashMap = NtroCollections.instance.concurrentMapImpl(elements);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(NtroCollections) + " must be initialized");
                        }
                        ;
                        return concurrentHashMap;
                    }
                    static concurrentSet(elements) {
                        let concurrentSet = null;
                        try {
                            concurrentSet = NtroCollections.instance.concurrentSetImpl(elements);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(NtroCollections) + " must be initialized");
                        }
                        ;
                        return concurrentSet;
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
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(ValueFormatter) + " must be initialized");
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
                class BackendService {
                }
                services.BackendService = BackendService;
                BackendService["__class"] = "ca.ntro.core.services.BackendService";
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
                class ThreadService {
                }
                services.ThreadService = ThreadService;
                ThreadService["__class"] = "ca.ntro.core.services.ThreadService";
            })(services = core.services || (core.services = {}));
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
                var log;
                (function (log) {
                    class Log {
                        static warning(...messages) {
                        }
                        static error(...messages) {
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
                            let fileName = "";
                            if (rawFilePath != null) {
                                let pathSplits = rawFilePath.split(SourceFileLocation.PATH_DELEMITOR);
                                fileName = pathSplits[pathSplits.length - 1];
                            }
                            return fileName;
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
            var mvc;
            (function (mvc) {
                class NtroContext {
                    constructor() {
                        if (this.lang === undefined)
                            this.lang = null;
                        if (this.user === undefined)
                            this.user = null;
                    }
                    getLang() {
                        return this.lang;
                    }
                    setLang(lang) {
                        this.lang = lang;
                    }
                    getUser() {
                        return this.user;
                    }
                    setUser(user) {
                        this.user = user;
                    }
                    hasSameLang(otherContext) {
                        if (this.lang == null && otherContext.lang != null)
                            return false;
                        return ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.lang, otherContext.lang);
                    }
                }
                mvc.NtroContext = NtroContext;
                NtroContext["__class"] = "ca.ntro.core.mvc.NtroContext";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ParentViewMessageHandler {
                    constructor() {
                        if (this.task === undefined)
                            this.task = null;
                        if (this.parentController === undefined)
                            this.parentController = null;
                    }
                    setMessageId(messageId) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.task = (new ca.ntro.core.mvc.ParentViewMessageHandlerTask(this, messageId));
                    }
                    setParentController(parentController) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.parentController = parentController;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(currentView, message) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(this.parentController.getView(), currentView, message);
                    }
                }
                mvc.ParentViewMessageHandler = ParentViewMessageHandler;
                ParentViewMessageHandler["__class"] = "ca.ntro.core.mvc.ParentViewMessageHandler";
                ParentViewMessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class Constants {
                }
                Constants.VIEW_LOADER_TASK_ID = "viewLoader";
                Constants.VIEW_CREATOR_TASK_ID = "viewCreator";
                Constants.MODEL_LOADER_TASK_ID = "modelLoader";
                Constants.VIEW_MODEL_TASK_ID = "viewModel";
                Constants.VIEW_HANDLER_TASK_ID = "viewReceptor";
                mvc.Constants = Constants;
                Constants["__class"] = "ca.ntro.core.mvc.Constants";
            })(mvc = core.mvc || (core.mvc = {}));
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
            var mvc;
            (function (mvc) {
                class ParentViewHandler {
                }
                mvc.ParentViewHandler = ParentViewHandler;
                ParentViewHandler["__class"] = "ca.ntro.core.mvc.ParentViewHandler";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class WindowViewHandler {
                    constructor() {
                        /*private*/ this.task = (new ca.ntro.core.mvc.WindowViewHandlerTask(this));
                        if (this.window === undefined)
                            this.window = null;
                    }
                    setWindow(window) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.window = window;
                    }
                    handleImpl(view) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(this.window, view);
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                }
                mvc.WindowViewHandler = WindowViewHandler;
                WindowViewHandler["__class"] = "ca.ntro.core.mvc.WindowViewHandler";
                WindowViewHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ControllerMessageHandler {
                    constructor() {
                        if (this.task === undefined)
                            this.task = null;
                        if (this.controller === undefined)
                            this.controller = null;
                    }
                    setMessageId(messageId) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.task = (new ca.ntro.core.mvc.ControllerMessageHandlerTask(this, messageId));
                    }
                    setController(controller) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.controller = controller;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(currentView, message) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(this.controller, currentView, message);
                    }
                }
                mvc.ControllerMessageHandler = ControllerMessageHandler;
                ControllerMessageHandler["__class"] = "ca.ntro.core.mvc.ControllerMessageHandler";
                ControllerMessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class Handler {
                    constructor() {
                        if (this.controller === undefined)
                            this.controller = null;
                    }
                    setController(controller) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.controller = controller;
                    }
                    getController() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.controller;
                    }
                }
                mvc.Handler = Handler;
                Handler["__class"] = "ca.ntro.core.mvc.Handler";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class AnyController {
                }
                mvc.AnyController = AnyController;
                AnyController["__class"] = "ca.ntro.core.mvc.AnyController";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class BackendControllerFactory {
                    static createBackendRootController(controllerClass, modelStore) {
                        ca.ntro.core.system.trace.T.call(BackendControllerFactory);
                        let rootController = (ca.ntro.core.introspection.Factory.newInstance(controllerClass));
                        rootController.setModelStore(modelStore);
                        rootController.onCreate();
                        return rootController;
                    }
                    static createBackendController(controllerClass, parentController) {
                        ca.ntro.core.system.trace.T.call(BackendControllerFactory);
                        let controller = (ca.ntro.core.introspection.Factory.newInstance(controllerClass));
                        controller.setParentController(parentController);
                        controller.setModelStore(parentController.getModelStore());
                        controller.onCreate();
                        return controller;
                    }
                }
                mvc.BackendControllerFactory = BackendControllerFactory;
                BackendControllerFactory["__class"] = "ca.ntro.core.mvc.BackendControllerFactory";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ControllerFactory {
                    static createRootController$java_lang_Class$java_lang_String$ca_ntro_core_mvc_NtroWindow$ca_ntro_core_mvc_NtroContext(controllerClass, path, window, context) {
                        ca.ntro.core.system.trace.T.call(ControllerFactory);
                        return (ControllerFactory.createRootController$java_lang_Class$ca_ntro_core_Path$ca_ntro_core_mvc_NtroWindow$ca_ntro_core_mvc_NtroContext(controllerClass, new ca.ntro.core.Path(path), window, context));
                    }
                    static createRootController(controllerClass, path, window, context) {
                        if (((controllerClass != null) || controllerClass === null) && ((typeof path === 'string') || path === null) && ((window != null && window instanceof ca.ntro.core.mvc.NtroWindow) || window === null) && ((context != null && context instanceof ca.ntro.core.mvc.NtroContext) || context === null)) {
                            return ca.ntro.core.mvc.ControllerFactory.createRootController$java_lang_Class$java_lang_String$ca_ntro_core_mvc_NtroWindow$ca_ntro_core_mvc_NtroContext(controllerClass, path, window, context);
                        }
                        else if (((controllerClass != null) || controllerClass === null) && ((path != null && path instanceof ca.ntro.core.Path) || path === null) && ((window != null && window instanceof ca.ntro.core.mvc.NtroWindow) || window === null) && ((context != null && context instanceof ca.ntro.core.mvc.NtroContext) || context === null)) {
                            return ca.ntro.core.mvc.ControllerFactory.createRootController$java_lang_Class$ca_ntro_core_Path$ca_ntro_core_mvc_NtroWindow$ca_ntro_core_mvc_NtroContext(controllerClass, path, window, context);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    static createRootController$java_lang_Class$ca_ntro_core_Path$ca_ntro_core_mvc_NtroWindow$ca_ntro_core_mvc_NtroContext(controllerClass, path, window, context) {
                        ca.ntro.core.system.trace.T.call(ControllerFactory);
                        let rootController = (ca.ntro.core.introspection.Factory.newInstance(controllerClass));
                        rootController.setWindow(window);
                        rootController.setPath(path);
                        rootController.setContext(context);
                        rootController.onCreate();
                        return rootController;
                    }
                    static createController(controllerClass, path, parentController, context) {
                        ca.ntro.core.system.trace.T.call(ControllerFactory);
                        let controller = (ca.ntro.core.introspection.Factory.newInstance(controllerClass));
                        controller.setParentController(parentController);
                        controller.setPath(path);
                        controller.setContext(context);
                        controller.onCreate();
                        return controller;
                    }
                }
                mvc.ControllerFactory = ControllerFactory;
                ControllerFactory["__class"] = "ca.ntro.core.mvc.ControllerFactory";
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewLoaders {
                    static viewLoaders_$LI$() { if (ViewLoaders.viewLoaders == null)
                        ViewLoaders.viewLoaders = ({}); return ViewLoaders.viewLoaders; }
                    ;
                    static registerViewLoader(viewClass, lang, viewLoader) {
                        ca.ntro.core.system.trace.T.call(ViewLoaders);
                        /* put */ ((m, k, v) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                m.entries[i].value = v;
                                return;
                            } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(ViewLoaders.viewLoaders_$LI$(), new ViewLoaders.ViewLoaderId(viewClass, lang), viewLoader);
                    }
                    static getViewLoader(viewClass, lang) {
                        ca.ntro.core.system.trace.T.call(ViewLoaders);
                        let viewLoader = ((m, k) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                return m.entries[i].value;
                            } return null; })(ViewLoaders.viewLoaders_$LI$(), new ViewLoaders.ViewLoaderId(viewClass, lang));
                        if (viewLoader == null) {
                            console.info("null viewLoader: " + (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(viewClass));
                        }
                        return ((o) => { if (o.clone != undefined) {
                            return o.clone();
                        }
                        else {
                            let clone = Object.create(o);
                            for (let p in o) {
                                if (o.hasOwnProperty(p))
                                    clone[p] = o[p];
                            }
                            return clone;
                        } })(viewLoader);
                    }
                }
                mvc.ViewLoaders = ViewLoaders;
                ViewLoaders["__class"] = "ca.ntro.core.mvc.ViewLoaders";
                (function (ViewLoaders) {
                    class ViewLoaderId {
                        constructor(viewClass, lang) {
                            if (this.viewClass === undefined)
                                this.viewClass = null;
                            if (this.lang === undefined)
                                this.lang = null;
                            ca.ntro.core.system.trace.T.call(this);
                            this.viewClass = viewClass;
                            this.lang = lang;
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
                            } })(this.viewClass) + ((o) => { if (o.hashCode) {
                                return o.hashCode();
                            }
                            else {
                                return o.toString().split('').reduce((prevHash, currVal) => (((prevHash << 5) - prevHash) + currVal.charCodeAt(0)) | 0, 0);
                            } })(this.lang);
                        }
                        /**
                         *
                         * @param {*} other
                         * @return {boolean}
                         */
                        equals(other) {
                            if (other != null && other instanceof ca.ntro.core.mvc.ViewLoaders.ViewLoaderId) {
                                let otherId = other;
                                return ((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(this.viewClass, otherId.viewClass) && ((o1, o2) => { if (o1 && o1.equals) {
                                    return o1.equals(o2);
                                }
                                else {
                                    return o1 === o2;
                                } })(this.lang, otherId.lang);
                            }
                            else {
                                return false;
                            }
                        }
                    }
                    ViewLoaders.ViewLoaderId = ViewLoaderId;
                    ViewLoaderId["__class"] = "ca.ntro.core.mvc.ViewLoaders.ViewLoaderId";
                })(ViewLoaders = mvc.ViewLoaders || (mvc.ViewLoaders = {}));
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var json;
            (function (json) {
                class JsonObject {
                    constructor(map) {
                        if (this.map === undefined)
                            this.map = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.map = map;
                    }
                    getTypeName() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.get(JsonObject.TYPE_KEY);
                    }
                    setTypeName(typeName) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.put(JsonObject.TYPE_KEY, typeName);
                    }
                    toMap() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.map;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    toString() {
                        ca.ntro.core.system.trace.T.call(this);
                        let builder = { str: "", toString: function () { return this.str; } };
                        {
                            let array157 = Object.keys(this.map);
                            for (let index156 = 0; index156 < array157.length; index156++) {
                                let key = array157[index156];
                                {
                                    let value = ((m, k) => m[k] === undefined ? null : m[k])(this.map, key);
                                    /* append */ (sb => { sb.str = sb.str.concat(key + " " + value + ", "); return sb; })(builder);
                                }
                            }
                        }
                        return builder.str;
                    }
                    size() {
                        ca.ntro.core.system.trace.T.call(this);
                        return Object.keys(this.map).length;
                    }
                    isEmpty() {
                        ca.ntro.core.system.trace.T.call(this);
                        return (Object.keys(this.map).length == 0);
                    }
                    containsKey(key) {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.map.hasOwnProperty(key);
                    }
                    containsValue(value) {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.map.containsValue(value);
                    }
                    get(key) {
                        ca.ntro.core.system.trace.T.call(this);
                        return ((m, k) => m[k] === undefined ? null : m[k])(this.map, key);
                    }
                    getSubObject(key) {
                        ca.ntro.core.system.trace.T.call(this);
                        return new JsonObject(((m, k) => m[k] === undefined ? null : m[k])(this.map, key));
                    }
                    put(key, value) {
                        ca.ntro.core.system.trace.T.call(this);
                        return (this.map[key] = value);
                    }
                    remove(key) {
                        ca.ntro.core.system.trace.T.call(this);
                        return (map => { let deleted = this.map[key]; delete this.map[key]; return deleted; })(this.map);
                    }
                    putAll(m) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.map.putAll(m);
                    }
                    clear() {
                        ca.ntro.core.system.trace.T.call(this);
                        /* clear */ (obj => { for (let member in obj)
                            delete obj[member]; })(this.map);
                    }
                    keySet() {
                        ca.ntro.core.system.trace.T.call(this);
                        return Object.keys(this.map);
                    }
                    values() {
                        ca.ntro.core.system.trace.T.call(this);
                        return (obj => Object.keys(obj).map(key => obj[key]))(this.map);
                    }
                    updateValue(fieldPath, value) {
                        ca.ntro.core.system.trace.T.call(this);
                        let oldValue = null;
                        if (fieldPath.length === 1) {
                            let fieldName = fieldPath[0];
                            oldValue = this.put(fieldName, value);
                        }
                        else {
                            let fieldName = fieldPath[0];
                            let subFieldPath = fieldPath.slice(1, /* size */ fieldPath.length - 1);
                            let subObject = this.getSubObject(fieldName);
                            oldValue = subObject.updateValue(subFieldPath, value);
                        }
                        return oldValue;
                    }
                    getValue(fieldPath) {
                        ca.ntro.core.system.trace.T.call(this);
                        let value = null;
                        if (fieldPath.length === 1) {
                            let fieldName = fieldPath[0];
                            value = this.get(fieldName);
                        }
                        else {
                            let fieldName = fieldPath[0];
                            let subFieldPath = fieldPath.slice(1, /* size */ fieldPath.length - 1);
                            let subObject = this.getSubObject(fieldName);
                            value = subObject.getValue(subFieldPath);
                        }
                        return value;
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
                        } })(this.map);
                    }
                    /**
                     *
                     * @param {*} otherObject
                     * @return {boolean}
                     */
                    equals(otherObject) {
                        if (otherObject == null)
                            return false;
                        if (otherObject === this)
                            return true;
                        if (otherObject != null && otherObject instanceof ca.ntro.core.json.JsonObject) {
                            let otherJsonObject = otherObject;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.map, otherJsonObject.map);
                        }
                        return false;
                    }
                }
                JsonObject.TYPE_KEY = "_T";
                json.JsonObject = JsonObject;
                JsonObject["__class"] = "ca.ntro.core.json.JsonObject";
                JsonObject["__interfaces"] = ["java.io.Serializable"];
            })(json = core.json || (core.json = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var json;
            (function (json) {
                class JsonObjectIO {
                    constructor() {
                    }
                    loadFromJsonObject$ca_ntro_core_json_JsonObject(jsonObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        let deserializedObjects = ({});
                        /* put */ (deserializedObjects[""] = this);
                        this.loadFromJsonObject$java_util_Map$java_lang_String$ca_ntro_core_json_JsonObject(deserializedObjects, "", jsonObject);
                    }
                    loadFromJsonObject$java_util_Map$java_lang_String$ca_ntro_core_json_JsonObject(deserializedObjects, valuePath, jsonObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        {
                            let array159 = jsonObject.keySet();
                            for (let index158 = 0; index158 < array159.length; index158++) {
                                let fieldName = array159[index158];
                                {
                                    let jsonValue = jsonObject.get(fieldName);
                                    let setter = ca.ntro.core.Ntro.introspector().findSetter(this.constructor, fieldName);
                                    if (setter != null) {
                                        try {
                                            let setterValue;
                                            if (this.isObjectReference(jsonValue)) {
                                                let objectReference = this.getObjectReference(jsonValue);
                                                setterValue = ((m, k) => m[k] === undefined ? null : m[k])(deserializedObjects, objectReference);
                                                throw Object.defineProperty(new Error("Cycle references not yet supported in JSON deserialization"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                                            }
                                            else {
                                                setterValue = ca.ntro.core.Ntro.introspector().buildValueForSetter(setter, jsonValue);
                                            }
                                            /* invoke */ setter.fn.apply(this, [setterValue]);
                                        }
                                        catch (e) {
                                            ca.ntro.core.system.log.Log.fatalError("[JsonObjectIO] Cannot invoke setter " + setter.name, e);
                                        }
                                        ;
                                    }
                                }
                            }
                        }
                    }
                    loadFromJsonObject(deserializedObjects, valuePath, jsonObject) {
                        if (((deserializedObjects != null && (deserializedObjects instanceof Object)) || deserializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null) && ((jsonObject != null && jsonObject instanceof ca.ntro.core.json.JsonObject) || jsonObject === null)) {
                            return this.loadFromJsonObject$java_util_Map$java_lang_String$ca_ntro_core_json_JsonObject(deserializedObjects, valuePath, jsonObject);
                        }
                        else if (((deserializedObjects != null && deserializedObjects instanceof ca.ntro.core.json.JsonObject) || deserializedObjects === null) && valuePath === undefined && jsonObject === undefined) {
                            return this.loadFromJsonObject$ca_ntro_core_json_JsonObject(deserializedObjects);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /*private*/ isObjectReference(jsonValue) {
                        return this.getObjectReference(jsonValue) != null;
                    }
                    /*private*/ getObjectReference(jsonValue) {
                        let objectReference = null;
                        if (ca.ntro.core.Ntro.introspector().isMap(jsonValue)) {
                            let map = jsonValue;
                            objectReference = ((m, k) => m[k] === undefined ? null : m[k])(map, "_I");
                        }
                        return objectReference;
                    }
                    toJsonObject$() {
                        ca.ntro.core.system.trace.T.call(this);
                        let serializedObjects = ({});
                        /* put */ ((m, k, v) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                m.entries[i].value = v;
                                return;
                            } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(serializedObjects, this, "");
                        return this.toJsonObject$java_util_Map$java_lang_String(serializedObjects, "");
                    }
                    toJsonObject$java_util_Map$java_lang_String(serializedObjects, valuePath) {
                        ca.ntro.core.system.trace.T.call(this);
                        let jsonObject = ca.ntro.core.json.JsonParser.jsonObject();
                        jsonObject.setTypeName(ca.ntro.core.Ntro.introspector().getFullNameForClass(this.constructor));
                        {
                            let array161 = ca.ntro.core.Ntro.introspector().userDefinedGetters(this);
                            for (let index160 = 0; index160 < array161.length; index160++) {
                                let getter = array161[index160];
                                {
                                    let value = null;
                                    try {
                                        value = getter.fn.apply(this);
                                    }
                                    catch (e) {
                                        ca.ntro.core.system.log.Log.fatalError("Cannot invoke getter " + getter.name, e);
                                    }
                                    ;
                                    let fieldName = ca.ntro.core.Ntro.introspector().fieldNameForGetter(getter);
                                    let newValuePath = valuePath + "/" + fieldName;
                                    let jsonValue = this.buildJsonValue(serializedObjects, newValuePath, value);
                                    jsonObject.put(fieldName, jsonValue);
                                }
                            }
                        }
                        return jsonObject;
                    }
                    toJsonObject(serializedObjects, valuePath) {
                        if (((serializedObjects != null && (serializedObjects instanceof Object)) || serializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null)) {
                            return this.toJsonObject$java_util_Map$java_lang_String(serializedObjects, valuePath);
                        }
                        else if (serializedObjects === undefined && valuePath === undefined) {
                            return this.toJsonObject$();
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    /*private*/ buildJsonValue(serializedObjects, valuePath, value) {
                        let jsonValue = value;
                        if (value != null && value instanceof ca.ntro.core.json.JsonObjectIO) {
                            if (((m, k) => { if (m.entries == null)
                                m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                    return true;
                                } return false; })(serializedObjects, value)) {
                                let reference = ({});
                                /* put */ (reference["_I"] = ((m, k) => { if (m.entries == null)
                                    m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                    if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                        return m.entries[i].value;
                                    } return null; })(serializedObjects, value));
                                jsonValue = reference;
                            }
                            else {
                                /* put */ ((m, k, v) => { if (m.entries == null)
                                    m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                    if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                        m.entries[i].value = v;
                                        return;
                                    } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(serializedObjects, value, valuePath);
                                jsonValue = value.toJsonObject$java_util_Map$java_lang_String(serializedObjects, valuePath).toMap();
                            }
                        }
                        else if (ca.ntro.core.Ntro.introspector().isList(value)) {
                            let result = ([]);
                            let list = value;
                            for (let index = 0; index < list.length; index++) {
                                {
                                    let item = list[index];
                                    let jsonItem = this.buildJsonValue(serializedObjects, valuePath + "/" + index, item);
                                    /* add */ (result.push(jsonItem) > 0);
                                }
                                ;
                            }
                            jsonValue = result;
                        }
                        else if (ca.ntro.core.Ntro.introspector().isMap(value) && ((m) => { if (m.entries == null)
                            m.entries = []; return m.entries.length; })(value) > 0) {
                            let map = null;
                            try {
                                map = value;
                            }
                            catch (e) {
                                ca.ntro.core.system.log.Log.fatalError("Only Map<String, ?> are supported for serialization", e);
                            }
                            ;
                            let result = ({});
                            {
                                let array163 = Object.keys(map);
                                for (let index162 = 0; index162 < array163.length; index162++) {
                                    let key = array163[index162];
                                    {
                                        let mapValue = ((m, k) => m[k] === undefined ? null : m[k])(map, key);
                                        let jsonMapValue = this.buildJsonValue(serializedObjects, valuePath + "/" + key, mapValue);
                                        /* put */ (result[key] = jsonMapValue);
                                    }
                                }
                            }
                            jsonValue = result;
                        }
                        return jsonValue;
                    }
                }
                json.JsonObjectIO = JsonObjectIO;
                JsonObjectIO["__class"] = "ca.ntro.core.json.JsonObjectIO";
                JsonObjectIO["__interfaces"] = ["java.io.Serializable"];
            })(json = core.json || (core.json = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var json;
            (function (json) {
                class JsonParser {
                    static classes_$LI$() { if (JsonParser.classes == null)
                        JsonParser.classes = ({}); return JsonParser.classes; }
                    ;
                    static initialize(instance) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        JsonParser.instance = instance;
                    }
                    static jsonObject() {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let result = null;
                        try {
                            result = JsonParser.instance.jsonObjectImpl();
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(JsonParser) + " must be initialized", e);
                        }
                        ;
                        return result;
                    }
                    static registerSerializableClass(_class) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        /* put */ (JsonParser.classes_$LI$()[ca.ntro.core.Ntro.introspector().getSimpleNameForClass(_class)] = ca.ntro.core.Ntro.introspector().getFullNameForClass(_class));
                    }
                    static fromString(jsonString) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let result = null;
                        try {
                            result = JsonParser.instance.fromStringImpl(jsonString);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(JsonParser) + " must be initialized", e);
                        }
                        ;
                        return result;
                    }
                    static fromStream(jsonStream) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let result = null;
                        try {
                            result = JsonParser.instance.fromStreamImpl(jsonStream);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(JsonParser) + " must be initialized", e);
                        }
                        ;
                        return result;
                    }
                    static toString(jsonObject) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let result = null;
                        try {
                            result = JsonParser.instance.toStringImpl(jsonObject);
                        }
                        catch (e) {
                            ca.ntro.core.system.log.Log.fatalError(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(JsonParser) + " must be initialized", e);
                        }
                        ;
                        return result;
                    }
                    static isUserDefined(jsonValue) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        return JsonParser.userDefinedTypeName(jsonValue) != null;
                    }
                    /*private*/ static userDefinedTypeName(jsonValue) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let result = null;
                        try {
                            if (jsonValue != null) {
                                let jsonMap = jsonValue;
                                result = ((m, k) => m[k] === undefined ? null : m[k])(jsonMap, ca.ntro.core.json.JsonObject.TYPE_KEY);
                            }
                        }
                        catch (e) {
                        }
                        ;
                        return result;
                    }
                    static buildUserDefined(jsonValue) {
                        ca.ntro.core.system.trace.T.call(JsonParser);
                        let typeName = JsonParser.userDefinedTypeName(jsonValue);
                        let typeClass = ca.ntro.core.Ntro.introspector().getClassFromName(typeName);
                        let userDefinedObject = (ca.ntro.core.introspection.Factory.newInstance(typeClass));
                        try {
                            userDefinedObject.loadFromJsonObject$ca_ntro_core_json_JsonObject(new ca.ntro.core.json.JsonObject(jsonValue));
                        }
                        catch (e) {
                        }
                        ;
                        return userDefinedObject;
                    }
                }
                JsonParser.instance = null;
                json.JsonParser = JsonParser;
                JsonParser["__class"] = "ca.ntro.core.json.JsonParser";
            })(json = core.json || (core.json = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                class ModelStore {
                    getLoaderImpl(modelClass, authToken, firstPathName, ...pathRemainder) {
                        ca.ntro.core.system.trace.T.call(this);
                        let modelLoader = new ca.ntro.core.models.ModelLoader(this);
                        let documentPath = new (__Function.prototype.bind.apply(ca.ntro.core.services.stores.DocumentPath, [null, /* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(modelClass), firstPathName].concat(pathRemainder)));
                        let jsonLoader = this.getJsonObject(documentPath);
                        jsonLoader.setTaskId("JsonLoader");
                        modelLoader.setTargetClass(modelClass);
                        modelLoader.addSubTask$ca_ntro_core_tasks_NtroTask(jsonLoader);
                        return modelLoader;
                    }
                }
                ModelStore.MODEL_ID_KEY = "modelId";
                ModelStore.MODEL_DATA_KEY = "modelData";
                models.ModelStore = ModelStore;
                ModelStore["__class"] = "ca.ntro.core.models.ModelStore";
            })(models = core.models || (core.models = {}));
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
            Constants.INITIALIZATION_TASK_ID = "initializationTask";
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
            class Path {
                constructor(path) {
                    /*private*/ this.names = ([]);
                    if (((typeof path === 'string') || path === null)) {
                        let __args = arguments;
                        this.names = ([]);
                        (() => {
                            ca.ntro.core.system.trace.T.call(this);
                            if (((str, searchString, position = 0) => str.substr(position, searchString.length) === searchString)(path, "/")) {
                                path = path.substring(1);
                            }
                            this.parsePath(path);
                        })();
                    }
                    else if (((path != null && (path instanceof Array)) || path === null)) {
                        let __args = arguments;
                        let names = __args[0];
                        this.names = ([]);
                        (() => {
                            ca.ntro.core.system.trace.T.call(this);
                            this.names = names;
                        })();
                    }
                    else
                        throw new Error('invalid overload');
                }
                /*private*/ parsePath(path) {
                    ca.ntro.core.system.trace.T.call(this);
                    {
                        let array165 = path.split("/");
                        for (let index164 = 0; index164 < array165.length; index164++) {
                            let name = array165[index164];
                            {
                                /* add */ (this.names.push(name) > 0);
                            }
                        }
                    }
                }
                startsWith(name) {
                    let startsWith = false;
                    if (this.names.length > 0) {
                        startsWith = ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(/* get */ this.names[0], name);
                    }
                    return startsWith;
                }
                subPath$int(beginIndex) {
                    return this.subPath$int$int(beginIndex, /* size */ this.names.length - 1);
                }
                subPath$int$int(beginIndex, endIndex) {
                    let path = null;
                    if (this.ifValidIndices(beginIndex, endIndex)) {
                        path = new Path(/* subList */ this.names.slice(beginIndex, endIndex + 1));
                    }
                    else {
                        path = new Path(([]));
                    }
                    return path;
                }
                subPath(beginIndex, endIndex) {
                    if (((typeof beginIndex === 'number') || beginIndex === null) && ((typeof endIndex === 'number') || endIndex === null)) {
                        return this.subPath$int$int(beginIndex, endIndex);
                    }
                    else if (((typeof beginIndex === 'number') || beginIndex === null) && endIndex === undefined) {
                        return this.subPath$int(beginIndex);
                    }
                    else
                        throw new Error('invalid overload');
                }
                /*private*/ ifValidIndices(beginIndex, endIndex) {
                    return endIndex < this.names.length && endIndex >= beginIndex && beginIndex >= 0;
                }
                /**
                 *
                 * @return {string}
                 */
                toString() {
                    let builder = { str: "", toString: function () { return this.str; } };
                    for (let index166 = 0; index166 < this.names.length; index166++) {
                        let name = this.names[index166];
                        {
                            /* append */ (sb => { sb.str = sb.str.concat("/"); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat(name); return sb; })(builder);
                        }
                    }
                    return builder.str;
                }
                removePrefix(prefix) {
                    ca.ntro.core.system.trace.T.call(this);
                    let remainder = null;
                    if (this.startsWith(prefix)) {
                        remainder = this.subPath$int(1);
                    }
                    else {
                        remainder = this.subPath$int(0);
                    }
                    return remainder;
                }
                getName(index) {
                    ca.ntro.core.system.trace.T.call(this);
                    let name = null;
                    if (this.ifIndexValid(index)) {
                        name = this.names[index];
                    }
                    return name;
                }
                /*private*/ ifIndexValid(index) {
                    ca.ntro.core.system.trace.T.call(this);
                    return index >= 0 && index < this.names.length;
                }
                size() {
                    ca.ntro.core.system.trace.T.call(this);
                    return this.names.length;
                }
                toFileName() {
                    let builder = { str: "", toString: function () { return this.str; } };
                    for (let index167 = 0; index167 < this.names.length; index167++) {
                        let name = this.names[index167];
                        {
                            /* append */ (sb => { sb.str = sb.str.concat(name); return sb; })(builder);
                            /* append */ (sb => { sb.str = sb.str.concat("_"); return sb; })(builder);
                        }
                    }
                    return builder.str;
                }
            }
            core.Path = Path;
            Path["__class"] = "ca.ntro.core.Path";
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            class Ntro {
                static messageServices_$LI$() { if (Ntro.messageServices == null)
                    Ntro.messageServices = ({}); return Ntro.messageServices; }
                ;
                static zzz_registerResourceLoader(resourceLoader) {
                    Ntro.__resourceLoader = resourceLoader;
                }
                static __registerIntrospector(introspector) {
                    Ntro.__introspector = introspector;
                }
                static __registerViewLoaderWeb(viewLoaderWeb) {
                    Ntro.viewLoaderWebClass = viewLoaderWeb;
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
                    ca.ntro.core.system.trace.__T.call(Ntro, "introspector");
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
                    return (ca.ntro.core.introspection.Factory.newInstance(Ntro.viewLoaderWebClass));
                }
                static zzz_registerThreadService(threadService) {
                    Ntro.__threadService = threadService;
                }
                static threadService() {
                    return Ntro.__threadService;
                }
                static zzz_registerMessageServiceClass(messageServiceClass) {
                    Ntro.messageServiceClass = messageServiceClass;
                }
                static messageService() {
                    return (ca.ntro.core.introspection.Factory.newInstance(Ntro.messageServiceClass));
                }
                static zzz_registerBackendService(backendService) {
                    Ntro.__backendService = backendService;
                }
                static backendService() {
                    return Ntro.__backendService;
                }
            }
            Ntro.__introspector = null;
            Ntro.__logger = null;
            Ntro.__appCloser = null;
            Ntro.__regEx = null;
            Ntro.__resourceLoader = null;
            Ntro.viewLoaderWebClass = null;
            Ntro.messageServiceClass = null;
            Ntro.__threadService = null;
            Ntro.__backendService = null;
            core.Ntro = Ntro;
            Ntro["__class"] = "ca.ntro.core.Ntro";
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var web;
        (function (web) {
            var dom;
            (function (dom) {
                class HtmlElement {
                    clearChildren() {
                        ca.ntro.core.system.trace.T.call(this);
                        for (let i = 0; i < this.children("*").size(); i++) {
                            {
                                let child = this.children("*").get(0);
                                child.remove();
                            }
                            ;
                        }
                    }
                }
                dom.HtmlElement = HtmlElement;
                HtmlElement["__class"] = "ca.ntro.web.dom.HtmlElement";
            })(dom = web.dom || (web.dom = {}));
        })(web = ntro.web || (ntro.web = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var web;
        (function (web) {
            var mvc;
            (function (mvc) {
                class NtroViewWeb {
                    constructor() {
                        if (this.rootElement === undefined)
                            this.rootElement = null;
                    }
                    setRootElement(rootElement) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.rootElement = rootElement;
                    }
                    getRootElement() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.rootElement;
                    }
                }
                mvc.NtroViewWeb = NtroViewWeb;
                NtroViewWeb["__class"] = "ca.ntro.web.mvc.NtroViewWeb";
                NtroViewWeb["__interfaces"] = ["ca.ntro.core.mvc.NtroView"];
            })(mvc = web.mvc || (web.mvc = {}));
        })(web = ntro.web || (ntro.web = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageHandler {
            }
            messages.MessageHandler = MessageHandler;
            MessageHandler["__class"] = "ca.ntro.messages.MessageHandler";
        })(messages = ntro.messages || (ntro.messages = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageHandlerTasks {
            }
            messages.MessageHandlerTasks = MessageHandlerTasks;
            MessageHandlerTasks["__class"] = "ca.ntro.messages.MessageHandlerTasks";
        })(messages = ntro.messages || (ntro.messages = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageHandlers {
            }
            messages.MessageHandlers = MessageHandlers;
            MessageHandlers["__class"] = "ca.ntro.messages.MessageHandlers";
        })(messages = ntro.messages || (ntro.messages = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageReceptors {
                static messageReceptors_$LI$() { if (MessageReceptors.messageReceptors == null)
                    MessageReceptors.messageReceptors = ({}); return MessageReceptors.messageReceptors; }
                ;
                addReceptor(messageClass, messageReceptionTask) {
                    ca.ntro.core.system.trace.T.call(this);
                    let receptorSet = ((m, k) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            return m.entries[i].value;
                        } return null; })(MessageReceptors.messageReceptors_$LI$(), messageClass);
                    if (receptorSet == null) {
                        receptorSet = ([]);
                    }
                    /* clear */ (receptorSet.length = 0);
                    /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                        s.push(e);
                        return true;
                    }
                    else {
                        return false;
                    } })(receptorSet, messageReceptionTask);
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(MessageReceptors.messageReceptors_$LI$(), messageClass, receptorSet);
                }
                sendMessage$ca_ntro_messages_NtroMessage(message) {
                    ca.ntro.core.system.trace.T.call(this);
                    let receptorSet = ((m, k) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            return m.entries[i].value;
                        } return null; })(MessageReceptors.messageReceptors_$LI$(), message.constructor);
                    if (receptorSet != null) {
                        this.sendMessage$ca_ntro_messages_NtroMessage$java_util_Set(message, receptorSet);
                    }
                    else {
                        console.info("[WARNING] no receptor for message " + ca.ntro.core.Ntro.introspector().getSimpleNameForClass(message.constructor));
                    }
                }
                sendMessage$ca_ntro_messages_NtroMessage$java_util_Set(message, receptorSet) {
                    ca.ntro.core.system.trace.T.call(this);
                    for (let index168 = 0; index168 < receptorSet.length; index168++) {
                        let receptor = receptorSet[index168];
                        {
                        }
                    }
                }
                sendMessage(message, receptorSet) {
                    if (((message != null && message instanceof ca.ntro.messages.NtroMessage) || message === null) && ((receptorSet != null && (receptorSet instanceof Array)) || receptorSet === null)) {
                        return this.sendMessage$ca_ntro_messages_NtroMessage$java_util_Set(message, receptorSet);
                    }
                    else if (((message != null && message instanceof ca.ntro.messages.NtroMessage) || message === null) && receptorSet === undefined) {
                        return this.sendMessage$ca_ntro_messages_NtroMessage(message);
                    }
                    else
                        throw new Error('invalid overload');
                }
            }
            messages.MessageReceptors = MessageReceptors;
            MessageReceptors["__class"] = "ca.ntro.messages.MessageReceptors";
        })(messages = ntro.messages || (ntro.messages = {}));
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
                        for (let index169 = 0; index169 < argTypes.length; index169++) {
                            let argType = argTypes[index169];
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
                        for (let index170 = 0; index170 < argTypes.length; index170++) {
                            let argType = argTypes[index170];
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
            var tasks;
            (function (tasks) {
                class NtroTaskAsync extends ca.ntro.core.tasks.NtroTaskImpl {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    runEntryTaskAsync() {
                        this.notifyEntryTaskFinished();
                    }
                    /**
                     *
                     */
                    runExitTaskAsync() {
                        this.runTaskAsync();
                    }
                    notifyTaskFinished() {
                        this.notifyExitTaskFinished();
                    }
                    /**
                     *
                     * @param {string} taskId
                     * @param {*} previousTask
                     */
                    onSomePreviousTaskFinished(taskId, previousTask) {
                    }
                    /**
                     *
                     * @param {string} taskId
                     * @param {*} subTask
                     */
                    onSomeSubTaskFinished(taskId, subTask) {
                    }
                }
                tasks.NtroTaskAsync = NtroTaskAsync;
                NtroTaskAsync["__class"] = "ca.ntro.core.tasks.NtroTaskAsync";
                NtroTaskAsync["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(tasks = core.tasks || (core.tasks = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageHandlerTask extends ca.ntro.core.tasks.NtroTaskImpl {
                constructor() {
                    super();
                }
                /**
                 *
                 */
                runEntryTaskAsync() {
                }
                /**
                 *
                 */
                runExitTaskAsync() {
                }
                /**
                 *
                 * @param {string} taskId
                 * @param {*} previousTask
                 */
                onSomePreviousTaskFinished(taskId, previousTask) {
                }
                /**
                 *
                 * @param {string} taskId
                 * @param {*} subTask
                 */
                onSomeSubTaskFinished(taskId, subTask) {
                }
                /**
                 *
                 * @param {Error} e
                 */
                onFailure(e) {
                }
            }
            messages.MessageHandlerTask = MessageHandlerTask;
            MessageHandlerTask["__class"] = "ca.ntro.messages.MessageHandlerTask";
            MessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
        })(messages = ntro.messages || (ntro.messages = {}));
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
        var web;
        (function (web) {
            class NtroWindowWeb extends ca.ntro.core.mvc.NtroWindow {
                writeHtml(out) {
                    ca.ntro.core.system.trace.T.call(this);
                    this.getDocument().writeHtml(out);
                }
                /**
                 *
                 * @param {*} rootView
                 */
                installRootView(rootView) {
                    ca.ntro.core.system.trace.T.call(this);
                    let body = this.getDocument().select("body").get(0);
                    let rootViewWeb = rootView;
                    body.appendElement(rootViewWeb.getRootElement());
                }
                setCurrentPath(path) {
                    ca.ntro.core.system.trace.T.call(this);
                    let body = this.getDocument().select("body").get(0);
                    body.setAttribute("current-path", path.toString());
                }
            }
            web.NtroWindowWeb = NtroWindowWeb;
            NtroWindowWeb["__class"] = "ca.ntro.web.NtroWindowWeb";
        })(web = ntro.web || (ntro.web = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var mvc;
            (function (mvc) {
                class ModelMessageHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        if (this.task === undefined)
                            this.task = null;
                    }
                    setMessageId(messageId) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.task = (new ca.ntro.core.mvc.ModelMessageHandlerTask(this, messageId));
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(model, message) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(model, message);
                    }
                }
                mvc.ModelMessageHandler = ModelMessageHandler;
                ModelMessageHandler["__class"] = "ca.ntro.core.mvc.ModelMessageHandler";
                ModelMessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ModelViewHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        /*private*/ this.task = (new ca.ntro.core.mvc.ModelViewHandlerTask(this));
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(view, model) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(model, view);
                    }
                }
                mvc.ModelViewHandler = ModelViewHandler;
                ModelViewHandler["__class"] = "ca.ntro.core.mvc.ModelViewHandler";
                ModelViewHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ModelViewSubViewHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        /*private*/ this.task = (new ca.ntro.core.mvc.ModelViewSubViewHandlerTask(this));
                        if (this.subViewLoader === undefined)
                            this.subViewLoader = null;
                    }
                    setSubViewLoader(subViewLoader) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.subViewLoader = subViewLoader;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(view, model) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(model, view, this.subViewLoader);
                    }
                }
                mvc.ModelViewSubViewHandler = ModelViewSubViewHandler;
                ModelViewSubViewHandler["__class"] = "ca.ntro.core.mvc.ModelViewSubViewHandler";
                ModelViewSubViewHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        /*private*/ this.mainTask = (new ca.ntro.core.mvc.ViewHandlerTask(this));
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask.execute();
                    }
                    handle$ca_ntro_core_mvc_NtroView(view) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(this.getController(), view);
                    }
                    handle$ca_ntro_core_mvc_NtroAbstractController$ca_ntro_core_mvc_NtroView(controller, view) { throw new Error('cannot invoke abstract overloaded method... check your argument(s) type(s)'); }
                    handle(controller, view) {
                        if (((controller != null) || controller === null) && ((view != null) || view === null)) {
                            return this.handle$ca_ntro_core_mvc_NtroAbstractController$ca_ntro_core_mvc_NtroView(controller, view);
                        }
                        else if (((controller != null) || controller === null) && view === undefined) {
                            return this.handle$ca_ntro_core_mvc_NtroView(controller);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                }
                mvc.ViewHandler = ViewHandler;
                ViewHandler["__class"] = "ca.ntro.core.mvc.ViewHandler";
                ViewHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewMessageHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        /*private*/ this.mainTask = (new ca.ntro.core.mvc.ViewMessageHandlerTask(this, "FIXME"));
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask.execute();
                    }
                    handleImpl(view, message) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(view, message);
                    }
                }
                mvc.ViewMessageHandler = ViewMessageHandler;
                ViewMessageHandler["__class"] = "ca.ntro.core.mvc.ViewMessageHandler";
                ViewMessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class MessageHandler extends ca.ntro.core.mvc.Handler {
                    constructor() {
                        super();
                        if (this.task === undefined)
                            this.task = null;
                    }
                    setMessageId(messageId) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.task = (new ca.ntro.core.mvc.MessageHandlerTask(this, messageId));
                        this.task.setTaskId(messageId);
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.task.execute();
                    }
                    handleImpl(message) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.handle(message);
                    }
                }
                mvc.MessageHandler = MessageHandler;
                MessageHandler["__class"] = "ca.ntro.core.mvc.MessageHandler";
                MessageHandler["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class NtroAbstractController extends ca.ntro.core.mvc.AnyController {
                    constructor() {
                        super();
                        /*private*/ this.mainTask = new ca.ntro.core.tasks.ContainerTask();
                        /*private*/ this.initTasks = new ca.ntro.core.tasks.ContainerTask();
                        if (this.context === undefined)
                            this.context = null;
                        if (this.path === undefined)
                            this.path = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.mainTask.setTaskId(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(this.constructor));
                        this.initTasks.setTaskId("InitTasks");
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](this.initTasks);
                    }
                    /*private*/ addDefaultTasks() {
                        ca.ntro.core.system.trace.T.call(this);
                        this.setViewLoader$ca_ntro_core_mvc_ViewLoader(new ca.ntro.core.mvc.EmptyViewLoader());
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.getTask().execute();
                    }
                    setContext(context) {
                        this.context = context;
                    }
                    setPath(path) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.path = path;
                    }
                    currentContext() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.context;
                    }
                    addSubController(controllerClass, controllerId) {
                        ca.ntro.core.system.trace.T.call(this);
                        if (this.path.startsWith(controllerId) || this.path.startsWith("*")) {
                            let pathRemainder = this.path.removePrefix(controllerId);
                            let subController = (ca.ntro.core.mvc.ControllerFactory.createController(controllerClass, pathRemainder, this, this.context));
                            this.initTasks['addNextTask$ca_ntro_core_tasks_NtroTask'](subController.getTask());
                        }
                    }
                    addSubViewLoader(subViewClass, lang) {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewLoader = ca.ntro.core.mvc.ViewLoaders.getViewLoader(subViewClass, lang);
                        ca.ntro.core.system.assertions.MustNot.beNull(viewLoader);
                        viewLoader.resetTask();
                        viewLoader.setTaskId(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(subViewClass));
                        this.getTask()['addSubTask$ca_ntro_core_tasks_NtroTask'](viewLoader);
                    }
                    addMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let message = (ca.ntro.messages.MessageFactory.getIncomingMessage(messageClass));
                        let messageId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(messageClass);
                        message.setTaskId(messageId);
                        handler.setMessageId(messageId);
                        handler.setController(this);
                        handler.getTask()['addPreviousTask$ca_ntro_core_tasks_NtroTask'](message);
                    }
                    addModelMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let messageId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(messageClass);
                        let message = (ca.ntro.messages.MessageFactory.getIncomingMessage(messageClass));
                        message.setTaskId(messageId);
                        handler.setMessageId(messageId);
                        handler.getTask()['addPreviousTask$ca_ntro_core_tasks_NtroTask'](message);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID);
                    }
                    setViewLoader$ca_ntro_core_mvc_ViewLoader(viewLoader) {
                        ca.ntro.core.system.trace.T.call(this);
                        viewLoader.resetTask();
                        let currentViewLoader = (this.initTasks.getSubTask(ca.ntro.core.mvc.ViewLoader, ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID));
                        if (currentViewLoader == null) {
                            viewLoader.setTaskId(ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID);
                            this.initTasks['addSubTask$ca_ntro_core_tasks_NtroTask'](viewLoader);
                            let viewCreator = new ca.ntro.core.mvc.ViewCreatorTask();
                            viewCreator.setTaskId(ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                            this.initTasks['addSubTask$ca_ntro_core_tasks_NtroTask'](viewCreator);
                            viewCreator.addPreviousTask$ca_ntro_core_tasks_NtroTask(viewLoader);
                            this.addPreviousTaskTo$java_lang_Class$java_lang_String$ca_ntro_core_tasks_NtroTask(ca.ntro.core.mvc.ModelViewHandlerTask, ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID, viewCreator);
                            this.addPreviousTaskTo$java_lang_Class$java_lang_String$ca_ntro_core_tasks_NtroTask(ca.ntro.core.mvc.ViewHandlerTask, ca.ntro.core.mvc.Constants.VIEW_HANDLER_TASK_ID, viewCreator);
                        }
                        else {
                            viewLoader.setTaskId(ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID);
                            currentViewLoader.replaceWith(viewLoader);
                        }
                    }
                    setViewLoader$java_lang_Class$java_lang_String(viewClass, lang) {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewLoader = ca.ntro.core.mvc.ViewLoaders.getViewLoader(viewClass, lang);
                        ca.ntro.core.system.assertions.MustNot.beNull(viewLoader);
                        this.setViewLoader$ca_ntro_core_mvc_ViewLoader(viewLoader);
                    }
                    setViewLoader(viewClass, lang) {
                        if (((viewClass != null) || viewClass === null) && ((typeof lang === 'string') || lang === null)) {
                            return this.setViewLoader$java_lang_Class$java_lang_String(viewClass, lang);
                        }
                        else if (((viewClass != null && viewClass instanceof ca.ntro.core.mvc.ViewLoader) || viewClass === null) && lang === undefined) {
                            return this.setViewLoader$ca_ntro_core_mvc_ViewLoader(viewClass);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    addPreviousTaskTo$java_lang_Class$java_lang_String$ca_ntro_core_tasks_NtroTask(taskClass, taskId, previousTask) {
                        ca.ntro.core.system.trace.T.call(this);
                        let task = (this.initTasks.getSubTask(taskClass, taskId));
                        if (task == null) {
                            task = (this.mainTask.getSubTask(taskClass, taskId));
                        }
                        if (task != null) {
                            task['addPreviousTask$ca_ntro_core_tasks_NtroTask'](previousTask);
                        }
                    }
                    addPreviousTaskTo(taskClass, taskId, previousTask) {
                        if (((taskClass != null) || taskClass === null) && ((typeof taskId === 'string') || taskId === null) && ((previousTask != null && (previousTask["__interfaces"] != null && previousTask["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || previousTask.constructor != null && previousTask.constructor["__interfaces"] != null && previousTask.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || previousTask === null)) {
                            return this.addPreviousTaskTo$java_lang_Class$java_lang_String$ca_ntro_core_tasks_NtroTask(taskClass, taskId, previousTask);
                        }
                        else if (((taskClass != null && (taskClass["__interfaces"] != null && taskClass["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0 || taskClass.constructor != null && taskClass.constructor["__interfaces"] != null && taskClass.constructor["__interfaces"].indexOf("ca.ntro.core.tasks.NtroTask") >= 0)) || taskClass === null) && ((taskId != null) || taskId === null) && ((typeof previousTask === 'string') || previousTask === null)) {
                            return this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(taskClass, taskId, previousTask);
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    setModelLoader(modelLoader) {
                        ca.ntro.core.system.trace.T.call(this);
                        let currentModelLoader = (this.mainTask.getSubTask(ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID));
                        if (currentModelLoader == null) {
                            modelLoader.setTaskId(ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID);
                            this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](modelLoader);
                            this.addPreviousTaskTo$java_lang_Class$java_lang_String$ca_ntro_core_tasks_NtroTask(ca.ntro.core.mvc.ModelViewHandlerTask, ca.ntro.core.mvc.Constants.VIEW_MODEL_TASK_ID, modelLoader);
                        }
                        else {
                            modelLoader.setTaskId(ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID);
                            currentModelLoader.asTask().replaceWith(modelLoader);
                        }
                    }
                    setModelViewHandler(handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let task = handler.getTask();
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](task);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(task, ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(task, ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID);
                    }
                    addModelViewSubViewHandler(subViewClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let task = handler.getTask();
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](task);
                        let subViewLoaderTaskId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(subViewClass);
                        let subViewLoader = (this.getTask().getSubTask(ca.ntro.core.mvc.ViewLoader, subViewLoaderTaskId));
                        handler.setSubViewLoader(subViewLoader);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(task, ca.ntro.core.mvc.ViewLoader, subViewLoaderTaskId);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(task, ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(task, ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID);
                    }
                    addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(nextTask, taskClass, taskId) {
                        ca.ntro.core.system.trace.T.call(this);
                        let previousTask = (this.initTasks.getSubTask(taskClass, taskId));
                        if (previousTask == null) {
                            previousTask = (this.mainTask.getSubTask(taskClass, taskId));
                        }
                        if (previousTask != null) {
                            nextTask['addPreviousTask$ca_ntro_core_tasks_NtroTask'](previousTask);
                        }
                    }
                    setViewHandler(handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        handler.setController(this);
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                    }
                    addViewMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                    }
                    getView() {
                        ca.ntro.core.system.trace.T.call(this);
                        let view = this.initTasks.getSubTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID).getView();
                        return view;
                    }
                }
                mvc.NtroAbstractController = NtroAbstractController;
                NtroAbstractController["__class"] = "ca.ntro.core.mvc.NtroAbstractController";
                NtroAbstractController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class BackendAbstractController extends ca.ntro.core.mvc.AnyController {
                    constructor() {
                        super();
                        /*private*/ this.mainTask = new ca.ntro.core.tasks.ContainerTask();
                        if (this.modelStore === undefined)
                            this.modelStore = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.mainTask.setTaskId(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(this.constructor));
                    }
                    setModelStore(modelStore) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.modelStore = modelStore;
                    }
                    getModelStore() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.modelStore;
                    }
                    addMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let message = (ca.ntro.messages.MessageFactory.getIncomingMessage(messageClass));
                        let messageId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(messageClass);
                        message.setTaskId(messageId);
                        handler.setMessageId(messageId);
                        handler.setController(this);
                        handler.getTask()['addPreviousTask$ca_ntro_core_tasks_NtroTask'](message);
                        this.getTask()['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                    }
                    addSubController(controllerClass, controllerId) {
                        ca.ntro.core.system.trace.T.call(this);
                        let subController = (ca.ntro.core.mvc.BackendControllerFactory.createBackendController(controllerClass, this));
                        this.mainTask['addSubTask$ca_ntro_core_tasks_NtroTask'](subController.getTask());
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getTask() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    execute() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.mainTask.execute();
                    }
                    getModel(modelClass, authToken, firstPathName, ...pathRemainder) {
                        let loader = (o => o.getLoaderImpl.apply(o, [modelClass, authToken, firstPathName].concat(pathRemainder)))(this.modelStore);
                        loader.execute();
                        return loader.getModel();
                    }
                }
                mvc.BackendAbstractController = BackendAbstractController;
                BackendAbstractController["__class"] = "ca.ntro.core.mvc.BackendAbstractController";
                BackendAbstractController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var simple;
                        (function (simple) {
                            class ObservableProperty extends ca.ntro.core.json.JsonObjectIO {
                                constructor(value) {
                                    if (((value != null) || value === null)) {
                                        let __args = arguments;
                                        super();
                                        if (this.value === undefined)
                                            this.value = null;
                                        if (this.valueListener === undefined)
                                            this.valueListener = null;
                                        if (this.deletionListener === undefined)
                                            this.deletionListener = null;
                                        this.observers = ([]);
                                        if (this.value === undefined)
                                            this.value = null;
                                        if (this.valueListener === undefined)
                                            this.valueListener = null;
                                        if (this.deletionListener === undefined)
                                            this.deletionListener = null;
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                            this.value = value;
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
                                        if (this.value === undefined)
                                            this.value = null;
                                        if (this.valueListener === undefined)
                                            this.valueListener = null;
                                        if (this.deletionListener === undefined)
                                            this.deletionListener = null;
                                        this.observers = ([]);
                                        if (this.value === undefined)
                                            this.value = null;
                                        if (this.valueListener === undefined)
                                            this.valueListener = null;
                                        if (this.deletionListener === undefined)
                                            this.deletionListener = null;
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                loadFromJsonObject(deserializedObjects, valuePath, jsonObject) {
                                    if (((deserializedObjects != null && (deserializedObjects instanceof Object)) || deserializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null) && ((jsonObject != null && jsonObject instanceof ca.ntro.core.json.JsonObject) || jsonObject === null)) {
                                        super.loadFromJsonObject(deserializedObjects, valuePath, jsonObject);
                                    }
                                    else if (((deserializedObjects != null && deserializedObjects instanceof ca.ntro.core.json.JsonObject) || deserializedObjects === null) && valuePath === undefined && jsonObject === undefined) {
                                        return this.loadFromJsonObject$ca_ntro_core_json_JsonObject(deserializedObjects);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                loadFromJsonObject$ca_ntro_core_json_JsonObject(jsonObject) {
                                    let jsonValue = jsonObject.get("value");
                                    let value = ca.ntro.core.Ntro.introspector().buildValueForType(this.valueType(), jsonValue);
                                    this.value = value;
                                }
                                toJsonObject(serializedObjects, valuePath) {
                                    if (((serializedObjects != null && (serializedObjects instanceof Object)) || serializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null)) {
                                        super.toJsonObject(serializedObjects, valuePath);
                                    }
                                    else if (serializedObjects === undefined && valuePath === undefined) {
                                        return this.toJsonObject$();
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                toJsonObject$() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let result = ca.ntro.core.json.JsonParser.jsonObject();
                                    result.setTypeName(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(this.constructor));
                                    let jsonValue = this.value;
                                    if (((o1, o2) => { if (o1 && o1.equals) {
                                        return o1.equals(o2);
                                    }
                                    else {
                                        return o1 === o2;
                                    } })(this.valueType(), ca.ntro.core.models.properties.NtroModelValue)) {
                                        jsonValue = this.value.toJsonObject().toMap();
                                    }
                                    result.put("value", jsonValue);
                                    return result;
                                }
                                getValue() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return this.value;
                                }
                                setValue(value) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.value = value;
                                }
                                get(valueListener) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    valueListener.onValue(this.value);
                                }
                                onDeleted(deletionListener) {
                                }
                                set(newValue) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let oldValue = this.value;
                                    this.value = newValue;
                                    for (let index171 = 0; index171 < this.observers.length; index171++) {
                                        let observer = this.observers[index171];
                                        {
                                            observer.onValueChanged(oldValue, newValue);
                                        }
                                    }
                                }
                                observe(observer) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    /* add */ (this.observers.push(observer) > 0);
                                    observer.onValue(this.value);
                                }
                            }
                            simple.ObservableProperty = ObservableProperty;
                            ObservableProperty["__class"] = "ca.ntro.core.models.properties.observable.simple.ObservableProperty";
                            ObservableProperty["__interfaces"] = ["java.io.Serializable"];
                        })(simple = observable.simple || (observable.simple = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    /**
                     * Every appointment-defined value inside a model
                     * must be a subclass of ModelValue
                     *
                     * @author mbergeron
                     * @class
                     * @extends ca.ntro.core.json.JsonObjectIO
                     */
                    class NtroModelValue extends ca.ntro.core.json.JsonObjectIO {
                        constructor() {
                            super();
                        }
                        /**
                         *
                         * @param {ca.ntro.core.services.stores.ValuePath} valuePath
                         * @param {ca.ntro.core.models.ModelStore} modelStore
                         */
                        connectToStore(valuePath, modelStore) {
                            ca.ntro.core.system.trace.T.call(this);
                            this.connectSubValues(valuePath, modelStore);
                        }
                        /*private*/ connectSubValues(valuePath, modelStore) {
                            ca.ntro.core.system.trace.T.call(this);
                            {
                                let array173 = ca.ntro.core.Ntro.introspector().userDefinedGetters(this);
                                for (let index172 = 0; index172 < array173.length; index172++) {
                                    let getter = array173[index172];
                                    {
                                        let fieldName = ca.ntro.core.Ntro.introspector().fieldNameForGetter(getter);
                                        let fieldValue = null;
                                        try {
                                            fieldValue = getter.fn.apply(this);
                                        }
                                        catch (e) {
                                            ca.ntro.core.system.log.Log.fatalError("[ModelValue] Cannot invoke getter " + getter.name, e);
                                        }
                                        ;
                                        if (fieldValue != null && (fieldValue["__interfaces"] != null && fieldValue["__interfaces"].indexOf("ca.ntro.core.models.StoreConnectable") >= 0 || fieldValue.constructor != null && fieldValue.constructor["__interfaces"] != null && fieldValue.constructor["__interfaces"].indexOf("ca.ntro.core.models.StoreConnectable") >= 0)) {
                                            valuePath.addFieldName(fieldName);
                                            fieldValue.connectToStore(valuePath, modelStore);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    properties.NtroModelValue = NtroModelValue;
                    NtroModelValue["__class"] = "ca.ntro.core.models.properties.NtroModelValue";
                    NtroModelValue["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                /**
                 *
                 * The properties of a Model must be:
                 * - Java values: String, Double, List<String>, etc.
                 * - User-defined classes that extend Value
                 *
                 * @author mbergeron
                 * @class
                 * @extends ca.ntro.core.json.JsonObjectIO
                 */
                class NtroModel extends ca.ntro.core.json.JsonObjectIO {
                    constructor() {
                        super();
                        if (this.id === undefined)
                            this.id = null;
                        if (this.modelStore === undefined)
                            this.modelStore = null;
                    }
                    save() {
                        ca.ntro.core.system.trace.T.call(this);
                        this.modelStore.saveJsonObject(this.documentPath(), this.toJsonObject());
                    }
                    updateOnceFrom(store, modelId, listener) {
                    }
                    synchronizeWith(store, modelId) {
                    }
                    /*private*/ documentPath() {
                        return new ca.ntro.core.services.stores.DocumentPath(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(this.constructor), this.id);
                    }
                    setId(id) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.id = id;
                    }
                    setOrigin(origin) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.modelStore = origin;
                    }
                    connectStoredValues() {
                        ca.ntro.core.system.trace.T.call(this);
                        {
                            let array175 = ca.ntro.core.Ntro.introspector().userDefinedGetters(this);
                            for (let index174 = 0; index174 < array175.length; index174++) {
                                let getter = array175[index174];
                                {
                                    let fieldName = ca.ntro.core.Ntro.introspector().fieldNameForGetter(getter);
                                    let fieldValue = null;
                                    try {
                                        fieldValue = getter.fn.apply(this);
                                    }
                                    catch (e) {
                                        ca.ntro.core.system.log.Log.fatalError("[Model] Cannot invoke getter " + getter.name, e);
                                    }
                                    ;
                                    if (fieldValue != null && (fieldValue["__interfaces"] != null && fieldValue["__interfaces"].indexOf("ca.ntro.core.models.StoreConnectable") >= 0 || fieldValue.constructor != null && fieldValue.constructor["__interfaces"] != null && fieldValue.constructor["__interfaces"].indexOf("ca.ntro.core.models.StoreConnectable") >= 0)) {
                                        let valuePath = ca.ntro.core.services.stores.ValuePath.collection(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(this.constructor)).documentId(this.id).field(fieldName);
                                        fieldValue.connectToStore(valuePath, this.modelStore);
                                    }
                                }
                            }
                        }
                    }
                }
                models.NtroModel = NtroModel;
                NtroModel["__class"] = "ca.ntro.core.models.NtroModel";
                NtroModel["__interfaces"] = ["java.io.Serializable"];
            })(models = core.models || (core.models = {}));
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
                var stores;
                (function (stores) {
                    class MemoryStore extends ca.ntro.core.models.ModelStore {
                        constructor() {
                            super(...arguments);
                            /*private*/ this.values = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                            /*private*/ this.valuesById = ca.ntro.core.services.NtroCollections.concurrentMap(({}));
                        }
                        static instance_$LI$() { if (MemoryStore.instance == null)
                            MemoryStore.instance = new MemoryStore(); return MemoryStore.instance; }
                        ;
                        static reInitialize() {
                            ca.ntro.core.system.trace.T.call(MemoryStore);
                            MemoryStore.instance = new MemoryStore();
                        }
                        static getLoader(modelClass, firstPathName, ...pathRemainder) {
                            ca.ntro.core.system.trace.T.call(MemoryStore);
                            let result = (o => o.getLoaderImpl.apply(o, [modelClass, "NO_TOKEN", firstPathName].concat(pathRemainder)))(MemoryStore.instance_$LI$());
                            return result;
                        }
                        static clearStore() {
                            ca.ntro.core.system.trace.T.call(MemoryStore);
                            /* clear */ MemoryStore.instance_$LI$().values.entries = [];
                        }
                        /**
                         *
                         * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                         * @return {ca.ntro.core.json.JsonLoader}
                         */
                        getJsonObject(documentPath) {
                            ca.ntro.core.system.trace.T.call(this);
                            let result = ((m, k) => m[k] === undefined ? null : m[k])(this.valuesById, documentPath.getId());
                            if (result == null) {
                                ca.ntro.core.system.trace.T.here();
                                result = ca.ntro.core.json.JsonParser.jsonObject();
                                /* put */ ((m, k, v) => { if (m.entries == null)
                                    m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                    if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                        m.entries[i].value = v;
                                        return;
                                    } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.values, documentPath, result);
                            }
                            return new ca.ntro.core.json.JsonLoaderMemory(documentPath, result);
                        }
                        /**
                         *
                         * @param {ca.ntro.core.services.stores.ValuePath} valuePath
                         * @param {*} valueListener
                         */
                        addValueListener(valuePath, valueListener) {
                        }
                        /**
                         *
                         * @param {ca.ntro.core.services.stores.ValuePath} valuePath
                         * @param {*} value
                         */
                        setValue(valuePath, value) {
                            ca.ntro.core.system.trace.T.call(this);
                            let document = ((m, k) => { if (m.entries == null)
                                m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                    return m.entries[i].value;
                                } return null; })(this.values, valuePath.extractDocumentPath());
                            valuePath.updateObject(document, value);
                        }
                        /**
                         *
                         * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                         * @param {ca.ntro.core.json.JsonObject} jsonObject
                         */
                        saveJsonObject(documentPath, jsonObject) {
                            ca.ntro.core.system.trace.T.call(this);
                            /* put */ ((m, k, v) => { if (m.entries == null)
                                m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                    m.entries[i].value = v;
                                    return;
                                } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.values, documentPath, jsonObject);
                            /* put */ (this.valuesById[documentPath.getId()] = jsonObject);
                        }
                        /**
                         *
                         */
                        close() {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                        /**
                         *
                         * @param {*} updateListener
                         */
                        installExternalUpdateListener(updateListener) {
                            ca.ntro.core.system.trace.T.call(this);
                        }
                    }
                    stores.MemoryStore = MemoryStore;
                    MemoryStore["__class"] = "ca.ntro.core.services.stores.MemoryStore";
                })(stores = services.stores || (services.stores = {}));
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
                class MessageService {
                    constructor() {
                        /*private*/ this.handlers = ({});
                    }
                    registerHandler(messageClass, handler) {
                        /* put */ ((m, k, v) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                m.entries[i].value = v;
                                return;
                            } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.handlers, messageClass, handler);
                        {
                            let array177 = ca.ntro.core.Ntro.threadService().subThreads();
                            for (let index176 = 0; index176 < array177.length; index176++) {
                                let subThread = array177[index176];
                                {
                                    subThread.handleMessageFromThread(messageClass, handler);
                                }
                            }
                        }
                        ca.ntro.core.Ntro.backendService().handleMessageFromBackend(messageClass, handler);
                    }
                    registerHandlerTask(messageClass, task) {
                        /* put */ ((m, k, v) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                m.entries[i].value = v;
                                return;
                            } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(this.handlers, messageClass, new MessageService.MessageService$0(this, task));
                    }
                    sendMessage(message) {
                        if (((m, k) => { if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                return true;
                            } return false; })(this.handlers, message.constructor)) {
                            let handler = ((m, k) => { if (m.entries == null)
                                m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                    return m.entries[i].value;
                                } return null; })(this.handlers, message.constructor);
                            handler.handle(message);
                        }
                        else if (ca.ntro.core.Ntro.threadService().hasParentThread()) {
                            ca.ntro.core.Ntro.threadService().sendMessageToParentThread(message);
                        }
                        else {
                            ca.ntro.core.Ntro.backendService().sendMessageToBackend(message);
                        }
                    }
                }
                services.MessageService = MessageService;
                MessageService["__class"] = "ca.ntro.core.services.MessageService";
                (function (MessageService) {
                    class MessageService$0 extends ca.ntro.messages.MessageHandler {
                        constructor(__parent, task) {
                            super();
                            this.task = task;
                            this.__parent = __parent;
                        }
                        /**
                         *
                         * @param {ca.ntro.messages.NtroMessage} message
                         */
                        handle(message) {
                            this.task.notifyExitTaskFinished();
                        }
                    }
                    MessageService.MessageService$0 = MessageService$0;
                })(MessageService = services.MessageService || (services.MessageService = {}));
            })(services = core.services || (core.services = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class MessageFactory {
                static messages_$LI$() { if (MessageFactory.messages == null)
                    MessageFactory.messages = ({}); return MessageFactory.messages; }
                ;
                static handlers_$LI$() { if (MessageFactory.handlers == null)
                    MessageFactory.handlers = new ca.ntro.messages.MessageHandlers(); return MessageFactory.handlers; }
                ;
                static tasks_$LI$() { if (MessageFactory.tasks == null)
                    MessageFactory.tasks = new ca.ntro.messages.MessageHandlerTasks(); return MessageFactory.tasks; }
                ;
                static getOutgoingMessage(messageClass) {
                    ca.ntro.core.system.trace.T.call(MessageFactory);
                    let message = ((m, k) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            return m.entries[i].value;
                        } return null; })(MessageFactory.messages_$LI$(), messageClass);
                    if (message == null) {
                        console.info(ca.ntro.core.Ntro.introspector().getSimpleNameForClass(messageClass));
                        ca.ntro.core.system.assertions.MustNot.beNull(message);
                    }
                    return message;
                }
                static createMessageHandler(messageClass, handlerClass) {
                    ca.ntro.core.system.trace.T.call(MessageFactory);
                    return null;
                }
                static createMessageHandlerTask(messageClass) {
                    ca.ntro.core.system.trace.T.call(MessageFactory);
                    return null;
                }
                static getIncomingMessage(messageClass) {
                    ca.ntro.core.system.trace.T.call(MessageFactory);
                    let message = (ca.ntro.core.introspection.Factory.newInstance(messageClass));
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(MessageFactory.messages_$LI$(), messageClass, message);
                    return message;
                }
                static reset() {
                    ca.ntro.core.system.trace.T.call(MessageFactory);
                    MessageFactory.messages = ({});
                }
            }
            messages.MessageFactory = MessageFactory;
            MessageFactory["__class"] = "ca.ntro.messages.MessageFactory";
        })(messages = ntro.messages || (ntro.messages = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var tasks;
            (function (tasks) {
                class NtroTaskSync extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        this.runTask();
                        this.notifyTaskFinished();
                    }
                }
                tasks.NtroTaskSync = NtroTaskSync;
                NtroTaskSync["__class"] = "ca.ntro.core.tasks.NtroTaskSync";
                NtroTaskSync["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class ResourceLoaderTask extends ca.ntro.core.tasks.NtroTaskAsync {
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
                ResourceLoaderTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class ViewCreatorTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor() {
                        super();
                        if (this.view === undefined)
                            this.view = null;
                    }
                    getView() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.view;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        this.view = (this.getPreviousTask(ca.ntro.core.mvc.ViewLoader, ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID)).createView();
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ViewCreatorTask = ViewCreatorTask;
                ViewCreatorTask["__class"] = "ca.ntro.core.mvc.ViewCreatorTask";
                ViewCreatorTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(viewReceptor) {
                        super();
                        if (this.viewReceptor === undefined)
                            this.viewReceptor = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.viewReceptor = viewReceptor;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let view = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID)).getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(view);
                        this.viewReceptor.handle(view);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ViewHandlerTask = ViewHandlerTask;
                ViewHandlerTask["__class"] = "ca.ntro.core.mvc.ViewHandlerTask";
                ViewHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class WindowViewHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.view === undefined)
                            this.view = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewCreatorTask = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID));
                        ca.ntro.core.system.assertions.MustNot.beNull(viewCreatorTask);
                        let view = viewCreatorTask.getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(view);
                        this.handler.handleImpl(view);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.WindowViewHandlerTask = WindowViewHandlerTask;
                WindowViewHandlerTask["__class"] = "ca.ntro.core.mvc.WindowViewHandlerTask";
                WindowViewHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ControllerMessageHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler, messageId) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.messageId === undefined)
                            this.messageId = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                        this.messageId = messageId;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let currentView = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID)).getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(currentView);
                        let message = (this.getPreviousTask(ca.ntro.messages.NtroMessage, this.messageId));
                        this.handler.handleImpl(currentView, message);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ControllerMessageHandlerTask = ControllerMessageHandlerTask;
                ControllerMessageHandlerTask["__class"] = "ca.ntro.core.mvc.ControllerMessageHandlerTask";
                ControllerMessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class MessageHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler, messageId) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.messageId === undefined)
                            this.messageId = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                        this.messageId = messageId;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let message = (this.getPreviousTask(ca.ntro.messages.NtroMessage, this.messageId));
                        ca.ntro.core.system.assertions.MustNot.beNull(message);
                        this.handler.handleImpl(message);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.MessageHandlerTask = MessageHandlerTask;
                MessageHandlerTask["__class"] = "ca.ntro.core.mvc.MessageHandlerTask";
                MessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ModelMessageHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler, messageId) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.messageId === undefined)
                            this.messageId = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                        this.messageId = messageId;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let model = (this.getPreviousTask(ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID)).getModel();
                        ca.ntro.core.system.assertions.MustNot.beNull(model);
                        let message = (this.getPreviousTask(ca.ntro.messages.NtroMessage, this.messageId));
                        ca.ntro.core.system.assertions.MustNot.beNull(message);
                        this.handler.handleImpl(model, message);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ModelMessageHandlerTask = ModelMessageHandlerTask;
                ModelMessageHandlerTask["__class"] = "ca.ntro.core.mvc.ModelMessageHandlerTask";
                ModelMessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ModelViewHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewCreator = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID));
                        let model = (this.getPreviousTask(ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID)).getModel();
                        ca.ntro.core.system.assertions.MustNot.beNull(viewCreator);
                        ca.ntro.core.system.assertions.MustNot.beNull(model);
                        let view = viewCreator.getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(view);
                        this.handler.handleImpl(view, model);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ModelViewHandlerTask = ModelViewHandlerTask;
                ModelViewHandlerTask["__class"] = "ca.ntro.core.mvc.ModelViewHandlerTask";
                ModelViewHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ModelViewSubViewHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewCreator = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID));
                        let modelLoader = (this.getPreviousTask(ca.ntro.core.models.ModelLoader, ca.ntro.core.mvc.Constants.MODEL_LOADER_TASK_ID));
                        ca.ntro.core.system.assertions.MustNot.beNull(modelLoader);
                        let model = modelLoader.getModel();
                        ca.ntro.core.system.assertions.MustNot.beNull(viewCreator);
                        if (model == null) {
                            console.info("modelLoader " + (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(modelLoader.constructor));
                        }
                        ca.ntro.core.system.assertions.MustNot.beNull(model);
                        let view = viewCreator.getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(view);
                        this.handler.handleImpl(view, model);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ModelViewSubViewHandlerTask = ModelViewSubViewHandlerTask;
                ModelViewSubViewHandlerTask["__class"] = "ca.ntro.core.mvc.ModelViewSubViewHandlerTask";
                ModelViewSubViewHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ParentViewMessageHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler, messageId) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.messageId === undefined)
                            this.messageId = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                        this.messageId = messageId;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let currentView = (this.getPreviousTask(ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID)).getView();
                        ca.ntro.core.system.assertions.MustNot.beNull(currentView);
                        let message = (this.getPreviousTask(ca.ntro.messages.NtroMessage, this.messageId));
                        this.handler.handleImpl(currentView, message);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ParentViewMessageHandlerTask = ParentViewMessageHandlerTask;
                ParentViewMessageHandlerTask["__class"] = "ca.ntro.core.mvc.ParentViewMessageHandlerTask";
                ParentViewMessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewLoader extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor() {
                        super();
                    }
                    createView() {
                        ca.ntro.core.system.trace.T.call(this);
                        let view = this.createViewImpl();
                        view.initialize();
                        return view;
                    }
                }
                mvc.ViewLoader = ViewLoader;
                ViewLoader["__class"] = "ca.ntro.core.mvc.ViewLoader";
                ViewLoader["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "java.lang.Cloneable", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class ViewMessageHandlerTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(handler, messageId) {
                        super();
                        if (this.handler === undefined)
                            this.handler = null;
                        if (this.messageId === undefined)
                            this.messageId = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.handler = handler;
                        this.messageId = messageId;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let viewLoader = (this.getPreviousTask(ca.ntro.core.mvc.ViewLoader, ca.ntro.core.mvc.Constants.VIEW_LOADER_TASK_ID));
                        let message = (this.getPreviousTask(ca.ntro.messages.NtroMessage, this.messageId));
                        ca.ntro.core.system.assertions.MustNot.beNull(viewLoader);
                        ca.ntro.core.system.assertions.MustNot.beNull(message);
                        let view = viewLoader.createView();
                        ca.ntro.core.system.assertions.MustNot.beNull(view);
                        this.handler.handleImpl(view, message);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                mvc.ViewMessageHandlerTask = ViewMessageHandlerTask;
                ViewMessageHandlerTask["__class"] = "ca.ntro.core.mvc.ViewMessageHandlerTask";
                ViewMessageHandlerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var json;
            (function (json) {
                class JsonLoader extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor() {
                        super();
                    }
                }
                json.JsonLoader = JsonLoader;
                JsonLoader["__class"] = "ca.ntro.core.json.JsonLoader";
                JsonLoader["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(json = core.json || (core.json = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                class ModelLoader extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor(modelStore) {
                        super();
                        if (this.modelClass === undefined)
                            this.modelClass = null;
                        if (this.model === undefined)
                            this.model = null;
                        if (this.modelStore === undefined)
                            this.modelStore = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.modelStore = modelStore;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        let jsonLoader = (this.getSubTask(ca.ntro.core.json.JsonLoader, "JsonLoader"));
                        let jsonObject = jsonLoader.getJsonObject();
                        let documentPath = jsonLoader.getDocumentPath();
                        this.model = (ca.ntro.core.introspection.Factory.newInstance(this.modelClass));
                        this.model.setOrigin(this.modelStore);
                        this.model.loadFromJsonObject$ca_ntro_core_json_JsonObject(jsonObject);
                        this.model.setId(documentPath.getId());
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    getModel() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.model;
                    }
                    setTargetClass(modelClass) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.modelClass = modelClass;
                    }
                }
                models.ModelLoader = ModelLoader;
                ModelLoader["__class"] = "ca.ntro.core.models.ModelLoader";
                ModelLoader["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var messages;
        (function (messages) {
            class NtroMessage extends ca.ntro.core.tasks.NtroTaskAsync {
                constructor() {
                    super();
                }
                sendMessage() {
                    ca.ntro.core.system.trace.T.call(this);
                    this.notifyTaskFinished();
                    this.resetTask();
                    this.execute();
                }
                /**
                 *
                 */
                runTaskAsync() {
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
            messages.NtroMessage = NtroMessage;
            NtroMessage["__class"] = "ca.ntro.messages.NtroMessage";
            NtroMessage["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
        })(messages = ntro.messages || (ntro.messages = {}));
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
            var mvc;
            (function (mvc) {
                class NtroRootController extends ca.ntro.core.mvc.NtroAbstractController {
                    constructor() {
                        super();
                        if (this.window === undefined)
                            this.window = null;
                    }
                    setWindow(window) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.window = window;
                    }
                    getWindow() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.window;
                    }
                    addWindowViewHandler(handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        handler.setWindow(this.window);
                        this.getTask()['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                    }
                }
                mvc.NtroRootController = NtroRootController;
                NtroRootController["__class"] = "ca.ntro.core.mvc.NtroRootController";
                NtroRootController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class NtroController extends ca.ntro.core.mvc.NtroAbstractController {
                    constructor() {
                        super();
                        if (this.parentController === undefined)
                            this.parentController = null;
                    }
                    setParentController(parentController) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.parentController = parentController;
                    }
                    getParentController() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.parentController;
                    }
                    addParentViewMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let messageId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(messageClass);
                        let handlerId = ca.ntro.core.Ntro.introspector().getSimpleNameForClass(handler.constructor);
                        handler.setParentController(this.parentController);
                        handler.setMessageId(messageId);
                        handler.getTask().setTaskId(handlerId);
                        this.getTask()['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                        let messageReceptor = (ca.ntro.messages.MessageFactory.getIncomingMessage(messageClass));
                        ca.ntro.core.Ntro.messageService().registerHandlerTask(messageClass, messageReceptor);
                        messageReceptor.setTaskId(messageId);
                        handler.getTask()['addPreviousTask$ca_ntro_core_tasks_NtroTask'](messageReceptor);
                    }
                    addControllerMessageHandler(messageClass, handler) {
                        ca.ntro.core.system.trace.T.call(this);
                        let messageId = (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(messageClass);
                        handler.setController(this);
                        handler.setMessageId(messageId);
                        this.getTask()['addSubTask$ca_ntro_core_tasks_NtroTask'](handler.getTask());
                        this.addPreviousTaskTo$ca_ntro_core_tasks_NtroTask$java_lang_Class$java_lang_String(handler.getTask(), ca.ntro.core.mvc.ViewCreatorTask, ca.ntro.core.mvc.Constants.VIEW_CREATOR_TASK_ID);
                        let message = (ca.ntro.messages.MessageFactory.getIncomingMessage(messageClass));
                        message.setTaskId(messageId);
                        handler.getTask()['addPreviousTask$ca_ntro_core_tasks_NtroTask'](message);
                    }
                }
                mvc.NtroController = NtroController;
                NtroController["__class"] = "ca.ntro.core.mvc.NtroController";
                NtroController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class BackendController extends ca.ntro.core.mvc.BackendAbstractController {
                    constructor() {
                        super();
                        if (this.parentController === undefined)
                            this.parentController = null;
                    }
                    setParentController(parentController) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.parentController = parentController;
                    }
                }
                mvc.BackendController = BackendController;
                BackendController["__class"] = "ca.ntro.core.mvc.BackendController";
                BackendController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
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
                class BackendRootController extends ca.ntro.core.mvc.BackendAbstractController {
                    constructor() {
                        super();
                    }
                }
                mvc.BackendRootController = BackendRootController;
                BackendRootController["__class"] = "ca.ntro.core.mvc.BackendRootController";
                BackendRootController["__interfaces"] = ["ca.ntro.core.tasks.TaskWrapper"];
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var list;
                        (function (list_1) {
                            class ObservableList extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    super();
                                    /*private*/ this.listObservers = ([]);
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.setValue(ca.ntro.core.services.NtroCollections.synchronizedList(value));
                                }
                                size() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return this.getValue().length;
                                }
                                indexOf(item) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return this.getValue().indexOf(item);
                                }
                                getItem(id) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return this.getValue()[id];
                                }
                                insertItem(index, item) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    /* add */ this.getValue().splice(index, 0, item);
                                    for (let index178 = 0; index178 < this.listObservers.length; index178++) {
                                        let listObserver = this.listObservers[index178];
                                        {
                                            listObserver.onItemAdded(index, item);
                                        }
                                    }
                                }
                                updateItem(index, item) {
                                    /* set */ (this.getValue()[index] = item);
                                    for (let index179 = 0; index179 < this.listObservers.length; index179++) {
                                        let listObserver = this.listObservers[index179];
                                        {
                                            listObserver.onItemUpdated(index, item);
                                        }
                                    }
                                }
                                addItem(item) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.insertItem(/* size */ this.getValue().length, item);
                                }
                                removeItem(item) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let index = this.getValue().lastIndexOf(item);
                                    /* remove */ this.getValue().splice(index, 1)[0];
                                    for (let index180 = 0; index180 < this.listObservers.length; index180++) {
                                        let listObserver = this.listObservers[index180];
                                        {
                                            listObserver.onItemRemoved(index, item);
                                        }
                                    }
                                }
                                removeObserver(listObserver) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let removed = (a => { let index = a.indexOf(listObserver); if (index >= 0) {
                                        a.splice(index, 1);
                                        return true;
                                    }
                                    else {
                                        return false;
                                    } })(this.listObservers);
                                }
                                removeAllObservers() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    /* clear */ (this.listObservers.length = 0);
                                }
                                observe$ca_ntro_core_models_properties_observable_list_ListObserver(listObserver) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    /* add */ (this.listObservers.push(listObserver) > 0);
                                    let list = this.getValue();
                                    if (list != null) {
                                        {
                                            for (let i = 0; i < list.length; i++) {
                                                {
                                                    listObserver.onItemAdded(i, /* get */ list[i]);
                                                }
                                                ;
                                            }
                                        }
                                        ;
                                    }
                                }
                                observe(listObserver) {
                                    if (((listObserver != null && (listObserver["__interfaces"] != null && listObserver["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.list.ListObserver") >= 0 || listObserver.constructor != null && listObserver.constructor["__interfaces"] != null && listObserver.constructor["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.list.ListObserver") >= 0)) || listObserver === null)) {
                                        return this.observe$ca_ntro_core_models_properties_observable_list_ListObserver(listObserver);
                                    }
                                    else if (((listObserver != null && (listObserver["__interfaces"] != null && listObserver["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueObserver") >= 0 || listObserver.constructor != null && listObserver.constructor["__interfaces"] != null && listObserver.constructor["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueObserver") >= 0)) || listObserver === null)) {
                                        super.observe(listObserver);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                toJsonObject(serializedObjects, valuePath) {
                                    if (((serializedObjects != null && (serializedObjects instanceof Object)) || serializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null)) {
                                        super.toJsonObject(serializedObjects, valuePath);
                                    }
                                    else if (serializedObjects === undefined && valuePath === undefined) {
                                        return this.toJsonObject$();
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                toJsonObject$() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let result = ca.ntro.core.json.JsonParser.jsonObject();
                                    result.setTypeName(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(this.constructor));
                                    let list = this.getValue();
                                    let jsonList = ([]);
                                    for (let index181 = 0; index181 < list.length; index181++) {
                                        let item = list[index181];
                                        {
                                            let jsonItem = item;
                                            if (((o1, o2) => { if (o1 && o1.equals) {
                                                return o1.equals(o2);
                                            }
                                            else {
                                                return o1 === o2;
                                            } })(this.valueType(), ca.ntro.core.models.properties.NtroModelValue)) {
                                                jsonItem = item.toJsonObject().toMap();
                                            }
                                            /* add */ (jsonList.push(jsonItem) > 0);
                                        }
                                    }
                                    result.put("value", jsonList);
                                    return result;
                                }
                                loadFromJsonObject(deserializedObjects, valuePath, jsonObject) {
                                    if (((deserializedObjects != null && (deserializedObjects instanceof Object)) || deserializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null) && ((jsonObject != null && jsonObject instanceof ca.ntro.core.json.JsonObject) || jsonObject === null)) {
                                        super.loadFromJsonObject(deserializedObjects, valuePath, jsonObject);
                                    }
                                    else if (((deserializedObjects != null && deserializedObjects instanceof ca.ntro.core.json.JsonObject) || deserializedObjects === null) && valuePath === undefined && jsonObject === undefined) {
                                        return this.loadFromJsonObject$ca_ntro_core_json_JsonObject(deserializedObjects);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                loadFromJsonObject$ca_ntro_core_json_JsonObject(jsonObject) {
                                    let jsonList = jsonObject.get("value");
                                    for (let index182 = 0; index182 < jsonList.length; index182++) {
                                        let jsonItem = jsonList[index182];
                                        {
                                            let item = ca.ntro.core.Ntro.introspector().buildValueForType(this.valueType(), jsonItem);
                                            /* add */ (this.getValue().push(item) > 0);
                                        }
                                    }
                                }
                            }
                            list_1.ObservableList = ObservableList;
                            ObservableList["__class"] = "ca.ntro.core.models.properties.observable.list.ObservableList";
                            ObservableList["__interfaces"] = ["java.io.Serializable"];
                        })(list = observable.list || (observable.list = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var map;
                        (function (map_1) {
                            class ObservableMap extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    super(value);
                                    /*private*/ this.mapObservers = ([]);
                                    ca.ntro.core.system.trace.T.call(this);
                                }
                                size() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    return Object.keys(this.getValue()).length;
                                }
                                addEntry(key, value) {
                                    /* put */ (this.getValue()[key] = value);
                                    for (let index183 = 0; index183 < this.mapObservers.length; index183++) {
                                        let mapObserver = this.mapObservers[index183];
                                        {
                                            mapObserver.onEntryAdded(key, value);
                                        }
                                    }
                                }
                                removeEntry(key) {
                                    let value = ((m, k) => m[k] === undefined ? null : m[k])(this.getValue(), key);
                                    /* remove */ (map => { let deleted = this.getValue()[key]; delete this.getValue()[key]; return deleted; })(this.getValue());
                                    if (value != null) {
                                        for (let index184 = 0; index184 < this.mapObservers.length; index184++) {
                                            let mapObserver = this.mapObservers[index184];
                                            {
                                                mapObserver.onEntryRemoved(key, value);
                                            }
                                        }
                                    }
                                }
                                observe$ca_ntro_core_models_properties_observable_map_MapObserver(mapObserver) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    /* add */ (this.mapObservers.push(mapObserver) > 0);
                                    let map = this.getValue();
                                    if (map != null) {
                                        {
                                            {
                                                let array186 = Object.keys(map);
                                                for (let index185 = 0; index185 < array186.length; index185++) {
                                                    let key = array186[index185];
                                                    {
                                                        let value = ((m, k) => m[k] === undefined ? null : m[k])(map, key);
                                                        mapObserver.onEntryAdded(key, value);
                                                    }
                                                }
                                            }
                                        }
                                        ;
                                    }
                                }
                                observe(mapObserver) {
                                    if (((mapObserver != null && (mapObserver["__interfaces"] != null && mapObserver["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.map.MapObserver") >= 0 || mapObserver.constructor != null && mapObserver.constructor["__interfaces"] != null && mapObserver.constructor["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.map.MapObserver") >= 0)) || mapObserver === null)) {
                                        return this.observe$ca_ntro_core_models_properties_observable_map_MapObserver(mapObserver);
                                    }
                                    else if (((mapObserver != null && (mapObserver["__interfaces"] != null && mapObserver["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueObserver") >= 0 || mapObserver.constructor != null && mapObserver.constructor["__interfaces"] != null && mapObserver.constructor["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueObserver") >= 0)) || mapObserver === null)) {
                                        super.observe(mapObserver);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                toJsonObject(serializedObjects, valuePath) {
                                    if (((serializedObjects != null && (serializedObjects instanceof Object)) || serializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null)) {
                                        super.toJsonObject(serializedObjects, valuePath);
                                    }
                                    else if (serializedObjects === undefined && valuePath === undefined) {
                                        return this.toJsonObject$();
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                toJsonObject$() {
                                    ca.ntro.core.system.trace.T.call(this);
                                    let result = ca.ntro.core.json.JsonParser.jsonObject();
                                    result.setTypeName(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(this.constructor));
                                    let map = this.getValue();
                                    let jsonMap = ({});
                                    {
                                        let array188 = (o => { let s = []; for (let e in o)
                                            s.push({ k: e, v: o[e], getKey: function () { return this.k; }, getValue: function () { return this.v; } }); return s; })(map);
                                        for (let index187 = 0; index187 < array188.length; index187++) {
                                            let entry = array188[index187];
                                            {
                                                let jsonValue = entry.getValue();
                                                if (((o1, o2) => { if (o1 && o1.equals) {
                                                    return o1.equals(o2);
                                                }
                                                else {
                                                    return o1 === o2;
                                                } })(this.valueType(), ca.ntro.core.models.properties.NtroModelValue)) {
                                                    jsonValue = jsonValue.toJsonObject().toMap();
                                                }
                                                /* put */ (jsonMap[entry.getKey()] = jsonValue);
                                            }
                                        }
                                    }
                                    result.put("value", jsonMap);
                                    return result;
                                }
                                loadFromJsonObject(deserializedObjects, valuePath, jsonObject) {
                                    if (((deserializedObjects != null && (deserializedObjects instanceof Object)) || deserializedObjects === null) && ((typeof valuePath === 'string') || valuePath === null) && ((jsonObject != null && jsonObject instanceof ca.ntro.core.json.JsonObject) || jsonObject === null)) {
                                        super.loadFromJsonObject(deserializedObjects, valuePath, jsonObject);
                                    }
                                    else if (((deserializedObjects != null && deserializedObjects instanceof ca.ntro.core.json.JsonObject) || deserializedObjects === null) && valuePath === undefined && jsonObject === undefined) {
                                        return this.loadFromJsonObject$ca_ntro_core_json_JsonObject(deserializedObjects);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                loadFromJsonObject$ca_ntro_core_json_JsonObject(jsonObject) {
                                    let jsonMap = jsonObject.get("value");
                                    {
                                        let array190 = (o => { let s = []; for (let e in o)
                                            s.push({ k: e, v: o[e], getKey: function () { return this.k; }, getValue: function () { return this.v; } }); return s; })(jsonMap);
                                        for (let index189 = 0; index189 < array190.length; index189++) {
                                            let entry = array190[index189];
                                            {
                                                let entryValue = ca.ntro.core.Ntro.introspector().buildValueForType(this.valueType(), entry.getValue());
                                                /* put */ (this.getValue()[entry.getKey()] = entryValue);
                                            }
                                        }
                                    }
                                }
                            }
                            map_1.ObservableMap = ObservableMap;
                            ObservableMap["__class"] = "ca.ntro.core.models.properties.observable.map.ObservableMap";
                            ObservableMap["__interfaces"] = ["java.io.Serializable"];
                        })(map = observable.map || (observable.map = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var simple;
                        (function (simple) {
                            class ObservableBoolean extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    if (((typeof value === 'boolean') || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
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
                                    return Boolean;
                                }
                            }
                            simple.ObservableBoolean = ObservableBoolean;
                            ObservableBoolean["__class"] = "ca.ntro.core.models.properties.observable.simple.ObservableBoolean";
                            ObservableBoolean["__interfaces"] = ["java.io.Serializable"];
                        })(simple = observable.simple || (observable.simple = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var simple;
                        (function (simple) {
                            class ObservableDouble extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    if (((typeof value === 'number') || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
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
                                    return Number;
                                }
                            }
                            simple.ObservableDouble = ObservableDouble;
                            ObservableDouble["__class"] = "ca.ntro.core.models.properties.observable.simple.ObservableDouble";
                            ObservableDouble["__interfaces"] = ["java.io.Serializable"];
                        })(simple = observable.simple || (observable.simple = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var simple;
                        (function (simple) {
                            class ObservableString extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    if (((typeof value === 'string') || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
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
                                    return String;
                                }
                            }
                            simple.ObservableString = ObservableString;
                            ObservableString["__class"] = "ca.ntro.core.models.properties.observable.simple.ObservableString";
                            ObservableString["__interfaces"] = ["java.io.Serializable"];
                        })(simple = observable.simple || (observable.simple = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var observable;
                    (function (observable) {
                        var simple;
                        (function (simple) {
                            class ObservableValue extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    if (((value != null) || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
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
                            simple.ObservableValue = ObservableValue;
                            ObservableValue["__class"] = "ca.ntro.core.models.properties.observable.simple.ObservableValue";
                            ObservableValue["__interfaces"] = ["java.io.Serializable"];
                        })(simple = observable.simple || (observable.simple = {}));
                    })(observable = properties.observable || (properties.observable = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var stored;
                    (function (stored) {
                        var simple;
                        (function (simple) {
                            class StoredProperty extends ca.ntro.core.models.properties.observable.simple.ObservableProperty {
                                constructor(value) {
                                    if (((value != null) || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener = null;
                                        if (this.observer === undefined)
                                            this.observer = null;
                                        if (this.valuePath === undefined)
                                            this.valuePath = null;
                                        if (this.modelStore === undefined)
                                            this.modelStore = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener = null;
                                        if (this.observer === undefined)
                                            this.observer = null;
                                        if (this.valuePath === undefined)
                                            this.valuePath = null;
                                        if (this.modelStore === undefined)
                                            this.modelStore = null;
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super();
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener = null;
                                        if (this.observer === undefined)
                                            this.observer = null;
                                        if (this.valuePath === undefined)
                                            this.valuePath = null;
                                        if (this.modelStore === undefined)
                                            this.modelStore = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener = null;
                                        if (this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener === undefined)
                                            this.__ca_ntro_core_models_properties_stored_simple_StoredProperty_deletionListener = null;
                                        if (this.observer === undefined)
                                            this.observer = null;
                                        if (this.valuePath === undefined)
                                            this.valuePath = null;
                                        if (this.modelStore === undefined)
                                            this.modelStore = null;
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                setValuePath(valuePath) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.valuePath = valuePath;
                                }
                                get(valueListener) {
                                }
                                onDeleted(deletionListener) {
                                }
                                set(newValue) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.setValue(newValue);
                                    if (this.valuePath != null && this.modelStore != null) {
                                        this.modelStore.setValue(this.valuePath, this);
                                    }
                                }
                                observe(observer) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.observer = observer;
                                    if (this.getValue() != null) {
                                        if (this.getValue() != null && this.getValue() instanceof ca.ntro.core.models.properties.observable.simple.ObservableProperty) {
                                            observer.onValue(this.getValue().getValue());
                                        }
                                        else {
                                            observer.onValue(this.getValue());
                                        }
                                    }
                                }
                                /**
                                 *
                                 * @param {ca.ntro.core.services.stores.ValuePath} valuePath
                                 * @param {ca.ntro.core.models.ModelStore} modelStore
                                 */
                                connectToStore(valuePath, modelStore) {
                                    ca.ntro.core.system.trace.T.call(this);
                                    this.valuePath = valuePath;
                                    this.modelStore = modelStore;
                                    modelStore.addValueListener(valuePath, new StoredProperty.StoredProperty$0(this));
                                }
                            }
                            simple.StoredProperty = StoredProperty;
                            StoredProperty["__class"] = "ca.ntro.core.models.properties.stored.simple.StoredProperty";
                            StoredProperty["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                            (function (StoredProperty) {
                                class StoredProperty$0 {
                                    constructor(__parent) {
                                        this.__parent = __parent;
                                    }
                                    /**
                                     *
                                     * @param {*} newValue
                                     */
                                    onValue(newValue) {
                                        ca.ntro.core.system.trace.T.call(this);
                                        let oldValue = this.__parent.getValue();
                                        this.__parent.setValue(newValue);
                                        if (this.__parent.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener != null) {
                                            this.__parent.__ca_ntro_core_models_properties_stored_simple_StoredProperty_valueListener.onValue(newValue);
                                        }
                                        if (this.__parent.observer != null) {
                                            if (oldValue == null) {
                                                this.__parent.observer.onValue(newValue);
                                            }
                                            else {
                                                this.__parent.observer.onValueChanged(oldValue, newValue);
                                            }
                                        }
                                    }
                                }
                                StoredProperty.StoredProperty$0 = StoredProperty$0;
                                StoredProperty$0["__interfaces"] = ["ca.ntro.core.models.properties.observable.simple.ValueListener"];
                            })(StoredProperty = simple.StoredProperty || (simple.StoredProperty = {}));
                        })(simple = stored.simple || (stored.simple = {}));
                    })(stored = properties.stored || (properties.stored = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            class NtroUser extends ca.ntro.core.models.NtroModel {
                constructor() {
                    super();
                    if (this.__ca_ntro_core_NtroUser_id === undefined)
                        this.__ca_ntro_core_NtroUser_id = null;
                    if (this.authToken === undefined)
                        this.authToken = null;
                }
                /**
                 *
                 */
                initializeStoredValues() {
                }
                getId() {
                    return this.__ca_ntro_core_NtroUser_id;
                }
                setId(id) {
                    this.__ca_ntro_core_NtroUser_id = id;
                }
                getAuthToken() {
                    return this.authToken;
                }
                setAuthToken(authToken) {
                    this.authToken = authToken;
                }
                isValid(queryToken) {
                    ca.ntro.core.system.trace.T.call(this);
                    let isValid = false;
                    if (this.authToken != null) {
                        isValid = ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(this.authToken, queryToken);
                    }
                    return isValid;
                }
            }
            core.NtroUser = NtroUser;
            NtroUser["__class"] = "ca.ntro.core.NtroUser";
            NtroUser["__interfaces"] = ["java.io.Serializable"];
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
                class ContainerTask extends ca.ntro.core.tasks.NtroTaskSync {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    runTask() {
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
                ContainerTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(tasks = core.tasks || (core.tasks = {}));
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
                class InitializationTask extends ca.ntro.core.tasks.NtroTaskSync {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     */
                    runTask() {
                        ca.ntro.core.system.trace.__T.call(InitializationTask, "runSyncTask");
                        this.performInitialization();
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
                        ca.ntro.core.Ntro.__registerViewLoaderWeb(this.provideViewLoaderWebClass());
                        ca.ntro.core.services.ValueFormatter.initialize(this.provideValueFormatter());
                        ca.ntro.core.services.NtroCollections.initialize(this.provideNtroCollections());
                        ca.ntro.core.json.JsonParser.initialize(this.provideJsonParser());
                        ca.ntro.core.services.stores.LocalStore.initialize(this.provideLocalStore());
                        ca.ntro.core.services.stores.NetworkStore.initialize(this.provideNetworkStore());
                        ca.ntro.core.Ntro.zzz_registerThreadService(this.provideThreadService());
                        ca.ntro.core.Ntro.zzz_registerMessageServiceClass(this.provideMessageServiceClass());
                        ca.ntro.core.Ntro.zzz_registerBackendService(this.provideBackendService());
                    }
                }
                initialization.InitializationTask = InitializationTask;
                InitializationTask["__class"] = "ca.ntro.core.initialization.InitializationTask";
                InitializationTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class EmptyViewLoader extends ca.ntro.core.mvc.ViewLoader {
                    constructor() {
                        super();
                    }
                    /**
                     *
                     * @return {*}
                     */
                    createViewImpl() {
                        throw Object.defineProperty(new Error("should not be called!"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    /**
                     *
                     * @return {ca.ntro.core.mvc.ViewLoader}
                     */
                    clone() {
                        return new EmptyViewLoader();
                    }
                }
                mvc.EmptyViewLoader = EmptyViewLoader;
                EmptyViewLoader["__class"] = "ca.ntro.core.mvc.EmptyViewLoader";
                EmptyViewLoader["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "java.lang.Cloneable", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = core.mvc || (core.mvc = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var web;
        (function (web) {
            var mvc;
            (function (mvc) {
                class ViewLoaderWeb extends ca.ntro.core.mvc.ViewLoader {
                    constructor() {
                        super();
                        if (this.html === undefined)
                            this.html = null;
                        if (this.htmlUrl === undefined)
                            this.htmlUrl = null;
                        if (this.viewClass === undefined)
                            this.viewClass = null;
                        ca.ntro.core.system.trace.T.call(this);
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        this.html = (this.getSubTask(ca.ntro.core.services.ResourceLoaderTask, "Html")).getResourceAsString();
                        ca.ntro.core.system.assertions.MustNot.beNull(this.html);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    setHtmlUrl(htmlPath) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.htmlUrl = htmlPath;
                        let htmlLoader = ca.ntro.core.Ntro.resourceLoader().loadResourceTask(htmlPath);
                        htmlLoader.setTaskId("Html");
                        this.addSubTask$ca_ntro_core_tasks_NtroTask(htmlLoader);
                        return this;
                    }
                    setCssUrl(string) {
                        return this;
                    }
                    setTranslationsUrl(string) {
                        return this;
                    }
                    getHtml() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.html;
                    }
                    getHtmlUrl() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.htmlUrl;
                    }
                    setTargetClass(viewClass) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.viewClass = viewClass;
                        return this;
                    }
                    getTargetClass() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.viewClass;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    createViewImpl() {
                        ca.ntro.core.system.trace.T.call(this);
                        ca.ntro.core.system.assertions.MustNot.beNull(this.html);
                        let view = (ca.ntro.core.introspection.Factory.newInstance(this.viewClass));
                        let rootElement = this.parseHtml(this.html);
                        view.setRootElement(rootElement);
                        return view;
                    }
                }
                mvc.ViewLoaderWeb = ViewLoaderWeb;
                ViewLoaderWeb["__class"] = "ca.ntro.web.mvc.ViewLoaderWeb";
                ViewLoaderWeb["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "java.lang.Cloneable", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(mvc = web.mvc || (web.mvc = {}));
        })(web = ntro.web || (ntro.web = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var json;
            (function (json) {
                class JsonLoaderMemory extends ca.ntro.core.json.JsonLoader {
                    constructor(documentPath, jsonObject) {
                        super();
                        if (this.jsonObject === undefined)
                            this.jsonObject = null;
                        if (this.documentPath === undefined)
                            this.documentPath = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.jsonObject = jsonObject;
                        this.documentPath = documentPath;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    /**
                     *
                     * @return {ca.ntro.core.json.JsonObject}
                     */
                    getJsonObject() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.jsonObject;
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.stores.DocumentPath}
                     */
                    getDocumentPath() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.documentPath;
                    }
                }
                json.JsonLoaderMemory = JsonLoaderMemory;
                JsonLoaderMemory["__class"] = "ca.ntro.core.json.JsonLoaderMemory";
                JsonLoaderMemory["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(json = core.json || (core.json = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                class EmptyModelLoader extends ca.ntro.core.models.ModelLoader {
                    constructor() {
                        super(null);
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                    }
                }
                models.EmptyModelLoader = EmptyModelLoader;
                EmptyModelLoader["__class"] = "ca.ntro.core.models.EmptyModelLoader";
                EmptyModelLoader["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(models = core.models || (core.models = {}));
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
                            __T.traceFilter = ca.ntro.core.system.trace.TraceLevel.NONE_$LI$(); return __T.traceFilter; }
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
                            T.previousFilter = ca.ntro.core.system.trace.TraceLevel.NONE_$LI$(); return T.previousFilter; }
                        ;
                        static traceFilter_$LI$() { if (T.traceFilter == null)
                            T.traceFilter = ca.ntro.core.system.trace.TraceLevel.NONE_$LI$(); return T.traceFilter; }
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
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var stored;
                    (function (stored) {
                        var list;
                        (function (list) {
                            class StoredList extends ca.ntro.core.models.properties.observable.list.ObservableList {
                                constructor(value) {
                                    super(value);
                                    ca.ntro.core.system.trace.T.call(this);
                                }
                                slice(firstIndex, lastIndex) {
                                    return null;
                                }
                                size() {
                                    return 0;
                                }
                                first() {
                                    return this.get$int(0);
                                }
                                last() {
                                    return this.get$int(this.size() - 1);
                                }
                                get(valueListener) {
                                    if (((valueListener != null && (valueListener["__interfaces"] != null && valueListener["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueListener") >= 0 || valueListener.constructor != null && valueListener.constructor["__interfaces"] != null && valueListener.constructor["__interfaces"].indexOf("ca.ntro.core.models.properties.observable.simple.ValueListener") >= 0)) || valueListener === null)) {
                                        super.get(valueListener);
                                    }
                                    else if (((typeof valueListener === 'number') || valueListener === null)) {
                                        return this.get$int(valueListener);
                                    }
                                    else
                                        throw new Error('invalid overload');
                                }
                                get$int(index) {
                                    return null;
                                }
                                addItem(item) {
                                }
                                removeItem(item) {
                                }
                                setOnItemAddedListener(itemAddedListener) {
                                }
                                setOnItemRemovedListener(itemRemovedListener) {
                                }
                            }
                            list.StoredList = StoredList;
                            StoredList["__class"] = "ca.ntro.core.models.properties.stored.list.StoredList";
                            StoredList["__interfaces"] = ["java.io.Serializable"];
                        })(list = stored.list || (stored.list = {}));
                    })(stored = properties.stored || (properties.stored = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var stored;
                    (function (stored) {
                        var map;
                        (function (map) {
                            class StoredMap extends ca.ntro.core.models.properties.observable.map.ObservableMap {
                                constructor(value) {
                                    super(value);
                                    ca.ntro.core.system.trace.T.call(this);
                                }
                                addEntry(key, value) {
                                }
                                removeEntry(key) {
                                }
                            }
                            map.StoredMap = StoredMap;
                            StoredMap["__class"] = "ca.ntro.core.models.properties.stored.map.StoredMap";
                            StoredMap["__interfaces"] = ["java.io.Serializable"];
                        })(map = stored.map || (stored.map = {}));
                    })(stored = properties.stored || (properties.stored = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var stored;
                    (function (stored) {
                        var simple;
                        (function (simple) {
                            class StoredBoolean extends ca.ntro.core.models.properties.stored.simple.StoredProperty {
                                constructor(value) {
                                    if (((typeof value === 'boolean') || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super(false);
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
                                    return Boolean;
                                }
                            }
                            simple.StoredBoolean = StoredBoolean;
                            StoredBoolean["__class"] = "ca.ntro.core.models.properties.stored.simple.StoredBoolean";
                            StoredBoolean["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                        })(simple = stored.simple || (stored.simple = {}));
                    })(stored = properties.stored || (properties.stored = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var core;
        (function (core) {
            var models;
            (function (models) {
                var properties;
                (function (properties) {
                    var stored;
                    (function (stored) {
                        var simple;
                        (function (simple) {
                            class StoredString extends ca.ntro.core.models.properties.stored.simple.StoredProperty {
                                constructor(value) {
                                    if (((typeof value === 'string') || value === null)) {
                                        let __args = arguments;
                                        super(value);
                                        (() => {
                                            ca.ntro.core.system.trace.T.call(this);
                                        })();
                                    }
                                    else if (value === undefined) {
                                        let __args = arguments;
                                        super("");
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
                                    return String;
                                }
                                test() {
                                    ca.ntro.core.system.trace.T.call(this);
                                }
                            }
                            simple.StoredString = StoredString;
                            StoredString["__class"] = "ca.ntro.core.models.properties.stored.simple.StoredString";
                            StoredString["__interfaces"] = ["ca.ntro.core.models.StoreConnectable", "java.io.Serializable"];
                        })(simple = stored.simple || (stored.simple = {}));
                    })(stored = properties.stored || (properties.stored = {}));
                })(properties = models.properties || (models.properties = {}));
            })(models = core.models || (core.models = {}));
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
                        super();
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
                NtroInitializationTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(initialization = core.initialization || (core.initialization = {}));
        })(core = ntro.core || (ntro.core = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
var __Function = Function;
ca.ntro.core.system.trace.T.traceFilter_$LI$();
ca.ntro.core.system.trace.T.previousFilter_$LI$();
ca.ntro.core.system.trace.__T.traceFilter_$LI$();
ca.ntro.core.system.trace.TraceLevel.ALL_$LI$();
ca.ntro.core.system.trace.TraceLevel.NTRO_ALL_$LI$();
ca.ntro.core.system.trace.TraceLevel.NTRO_$LI$();
ca.ntro.core.system.trace.TraceLevel.APP_$LI$();
ca.ntro.core.system.trace.TraceLevel.NONE_$LI$();
ca.ntro.messages.MessageFactory.tasks_$LI$();
ca.ntro.messages.MessageFactory.handlers_$LI$();
ca.ntro.messages.MessageFactory.messages_$LI$();
ca.ntro.core.services.stores.MemoryStore.instance_$LI$();
ca.ntro.messages.MessageReceptors.messageReceptors_$LI$();
ca.ntro.core.Ntro.messageServices_$LI$();
ca.ntro.core.json.JsonParser.classes_$LI$();
ca.ntro.core.mvc.ViewLoaders.viewLoaders_$LI$();
ca.ntro.core.tasks.NtroTaskImpl.classIds_$LI$();
//# sourceMappingURL=bundle.js.map
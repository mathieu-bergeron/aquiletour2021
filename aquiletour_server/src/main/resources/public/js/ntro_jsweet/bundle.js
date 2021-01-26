/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var debug;
            (function (debug) {
                function installSourceMapAnalyzer(sourceMapAnalyserFunction) {
                    ca.ntro.jsweet.debug.SourceMapAnalyzer.initialize(sourceMapAnalyserFunction);
                }
                debug.installSourceMapAnalyzer = installSourceMapAnalyzer;
            })(debug = jsweet.debug || (jsweet.debug = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var debug;
            (function (debug) {
                class SourceMapAnalyzer {
                    static initialize(sourceMapAnalyzer) {
                        SourceMapAnalyzer.analyzerFunction = sourceMapAnalyzer;
                    }
                    static getOriginalLocation(line, column) {
                        let lineColumn = new Object();
                        lineColumn["line"] = line;
                        lineColumn["column"] = column;
                        let result = (SourceMapAnalyzer.analyzerFunction(lineColumn));
                        let fileName = (result["source"]);
                        let resultLine = (result["line"]);
                        return new ca.ntro.core.system.source.SourceFileLocation(fileName, resultLine);
                    }
                }
                SourceMapAnalyzer.analyzerFunction = null;
                debug.SourceMapAnalyzer = SourceMapAnalyzer;
                SourceMapAnalyzer["__class"] = "ca.ntro.jsweet.debug.SourceMapAnalyzer";
            })(debug = jsweet.debug || (jsweet.debug = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var debug;
            (function (debug) {
                class StackAnalyzerJSweet extends ca.ntro.core.system.stack.StackAnalyzer {
                    /**
                     *
                     * @param {string} className
                     * @param {number} finalStackOffset
                     * @return {ca.ntro.core.system.stack.StackFrame}
                     */
                    getTracedFrameImpl(className, finalStackOffset) {
                        let result = null;
                        let error = new Error();
                        let stackLines = error.stack.split("\n");
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(stackLines[0], "Error")) {
                            result = this.parseChromiumStackLines(className, finalStackOffset, stackLines);
                        }
                        else {
                            result = this.parseFirefoxStackLines(className, finalStackOffset, stackLines);
                        }
                        return result;
                    }
                    /*private*/ parseFirefoxStackLines(className, finalStackOffset, stackLines) {
                        let stackLine = stackLines[finalStackOffset];
                        let methodName = stackLine.split("@")[0];
                        let colonSplits = stackLine.split(":");
                        let rawLineNumber = colonSplits[colonSplits.length - 2];
                        let rawColumnNumber = colonSplits[colonSplits.length - 1];
                        let line = parseFloat(rawLineNumber);
                        let column = parseFloat(rawColumnNumber);
                        let location = ca.ntro.jsweet.debug.SourceMapAnalyzer.getOriginalLocation(line, column);
                        return new ca.ntro.core.system.stack.StackFrame(className, methodName, location);
                    }
                    /*private*/ parseChromiumStackLines(className, finalStackOffset, stackLines) {
                        let stackLine = stackLines[finalStackOffset + 1];
                        while ((((str, searchString, position = 0) => str.substr(position, searchString.length) === searchString)(stackLine, " "))) {
                            {
                                stackLine = stackLine.substring(1);
                            }
                        }
                        ;
                        let methodName = stackLine.split("at ")[1];
                        if (((str, searchString, position = 0) => str.substr(position, searchString.length) === searchString)(methodName, "new")) {
                            methodName = methodName.split("new ")[1];
                        }
                        else {
                            methodName = methodName.split(".")[1];
                            methodName = methodName.split(" ")[0];
                        }
                        let colonSplits = stackLine.split(":");
                        let rawLineNumber = colonSplits[colonSplits.length - 2];
                        let rawColumnNumber = colonSplits[colonSplits.length - 1].split(")").join("");
                        let lineNumber = parseFloat(rawLineNumber);
                        let columnNumber = parseFloat(rawColumnNumber);
                        let location = ca.ntro.jsweet.debug.SourceMapAnalyzer.getOriginalLocation(lineNumber, columnNumber);
                        return new ca.ntro.core.system.stack.StackFrame(className, methodName, location);
                    }
                    /**
                     *
                     * @return {number}
                     */
                    getInitialStackOffset() {
                        return 1;
                    }
                }
                debug.StackAnalyzerJSweet = StackAnalyzerJSweet;
                StackAnalyzerJSweet["__class"] = "ca.ntro.jsweet.debug.StackAnalyzerJSweet";
            })(debug = jsweet.debug || (jsweet.debug = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var dom;
            (function (dom) {
                class HtmlDocumentJSweet {
                    constructor() {
                    }
                    /**
                     *
                     * @param {string} cssQuery
                     * @return {*}
                     */
                    select(cssQuery) {
                        return new ca.ntro.jsweet.dom.HtmlElementsJSweet($(cssQuery));
                    }
                    /**
                     *
                     * @param {{ str: string, toString: Function }} out
                     */
                    writeHtml(out) {
                        ca.ntro.core.system.trace.T.call(this);
                        /* append */ (sb => { sb.str = sb.str.concat($(document).html()); return sb; })(out);
                    }
                }
                dom.HtmlDocumentJSweet = HtmlDocumentJSweet;
                HtmlDocumentJSweet["__class"] = "ca.ntro.jsweet.dom.HtmlDocumentJSweet";
                HtmlDocumentJSweet["__interfaces"] = ["ca.ntro.core.web.dom.HtmlDocument"];
            })(dom = jsweet.dom || (jsweet.dom = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var dom;
            (function (dom) {
                class HtmlElementJSweet {
                    constructor(jQueryElement) {
                        if (this.jQueryElement === undefined)
                            this.jQueryElement = null;
                        this.jQueryElement = jQueryElement;
                    }
                    /**
                     *
                     * @param {string} newText
                     */
                    text(newText) {
                        this.jQueryElement.text(newText);
                    }
                    /**
                     *
                     * @param {string} event
                     * @param {*} listener
                     */
                    addEventListener(event, listener) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.jQueryElement.on(event, (t, u) => {
                            ca.ntro.core.system.trace.T.call(this);
                            t.preventDefault();
                            listener.onEvent();
                            return null;
                        });
                    }
                }
                dom.HtmlElementJSweet = HtmlElementJSweet;
                HtmlElementJSweet["__class"] = "ca.ntro.jsweet.dom.HtmlElementJSweet";
                HtmlElementJSweet["__interfaces"] = ["ca.ntro.core.web.dom.HtmlElement"];
            })(dom = jsweet.dom || (jsweet.dom = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var dom;
            (function (dom) {
                class HtmlElementsJSweet {
                    constructor(jQueryElements) {
                        if (this.jQueryElements === undefined)
                            this.jQueryElements = null;
                        this.jQueryElements = jQueryElements;
                    }
                    /**
                     *
                     * @param {number} index
                     * @return {*}
                     */
                    get(index) {
                        return new ca.ntro.jsweet.dom.HtmlElementJSweet($(this.jQueryElements.get(index)));
                    }
                }
                dom.HtmlElementsJSweet = HtmlElementsJSweet;
                HtmlElementsJSweet["__class"] = "ca.ntro.jsweet.dom.HtmlElementsJSweet";
                HtmlElementsJSweet["__interfaces"] = ["ca.ntro.core.web.dom.HtmlElements"];
            })(dom = jsweet.dom || (jsweet.dom = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var initialization;
            (function (initialization) {
                class InitializationTaskJSweet extends ca.ntro.core.initialization.InitializationTask {
                    /**
                     *
                     * @return {ca.ntro.core.system.stack.StackAnalyzer}
                     */
                    provideStackAnalyzer() {
                        ca.ntro.core.system.trace.__T.call(this, "provideStackAnalyzer");
                        return new ca.ntro.jsweet.debug.StackAnalyzerJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.introspection.Introspector}
                     */
                    provideIntrospector() {
                        ca.ntro.core.system.trace.T.call(this);
                        return new ca.ntro.jsweet.introspection.IntrospectorJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.Logger}
                     */
                    provideLogger() {
                        ca.ntro.core.system.trace.__T.call(this, "provideLogger");
                        return new ca.ntro.jsweet.services.LoggerJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.AppCloser}
                     */
                    provideAppCloser() {
                        ca.ntro.core.system.trace.__T.call(this, "provideAppCloser");
                        return new ca.ntro.jsweet.services.AppCloserJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.regex.RegEx}
                     */
                    provideRegEx() {
                        ca.ntro.core.system.trace.__T.call(this, "provideRegEx");
                        return new ca.ntro.jsweet.regex.RegExJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.ValueFormatter}
                     */
                    provideValueFormatter() {
                        ca.ntro.core.system.trace.__T.call(this, "provideValueFormatter");
                        return new ca.ntro.jsweet.services.ValueFormatterJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.NtroCollections}
                     */
                    provideNtroCollections() {
                        ca.ntro.core.system.trace.__T.call(this, "provideNtroCollections");
                        return new ca.ntro.jsweet.services.NtroCollectionsJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.ResourceLoader}
                     */
                    provideResourceLoader() {
                        ca.ntro.core.system.trace.__T.call(this, "provideResourceLoader");
                        return new ca.ntro.jsweet.services.ResourceLoaderJSweet();
                    }
                }
                initialization.InitializationTaskJSweet = InitializationTaskJSweet;
                InitializationTaskJSweet["__class"] = "ca.ntro.jsweet.initialization.InitializationTaskJSweet";
            })(initialization = jsweet.initialization || (jsweet.initialization = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var initialization;
            (function (initialization) {
                class LoadSourceMapTask extends ca.ntro.core.tasks.NtroTask {
                    constructor(path) {
                        super();
                        this.addSubTask(new ca.ntro.jsweet.services.ResourceLoaderTaskJsweet(path));
                    }
                    /**
                     *
                     */
                    runTask() {
                        let sourceMap = this.getSubTask(ca.ntro.jsweet.services.ResourceLoaderTaskJsweet).getResourceAsString();
                        installSourceMap(sourceMap);
                        this.notifyTaskFinished();
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                initialization.LoadSourceMapTask = LoadSourceMapTask;
                LoadSourceMapTask["__class"] = "ca.ntro.jsweet.initialization.LoadSourceMapTask";
            })(initialization = jsweet.initialization || (jsweet.initialization = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var initialization;
            (function (initialization) {
                class OnLoadTask extends ca.ntro.core.tasks.NtroTask {
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
                initialization.OnLoadTask = OnLoadTask;
                OnLoadTask["__class"] = "ca.ntro.jsweet.initialization.OnLoadTask";
            })(initialization = jsweet.initialization || (jsweet.initialization = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var introspection;
            (function (introspection) {
                class IntrospectorJSweet extends ca.ntro.core.introspection.Introspector {
                    /**
                     *
                     * @param {{ owner: any, name: string, fn : Function }} setter
                     * @param {*} jsonValue
                     * @return {*}
                     */
                    buildValueForSetter(setter, jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.buildValue(jsonValue);
                    }
                    /*private*/ buildValue(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        let result = null;
                        if (this.isAMap(jsonValue)) {
                            result = this.buildMap(jsonValue);
                        }
                        else if (this.isAList(jsonValue)) {
                            result = this.buildList(jsonValue);
                        }
                        else {
                            result = this.buildSimpleValue(jsonValue);
                        }
                        return result;
                    }
                    /*private*/ buildSimpleValue(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        return jsonValue;
                    }
                    /*private*/ buildList(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        let inputList = jsonValue;
                        let outputList = ([]);
                        for (let index138 = 0; index138 < inputList.length; index138++) {
                            let inputValue = inputList[index138];
                            {
                                let outputValue = this.buildValue(inputValue);
                                /* add */ (outputList.push(outputValue) > 0);
                            }
                        }
                        return outputList;
                    }
                    /*private*/ buildMap(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        let inputMap = jsonValue;
                        let outputMap = ({});
                        {
                            let array140 = Object.keys(inputMap);
                            for (let index139 = 0; index139 < array140.length; index139++) {
                                let key = array140[index139];
                                {
                                    let inputValue = ((m, k) => m[k] === undefined ? null : m[k])(inputMap, key);
                                    let outputValue = this.buildValue(inputValue);
                                    /* put */ (outputMap[key] = outputValue);
                                }
                            }
                        }
                        return outputMap;
                    }
                    /*private*/ isAList(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        return jsonValue != null && ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(jsonValue.constructor), "Array");
                    }
                    /*private*/ isAMap(jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        return jsonValue != null && ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(/* getName */ (c => c["__class"] ? c["__class"] : c["name"])(jsonValue.constructor), "Object");
                    }
                    /**
                     *
                     * @param {*} superType
                     * @param {*} jsonValue
                     * @return {*}
                     */
                    buildValueForType(superType, jsonValue) {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.buildValue(jsonValue);
                    }
                    /**
                     *
                     * @param {*} javaClass
                     * @return {{ owner: any, name: string, fn : Function }[]}
                     */
                    userDefinedMethodsFromClass(javaClass) {
                        ca.ntro.core.system.trace.T.call(this);
                        let result = ([]);
                        let jsClass = javaClass;
                        /* addAll */ ((l1, l2) => l1.push.apply(l1, l2))(result, this.methods(javaClass, jsClass));
                        return result;
                    }
                    /*private*/ methods(owner, jsClass) {
                        ca.ntro.core.system.trace.T.call(this);
                        let result = ([]);
                        /* addAll */ ((l1, l2) => l1.push.apply(l1, l2))(result, this.declaredMethods(owner, jsClass));
                        let jsSuperClass = this.jsSuperClass(jsClass);
                        if (jsSuperClass != null) {
                            /* addAll */ ((l1, l2) => l1.push.apply(l1, l2))(result, this.methods(owner, jsSuperClass));
                        }
                        return result;
                    }
                    /*private*/ declaredMethods(owner, jsClass) {
                        ca.ntro.core.system.trace.T.call(this);
                        let result = ([]);
                        let prototype = (jsClass["prototype"]);
                        if (prototype != null) {
                            let prototypeMethods = Object.getOwnPropertyNames(prototype);
                            for (let index141 = 0; index141 < prototypeMethods.length; index141++) {
                                let method = prototypeMethods[index141];
                                {
                                    let methodName = method.toString();
                                    if (!this.isAJsSpecialFunction(methodName)) {
                                        let jsMethod = new Object();
                                        let fn = (prototype[methodName]);
                                        jsMethod["fn"] = fn;
                                        jsMethod["name"] = methodName;
                                        jsMethod["owner"] = owner;
                                        let methodAsObject = jsMethod;
                                        let javaMethod = methodAsObject;
                                        /* add */ (result.push(javaMethod) > 0);
                                    }
                                }
                            }
                        }
                        return result;
                    }
                    /*private*/ isAJsSpecialFunction(functionName) {
                        ca.ntro.core.system.trace.T.call(this);
                        let result = false;
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(functionName, "constructor")) {
                            result = true;
                        }
                        return result;
                    }
                    /*private*/ jsSuperClass(jsObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        let superType = (jsObject["__proto__"]);
                        return superType;
                    }
                    /**
                     *
                     * @param {{ owner: any, name: string, fn : Function }} method
                     * @return {ca.ntro.core.introspection.MethodSignature}
                     */
                    methodSignature(method) {
                        throw Object.defineProperty(new Error("TODO: IntrospectorJSweet.methodSignature"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                    /**
                     *
                     * @param {*} javaClass
                     * @return {ca.ntro.core.introspection.FieldSignature[]}
                     */
                    userDefinedFieldsFromClass(javaClass) {
                        throw Object.defineProperty(new Error("TODO: IntrospectorJSweet.userDefinedFieldsFromClass"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                    /**
                     *
                     * @param {*} object
                     * @return {boolean}
                     */
                    isClass(object) {
                        return ((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(/* getSimpleName */ (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(object.constructor), "Function");
                    }
                }
                introspection.IntrospectorJSweet = IntrospectorJSweet;
                IntrospectorJSweet["__class"] = "ca.ntro.jsweet.introspection.IntrospectorJSweet";
            })(introspection = jsweet.introspection || (jsweet.introspection = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            class NtroJSweet {
                static defaultInitializationTask() {
                    ca.ntro.core.system.trace.__T.call(NtroJSweet, "defaultInitializationTask");
                    let initializationTask = new ca.ntro.core.initialization.NtroInitializationTask();
                    let initJSweet = new ca.ntro.jsweet.initialization.InitializationTaskJSweet();
                    initializationTask.addSubTask(initJSweet);
                    initializationTask.addSubTask(jsweet.onLoadTask_$LI$());
                    return initializationTask;
                }
                static viewLoader() {
                    return new ca.ntro.core.mvc.view.ViewLoaderJWeb();
                }
            }
            jsweet.NtroJSweet = NtroJSweet;
            NtroJSweet["__class"] = "ca.ntro.jsweet.NtroJSweet";
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var regex;
            (function (regex) {
                class RegExJSweet extends ca.ntro.core.regex.RegEx {
                    /**
                     *
                     * @param {string} pattern
                     * @return {ca.ntro.core.regex.Pattern}
                     */
                    compilePattern(pattern) {
                        throw Object.defineProperty(new Error("TODO: RegExJSweet"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                }
                regex.RegExJSweet = RegExJSweet;
                RegExJSweet["__class"] = "ca.ntro.jsweet.regex.RegExJSweet";
            })(regex = jsweet.regex || (jsweet.regex = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class AppCloserJSweet extends ca.ntro.core.services.AppCloser {
                    /**
                     *
                     */
                    close() {
                        alert("[Ntro] AppCloser.close()");
                    }
                }
                services.AppCloserJSweet = AppCloserJSweet;
                AppCloserJSweet["__class"] = "ca.ntro.jsweet.services.AppCloserJSweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class LoggerJSweet extends ca.ntro.core.services.Logger {
                    /**
                     *
                     * @param {*} value
                     */
                    value(value) {
                        console.info(value);
                    }
                }
                services.LoggerJSweet = LoggerJSweet;
                LoggerJSweet["__class"] = "ca.ntro.jsweet.services.LoggerJSweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class NtroCollectionsJSweet extends ca.ntro.core.services.NtroCollections {
                    /**
                     *
                     * @param {*[]} elements
                     * @return {*[]}
                     */
                    synchronizedListImpl(elements) {
                        let list = ([]);
                        /* addAll */ ((l1, l2) => l1.push.apply(l1, l2))(list, elements);
                        return list;
                    }
                    /**
                     *
                     * @param {*} elements
                     * @return {*}
                     */
                    concurrentHashMapImpl(elements) {
                        let map = ({});
                        map.putAll(elements);
                        return map;
                    }
                }
                services.NtroCollectionsJSweet = NtroCollectionsJSweet;
                NtroCollectionsJSweet["__class"] = "ca.ntro.jsweet.services.NtroCollectionsJSweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class ResourceLoaderJSweet extends ca.ntro.core.services.ResourceLoader {
                    /**
                     *
                     * @param {string} resourcePath
                     * @return {ca.ntro.core.services.ResourceLoaderTask}
                     */
                    loadResourceTask(resourcePath) {
                        return new ca.ntro.jsweet.services.ResourceLoaderTaskJsweet(ca.ntro.core.Constants.RESOURCES_URL_PREFIX + "/" + resourcePath);
                    }
                }
                services.ResourceLoaderJSweet = ResourceLoaderJSweet;
                ResourceLoaderJSweet["__class"] = "ca.ntro.jsweet.services.ResourceLoaderJSweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class ResourceLoaderTaskJsweet extends ca.ntro.core.services.ResourceLoaderTask {
                    constructor(resourcePath) {
                        super(resourcePath);
                        if (this.resourceAsString === undefined)
                            this.resourceAsString = null;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getResourceAsString() {
                        return this.resourceAsString;
                    }
                    /**
                     *
                     */
                    runTask() {
                        fetch(this.getResourcePath()).then((response) => {
                            response.text().then((text) => {
                                this.resourceAsString = text;
                                this.notifyTaskFinished();
                            });
                        });
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                }
                services.ResourceLoaderTaskJsweet = ResourceLoaderTaskJsweet;
                ResourceLoaderTaskJsweet["__class"] = "ca.ntro.jsweet.services.ResourceLoaderTaskJsweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var services;
            (function (services) {
                class ValueFormatterJSweet extends ca.ntro.core.services.ValueFormatter {
                    /**
                     *
                     * @param {{ str: string, toString: Function }} builder
                     * @param {boolean} isHtml
                     * @param {Array} values
                     */
                    formatImpl(builder, isHtml, ...values) {
                        throw Object.defineProperty(new Error("TODO: ValueFormatterJSweet"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                }
                services.ValueFormatterJSweet = ValueFormatterJSweet;
                ValueFormatterJSweet["__class"] = "ca.ntro.jsweet.services.ValueFormatterJSweet";
            })(services = jsweet.services || (jsweet.services = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            function onLoadTask_$LI$() { if (jsweet.onLoadTask == null)
                jsweet.onLoadTask = new ca.ntro.jsweet.initialization.OnLoadTask(); return jsweet.onLoadTask; }
            jsweet.onLoadTask_$LI$ = onLoadTask_$LI$;
            ;
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
//# sourceMappingURL=bundle.js.map
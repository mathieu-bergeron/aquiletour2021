/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
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
                        for (let index193 = 0; index193 < inputList.length; index193++) {
                            let inputValue = inputList[index193];
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
                            let array195 = Object.keys(inputMap);
                            for (let index194 = 0; index194 < array195.length; index194++) {
                                let key = array195[index194];
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
                     * @param {*} clazz
                     * @return {string}
                     */
                    getSimpleNameForClass(clazz) {
                        let ret;
                        if (((o1, o2) => { if (o1 && o1.equals) {
                            return o1.equals(o2);
                        }
                        else {
                            return o1 === o2;
                        } })(typeof clazz, "string")) {
                            let interfaceName = new String(clazz);
                            ret = interfaceName.substring(interfaceName.lastIndexOf(".") + 1, interfaceName.length - 1).toString();
                        }
                        else {
                            ret = (c => c["__class"] ? c["__class"].substring(c["__class"].lastIndexOf('.') + 1) : c["name"].substring(c["name"].lastIndexOf('.') + 1))(clazz);
                        }
                        return ret;
                    }
                    /**
                     *
                     * @param {*} clazz
                     * @return {string}
                     */
                    getFullNameForClass(clazz) {
                        return (c => c["__class"] ? c["__class"] : c["name"])(clazz);
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
                            for (let index196 = 0; index196 < prototypeMethods.length; index196++) {
                                let method = prototypeMethods[index196];
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
                    /**
                     *
                     * @param {*} object
                     * @return {boolean}
                     */
                    isMap(object) {
                        return (object != null && (object instanceof Object));
                    }
                    /**
                     *
                     * @param {*} object
                     * @return {boolean}
                     */
                    isList(object) {
                        return (object != null && (object instanceof Array));
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
                    static getOriginalLocation(fileName, line, column) {
                        let lineColumn = new Object();
                        lineColumn["fileName"] = fileName;
                        lineColumn["line"] = line;
                        lineColumn["column"] = column;
                        let result = (SourceMapAnalyzer.analyzerFunction(lineColumn));
                        let resultingFileName = (result["source"]);
                        let resultLine = (result["line"]);
                        return new ca.ntro.core.system.source.SourceFileLocation(resultingFileName, resultLine);
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
                        let filePrefix = window.location.origin;
                        let fileName = stackLine.split("@")[1].split(filePrefix).join("").split(":")[0];
                        let methodName = stackLine.split("@")[0];
                        let colonSplits = stackLine.split(":");
                        let rawLineNumber = colonSplits[colonSplits.length - 2];
                        let rawColumnNumber = colonSplits[colonSplits.length - 1];
                        let line = parseFloat(rawLineNumber);
                        let column = parseFloat(rawColumnNumber);
                        let location = ca.ntro.jsweet.debug.SourceMapAnalyzer.getOriginalLocation(fileName, line, column);
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
                        let location = ca.ntro.jsweet.debug.SourceMapAnalyzer.getOriginalLocation("something", lineNumber, columnNumber);
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
                class LocalStoreJSweet extends ca.ntro.core.models.ModelStore {
                    constructor() {
                        super(...arguments);
                        this.localStorage = window.localStorage;
                    }
                    /**
                     *
                     * @param {*} updateListener
                     */
                    installExternalUpdateListener(updateListener) {
                        ca.ntro.core.system.trace.T.call(this);
                        window.addEventListener("storage", (evt) => {
                            ca.ntro.core.system.trace.T.call(this);
                            updateListener.onExternalUpdate();
                        });
                    }
                    /*private*/ fullId(documentPath) {
                        return documentPath.getCollection() + "_" + documentPath.getId();
                    }
                    /**
                     *
                     * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                     * @return {ca.ntro.core.json.JsonLoader}
                     */
                    getJsonObject(documentPath) {
                        ca.ntro.core.system.trace.T.call(this);
                        let fullId = this.fullId(documentPath);
                        let jsonString = this.localStorage.getItem(fullId);
                        let jsonObject = null;
                        if (jsonString != null) {
                            jsonObject = ca.ntro.core.json.JsonParser.fromString(jsonString);
                        }
                        else {
                            jsonObject = ca.ntro.core.json.JsonParser.jsonObject();
                        }
                        let jsonLoader = new ca.ntro.core.json.JsonLoaderMemory(documentPath, jsonObject);
                        return jsonLoader;
                    }
                    /**
                     *
                     * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                     * @param {ca.ntro.core.json.JsonObject} jsonObject
                     */
                    saveJsonObject(documentPath, jsonObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        let jsonString = ca.ntro.core.json.JsonParser.toString(jsonObject);
                        let fullId = this.fullId(documentPath);
                        this.localStorage.setItem(fullId, jsonString);
                    }
                    /**
                     *
                     */
                    close() {
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
                    }
                }
                services.LocalStoreJSweet = LocalStoreJSweet;
                LocalStoreJSweet["__class"] = "ca.ntro.jsweet.services.LocalStoreJSweet";
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
                class NtroWindowJSweet extends ca.ntro.web.NtroWindowWeb {
                    constructor() {
                        super(...arguments);
                        /*private*/ this.document = new ca.ntro.jsweet.dom.HtmlDocumentJSweet();
                    }
                    /**
                     *
                     * @return {*}
                     */
                    getDocument() {
                        ca.ntro.core.system.trace.T.call(this);
                        return this.document;
                    }
                }
                services.NtroWindowJSweet = NtroWindowJSweet;
                NtroWindowJSweet["__class"] = "ca.ntro.jsweet.services.NtroWindowJSweet";
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
                class BackendServiceJSweet extends ca.ntro.core.services.BackendService {
                    /**
                     *
                     * @param {ca.ntro.messages.NtroMessage} message
                     */
                    sendMessageToBackend(message) {
                    }
                    /**
                     *
                     * @param {*} messageClass
                     * @param {ca.ntro.messages.MessageHandler} handler
                     */
                    handleMessageFromBackend(messageClass, handler) {
                    }
                }
                services.BackendServiceJSweet = BackendServiceJSweet;
                BackendServiceJSweet["__class"] = "ca.ntro.jsweet.services.BackendServiceJSweet";
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
                class MessageServiceJSweet extends ca.ntro.core.services.MessageService {
                }
                services.MessageServiceJSweet = MessageServiceJSweet;
                MessageServiceJSweet["__class"] = "ca.ntro.jsweet.services.MessageServiceJSweet";
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
                        for (let index197 = 0; index197 < elements.length; index197++) {
                            let item = elements[index197];
                            {
                                /* add */ (list.push(item) > 0);
                            }
                        }
                        return list;
                    }
                    /**
                     *
                     * @param {*} elements
                     * @return {*}
                     */
                    concurrentMapImpl(elements) {
                        let map = ({});
                        {
                            let array199 = ((m) => { if (m.entries == null)
                                m.entries = []; return m.entries; })(elements);
                            for (let index198 = 0; index198 < array199.length; index198++) {
                                let entry = array199[index198];
                                {
                                    /* put */ ((m, k, v) => { if (m.entries == null)
                                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                                            m.entries[i].value = v;
                                            return;
                                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(map, entry.getKey(), entry.getValue());
                                }
                            }
                        }
                        return map;
                    }
                    /**
                     *
                     * @param {*[]} elements
                     * @return {*[]}
                     */
                    concurrentSetImpl(elements) {
                        let set = ([]);
                        for (let index200 = 0; index200 < elements.length; index200++) {
                            let value = elements[index200];
                            {
                                /* add */ ((s, e) => { if (s.indexOf(e) == -1) {
                                    s.push(e);
                                    return true;
                                }
                                else {
                                    return false;
                                } })(set, value);
                            }
                        }
                        return set;
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
                class NetworkStoreJSweet extends ca.ntro.core.models.ModelStore {
                    constructor() {
                        super(...arguments);
                        this.localStorage = window.localStorage;
                    }
                    /**
                     *
                     * @param {*} updateListener
                     */
                    installExternalUpdateListener(updateListener) {
                        ca.ntro.core.system.trace.T.call(this);
                        window.addEventListener("storage", (evt) => {
                            ca.ntro.core.system.trace.T.call(this);
                            updateListener.onExternalUpdate();
                        });
                    }
                    /*private*/ fullId(documentPath) {
                        return documentPath.getCollection() + "/" + documentPath.getId();
                    }
                    /**
                     *
                     * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                     * @return {ca.ntro.core.json.JsonLoader}
                     */
                    getJsonObject(documentPath) {
                        ca.ntro.core.system.trace.T.call(this);
                        return new ca.ntro.jsweet.json.JsonLoaderJSweet(documentPath);
                    }
                    /**
                     *
                     * @param {ca.ntro.core.services.stores.DocumentPath} documentPath
                     * @param {ca.ntro.core.json.JsonObject} jsonObject
                     */
                    saveJsonObject(documentPath, jsonObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        throw Object.defineProperty(new Error("TODO"), '__classes', { configurable: true, value: ['java.lang.Throwable', 'java.lang.Object', 'java.lang.RuntimeException', 'java.lang.Exception'] });
                    }
                    /**
                     *
                     */
                    close() {
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
                    }
                }
                services.NetworkStoreJSweet = NetworkStoreJSweet;
                NetworkStoreJSweet["__class"] = "ca.ntro.jsweet.services.NetworkStoreJSweet";
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
                    runTaskAsync() {
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
                ResourceLoaderTaskJsweet["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class ViewLoaderWebJSweet extends ca.ntro.web.mvc.ViewLoaderWeb {
                    constructor() {
                        super();
                        ca.ntro.core.system.trace.T.call(this);
                    }
                    /**
                     *
                     * @return {ca.ntro.core.mvc.ViewLoader}
                     */
                    clone() {
                        ca.ntro.core.system.trace.T.call(this);
                        let clone = new ViewLoaderWebJSweet();
                        clone.setHtmlUrl(this.getHtmlUrl());
                        clone.setTargetClass(this.getTargetClass());
                        return clone;
                    }
                    /**
                     *
                     * @param {string} html
                     * @return {ca.ntro.web.dom.HtmlElement}
                     */
                    parseHtml(html) {
                        ca.ntro.core.system.trace.T.call(this);
                        let parsedHtml = $.parseHTML(html, document, false);
                        let rootDiv = $(document.createElement("div"));
                        for (let index201 = 0; index201 < parsedHtml.length; index201++) {
                            let parsedElement = parsedHtml[index201];
                            {
                                rootDiv.append($(parsedElement));
                            }
                        }
                        return new ca.ntro.jsweet.dom.HtmlElementJSweet(rootDiv);
                    }
                }
                services.ViewLoaderWebJSweet = ViewLoaderWebJSweet;
                ViewLoaderWebJSweet["__class"] = "ca.ntro.jsweet.services.ViewLoaderWebJSweet";
                ViewLoaderWebJSweet["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "java.lang.Cloneable", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class JsonParserJSweet extends ca.ntro.core.json.JsonParser {
                    /**
                     *
                     * @return {ca.ntro.core.json.JsonObject}
                     */
                    jsonObjectImpl() {
                        ca.ntro.core.system.trace.T.call(this);
                        return new ca.ntro.core.json.JsonObject(new Object());
                    }
                    /**
                     *
                     * @param {string} jsonString
                     * @return {ca.ntro.core.json.JsonObject}
                     */
                    fromStringImpl(jsonString) {
                        ca.ntro.core.system.trace.T.call(this);
                        let jsObject = JSON.parse(jsonString);
                        return new ca.ntro.core.json.JsonObject(jsObject);
                    }
                    /**
                     *
                     * @param {ca.ntro.core.json.JsonObject} jsonObject
                     * @return {string}
                     */
                    toStringImpl(jsonObject) {
                        ca.ntro.core.system.trace.T.call(this);
                        return JSON.stringify(jsonObject.toMap());
                    }
                    /**
                     *
                     * @param {{ str: string, cursor: number }} jsonStream
                     * @return {ca.ntro.core.json.JsonObject}
                     */
                    fromStreamImpl(jsonStream) {
                        return this.fromStringImpl(jsonStream.toString());
                    }
                }
                services.JsonParserJSweet = JsonParserJSweet;
                JsonParserJSweet["__class"] = "ca.ntro.jsweet.services.JsonParserJSweet";
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
                class ThreadServiceJSweet extends ca.ntro.core.services.ThreadService {
                    constructor() {
                        super(...arguments);
                        /*private*/ this.ROOT_THREAD_ID = 0;
                    }
                    /**
                     *
                     * @return {*}
                     */
                    currentThread() {
                        return new ca.ntro.jsweet.thread.NtroThreadJSweet(this.ROOT_THREAD_ID);
                    }
                    /**
                     *
                     * @return {boolean}
                     */
                    hasParentThread() {
                        return false;
                    }
                    /**
                     *
                     * @param {ca.ntro.messages.NtroMessage} message
                     */
                    sendMessageToParentThread(message) {
                    }
                    /**
                     *
                     * @return {*}
                     */
                    newSubThread() {
                        return null;
                    }
                    /**
                     *
                     * @return {*[]}
                     */
                    subThreads() {
                        return ([]);
                    }
                    /**
                     *
                     * @param {*} task
                     */
                    executeLater(task) {
                        ca.ntro.core.system.log.Log.warning("threadService().executeLater not supported in JSweet");
                    }
                }
                services.ThreadServiceJSweet = ThreadServiceJSweet;
                ThreadServiceJSweet["__class"] = "ca.ntro.jsweet.services.ThreadServiceJSweet";
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
                class AppCloserJSweet extends ca.ntro.core.services.AppCloser {
                    /**
                     *
                     */
                    close() {
                        console.error("[Ntro] AppCloser.close()");
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
            var initialization;
            (function (initialization) {
                class InitializationTaskJSweet extends ca.ntro.core.initialization.InitializationTask {
                    constructor() {
                        super();
                    }
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
                    /**
                     *
                     * @return {*}
                     */
                    provideViewLoaderWebClass() {
                        ca.ntro.core.system.trace.__T.call(this, "provideViewLoaderWeb");
                        return ca.ntro.jsweet.services.ViewLoaderWebJSweet;
                    }
                    /**
                     *
                     * @return {ca.ntro.core.json.JsonParser}
                     */
                    provideJsonParser() {
                        ca.ntro.core.system.trace.__T.call(this, "provideJsonParser");
                        return new ca.ntro.jsweet.services.JsonParserJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.models.ModelStore}
                     */
                    provideLocalStore() {
                        ca.ntro.core.system.trace.__T.call(this, "provideLocalStore");
                        return new ca.ntro.jsweet.services.LocalStoreJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.models.ModelStore}
                     */
                    provideNetworkStore() {
                        ca.ntro.core.system.trace.__T.call(this, "provideNetworkStore");
                        return new ca.ntro.jsweet.services.NetworkStoreJSweet();
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.ThreadService}
                     */
                    provideThreadService() {
                        ca.ntro.core.system.trace.__T.call(this, "provideThreadService");
                        return new ca.ntro.jsweet.services.ThreadServiceJSweet();
                    }
                    /**
                     *
                     * @return {*}
                     */
                    provideMessageServiceClass() {
                        ca.ntro.core.system.trace.__T.call(this, "provideMessageServiceClass");
                        return ca.ntro.jsweet.services.MessageServiceJSweet;
                    }
                    /**
                     *
                     * @return {ca.ntro.core.services.BackendService}
                     */
                    provideBackendService() {
                        ca.ntro.core.system.trace.__T.call(this, "provideBackendService");
                        return new ca.ntro.jsweet.services.BackendServiceJSweet();
                    }
                }
                initialization.InitializationTaskJSweet = InitializationTaskJSweet;
                InitializationTaskJSweet["__class"] = "ca.ntro.jsweet.initialization.InitializationTaskJSweet";
                InitializationTaskJSweet["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class LoadSourceMapTask extends ca.ntro.core.tasks.NtroTaskSync {
                    constructor(path) {
                        super();
                        this.addSubTask(new ca.ntro.jsweet.services.ResourceLoaderTaskJsweet(path), "Loader");
                    }
                    /**
                     *
                     */
                    runTask() {
                        let sourceMap = this.getSubTask(ca.ntro.jsweet.services.ResourceLoaderTaskJsweet, "Loader").getResourceAsString();
                        installSourceMap(sourceMap);
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
                LoadSourceMapTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
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
                class OnLoadTask extends ca.ntro.core.tasks.NtroTaskAsync {
                    constructor() {
                        super();
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
                }
                initialization.OnLoadTask = OnLoadTask;
                OnLoadTask["__class"] = "ca.ntro.jsweet.initialization.OnLoadTask";
                OnLoadTask["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(initialization = jsweet.initialization || (jsweet.initialization = {}));
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
                HtmlDocumentJSweet["__interfaces"] = ["ca.ntro.web.dom.HtmlDocument"];
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
                class HtmlElementJSweet extends ca.ntro.web.dom.HtmlElement {
                    constructor(jQueryElement) {
                        super();
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
                    /**
                     *
                     * @param {string} html
                     */
                    appendHtml(html) {
                        ca.ntro.core.system.trace.T.call(this);
                        (o => o.append.apply(o, $.parseHTML(html)))(this.jQueryElement);
                    }
                    /**
                     *
                     * @param {ca.ntro.web.dom.HtmlElement} element
                     */
                    appendElement(element) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.jQueryElement.append(element.jQueryElement);
                    }
                    /**
                     *
                     * @param {string} cssQuery
                     * @return {*}
                     */
                    children(cssQuery) {
                        ca.ntro.core.system.trace.T.call(this);
                        return new ca.ntro.jsweet.dom.HtmlElementsJSweet(this.jQueryElement.children(cssQuery));
                    }
                    /**
                     *
                     * @param {string} cssQuery
                     * @return {*}
                     */
                    find(cssQuery) {
                        ca.ntro.core.system.trace.T.call(this);
                        return new ca.ntro.jsweet.dom.HtmlElementsJSweet(this.jQueryElement.find(cssQuery));
                    }
                    /**
                     *
                     * @param {string} name
                     * @param {string} value
                     */
                    setAttribute(name, value) {
                        ca.ntro.core.system.trace.T.call(this);
                        this.jQueryElement.attr(name, value);
                    }
                    /**
                     *
                     */
                    remove() {
                        ca.ntro.core.system.trace.T.call(this);
                        this.jQueryElement.remove();
                    }
                    /**
                     *
                     * @param {string} value
                     */
                    value(value) {
                        this.jQueryElement.val(value);
                    }
                    /**
                     *
                     * @return {string}
                     */
                    getValue() {
                        return this.jQueryElement.val().toString();
                    }
                }
                dom.HtmlElementJSweet = HtmlElementJSweet;
                HtmlElementJSweet["__class"] = "ca.ntro.jsweet.dom.HtmlElementJSweet";
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
                     * @return {ca.ntro.web.dom.HtmlElement}
                     */
                    get(index) {
                        return new ca.ntro.jsweet.dom.HtmlElementJSweet($(this.jQueryElements.get(index)));
                    }
                    /**
                     *
                     * @return {number}
                     */
                    size() {
                        return (this.jQueryElements.length | 0);
                    }
                }
                dom.HtmlElementsJSweet = HtmlElementsJSweet;
                HtmlElementsJSweet["__class"] = "ca.ntro.jsweet.dom.HtmlElementsJSweet";
                HtmlElementsJSweet["__interfaces"] = ["ca.ntro.web.dom.HtmlElements"];
            })(dom = jsweet.dom || (jsweet.dom = {}));
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
                    ca.ntro.core.services.NtroCollections.initialize(new ca.ntro.jsweet.services.NtroCollectionsJSweet());
                    let initializationTask = new ca.ntro.core.initialization.NtroInitializationTask();
                    initializationTask.setTaskId(ca.ntro.core.Constants.INITIALIZATION_TASK_ID);
                    let initJSweet = new ca.ntro.jsweet.initialization.InitializationTaskJSweet();
                    initializationTask.addSubTask(initJSweet);
                    initializationTask.addSubTask(jsweet.onLoadTask_$LI$());
                    return initializationTask;
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
            var thread;
            (function (thread) {
                class NtroThreadJSweet {
                    constructor(threadId) {
                        if (this.threadId === undefined)
                            this.threadId = 0;
                        this.threadId = threadId;
                    }
                    /**
                     *
                     * @param {ca.ntro.messages.NtroMessage} message
                     */
                    sendMessageToThread(message) {
                    }
                    /**
                     *
                     * @param {*} messageClass
                     * @param {ca.ntro.messages.MessageHandler} handler
                     */
                    handleMessageFromThread(messageClass, handler) {
                    }
                }
                thread.NtroThreadJSweet = NtroThreadJSweet;
                NtroThreadJSweet["__class"] = "ca.ntro.jsweet.thread.NtroThreadJSweet";
                NtroThreadJSweet["__interfaces"] = ["ca.ntro.threads.NtroThread"];
            })(thread = jsweet.thread || (jsweet.thread = {}));
        })(jsweet = ntro.jsweet || (ntro.jsweet = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var jsweet;
        (function (jsweet) {
            var json;
            (function (json_1) {
                class JsonLoaderJSweet extends ca.ntro.core.json.JsonLoader {
                    constructor(documentPath) {
                        super();
                        if (this.jsonObject === undefined)
                            this.jsonObject = null;
                        if (this.documentPath === undefined)
                            this.documentPath = null;
                        ca.ntro.core.system.trace.T.call(this);
                        this.documentPath = documentPath;
                    }
                    /**
                     *
                     */
                    runTaskAsync() {
                        ca.ntro.core.system.trace.T.call(this);
                        fetch("/_B/" + this.fullId(this.documentPath)).then((response) => {
                            if (response.ok) {
                                return response.json().then((json) => {
                                    this.jsonObject = ca.ntro.core.json.JsonParser.fromString(JSON.stringify(json));
                                    this.notifyTaskFinished();
                                }).catch((error) => {
                                    console.error("[NetworkStore] Erreur lors du chargement du mod\u00e8le (JSON invalide)");
                                });
                            }
                            else {
                                return Promise.reject("[NetworkStore] Erreur lors du chargement du mod\u00e8le (code non-200 du serveur)");
                            }
                        });
                    }
                    /**
                     *
                     * @param {Error} e
                     */
                    onFailure(e) {
                    }
                    /*private*/ fullId(documentPath) {
                        return documentPath.getCollection() + "/" + documentPath.getId();
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
                json_1.JsonLoaderJSweet = JsonLoaderJSweet;
                JsonLoaderJSweet["__class"] = "ca.ntro.jsweet.json.JsonLoaderJSweet";
                JsonLoaderJSweet["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
            })(json = jsweet.json || (jsweet.json = {}));
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
/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ParentClassAB {
                        constructor() {
                        }
                    }
                    classes.ParentClassAB = ParentClassAB;
                    ParentClassAB["__class"] = "ca.ntro.test.introspector.classes.ParentClassAB";
                    ParentClassAB["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceAB", "ca.ntro.test.introspector.interfaces.ParentInterfaceA", "ca.ntro.test.introspector.interfaces.ParentInterfaceB"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ParentClassA {
                        constructor() {
                        }
                    }
                    classes.ParentClassA = ParentClassA;
                    ParentClassA["__class"] = "ca.ntro.test.introspector.classes.ParentClassA";
                    ParentClassA["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceA", "ca.ntro.test.introspector.interfaces.ParentInterfaceA"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ParentClassB {
                        constructor() {
                        }
                    }
                    classes.ParentClassB = ParentClassB;
                    ParentClassB["__class"] = "ca.ntro.test.introspector.classes.ParentClassB";
                    ParentClassB["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceB", "ca.ntro.test.introspector.interfaces.ParentInterfaceB"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                class IntrospectorTests {
                    testDoesImplement() {
                        let childClassAB = new ca.ntro.test.introspector.classes.ChildClassAB();
                        ca.ntro.core.Ntro.assertService().assertTrue(false);
                    }
                }
                introspector.IntrospectorTests = IntrospectorTests;
                IntrospectorTests["__class"] = "ca.ntro.test.introspector.IntrospectorTests";
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var json;
            (function (json) {
                class ListItemB extends ca.ntro.core.json.JsonObjectIO {
                    constructor() {
                        super();
                        /*private*/ this.propB = 1;
                    }
                    getPropB() {
                        return this.propB;
                    }
                    setPropB(probB) {
                        this.propB = probB;
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
                        if (other != null && other instanceof ca.ntro.test.json.ListItemB) {
                            let otherItem = other;
                            return this.propB === otherItem.propB;
                        }
                        return false;
                    }
                }
                json.ListItemB = ListItemB;
                ListItemB["__class"] = "ca.ntro.test.json.ListItemB";
                ListItemB["__interfaces"] = ["ca.ntro.test.json.ListItem", "java.io.Serializable"];
            })(json = test.json || (test.json = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var json;
            (function (json) {
                class ListItemA extends ca.ntro.core.json.JsonObjectIO {
                    constructor() {
                        super();
                        /*private*/ this.propA = 0;
                    }
                    getPropA() {
                        return this.propA;
                    }
                    setPropA(propA) {
                        this.propA = propA;
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
                        if (other != null && other instanceof ca.ntro.test.json.ListItemA) {
                            let otherItem = other;
                            return this.propA === otherItem.propA;
                        }
                        return false;
                    }
                }
                json.ListItemA = ListItemA;
                ListItemA["__class"] = "ca.ntro.test.json.ListItemA";
                ListItemA["__interfaces"] = ["ca.ntro.test.json.ListItem", "java.io.Serializable"];
            })(json = test.json || (test.json = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var json;
            (function (json) {
                class LinkedListNode extends ca.ntro.core.json.JsonObjectIO {
                    constructor(value) {
                        if (((typeof value === 'string') || value === null)) {
                            let __args = arguments;
                            super();
                            if (this.next === undefined)
                                this.next = null;
                            if (this.value === undefined)
                                this.value = null;
                            this.nextList = ([]);
                            this.nextMap = ({});
                            if (this.next === undefined)
                                this.next = null;
                            if (this.value === undefined)
                                this.value = null;
                            (() => {
                                this.value = value;
                            })();
                        }
                        else if (value === undefined) {
                            let __args = arguments;
                            super();
                            if (this.next === undefined)
                                this.next = null;
                            if (this.value === undefined)
                                this.value = null;
                            this.nextList = ([]);
                            this.nextMap = ({});
                            if (this.next === undefined)
                                this.next = null;
                            if (this.value === undefined)
                                this.value = null;
                        }
                        else
                            throw new Error('invalid overload');
                    }
                    getNext() {
                        return this.next;
                    }
                    setNext(next) {
                        this.next = next;
                    }
                    getValue() {
                        return this.value;
                    }
                    setValue(value) {
                        this.value = value;
                    }
                    getNextList() {
                        return this.nextList;
                    }
                    setNextList(nextList) {
                        this.nextList = nextList;
                    }
                    getNextMap() {
                        return this.nextMap;
                    }
                    setNextMap(nextMap) {
                        this.nextMap = nextMap;
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
                        } })(this.value);
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
                        if (other != null && other instanceof ca.ntro.test.json.LinkedListNode) {
                            let otherNode = other;
                            return ((o1, o2) => { if (o1 && o1.equals) {
                                return o1.equals(o2);
                            }
                            else {
                                return o1 === o2;
                            } })(this.value, otherNode.value);
                        }
                        return false;
                    }
                    /**
                     *
                     * @return {string}
                     */
                    toString() {
                        return ca.ntro.core.Ntro.introspector().getSimpleNameForClass(this.constructor);
                    }
                }
                LinkedListNode.serialVersionUID = 2667416858048644003;
                json.LinkedListNode = LinkedListNode;
                LinkedListNode["__class"] = "ca.ntro.test.json.LinkedListNode";
                LinkedListNode["__interfaces"] = ["java.io.Serializable"];
            })(json = test.json || (test.json = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var json;
            (function (json) {
                class MyList extends ca.ntro.core.json.JsonObjectIO {
                    constructor() {
                        super();
                        /*private*/ this.list = ([]);
                    }
                    getList() {
                        return this.list;
                    }
                    setList(list) {
                        this.list = list;
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
                        } })(this.list);
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
                        if (other != null && other instanceof ca.ntro.test.json.MyList) {
                            let otherList = other;
                            return ((a1, a2) => { if (a1 == null && a2 == null)
                                return true; if (a1 == null || a2 == null)
                                return false; if (a1.length != a2.length)
                                return false; for (let i = 0; i < a1.length; i++) {
                                if (a1[i] != a2[i])
                                    return false;
                            } return true; })(this.list, otherList.list);
                        }
                        return false;
                    }
                }
                MyList.serialVersionUID = 8669571506241213302;
                json.MyList = MyList;
                MyList["__class"] = "ca.ntro.test.json.MyList";
                MyList["__interfaces"] = ["java.io.Serializable"];
            })(json = test.json || (test.json = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ChildClassAB extends ca.ntro.test.introspector.classes.ParentClassAB {
                        constructor() {
                            super();
                        }
                    }
                    classes.ChildClassAB = ChildClassAB;
                    ChildClassAB["__class"] = "ca.ntro.test.introspector.classes.ChildClassAB";
                    ChildClassAB["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceAB", "ca.ntro.test.introspector.interfaces.ParentInterfaceA", "ca.ntro.test.introspector.interfaces.ParentInterfaceB"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ChildClassA extends ca.ntro.test.introspector.classes.ParentClassA {
                        constructor() {
                            super();
                        }
                    }
                    classes.ChildClassA = ChildClassA;
                    ChildClassA["__class"] = "ca.ntro.test.introspector.classes.ChildClassA";
                    ChildClassA["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceA", "ca.ntro.test.introspector.interfaces.ParentInterfaceA"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
(function (ca) {
    var ntro;
    (function (ntro) {
        var test;
        (function (test) {
            var introspector;
            (function (introspector) {
                var classes;
                (function (classes) {
                    class ChildClassB extends ca.ntro.test.introspector.classes.ParentClassB {
                        constructor() {
                            super();
                        }
                    }
                    classes.ChildClassB = ChildClassB;
                    ChildClassB["__class"] = "ca.ntro.test.introspector.classes.ChildClassB";
                    ChildClassB["__interfaces"] = ["ca.ntro.test.introspector.interfaces.ChildInterfaceB", "ca.ntro.test.introspector.interfaces.ParentInterfaceB"];
                })(classes = introspector.classes || (introspector.classes = {}));
            })(introspector = test.introspector || (test.introspector = {}));
        })(test = ntro.test || (ntro.test = {}));
    })(ntro = ca.ntro || (ca.ntro = {}));
})(ca || (ca = {}));
//# sourceMappingURL=bundle.js.map
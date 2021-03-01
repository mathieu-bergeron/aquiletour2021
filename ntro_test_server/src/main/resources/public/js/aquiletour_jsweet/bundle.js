/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            class AquiletourMainJSweet extends ca.aquiletour.core.AquiletourMain {
                constructor() {
                    super();
                }
                /**
                 *
                 */
                registerViewLoaders() {
                    ca.ntro.core.system.trace.T.call(this);
                    ca.aquiletour.web.ViewLoaderRegistrationWeb.registerViewLoaders();
                }
                /**
                 *
                 * @return {ca.ntro.core.mvc.NtroWindow}
                 */
                getWindow() {
                    ca.ntro.core.system.trace.T.call(this);
                    return new ca.ntro.jsweet.services.NtroWindowJSweet();
                }
            }
            jsweet.AquiletourMainJSweet = AquiletourMainJSweet;
            AquiletourMainJSweet["__class"] = "ca.aquiletour.jsweet.AquiletourMainJSweet";
            AquiletourMainJSweet["__interfaces"] = ["ca.ntro.core.tasks.Node", "ca.ntro.core.tasks.NodeDescription", "ca.ntro.core.tasks.Labellable", "ca.ntro.core.tasks.NtroTask", "ca.ntro.core.tasks.TaskGraph"];
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            class JavaMainJSweet {
                static main(args) {
                    ca.ntro.core.system.trace.__T.call(JavaMainJSweet, "main");
                    let options = ["--traceLevel", "APP"];
                    ca.ntro.core.Ntro.__registerIntrospector(new ca.ntro.jsweet.introspection.IntrospectorJSweet());
                    ca.ntro.core.json.JsonParser.initialize(new ca.ntro.jsweet.services.JsonParserJSweet());
                    let a = new ca.aquiletour.jsweet.test.LinkedListNode("A");
                    let b = new ca.aquiletour.jsweet.test.LinkedListNode("B");
                    let c = new ca.aquiletour.jsweet.test.LinkedListNode("C");
                    let d = new ca.aquiletour.jsweet.test.LinkedListNode("D");
                    let map = ({});
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(map, a, 10);
                    let otherMap = ({});
                    /* put */ ((m, k, v) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            m.entries[i].value = v;
                            return;
                        } m.entries.push({ key: k, value: v, getKey: function () { return this.key; }, getValue: function () { return this.value; } }); })(otherMap, 2, a);
                    let x = ((m, k) => { if (m.entries == null)
                        m.entries = []; for (let i = 0; i < m.entries.length; i++)
                        if (m.entries[i].key.equals != null && m.entries[i].key.equals(k) || m.entries[i].key === k) {
                            return m.entries[i].value;
                        } return null; })(map, a);
                    {
                        let array203 = ((m) => { if (m.entries == null)
                            m.entries = []; return m.entries; })(map);
                        for (let index202 = 0; index202 < array203.length; index202++) {
                            let entry = array203[index202];
                            {
                                console.info(entry.getKey());
                            }
                        }
                    }
                    {
                        let array205 = ((m) => { let r = []; if (m.entries == null)
                            m.entries = []; for (let i = 0; i < m.entries.length; i++)
                            r.push(m.entries[i].value); return r; })(map);
                        for (let index204 = 0; index204 < array205.length; index204++) {
                            let value = array205[index204];
                            {
                                console.info(value);
                            }
                        }
                    }
                    a.setNext(b);
                    b.setNext(c);
                    c.setNext(d);
                    d.setNext(c);
                    /* put */ (c.getNextMap()["d"] = d);
                    /* put */ (d.getNextMap()["b"] = b);
                    /* add */ (b.getNextList().push(a) > 0);
                    /* add */ (b.getNextList().push(b) > 0);
                    /* add */ (b.getNextList().push(c) > 0);
                    /* add */ (b.getNextList().push(d) > 0);
                    let jsonObject = a.toJsonObject();
                    console.info(jsonObject);
                    console.info(ca.ntro.core.json.JsonParser.toString(jsonObject));
                    ca.ntro.jsweet.NtroJSweet.defaultInitializationTask().setOptions(options).addNextTask(new ca.aquiletour.jsweet.AquiletourMainJSweet()).execute();
                }
            }
            jsweet.JavaMainJSweet = JavaMainJSweet;
            JavaMainJSweet["__class"] = "ca.aquiletour.jsweet.JavaMainJSweet";
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            var test;
            (function (test) {
                class LinkedListNode extends ca.ntro.core.json.JsonObjectIO {
                    constructor(value) {
                        super();
                        /*private*/ this.nextList = ([]);
                        /*private*/ this.nextMap = ({});
                        if (this.next === undefined)
                            this.next = null;
                        if (this.value === undefined)
                            this.value = null;
                        this.value = value;
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
                        if (other != null && other instanceof ca.aquiletour.jsweet.test.LinkedListNode) {
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
                }
                LinkedListNode.serialVersionUID = 2667416858048644003;
                test.LinkedListNode = LinkedListNode;
                LinkedListNode["__class"] = "ca.aquiletour.jsweet.test.LinkedListNode";
                LinkedListNode["__interfaces"] = ["java.io.Serializable"];
            })(test = jsweet.test || (jsweet.test = {}));
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            var test;
            (function (test) {
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
                        if (other != null && other instanceof ca.aquiletour.jsweet.test.ListItemA) {
                            let otherItem = other;
                            return this.propA === otherItem.propA;
                        }
                        return false;
                    }
                }
                test.ListItemA = ListItemA;
                ListItemA["__class"] = "ca.aquiletour.jsweet.test.ListItemA";
                ListItemA["__interfaces"] = ["ca.aquiletour.jsweet.test.ListItem", "java.io.Serializable"];
            })(test = jsweet.test || (jsweet.test = {}));
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            var test;
            (function (test) {
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
                        if (other != null && other instanceof ca.aquiletour.jsweet.test.ListItemB) {
                            let otherItem = other;
                            return this.propB === otherItem.propB;
                        }
                        return false;
                    }
                }
                test.ListItemB = ListItemB;
                ListItemB["__class"] = "ca.aquiletour.jsweet.test.ListItemB";
                ListItemB["__interfaces"] = ["ca.aquiletour.jsweet.test.ListItem", "java.io.Serializable"];
            })(test = jsweet.test || (jsweet.test = {}));
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            var test;
            (function (test) {
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
                        if (other != null && other instanceof ca.aquiletour.jsweet.test.MyList) {
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
                test.MyList = MyList;
                MyList["__class"] = "ca.aquiletour.jsweet.test.MyList";
                MyList["__interfaces"] = ["java.io.Serializable"];
            })(test = jsweet.test || (jsweet.test = {}));
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
ca.aquiletour.jsweet.JavaMainJSweet.main(null);
//# sourceMappingURL=bundle.js.map
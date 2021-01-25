/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            class RootpageMainJSweet extends ca.aquiletour.web.RootpageMainWeb {
                constructor(lang) {
                    super(lang);
                    /*private*/ this.window = new ca.aquiletour.jsweet.NtroWindowJSweet();
                }
                /**
                 *
                 * @return {ca.ntro.core.web.NtroWindowWeb}
                 */
                getWindow() {
                    return this.window;
                }
            }
            jsweet.RootpageMainJSweet = RootpageMainJSweet;
            RootpageMainJSweet["__class"] = "ca.aquiletour.jsweet.RootpageMainJSweet";
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            class NtroWindowJSweet extends ca.ntro.core.web.NtroWindowWeb {
                constructor() {
                    super(...arguments);
                    /*private*/ this.__ca_aquiletour_jsweet_NtroWindowJSweet_document = new ca.ntro.jsweet.dom.HtmlDocumentJSweet();
                }
                /**
                 *
                 * @return {*}
                 */
                getDocument() {
                    return this.__ca_aquiletour_jsweet_NtroWindowJSweet_document;
                }
            }
            jsweet.NtroWindowJSweet = NtroWindowJSweet;
            NtroWindowJSweet["__class"] = "ca.aquiletour.jsweet.NtroWindowJSweet";
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var jsweet;
        (function (jsweet) {
            class AquiletourMainJSweet extends ca.aquiletour.core.AquiletourMain {
                /**
                 *
                 * @param {string} lang
                 * @return {ca.aquiletour.core.pages.rootpage.RootpageMain}
                 */
                rootpageMain(lang) {
                    return new ca.aquiletour.jsweet.RootpageMainJSweet(lang);
                }
            }
            jsweet.AquiletourMainJSweet = AquiletourMainJSweet;
            AquiletourMainJSweet["__class"] = "ca.aquiletour.jsweet.AquiletourMainJSweet";
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
                    ca.ntro.jsweet.NtroJSweet.defaultInitializationTask().setOptions(options).addNextTask(new ca.aquiletour.jsweet.AquiletourMainJSweet()).execute();
                }
            }
            jsweet.JavaMainJSweet = JavaMainJSweet;
            JavaMainJSweet["__class"] = "ca.aquiletour.jsweet.JavaMainJSweet";
        })(jsweet = aquiletour.jsweet || (aquiletour.jsweet = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
ca.aquiletour.jsweet.JavaMainJSweet.main(null);
//# sourceMappingURL=bundle.js.map
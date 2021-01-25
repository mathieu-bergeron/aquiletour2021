/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            class AquiletourRequestHandler {
                initialRequest(path, parameters, authToken) {
                    ca.ntro.core.system.trace.T.call(this);
                    let lang = "fr";
                    let handler = new ca.aquiletour.web.HandlerTask();
                    handler.addSubTask(this.rootpageMain(lang), "RootPageMain");
                    return handler;
                }
                newRequest(oldPath, path, oldParameters, parameters, authToken) {
                    ca.ntro.core.system.trace.T.call(this);
                    return new ca.aquiletour.web.HandlerTask();
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
            class RootpageMainWeb extends ca.aquiletour.core.pages.rootpage.RootpageMain {
                constructor(lang) {
                    super(lang);
                }
                loadView(lang) {
                    return ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("views/rootpage/structure.html").setCssUrl("views/rootpage/style.css").setTranslationsUrl("i18/" + lang + "/strings.json");
                }
                writeHtml(out) {
                    ca.ntro.core.system.trace.T.call(this);
                    this.getWindow().writeHtml(out);
                }
            }
            web.RootpageMainWeb = RootpageMainWeb;
            RootpageMainWeb["__class"] = "ca.aquiletour.web.RootpageMainWeb";
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var web;
        (function (web) {
            class HandlerTask extends ca.ntro.core.tasks.NtroTask {
                writeHtml(out) {
                    ca.ntro.core.system.trace.T.call(this);
                    this.getSubTask(ca.aquiletour.web.RootpageMainWeb, "RootPageMain").writeHtml(out);
                }
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
            }
            web.HandlerTask = HandlerTask;
            HandlerTask["__class"] = "ca.aquiletour.web.HandlerTask";
        })(web = aquiletour.web || (aquiletour.web = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
//# sourceMappingURL=bundle.js.map
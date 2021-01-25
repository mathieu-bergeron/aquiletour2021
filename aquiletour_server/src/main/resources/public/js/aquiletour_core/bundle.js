/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
var ca;
(function (ca) {
    var aquiletour;
    (function (aquiletour) {
        var core;
        (function (core) {
            class AquiletourMain extends ca.ntro.core.tasks.NtroTask {
                /**
                 *
                 */
                runTask() {
                    ca.ntro.core.system.trace.T.call(this);
                    let lang = this.getPreviousTask(ca.ntro.core.initialization.NtroInitializationTask).getOption("lang");
                    lang = "fr";
                    this.rootpageMain(lang).execute();
                    this.notifyTaskFinished();
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
                var rootpage;
                (function (rootpage) {
                    class RootpageMain extends ca.ntro.core.tasks.NtroTask {
                        constructor(lang) {
                            super();
                            ca.ntro.core.system.trace.T.call(this);
                            this.addSubTask(this.loadView(lang), "ViewLoader");
                        }
                        /**
                         *
                         */
                        runTask() {
                            ca.ntro.core.system.trace.T.call(this);
                            let viewLoader = (this.getSubTask(ca.ntro.core.mvc.view.ViewLoader, "ViewLoader"));
                            this.getWindow().installRootView(viewLoader);
                            this.notifyTaskFinished();
                        }
                        /**
                         *
                         * @param {Error} e
                         */
                        onFailure(e) {
                        }
                    }
                    rootpage.RootpageMain = RootpageMain;
                    RootpageMain["__class"] = "ca.aquiletour.core.pages.rootpage.RootpageMain";
                })(rootpage = pages.rootpage || (pages.rootpage = {}));
            })(pages = core.pages || (core.pages = {}));
        })(core = aquiletour.core || (aquiletour.core = {}));
    })(aquiletour = ca.aquiletour || (ca.aquiletour = {}));
})(ca || (ca = {}));
//# sourceMappingURL=bundle.js.map
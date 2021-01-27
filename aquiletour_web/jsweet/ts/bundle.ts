/* Generated from Java with JSweet 2.2.0 - http://www.jsweet.org */
namespace ca.aquiletour.web {
    export abstract class AquiletourRequestHandler {
        public initialRequest(path : string, parameters : any, authToken : string) : ca.aquiletour.web.HandlerTask {
            ca.ntro.core.system.trace.T.call(this);
            let lang : string = "fr";
            let handler : ca.aquiletour.web.HandlerTask = new ca.aquiletour.web.HandlerTask();
            handler.addSubTask(this.rootpageMain(lang), "RootPageMain");
            return handler;
        }

        abstract rootpageMain(lang : string) : ca.aquiletour.core.rootpage.RootpageMain;

        public newRequest(oldPath : string, path : string, oldParameters : any, parameters : any, authToken : string) : ca.aquiletour.web.HandlerTask {
            ca.ntro.core.system.trace.T.call(this);
            return new ca.aquiletour.web.HandlerTask();
        }
    }
    AquiletourRequestHandler["__class"] = "ca.aquiletour.web.AquiletourRequestHandler";

}
namespace ca.aquiletour.web {
    export class HandlerTask extends ca.ntro.core.tasks.NtroTask {
        public writeHtml(out : { str: string, toString: Function }) {
            ca.ntro.core.system.trace.T.call(this);
            this.getSubTask<any>(ca.aquiletour.web.RootpageMainWeb, "RootPageMain").writeHtml(out);
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
        onFailure(e : Error) {
        }
    }
    HandlerTask["__class"] = "ca.aquiletour.web.HandlerTask";

}
namespace ca.aquiletour.web {
    export abstract class RootpageMainWeb extends ca.aquiletour.core.rootpage.RootpageMain {
        public constructor(lang : string) {
            super(lang);
        }

        loadView(lang : string) : ca.ntro.core.mvc.view.ViewLoader {
            return ca.ntro.core.Ntro.viewLoaderWeb().setHtmlUrl("views/rootpage/structure.html").setCssUrl("views/rootpage/style.css").setTranslationsUrl("i18/" + lang + "/strings.json");
        }

        /**
         * 
         * @return {ca.ntro.core.web.NtroWindowWeb}
         */
        abstract getWindow() : ca.ntro.core.web.NtroWindowWeb;

        public writeHtml(out : { str: string, toString: Function }) {
            ca.ntro.core.system.trace.T.call(this);
            this.getWindow().writeHtml(out);
        }
    }
    RootpageMainWeb["__class"] = "ca.aquiletour.web.RootpageMainWeb";

}


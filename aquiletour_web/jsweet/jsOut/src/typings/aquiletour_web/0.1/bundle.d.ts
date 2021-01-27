declare namespace ca.aquiletour.web {
    abstract class AquiletourRequestHandler {
        initialRequest(path: string, parameters: any, authToken: string): ca.aquiletour.web.HandlerTask;
        abstract rootpageMain(lang: string): ca.aquiletour.core.rootpage.RootpageMain;
        newRequest(oldPath: string, path: string, oldParameters: any, parameters: any, authToken: string): ca.aquiletour.web.HandlerTask;
    }
}
declare namespace ca.aquiletour.web {
    class HandlerTask extends ca.ntro.core.tasks.NtroTask {
        writeHtml(out: {
            str: string;
            toString: Function;
        }): void;
        /**
         *
         */
        runTask(): void;
        /**
         *
         * @param {Error} e
         */
        onFailure(e: Error): void;
    }
}
declare namespace ca.aquiletour.web {
    abstract class RootpageMainWeb extends ca.aquiletour.core.rootpage.RootpageMain {
        constructor(lang: string);
        loadView(lang: string): ca.ntro.core.mvc.view.ViewLoader;
        /**
         *
         * @return {ca.ntro.core.web.NtroWindowWeb}
         */
        abstract getWindow(): ca.ntro.core.web.NtroWindowWeb;
        writeHtml(out: {
            str: string;
            toString: Function;
        }): void;
    }
}

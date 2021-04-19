sourceMap.SourceMapConsumer.initialize({
	"lib/mappings.wasm": "https://unpkg.com/source-map@0.7.3/lib/mappings.wasm"
});

const sourceMapsToLoad = [
	"/_resources/js/ntro_core/bundle.js.map",
	"/_resources/js/ntro_jsweet/bundle.js.map",
	"/_resources/js/ntro_test_jsweet/bundle.js.map",
];

const loadedSourceMaps = {};

window.onload = async function() {
	for (const sourceMapUrl of sourceMapsToLoad) {
		const response = await fetch(sourceMapUrl);

		if (response.ok) {
			const rawSourceMap = await response.text();

			loadedSourceMaps[sourceMapUrl] = await new sourceMap.SourceMapConsumer(rawSourceMap);

		} else {
			ca.ntro.jsweet.onLoadTask.setError(new Error("cannot load source map"));
		}
	}

	ca.ntro.jsweet.debug.installSourceMapAnalyzer(function (lineColumn) {
		const sourceMapFile = loadedSourceMaps[`${lineColumn.fileName}.map`];

		if (!sourceMapFile) {
			console.warn('could not associate fileName with a loaded source map');
		}

		originalPosition = sourceMapFile.originalPositionFor(lineColumn);

		return originalPosition;
	});

	ca.ntro.jsweet.onLoadTask.notifyTaskFinished();
}

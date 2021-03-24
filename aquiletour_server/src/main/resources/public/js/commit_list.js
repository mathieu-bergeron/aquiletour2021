function initializeCommitList(viewRootElement, jSweet) {
	console.log("initializeCommitList");

	const commitList = viewRootElement.find("#commit-list li");

	window.onload = function() {
		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : window.location.pathname + "'s commit history"
			},
			data : [ {
				type : "column",
				dataPoints : [

				]
			} ]
		});
		chart.render();

		commitList.each(function(i, li) {
			var commit = $(li)
			var div = commit.children()[0];
			
			var timeStampSpan = $(div).find('#timestamp');
			var estimatedEffortSpan = $(div).find('#estimatedEffort');
			var commitMessageSpan = $(div).find('#commitMessage');
			var exercicePathSpan = $(div).find('#exercicePath');
			var modifiedFilesSpan = $(div).find('#modifiedFiles');
			
			var timeStamp = parseInt($(timeStampSpan).text());
			var estimatedEffort = parseInt($(estimatedEffortSpan).text());
			var commitMessage = $(commitMessageSpan).text();
			var exercicePath = $(exercicePathSpan).text();
			var modifiedFiles = $(modifiedFilesSpan).text();

			chart.options.data[0].dataPoints.push({
				x : timeStamp ,
				y : estimatedEffort,
				toolTipContent: "Timestamp : {x}<br/>" +
						"Estimated Effort : {y}<br/>" +
						"Commit Message : " + commitMessage + "<br/>" +
						"Exercice Path : " + exercicePath + "<br/>" +
						"Modified Files : " + modifiedFiles 
						
			});
			chart.render();
		});

		function toogleDataSeries(e) {
			if (typeof (e.dataSeries.visible) === "undefined"
					|| e.dataSeries.visible) {
				e.dataSeries.visible = false;
			} else {
				e.dataSeries.visible = true;
			}
			e.chart.render();
		}

	}
}

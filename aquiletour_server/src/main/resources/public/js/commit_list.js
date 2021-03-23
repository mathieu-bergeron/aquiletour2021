function initializeCommitList(viewRootElement, jSweet) {
	console.log("initializeCommitList");

	const commitList = viewRootElement.find("#commit-List");

	commitList.each(function(i, li) {
		var commit = $(li)
	});
	window.onload = function() {
		var chart = new CanvasJS.Chart("chartContainer", {
			title : {
				text : window.location.pathname + "'s commit history"
			},
			data : [ {
				type : "line",
				dataPoints: [
					{ y: 10 },
					{ y:  4 },
					{ y: 18 },
					{ y:  8 }	
				]
			} ]
		});
		chart.render();

		// var options = {
		// animationEnabled: true,
		// theme: "light2",
		// title:{
		// text: window.location.pathname + "'s commit history"
		// },
		// axisX:{
		// //valueFormatString: "DD MMM"
		// },
		// axisY: {
		// title: "Estimated Effort",
		// //suffix: "K",
		// minimum: 0
		// },
		// toolTip:{
		// shared:true
		// },
		// legend:{
		// cursor:"pointer",
		// verticalAlign: "bottom",
		// horizontalAlign: "left",
		// dockInsidePlotArea: true,
		// itemclick: toogleDataSeries
		// },
		// data: [{
		// type: "line",
		// showInLegend: true,
		// name: "Commits",
		// markerType: "square",
		// //xValueFormatString: "DD MMM, YYYY",
		// color: "#F08080",
		// yValueFormatString: "#,##0K",
		// dataPoints: [
		// { x: new Date(2017, 10, 1), y: 63 },
		// { x: new Date(2017, 10, 2), y: 69 },
		// { x: new Date(2017, 10, 3), y: 65 },
		// { x: new Date(2017, 10, 4), y: 70 },
		// { x: new Date(2017, 10, 5), y: 71 },
		// { x: new Date(2017, 10, 6), y: 65 },
		// { x: new Date(2017, 10, 7), y: 73 },
		// { x: new Date(2017, 10, 8), y: 96 },
		// { x: new Date(2017, 10, 9), y: 84 },
		// { x: new Date(2017, 10, 10), y: 85 },
		// { x: new Date(2017, 10, 11), y: 86 },
		// { x: new Date(2017, 10, 12), y: 94 },
		// { x: new Date(2017, 10, 13), y: 97 },
		// { x: new Date(2017, 10, 14), y: 86 },
		// { x: new Date(2017, 10, 15), y: 89 }
		// ]
		// }]
		// };
//		chart.options.data[0].dataPoints.push({
//			y : 25
//		});
//		chart.render();
		commitList.each(function(i, li) {
			var commit = $(li)
			// chart.options.data[0].dataPoints.push({ y:
			// commit.find("timestamp"), x: commit.find("estimatedEffort")});
			chart.options.data[0].dataPoints.push({
				y : 25
			});
			chart.render();
		});
		// $("#chartContainer").CanvasJSChart(options);

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

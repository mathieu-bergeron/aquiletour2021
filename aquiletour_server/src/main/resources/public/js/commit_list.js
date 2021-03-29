function initializeCommitList(viewRootElement, jSweet) {
	console.log("initializeCommitList");

	const commitList = viewRootElement.find("#commit-list li");
	var ctx = viewRootElement.find("#commitsChart");
	var pointBackgroundColors = [];
	var commitsChart = new Chart(ctx, {
	    type: 'scatter',
	    data: {
	    	datasets: [{
	    		backgroundColor:"#EF1010",
	    		pointBackgroundColor: pointBackgroundColors,
	            label: "student's commit history",
	            pointRadius: 10,
	            pointHoverRadius: 10,
	            data: [{
	            }],
	         }]
	    },
	    options: {
	    	legend: {
	    	       labels: {
	    	         fontColor: "#000000",
	    	        fontSize: 30
	    	      }
	    	   },
	    	scales: {
	    	      xAxes: [{
	    	        display: true,
	    	        gridLines: {
	    	          display: true,
	    	          color: "#000000"
	    	        },
	    	        scaleLabel: {
	    	          display: true,
	    	          labelString: 'Timestamp',
	    	          fontColor: "black",
	    	          fontSize: 24
	    	        },
	    	        ticks: {
                        fontColor: "black",
                        fontSize: 24,
                        callback: function(value) { 
                            return new Date(value).toLocaleDateString('fr-ca', {month:'short', day:'numeric'}); 
                        },
                    }
	    	      }],
	    	      yAxes: [{
	    	        display: true,
	    	        gridLines: {
	    	          display: true,
	    	          color: "#000000"
	    	        },
	    	        scaleLabel: {
	    	          display: true,
	    	          labelString: 'Estimated Effort',
	    	          fontColor: "black",
	    	          fontSize: 24
	    	        },
	    	        ticks: {
                        fontColor: "black",
                        fontSize: 24,
                        stepSize: 1,
                        beginAtZero: true
                    }
	    	      }]
	    	    },
	    	tooltips:{
	    		titleFontSize: 24,
	    	    bodyFontSize: 18,
	    		callbacks: {
	                 label: function(tooltipItem, data) {
	                    var commitInfo; 			
	                    commitInfo = ["Timestamp : " + (new Date(tooltipItem.xLabel).toLocaleDateString('fr-ca', {month:'short', day:'numeric'})).toString() ];
	                    commitInfo.push("Estimated Effort : " + tooltipItem.yLabel);
	                    commitInfo.push("Commit Message : " + getCommitMessage(tooltipItem, data.datasets));
	                    commitInfo.push("Exercice Path : " + getExercicePath(tooltipItem, data.datasets));
	                    commitInfo.push("Modified Files : " + getModifiedFiles(tooltipItem, data.datasets));                	             			
	                    return commitInfo;
	                 }
	             }
	    	},
	    	    elements: {
	    	        line: {
	    	            tension: .1, // bezier curves
	    	        }
	    	     }
	    	
	    }
	});
	
	function getCommitMessage(tooltipItem, datasets) {
	    return datasets[tooltipItem.datasetIndex].data.find(datum => {
	        return datum.x === tooltipItem.xLabel && datum.y === tooltipItem.yLabel;
	    }).commitMessage;
	}
	function getExercicePath(tooltipItem, datasets) {
	    return datasets[tooltipItem.datasetIndex].data.find(datum => {
	        return datum.x === tooltipItem.xLabel && datum.y === tooltipItem.yLabel;
	    }).exercicePath;
	}
	function getModifiedFiles(tooltipItem, datasets) {
	    return datasets[tooltipItem.datasetIndex].data.find(datum => {
	        return datum.x === tooltipItem.xLabel && datum.y === tooltipItem.yLabel;
	    }).modifiedFiles;
	}
	
	
		commitList.each(function(i, li) {
			var commit = $(li)
			var div = commit.children()[0];
			
			var timeStampSpan = $(div).find('#timestamp');
			var estimatedEffortSpan = $(div).find('#estimatedEffort');
			var commitMessageSpan = $(div).find('#commitMessage');
     		var exercicePathSpan = $(div).find('#exercicePath');
     		var modifiedFilesSpan = $(div).find('#modifiedFiles');
			
			var timeStampInt = parseInt($(timeStampSpan).text());
			var estimatedEffortInt = parseInt($(estimatedEffortSpan).text());
			var commitMessageText = $(commitMessageSpan).text();
     		var exercicePathText = $(exercicePathSpan).text();
     		var modifiedFilesText = $(modifiedFilesSpan).text();
     		console.log(new Date(timeStampInt))
			
			commitsChart.data.datasets.forEach((dataset) => {
		        dataset.data.push({x:timeStampInt,y:estimatedEffortInt,
		        	commitMessage: commitMessageText, exercicePath: exercicePathText, modifiedFiles: modifiedFilesText });
			});
			commitsChart.update();
		});
		const colors = ["#EF1010","#F05410","#F08A10","#F0B010","#F0E610","#B6F010","#68F010","#10F043","#10F0ED" ];
		var counter = 0;
		for (i = 0; i < commitsChart.data.datasets[0].data.length; i++) {
			if(counter <= colors.length - 1){
				pointBackgroundColors.push(colors[counter]);
				counter ++;
			}else {
				counter = 0;
				pointBackgroundColors.push(colors[counter]);
				counter ++;
			}
		}
		commitsChart.update();
}

function initializeCommitList(viewRootElement, jSweet) {
    console.log("initializeCommitList");

    const commitList = viewRootElement.find("#commit-list li");
    var ctx = viewRootElement.find("#commitsChart");
    var pointBackgroundColors = [];
    var pointRadiusByEstimatedEffort = [0];
    var commitsChart = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                backgroundColor: "#EF1010",
                pointBackgroundColor: pointBackgroundColors,
                label: "student's commit history",
                pointRadius: pointRadiusByEstimatedEffort,
                pointHoverRadius: pointRadiusByEstimatedEffort,
                data: [{}],
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
                        callback: function (value) {
                            return new Date(value).toLocaleDateString('fr-ca', {month: 'short', day: 'numeric'});
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
            tooltips: {
                titleFontSize: 24,
                bodyFontSize: 18,
                callbacks: {
                    label: function (tooltipItem, data) {
                        var commitInfo;
                        commitInfo = ["Timestamp : " + (new Date(tooltipItem.xLabel).toLocaleDateString('fr-ca', {
                            month: 'short',
                            day: 'numeric',
                            hour: 'numeric',
                            minute: 'numeric'
                        })).toString()];
                        commitInfo.push("Estimated Effort : " + tooltipItem.yLabel);
                        commitInfo.push("Commit Message : " + getCommitMessage(tooltipItem, data.datasets));
                        commitInfo.push("Exercice Path : " + getExercicePath(tooltipItem, data.datasets));
                        commitInfo.push("Modified Files : " + getModifiedFiles(tooltipItem, data.datasets));
                        return commitInfo;
                    }
                }
            },
            onClick: function(evt) {//bring to commit on page
                var element = commitsChart.getElementAtEvent(evt);
                if(element.length > 0)
                {
                    var ind = element[0]._index;
                    window.location.replace("#commit-" + ind);
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
        }).exercisePath;
    }

    function getModifiedFiles(tooltipItem, datasets) {
        return datasets[tooltipItem.datasetIndex].data.find(datum => {
            return datum.x === tooltipItem.xLabel && datum.y === tooltipItem.yLabel;
        }).modifiedFiles;
    }


    commitList.each(function (i, li) {
        var commit = $(li)
        var div = commit.children()[0];

        var timeStampSpan = $(div).find('#timestamp');
        var estimatedEffortSpan = $(div).find('#estimatedEffort');
        var commitMessageSpan = $(div).find('#commitMessage');
        var exercisePathSpan = $(div).find('#exercicePath');
        var modifiedFilesSpan = $(div).find('#modifiedFiles');

        var timeStampInt = parseInt($(timeStampSpan).text());
        var estimatedEffortInt = parseInt($(estimatedEffortSpan).text());
        var commitMessageText = $(commitMessageSpan).text();
        var exercisePathText = $(exercisePathSpan).text();
        var modifiedFilesText = $(modifiedFilesSpan).text();

        commitsChart.data.datasets.forEach((dataset) => {
            dataset.data.push({
                x: timeStampInt, y: estimatedEffortInt,
                commitMessage: commitMessageText, exercisePath: exercisePathText, modifiedFiles: modifiedFilesText
            });
        });
        commitsChart.update();
    });

    //colors
    var colors = [];
    var exercisePathsAlreadyColored = [];
    var exercisePathIsInTheArray;
    for (i = 0; i < commitsChart.data.datasets[0].data.length; i++) {
		var exercisePath = commitsChart.data.datasets[0].data[i].exercisePath;
		var color = getRandomColor();
        exercisePathIsInTheArray = false
		if (exercisePathsAlreadyColored.length === 0) {
            exercisePathsAlreadyColored.push(exercisePath);
            colors.push(color);
            pointBackgroundColors.push(color);

		} else {
			for (j = 0; j < exercisePathsAlreadyColored.length; j++) {
				if (exercisePathsAlreadyColored[j] === exercisePath) {
					pointBackgroundColors.push(colors[j]);
                    exercisePathIsInTheArray = true;
				}
			}
			if(!exercisePathIsInTheArray){
                exercisePathsAlreadyColored.push(exercisePath);
                colors.push(color);
                pointBackgroundColors.push(color);
            }
		}
	}
    console.log(exercisePathsAlreadyColored);
    commitsChart.update();


    function getRandomColor() {
        var letters = '0123456789ABCDEF';
        var color = '#';
        for (var i = 0; i < 6; i++) {
            color += letters[Math.floor(Math.random() * 16)];
        }
        return color;
    }

    //size
    for (i = 1; i < commitsChart.data.datasets[0].data.length; i++) { //i = 1, because first entry is not a datapoint
        var estimatedEffort = commitsChart.data.datasets[0].data[i].y;
        if (estimatedEffort >= 0 && estimatedEffort < 10) {
            pointRadiusByEstimatedEffort.push(4);
        } else if (estimatedEffort >= 10 && estimatedEffort < 20) {
            pointRadiusByEstimatedEffort.push(7);
        } else if (estimatedEffort >= 20 && estimatedEffort < 30) {
            pointRadiusByEstimatedEffort.push(9);
        } else if (estimatedEffort >= 30 && estimatedEffort < 40) {
            pointRadiusByEstimatedEffort.push(11);
        } else if (estimatedEffort >= 40 && estimatedEffort < 50) {
            pointRadiusByEstimatedEffort.push(13);
        } else if (estimatedEffort >= 50 && estimatedEffort < 60) {
            pointRadiusByEstimatedEffort.push(16);
        } else if (estimatedEffort >= 60 && estimatedEffort < 70) {
            pointRadiusByEstimatedEffort.push(18);
        } else if (estimatedEffort >= 70 && estimatedEffort < 80) {
            pointRadiusByEstimatedEffort.push(20);
        } else if (estimatedEffort >= 80 && estimatedEffort < 90) {
            pointRadiusByEstimatedEffort.push(22);
        } else if (estimatedEffort >= 90 && estimatedEffort <= 100) {
            pointRadiusByEstimatedEffort.push(25);
        }
    }
    commitsChart.update();


}

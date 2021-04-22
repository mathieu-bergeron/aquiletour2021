function initializeCommitList(viewRootElement, jSweet) {
    console.log("initializeCommitList");

    const commitList = viewRootElement.find("#commit-list li");
    var ctx = viewRootElement.find("#commitsChart");

    var pointBackgroundColors = [0];// color of points
    var pointRadiusByEstimatedEffort = [0];//size of points

    var exercisePaths = []; // for tooltip content
    var timeStamps = [];//
    var estimatedEfforts = [];//
    var commitMessages = [];//
    var modifiedFilesOfEachCommit = [];//

    var colors = [];// for deadlines
    var deadlines = [1715215942 * 1000, 2715215941* 1000, 2015215942* 1000, 2215215942* 1000, 2615215941* 1000, 2515215941* 1000];
    setAllCommitInfo();
    changeColors();
    var annotations = deadlines.map(function(epoch, index) {
        return {
            type: 'line',
            id: 'vline' + index,
            mode: 'vertical',
            scaleID: 'x-axis-1',
            value: epoch,
            borderColor: colors[index],
            borderWidth: 3,
            label: {
                enabled: true,
                backgroundColor: 'rgba(0,0,0,0.3)',
                fontSize: 12
            },
            onMouseenter: function(e) {
               this.options.borderWidth = 6;
                this.options.label.backgroundColor = 'rgba(0,0,0,0.8)';
                this.options.label.fontSize = 18;
                this.options.label.position = "center";
                this.options.label.content = "Remise : " + new Date(epoch).toLocaleDateString('fr-ca', {
                    month: 'short',
                    day: 'numeric',
                    hour: 'numeric',
                    minute: 'numeric'
                }),
                commitsChart.update();
            },
            onMouseleave: function(e) {
                this.options.borderWidth = 3;
                this.options.label.backgroundColor = 'rgba(0,0,0,0.3)';
                this.options.label.fontSize = 12;
                this.options.label.content = "";
                commitsChart.update();
            },
        }
    });
    commitHistoryName = window.location.pathname.slice(4);
    var commitsChart = new Chart(ctx, {
        type: 'scatter',
        data: {
            datasets: [{
                backgroundColor: "#EF1010",
                pointBackgroundColor: pointBackgroundColors,
                label: "L'historique des commits de : " + commitHistoryName,
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
                        labelString: 'Date',
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
                        labelString: "Effort estimé",
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
            annotation: {
                events: ['click','mouseenter','mouseleave'],
                annotations: annotations
            },
            tooltips: {
                titleFontSize: 24,
                bodyFontSize: 18,
                callbacks: {
                    label: function (tooltipItem, data) {
                        var commitInfo;
                        commitInfo = ["Date : " + (new Date(tooltipItem.xLabel).toLocaleDateString('fr-ca', {
                            year: "numeric",
                            month: 'short',
                            day: 'numeric',
                            hour: 'numeric',
                            minute: 'numeric'
                        })).toString()];
                        commitInfo.push("L'effort estimé : " + tooltipItem.yLabel);
                        commitInfo.push("Message du commit : " + getCommitMessage(tooltipItem, data.datasets));
                        commitInfo.push("Chemin de l'exercise : " + getExercicePath(tooltipItem, data.datasets));
                        commitInfo.push("Fichiers modifiés : " + getModifiedFiles(tooltipItem, data.datasets));
                        return commitInfo;
                    }
                }
            },
            onClick: function (evt) {//bring to commit on page
                var element = commitsChart.getElementAtEvent(evt);
                if (element.length > 0) {
                    var ind = element[0]._index;
                    $(viewRootElement.find("#commit-" + ind).parent()).css('border-style', 'solid');
                    $(viewRootElement.find("#commit-" + ind).parent()).css('border-color', 'red');
                    setTimeout(function () {
                        $(viewRootElement.find("#commit-" + ind).parent()).css('border-style', '');
                        $(viewRootElement.find("#commit-" + ind).parent()).css('border-color', '');
                    }, 5000)
                    window.location.replace("#commit-" + ind);
                }
            }

        }
    });
    populateGraph(timeStamps, estimatedEfforts, commitMessages, exercisePaths, modifiedFilesOfEachCommit);

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


    function setAllCommitInfo() {
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
            exercisePathText = exercisePathText.replace(/ /g, '');//there's a big empty space, this removes it
            modifiedFilesText = modifiedFilesText.replace(/ /g, '');//
            
            timeStampInt *= 1000;
            $(timeStampSpan).text((new Date(timeStampInt).toLocaleDateString('fr-ca', {
                year: "numeric",
                month: 'short',
                day: 'numeric',
                hour: 'numeric',
                minute: 'numeric'
            })).toString());
            timeStamps.push(timeStampInt);
            estimatedEfforts.push(estimatedEffortInt);
            if(commitMessageText.length > 40){//cannot show more than 40 characters
                commitMessageText = commitMessageText.slice(0,39) + "...";
            }
            commitMessages.push(commitMessageText);
            if(exercisePathText.length > 40){
                exercisePathText = exercisePathText.slice(0,39) + "...";
            }
            exercisePaths.push(exercisePathText);
            if(modifiedFilesText.length > 40){
                modifiedFilesText = modifiedFilesText.slice(0,39) + "...";
            }
            modifiedFilesOfEachCommit.push(modifiedFilesText);
        });
    }
    function populateGraph(timeStamps, estimatedEfforts, commitMessages, exercisePaths, modifiedFilesOfEachCommit ){
        for(i = 0; i < timeStamps.length; i++){
            commitsChart.data.datasets.forEach((dataset) => {
                dataset.data.push({
                    x: timeStamps[i], y: estimatedEfforts[i],
                    commitMessage: commitMessages[i], exercisePath: exercisePaths[i], modifiedFiles: modifiedFilesOfEachCommit[i]
                });
            });
        }
        commitsChart.update();
    }

    //colors
    function changeColors() {
        var exercisePathsAlreadyColored = [];
        var exercisePathIsInTheArray;
        for (i = 0; i < exercisePaths.length; i++) {
            var exercisePath = exercisePaths[i];
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
                if (!exercisePathIsInTheArray) {
                    exercisePathsAlreadyColored.push(exercisePath);
                    colors.push(color);
                    pointBackgroundColors.push(color);
                }
            }
        }
    }


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
            pointRadiusByEstimatedEffort.push(8);
        } else if (estimatedEffort >= 10 && estimatedEffort < 20) {
            pointRadiusByEstimatedEffort.push(10);
        } else if (estimatedEffort >= 20 && estimatedEffort < 30) {
            pointRadiusByEstimatedEffort.push(12);
        } else if (estimatedEffort >= 30 && estimatedEffort < 40) {
            pointRadiusByEstimatedEffort.push(14);
        } else if (estimatedEffort >= 40 && estimatedEffort < 50) {
            pointRadiusByEstimatedEffort.push(16);
        } else if (estimatedEffort >= 50 && estimatedEffort < 60) {
            pointRadiusByEstimatedEffort.push(18);
        } else if (estimatedEffort >= 60 && estimatedEffort < 70) {
            pointRadiusByEstimatedEffort.push(20);
        } else if (estimatedEffort >= 70 && estimatedEffort < 80) {
            pointRadiusByEstimatedEffort.push(22);
        } else if (estimatedEffort >= 80 && estimatedEffort < 90) {
            pointRadiusByEstimatedEffort.push(24);
        } else if (estimatedEffort >= 90 && estimatedEffort <= 100) {
            pointRadiusByEstimatedEffort.push(26);
        }
    }
    commitsChart.update();


}

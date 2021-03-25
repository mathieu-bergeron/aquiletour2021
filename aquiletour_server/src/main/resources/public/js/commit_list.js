function initializeCommitList(viewRootElement, jSweet) {
	console.log("initializeCommitList");

	const commitList = viewRootElement.find("#commit-list li");
	var ctx = viewRootElement.find("#myChart");
	var myChart = new Chart(ctx, {
	    type: 'scatter',
	    data: {
	    	datasets: [{
	    		backgroundColor: 'rgb(255, 30, 30)',
	            label: "student's commit history",
	            data: [{
	            }],
	         }]
	    },
	    options: {
	    	scales: {
	    	      xAxes: [{
	    	        display: true,
	    	        gridLines: {
	    	          display: true,
	    	          color: "#000000"
	    	        },
	    	        scaleLabel: {
	    	          display: true,
	    	          labelString: 'Timestamp'
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
	    	          labelString: 'Estimated Effort'
	    	        }
	    	      }]
	    	    },
	    	tooltips:{
	    		 callbacks: {
	                 label: function(tooltipItem, data) {
	                     var label;
	                  
	                     commitList.each(function(i, li) {
	                    		var commit = $(li)
		             			var div = commit.children()[0];
	                    		var timeStampSpan = $(div).find('#timestamp');
	                    		var timeStamp = parseInt($(timeStampSpan).text());
	                    		
	                    		if(timeStamp==tooltipItem.xLabel){
	                    			var commitMessageSpan = $(div).find('#commitMessage');
			             			var exercicePathSpan = $(div).find('#exercicePath');
			             			var modifiedFilesSpan = $(div).find('#modifiedFiles');
			             			
			             			var commitMessage = $(commitMessageSpan).text();
			             			var exercicePath = $(exercicePathSpan).text();
			             			var modifiedFiles = $(modifiedFilesSpan).text();
			             			
			             			label = ["Timestamp : " + tooltipItem.xLabel];
	         						label.push("Estimated Effort : " + tooltipItem.yLabel);
	         						label.push("Commit Message : " + commitMessage);
	         						label.push("Exercice Path : " + exercicePath);
	         						label.push("Modified Files : " + modifiedFiles); 
	                    		}                 	             			
	             		});
	                     return label;
	                 }
	             }
	    	}
	    	
	    }
	});
	
		commitList.each(function(i, li) {
			var commit = $(li)
			var div = commit.children()[0];
			
			var timeStampSpan = $(div).find('#timestamp');
			var estimatedEffortSpan = $(div).find('#estimatedEffort');
			
			var timeStamp = parseInt($(timeStampSpan).text());
			var estimatedEffort = parseInt($(estimatedEffortSpan).text());
			
			myChart.data.datasets.forEach((dataset) => {
		        dataset.data.push({x:timeStamp,y:estimatedEffort});
			});
			myChart.update();
		});
}

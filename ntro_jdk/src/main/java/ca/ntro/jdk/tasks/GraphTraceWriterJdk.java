package ca.ntro.jdk.tasks;

import java.io.File;
import java.io.IOException;

import ca.ntro.core.system.log.Log;
import ca.ntro.core.task2.GraphDescription;
import ca.ntro.core.task2.GraphTraceWriter;

public class GraphTraceWriterJdk implements GraphTraceWriter {
	
	private File graphDir;
	private String baseName;

	public GraphTraceWriterJdk(File graphDir) {
		this.graphDir = graphDir;
		this.baseName = graphDir.getName();
		
		if(!graphDir.exists()) {
			graphDir.mkdirs();
		}
	}

	@Override
	public void write(int index, GraphDescription graph) {
		
		String graphName = String.format("%s_%02d", baseName, index);
		
		GraphWriterJdk writer = new GraphWriterJdk(graphName);
		
		graph.write(writer);
		
		File dotFile = new File(graphDir, graphName + ".dot");
		File svgFile = new File(graphDir, graphName + ".svg");

		try {

			writer.toDot(dotFile);

		} catch (IOException e) {
			
			Log.warning("Unable to write GraphTrace: " + dotFile.getAbsolutePath());

		}

		try {

			writer.toSvg(svgFile);

		} catch (IOException e) {
			
			Log.warning("Unable to write GraphTrace: " + svgFile.getAbsolutePath());
		}
	}
		

}

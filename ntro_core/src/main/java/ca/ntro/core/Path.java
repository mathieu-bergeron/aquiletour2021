package ca.ntro.core;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.system.trace.T;

public class Path {
	
	private List<String> names = new ArrayList<>();
	
	public Path(String path) {
		T.call(this);
		
		if(path.startsWith("/")) {
			path = path.substring(1);
		}
		
		parsePath(path);
	}

	public Path(List<String> names) {
		T.call(this);

		this.names = names;
	}

	private void parsePath(String path) {
		T.call(this);
		
		for(String name : path.split("/")){
			names.add(name);
		}
	}
	
	public boolean startsWith(String name) {
		boolean startsWith = false;
		
		if(names.size() > 0) {
			startsWith = names.get(0).equals(name);
		}

		return startsWith;
	}
	
	public Path subPath(int beginIndex) {
		return subPath(beginIndex, names.size()-1);
	}

	public Path subPath(int beginIndex, int endIndex) {
		Path path = null;
		
		if(ifValidIndices(beginIndex, endIndex)) {
			path = new Path(names.subList(beginIndex, endIndex));
		}else {
			path = new Path(new ArrayList<>());
		}
		
		return path;
		
	}
	
	private boolean ifValidIndices(int beginIndex, int endIndex) {
		return endIndex < names.size() 
				&& endIndex >= beginIndex
				&& beginIndex >= 0;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for(String name : names) {
			builder.append("/");
			builder.append(name);
		}
		
		return builder.toString();
	}

	public Path removePrefix(String prefix) {
		T.call(this);
		
		Path remainder = null;
		
		if(startsWith(prefix)) {
			remainder = subPath(1);
			
		}else {

			remainder = subPath(0);
		}

		return remainder;
	}
}
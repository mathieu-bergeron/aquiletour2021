package ca.ntro.core;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class Path implements JsonSerializable {
	
	private List<String> names = new ArrayList<>();

	public Path() {
		T.call(this);
	}
	
	public Path(String path) {
		T.call(this);
		
		parsePath(path);
	}

	private Path(List<String> names) {
		T.call(this);

		this.names = names;
	}

	private void parsePath(String path) {
		T.call(this);
		
		for(String name : path.split("/")){
			if(name.length() > 0) {
				names.add(name);
			}
		}
	}

	public void parseFileName(String path) {
		T.call(this);

		for(String name : path.split("_")){
			if(name.length() > 0) {
				names.add(name);
			}
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
			path = new Path(names.subList(beginIndex, endIndex+1));
		}else {
			path = new Path();
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
		
		if(names.size() > 0) {
			for(String name : names) {
				builder.append("/");
				builder.append(name);
			}
		}else {
			builder.append("/");
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

	public String name(int index) {
		T.call(this);
		
		String name = null;
		
		if(ifIndexValid(index)) {

			name = names.get(index);
		}

		return name;
	}

	private boolean ifIndexValid(int index) {
		T.call(this);

		return index >= 0 && index < names.size();
	}

	public int nameCount() {
		T.call(this);
		
		return names.size();
	}

	public String toFileName() {
		StringBuilder builder = new StringBuilder();
		
		if(names.size() > 0) {
			builder.append(names.get(0));
			for(int i = 1; i < names.size(); i++) {
				builder.append("_");
				builder.append(names.get(i));
			}
		}else {
			builder.append("_");
		}
		
		return builder.toString();
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public boolean isPrefixOf(Path path) {
		boolean isPrefixOf = true;
		
		if(nameCount() >= path.nameCount()) {

			isPrefixOf = false;

		}else {
			for(int i = 0; i < nameCount(); i++) {
				if(!name(i).equals(path.name(i))) {
					isPrefixOf = false;
					break;
				}
			}
		}

		return isPrefixOf;
	}

	public Path parent() {
		T.call(this);

		Path parentPath = null;

		if(nameCount() > 1) {

			parentPath = subPath(0, nameCount() - 2);

		}else if(nameCount() == 1) {

			parentPath = new Path();
		}
		
		return parentPath;
	}

}

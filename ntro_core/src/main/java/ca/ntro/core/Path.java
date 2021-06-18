package ca.ntro.core;

import java.util.ArrayList;
import java.util.List;

import ca.ntro.core.json.JsonSerializable;
import ca.ntro.core.system.trace.T;

public class Path implements JsonSerializable {
	
	public static final String PATH_SEPARATOR = "/";
	public static final String KEY_SEPARATOR = "/";
	public static final String FILENAME_SEPARATOR = "¤";
	public static final String HTML_ID_SEPARATOR = "-";
	
	private List<String> names = new ArrayList<>();

	public Path() {
		T.call(this);
	}

	public static Path fromRawPath(String rawPath) {
		T.call(Path.class);
		
		Path path = new Path();
		
		path.parsePath(rawPath, PATH_SEPARATOR);

		return path;
	}

	public static Path fromKey(String key) {
		T.call(Path.class);
		
		Path path = new Path();
		
		path.parsePath(key, KEY_SEPARATOR);

		return path;
	}

	public static Path fromHtmlId(String htmlId) {
		T.call(Path.class);
		
		Path path = new Path();
		
		path.parsePath(htmlId, HTML_ID_SEPARATOR);

		return path;
	}

	public static Path fromFileName(String filename) {
		T.call(Path.class);
		
		Path path = new Path();
		
		path.parsePath(filename, FILENAME_SEPARATOR);

		return path;
	}

	private Path(List<String> names) {
		T.call(this);

		this.names = names;
	}

	private void parsePath(String path, String separator) {
		T.call(this);
		
		for(String name : path.split(separator)){
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
	
	public Path clone() {
		return subPath(0, names.size()-1);
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
	
	public String toString(String separator, boolean startsWithSeparator) {
		StringBuilder builder = new StringBuilder();
		
		if(startsWithSeparator) {
			builder.append(separator);
		}
		
		if(nameCount() > 0) {
			builder.append(name(0));
		}
		
		for(int i = 1; i < nameCount(); i++) {
			builder.append(separator);
			builder.append(name(i));
		}

		return builder.toString();
	}

	@Override
	public String toString() {
		return toRawPath();
	}

	public String toRawPath() {
		return toString(PATH_SEPARATOR, true);
	}

	public String toFileName() {
		return toString(FILENAME_SEPARATOR, false);
	}

	public String toHtmlId() {
		return toString(HTML_ID_SEPARATOR, false);
	}

	public String toKey() {
		return toString(KEY_SEPARATOR, false);
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

	public void addName(String name) {
		T.call(this);
		
		getNames().add(name);
	}

	public String lastName() {
		T.call(this);

		int nameCount = nameCount();
		return name(nameCount-1);
	}
	
	@Override
	public boolean equals(Object other) {
		if(other == this) return true;
		if(other == null) return false;
		if(other instanceof Path) {
			Path otherPath = (Path) other;
			
			if(otherPath.nameCount() != nameCount()) return false;
			
			for(int i = 0; i < otherPath.nameCount(); i++) {
				if(!name(i).equals(otherPath.name(i))) {
					return false;
				}
			}
			
			return true;
		}

		return false;

	}
	public boolean isRootPath() {
		T.call(this);

		return nameCount() == 0;
	}

	protected void copyNamesOf(Path path) {
		T.call(this);
		
		getNames().clear();

		for(int i = 0; i < path.nameCount(); i++) {
			this.addName(path.name(i));
		}
	}
}

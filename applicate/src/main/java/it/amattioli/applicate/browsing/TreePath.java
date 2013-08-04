package it.amattioli.applicate.browsing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TreePath {
	private List<Integer> path;
	
	public TreePath(List<Integer> path) {
		this.path = path;
	}
	
	public TreePath(int... path) {
		this.path = new ArrayList<Integer>();
		for (Integer curr: path) {
			this.path.add(curr);
		}
	}
	
	public TreePath(String s) {
		String[] spath = s.substring(1, s.length()-1).split(",");
		path = new ArrayList<Integer>(spath.length);
		for (int i = 0; i < spath.length; i++) {
			path.add(Integer.valueOf(spath[i].trim()));
		}
	}
	
	public int[] asIntArray() {
		int[] result = new int[path.size()];
		for (int i=0; i < path.size(); i++) {
			result[i] = path.get(i);
		}
		return result;
	}
	
	public TreePath parentPath() {
		return new TreePath(path.subList(0, path.size() - 1));
	}
	
	public TreePath childrenPath(int index) {
		List<Integer> newPath = new ArrayList<Integer>(path);
		newPath.add(index);
		return new TreePath(newPath);
	}
	
	public String toString() {
		return Arrays.toString(asIntArray());
	}
	
	public int depth() {
		return path.size();
	}
	
	public int elementAt(int idx) {
		return path.get(idx);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof TreePath)) {
			return false;
		}
		TreePath other = (TreePath) obj;
		if (path == null) {
			if (other.path != null) {
				return false;
			}
		} else if (!path.equals(other.path)) {
			return false;
		}
		return true;
	}

}

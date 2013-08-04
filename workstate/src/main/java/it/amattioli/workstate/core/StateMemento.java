package it.amattioli.workstate.core;

import java.util.*;

/**
 * Capture the internal representation of a state so to persist it.
 * 
 */
public class StateMemento implements java.io.Serializable {
	private Map<String, StateMemento> children = Collections.emptyMap();
	private StateMemento parent;
	private String tag;
	private Map<String, Object> attributes;

	public StateMemento(String tag, StateMemento parent, Map<String,Object> attributes) {
		this.tag = tag;
		this.parent = parent;
		if (attributes == null) {
			this.attributes = Collections.emptyMap();
		} else {
			this.attributes = attributes;
		}
		if (parent != null) {
			parent.addChild(tag, this);
		}
	}

	private void addChild(String tag, StateMemento child) {
		if (children.isEmpty()) {
			children = new HashMap<String, StateMemento>();
		}
		children.put(tag, child);
	}

	public String getTag() {
		StringBuffer result = new StringBuffer(tag);
		if (!children.isEmpty()) {
			result.append("[");
			for (Iterator<StateMemento> iter = children.values().iterator(); iter.hasNext();) {
				StateMemento curr = (StateMemento) iter.next();
				result.append(curr.getTag());
				if (iter.hasNext()) {
					result.append(",");
				} else {
					result.append("]");
				}
			}
		}
		return result.toString();
	}

	public Map<String,Object> getAttributes() {
		return Collections.unmodifiableMap(attributes);
	}

	public Map<String, StateMemento> getChildren() {
		return Collections.unmodifiableMap(children);
	}

	/**
	 * Reconstruct a {@link StateMemento} tree given a string obtained using
	 * {@link #getTag()}. The string does not contains attribute values so they
	 * will not be reconstructed.
	 * 
	 * @param tag
	 * @return
	 */
	public static StateMemento fromTag(String tag) {
		Stack<StateMemento> parents = new Stack<StateMemento>();
		StateMemento currMem = null;
		StateMemento result = null;
		StringBuffer currTag = new StringBuffer();
		for (int i = 0; i < tag.length(); i++) {
			StateMemento currParent = null;
			switch (tag.charAt(i)) {
			case '[':
				if (!parents.isEmpty()) {
					currParent = (StateMemento) parents.peek();
				} else {
					currParent = null;
				}
				currMem = new StateMemento(currTag.toString(), currParent, null);
				currTag = new StringBuffer();
				parents.push(currMem);
				break;

			case ']':
				if (!currTag.toString().equals("")) {
					if (!parents.isEmpty()) {
						currParent = (StateMemento) parents.peek();
					} else {
						currParent = null;
					}
					currMem = new StateMemento(currTag.toString(), currParent, null);
					currTag = new StringBuffer();
				}
				result = (StateMemento) parents.pop();
				break;

			case ',':
				if (!parents.isEmpty()) {
					currParent = (StateMemento) parents.peek();
				} else {
					currParent = null;
				}
				currMem = new StateMemento(currTag.toString(), currParent, null);
				currTag = new StringBuffer();
				break;

			default:
				currTag.append(tag.charAt(i));
				break;
			}
		}
		return result;
	}

}

package it.amattioli.guidate.smarttab;

import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Include;

public class IncludedSmartTabDescriptor extends SmartTabDescriptor {
	private String src;
	private Map<String, Object> args = new HashMap<String, Object>();

	public IncludedSmartTabDescriptor() {
		
	}
	
	public IncludedSmartTabDescriptor(String identifier, String label, String src) {
		super(identifier, label);
		setSrc(src);
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
	
	public void setArg(String name, Object value) {
		args.put(name, value);
	}
	
	public Object getArg(String name) {
		return args.get(name);
	}

	@Override
	public Component getPanelContent() {
		Include included = new Include(getSrc());
		included.setDynamicProperty(SmartTabComposer.SMART_TAB_IDENTIFIER, getIdentifier());
		for (String curr : args.keySet()) {
			included.setDynamicProperty(curr, args.get(curr));
		}
		return included;
	}
}

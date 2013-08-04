package it.amattioli.guidate.containers;

import org.zkoss.zk.ui.Component;

public class BackBeanAccessorImpl implements BackBeanAccessor {
	private Component comp;
	
	public BackBeanAccessorImpl(Component comp) {
		this.comp = comp;
	}
	
	@Override
	public Object getBackBean() {
		return BackBeans.findBackBean(comp);
	}

}

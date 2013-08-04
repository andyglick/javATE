package it.amattioli.guidate.containers;

import org.zkoss.lang.reflect.FusionInvoker;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.ComposerExt;
import org.zkoss.zk.ui.util.GenericAutowireComposer;

public class BackBeanComposer extends GenericAutowireComposer implements ComposerExt {

	@Override
	public ComponentInfo doBeforeCompose(Page page, Component comp, ComponentInfo info) {
		return info;
	}

	@Override
	public void doBeforeComposeChildren(Component comp) throws Exception {
		bindComponent(comp);
		BackBeanAccessor accessor = new BackBeanAccessorImpl(comp);
		Object obj = FusionInvoker.newInstance(new Object[] { comp, accessor });
		comp.setVariable(comp.getId(), obj, true);
	}

	@Override
	public boolean doCatch(Throwable e) throws Exception {
		return false;
	}

	@Override
	public void doFinally() throws Exception {
		
	}
	
}

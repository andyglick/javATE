package it.amattioli.guidate.btns;

import it.amattioli.guidate.containers.BackBeans;

import java.util.HashMap;
import java.util.Set;

import static java.util.Map.Entry;

import static org.zkoss.zk.ui.Component.*;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;
import org.zkoss.zul.Window;

/**
 * A composer that allows to open a modal window when an onClick event
 * is sent.<p>
 * The window definition is retrieved from a .zul file whose URI is in
 * a custom-attribute called "windowUri" of the component to which this
 * component is applied.
 * <pre>
 *     &lt;button apply="it.amattioli.guidate.btns.OpenWindowComposer"&gt;
 *         &lt;custom-attributes windowUri="myWindow.zul"/&gt;
 *     &lt;/button&gt;
 * </pre>
 * If you need to pass parameters to the window you can use custom-attributes
 * with the "arg." prefix like in:
 * <pre>
 *     &lt;button apply="it.amattioli.guidate.btns.OpenWindowComposer"&gt;
 *         &lt;custom-attributes windowUri="myWindow.zul"
 *                            arg.myParam="myValue"/&gt;
 *     &lt;/button&gt;
 * </pre>
 * The parameter value can be retrieved from a back-bean property using the
 * "propertyArg" prefix:
 * <pre>
 *     &lt;button apply="it.amattioli.guidate.btns.OpenWindowComposer"&gt;
 *         &lt;custom-attributes windowUri="myWindow.zul"
 *                            propertyArg.myParam="myPropertyName"/&gt;
 *     &lt;/button&gt;
 * </pre>
 *  
 * @author andrea
 *
 */
public class OpenWindowComposer extends GenericComposer {

	private static final String PROPERTY_ARG_PREFIX = "propertyArg.";
	private static final String ARG_PREFIX = "arg.";
	private static final String WINDOW_URI = "windowUri";

	public void onClick(Event evt) throws Exception {
		Component target = evt.getTarget();
		String windowUrl = getWindowUrl(target);
		HashMap<String, Object> arg = getArguments(target);
		Window win = (Window)Executions.createComponents(windowUrl, null, arg);
		win.doModal();
	}

	protected String getWindowUrl(Component target) {
		return (String)target.getAttribute(WINDOW_URI);
	}
	
	protected HashMap<String, Object> getArguments(Component target) throws Exception {
		HashMap<String, Object> arg = new HashMap<String, Object>();
		Object backBean = BackBeans.findBackBean(target);
		for (Entry<String, Object> attr: (Set<Entry<String, Object>>)target.getAttributes(COMPONENT_SCOPE).entrySet()) {
			if (!WINDOW_URI.equals(attr.getKey())) {
				if (attr.getKey().startsWith(ARG_PREFIX)) {
					String argName = attr.getKey().substring(ARG_PREFIX.length());
					Object value = attr.getValue();
					arg.put(argName, value);
				} else if (attr.getKey().startsWith(PROPERTY_ARG_PREFIX)) {
					String argName = attr.getKey().substring(PROPERTY_ARG_PREFIX.length());
					Object value;
					try {
						value = PropertyUtils.getProperty(backBean, (String)attr.getValue());
					} catch(NestedNullException e) {
						value = null;
					}
					arg.put(argName, value);
				}
			}
		}
		return arg;
	}
	
}

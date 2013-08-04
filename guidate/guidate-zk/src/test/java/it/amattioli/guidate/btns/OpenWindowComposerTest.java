package it.amattioli.guidate.btns;

import java.util.HashMap;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Button;

import junit.framework.TestCase;

public class OpenWindowComposerTest extends TestCase {

	public void testWindowUri() {
		Component target = new Button();
		target.setAttribute("windowUri", "myWindow.zul");
		OpenWindowComposer composer = new OpenWindowComposer();
		String uri = composer.getWindowUrl(target);
		assertEquals("myWindow.zul", uri);
	}
	
	public void testSimpleArgs() throws Exception {
		Component target = new Button();
		target.setAttribute("windowUri", "myWindow.zul");
		target.setAttribute("arg.argument1", "value1");
		target.setAttribute("arg.argument2", "value2");
		OpenWindowComposer composer = new OpenWindowComposer();
		HashMap<String, Object> args = composer.getArguments(target);
		assertEquals(2, args.size());
		assertEquals("value1", args.get("argument1"));
		assertEquals("value2", args.get("argument2"));
	}
	
	public void testPropertyArgs() throws Exception {
		Component target = new Button();
		target.setAttribute("windowUri", "myWindow.zul");
		target.setAttribute("propertyArg.argument2", "stringProperty");
		target.setAttribute("backBean", new MyBean());
		OpenWindowComposer composer = new OpenWindowComposer();
		HashMap<String, Object> args = composer.getArguments(target);
		assertEquals(1, args.size());
		assertEquals("stringvalue", args.get("argument2"));
	}
}

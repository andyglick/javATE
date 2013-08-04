package it.amattioli.workstate.config;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import java.io.*;
import static it.amattioli.workstate.config.ConfigBuilder.StateType.*;

/**
 * 
 * @author a.mattioli
 */
public class SaxEventsHandler extends DefaultHandler {
	private ConfigBuilder builder;
	protected CharArrayWriter cDataBuffer = new CharArrayWriter();

	public SaxEventsHandler(ConfigBuilder builder) {
		if (builder == null) {
			// TODO: GESTIRE MEGLIO L'ECCEZIONE !!!!!!!!!!!!
			throw new NullPointerException();
		}
		this.builder = builder;
	}

	public void startElement(String namespaceURI, String localName, String qName, Attributes attrs) throws SAXException {
		if (qName.equals("SimpleState")) {
			builder.newState(SIMPLE, attrs.getValue("tag"), attrs.getValue("id"));
		} else if (qName.equals("SequentialState")) {
			builder.newState(SEQUENTIAL, attrs.getValue("tag"), attrs.getValue("id"));
		} else if (qName.equals("ConcurrentState")) {
			builder.newState(CONCURRENT, attrs.getValue("tag"), attrs.getValue("id"));
		} else if (qName.equals("ReferenceState")) {
			builder.newState(REFERENCE, attrs.getValue("tag"), attrs.getValue("id"));
			builder.setReference(attrs.getValue("ref"));
		} else if (qName.equals("InitialState")) {
			builder.newState(INITIAL, "", attrs.getValue("id"));
		} else if (qName.equals("FinalState")) {
			builder.newState(FINAL, "", attrs.getValue("id"));
		} else if (qName.equals("JunctionPoint")) {
			builder.newState(JUNCTION, "", attrs.getValue("id"));
		} else if (qName.equals("Attribute")) {
			builder.newAttribute(attrs.getValue("tag"),attrs.getValue("class"), attrs.getValue("initial"));
		} else if (qName.equals("Event")) {
			builder.newEvent(attrs.getValue("tag"), attrs.getValue("id"));
		} else if (qName.equals("ExternalTransition")) {
			builder.newExternalTransition(attrs.getValue("event"), attrs.getValue("start"), attrs.getValue("end"));
		} else if (qName.equals("InternalTransition")) {
			builder.newInternalTransition(attrs.getValue("event"), attrs.getValue("state"));
		} else if (qName.equals("Machine")) {
			builder.setOwnerClass(attrs.getValue("owner"));
			builder.newState(MACHINE, attrs.getValue("tag"), attrs.getValue("id"));
		}
		cDataBuffer.reset();
	}

	public void characters(char[] ch, int start, int length) throws SAXException {
		cDataBuffer.write(ch, start, length);
	}

	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if (qName.equals("EntryAction")) {
			builder.setEntryAction(cDataBuffer.toString());
		} else if (qName.equals("ExitAction")) {
			builder.setExitAction(cDataBuffer.toString());
		} else if (qName.equals("SimpleState")) {
			builder.endState();
		} else if (qName.equals("SequentialState")) {
			builder.endState();
		} else if (qName.equals("ConcurrentState")) {
			builder.endState();
		} else if (qName.equals("ReferenceState")) {
			builder.endState();
		} else if (qName.equals("InitialState")) {
			builder.endState();
		} else if (qName.equals("FinalState")) {
			builder.endState();
		} else if (qName.equals("JunctionPoint")) {
			builder.endState();
		} else if (qName.equals("Attribute")) {
			builder.endAttribute();
		} else if (qName.equals("Event")) {
			builder.endEvent();
		} else if (qName.equals("ExternalTransition")) {
			builder.endTransition();
		} else if (qName.equals("InternalTransition")) {
			builder.endTransition();
		} else if (qName.equals("Action")) {
			builder.setAction(cDataBuffer.toString());
		} else if (qName.equals("Guard")) {
			builder.setGuard(cDataBuffer.toString());
		} else if (qName.equals("Machine")) {
			builder.endState();
		}
	}

}

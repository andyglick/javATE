package it.amattioli.workstate.config;

import it.amattioli.workstate.core.*;

import java.io.*;
import javax.xml.parsers.*;

/**
 * An XmlConfiguration reads a state machine configuration from an XML file.
 * The datasource string will be interpreted as the name of a resource to be found
 * in the current thread classpath.
 * The xml file DTD can be found in WorkflowConfig.dtd
 * 
 * @author a.mattioli
 */
public class XmlConfiguration implements Configuration, java.io.Serializable {
	private static final SAXParserFactory PARSER_FACTORY = SAXParserFactory.newInstance();
	private String id;
	private String fileName;
	private int hashCode = 0;
	private MetaMachine config;

	public XmlConfiguration() {
	}

	/**
	 * Construct an XmlConfiguration given the file name and the configuration id.
	 * 
	 * @param fileName the name of the xml resource file containing the configuration
	 *            
	 * @param id the configuration identifier
	 *            
	 */
	public XmlConfiguration(String fileName, String id) {
		this.id = id;
		this.fileName = fileName;
	}

	public void setSource(String source) {
		this.fileName = source;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof XmlConfiguration) {
			XmlConfiguration reader = (XmlConfiguration) obj;
			result = getId().equals(reader.getId())
					&& fileName.equals(reader.fileName);
		}
		return result;
	}

	public int hashCode() {
		if (hashCode == 0) {
			int result = 17;
			result = 37 * result + id.hashCode();
			result = 37 * result + fileName.hashCode();
			hashCode = result;
		}
		return hashCode;
	}

	@Override
	public MetaMachine read() {
		if (config == null) {
			try {
				SAXParser parser = PARSER_FACTORY.newSAXParser();
				ConfigBuilder builder = new ConfigBuilder(this, getId());
				SaxEventsHandler seh = new SaxEventsHandler(builder);
				InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
				// InputStream input =
				// getClass().getClassLoader().getResourceAsStream(fileName);
				parser.parse(input, seh);
				config = builder.getResult();
			} catch (Exception e) {
				// TODO: Gestire meglio l'eccezione
				throw new RuntimeException("Error during state machine configuration: ", e);
			}
		}
		return config;
	}

}

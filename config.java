import processing.core.*;
import java.awt.*;
import proxml.*;
import proxml.XMLElement;

class ConfigParser {
	private XMLInOut xmlIO;
	private PApplet parent;
	private XMLElement xmlElement;
	private String configFile = "config.xml";
	
	ConfigParser(final PApplet parent){
		this.parent = parent;
		this.xmlIO = new XMLInOut(parent);
		this.xmlIO.loadElement(this.configFile);
	}
	
	public void xmlEvent(XMLElement xmlElement){
		this.xmlElement = xmlElement;
	}
	
	public int getIntConfig(String key){
		int val=0;
		try {
			val = this.xmlElement.getIntAttribute(key);
		} catch(NullPointerException e) { parent.println(e); }
		return val;
	}
	
	public String getStrConfig(String key){
		return this.xmlElement.getAttribute(key);
	}
	
	public void saveConfig(String key, int val) {
		this.xmlElement.addAttribute(key, val);
		this.saveToFile();
	}
	
	private void saveToFile() {
		this.xmlIO.saveElement(this.xmlElement, this.configFile); 
	}
}

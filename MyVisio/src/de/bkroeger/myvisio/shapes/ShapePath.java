package de.bkroeger.myvisio.shapes;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.bkroeger.myvisio.utility.TechnicalException;
import de.bkroeger.myvisio.vector.BaseVectorCommand;
import de.bkroeger.myvisio.vector.LineToCommand;
import de.bkroeger.myvisio.vector.MoveToCommand;

public class ShapePath {

	private List<BaseVectorCommand> commands;
	
	public ShapePath() {
		commands = new ArrayList<BaseVectorCommand>();
	}
	
	public ShapePath(Element elem) throws TechnicalException {
		commands = new ArrayList<BaseVectorCommand>();
		
		this.load(elem);
	}
	
	private void load(Element parent) throws TechnicalException {
		
		NodeList children1 = parent.getChildNodes();
		
		for (int j=0; j<children1.getLength(); j++) {
			Node child1 = children1.item(j);
			if (child1.getNodeType() == Node.ELEMENT_NODE) {
				Element elem1 = (Element)child1;
				
				switch (elem1.getLocalName()) {
				case "commands":
					loadCommands(elem1);
					break;
				}
			}
		}
	}
	
	private void loadCommands(Element parent) throws TechnicalException {
		
		NodeList children2 = parent.getChildNodes();
		
		for (int i=0; i<children2.getLength(); i++) {
			Node child2 = children2.item(i);
			
			if (child2.getNodeType() == Node.ELEMENT_NODE) {
				Element elem2 = (Element)child2;
				if (elem2.getLocalName().equals("command")) {
					
					BaseVectorCommand command = null;
					String c = elem2.getAttribute("char").substring(0,1);
					switch (c) {
					case "M":
					case "m":
						command = new MoveToCommand(c, elem2);
						break;
					case "L":
					case "l":
						command = new LineToCommand(c, elem2);
						break;
					default:
						throw new TechnicalException("Invalid vector command: "+c);
					}
					this.commands.add(command);
				}
			}
		}

	}
}


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XML {

	public static final String xmlFilePath = "E:\\Project01\\Stickylets\\src\\vault.xml";
	static File xmlFile = new File(xmlFilePath);

	public static void main(String[] args) {

	}

	static void insert(int id, String titleVal, String content) {

		if(xmlFile.exists()) {
			//if file exisits
			try {
			//an instance of factory that gives a document builder  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(xmlFile);  
			doc.getDocumentElement().normalize();  
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
			NodeList nodeList = doc.getElementsByTagName("notes");  
			// nodeList is not iterable, so we are using for loop 
			
			for (int itr = 0; itr < nodeList.getLength(); itr++)   
			{  
			Node node = nodeList.item(itr);  
			//System.out.println("\nNode Name :" + node.getNodeName());  
			if (node.getNodeType() == Node.ELEMENT_NODE)   
			{  
			Element eElement = (Element) node;  
			System.out.println("Student id: "+ eElement.getElementsByTagName("title").item(0).getTextContent());  
			System.out.println("First Name: "+ eElement.getElementsByTagName("text").item(0).getTextContent()); 
			} } 
			
			
			} catch(Exception e) {
				
			}
			
		}else{
			//if not exists create a new xml file

			try {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.newDocument();

				// root element
				Element rootElement = doc.createElement("notes");
				doc.appendChild(rootElement);

				// setting attribute to the root element
				Attr attr = doc.createAttribute("id");
				attr.setValue(Integer.toString(id));
				rootElement.setAttributeNode(attr);

				// Title element
				Element title = doc.createElement("title");
				title.appendChild(doc.createTextNode(titleVal));
				rootElement.appendChild(title);

				//contents
				Element text = doc.createElement("text");
				text.appendChild(doc.createTextNode(content));
				rootElement.appendChild(text);

				// write the content into xml file
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(xmlFilePath));
				transformer.transform(source, result);

				writeXMLFile (doc);

				// Output to console for testing
				StreamResult consoleResult = new StreamResult(System.out);
				transformer.transform(source, consoleResult);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	
	private static void writeXMLFile(Document doc) {

		try {
			doc.getDocumentElement().normalize();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer;
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFilePath));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			System.out.println("XML file updated successfully");
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//end of insert    	

	
	
	//retun the list of the data
	ArrayList getTitleList() {
		ArrayList<String> titlesArr = new ArrayList<String>();
		if(xmlFile.exists()) {
			//if file exisits
			try {
			//an instance of factory that gives a document builder  
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			//an instance of builder to parse the specified xml file  
			DocumentBuilder db = dbf.newDocumentBuilder();  
			Document doc = db.parse(xmlFile);  
			doc.getDocumentElement().normalize();  
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
			NodeList nodeList = doc.getElementsByTagName("notes");  
			// nodeList is not iterable, so we are using for loop 
			
			for (int itr = 0; itr < nodeList.getLength(); itr++)   
			{  
			Node node = nodeList.item(itr);  
			//System.out.println("\nNode Name :" + node.getNodeName());  
			if (node.getNodeType() == Node.ELEMENT_NODE)   
			{  
			Element eElement = (Element) node;  
			System.out.println("Title: "+ eElement.getElementsByTagName("title").item(0).getTextContent());  
			System.out.println("Text: "+ eElement.getElementsByTagName("text").item(0).getTextContent());
			titlesArr.add(eElement.getElementsByTagName("title").item(0).getTextContent());
			} } 
			
			
			} catch(Exception e) {
				
			}
			
		}
		return titlesArr;
	}
}

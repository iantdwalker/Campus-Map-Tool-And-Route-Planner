package applicationpackage;

import java.awt.Component;
import java.io.*;

import javax.xml.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;
import org.xml.sax.*;

/**
 * A class to read and write to XML files using a Document.
 */
class XmlParser
{
  private   File                     _xmlFileToRead;
  private   File                     _xmlFileToWrite;
  private   InputStream              _inputStream;
  private   DocumentBuilderFactory   _documentBuilderFactory;
  private   DocumentBuilder          _documentBuilder;
  private   Document                 _xmlDocument;
  private   Component                _parent; 

  /**
   * Constructor.
   * @param parent
   */
  public XmlParser(Component parent)
  {
    _parent = parent;
    this.createDocumentBuilder();    
	}
  
  /**
   * Create the document builder for reading/writing to XML.
   */
  private void createDocumentBuilder()
  {
    try
    {
      //Step 1: Create a DOM factory
			_documentBuilderFactory = DocumentBuilderFactory.newInstance();

			//Step 2: Create a document builder from the factory
      _documentBuilder        = _documentBuilderFactory.newDocumentBuilder();

      //Step 3: Create a document from the document builder
      _xmlDocument            = _documentBuilder.newDocument();
    }
    catch (Exception ex)
    {
      Error.showMessage(_parent, "The parser was not configured correctly: \n" +
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
		}
  }

  /**
   *Load an XML document into Document object tree using filename specified
   *(Application).
   *@param xmlFile
   */  
  public void loadXmlFile(String xmlFile)
  {
    try
    {
      //parse the XML document
      _xmlFileToRead  = new File(xmlFile);
      _xmlDocument    = _documentBuilder.parse(_xmlFileToRead);			
		}
    catch (IllegalArgumentException ex)
    {
      Error.showMessage(_parent, "Please specify a valid XML source: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());
      ex.printStackTrace();   
		}
    catch (IOException ex)
    {
			Error.showMessage(_parent, "Error reading XML document: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
		}
    catch (SAXException ex)
    {
      Error.showMessage(_parent, "Error parsing XML document: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
		}
  }

  /**
   *Load XML document into Document object tree using InputStream (Applet).
   *@param inputStream
   */  
  public void loadXmlFile(InputStream inputStream)
  {
    try
    {
      //parse the XML document
      _inputStream  = inputStream;
			_xmlDocument  = _documentBuilder.parse(_inputStream);			
		}
    catch (IllegalArgumentException ex)
    {
      Error.showMessage(_parent, "Please specify a valid XML source: \n" +
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
		}
    catch (IOException ex)
    {
			Error.showMessage(_parent, "Error reading XML document: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();
		}
    catch (SAXException ex)
    {
			Error.showMessage(_parent, "Error parsing XML document: \n" + 
      ex.getMessage() + "\nReason:\n" + ex.toString());      
      ex.printStackTrace();              
		}
  }

  /**
   *Write document object to a new xml using the filename specified.
   *@param fileName
   */  
  public void writeXmlFile(String fileName)
  {
    try
    {
      //prepare the DOM document for writing
      Source      source    = new DOMSource(_xmlDocument);

      //prepare the output file
      _xmlFileToWrite       = new File(fileName);
      Result      result    = new StreamResult(_xmlFileToWrite);

      //write the DOM document to the file
      Transformer xmlformer = TransformerFactory.newInstance().newTransformer();
      xmlformer.transform(source, result);
    }    
    catch (TransformerConfigurationException ex)
    {
      Error.showMessage(_parent, "XML write error: \n" + ex.getMessage() + 
      "\nReason:\n" + ex.toString());      
      ex.printStackTrace();           
    }
    catch (TransformerException ex)
    {
      Error.showMessage(_parent, "XML write error: \n" + ex.getMessage() + 
      "\nReason:\n" + ex.toString());      
      ex.printStackTrace();                   
    }
  }
  
  /**
   * Set the writable XML Document object to the document specified.
   * @param document
   */
  public void setDocument(Document document)
  {
    _xmlDocument = document;
  }

  /**
   * Get the XML Document object once loaded.
   * @return Document
   */
  public Document getDocument()
  {
    return _xmlDocument;
  }
}
package appletpackage;
import java.io.File;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlParser
{
    /** Generic attributes */
    static int indent = 0;
    File xmlFile;
    File newXmlFile;
    InputStream xmlIs;
    DocumentBuilderFactory dbf;
    DocumentBuilder db;
    Document xpDoc;
    Element root;
    NamedNodeMap attrs;
    Attr attr;

    public XmlParser()
    {
        try
        {
            /** Step 1: Create a DOM factory */
            dbf = DocumentBuilderFactory.newInstance();

            /** Step 2: Create a document builder from the factory */
            db = dbf.newDocumentBuilder();

            /** Step 3: Create a document from the document builder */
            xpDoc = db.newDocument();
        }

        catch (javax.xml.parsers.ParserConfigurationException pce)
        {
            System.out.println("The parser was not configured correctly - " +
            pce.getMessage());
            System.exit(1);
        }
    }

    /** Load an XML document into Document object tree using file (Application)**/
    public void loadXmlFile(String xmlFileIn)
    {
        try
        {
            /** Parse the XML source and obtain the Document node */
            xmlFile = new File(xmlFileIn);
            xpDoc = db.parse(xmlFile);

            /** Get the root element, even though its not used in this sample */
            root = xpDoc.getDocumentElement();    
        }

        catch (java.lang.IllegalArgumentException ae)
        {
            System.out.println("Please specify an XML source - " + ae.getMessage());
            System.exit(1);
        }
        
        catch (java.io.IOException ioe)
        {
            System.out.println("Error reading XML document - " + ioe.getMessage());
            System.exit(1);
        }
        
        catch (org.xml.sax.SAXException se)
        {
            System.out.println("Error parsing document - " + se.getMessage());
            System.exit(1);
        }
    }

    /** Load XML document into Document object tree using InputStream (Applet)*/
    public void loadXmlFile(InputStream isIn)
    {
        try
        {
            /** Parse the XML source and obtain the Document node */
            xmlIs = isIn;
            xpDoc = db.parse(xmlIs);

            /** Get the root element, even though its not used in this sample */
            root = xpDoc.getDocumentElement();    
        }

        catch (java.lang.IllegalArgumentException ae)
        {
            System.out.println("Please specify an XML source - " + ae.getMessage());
            System.exit(1);
        }
        
        catch (java.io.IOException ioe)
        {
            System.out.println("Error reading XML document - " + ioe.getMessage());
            System.exit(1);
        }
        
        catch (org.xml.sax.SAXException se)
        {
            System.out.println("Error parsing document - " + se.getMessage());
            System.exit(1);
        }
    }
    
    /** Write document object to a new xml file *******************************/
    public void writeXmlFile(String filename)
    {
        try
        {
            //Prepare the DOM document for writing
            Source source = new DOMSource(xpDoc);

            //Prepare the output file
            newXmlFile = new File(filename);
            Result result = new StreamResult(newXmlFile);

            //Write the DOM document to the file
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.transform(source, result);
        }
        
        catch (TransformerConfigurationException e)
        {
            System.out.println("XML write error");
        }
        
        catch (TransformerException e)
        {
            System.out.println("XML write error");
        }
    }

    /** Get and set methods for the xml Document object ***********************/
    public void setDocument(Document newDocIn)
    {
        xpDoc = newDocIn;
    }

    public Document getDocument()
    {
        return xpDoc;
    }
}
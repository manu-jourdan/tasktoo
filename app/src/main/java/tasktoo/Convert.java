package tasktoo;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Convert {

    public static void convertXML() {
        try {
            // load the XML file
            File xmlFile = new File("src/main/resources/data.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            // normalize the XML structure
            doc.getDocumentElement().normalize();

            // extract the record elements
            NodeList nList = doc.getElementsByTagName("record");

            // iterate over the record elements and print the field values for each
            for (int i = 0; i < nList.getLength(); i++) {
                Element recordElement = (Element) nList.item(i);

                // extract the fields from the record element
                String name = recordElement.getElementsByTagName("name").item(0).getTextContent();
                String postalZip = recordElement.getElementsByTagName("postalZip").item(0).getTextContent();
                String region = recordElement.getElementsByTagName("region").item(0).getTextContent();
                String country = recordElement.getElementsByTagName("country").item(0).getTextContent();
                String address = recordElement.getElementsByTagName("address").item(0).getTextContent();
                String list = recordElement.getElementsByTagName("list").item(0).getTextContent();

                // print the field values to the console
                System.out.println("Record " + (i+1) + ":");
                System.out.println("Name: " + name);
                System.out.println("Postal Zip: " + postalZip);
                System.out.println("Region: " + region);
                System.out.println("Country: " + country);
                System.out.println("Address: " + address);
                System.out.println("List: " + list);
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

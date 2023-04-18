package tasktoo;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.Scanner;

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

            // get user input for which fields to display
            Scanner scanner = new Scanner(System.in);
            System.out.println("Which fields do you want to see? (comma-separated list, e.g. name, country)");
            String fieldString = scanner.nextLine();
            String[] fields = fieldString.split(",");

            // iterate over the record elements and print the selected fields for each
            for (int i = 0; i < nList.getLength(); i++) {
                Element recordElement = (Element) nList.item(i);
                System.out.println("Record " + (i+1) + ":");

                // extract and print the selected fields from the record element
                for (String field : fields) {
                    String fieldValue = recordElement.getElementsByTagName(field.trim()).item(0).getTextContent();
                    System.out.println(field.trim() + ": " + fieldValue);
                }
                System.out.println();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


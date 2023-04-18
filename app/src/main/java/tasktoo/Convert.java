package tasktoo;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

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
            String fieldString = "";
            String[] fields = new String[0];

            while (fields.length == 0) {
                System.out.println("Which fields do you want to see? (comma-separated list, e.g. name, country)");
                fieldString = scanner.nextLine();
                fields = fieldString.split(",");

                // check if any of the requested fields are missing from the XML file
                for (String field : fields) {
                    if (doc.getElementsByTagName(field.trim()).getLength() == 0) {
                        System.out.println("The field '" + field.trim() + "' does not exist in the XML file.");
                        fields = new String[0];
                        break;
                    }
                }
            }

            // create a new JSON array to store the output
            JSONObject outputJson = new JSONObject();

            // iterate over the record elements and create JSON objects for the selected fields
            for (int i = 0; i < nList.getLength(); i++) {
                Element recordElement = (Element) nList.item(i);
                JSONObject recordObject = new JSONObject();

                // extract the selected fields and add them to the JSON object
                for (String field : fields) {
                    String fieldValue = recordElement.getElementsByTagName(field.trim()).item(0).getTextContent();
                    recordObject.put(field.trim(), fieldValue);
                }

                // add the record object to the output array
                outputJson.append("records", recordObject);
            }

            // write the JSON output to a file in the resources directory
            String outputString = outputJson.toString(2);
            Path outputPath = Paths.get("src/main/resources/output.json");
            Files.write(outputPath, outputString.getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

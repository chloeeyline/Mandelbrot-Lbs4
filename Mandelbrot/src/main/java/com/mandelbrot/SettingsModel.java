package com.mandelbrot;

import javafx.scene.paint.Color;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class SettingsModel {
    public Color BackgroundColor;
    public Float XMin;
    public Float XMax;
    public Float YMin;
    public Float YMax;
    public int MaxIteration;

    public void getXML() {
        try {
            // Step 1: Create a DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Step 2: Create a new Document
            Document document = builder.newDocument();

            // Step 3: Build the XML structure
            // Create root element
            Element root = document.createElement("Data");
            document.appendChild(root);

            // Create a person element with attributes
            Element limit = document.createElement("Limit");
            root.appendChild(limit);

            // Create a person element with attributes
            Element limitX = document.createElement("X");
            limitX.setAttribute("min", XMin.toString());
            limitX.setAttribute("max", XMax.toString());
            limit.appendChild(limitX);

            // Step 4: Write the XML to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File("output_with_attributes.xml"));

            // Transform the DOM object to an XML file
            transformer.transform(domSource, streamResult);

            System.out.println("XML file with attributes created successfully!");

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }
}

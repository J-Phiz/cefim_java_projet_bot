package fr.leonie.jp.bot.xml;

import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;
import fr.leonie.jp.bot.utilisateurs.Sportif;
import fr.leonie.jp.bot.utilisateurs.Utilisateur;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;

public class ExportXML {

    public static void exportUtilisateurs(ArrayList<Utilisateur> listeUtilisateurs) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement = doc.createElementNS(null,"Utilisateurs");
            //append root element to document
            doc.appendChild(rootElement);

            //append child element to root element from listeUtilisateurs
            for(int i = 0; i < listeUtilisateurs.size(); i++) {
                rootElement.appendChild(getUser(doc, listeUtilisateurs.get(i), i));
            }

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to file
            StreamResult file = new StreamResult(new File(Constant.getXmlPath()));
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getUser(Document doc, Utilisateur utilisateur, int id) {
        Element user = doc.createElement(utilisateur.getClass().getSimpleName());

        //set id attribute
        user.setAttribute("id", Integer.toString(id));

        //create nom element
        user.appendChild(getFilledElement(doc, "name", utilisateur.getNom()));

        //create firstname element
        user.appendChild(getFilledElement(doc,"firstname", utilisateur.getPrenom()));

        //create town element
        user.appendChild(getFilledElement(doc,"town", utilisateur.getVille()));

        //create age element
        user.appendChild(getFilledElement(doc,"age", Integer.toString(utilisateur.getAge())));

        //create loisir elements
        Element loisirs = doc.createElement(utilisateur.getLoisirCategory() + "s");
        user.appendChild(loisirs);
        for(Loisir loisir : utilisateur.getListeLoisirs()) {
            loisirs.appendChild(getFilledElement(doc, loisir.getClass().getSimpleName(), loisir.getName()));
        }

        return user;
    }

    //utility method to create text node
    private static Node getFilledElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }
}

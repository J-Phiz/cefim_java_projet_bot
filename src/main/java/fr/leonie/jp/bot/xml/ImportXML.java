package fr.leonie.jp.bot.xml;

import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Sport;
import fr.leonie.jp.bot.utilisateurs.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class ImportXML {
    public static void importUtilisateurs(ArrayList<Utilisateur> listeUtilisateurs) {
        try {

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Constant.getXmlPath());

            doc.getDocumentElement().normalize();
            NodeList listeSportifs = doc.getElementsByTagName("Sportif");
            NodeList listeJoueurs = doc.getElementsByTagName("Joueur");

            for (int temp = 0; temp < listeSportifs.getLength(); temp++) {

                Node nNode = listeSportifs.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Sportif sportif = new Sportif(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("firstname").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()), eElement.getElementsByTagName("town").item(0).getTextContent());

                    NodeList listeSports = eElement.getElementsByTagName("sports");

                    for(int i = 0; i < listeSports.getLength(); i++) {
                        // trouver dans la liste des sports
                        // ajouter au sportif
                        // sportif.addSport();
                    }

                    if(eElement.getElementsByTagName("nbSeancesParSemaine").getLength() > 0) {
                        sportif.setNbSeancesParSemaine(Integer.parseInt(eElement.getElementsByTagName("nbSeancesParSemaine").item(0).getTextContent()));
                    }

                    if(eElement.getElementsByTagName("niveau").getLength() > 0) {
                        sportif.setNiveau(Integer.parseInt(eElement.getElementsByTagName("niveau").item(0).getTextContent()));
                    }

                    listeUtilisateurs.add(sportif);
                }
            }

            for (int temp = 0; temp < listeJoueurs.getLength(); temp++) {

                Node nNode = listeJoueurs.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Joueur joueur = new Joueur(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("firstname").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()), eElement.getElementsByTagName("town").item(0).getTextContent());

                    NodeList listeJeux = eElement.getElementsByTagName("jeux");

                    for(int i = 0; i < listeJeux.getLength(); i++) {
                        // trouver dans la liste des jeux
                        // ajouter au joueur
                        // joueur.addJeu();
                    }

                    listeUtilisateurs.add(joueur);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

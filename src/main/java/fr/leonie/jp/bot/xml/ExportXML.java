package fr.leonie.jp.bot.xml;

import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Jeu;
import fr.leonie.jp.bot.loisirs.Loisir;
import fr.leonie.jp.bot.loisirs.Sport;
import fr.leonie.jp.bot.utilisateurs.Joueur;
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
import java.util.Locale;

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
            StreamResult file = new StreamResult(new File(Constant.getXmlPath() + "utilisateurs.xml"));
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

        //create sdb element
        if(utilisateur.getSdb() != null && !utilisateur.getSdb().trim().isEmpty()) {
            user.appendChild(getFilledElement(doc, "sdb", utilisateur.getSdb()));
        }

        //create frequence element
        if(utilisateur.getFrequence() > 0) {
            user.appendChild(getFilledElement(doc,"frequence", Integer.toString(utilisateur.getFrequence())));
        }

        //create loisirPrefere element
        if(utilisateur.getLoisirPrefere() != null && !utilisateur.getLoisirPrefere().trim().isEmpty()) {
            user.appendChild(getFilledElement(doc,"loisirPrefere", utilisateur.getLoisirPrefere()));
        }

        //create loisir elements
        if(utilisateur.getListeLoisirs().size() > 0) {
            Element loisirs = doc.createElement(utilisateur.getLoisirCategory() + "s");
            user.appendChild(loisirs);
            for(Loisir loisir : utilisateur.getListeLoisirs()) {
                loisirs.appendChild(getFilledElement(doc, loisir.getClass().getSimpleName().toLowerCase(), loisir.getName()));
            }
        }

        //create specific elements for Sportif & Joueur
        switch(utilisateur.getClass().getSimpleName()) {
            case "Joueur" :
                Joueur joueur = (Joueur) utilisateur;
                //create periodeJeu element
                if(joueur.getPeriodeJeu() != null && !joueur.getPeriodeJeu().trim().isEmpty()) {
                    user.appendChild(getFilledElement(doc, "periodeJeu", joueur.getPeriodeJeu()));
                }
                //create moyenneNbPers element
                if(joueur.getMoyenneNbPers() > 0) {
                    user.appendChild(getFilledElement(doc, "moyenneNbPers", Integer.toString(joueur.getMoyenneNbPers())));
                }
                //create mange element
                if(joueur.getMange() != null && !joueur.getMange().trim().isEmpty()) {
                    user.appendChild(getFilledElement(doc, "mange", joueur.getMange()));
                }
                //create bois element
                if(joueur.getBois() != null && !joueur.getBois().trim().isEmpty()) {
                    user.appendChild(getFilledElement(doc, "bois", joueur.getBois()));
                }
                break;
            case "Sportif" :
                Sportif sportif = (Sportif) utilisateur;
                //create periodeSport element
                if(sportif.getPeriodeSport() != null && !sportif.getPeriodeSport().trim().isEmpty()) {
                    user.appendChild(getFilledElement(doc, "periodeSport", sportif.getPeriodeSport()));
                }
                //create cardioSport element
                if(sportif.getCardioSport() > 0) {
                    user.appendChild(getFilledElement(doc, "cardioSport", Integer.toString(sportif.getCardioSport())));
                }
                //create cardioRepos element
                if(sportif.getCardioRepos() > 0) {
                    user.appendChild(getFilledElement(doc, "cardioRepos", Integer.toString(sportif.getCardioRepos())));
                }
                break;
        }

        return user;
    }

    //utility method to create text node
    private static Node getFilledElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));
        return node;
    }

    public static void exportSports(ArrayList<Sport> listeSports) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement = doc.createElementNS(null,"sports");
            //append root element to document
            doc.appendChild(rootElement);

            //append child element to root element from listeUtilisateurs
            for(int i = 0; i < listeSports.size(); i++) {
                rootElement.appendChild(getSport(doc, listeSports.get(i), i));
            }

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to file
            StreamResult file = new StreamResult(new File(Constant.getXmlPath() + "sports.xml"));
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getSport(Document doc, Sport sport, int id) {
        Element sportNode = doc.createElement("sport");

        //set id attribute
        sportNode.setAttribute("id", Integer.toString(id));

        //create nom element
        sportNode.appendChild(getFilledElement(doc, "name", sport.getName()));

        //create nbParticipants element
        sportNode.appendChild(getFilledElement(doc,"nbParticipants", Integer.toString(sport.getNbParticipants())));

        return sportNode;
    }

    public static void exportJeux(ArrayList<Jeu> listeJeux) {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;

        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            //add elements to Document
            Element rootElement = doc.createElementNS(null,"jeux");
            //append root element to document
            doc.appendChild(rootElement);

            //append child element to root element from listeUtilisateurs
            for(int i = 0; i < listeJeux.size(); i++) {
                rootElement.appendChild(getJeu(doc, listeJeux.get(i), i));
            }

            //for output to file, console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            //for pretty print
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);

            //write to file
            StreamResult file = new StreamResult(new File(Constant.getXmlPath() + "jeux.xml"));
            transformer.transform(source, file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Node getJeu(Document doc, Jeu jeu, int id) {
        Element jeuNode = doc.createElement("jeu");

        //set id attribute
        jeuNode.setAttribute("id", Integer.toString(id));

        //create nom element
        jeuNode.appendChild(getFilledElement(doc, "name", jeu.getName()));

        //create nbParticipants element
        jeuNode.appendChild(getFilledElement(doc,"nbParticipants", Integer.toString(jeu.getNbParticipants())));

        return jeuNode;
    }
}

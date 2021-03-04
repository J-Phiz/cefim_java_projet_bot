package fr.leonie.jp.bot.xml;

import fr.leonie.jp.bot.constant.Constant;
import fr.leonie.jp.bot.loisirs.Jeu;
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
    public static void importUtilisateurs(ArrayList<Utilisateur> listeUtilisateurs, ArrayList<Sport> listeSports, ArrayList<Jeu> listeJeux) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Constant.getXmlPath() + "utilisateurs.xml");

            doc.getDocumentElement().normalize();
            NodeList listeSportifs = doc.getElementsByTagName("Sportif");
            NodeList listeJoueurs = doc.getElementsByTagName("Joueur");

            for (int temp = 0; temp < listeSportifs.getLength(); temp++) {

                Node nNode = listeSportifs.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Sportif sportif = new Sportif(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("firstname").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()), eElement.getElementsByTagName("town").item(0).getTextContent());

                    if(eElement.getElementsByTagName("loisirPrefere").getLength() > 0) {
                        sportif.setLoisirPrefere(eElement.getElementsByTagName("loisirPrefere").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("sdb").getLength() > 0) {
                        sportif.setSdb(eElement.getElementsByTagName("sdb").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("frequence").getLength() > 0) {
                        sportif.setFrequence(Integer.parseInt(eElement.getElementsByTagName("frequence").item(0).getTextContent()));
                    }
                    if(eElement.getElementsByTagName("periodeSport").getLength() > 0) {
                        sportif.setPeriodeSport(eElement.getElementsByTagName("periodeSport").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("cardioSport").getLength() > 0) {
                        sportif.setCardioSport(Integer.parseInt(eElement.getElementsByTagName("cardioSport").item(0).getTextContent()));
                    }
                    if(eElement.getElementsByTagName("cardioRepos").getLength() > 0) {
                        sportif.setCardioRepos(Integer.parseInt(eElement.getElementsByTagName("cardioRepos").item(0).getTextContent()));
                    }

                    NodeList listeSportsUtilisateur = eElement.getElementsByTagName("sport");

                    for(int i = 0; i < listeSportsUtilisateur.getLength(); i++) {
                        Sport sportFinal = null;

                        // trouver dans la liste des sports
                        String nom = listeSportsUtilisateur.item(i).getTextContent();
                        for(Sport sport : listeSports) {
                            if(sport.getName().equals(nom)) {
                                sportFinal = sport;
                                break;
                            }
                        }

                        // ajouter au sportif
                        sportif.getListeLoisirs().add(sportFinal);
                    }

                    listeUtilisateurs.add(sportif);
                }
            }

            for (int temp = 0; temp < listeJoueurs.getLength(); temp++) {

                Node nNode = listeJoueurs.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Joueur joueur = new Joueur(eElement.getElementsByTagName("name").item(0).getTextContent(), eElement.getElementsByTagName("firstname").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("age").item(0).getTextContent()), eElement.getElementsByTagName("town").item(0).getTextContent());

                    if(eElement.getElementsByTagName("loisirPrefere").getLength() > 0) {
                        joueur.setLoisirPrefere(eElement.getElementsByTagName("loisirPrefere").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("sdb").getLength() > 0) {
                        joueur.setSdb(eElement.getElementsByTagName("sdb").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("frequence").getLength() > 0) {
                        joueur.setFrequence(Integer.parseInt(eElement.getElementsByTagName("frequence").item(0).getTextContent()));
                    }
                    if(eElement.getElementsByTagName("periodeJeu").getLength() > 0) {
                        joueur.setPeriodeJeu(eElement.getElementsByTagName("periodeJeu").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("moyenneNbPers").getLength() > 0) {
                        joueur.setMoyenneNbPers(Integer.parseInt(eElement.getElementsByTagName("moyenneNbPers").item(0).getTextContent()));
                    }
                    if(eElement.getElementsByTagName("mange").getLength() > 0) {
                        joueur.setMange(eElement.getElementsByTagName("mange").item(0).getTextContent());
                    }
                    if(eElement.getElementsByTagName("bois").getLength() > 0) {
                        joueur.setBois(eElement.getElementsByTagName("bois").item(0).getTextContent());
                    }

                    NodeList listeJeuxUtilisateur = eElement.getElementsByTagName("jeu");

                    for(int i = 0; i < listeJeuxUtilisateur.getLength(); i++) {
                        Jeu jeuFinal = null;

                        // trouver dans la liste des jeux
                        String nom = listeJeuxUtilisateur.item(i).getTextContent();
                        for(Jeu jeu : listeJeux) {
                            if(jeu.getName().equals(nom)) {
                                jeuFinal = jeu;
                                break;
                            }
                        }

                        // ajouter au joueur
                        joueur.getListeLoisirs().add(jeuFinal);
                    }

                    listeUtilisateurs.add(joueur);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void importSports(ArrayList<Sport> listeSports) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Constant.getXmlPath() + "sports.xml");

            doc.getDocumentElement().normalize();
            NodeList liste = doc.getElementsByTagName("sport");

            for (int temp = 0; temp < liste.getLength(); temp++) {

                Node nNode = liste.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Sport sport= new Sport(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("nbParticipants").item(0).getTextContent()));
                    listeSports.add(sport);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void importJeux(ArrayList<Jeu> listeJeux) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(Constant.getXmlPath() + "jeux.xml");

            doc.getDocumentElement().normalize();
            NodeList liste = doc.getElementsByTagName("jeu");

            for (int temp = 0; temp < liste.getLength(); temp++) {

                Node nNode = liste.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    Jeu jeu= new Jeu(eElement.getElementsByTagName("name").item(0).getTextContent(), Integer.parseInt(eElement.getElementsByTagName("nbParticipants").item(0).getTextContent()));
                    listeJeux.add(jeu);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

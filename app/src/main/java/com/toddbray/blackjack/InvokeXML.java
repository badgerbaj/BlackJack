package com.toddbray.blackjack;

import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by Brad on 1/28/2017.
 *
 */

public class InvokeXML extends MainActivity {

    private static final String XML_ROOT = "blackjack";
    private static final String XML_GAMEPLAY = "gameplay";


    public GameState readGameXML(File file) {

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            playGame = new GameState();
            if(fileExistence(file))
                //new File(this.getFilesDir(), FILE_NAME);
                dom = db.parse(file);
            else return playGame;

            Element doc = dom.getDocumentElement();

            NodeList nl;
            nl = doc.getElementsByTagName(XML_GAMEPLAY);

            for(int i = 0; i < nl.getLength(); i++)
            {
                for(int ii = 0; ii < nl.item(i).getChildNodes().getLength(); ii++)
                {
                    switch (nl.item(i).getChildNodes().item(ii).getNodeName()) {
                        case PLAYER_CASH_KEY:
                            playGame.setPlayerCash(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case PLAYER_SCORE_KEY:
                            playGame.setPlayerScore(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case DEALER_SCORE_KEY:
                            playGame.setDealerScore(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case BET_AMOUNT_KEY:
                            playGame.setBetAmount(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case PLAYER_HAND_KEY:
                            int count =0;
                            for(
                                    int iii = 0;
                                    iii < nl.item(i).getChildNodes().item(ii).getChildNodes().getLength();
                                    iii++) {
                                    if(NUMBER_KEY.equals(nl.item(i).getChildNodes().item(ii).getChildNodes().item(iii).getNodeName())) {
                                        playGame.setPlayerHandPos(count, Integer.parseInt(nl.item(i).getChildNodes()
                                                .item(ii).getChildNodes().item(iii).getTextContent()));
                                        count++;
                                    }
                            }
                            break;
                        case DEALER_HAND_KEY:
                            count =0;
                            for(
                                    int iii = 0;
                                    iii < nl.item(i).getChildNodes().item(ii).getChildNodes().getLength();
                                    iii++) {
                                    if(NUMBER_KEY.equals(nl.item(i).getChildNodes().item(ii).getChildNodes().item(iii).getNodeName())) {
                                        playGame.setDealerHandPos(count, Integer.parseInt(nl.item(i).getChildNodes()
                                                .item(ii).getChildNodes().item(iii).getTextContent()));
                                        count++;
                                    }
                            }
                            break;
                    }
                }
            }

            return playGame;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return playGame;
    }

    public Deck readDeckXML(File file) {

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            playDeck = new Deck();
            if(fileExistence(file))
                //new File(this.getFilesDir(), FILE_NAME);
                dom = db.parse(file);
            else return playDeck;

            Element doc = dom.getDocumentElement();

            NodeList nl;
            nl = doc.getElementsByTagName(XML_GAMEPLAY);

            int[] shuffle;
            shuffle = new int[CARD_COUNT];

            for(int i = 0; i < nl.getLength(); i++)
            {
                for(int ii = 0; ii < nl.item(i).getChildNodes().getLength(); ii++)
                {
                    //String val = nl.item(0).getChildNodes().item(1).getTextContent();
                    switch (nl.item(i).getChildNodes().item(ii).getNodeName()) {
                        case DECK_COUNT_KEY:
                            playDeck.setDeckCount(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            shuffle = new int[(CARD_COUNT * (playDeck.getDeckCount()))];
                            break;
                        case DECK_POSITION_KEY:
                            playDeck.setDeckPosition(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case SHUFFLE_KEY:
                            int count =0;
                            for(
                                    int iii = 0;
                                    iii < nl.item(i).getChildNodes().item(ii).getChildNodes().getLength();
                                    iii++) {
                                if(NUMBER_KEY.equals(nl.item(i).getChildNodes().item(ii).getChildNodes().item(iii).getNodeName()))
                                {
                                    shuffle[count] = Integer.parseInt(nl.item(i).getChildNodes()
                                            .item(ii).getChildNodes().item(iii).getTextContent());
                                    count++;
                                }
                            }
                            playDeck.setShuffleOrder(shuffle);
                            break;
                    }
                }
            }

            return playDeck;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return playDeck;
    }

    public void writeGameXML(GameState game, File file) {
        Document dom;
        Element newDeck;
        Element e;
        Element c;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement(XML_ROOT);

            // build gameplay element
            newDeck = dom.createElement(XML_GAMEPLAY);


            e = dom.createElement(PLAYER_CASH_KEY);
            e.appendChild(dom.createTextNode("" + game.getPlayerCash()));
            newDeck.appendChild(e);

            e = dom.createElement(PLAYER_SCORE_KEY);
            e.appendChild(dom.createTextNode("" + game.getPlayerScore()));
            newDeck.appendChild(e);

            e = dom.createElement(DEALER_SCORE_KEY);
            e.appendChild(dom.createTextNode("" + game.getDealerScore()));
            newDeck.appendChild(e);

            e = dom.createElement(BET_AMOUNT_KEY);
            e.appendChild(dom.createTextNode("" + game.getBetAmount()));
            newDeck.appendChild(e);

            e = dom.createElement(PLAYER_HAND_KEY);
            //  Add NUMBER_KEY elements per player hand entry
            for(int num : game.getPlayerHand()) {
                c = dom.createElement(NUMBER_KEY);
                c.appendChild(dom.createTextNode("" + num));
                e.appendChild(c);
            }
            newDeck.appendChild(e);

            e = dom.createElement(DEALER_HAND_KEY);
            //  Add NUMBER_KEY elements per dealer hand entry
            for(int num : game.getDealerHand()) {
                c = dom.createElement(NUMBER_KEY);
                c.appendChild(dom.createTextNode("" + num));
                e.appendChild(c);
            }
            newDeck.appendChild(e);

            rootEle.appendChild(newDeck);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public void writeDeckXML(Deck deck, File file) {
        Document dom;
        Element newDeck;
        Element e;
        Element c;

        // instance of a DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use factory to get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // create instance of DOM
            dom = db.newDocument();

            // create the root element
            Element rootEle = dom.createElement(XML_ROOT);

            // build gameplay element
            newDeck = dom.createElement(XML_GAMEPLAY);

            e = dom.createElement(DECK_COUNT_KEY);
            e.appendChild(dom.createTextNode("" + deck.getDeckCount()));
            newDeck.appendChild(e);

            e = dom.createElement(DECK_POSITION_KEY);
            e.appendChild(dom.createTextNode("" + deck.getDeckPosition()));
            newDeck.appendChild(e);


            e = dom.createElement(SHUFFLE_KEY);
            //  Add NUMBER_KEY elements per shuffled deck entry
            for(int num : deck.getShuffleOrder()) {
                c = dom.createElement(NUMBER_KEY);
                c.appendChild(dom.createTextNode("" + num));
                e.appendChild(c);
            }
            newDeck.appendChild(e);

            rootEle.appendChild(newDeck);

            dom.appendChild(rootEle);

            try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "roles.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(file)));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public boolean fileExistence(File file){
        //File file = new File(this.getFilesDir(), FILE_NAME);
        return file.exists();
    }
}
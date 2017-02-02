package com.toddbray.blackjack;

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


    public boolean readXML(File file) {

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            if(fileExistance(file))
                //new File(this.getFilesDir(), FILE_NAME);
                dom = db.parse(new File(this.getFilesDir(), FILE_NAME));
            else return false;

            Element doc = dom.getDocumentElement();

            NodeList nl;
            nl = doc.getElementsByTagName(XML_GAMEPLAY);

            int[] shuffle;
            shuffle = new int[CARD_COUNT];

            for(int i = 0; i < nl.getLength(); i++)
            {
                for(int ii = 0; ii < nl.item(i).getChildNodes().getLength(); ii++)
                {
                    switch (nl.item(i).getChildNodes().item(ii).getNodeName()) {
                        case DECK_POSITION_KEY:
                            playDeck.setDeckPosition(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
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
                            playGame.setDealerScore(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case SHUFFLE_KEY:
                            for(
                                    int iii = 0;
                                    iii < nl.item(i).getChildNodes().item(ii).getChildNodes().getLength();
                                    iii++) {
                                if((nl.item(i).getChildNodes().item(ii).getChildNodes().item(iii).getNodeName()) == DECK_COUNT_KEY) {
                                    playDeck.setDeckCount(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                                    shuffle = new int[(CARD_COUNT * (playDeck.getDeckCount()))];
                                }
                                else shuffle[iii] = Integer.parseInt(nl.item(i).getChildNodes()
                                        .item(ii).getChildNodes().item(iii).getTextContent());
                            }
                            playDeck.setShuffleOrder(shuffle);
                            break;
                    }
                }
            }

            return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }

    public void saveToXML(GameState game, Deck deck, File file) {
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
            e.appendChild(dom.createTextNode("" + deck.getDeckPosition()));
            newDeck.appendChild(e);

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

            e = dom.createElement(SHUFFLE_KEY);

            //  Build DECK_POSITION_KEY under a SHUFFLE_KEY element
            c = dom.createElement(DECK_POSITION_KEY);
            c.appendChild(dom.createTextNode("" + deck.getDeckPosition()));
            e.appendChild(c);

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

    public boolean fileExistance(File file){
        //File file = new File(this.getFilesDir(), FILE_NAME);
        return file.exists();
    }
}

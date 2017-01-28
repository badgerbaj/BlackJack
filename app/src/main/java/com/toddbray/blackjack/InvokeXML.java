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
    private static final String FILE_NAME = "play_data.xml";

    public boolean readXML() {

        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            if(fileExistance())
                //new File(this.getFilesDir(), FILE_NAME);
                dom = db.parse(new File(this.getFilesDir(), FILE_NAME));
            else return false;

            Element doc = dom.getDocumentElement();

            NodeList nl;
            nl = doc.getElementsByTagName(XML_GAMEPLAY);
            Deck thisDeck = new Deck();
            int[] shuffle = new int[CARD_COUNT];

            for(int i = 0; i < nl.getLength(); i++)
            {
                for(int ii = 0; ii < nl.item(i).getChildNodes().getLength(); ii++)
                {
                    switch (nl.item(i).getChildNodes().item(ii).getNodeName()) {
                        case SHUFFLE_KEY:
                            for(
                                    int iii = 0;
                                    iii < nl.item(i).getChildNodes().item(ii).getChildNodes().getLength();
                                    iii++) {
                                shuffle[iii] = Integer.parseInt(nl.item(i).getChildNodes()
                                        .item(ii).getChildNodes().item(iii).getTextContent());
                            }
                            thisDeck.setShuffleOrder(shuffle);
                            break;
                        case DECK_POSITION_KEY:
                            thisDeck.setDeckPosition(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
                            break;
                        case PLAYER_CASH_KEY:
                            thisDeck.setDeckPosition(Integer.parseInt(nl.item(i).getChildNodes().item(ii).getTextContent()));
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

    public void saveToXML(Deck thisDeck) {
        Document dom;
        Element newDeck;
        Element e;

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

            e = dom.createElement(DECK_POSITION_KEY);
            e.appendChild(dom.createTextNode("" + thisDeck.getDeckPosition()));
            newDeck.appendChild(e);

            e = dom.createElement(SHUFFLE_KEY);
            //e.appendChild(dom.createTextNode(thisDeck.getShuffleOrder()));
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
                tr.transform(new DOMSource(dom), new StreamResult(new FileOutputStream(new File(this.getFilesDir(), FILE_NAME))));

            } catch (TransformerException te) {
                System.out.println(te.getMessage());
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        } catch (ParserConfigurationException pce) {
            System.out.println("UsersXML: Error trying to instantiate DocumentBuilder " + pce);
        }
    }

    public boolean fileExistance(){
        File file = new File(this.getFilesDir(), FILE_NAME);
        return file.exists();
    }
}

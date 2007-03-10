
//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;
package parser;


import java.awt.Font;
import java.awt.font.TextAttribute;
import java.awt.font.TransformAttribute;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import syntaxTree.CaseFeatureSet;
import syntaxTree.GenericFeatureSet;
import syntaxTree.RepositionTree;
import syntaxTree.Sentence;
import syntaxTree.SyntacticAssociation;
import syntaxTree.SyntacticFeature;
import syntaxTree.SyntacticFeatureSet;
import syntaxTree.SyntacticStructure;
import syntaxTree.SyntaxFacade;
import syntaxTree.ThetaRoleFeatureSet;
import userInterface.UserFrame;
import userInterface.UserInternalFrame;
import enumerators.SyntacticLevel;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 20-Aug-2004
 * <br>
 * <br>
 * The utility for saving and loading XML files of Syntax Trees
 */
public class XMLParser implements SaveFile, LoadFile {

	/**
	 * 
	 * @uml.property name="mMap"
	 * @uml.associationEnd 
	 * @uml.property name="mMap" multiplicity="(0 1)" qualifier="getAttribute:java.lang.String
	 * lSA:syntaxTree.SyntacticAssociation"
	 */
	private HashMap mAssociationMap;
	private HashMap mStructureMap;
	/**
	 * 
	 * @uml.property name="mInternalFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mInternalFrame" multiplicity="(0 1)"
	 */
	private UserInternalFrame mInternalFrame;

	/**
	 * 
	 * @uml.property name="mSyntaxFacade"
	 * @uml.associationEnd 
	 * @uml.property name="mSyntaxFacade" multiplicity="(0 1)"
	 */
	private SyntaxFacade mSyntaxFacade;

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(0 1)"
	 */
	private UserFrame mUserFrame;

	/**
	 * 
	 * @uml.property name="mFactory"
	 * @uml.associationEnd 
	 * @uml.property name="mFactory" multiplicity="(0 1)"
	 */
	private DocumentBuilderFactory mFactory;

	/**
	 * 
	 * @uml.property name="mBuilder"
	 * @uml.associationEnd 
	 * @uml.property name="mBuilder" multiplicity="(0 1)"
	 */
	private DocumentBuilder mBuilder;

	/**
	 * 
	 * @uml.property name="mImpl"
	 * @uml.associationEnd 
	 * @uml.property name="mImpl" multiplicity="(0 1)"
	 */
	private DOMImplementation mImpl;

	/**
	 * 
	 * @uml.property name="mDoc"
	 * @uml.associationEnd 
	 * @uml.property name="mDoc" multiplicity="(0 1)"
	 */
	private Document mDoc;

	/**
	 * 
	 * @uml.property name="mRoot"
	 * @uml.associationEnd 
	 * @uml.property name="mRoot" multiplicity="(0 1)"
	 */
	private Element mRoot;

/**
 * implementation of the SaveFile interface.
 * <br>
 * @param pSyntaxFacade - the SyntaxFacade containing the Sentence to be saved.
 * <br>
 * <br>
 * First, build an XML document with &lt;sentence&gt;&lt;/sentence&gt;
 * <br>
 * Then add the name of the file, the path of the file, and the file version
 * (currently TreeForm)
 * <br>
 * Then add the details (saveHead), and save to a file (using an XSLT tranform)
 */
	public void saveFileToDisk(SyntaxFacade pSyntaxFacade) {
		
		mDoc = saveFile(pSyntaxFacade);
		TransformerFactory xformFactory 
		   = TransformerFactory.newInstance();  
		  Transformer idTransform = null;
		try {
			idTransform = xformFactory.newTransformer();
		} catch (TransformerConfigurationException e1) {
			e1.printStackTrace();
		}
		  Source input = new DOMSource(mDoc);
		  Result output = new StreamResult(pSyntaxFacade.getFile());
		  try {
			idTransform.transform(input, output);
		} catch (TransformerException e2) {
			e2.printStackTrace();
		}
	}
	public Document saveFile(SyntaxFacade pSyntaxFacade)
	{
		mFactory = DocumentBuilderFactory.newInstance();
		mFactory.setNamespaceAware(true);
		try {
			mBuilder = mFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		mImpl = mBuilder.getDOMImplementation();
		mDoc = mImpl.createDocument(null,"sentence",null);
		mRoot = mDoc.getDocumentElement();
		Attr lName = mDoc.createAttribute("name");
		lName.setValue(pSyntaxFacade.getName());
		mRoot.setAttributeNode(lName);
		lName = mDoc.createAttribute("file");
		lName.setValue(pSyntaxFacade.getFile().getPath());
		mRoot.setAttributeNode(lName);
		lName = mDoc.createAttribute("type");
		lName.setValue("TreeForm");
		mRoot.setAttributeNode(lName);
		for(int i = 0;i < pSyntaxFacade.getSentence().getChildren().size();i++)
		{
			saveHead((SyntacticStructure) pSyntaxFacade.getSentence().getChildren().get(0),mRoot);
		}
		return mDoc;
	}
/**
 * 
 * @param pSS SyntacticStructure
 * @param pRoot Root Element in which to add file information.
 * <br>
 * <br>
 * Saves an element called syntacticstructure,
 * <br>
 * Adds the SyntacticLevel information as an attribute
 * <br>
 * appends the AttributedString details
 * <br>
 * appends the feature sets and all their details
 * <br>
 * appends the associations and all their details
 * <br>
 * calls itself recursively on all SyntacticStructure children.
 */
	private void saveHead(SyntacticStructure pSS,Element pRoot) {
		
		Element lRoot = mDoc.createElement("syntacticstructure");
		Attr lAttr = mDoc.createAttribute("syntacticlevel");
		lAttr.setValue(pSS.getSyntacticLevel().toString());
		lRoot.setAttributeNode(lAttr);
		lAttr = mDoc.createAttribute("string");
		lAttr.setValue(Integer.toString(pSS.hashCode()));
		lRoot.setAttributeNode(lAttr);
		lRoot.appendChild(saveATDetails(pSS.getHead()));
		lRoot.appendChild(saveFeatureSet(pSS.getSyntacticFeatureSet(), pRoot));
		lRoot.appendChild(saveAssociations(pSS.getSyntacticAssociation(),pRoot));
		lRoot.appendChild(saveStartTrace(pSS.getStartTrace(),pRoot));
		lRoot.appendChild(saveEndTrace(pSS.getEndTrace(),pRoot));
		pRoot.appendChild(lRoot);
		for (int i = 0; i < pSS.getChildren().size(); i++)
		{
			saveHead((SyntacticStructure) pSS.getChildren().get(i),lRoot);
		}
	}

/**
 * 
 * @param list a linkedlist of SyntacticAssociations
 * @param pRoot The root element
 * @return all the new elements containing details of associations.
 * Create an association, add the hashcode for the association (so they can
 * be matched up by the load program)
 * <br>
 */
	private Node saveAssociations(LinkedList list, Element pRoot) {
		Element lRootSAS = mDoc.createElement("syntacticassociations");
		for (int i = 0; i < list.size(); i++)
		{
			Element lRootSA = mDoc.createElement("syntacticassociation");
			Attr lAttr = mDoc.createAttribute("string");
			lAttr.setValue(Integer.toString(list.get(i).hashCode()));
			lRootSA.setAttributeNode(lAttr);
			lRootSAS.appendChild(lRootSA);
		}
		return lRootSAS;
	}
	private Node saveStartTrace(LinkedList list, Element pRoot) {
		Element lRootSAS = mDoc.createElement("starttraces");
		for (int i = 0; i < list.size(); i++)
		{
			Element lRootSA = mDoc.createElement("starttrace");
			Attr lAttr = mDoc.createAttribute("string");
			lAttr.setValue(Integer.toString(list.get(i).hashCode()));
			lRootSA.setAttributeNode(lAttr);
			lRootSAS.appendChild(lRootSA);
		}
		return lRootSAS;
	}
	private Node saveEndTrace(LinkedList list, Element pRoot) {
		Element lRootSAS = mDoc.createElement("endtraces");
		for (int i = 0; i < list.size(); i++)
		{
			Element lRootSA = mDoc.createElement("endtrace");
			Attr lAttr = mDoc.createAttribute("string");
			lAttr.setValue(Integer.toString(list.get(i).hashCode()));
			lRootSA.setAttributeNode(lAttr);
			lRootSAS.appendChild(lRootSA);
		}
		return lRootSAS;
	}
/**
 * 
 * @param list LinkedList of featureSets
 * @param pRoot The root element
 * @return the element with all the new information.
 * <br>
 * This method creates a list of featuresets,
 * <br>
 * adds each featureset, including their type (theta, case, feature, etc. . . )
 * <br>
 * Then for each feature in the featureset, add a reference,
 * and all the text details
 * <br>
 * Then, for each association of each feature, save the hashcode for 
 * the load routine (so associations can be rebuilt)
 */
	private Element saveFeatureSet(LinkedList list, Element pRoot) {
		Element lRootSFSS = mDoc.createElement("syntacticfeaturesets");
		for (int i = 0; i < list.size(); i++)
		{
			SyntacticFeatureSet lSFS = (SyntacticFeatureSet) list.get(i);
			Element lRootSFS = mDoc.createElement("syntacticfeatureset");
			Attr lAttr = mDoc.createAttribute("featuretype");
			if (list.get(i) instanceof ThetaRoleFeatureSet)
			{
				lAttr.setValue("theta");
			}
			else if (list.get(i) instanceof CaseFeatureSet)
			{
				lAttr.setValue("case");
			}
			else if (list.get(i) instanceof GenericFeatureSet)
			{
				lAttr.setValue("feature");
			}
			lRootSFS.setAttributeNode(lAttr);
			for (int j = 0; j < lSFS.getSyntacticFeature().size(); j++)
			{
				SyntacticFeature lSF = (SyntacticFeature) lSFS.getSyntacticFeature().get(j);
				Element lRootSF = mDoc.createElement("syntacticfeature");
				lRootSF.appendChild(saveATDetails(lSF.getHead()));
				Element lRootSAS = mDoc.createElement("syntacticassociations");
				lRootSF.appendChild(lRootSAS);
				for (int k = 0; k < lSF.getSyntacticAssociation().size(); k++)
				{
					SyntacticAssociation lSA = (SyntacticAssociation) lSF.getSyntacticAssociation().get(k);
					Element lRootSA = mDoc.createElement("syntacticassociation");
					lAttr = mDoc.createAttribute("string");
					lAttr.setValue(Integer.toString(lSA.hashCode()));
					lRootSA.setAttributeNode(lAttr);
					lRootSAS.appendChild(lRootSA);
				}
				lRootSFS.appendChild(lRootSF);
			}
			lRootSFSS.appendChild(lRootSFS);
		}
		return lRootSFSS;
	}
/**
 * 
 * @param pAT AttributedString
 * @return all the text and map details for an attributedString
 * <br>
 * First, get the text out of the attributed string,
 * <br>
 * then, for each character, make a subelement with
 * all the font and transform information
 * <br>
 */
	private Element saveATDetails(AttributedString pAT)
	{
		Element lText = mDoc.createElement("text");
		String lString = "";
		AttributedCharacterIterator lIterator = pAT.getIterator();
		for (char c = lIterator.first();
			c != CharacterIterator.DONE;
			c = lIterator.next()) 
		{
			lString += c;
		}
		Attr lAttr = mDoc.createAttribute("text"); 
		lAttr.setValue(lString);
		lText.setAttributeNode(lAttr);
		int i = 0;
		for (char c = lIterator.first();
					c != CharacterIterator.DONE;
					c = lIterator.next())
		{
			Map lMap = lIterator.getAttributes();
			Set lSet = lMap.keySet();
			Iterator lSetIterator = lSet.iterator();
			Element lIElement = mDoc.createElement("N" + Integer.toString(i));
			
			while(lSetIterator.hasNext())
			{
				Object lKey = lSetIterator.next();
				Object lValue = lMap.get(lKey);
				saveAttributes(lIElement, lKey, lValue);
			}
			lText.appendChild(lIElement);
			i++;
			
		}
		return lText;
	}
/**
 * 
 * @param lIElement Base element to add stuff to
 * @param lKey the Map key
 * @param lValue the Map value
 * <br>
 * <br>
 * for each attribute, save information in a text format than can
 * then be read using a load routine and used to rebuild the
 * attribute Map for each character of each AttributedString
 */
	private void saveAttributes(Element lIElement, Object lKey, Object lValue) {
		if (lKey.equals(TextAttribute.FONT))
		{
			Font lFont = (Font)lValue;
			Attr lAttr = mDoc.createAttribute("font");
			lAttr.setValue(lFont.getFamily());
			lIElement.setAttributeNode(lAttr);
			lAttr = mDoc.createAttribute("style");
			lAttr.setValue(Integer.toString(lFont.getStyle()));
			lIElement.setAttributeNode(lAttr);
			lAttr = mDoc.createAttribute("size");
			lAttr.setValue(Integer.toString(lFont.getSize()));
			lIElement.setAttributeNode(lAttr);
			lAttr = mDoc.createAttribute("subscript");
			if(((TransformAttribute)lFont.getAttributes().get(TextAttribute.TRANSFORM)).getTransform().getTranslateY() == 1)
			{
				lAttr.setValue("sub");
			}
			else if(((TransformAttribute)lFont.getAttributes().get(TextAttribute.TRANSFORM)).getTransform().getTranslateY() == -3)
			{
				lAttr.setValue("super");
			}
			else
			{
				lAttr.setValue("normal");
			}
			lIElement.setAttributeNode(lAttr);
		}
		else if (lKey.equals(TextAttribute.UNDERLINE))
		{
			Attr lAttr = mDoc.createAttribute("underline");
			if (lValue.equals(TextAttribute.UNDERLINE_ON))
			{
				lAttr.setValue("true");
			}
			else
			{
				lAttr.setValue("false");
			}
			lIElement.setAttributeNode(lAttr);
		}
		else if (lKey.equals(TextAttribute.STRIKETHROUGH))
		{
			Attr lAttr = mDoc.createAttribute("strikethrough");
			if (lValue.equals(TextAttribute.STRIKETHROUGH_ON))
			{
				lAttr.setValue("true");
			}
			else
			{
				lAttr.setValue("false");
			}
			lIElement.setAttributeNode(lAttr);
		}
		else
		{
			System.out.println("ERROR!");
		}
	}
/**
 * @param pUserFrame The Frame that will hold the InternalFrame and Sentence to be
 * created
 * @param pFile The file containing the sentence to be loaded.
 * <br>
 * This program loads the file, and then checks to see that this XML file is a TreeForm
 * file, if it is, grab the name, file, and title information from the XML,
 * and then load all the file information to create the SyntacticStructures
 * and SyntacticFeatures.
 * <br>
 * <br>
 * SyntacticAssociations are harder, a key/value set was created containing all
 * SyntacticAssociations.  Go through this, set the heads of the associations to
 * the heads of the features, and THEN redisplay the tree so the user can see
 * the loaded file.
 */
	public void loadFileFromDisk(UserFrame pUserFrame, File pFile) 
	{
		mUserFrame = pUserFrame;
		mFactory = DocumentBuilderFactory.newInstance();
		try {
			mBuilder = mFactory.newDocumentBuilder();
			mDoc = mBuilder.parse(pFile);	
			mRoot = mDoc.getDocumentElement();
			String lString = mRoot.getAttribute("type");
			if (lString.equals("TreeForm"))
			{			
				mUserFrame.getDesktopPane().addInternalFrame();
				mInternalFrame = mUserFrame.getDesktopPane().getInternalFrame();
				loadFile(mDoc,mInternalFrame);
			}
		else
			{
				JOptionPane.showMessageDialog(null,"Not a treeform file","This is NOT a treeform file!",JOptionPane.ERROR_MESSAGE);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
public void loadFile(Document doc,UserInternalFrame userInternalFrame) {
		
		mInternalFrame = userInternalFrame;
		mUserFrame = mInternalFrame.getUserFrame();
		mFactory = DocumentBuilderFactory.newInstance();
		try {
			mBuilder = mFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mDoc = doc;
		mRoot = mDoc.getDocumentElement();		
		mSyntaxFacade = mInternalFrame.getSyntaxFacade();
		mAssociationMap = new HashMap();
		mStructureMap = new HashMap();
		loadATDetails(mRoot.getChildNodes().item(0),(RepositionTree)mSyntaxFacade.getSentence());
		mSyntaxFacade.setName(mRoot.getAttribute("name"));
		mSyntaxFacade.setFile(mRoot.getAttribute("file"));
		mInternalFrame.setTitle(mRoot.getAttribute("name"));
		Set lKey = mAssociationMap.keySet();
		Iterator lIterator = lKey.iterator();
		while (lIterator.hasNext())
		{
			SyntacticAssociation lAS = (SyntacticAssociation) mAssociationMap.get(lIterator.next());
			lAS.setHead(lAS.getSyntacticFeature().getHead());
			mInternalFrame.getContentPane().add(lAS);
			lAS.getSyntacticStructure().testXY();
		}
		mSyntaxFacade.displayTree();
	}
/**
 * 
 * @param pRoot The root node from which all the details of the AttributedString will be
 * retrieved.
 * @param pSS The RepositionTree structure that will hold the new AttributedSring
 * head generated from the nodelist
 * <br>
 * This routine differentiates betwen syntacticsructure information, and syntacticfeaturesets
 * which are loaded in a different method, and syntacticassociations which
 * are loaded from yet another method.
 */
	private void loadATDetails(Node pRoot, RepositionTree pSS) {
		
		Element lElement = (Element) pRoot;
		if(lElement != null)
		{
			if (lElement.getNodeName().equals("syntacticstructure"))
			{
				NodeList lNodeList = pRoot.getChildNodes();
				SyntacticStructure lSS = null;
				System.out.println(lElement.getAttribute("string"));
				if (lElement.getAttribute("string") != "")
				{
					if (mStructureMap.get(lElement.getAttribute("string")) == null)
					{
						lSS = new SyntacticStructure(mInternalFrame,pSS);
						mStructureMap.put(lElement.getAttribute("string"), lSS);
					}
					else
					{
						lSS = (SyntacticStructure) mStructureMap.get(lElement.getAttribute("string"));
						lSS.setSyntacticParent(pSS);
					}
				}
				else
				{
					lSS = new SyntacticStructure(mInternalFrame,pSS);
				}
				lSS.setSyntacticLevel(getSyntacticLevel(lElement.getAttribute("syntacticlevel")));
				lSS.setHead(loadHead(lElement));
				if (pSS instanceof Sentence)
				{
					lSS.setSyntacticParent(null);
				}
				if (pSS != null)
				{
					pSS.getChildren().add(lSS);
				}
				mInternalFrame.getContentPane().add(lSS);
				mInternalFrame.getContentPane().add(lSS.getSyntacticStructureLines());
				for (int i = 0; i < lNodeList.getLength(); i++)
				{
					loadATDetails(lNodeList.item(i),lSS);
				}
			}
			else if (lElement.getNodeName().equals("syntacticfeaturesets"))
			{
				loadFeatureSets(lElement, pSS);
			}
			else if (lElement.getNodeName().equals("syntacticassociations"))
			{
				loadAssociations(lElement, pSS);
			}
			else if (lElement.getNodeName().equals("starttraces"))
			{
				loadStartTraces(lElement, pSS);
			}
			else if (lElement.getNodeName().equals("endtraces"))
			{
				loadEndTraces(lElement, pSS);
			}
		}
	}
/**
 * 
 * @param lElement element containing association information
 * @param pSS the RepositionTree that will contain the associations
 * <br>
 * This routine loads each association one at a time, and adds
 * new associations to a map if they are not there and/or reads
 * already existing associations from the map (remember that
 * each association was saved with a hash integer which stored the
 * uniqueness of that association.  So the map will always be correct.)
 */
	private void loadAssociations(Element lElement, RepositionTree pSS) {
		SyntacticStructure lSS = (SyntacticStructure) pSS;
		
		NodeList lAssociations = lElement.getChildNodes();
		for (int l = 0; l < lAssociations.getLength(); l++)
		{
			Element lRootSA = (Element) lAssociations.item(l);
			SyntacticAssociation lSA = null;
			if (mAssociationMap.get(lRootSA.getAttribute("string")) != null)
			{
				lSA = (SyntacticAssociation) mAssociationMap.get(lRootSA.getAttribute("string"));
				lSS.getSyntacticAssociation().add(lSA);
				lSA.setSyntacticStructure(lSS);	
			}
			else
			{
				lSA = new SyntacticAssociation(mInternalFrame);
				lSS.getSyntacticAssociation().add(lSA);
				lSA.setSyntacticStructure(lSS);
				mAssociationMap.put(lRootSA.getAttribute("string"),lSA);
			}
		}	
	}
	
	private void loadStartTraces(Element lElement, RepositionTree pSS) {
		SyntacticStructure lSS = (SyntacticStructure) pSS;
		
		NodeList lStartTraces = lElement.getChildNodes();
		for (int l = 0; l < lStartTraces.getLength(); l++)
		{
			Element lStartTrace = (Element) lStartTraces.item(l);
			SyntacticStructure lSSTrace = null;
			//System.out.println(lStartTrace.getAttribute("string"));
			if (mStructureMap.get(lStartTrace.getAttribute("string")) != null)
			{
				lSSTrace = (SyntacticStructure) mStructureMap.get(lStartTrace.getAttribute("string"));
				lSS.getStartTrace().add(lSSTrace);
			}
			else
			{
				lSSTrace = new SyntacticStructure(mInternalFrame, null);
				lSS.getStartTrace().add(lSSTrace);
				mStructureMap.put(lStartTrace.getAttribute("string"),lSSTrace);
			}
		}	
	}
	private void loadEndTraces(Element lElement, RepositionTree pSS) {
		SyntacticStructure lSS = (SyntacticStructure) pSS;
		
		NodeList lEndTraces = lElement.getChildNodes();
		for (int l = 0; l < lEndTraces.getLength(); l++)
		{
			Element lEndTrace = (Element) lEndTraces.item(l);
			SyntacticStructure lSSTrace = null;
			//System.out.println(lEndTrace.getAttribute("string"));
			if (mStructureMap.get(lEndTrace.getAttribute("string")) != null)
			{
				lSSTrace = (SyntacticStructure) mStructureMap.get(lEndTrace.getAttribute("string"));
				lSS.getEndTrace().add(lSSTrace);
			}
			else
			{
				lSSTrace = new SyntacticStructure(mInternalFrame, null);
				lSS.getEndTrace().add(lSSTrace);
				mStructureMap.put(lEndTrace.getAttribute("string"),lSSTrace);
			}
		}	
	}
/**
 * 
 * @param lElement The element containing the feature sets
 * @param pSS the RepositionTree that will store these featuresets
 * <br>
 * <br>
 * Rebuild feature sets according to the saved information, including 
 * generating the correct subclasses, add the features to the feature collection,
 * and adding all the correct AttributedString text.
 */
	private void loadFeatureSets(Element lElement, RepositionTree pSS) {
		SyntacticStructure lSS = (SyntacticStructure) pSS;
		NodeList lFeatureSets = lElement.getChildNodes();
		for (int i = 0; i < lFeatureSets.getLength(); i++)
		{
			Element lEFS = ((Element)lFeatureSets.item(i));
			SyntacticFeatureSet lSFS = null;
			if (lEFS.getAttribute("featuretype").equals("theta"))
			{
				lSFS = new ThetaRoleFeatureSet();
			}
			else if (lEFS.getAttribute("featuretype").equals("case"))
			{
				lSFS = new CaseFeatureSet();
			}
			else if (lEFS.getAttribute("featuretype").equals("feature"))
			{
				lSFS = new GenericFeatureSet();
			}
			else
			{
				System.out.println("ERROR!  Danger Will Robinson!!");
			}
			lSFS.setSyntacticStructure(lSS);
			lSS.getSyntacticFeatureSet().add(lSFS);
			NodeList lFeatures = lEFS.getChildNodes();
			for (int j = 0; j < lFeatures.getLength(); j++)
			{
				Element lEF = (Element) lFeatures.item(j);
				SyntacticFeature lSF = new SyntacticFeature(mInternalFrame);
				lSFS.getSyntacticFeature().add(lSF);
				mInternalFrame.getContentPane().add(lSF);
				lSF.setSyntacticFeatureSet(lSFS);
				lSF.setHead(loadHead(lEF));
				NodeList lFeature = lEF.getChildNodes();
				for (int k = 0; k < lFeature.getLength(); k++)
				{
					if (((Element)lFeature.item(k)).getNodeName().equals("syntacticassociations"))
					{
						NodeList lAssociations = ((Element)lFeature.item(k)).getChildNodes();
						for (int l = 0; l < lAssociations.getLength(); l++)
						{
							Element lRootSA = (Element) lAssociations.item(l);
							SyntacticAssociation lSA = null;
							if (mAssociationMap.get(lRootSA.getAttribute("string")) != null)
							{
								lSA = (SyntacticAssociation) mAssociationMap.get(lRootSA.getAttribute("string"));
								lSF.getSyntacticAssociation().add(lSA);
								lSA.setSyntacticFeature(lSF);
							}
							else
							{
								lSA = new SyntacticAssociation(mInternalFrame);
								lSF.getSyntacticAssociation().add(lSA);
								lSA.setSyntacticFeature(lSF);
								mAssociationMap.put(lRootSA.getAttribute("string"),lSA);
							}
						}
					}
				}
			}
		}
		lSS.testXY();
	}
/**
 * 
 * @param pElement The element needed to recreate an attributedString from the text/map
 * information in the save file.
 * @return A fully formed and transformed AttributedString
 */
	private AttributedString loadHead(Element pElement) {
		Node lNode =  pElement.getChildNodes().item(0);
		Element lElement = (Element)lNode;
		String lString = lElement.getAttribute("text");
		AttributedString lAT =  new AttributedString(lString);
		NodeList lNodeList = lElement.getChildNodes();
		for(int i = 0; i < lNodeList.getLength(); i++)
		{
			lAT.addAttributes(loadAttributes(lNodeList.item(i)),i,i+1);
		}
		return lAT;
	}
/**
 * 
 * @param pNode The node containing information needed to recreate the Map
 * of attributes for the AttributedString
 * @return returns the rebuilt Map.
 */
	private Map loadAttributes(Node pNode) {
		Map lMap = new HashMap();
		Element lElement = (Element)pNode;
		String lFontName = lElement.getAttribute("font");
		String lStyle = lElement.getAttribute("style");
		String lSize = lElement.getAttribute("size");
		String lUnderline = lElement.getAttribute("underline");
		String lStrikethrough = lElement.getAttribute("strikethrough");
		String lScript = lElement.getAttribute("subscript");
		if (!lFontName.equals(""))
		{
			Font lFont = new Font(lFontName, new Integer(lStyle).intValue(),new Integer(lSize).intValue());
			AffineTransform lAT = new AffineTransform();
			if (lScript.equals("sub"))
			{
				lAT.translate(0,1);
				lAT.scale(2d/3d,2d/3d);
				lFont = lFont.deriveFont(lAT);
			}
			else if (lScript.equals("super"))
			{
				lAT.translate(0,-3);
				lAT.scale(2d/3d,2d/3d);
				lFont = lFont.deriveFont(lAT);
			}
			
			lMap.put(TextAttribute.FONT,lFont);
		}
		if(lUnderline.equals("true"))
		{
			lMap.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
		}
		if(lStrikethrough.equals("true"))
		{
			lMap.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		}
		return lMap;
	}
/**
 * 
 * @param string The syntactic level string
 * @return the correct syntacticLevel object.
 */
	private SyntacticLevel getSyntacticLevel(String string) {
		if (string.equals("DOUBLE_BAR"))
		{
			return SyntacticLevel.DOUBLE_BAR;
		}
		else if (string.equals("BAR"))
		{
			return SyntacticLevel.BAR;
		}
		else if (string.equals("HEAD"))
		{
			return SyntacticLevel.HEAD;
		}
		else if (string.equals("MORPH"))
		{
			return SyntacticLevel.MORPH;
		}
		else if (string.equals("NULL"))
		{
			return SyntacticLevel.NULL;
		}
		else if (string.equals("TRIANGLE"))
		{
			return SyntacticLevel.TRIANGLE;
		}
		else
		{
			return null;
		}
	}
}
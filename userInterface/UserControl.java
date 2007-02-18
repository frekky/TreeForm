
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

package userInterface;

import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.print.DocPrintJob;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import parser.XMLParser;
import staticFunctions.Sizer;
import syntaxTree.SyntacticStructure;
import syntaxTree.SyntaxFacade;
import enumerators.ExportPictureType;
import enumerators.SaveFileType;
import enumerators.SyntacticViewLayout;


//import java.io.Writer;
//import java.io.OutputStreamWriter;
//import java.io.IOException;
//import org.apache.batik.svggen.SVGGraphics2D;
//import org.apache.batik.svggen.SVGGraphics2DIOException;
//import org.apache.batik.dom.GenericDOMImplementation;
//import org.w3c.dom.Document;
//import org.w3c.dom.DOMImplementation;


/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * The main class that controls all of the UserInterface Commands.  A big
 * storehouse of commands.
 * 
 */
public class UserControl{

	/**
	 * 
	 * @uml.property name="mUserFrame"
	 * @uml.associationEnd 
	 * @uml.property name="mUserFrame" multiplicity="(1 1)" inverse="mUserControl:userInterface.UserFrame"
	 */
	private UserFrame mUserFrame;

/**
 * 
 * @param pUserFrame The UserFrame for this instance of TreeForm
 */
	public UserControl(UserFrame pUserFrame) {
		super();
		mUserFrame = pUserFrame;
	}
/**
 * Loads a sentence from an XML file.  This method opens the JFileChooser.
 * If the user selects a valid file and approves the choice, the XMLParser.loaFile
 * command is called.
 *
 */

public void loadTree() {
		
		FileDialog fileDialog = new FileDialog(mUserFrame,"Load Syntax Tree");
		FileFilterXML fileFilterXML = new FileFilterXML();
		fileDialog.setFilenameFilter(fileFilterXML);
		fileDialog.setVisible(true);
		
		if(fileDialog.getFile() != null)
		{
			XMLParser lXMLP = new XMLParser();
			lXMLP.loadFileFromDisk(mUserFrame, new File(fileDialog.getDirectory() + fileDialog.getFile()));
			mUserFrame.getObservableNew().setValue(mUserFrame.getObservableNew().getValue()+1);
		}
	}
/**
 * Prints the tree.  First this method sets the background to white,
 * then sends the selected InternalFrame to the PrintUtilities object.
 *
 */
		public void printTree() {
			Container lC = mUserFrame.getInternalFrame().getContentPane();
			Color lColor = lC.getBackground();
			lC.setBackground(new Color(255,255,255));
			if(mUserFrame.getObservableClipboard().getValue() != null)
			{
			mUserFrame.getObservableClipboard().getValue().setCarat(false);
			mUserFrame.getObservableClipboard().getValue().repaint();
			}
			PrintUtilities.printComponent(lC);
			lC.setBackground(lColor);
			if(mUserFrame.getObservableClipboard().getValue() != null)
			{
			mUserFrame.getObservableClipboard().getValue().setCarat(true);
			mUserFrame.getObservableClipboard().getValue().repaint();
			}
		}
/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * A class designed to watch and wait for the print job to complete.
 *
 */
	protected class PrintJobWatcher {
			// true iff it is safe to close the print job's input stream
			private boolean done = false;
 /**
  * Constructor
  * @param job The print job
  */   
			public PrintJobWatcher(DocPrintJob job) {
				// Add a listener to the print job
				job.addPrintJobListener(new PrintJobAdapter() {
					public void printJobCanceled(PrintJobEvent pje) {
						allDone();
					}
					public void printJobCompleted(PrintJobEvent pje) {
						allDone();
					}
					public void printJobFailed(PrintJobEvent pje) {
						allDone();
					}
					public void printJobNoMoreEvents(PrintJobEvent pje) {
						allDone();
					}
					void allDone() {
						synchronized (PrintJobWatcher.this) {
							done = true;
							PrintJobWatcher.this.notify();
						}
					}
				});
			}
/**
 * Wait until the print job is done.
 *
 */

			public synchronized void waitForDone() {
				try {
					while (!done) {
						wait();
					}
				} catch (InterruptedException e) {
				}
			}
		}

/**
 * 
 * @param pSyntaxFacade
 * Note: source for ExampleFileFilter can be found in FileChooserDemo,
 * under the demo/jfc directory in the Java 2 SDK, Standard Edition.
 * 
 * This method opens the file chooser, and asks the user to select the export
 * type and type in a file name.  If the file name is valid, the project is
 * exported.
 * 
 **/		
		public void exportPicture(SyntaxFacade pSyntaxFacade) {
		
			JFileChooser lChooser = new JFileChooser();
 
			lChooser.setDialogTitle("Export Picture");
			lChooser.setAcceptAllFileFilterUsed(false);
			lChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			lChooser.setFileFilter(new FileFilterJPG300());
			lChooser.addChoosableFileFilter(new FileFilterJPG600());
			lChooser.addChoosableFileFilter(new FileFilterPNG300());
			lChooser.addChoosableFileFilter(new FileFilterPNG600());
			lChooser.setSelectedFile(pSyntaxFacade.getPicture());
			int returnVal = lChooser.showSaveDialog(mUserFrame);
			if(returnVal == JFileChooser.APPROVE_OPTION) {
				 ExportPictureType lExportPictureType = null;
				 File lFile = lChooser.getSelectedFile();
				 if (lChooser.getFileFilter() instanceof FileFilterJPG300)
				 {
					lExportPictureType = ExportPictureType.JPG300;
					lFile = checkExtension(lFile,".jpg");
				 }
				else if (lChooser.getFileFilter() instanceof FileFilterJPG600)
				 {
					lExportPictureType = ExportPictureType.JPG600;
					lFile = checkExtension(lFile,".jpg");
				 }
				else if (lChooser.getFileFilter() instanceof FileFilterPNG300)
				 {
					lExportPictureType = ExportPictureType.PNG300;
					lFile = checkExtension(lFile,".png");
				 }
				else if (lChooser.getFileFilter() instanceof FileFilterPNG600)
				 {
					lExportPictureType = ExportPictureType.PNG600;
					lFile = checkExtension(lFile,".png");
				 }
				else
				{
				}
				 pSyntaxFacade.setPicture(lFile.getPath());
				 exportTree(pSyntaxFacade, lFile, lExportPictureType);
			  }		
		}
/**
 * 
 * @param pSyntaxFacade The SyntaxFacade for the selected InternalFrame
 * @param lFile The selected file
 * @param lExportPictureType The export picture type selected from the JFileChooser
 * menu.
 * <br>
 * <br>
 * This command sets the background color to white (for exporting)
 * The command then sets the export picture size based on the formula
 * chosen DPI (300 or 600) / 72 DPI (Standard Screen Resolution) / Sizer.width & Length
 * (Based on the chosen screen resolution).  Exported pictures are scaled to fit
 * the new component size.
 */
		private void exportTree(SyntaxFacade pSyntaxFacade, File pFile, ExportPictureType pEPT) {
			Container lC = mUserFrame.getDesktopPane().getInternalFrame().getContentPane();
			Color lColor = lC.getBackground();
			lC.setBackground(new Color(255,255,255));
			JPanel lPanel = (JPanel) lC;
			int lDetail;
			if (pEPT == ExportPictureType.JPG600  || pEPT == ExportPictureType.PNG600)
			{
				lDetail = 600;
			}
			else
			{
				lDetail = 300;
			}
			try
			{
				BufferedImage lImg = new				
				BufferedImage(new Float(lPanel.getWidth() * (lDetail/72/ Sizer.scaleWidth())).intValue(),new Float(lPanel.getHeight() * (lDetail/72/Sizer.scaleHeight())).intValue(), BufferedImage.TYPE_INT_RGB);
				Graphics lGraphics= lImg.getGraphics();		
				Graphics2D lG2D = ((Graphics2D)lGraphics);
				AffineTransform lAT = new AffineTransform();
				lAT.setToScale(lDetail/72/Sizer.scaleWidth(),lDetail/72/Sizer.scaleHeight());
				lG2D.setTransform(lAT);
				lPanel.print(lG2D);
				OutputStream lOut;
				if (pEPT == ExportPictureType.JPG300 || pEPT == ExportPictureType.JPG600)
				{
					try {
						lOut = new FileOutputStream(pFile);
						Iterator writers = ImageIO.getImageWritersBySuffix("jpeg");	
						ImageWriter writer = (ImageWriter) writers.next();
						ImageOutputStream ios = ImageIO.createImageOutputStream(lOut);
						writer.setOutput(ios);
						ImageWriteParam param = writer.getDefaultWriteParam();
						param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
						param.setCompressionQuality(1.0F);
						writer.write(null, new IIOImage(lImg, null, null), param);
						ios.close();
						writer.dispose();
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
				else if (pEPT == ExportPictureType.PNG300 || pEPT == ExportPictureType.PNG600)
				{
					try {
						ImageIO.write(lImg, "png", pFile);
					} catch (IOException e) {
						e.printStackTrace();
					}		
				}
				else
				{
				 JOptionPane.showMessageDialog(null,"You have not chosen a valid file type","I have no idea how you got here!",JOptionPane.ERROR_MESSAGE);
				}
			}
			catch (OutOfMemoryError pOME)
			{
				JOptionPane.showMessageDialog(null,"You have not allocated enough memory to Java to export this picture","Run this program using the batch script instead",JOptionPane.ERROR_MESSAGE);
			}
			lC.setBackground(lColor);

			
		}
/**
 * Open the help screen
 *
 */

		public void openHelp() {
			System.out.println("Open About");
			HelpFrame lHelpFrame = new HelpFrame();
			lHelpFrame.validate();
		}

		
/**
 * 
 * @param pSyntaxFacade The syntaxFacade for the selected InternalFrame
 * @param pFile The Selected save file
 * @param pSFT The selected save file type.
 * <br>
 * <br>
 * This method calls the correct parser based on the save file type, and passes
 * that parser the SyntaxFacade needed to save files.
 */ 
		public void saveTree(SyntaxFacade pSyntaxFacade, File pFile,SaveFileType pSFT) {
			if (pSyntaxFacade.getName() == "")
			{
				saveAsTree(pSyntaxFacade);
			}
			else
			{
				if (pSFT == SaveFileType.XML)
				{
					XMLParser lXML = new XMLParser();
					lXML.saveFileToDisk(pSyntaxFacade);
				}
				if (pSFT == SaveFileType.SVG)
				{
//get rid.
				}
			}
		}

/**
 * 
 * @param pSyntaxFacade The SyntaxFacade for the selected InternalFrame
 * <br>
 * 	Note: source for ExampleFileFilter can be found in FileChooserDemo,
 * under the demo/jfc directory in the Java 2 SDK, Standard Edition.
 * <br>
 * This method calls the XMLParser object and saves a copy of the tree.
 **/
	public void saveAsTree(SyntaxFacade pSyntaxFacade) {
			
			FileDialog fileDialog = new FileDialog(mUserFrame,"Save Syntax Tree");
			FileFilterXML fileFilterXML = new FileFilterXML();
			fileDialog.setFilenameFilter(fileFilterXML);
			//FileFilterSVG fileFilterSVG = new FileFilterSVG();
			//fileDialog.setFilenameFilter(fileFilterSVG);
			fileDialog.setMode(FileDialog.SAVE);
			fileDialog.setFile(pSyntaxFacade.getFile().getName());
			fileDialog.setVisible(true);
			
			if(fileDialog.getFile() != null)
			{
			//	XMLParser lXMLP = new XMLParser();
			//	lXMLP.loadFile(mUserFrame, new File(fileDialog.getFile()));
			//	mUserFrame.getObservableNew().setValue(mUserFrame.getObservableNew().getValue()+1);
				 pSyntaxFacade.setName(fileDialog.getFile());
				 SaveFileType lSaveFileType = SaveFileType.XML;
			 	 File lFile = new File(fileDialog.getDirectory() + fileDialog.getFile());
			 	 //System.out.println("XML = "+fileDialog.getDirectory() + fileDialog.getFile());
				 lFile = checkExtension(lFile,".xml");
				 pSyntaxFacade.setFile(lFile.getPath());
				 pSyntaxFacade.setName(lFile.getName());
				 pSyntaxFacade.getUIF().setTitle(lFile.getName());
				 saveTree(pSyntaxFacade, lFile, lSaveFileType);
			}
		}

/**
 * 
 * @param lFile The passed in file Object
 * @param pExtension The extension required.
 * @return a File Object with the correct extension
 * <br>
 * PRE: a valid file Object
 * POST: a corrected file Object
 */
		private File checkExtension(File lFile, String pExtension) {
			if (lFile.getName().endsWith(pExtension))
			{
				// do nothing
			}
			else
			{
				if (lFile.getName().indexOf(".") == -1)
				{
					lFile = new File(lFile.getPath() + pExtension);
				}
				else
				{
					lFile = new File(lFile.getPath().substring(0,lFile.getPath().indexOf(".")) + pExtension);
				}
			}
			return lFile;
		}
/**
 * Needs implementation
 *
 */
		public void addDemographicInformation() {
			// TODO: I have to add something here, structure not known yet.
		}
/**
 * 
 *
 */
		public void editPresenation() {
			JOptionPane.showMessageDialog(null,"Not Impemented","This code has not been written yet.",JOptionPane.INFORMATION_MESSAGE);
		}

/**
 * Creates a new tree by adding a new InternalFrame.
 *
 */
		public void createNewTree()
		{
			mUserFrame.getDesktopPane().addInternalFrame();
		}

		public void changeViewLayout(SyntacticViewLayout pSyntacticViewLayout) {
			JOptionPane.showMessageDialog(null,"Not Impemented","This code has not been written yet.",JOptionPane.INFORMATION_MESSAGE);
			System.out.println(pSyntacticViewLayout);
		}

/**
 * Closes Trees without saving
 *
 */
		public void closeTree() {
			int lI = JOptionPane.showConfirmDialog(null,"Clicking NO will close trees without saving them.","Save trees first?",JOptionPane.YES_NO_OPTION);
			if (lI == JOptionPane.YES_OPTION)
			{
				saveAsTree(mUserFrame.getSyntaxFacade());
			}
			else
			{
			mUserFrame.getDesktopPane().closeInternalFrame();
			}
		}
/**
 * Closes all Trees without saving
 */
		public void closeAllTrees() {
			int lI = JOptionPane.showConfirmDialog(null,"Clicking NO will close ALL trees without saving them.","Save trees first?",JOptionPane.YES_NO_OPTION);
			if (lI == JOptionPane.YES_OPTION)
			{
				saveAllTrees(mUserFrame.getDesktopPane());
			}
			else
			{
			mUserFrame.getDesktopPane().closeAllInternalFrames();
			}
		}
/**
 * 
 * @param pUdp Saves all the trees
 */
		public void saveAllTrees(UserDesktopPane pUdp) {
			JInternalFrame[] internalPanes = pUdp.getAllFrames();
			for(int i = 0; i < internalPanes.length; i++)
			{
				saveTree(((UserInternalFrame)internalPanes[i]).getSyntaxFacade(),((UserInternalFrame)internalPanes[i]).getSyntaxFacade().getFile(), ((UserInternalFrame)internalPanes[i]).getSaveFileType());
			}
		}
/**
 * Redo
 */
		public void redo() {

			mUserFrame.getDesktopPane().getInternalFrame().getSyntaxFacade().redo();
		}
/**
 * Undo
 */
		public void undo() {
			mUserFrame.getDesktopPane().getInternalFrame().getSyntaxFacade().undo();
		}
/**
 * Copies selected text to clipboard
 */
		public void copy() {
			setClipboard(mUserFrame.getObservableClipboard().getValue().getClip());
		}
/**
 * copies selected text to clipboard (doesn't cut!)
 */
		public void cut() {
			setClipboard(mUserFrame.getObservableClipboard().getValue().getClip());
		}
/**
 * pastes text from the clipboard, UNICODE from external sources, AttributedStrings
 * from TreeForm
 */
		public void paste() {
			Object lObject = getClipboard();
			AttributedString lAT = null;
			if (lObject instanceof String)
			{
				lAT = new AttributedString((String)lObject);
				lAT.addAttributes(getAttributes(),0,((String)lObject).length());
			}
			else
			{
				lAT = (AttributedString) lObject;
			}
			int lLength = 0;
			AttributedCharacterIterator lIterator = lAT.getIterator();
			if (lIterator.first() != AttributedCharacterIterator.DONE)
			{
				lLength = 1;
			}
			while (lIterator.next() != AttributedCharacterIterator.DONE)
			{
				lLength += 1;
			}
			mUserFrame.getObservableClipboard().getValue().deleteHead();
			mUserFrame.getObservableClipboard().getValue().insertHead(lAT,mUserFrame.getObservableClipboard().getIndex());
			mUserFrame.getObservableClipboard().getValue().setHighlightEnd(((SyntacticStructure)mUserFrame.getObservableClipboard().getValue()).getInsertionIndex() + lLength);
			mUserFrame.getObservableClipboard().getValue().setHighlightBegin(((SyntacticStructure)mUserFrame.getObservableClipboard().getValue()).getInsertionIndex() + lLength);
			mUserFrame.getObservableClipboard().getValue().setInsertionIndex(((SyntacticStructure)mUserFrame.getObservableClipboard().getValue()).getInsertionIndex() + lLength);
			mUserFrame.getObservableClipboard().getValue().setCarat(true);
			mUserFrame.getDesktopPane().getInternalFrame().getSyntaxFacade().displayTree();
		}
/**
 * 
 * @return Returns a Map of all the attributes at the given cursor location of the
 * selected tree structure, feature, or association.
 * <br>
 * <br>
 * Note the hokey implementation of Subscript and Superscript, thanks to a persistant
 * Java bug.
 */
		public Map getAttributes() {
			Map lMap = new HashMap();
			int lStyle = 0;
			if(mUserFrame.getObservableFontBold().getValue())
			{
				lStyle += Font.BOLD;
			}
			if(mUserFrame.getObservableFontItalic().getValue())
			{
				lStyle += Font.ITALIC;
			}
			Font lFont = new Font(mUserFrame.getObservableFont().getValue(),lStyle,mUserFrame.getObservableFontSize().getValue());
			if(mUserFrame.getObservableSubscript().getValue())
			{
				AffineTransform lAT = new AffineTransform();
				lAT.translate(0,1);
				lAT.scale(2d/3d,2d/3d);
				lFont = lFont.deriveFont(lAT);
			}
			if(mUserFrame.getObservableSuperscript().getValue())
			{
				AffineTransform lAT = new AffineTransform();
				lAT.translate(0,-3);
				lAT.scale(2d/3d,2d/3d);
				lFont = lFont.deriveFont(lAT);
			}
			lMap.put(TextAttribute.FONT, lFont);
			if(mUserFrame.getObservableFontUnderline().getValue())
			{
				lMap.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE_ON);
			}
			else
			{
				lMap.put(TextAttribute.UNDERLINE,TextAttribute.UNDERLINE);
			}
			return lMap;
		}
/**
 * 
 * @param lAS The string passed to the clipboard
 */
		public static void setClipboard(AttributedString lAS)
		{
			UserTransferable t = new UserTransferable(lAS);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(t,null);
		}
		public static Object getClipboard() {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		String text = "";
		if (t != null && t.isDataFlavorSupported(new DataFlavor(AttributedString.class,"Attributed String")))
		{
			try {
				return t.getTransferData(new DataFlavor(AttributedString.class, "Attributed String"));
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				try {
					text = (String)t.getTransferData(DataFlavor.stringFlavor);
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return text;
	}		

/**
 * 
 *
 */
		public void selectAll() {
			JOptionPane.showMessageDialog(null,"Not Impemented","This code has not been written yet.",JOptionPane.INFORMATION_MESSAGE);			
			System.out.println("Select All Objects");
		}
/**
 * 
 */
		public void openAbout() {
			System.out.println("Open About");
			AboutFrame lAboutFrame = new AboutFrame();
			lAboutFrame.validate();
		}

/**
 * 
 *
 */
		public void printPreview() {
			JOptionPane.showMessageDialog(null,"Not Impemented","This code has not been written yet.",JOptionPane.INFORMATION_MESSAGE);
		}

/**
 * 
 * @param pZoom
 */		
		public void zoom(float pZoom) {
			UserInternalFrame lUIF = mUserFrame.getDesktopPane().getInternalFrame();
			lUIF.setScale(pZoom);
//			lUIF.getSyntaxFacade().displayTree(lUIF.getSyntaxFacade().getSentence());
			lUIF.getSyntaxFacade().displayTree();
			//lUIF.getSyntaxFacade().setUIFBounds();
		}
/**
 * Selects a new frame.
 */
		public void selectNewFrame() {
			mUserFrame.getDesktopPane().selecteNewFrame();
			
		}
/**
 * Delete selected items.
 */
	public void delete() {
		JOptionPane.showMessageDialog(null,"Not Impemented","This code has not been written yet.",JOptionPane.INFORMATION_MESSAGE);
	}	
}


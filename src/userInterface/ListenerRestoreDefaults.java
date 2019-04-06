package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerRestoreDefaults implements ActionListener {

	private Properties mProperties;
	private SyntaxFacade mSyntaxFacade;
	private PropertiesFrame mFrame;

	public ListenerRestoreDefaults(Properties properties, SyntaxFacade syntaxFacade,PropertiesFrame frame) {
		mProperties = properties;
		mSyntaxFacade = syntaxFacade;
		mFrame = frame;
		
	}

	public void actionPerformed(ActionEvent arg0) {
		mProperties.restoreDefaults();
		mSyntaxFacade.displayTree();
		mSyntaxFacade.displayTree();
		mFrame.setMinTextWidth(mProperties.getMinLineWidth());
		mFrame.setLineLength(mProperties.getMinLineLength());
		mFrame.setLeftTranslate(mProperties.getLeftTranslate());
		mFrame.setTopTranslate(mProperties.getTopTranslate());
		mFrame.setFontSize(mProperties.getDefaultFontSize());
		mFrame.setFontColor(mProperties.getFontColor());
		mFrame.setBackgroundColor(mProperties.getBackgroundColor());
		mFrame.setLineColor(mProperties.getLineColor());
	}

}

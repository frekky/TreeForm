package userInterface;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerDefaultFontSize implements ChangeListener {

	private Properties mProperties;
	private SyntaxFacade mSyntaxFacade;

	public ListenerDefaultFontSize(Properties properties, SyntaxFacade syntaxFacade) {
		mProperties = properties;
		mSyntaxFacade = syntaxFacade;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setDefaultFontSize(((JSlider)e.getSource()).getValue());
		mSyntaxFacade.displayTree();

	}

}

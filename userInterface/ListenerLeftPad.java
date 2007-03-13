package userInterface;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerLeftPad implements ChangeListener {

	private Properties mProperties;
	private SyntaxFacade mSyntaxFacade;

	public ListenerLeftPad(Properties properties, SyntaxFacade syntaxFacade) {
		mProperties = properties;
		mSyntaxFacade = syntaxFacade;
	}

	public void stateChanged(ChangeEvent e) {
		mProperties.setLeftTranslate(((JSlider)e.getSource()).getValue());
		mSyntaxFacade.displayTree();

	}

}

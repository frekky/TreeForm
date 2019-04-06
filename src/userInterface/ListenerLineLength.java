package userInterface;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerLineLength implements ChangeListener {

    private Properties mProperties;
    private SyntaxFacade mSyntaxFacade;

    public ListenerLineLength(Properties properties, SyntaxFacade syntaxFacade) {
        mProperties = properties;
        mSyntaxFacade = syntaxFacade;
    }

    public void stateChanged(ChangeEvent arg0) {
        mProperties.setLineLength(((JSlider)arg0.getSource()).getValue());
        mSyntaxFacade.displayTree();
    }

}

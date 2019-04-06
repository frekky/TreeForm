package userInterface;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerLineWidth implements ChangeListener {

    private Properties mProperties;
    private SyntaxFacade mSyntaxFacade;

    public ListenerLineWidth(Properties properties, SyntaxFacade syntaxFacade) {
        mProperties = properties;
        mSyntaxFacade = syntaxFacade;
    }

    public void stateChanged(ChangeEvent e) {
        mProperties.setMinLineWidth(((JSlider)e.getSource()).getValue());
        mSyntaxFacade.displayTree();
    }
}

package userInterface;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import syntaxTree.Properties;
import syntaxTree.SyntaxFacade;

public class ListenerTopPad implements ChangeListener {

    private Properties mProperties;
    private SyntaxFacade mSyntaxFacade;

    public ListenerTopPad(Properties properties, SyntaxFacade syntaxFacade) {
        mProperties = properties;
        mSyntaxFacade = syntaxFacade;
    }

    public void stateChanged(ChangeEvent e) {
        mProperties.setTopTranslate(((JSlider)e.getSource()).getValue());
        mSyntaxFacade.displayTree();
    }

}

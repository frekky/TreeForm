package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerDeleteEndTrace implements ActionListener {

    private SyntaxFacade mSF;
    private SyntacticStructure mSS;

    public ListenerDeleteEndTrace(SyntaxFacade pSF, SyntacticStructure pSS) {
        mSF = pSF;
        mSS = pSS;
    }

    public void actionPerformed(ActionEvent arg0) {
        mSF.deleteEndTrace(mSS,true);
        mSF.displayTree();
    }
}

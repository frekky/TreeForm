package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerCustomizeStartTrace implements ActionListener {

    private SyntaxFacade mSF;
    private SyntacticStructure mSS;

    public ListenerCustomizeStartTrace(SyntaxFacade pSF, SyntacticStructure pSS) {
        mSF = pSF;
        mSS = pSS;
    }

    public void actionPerformed(ActionEvent arg0) {
        mSF.getUIF().activateBezierPane(mSS);

    }

}

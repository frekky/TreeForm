package syntaxTree;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ListenerCustomizeEndTrace implements ActionListener {


    private SyntaxFacade mSF;
    private SyntacticStructure mSS;

    public ListenerCustomizeEndTrace(SyntaxFacade pSF, SyntacticStructure pSS) {
        mSF = pSF;
        mSS = pSS;
    }

    public void actionPerformed(ActionEvent arg0) {
        mSF.getUIF().activateBezierPane((SyntacticStructure) mSS.getEndTrace().get(0));
    }
}

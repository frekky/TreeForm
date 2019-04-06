package userInterface;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import syntaxTree.SyntacticFeature;
import syntaxTree.SyntacticStructure;

public class UserAssociationPane extends JComponent {

    private UserFrame mUserFrame;
    private SyntacticStructure mSS;
    private int mX;
    private int mY;
    private SyntacticFeature mSA;

    public UserAssociationPane(UserFrame userFrame, SyntacticFeature feature) {
        mUserFrame = userFrame;
        mSA = feature;
        mSS = feature.getSyntacticFeatureSet().getSyntacticStructure();
        mX = (int) (mSS.getButtonX() + (mSS.getButtonWidth()/2) - 5);
        mY = (int) (mSS.getButtonY() + mSS.getButtonHeight() - mUserFrame.getInternalFrame().getProperties().getMinLineLength());

    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public void setPosition(MouseEvent pme) {
        mX = (int) (pme.getX() / (Sizer.scaleWidth()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
        mY = (int) (pme.getY()/ (Sizer.scaleHeight()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale()));
        repaint();

    }

    public void setEnd(SyntacticStructure structure) {
        mUserFrame.getSyntaxFacade().associateSyntacticFeature(structure,mSA);

    }

    public void paintComponent(Graphics g) {
        Graphics2D lGraphics2D = (Graphics2D) g;
        lGraphics2D.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        lGraphics2D.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);

        lGraphics2D.scale(
            Sizer.scaleWidth()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale(),
            Sizer.scaleHeight()
            * mUserFrame.getDesktopPane().getInternalFrame().getScale());
        lGraphics2D.setColor(new Color(0,0,200));
        Rectangle2D.Double ellipse = new Rectangle2D.Double(mX, mY, 10,10);
        lGraphics2D.fill(ellipse);
        int x = (int) (mSS.getButtonX() + (mSS.getButtonWidth()/2) - 5);
        int y = (int) (mSS.getButtonY() + mSS.getButtonHeight() - mUserFrame.getInternalFrame().getProperties().getMinLineLength());

        ellipse = new Rectangle2D.Double(x,
            y, 10,10);
        lGraphics2D.fill(ellipse);
        lGraphics2D.setColor(Color.BLACK);
        float dash[] = {2.0f, 2.0f};
        lGraphics2D.setStroke(new BasicStroke(2f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f)); 			//stroke.getDashPhase();
        lGraphics2D.drawLine(mX+5, mY+5, x+5, y+5);


    }

}

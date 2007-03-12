package syntaxTree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import userInterface.UserInternalFrame;

public class TraceComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int padWidth = 6;
	private static final int circle = 4;
	static final int lineLength = 4;
	static final int padEdge = 6;
	private static final float triangleLength = 3;
	private UserInternalFrame mUserInternalFrame;
	private SyntaxFacade mSyntaxFacade;
	private boolean mDrawTrace;
	private int mStartX;
	private int mStartY;
	private int mEndY;
	private int mEndX;

	public TraceComponent(UserInternalFrame pUserInternalFrame) {
		mUserInternalFrame = pUserInternalFrame;
		mSyntaxFacade = mUserInternalFrame.getSyntaxFacade();
		mDrawTrace = true;
	}
	public void paint(Graphics G) {
		this.setBounds(getUserInternalFrame().getBounds());
		super.paint(G);
		if(mDrawTrace)
		{
		displayMovement(G);
		}
	}

	private void displayMovement(Graphics G) {
		if (getSyntaxFacade().getSentence().getChildren().size() > 0) {
			SyntacticStructure mR = (SyntacticStructure) getSyntaxFacade()
					.getSentence().getChildren().get(0);
			Graphics2D lGraphics2D = (Graphics2D) G;
			lGraphics2D.scale(Sizer.scaleWidth() * getUserInternalFrame().getScale(), Sizer
					.scaleHeight()
					* getUserInternalFrame().getScale());
			// set the font
			// Set the g2D to antialias.
			lGraphics2D.setColor(Color.BLACK);
			lGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			lGraphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,
					RenderingHints.VALUE_RENDER_QUALITY);
			lGraphics2D.setStroke(new BasicStroke(.5F));
			resetCounters(mR);
			displayMovementRecursive(mR, lGraphics2D);
		}
	}

	private void resetCounters(SyntacticStructure start) {
		start.setPadBottomCount(0);
		start.setPadLeftCount(0);
		start.setPadRightCount(0);
		start.setPadStartLeftCount(0);
		start.setPadStartRightCount(0);
		for (int i = 0; i < start.getChildren().size(); i++) {
			SyntacticStructure w = (SyntacticStructure) start.getChildren()
					.get(i);
			resetCounters(w);
		}
	}

	private void displayMovementRecursive(SyntacticStructure start,
			Graphics2D contentGraphics) {

		for (int i = 0; i < start.getStartTrace().size(); i++) {
			SyntacticStructure end = (SyntacticStructure) start
					.getStartTrace().get(i);
			drawMovement(start, end, contentGraphics);
		}
		for (int i = 0; i < start.getChildren().size(); i++) {
			SyntacticStructure w = (SyntacticStructure) start.getChildren()
					.get(i);
			displayMovementRecursive(w, contentGraphics);
		}
	}

	private void drawMovement(SyntacticStructure start,
			SyntacticStructure end, Graphics2D contentGraphics) {
		
		drawStart(start, contentGraphics);
		drawEnd(end, contentGraphics);
		drawQuadCurve(start, contentGraphics);
	}
	
	private void drawQuadCurve(SyntacticStructure start, Graphics2D contentGraphics) {
		CubicCurve2D bezier = new CubicCurve2D.Float(mStartX,mStartY,mStartX,mStartY,mEndX,mEndY,mEndX,mEndY);
		
		contentGraphics.draw(bezier);
		
	}
	private void drawStart(SyntacticStructure start,
			Graphics2D contentGraphics) {
		
			float padBottom = 0;
			
			padBottom = - (start.getPadStartLeft() * padWidth) + start.getPadStartLeftCount() * padWidth;
			
			contentGraphics.fillArc((int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom - circle/2),(int) (start
							.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().fontSize()), circle,
					circle, 0, 360);
	
			mStartX = (int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom);
			mStartY = (int) (start
					.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().lineLength() + circle);
		
//		else
//		{
//		if (left) {
//			contentGraphics.fillArc((int) start.getButtonX() - padEdge  
//					- start.getPadStartLeftCount() * padWidth, (int) start
//					.getButtonY() + (start.getTextHeight()/2) - (padLength/2)- (circle/2)
//					+ start.getPadStartLeftCount() * padLength, circle,
//					circle, 0, 360);
//
//			contentGraphics
//					.drawLine(
//							(int) start.getButtonX() - padEdge
//									- start.getPadStartLeftCount()
//									* padWidth,
//									(int) start
//									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//									+ start.getPadStartLeftCount() * padLength,
//							(int) start.getButtonX()
//									- padEdge - (start.getPadStartLeftCount() * padWidth)
//									- lineLength,
//									(int) start
//									.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//									+ start.getPadStartLeftCount() * padLength);
//			//testWidthStart(start, left,false);
//			mStartX = (int) start.getButtonX() - padEdge
//					- (start.getPadStartLeftCount() * padWidth) - lineLength;
//			mStartY = (int) start
//			.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//			+ start.getPadStartLeftCount() * padLength;
//			
//		} else {
//			contentGraphics.fillArc((int) start.getButtonX() + padEdge
//					+ start.getButtonWidth() + start.getPadStartRightCount()
//					* padWidth - lineLength,
//					(int) start.getButtonY() + (start.getTextHeight()/2) - (circle/2) - (padLength/2)
//					+ start.getPadStartRightCount() * padLength,
//					circle,
//					circle, 0, 360);
//
//			contentGraphics
//					.drawLine(
//							(int) start.getButtonX() + padEdge
//									+ start.getButtonWidth()
//									+ (start.getPadStartRightCount() * padWidth)
//									,
//									(int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//									+ start.getPadStartRightCount() * padLength,
//							(int) start.getButtonX() + padEdge
//									+ start.getButtonWidth()
//									+ (start.getPadStartRightCount() * padWidth)
//									+ lineLength, (int) start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//									+ start.getPadStartRightCount() * padLength);
//			//testWidthStart(start, left,false);
//			mStartX = (int) start.getButtonX() + padEdge
//					+ start.getButtonWidth()
//					+ (start.getPadStartRightCount() * padWidth) + lineLength;
//			mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
//			+ start.getPadStartRightCount() * padLength);
//			
//		}
//		}
//		if (left)
//		{
//			start.setPadStartLeftCount(start.getPadStartLeftCount() + 1);
//		}
//		else
//		{
//			start.setPadStartRightCount(start.getPadStartRightCount() + 1);
//		}
	}




	private void drawEnd(SyntacticStructure end,
			Graphics2D contentGraphics) {
		
			float padBottom = 0;
			
				padBottom = - (end.getPadStartLeft() * padWidth) + end.getPadStartLeftCount() * padWidth;
			
			GeneralPath polly = new GeneralPath();
			// move the pollygon to the middle and bottom
			polly.moveTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().lineLength());
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) - (triangleLength),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().lineLength() + triangleLength);
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) + (triangleLength),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().lineLength() + triangleLength);
			polly.closePath();
			contentGraphics.fill(polly);

			mEndX = (int)(end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom);
			mEndY = (int) (end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().lineLength() + triangleLength);
			
//		else
//		{
//		if (left) {
//			
//			GeneralPath polly = new GeneralPath();
//			polly.moveTo((float) end.getButtonX() - padEdge
//					- end.getPadStartLeftCount() * padWidth + triangleLength,
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartLeftCount() * padLength
//							+ 0.25f);
//			polly.lineTo((float) end.getButtonX() - padEdge
//					- end.getPadStartLeftCount() * padWidth,
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartLeftCount() * padLength
//							+ 3.25f);
//			polly.lineTo((float) end.getButtonX() - padEdge
//					- end.getPadStartLeftCount() * padWidth,
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartLeftCount() * padLength
//							- 2.75f);
//			polly.closePath();
//			contentGraphics.fill(polly);
//			
//			contentGraphics.drawLine((int) end.getButtonX() - padEdge
//					- (end.getPadStartLeftCount() * padWidth) - lineLength , (int) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartLeftCount() * padLength, (int) end
//					.getButtonX() - padEdge
//					- (end.getPadStartLeftCount() * padWidth),
//					 (int) end
//						.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//						+ end.getPadStartLeftCount() * padLength);
//			//testWidthEnd(end, left,false);
//			mEndX = (int) end.getButtonX() - padEdge
//					- (end.getPadStartLeftCount() * padWidth) - lineLength;
//			mEndY = (int) end
//			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//			+ end.getPadStartLeftCount() * padLength;
//		} else {
//			
//			GeneralPath polly = new GeneralPath();
//			polly.moveTo((float) end.getButtonX() + padEdge
//					+ end.getButtonWidth()
//					+ (end.getPadStartRightCount() * padWidth) - triangleLength,
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartRightCount() * padLength
//							+ 0.25f);
//			polly.lineTo((float) end.getButtonX() + padEdge
//					+ end.getButtonWidth()
//					+ (end.getPadStartRightCount() * padWidth),
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartRightCount() * padLength
//							+ 3.25f);
//			polly.lineTo((float) end.getButtonX() + padEdge
//					+ end.getButtonWidth()
//					+ (end.getPadStartRightCount() * padWidth),
//					(float) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartRightCount() * padLength
//							- 2.75f);
//			polly.closePath();
//			contentGraphics.fill(polly);
//			
//			contentGraphics.drawLine((int) end.getButtonX() + padEdge
//					+ end.getButtonWidth()
//					+ (end.getPadStartRightCount() * padWidth) ,
//					(int) end
//					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//					+ end.getPadStartRightCount() * padLength, (int) end.getButtonX() + padEdge
//							+ end.getButtonWidth()
//							+ (end.getPadStartRightCount() * padWidth) + lineLength,
//							(int) end
//							.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//							+ end.getPadStartRightCount() * padLength);
//			//testWidthEnd(end, left, false);
//			mEndX = (int) end.getButtonX() + padEdge + end.getButtonWidth()
//					+ (end.getPadStartRightCount() * padWidth) + lineLength;
//			mEndY = (int) end
//			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
//			+ end.getPadStartRightCount() * padLength;
//		}
//		}
//		if (left)
//		{
//			end.setPadStartLeftCount(end.getPadStartLeftCount() + 1);
//		}
//		else
//		{
//			end.setPadStartRightCount(end.getPadStartRightCount() + 1);
//		}
	}

	private SyntaxFacade getSyntaxFacade()
	{
		return mSyntaxFacade;
	}
	private UserInternalFrame getUserInternalFrame()
	{
		return mUserInternalFrame;
	}
	public void setDrawTrace(boolean drawTrace)
	{
		mDrawTrace = drawTrace;
	}
	public boolean getDrawTrace()
	{
		return mDrawTrace;
	}
	
}


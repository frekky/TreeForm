package syntaxTree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.GeneralPath;
import java.util.LinkedList;

import javax.swing.JComponent;

import staticFunctions.Sizer;
import userInterface.UserInternalFrame;

public class TraceComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static final int padWidth = 7;
	private static final int circle = 4;
	static final int lineLength = 4;
	static final int padEdge = 6;
	static final int padLength = 6;
	private static final float triangleLength = 3;
	private UserInternalFrame mUserInternalFrame;
	private SyntaxFacade mSyntaxFacade;
	private boolean mDrawTrace;
	private int mStartX;
	private int mStartY;
	private int mEndY;
	private int mEndX;
	private int midStartY;
	private int midEndY;
	private int midStartX;
	private int midEndX;
	private static final int POSITION_BOTTOM = 0;
	private static final int POSITION_LEFT = 1;
	private static final int POSITION_RIGHT = 2;

	public TraceComponent(UserInternalFrame pUserInternalFrame) {
		mUserInternalFrame = pUserInternalFrame;
		mSyntaxFacade = mUserInternalFrame.getSyntaxFacade();
		mDrawTrace = true;
	}
	public void paint(Graphics G) {
		this.setBounds(getUserInternalFrame().getContentPane().getBounds());
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
		start.setTraceCount(0);
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
		
		calculateMovement(start,end,contentGraphics);
		drawCubicCurve(start, end, contentGraphics);
	}
	
	private void drawCubicCurve(SyntacticStructure start,SyntacticStructure end, Graphics2D contentGraphics) {
		
		if (start.getCustomTrace() &&
				start.getStartX() == mStartX &&
				start.getEndX() == mEndX &&
				start.getStartY() == mStartY &&
				start.getEndY() == mEndY)
		{
			midStartX = start.getControlStartX();
			midEndX = start.getControlEndX();
			midStartY = start.getControlStartY();
			midEndY = start.getControlEndY();
		}
		else
		{
			start.setControlStartX(midStartX);
			start.setControlEndX(midEndX);
			start.setControlStartY(midStartY);
			start.setControlEndY(midEndY);
			start.setCustomTrace(false);
		}
		CubicCurve2D bezier = new CubicCurve2D.Float(mStartX,mStartY
				,midStartX,(float) midStartY,
				midEndX,(float) midEndY,mEndX,mEndY);
		contentGraphics.draw(bezier);
		start.setStartX(mStartX);
		start.setEndX(mEndX);
		start.setStartY(mStartY);
		start.setEndY(mEndY);
	}
	
	private boolean calculateMovement(SyntacticStructure start, SyntacticStructure end,Graphics2D contentGraphics)
	{
		
		SyntacticStructure tempTop = null;
		SyntacticStructure tempBottom = null;

		if(start.getChildren().size() == 0 && end.getChildren().size() == 0)
		{
			drawStart(start,contentGraphics,POSITION_BOTTOM);
			drawEnd(end,contentGraphics,POSITION_BOTTOM);
			midStartX = mStartX;
			midEndX = mEndX;
			if (mEndY > mStartY)
			{
				midStartY = mEndY + 20;
				midEndY = mEndY + 20;
			}
			else
			{
				midStartY = mStartY + 20;
				midEndY = mStartY + 20;
			}
			return true;
		}
		
		if (start.getAbsoluteOrder() == 0 || end.getAbsoluteOrder() == 0)
		{
			drawStart(start,contentGraphics,POSITION_LEFT);
			drawEnd(end,contentGraphics,POSITION_LEFT);
			midStartX = mStartX;
			midEndX = mEndX;
			if (mEndY > mStartY)
			{
				midStartY = mEndY + 20;
				midEndY = mEndY + 20;
			}
			else
			{
				midStartY = mStartY + 20;
				midEndY = mStartY + 20;
			}
			return true;
		}
		if (start.getAbsoluteOrder() == ((LinkedList) mSyntaxFacade.getLinkedArray().get(start.getLevel())).size()-1
				|| end.getAbsoluteOrder() == ((LinkedList) mSyntaxFacade.getLinkedArray().get(end.getLevel())).size()-1)
		{
			drawStart(start,contentGraphics,POSITION_RIGHT);
			drawEnd(end,contentGraphics,POSITION_RIGHT);
			midStartX = mStartX;
			midEndX = mEndX;
			if (mEndY < mStartY)
			{
				midStartY = mEndY - 20;
				midEndY = mEndY - 20;
			}
			else
			{
				midStartY = mStartY - 20;
				midEndY = mStartY - 20;
			}
			return true;
		}
		
		
		if(start.getLevel() > end.getLevel())
		{
			tempTop = end;
			tempBottom = start;
		}
		else
		{
			tempTop = start;
			tempBottom = end;
		}
		
		tempTop = mSyntaxFacade.getLower(tempTop, tempTop.getNumber(), tempTop.getLevel(), tempBottom.getLevel(), true);
		if(tempTop != null)
		{
			if (tempTop.equals(tempBottom))
			{
				drawStart(start,contentGraphics,POSITION_LEFT);
				drawEnd(end,contentGraphics,POSITION_LEFT);
					midStartX = mStartX;
					midEndX = mEndX;
				if (mEndY < mStartY)
				{
					midStartY = mEndY;
					midEndY = mEndY;
				}
				else
				{
					midStartY = mStartY;
					midEndY = mStartY;
				}
				return true;
			}
			LinkedList tempList = ((LinkedList) mSyntaxFacade.getLinkedArray().get(tempTop.getLevel()));
			if(!(tempTop.getAbsoluteOrder() == 0))
			{
				//System.out.println("left down");
				if (tempList.get(tempTop.getAbsoluteOrder()-1).equals(tempBottom))
				{
					//System.out.println("we have a match");
					if (tempBottom == start)
					{
						tempTop = end;
						drawStart(tempBottom,contentGraphics,POSITION_RIGHT);
						drawEnd(tempTop,contentGraphics,POSITION_LEFT);
					}
					else
					{
						tempTop = start;
						drawStart(tempTop,contentGraphics,POSITION_LEFT);
						drawEnd(tempBottom,contentGraphics,POSITION_RIGHT);
					}
					double difference = mStartX - mEndX;
					midStartX = (int) (mStartX - .3*difference);
					midEndX = (int) (mEndX + .3*difference);
					midStartY = mStartY;
					midEndY = mEndY;
					return true;
				}
			}
		}
		if(start.getLevel() > end.getLevel())
		{
			tempTop = end;
			tempBottom = start;
		}
		else
		{
			tempTop = start;
			tempBottom = end;
		}
		
			tempTop = mSyntaxFacade.getLower(tempTop, tempTop.getNumber(), tempTop.getLevel(), tempBottom.getLevel(), false);

		if(tempTop != null)
		{
		if (tempTop.equals(tempBottom))
		{
			drawStart(start,contentGraphics,POSITION_RIGHT);
			drawEnd(end,contentGraphics,POSITION_RIGHT);
			
				midStartX = mStartX;
				midEndX = mEndX;
			if (mEndY < mStartY)
			{
				midStartY = mEndY;
				midEndY = mEndY;
			}
			else
			{
				midStartY = mStartY;
				midEndY = mStartY;
			}
			return true;
		}
		LinkedList tempList = ((LinkedList) mSyntaxFacade.getLinkedArray().get(tempTop.getLevel()));
		if(!(tempTop.getAbsoluteOrder() + 1 == 
			tempList.size()))
		{
			//System.out.println("right down");
			if (tempList.get(tempTop.getAbsoluteOrder()+1).equals(tempBottom))
			{
				//System.out.println("we hae a match");
				if (tempBottom == start)
				{
					tempTop = end;
					drawStart(tempBottom,contentGraphics,POSITION_LEFT);
					drawEnd(tempTop,contentGraphics,POSITION_RIGHT);
				}
				else
				{
					tempTop = start;
					drawStart(tempTop,contentGraphics,POSITION_RIGHT);
					drawEnd(tempBottom,contentGraphics,POSITION_LEFT);
				}
				double difference = mStartX - mEndX;
				midStartX = (int) (mStartX - .3*difference);
				midEndX = (int) (mEndX + .3*difference);
				midStartY = mStartY;
				midEndY = mEndY;
				return true;
			}
		}
		}
		if (start.getAbsoluteOrder() == 0 
				&& end.getAbsoluteOrder() == ((LinkedList)mSyntaxFacade.getLinkedArray().get(end.getLevel())).size()-1)
				{
					drawStart(start,contentGraphics,POSITION_LEFT);
					drawEnd(end,contentGraphics,POSITION_RIGHT);
					midStartY = mUserInternalFrame.getProperties().getTopTranslate() - 40;
					midStartX = mStartX - 20;
					midEndX = mEndX + 20;
					midEndY = mUserInternalFrame.getProperties().getTopTranslate() - 40;
					return true;
				}
		if (end.getAbsoluteOrder() == 0
				&& start.getAbsoluteOrder() == ((LinkedList)mSyntaxFacade.getLinkedArray().get(start.getLevel())).size()-1)
				{
					drawStart(start,contentGraphics,POSITION_RIGHT);
					drawEnd(end,contentGraphics,POSITION_LEFT);
					midStartY = mUserInternalFrame.getProperties().getTopTranslate() - 40;
					midStartX = mStartX + 20;
					midEndX = mEndX - 20;
					midEndY = mUserInternalFrame.getProperties().getTopTranslate() - 40;
					return true;
				}
		if (start.getButtonX() > end.getButtonX())
		{
			drawStart(start,contentGraphics,POSITION_LEFT);
			drawEnd(end,contentGraphics,POSITION_RIGHT);
			//midStartX = 0;
			//midEndX = (int) getSyntaxFacade().getRightShift();
		}
		else
		{
			drawStart(start,contentGraphics,POSITION_RIGHT);
			drawEnd(end,contentGraphics,POSITION_LEFT);
			//midStartX = (int) getSyntaxFacade().getRightShift();
			//midEndX = 0;
			
		}
		midStartX = mStartX;
		midEndX = mEndX;
		midStartY = (int) getSyntaxFacade().getBottomShift();
		midEndY = midStartY;
		return true;
	}
	
	private void drawStart(SyntacticStructure start,
			Graphics2D contentGraphics, int position) {
		
		if (position == POSITION_BOTTOM)
		{
			float padBottom = 0;
			padBottom = - (start.getTraceNumber() * padWidth)/2 + start.getTraceCount() * padWidth;
			contentGraphics.fillArc((int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom - circle/2),(int) (start
							.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()), circle,
					circle, 0, 360);
	
			mStartX = (int)(start.getButtonX() 
					+ start.getButtonWidth()/2 + padBottom);
			mStartY = (int) (start
					.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() + circle);
		}
		else if (position == POSITION_LEFT) {
			contentGraphics.fillArc((int) start.getButtonX() - padEdge  , (int) start
					.getButtonY() + (start.getTextHeight()/2) - (padLength/2)- (circle/2)
					+ start.getTraceCount() * padLength, circle,
					circle, 0, 360);

			mStartX = (int) start.getButtonX() - padEdge;
			mStartY = (int) start
			.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getTraceCount() * padLength;
			
		} 
		else
		{
			contentGraphics.fillArc((int) start.getButtonX() + padEdge
					+ start.getButtonWidth() - lineLength,
					(int) start.getButtonY() + (start.getTextHeight()/2) - (circle/2) - (padLength/2)
					+ start.getTraceCount() * padLength,
					circle,
					circle, 0, 360);

			mStartX = (int) start.getButtonX() + padEdge
					+ start.getButtonWidth();
			mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
			+ start.getTraceCount() * padLength);
			
		}
			start.setTraceCount(start.getTraceCount() + 1);
	}




	private void drawEnd(SyntacticStructure end,
			Graphics2D contentGraphics, int position) {
	
		if (position == POSITION_BOTTOM)
		{
			float padBottom = 0;
			padBottom = - (end.getTraceNumber() * padWidth)/2 + end.getTraceCount() * padWidth;
			GeneralPath polly = new GeneralPath();
			// move the pollygon to the middle and bottom
			polly.moveTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength());
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) - (triangleLength),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() + triangleLength);
			polly.lineTo((float) (end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom) + (triangleLength),
					(float) end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() + triangleLength);
			polly.closePath();
			contentGraphics.fill(polly);

			mEndX = (int)(end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom);
			mEndY = (int) (end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() + triangleLength);
		}
		else if (position == POSITION_LEFT) {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX() - padEdge
					+ triangleLength,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX() - padEdge
					,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX() - padEdge
					,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			mEndX = (int) end.getButtonX() - padEdge
					;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getTraceCount() * padLength;
		} else {
			
			GeneralPath polly = new GeneralPath();
			polly.moveTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					- triangleLength,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							+ 0.25f);
			polly.lineTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							+ 3.25f);
			polly.lineTo((float) end.getButtonX() + padEdge
					+ end.getButtonWidth()
					,
					(float) end
					.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
					+ end.getTraceCount() * padLength
							- 2.75f);
			polly.closePath();
			contentGraphics.fill(polly);
			
			mEndX = (int) end.getButtonX() + padEdge + end.getButtonWidth()
					;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getTraceCount() * padLength;
		}
		end.setTraceCount(end.getTraceCount()+1);
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


package syntaxTree;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
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
	private int mLeft;
	private int mRight;
	private int mTop;
	private int mBottom;
	private double mLeftHeightBottom;
	private double mRightHeightBottom;
	private double mLeftHeightTop;
	private double mRightHeightTop;
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
		
		mStartX -= mUserInternalFrame.getProperties().getLeftTranslate();
		mEndX -= mUserInternalFrame.getProperties().getLeftTranslate();
		mStartY -= mUserInternalFrame.getProperties().getTopTranslate();
		mEndY -= mUserInternalFrame.getProperties().getTopTranslate();
		midStartX -= mUserInternalFrame.getProperties().getLeftTranslate();
		midEndX -= mUserInternalFrame.getProperties().getLeftTranslate();
		midStartY -= mUserInternalFrame.getProperties().getTopTranslate();
		midEndY -= mUserInternalFrame.getProperties().getTopTranslate();
		
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
		start.setStartX(mStartX);
		start.setEndX(mEndX);
		start.setStartY(mStartY);
		start.setEndY(mEndY);
		mStartX += mUserInternalFrame.getProperties().getLeftTranslate();
		mEndX += mUserInternalFrame.getProperties().getLeftTranslate();
		mStartY += mUserInternalFrame.getProperties().getTopTranslate();
		mEndY += mUserInternalFrame.getProperties().getTopTranslate();
		midStartX += mUserInternalFrame.getProperties().getLeftTranslate();
		midEndX += mUserInternalFrame.getProperties().getLeftTranslate();
		midStartY += mUserInternalFrame.getProperties().getTopTranslate();
		midEndY += mUserInternalFrame.getProperties().getTopTranslate();
		CubicCurve2D bezier = new CubicCurve2D.Float(mStartX,mStartY
				,midStartX,(float) midStartY,
				midEndX,(float) midEndY,mEndX,mEndY);
		contentGraphics.draw(bezier);
		drawArrow(contentGraphics);
		
		
	}
	
	private void drawArrow(Graphics2D contentGraphics) {
		double    xFrom, xTo, yFrom, yTo;
	    double denom, x, y, dx, dy, cos, sin;
	    Polygon triangle;

	    xFrom  = midEndX;
	    xTo   = mEndX;
	    yFrom  = midEndY;
	    yTo   = mEndY;

	    dx   	= (double)(xTo - xFrom);
	    dy   	= (double)(yTo - yFrom);
	    denom 	= Math.sqrt(dx*dx + dy*dy);

	    cos = triangleLength /denom;
	    sin = triangleLength /denom;
	    x   = xTo - cos*dx;
	    y   = yTo - cos*dy;
	    int x1  = (int)(x - sin*dy);
	    int y1  = (int)(y + sin*dx);
	    int x2  = (int)(x + sin*dy);
	    int y2  = (int)(y - sin*dx);

	    triangle = new Polygon();
	    triangle.addPoint((int)xTo, (int)yTo);
	    triangle.addPoint(x1, y1);
	    triangle.addPoint(x2, y2);
	    contentGraphics.fillPolygon(triangle);
	    contentGraphics.drawPolygon(triangle);
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
			if(end.getChildren().size() == 0)
			{
				drawEnd(end,contentGraphics,POSITION_BOTTOM);
			}
			else
			{
				drawEnd(end,contentGraphics,POSITION_LEFT);
			}
			midStartX = mStartX;
			midEndX = mEndX;
			if (mEndY > mStartY)
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
		if (start.getAbsoluteOrder() == ((LinkedList) mSyntaxFacade.getLinkedArray().get(start.getLevel())).size()-1
				|| end.getAbsoluteOrder() == ((LinkedList) mSyntaxFacade.getLinkedArray().get(end.getLevel())).size()-1)
		{
			drawStart(start,contentGraphics,POSITION_RIGHT);
			if(end.getChildren().size() == 0)
			{
				drawEnd(end,contentGraphics,POSITION_BOTTOM);
			}
			else
			{
				drawEnd(end,contentGraphics,POSITION_RIGHT);
			}
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
		if (start.getAbsoluteOrder() == 0 
				&& end.getAbsoluteOrder() == ((LinkedList)mSyntaxFacade.getLinkedArray().get(end.getLevel())).size()-1)
				{
					drawStart(start,contentGraphics,POSITION_LEFT);
					if(end.getChildren().size() == 0)
					{
						drawEnd(end,contentGraphics,POSITION_BOTTOM);
					}
					else
					{
						drawEnd(end,contentGraphics,POSITION_RIGHT);
					}
					midStartY = mUserInternalFrame.getProperties().getTopTranslate() - 30;
					midStartX = mStartX - 20;
					midEndX = mEndX + 20;
					midEndY = mUserInternalFrame.getProperties().getTopTranslate() - 30;
					return true;
				}
		if (end.getAbsoluteOrder() == 0
				&& start.getAbsoluteOrder() == ((LinkedList)mSyntaxFacade.getLinkedArray().get(start.getLevel())).size()-1)
				{
					drawStart(start,contentGraphics,POSITION_RIGHT);
					if(end.getChildren().size() == 0)
					{
						drawEnd(end,contentGraphics,POSITION_BOTTOM);
					}
					else
					{
						drawEnd(end,contentGraphics,POSITION_LEFT);
					}
					midStartY = mUserInternalFrame.getProperties().getTopTranslate() - 30;
					midStartX = mStartX + 20;
					midEndX = mEndX - 20;
					midEndY = mUserInternalFrame.getProperties().getTopTranslate() - 30;
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
		//if (start.getChildren().size() == 0)
		if (0==0)
		{
			if (position == POSITION_BOTTOM)
			{
				float padBottom = 0;
				padBottom = - (start.getTraceNumber() * padWidth)/2 + start.getTraceCount() * padWidth;
				contentGraphics.fillArc((int)(start.getButtonX() 
						+ start.getButtonWidth()/2 + padBottom - circle/2),(int) (start
								.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()) + (circle/2), circle,
						circle, 0, 360);
		
				mStartX = (int)(start.getButtonX() 
						+ start.getButtonWidth()/2 + padBottom);
				mStartY = (int) (start
						.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() + circle);
			}
			else if (position == POSITION_LEFT) {
				contentGraphics.fillArc((int) start.getButtonX() - padEdge  -(circle/2), (int) start
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
						+ start.getButtonWidth() - (circle/2),
						(int) start.getButtonY() + (start.getTextHeight()/2) - (circle/2) - (padLength/2)
						+ start.getTraceCount() * padLength,
						circle,
						circle, 0, 360);
	
				mStartX = (int) start.getButtonX() + padEdge
						+ start.getButtonWidth();
				mStartY = (int) (start.getButtonY() + (start.getTextHeight()/2) - (padLength/2)
				+ start.getTraceCount() * padLength);
				
			}
		}
		else
		{
			mLeft = (int) start.getButtonX();
			mRight = (int) (start.getButtonX() + start.getButtonWidth());
			mTop = (int) start.getButtonY() - mUserInternalFrame.getProperties().getMinLineLength()/2;
			mBottom = (int) (start.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength());
			setSubtreeBounds(start);
			// Which diagonal is bigger?
			double LBRTy = mLeftHeightBottom - mRightHeightTop;
			double LTRBy = mLeftHeightTop - mRightHeightBottom;
			double width = Math.abs(mLeft - mRight);
			double LBRT = Math.sqrt(width*width + LBRTy*LBRTy);
			double LTRB = Math.sqrt(width*width + LTRBy*LTRBy);
			double leftY = (LBRT > LTRB ? mLeftHeightBottom :mLeftHeightTop);
			double rightY = (LBRT > LTRB ? mRightHeightTop:mRightHeightBottom);
			// now, get the center:
			double centerX = mLeft + (mRight-mLeft)/2;
			double centerY = mTop + (mBottom-mTop)/2;
			double leftAngle = (centerX - mLeft)/ (centerY - leftY);
			double rightAngle = (centerX - mRight)/ (centerY - rightY);
			double sinLeft = Math.sin(Math.atan(leftAngle));
			double sinRight = Math.sin(Math.atan(rightAngle));
			double sin = sinLeft < sinRight ? sinLeft : sinRight;
			double maxLength = Math.abs(centerX-mLeft) > Math.abs(centerX-mRight) ? 
					Math.abs(centerX-mLeft) : Math.abs(centerX-mRight);
			mLeft = (int) (centerX-Math.abs(maxLength/sin));
			mRight = (int) (centerX+Math.abs(maxLength/sin));
			System.out.println(mLeft);
			System.out.println(mRight);
			Ellipse2D ellipse = new Ellipse2D.Double(mLeft ,
					mTop,
					mRight - mLeft,
					mBottom - mTop);
			
			contentGraphics.draw(ellipse);
			if (position == POSITION_BOTTOM)
			{
				mStartX = mLeft + (mRight-mLeft)/2;
				mStartY = mBottom;
			}
			if (position == POSITION_LEFT)
			{
				mStartX = mLeft;
				mStartY = mTop + (mBottom - mTop)/2;
			}
			if (position == POSITION_RIGHT)
			{
				mStartX = mRight;
				mStartY = mTop + (mBottom - mTop)/2;
			}
		}
			start.setTraceCount(start.getTraceCount() + 1);
	}

	private void setSubtreeBounds(SyntacticStructure start) {
		if (mLeft > (int) start.getButtonX())
		{
			mLeft = (int) start.getButtonX();
			mLeftHeightTop = start.getButtonY();
			mLeftHeightBottom = start.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()/2;
		}
		if (mRight < (int) (start.getButtonX() + start.getButtonWidth()))
		{
			mRight = (int) (start.getButtonX() + start.getButtonWidth());
			mRightHeightTop = start.getButtonY();
			mRightHeightBottom = start.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()/2;
		}
		if (mBottom < (int) (start.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()/2))
		{
			mBottom = (int) (start.getButtonY() + start.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength()/2);
		}
		for(int i = 0; i < start.getChildren().size(); i++)
		{
			setSubtreeBounds((SyntacticStructure) start.getChildren().get(i));
		}
	}
	private void drawEnd(SyntacticStructure end,
			Graphics2D contentGraphics, int position) {
	
		if (position == POSITION_BOTTOM)
		{
			float padBottom = 0;
			padBottom = - (end.getTraceNumber() * padWidth)/2 + end.getTraceCount() * padWidth;

			mEndX = (int)(end.getButtonX() 
					+ end.getButtonWidth()/2 + padBottom);
			mEndY = (int) (end
					.getButtonY() + end.getButtonHeight() - mUserInternalFrame.getProperties().getMinLineLength() );
		}
		else if (position == POSITION_LEFT) {
			
			mEndX = (int) end.getButtonX() - padEdge
					;
			mEndY = (int) end
			.getButtonY() + (end.getTextHeight()/2) - (padLength/2)
			+ end.getTraceCount() * padLength;
		} else {
			
			
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


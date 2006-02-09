
//TreeForm Syntax Tree Drawing Software
//Copyright (C) 2006  Donald Derrick
//
//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
//package userInterface;
/*
 * Created on 20-Jul-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package userInterface;

import java.awt.*;

/**
 * 
 * @author Donald Derrick
 * @version 0.1
 * <br>
 * date: 19-Aug-2004
 * <br>
 * <br>
* Present a simple graphic to the user upon launch of the application, to 
* provide a faster initial response than is possible with the main window.
* 
* <P>Adapted from an 
* <a href=http://developer.java.sun.com/developer/qow/archive/24/index.html>item</a> 
* on Sun's Java Developer Connection.
*
* <P>This splash screen appears within about 2.5 seconds on a development 
* machine. The main screen takes about 6.0 seconds to load, so use of a splash 
* screen cuts down the initial display delay by about 55 percent. 
*
* @author <a href="http://www.javapractices.com/">javapractices.com</a>
*/

public final class UserSplashScreen extends Frame {

  /**
  * @param aImageId must have content, and is used by  
  * <code>Class.getResource</code> to retrieve the splash screen image.
  */
  public UserSplashScreen(String aImageId) {
    /* Implementation Note
    * Args.checkForContent is not called here, in an attempt to minimize 
    * class loading.
    */
    if ( aImageId == null || aImageId.trim().length() == 0 ){
      throw new IllegalArgumentException("Image Id does not have content.");
    }
    mImageId = aImageId;
  }
   
  /**
  * Show the splash screen to the end user.
  *
  * <P>Once this method returns, the splash screen is realized, which means 
  * that almost all work on the splash screen should proceed through the event 
  * dispatch thread. In particular, any call to <code>dispose</code> for the 
  * splash screen must be performed in the event dispatch thread.
  */
  public void splash(){
    initImageAndTracker();
    setSize(mImage.getWidth(null), mImage.getHeight(null));
    center();
    
    mMediaTracker.addImage(mImage, 0);
    try {
      mMediaTracker.waitForID(0);
    }
    catch(InterruptedException ie){
      System.out.println("Cannot track image load.");
    }

    SplashWindow splashWindow = new SplashWindow(this,mImage);
  }
  
  
  // PRIVATE//
  private final String mImageId;
  private MediaTracker mMediaTracker;
  private Image mImage;

  private void initImageAndTracker(){
    mMediaTracker = new MediaTracker(this);
    //URL imageURL = UserSplashScreen.class.getResource(fImageId);
    mImage = Toolkit.getDefaultToolkit().getImage(mImageId);
  }

  /**
  * Centers the frame on the screen.
  *
  * This centering service is more or less in {@link UiUtil}; this duplication 
  * is justified only because the use of {@link UiUtil} would entail more 
  * class loading, which is not desirable for a splash screen.
  */
  private void center(){
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    Rectangle frame = getBounds();
    setLocation((screen.width - frame.width)/2, (screen.height - frame.height)/2);
  }
 
  private class SplashWindow extends Window {
    SplashWindow(Frame aParent, Image aImage) {
       super(aParent);
       fImage = aImage;
       setSize(fImage.getWidth(null), fImage.getHeight(null));
       Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
       Rectangle window = getBounds();
       setLocation((screen.width - window.width) / 2, (screen.height - window.height) / 2);
       setVisible(true);
    }
    public void paint(Graphics pGraphics) {
      if (fImage != null) {
        pGraphics.drawImage(fImage,0,0,this);
      }
    }
    private Image fImage;
  }
}
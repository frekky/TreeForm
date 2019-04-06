
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

package userInterface;

import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import sun.misc.Launcher;

/**
 * TreeForm 1.0.3
 *
 * @author Donald Derrick
 * @version 1.0.3
 * Copyright (C) 2004-2010 Donald Derrick
 *
 * The content of this project was created by Donald Derrick except where otherwise
 * noted in code comments and in the about.htm page which must be distributed with
 * each copy of this software.
 *
 * @see <a href="http://www.mapsofspeech.com/2017/10/02/treeform">TreeForm Home Page</a>
 *
 */
public class Start {

    private static UserSplashScreen mUserSplashScreen;
    private static final Logger pLogger =
        Logger.getLogger(Launcher.class.getPackage().getName());
    /**
     *
     * @param args Unused right now, contains command-line argumenets
     * TODO: Implement command-line arguments for syntax level versions
     * such as "100", "200", "300", blank, for full version.
     */

    public static void main(String[] args) {
        System.setProperty("apple.laf.useScreenMenuBar", "true");

        try {
            UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (InstantiationException e) {

            e.printStackTrace();
        } catch (IllegalAccessException e) {

            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {

            e.printStackTrace();
        }
        pLogger.info("Launching the application...");

        mUserSplashScreen = new UserSplashScreen("image/sunset800.jpg");
        mUserSplashScreen.splash();
        UserFrame userUI = new UserFrame();
        //userUI.addKeyListener(new ListenerKeyboard());
        userUI.validate();
        EventQueue.invokeLater( new SplashScreenCloser() );

        pLogger.info("Launch thread now exiting...");
    }
    /**
     *
     * @author Donald Derrick
     *
     *The basic splash screen.
     *
     */
    private static final class SplashScreenCloser implements Runnable {
        public void run(){
            mUserSplashScreen.dispose();
        }
    }
}

// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import java.awt.*;
import java.io.InputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class Window extends Canvas
{
    private BufferedImage img;
    static Container con;
    static JFrame frame;
    static JPanel panel;
    static Dimension screenSize;
    static BufferedImage cursorImage;
    static Cursor transCursor;

    public static String cursor;
    static String transCursorName = "transparentCursor";
    public Window(final String title, final Game game) {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame(title);
        frame.setPreferredSize(new Dimension(1200, 800));
        final ImageIcon logo = new ImageIcon(this.getClass().getClassLoader().getResource("texture/logo.png"));
        final InputStream is = this.getClass().getResourceAsStream("/texture/logo.png");
        try {
            this.img = ImageIO.read(is);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        final Taskbar taskbar = Taskbar.getTaskbar();
        try {
            taskbar.setIconImage(this.img);
        }
        catch (UnsupportedOperationException e2) {
            System.out.println("the os does not support taskbar.setIconImage");
        }
        catch (SecurityException e3) {
            System.out.println("Security Exception");
        }
        frame.setIconImage(logo.getImage());
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(screenSize.width/7, screenSize.height/8);
        frame.add(game);
        frame.setVisible(true);
        frame.setMinimumSize(new Dimension(1200, 800));

        if (Game.catCursor) cursor = "catnoir"; else cursor = "ladybug";

        cursorChange(cursor);

        /*panel = new JPanel();
        JLabel username = new JLabel("Press button to enter your name here");
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton setNameButton = new JButton("Set name");
        setNameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setNameButton.addActionListener((ActionEvent e) -> {
            String usernameinput = JOptionPane.showInputDialog(setNameButton, "Enter a username", "Set username", JOptionPane.OK_CANCEL_OPTION);

            if (usernameinput != null) {
                stringUsername = String.valueOf(usernameinput);
            }
        });

        panel.add(Box.createRigidArea(new Dimension(5,10)));
        panel.add(username);
        panel.add(Box.createRigidArea(new Dimension(5,10)));
        panel.add(setNameButton);
        frame.add(panel);*/

        game.start();
    }

    public static void cursorChange(String cursor) {
        final BufferedImageLoader loader = new BufferedImageLoader();
        cursorImage = loader.loadImage("/texture/"+cursor+"Cursor.png");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        MediaTracker mediaTracker = new MediaTracker(frame);
        mediaTracker.addImage(cursorImage, 0);
        try
        {
            mediaTracker.waitForID(0);
        }
        catch (InterruptedException ie)
        {
            System.err.println(ie);
            System.out.println("mouse failed at wait for id");
            System.exit(1);
        }
        transCursor = toolkit.createCustomCursor(cursorImage, new Point(0,0), transCursorName);
        ((Component)frame).setCursor(transCursor);
    }

    public void tick() {
        this.setLocation(frame.getLocation());
    }
}

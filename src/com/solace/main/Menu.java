// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.awt.event.MouseAdapter;

import static com.solace.main.Game.*;

public class Menu extends MouseAdapter
{
    private final Game game;
    private Handler handler;
    private Random r;
    private HUD hud;

    private LoadSave loadSave;
    public boolean blackout;
    public boolean blackoutUp;
    float blackoutAlpha = 0f;
    int blackoutTimer = 0;
    public STATE blackoutState;
    Color blackoutColor;
    float[] size = new float[4];
    boolean[] enlarge = new boolean[4];
    private String cursor;
    final BufferedImageLoader loader = new BufferedImageLoader();
    
    public Menu(final Game game, final Handler handler, final HUD hud, LoadSave loadSave) {
        this.r = new Random();
        this.game = game;
        this.handler = handler;
        this.hud = hud;
        this.loadSave = loadSave;
        Arrays.fill(size, 1f);
        Arrays.fill(enlarge, false);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
        final int mx = e.getX();
        final int my = e.getY();
        if (gameState == STATE.Menu) {
            if (this.mouseOver(mx, my, Game.screenWidth/2-30, 250, 200, 64)) {
                System.out.println("play pressed");
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = Game.STATE.CharacterSelect;
            }
            if (this.mouseOver(mx, my, Game.screenWidth/2-30, 350, 200, 64)) {
                System.out.println("help pressed");
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = STATE.Help;
            }
            if (this.mouseOver(mx, my, Game.screenWidth/2-30, 450, 200, 64)) {
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = STATE.Settings;
            }
            if (this.mouseOver(mx, my, Game.screenWidth/2-30, 550, 200, 64)) {
                System.exit(2);
            }
        } else if (gameState == Game.STATE.Settings) {
            if (this.mouseOver(mx, my, 350, 174, 32, 16)) {
                if (Game.catCursor) {
                    Game.catCursor = false;
                    cursor = "ladybug";
                    Window.cursorChange(cursor);
                } else {
                    Game.catCursor = true;
                    cursor = "catnoir";
                    Window.cursorChange(cursor);
                }
            }
            if (this.mouseOver(mx, my, 350, 274, 32, 16)) {
                autoSave = !autoSave;
            }
            if (this.mouseOver(mx, my, 350, 374, 32, 16)) {
                if (music) {
                    game.stopSound("theme");
                } else {
                    game.loopSound("theme");
                }
                music = !music;
            }
            if (this.mouseOver(mx, my, 50, 50, 200, 64)) {
                LoadSave.writeToSettingsFile();
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = Game.STATE.Menu;
            }
        }
        if (gameState == STATE.CharacterSelect) {
            if (this.mouseOver(mx, my, 0, 0, screenWidth / 2, screenHeight / 4 * 3)) {
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = STATE.Playing;
                isCharacterLadybug = true;
                System.out.println("ladybug");
            }
            if (this.mouseOver(mx, my, screenWidth / 2, 0, screenWidth / 2, screenHeight / 4 * 3)) {
                blackoutAlpha = 0.0f;
                blackout = true;
                blackoutState = STATE.Playing;
                isCharacterLadybug = false;
                System.out.println("chat noir");
            }
        }
    }
    
    @Override
    public void mouseReleased(final MouseEvent e) {
    }
    
    private boolean mouseOver(final int mx, final int my, final int x, final int y, final int width, final int height) {
        return mx > x && mx < x + width && (my > y && my < y + height);
    }
    
    public void tick() {
        mouseMovedCheck();
        if (blackout) blackout();
        for (int i = 0; i < enlarge.length; i++) {
            if (enlarge[i]) enlargeImage(i, 1.3f);
            else shrinkImage(i, 1f);
        }
    }

    public void mouseMovedCheck() {
        final int mx = MouseInfo.getPointerInfo().getLocation().x - window.getLocation().x;
        final int my = MouseInfo.getPointerInfo().getLocation().y - window.getLocation().y;
        //System.out.println(mx + " " + my);
        if (gameState == STATE.CharacterSelect) {
            if (mouseOver(mx, my, 0, 0, screenWidth / 2, screenHeight / 4 * 3)) {
                enlarge[0] = true;
            }if (mouseOver(mx, my, screenWidth / 2, 0, screenWidth / 2, screenHeight / 4 * 3)) {
                enlarge[1] = true;
            }
        }
    }

    public void enlargeImage(int i, float limit) {
        if (size[i] < limit) size[i] += 0.03;
        else enlarge[i] = false;
    }

    public void shrinkImage(int i, float limit) {
        if (size[i] > 1f) size[i] -= 0.03;
    }
    public void blackout() {
        if (blackoutAlpha >= 0) {
            blackoutColor = new Color(0, 0,0, Game.clamp(blackoutAlpha, 0.0f, 1.0f));
            if (!blackoutUp) {
                blackoutAlpha +=0.03;
                if (blackoutAlpha > 1) blackoutUp = true;
            } else {
                gameState = blackoutState;
                blackoutAlpha -= 0.03;
            }
        } else {
            blackoutUp = false;
            blackout = false;
            blackoutAlpha = 0;
        }
    }
    
    public void render(final Graphics g) {
        final Font fnt = new Font("arial", 1, 60);
        final Font fnt2 = new Font("arial", 1, 30);
        final Font fnt3 = new Font("arial", 1, 20);
        Color baseGrey = new Color(38, 38, 38);
        if (this.game.gameState == Game.STATE.Menu) {
            g.setFont(fnt);
            g.setColor(baseGrey);
            g.drawString("Miraculous Simulator X", Game.screenWidth/2-310, 100);
            g.drawRect(Game.screenWidth/2-100, 250, 200, 64);
            g.drawRect(Game.screenWidth/2-100, 350, 200, 64);
            g.drawRect(Game.screenWidth/2-100, 450, 200, 64);
            g.drawRect(Game.screenWidth/2-100, 550, 200, 64);
            g.setFont(fnt2);
            g.drawString("Play", Game.screenWidth/2-30, 290);
            g.drawString("Help", Game.screenWidth/2-30, 390);
            g.drawString("Options", Game.screenWidth/2-55, 490);
            g.drawString("Quit", Game.screenWidth/2-30, 590);
            //g.drawLine(Game.screenWidth/2, 0, Game.screenWidth/2, Game.screenHeight);
        } else if (this.game.gameState == Game.STATE.Settings) {
            g.setColor(baseGrey);
            g.setFont(fnt3);
            g.drawString("Cursor", 220, 190);
            g.drawString("Autosave", 220, 290);
            g.drawString("Music", 220, 390);
            g.drawRect(350, 174, 32, 16);
            g.drawRect(210, 150, 200, 64);
            g.drawRect(350, 274, 32, 16);
            g.drawRect(210, 250, 200, 64);
            g.drawRect(350, 374, 32, 16);
            g.drawRect(210, 350, 200, 64);
            if (!game.catCursor) {
                g.setColor(new Color(215, 65, 31));
                g.fillRect(350, 175, 15, 14);
            } else {
                g.setColor(new Color(45, 225, 50));
                g.fillRect(366, 175, 15, 14);
            }
            if (!game.autoSave) {
                g.setColor(new Color(215, 65, 31));
                g.fillRect(350, 275, 15, 14);
            } else {
                g.setColor(new Color(45, 225, 50));
                g.fillRect(366, 275, 15, 14);
            }
            if (!game.music) {
                g.setColor(new Color(215, 65, 31));
                g.fillRect(350, 375, 15, 14);
            } else {
                g.setColor(new Color(45, 225, 50));
                g.fillRect(366, 375, 15, 14);
            }
            g.setFont(fnt2);
            g.setColor(baseGrey);
            g.drawString("Back", 130, 90);
            g.drawRect(50, 50, 200, 64);
        } else if (Game.gameState == Game.STATE.CharacterSelect) {
            g.setColor(new Color(173, 229, 174));
            g.fillRect(screenWidth/2, 0, screenWidth/2, screenHeight);
            g.setColor(new Color(217, 143, 143));
            g.fillRect(0, 0, screenWidth/2, screenHeight);
            g.drawImage(catnoir2, (int) (screenWidth/2-(200*(size[1]-1))), (int) (screenHeight/6-(200*(size[1]-1))), (int) (((catnoir2.getWidth())*0.75)*size[1]), (int) (((catnoir2.getHeight())*0.75)*size[1]), null);
            g.drawImage(ladybug, (int) -(200*(size[0]-1)), (int) (screenHeight/6-(200*(size[0]-1))), (int) (((ladybug.getWidth())*0.75)*size[0]), (int) (((ladybug.getHeight())*0.75)*size[0]), null);
            g.setColor(new Color(100, 227, 103));
            g.fillRect(screenWidth/2+1, screenHeight/4*3-30, screenWidth/2, screenHeight);
            g.setColor(new Color(218, 57, 57));
            g.fillRect(0, screenHeight/4*3-30, screenWidth/2-1, screenHeight);
            g.drawImage(ladybuglogo, screenWidth/4-64, screenHeight/4*3, 128, 128, null);
            g.drawImage(catnoirlogo, screenWidth/4*3-64, screenHeight/4*3, 128, 128,null);
            g.setColor(new Color(38, 38, 38));
            //g.drawLine(0, screenHeight/4*3-31, screenWidth, screenHeight/4*3-31);
            //g.drawLine(0, screenHeight/4*3-30, screenWidth, screenHeight/4*3-30);
            //g.drawLine(0, screenHeight/4*3-29, screenWidth, screenHeight/4*3-29);
            //g.drawLine(screenWidth/2, 0, screenWidth/2, screenHeight);
            //g.drawLine(screenWidth/2-1, 0, screenWidth/2-1, screenHeight);
            //g.drawLine(screenWidth/2+1, 0, screenWidth/2+1, screenHeight);
        }
        if (blackout) {
            g.setColor(blackoutColor);
            g.fillRect(0,0,Game.screenWidth, Game.screenHeight);
        }
    }
}

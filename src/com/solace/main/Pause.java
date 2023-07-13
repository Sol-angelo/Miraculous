// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.awt.event.MouseAdapter;

public class Pause extends MouseAdapter
{
    private Game game;
    private Handler handler;
    private Random r;
    private HUD hud;
    
    public Pause(final Game game, final Handler handler, final HUD hud) {
        this.r = new Random();
        this.game = game;
        this.handler = handler;
        this.hud = hud;
    }
    
    @Override
    public void mousePressed(final MouseEvent e) {
        final int mx = e.getX();
        final int my = e.getY();
        final Game game = this.game;
        if (Game.paused = true) {
            if (this.mouseOver(mx, my, 210, 150, 200, 64)) {
                final Game game2 = this.game;
                Game.paused = false;
            }
            if (this.mouseOver(mx, my, 210, 250, 200, 64)) {
                this.handler.clearAll();
                this.game.gameState = Game.STATE.Menu;
                for (int i = 0; i < 20; ++i) {
                }
                final Game game3 = this.game;
                Game.paused = false;
            }
            if (this.mouseOver(mx, my, 210, 350, 200, 64)) {
                System.exit(2);
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
    }
    
    public void render(final Graphics g) {
        final Font fnt2 = new Font("arial", 1, 30);
        g.setFont(fnt2);
        g.setColor(Color.white);
        g.drawString("Play", 275, 190);
        g.setFont(fnt2);
        g.setColor(Color.white);
        g.drawString("Menu", 275, 290);
        g.setFont(fnt2);
        g.setColor(Color.white);
        g.drawString("Quit", 275, 390);
        g.setColor(Color.WHITE);
        g.drawRect(210, 150, 200, 64);
        g.setColor(Color.WHITE);
        g.drawRect(210, 250, 200, 64);
        g.setColor(Color.WHITE);
        g.drawRect(210, 350, 200, 64);
    }
}

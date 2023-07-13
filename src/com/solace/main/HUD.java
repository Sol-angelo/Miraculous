// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import static com.solace.main.Game.gameState;

public class HUD
{
    private Game game;
    private final Handler handler;
    public static float health;
    private float greenValue;
    private float greenValueT;
    private Random r;
    private static int score;
    private static int level;
    private int timerh;
    private static int levelTest;
    
    public HUD(final Game game, final Handler handler) {
        this.greenValue = 255.0f;
        score = 0;
        level = 1;
        this.timerh = 0;
        this.game = game;
        this.handler = handler;
    }
    
    public void tick() {
        HUD.health = Game.clamp(HUD.health, 0.0f, 100.0f);
        this.greenValue = (float)(int)Game.clamp(this.greenValue, 0.0f, 255.0f);
        this.greenValueT = (float)(int)Game.clamp(this.greenValueT, 0.0f, 255.0f);
        this.greenValue = (float)((int)HUD.health * 2);
        ++this.timerh;
        /*if (gameState == Game.STATE.GameModeSelector) {
                for (int i = 0; i < 20; ++i) {
                    HUD.SHEALTH = 100.0f;
                    HUD.health = 100.0f;
                }
        }
        if (gameState == Game.STATE.BEasy && this.timerh >= 100) {
            if (health < 100) {
                HUD.health += 2.0f;
            }
            this.timerh = 0;
        }
        else if (gameState == Game.STATE.BMedium && this.timerh >= 150) {
            if (health < 100) {
                ++HUD.health;
            }
            this.timerh = 0;

        }
        else if (gameState == Game.STATE.BHard && this.timerh >= 200) {
            if (health < 100) {
                ++HUD.health;
            }
            this.timerh = 0;
        }
        this.r = new Random();
        ++score;
        if (HUD.health <= 0.0f) {
            gameState = Game.STATE.Death;
            this.handler.clearAll();
            if (gameState == Game.STATE.Menu || gameState == Game.STATE.Death || gameState == Game.STATE.Help) {
                for (int i = 0; i < 20; ++i) {
                    HUD.health = 100.0f;
                }
            }
        }
        if (HUD.SHEALTH <= 0.0f) {
            gameState = Game.STATE.Death;
            this.handler.clearAll();
            if (gameState == Game.STATE.Menu || gameState == Game.STATE.Death || gameState == Game.STATE.Help) {
                for (int i = 0; i < 20; ++i) {
                    HUD.SHEALTH = 100.0f;
                }
            }
        }*/
    }
    
    public void render(final Graphics g) {
        g.setColor(Color.darkGray);
        g.fillRect(15, 15, 200, 32);
        g.setColor(new Color(75, (int)this.greenValue, 0));
        g.fillRect(15, 15, (int)HUD.health * 2, 32);
        g.setColor(Color.WHITE);
        g.drawRect(15, 15, 200, 32);
        g.drawString(("Score: "+score), 15, 64);
        g.drawString(("Level: "+level), 15, 80);
    }
    
    public static void setScore(final int tscore) {
        score = tscore;
    }
    
    public float getscore() {
        return (float)score;
    }

    public static float getStaticscore() {
        return (float)score;
    }
    
    public int getlevel() {
        return level;
    }
    public static int getStaticlevel() {
        if (score <= 499) {
            levelTest = 1;
        } else if (score <= 999) {
            levelTest = 2;
        } else if (score <= 1499) {
            levelTest = 3;
        } else if (score <= 1999) {
            levelTest = 4;
        } else if (score <= 2499) {
            levelTest = 5;
        } else if (score <= 2999) {
            levelTest = 6;
        } else if (score <= 3499) {
            levelTest = 7;
        } else if (score <= 3999) {
            levelTest = 8;
        } else if (score <= 4499) {
            levelTest = 9;
        } else if (score <= 4499 + 2180) {
            levelTest = 10;
        } else if (score <= 4999 + 2180) {
            levelTest = 11;
        } else if (score <= 5499 + 2180) {
            levelTest = 12;
        } else if (score <= 5999 + 2180) {
            levelTest = 13;
        } else if (score <= 6499 + 2180) {
            levelTest = 14;
        } else if (score <= 6999 + 2180) {
            levelTest = 15;
        }
        return levelTest;
    }
    
    public static void setLevel(final int tlevel) {
        level = tlevel;
    }
}

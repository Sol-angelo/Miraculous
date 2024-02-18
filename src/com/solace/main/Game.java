// 
// Decompiled by Procyon v0.5.36
// 

package com.solace.main;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import static com.solace.main.Window.con;
import static com.solace.main.Window.frame;

public class Game extends Canvas implements Runnable
{
    private Thread thread;
    private boolean running;
    public boolean fullscreen = false;
    private Random r;
    private Handler handler;
    private HUD hud;
    private Menu menu;
    private LoadSave loadSave;
    public int currentFramesInt;
    public static BufferedImage background;
    public static BufferedImage marinette_room;
    public static BufferedImage catnoir;
    public static BufferedImage catnoir2;
    public static BufferedImage adrien;
    public static BufferedImage adrien2;
    public static BufferedImage gabriel;
    public static BufferedImage gabriel2;
    public static BufferedImage hawkmoth;
    public static BufferedImage ladybug;
    public static BufferedImage marinette;
    public static BufferedImage masterfu;
    public static BufferedImage nathalie;
    public static BufferedImage peacock;
    public static BufferedImage plagg;
    public static BufferedImage plagg2;
    public static BufferedImage shadowmoth;
    public static BufferedImage tiki;
    public static BufferedImage tiki2;
    public static BufferedImage logo;
    public static BufferedImage catnoirlogo;
    public static BufferedImage ladybuglogo;
    public static boolean paused;
    public static boolean isCharacterLadybug;
    public static STATE gameState;
    public static SCENE currentScene;
    public static boolean isF3;
    public static int gameStateAsInt;
    public static int screenWidth;
    public static int screenHeight;
    public static boolean catCursor;
    public static boolean autoSave;
    public static boolean music;
    public static Window window;
    
    public Game() {
        this.running = false;
        gameState = STATE.Menu;
        currentScene = SCENE.Null;
        this.handler = new Handler(this);
        this.hud = new HUD(this, this.handler);
        this.menu = new Menu(this, this.handler, this.hud, this.loadSave);
        this.addKeyListener(new KeyInput(this.handler, this));
        this.addMouseListener(this.menu);
        LoadSave.readFromSettingsFile();
        window = new Window("Chat Noir Simulator X", this);
        fullscreen();
        final BufferedImageLoader loader = new BufferedImageLoader();
        Game.background = loader.loadImage("/texture/background.jpeg");
        Game.marinette_room = loader.loadImage("/texture/marinette_room.png");
        Game.catnoir = loader.loadImage("/texture/catnoir.png");
        Game.catnoir2 = loader.loadImage("/texture/catnoir2.png");
        Game.adrien = loader.loadImage("/texture/adrien.png");
        Game.adrien2 = loader.loadImage("/texture/adrien2.jpeg");
        Game.ladybug = loader.loadImage("/texture/ladybug2.png");
        Game.marinette = loader.loadImage("/texture/marinette.png");
        Game.gabriel = loader.loadImage("/texture/gabriel.png");
        Game.gabriel2 = loader.loadImage("/texture/gabriel2.png");
        Game.masterfu = loader.loadImage("/texture/masterfu.png");
        Game.peacock = loader.loadImage("/texture/peacock.png");
        Game.nathalie = loader.loadImage("/texture/nathalie.png");
        Game.hawkmoth = loader.loadImage("/texture/hawkmoth.png");
        Game.plagg = loader.loadImage("/texture/plagg.png");
        Game.plagg2 = loader.loadImage("/texture/plagg2.png");
        Game.tiki = loader.loadImage("/texture/tiki.png");
        Game.tiki2 = loader.loadImage("/texture/tiki2.png");
        Game.shadowmoth = loader.loadImage("/texture/shadowmoth.png");
        Game.logo = loader.loadImage("/texture/logo.png");
        Game.ladybuglogo = loader.loadImage("/texture/ladybuglogo.png");
        Game.catnoirlogo = loader.loadImage("/texture/catnoirlogo.png");
        HUD.setLevel(1);
        HUD.setScore(0);
        if (music) playSound("theme");
        this.r = new Random();
        if (gameState == STATE.Menu || gameState == STATE.Help) {
            for (int i = 0; i < 20; ++i) {
                //this.handler.addObject(new MenuParticle((float)this.r.nextInt(640), (float)this.r.nextInt(480), ID.MenuParticle, this.handler));
            }
        }
    }

    public static int getCurrentGameStateToInt() {
        return gameStateAsInt;
    }
    
    public synchronized void start() {
        (this.thread = new Thread(this)).start();
        this.running = true;
    }
    
    public synchronized void stop() {
        try {
            this.thread.join();
            this.running = false;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        this.requestFocus();
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        final double ns = 1.0E9 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        float frames = 0.0f;
        while (this.running) {
            final long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1.0) {
                this.tick();
                --delta;
            }
            if (this.running) {
                this.render();
            }
            ++frames;
            if (System.currentTimeMillis() - timer > 1000L) {
                timer += 1000L;
                System.out.println((int)frames + " FPS");
                currentFramesInt = (int) frames;
                frames = 0.0f;
            }
        }
        this.stop();
    }
    
    private void tick() {
        this.window.tick();
        this.menu.tick();
        screenWidth = (int) Window.frame.getBounds().getWidth();
        screenHeight = (int) Window.frame.getBounds().getHeight();
        /*if (Window.stringUsername != null) {
            panel.setVisible(false);
        }
        if (!paused && !inventory) {
            this.handler.tick();
            if (this.gameState == STATE.BEasy) {
                this.hud.tick();
                this.espawner.tick();
            }
            else if (this.gameState == STATE.BMedium) {
                this.hud.tick();
                this.mspawner.tick();
            }else if (this.gameState == STATE.SEasy) {
                this.hud.tick();
            }
            else if (this.gameState == STATE.BHard) {
                this.hud.tick();
                this.hspawner.tick();
            }
            else if (this.gameState == STATE.Menu || this.gameState == STATE.Help) {
                this.menu.tick();
                this.hud.setLevel(1);
                this.hud.setScore(0);
            }
            else if (this.gameState == STATE.Death) {
                this.menu.tick();
            }
        }*/
    }
    
    private void render() {
        final BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        final Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(173, 173, 173));
        if (gameState != STATE.CharacterSelect && gameState != STATE.Playing) g.drawImage(background, screenWidth/2-960, 0, null);
        else if (gameState == STATE.Playing && currentScene == SCENE.MarinetteRoom) g.drawImage(marinette_room, screenWidth/2-640, screenHeight/2-420, null);
        else g.fillRect(0, 0, screenWidth+100, screenHeight+100);
        this.handler.render(g);
        if (gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.Saveload || gameState == STATE.Settings || gameState == STATE.CharacterSelect) {
            this.menu.render(g);
        }
        if (paused) {
            final Font fnt = new Font("verdana", 0, 40);
            g.setFont(fnt);
            g.setColor(new Color(70, 70, 70, 102));
            g.fillRect(0, 0, screenWidth, screenHeight);
            g.setColor(new Color(255, 255, 255));
            g.drawString("Pause", screenWidth/2-60, screenHeight/2-300);
            //g.drawLine(screenWidth/2, 0, screenWidth/2, screenHeight);
            this.menu.render(g);
        }
        if (isF3) {
            final Font fnt = new Font("verdana", 0, 15);
            g.setFont(fnt);
            g.setColor(new Color(33, 33, 33));
            g.drawString("FPS: "+currentFramesInt, 10, 20);
        }
        g.dispose();
        bs.show();
    }

    public void fullscreen() {
        if (fullscreen) {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            gd.setFullScreenWindow(Window.frame);
            frame.setVisible(true);
            frame.setResizable(false);
            repaint();
        } else {
            frame.setResizable(true);
            frame.setVisible(true);
            repaint();
        }
    }

    public void playSound(String name) {
        URL url = this.getClass().getResource("/sounds/"+name+".wav");
        try {
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void loopSound(String name) {
        URL url = this.getClass().getResource("/sounds/"+name+".wav");
        try {
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
                clip.loop(999999999);
            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopSound(String name) {
        URL url = this.getClass().getResource("/sounds/"+name+".wav");
        try {
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                /*
                FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                control.setValue(-10.0f);
                clip.start();
                */
                clip.stop();

            }
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        }
    }
    
    public static float clamp(float var, final float min, final float max) {
        if (var >= max) {
            var = max;
            return max;
        }
        if (var <= min) {
            var = min;
            return min;
        }
        return var;
    }
    
    public static void main(final String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new Game();
    }
    
    static {
        Game.paused = false;
    }
    
    public enum STATE
    {
        Menu,
        Help,
        Saveload,
        StartSave,
        CharacterSelect,
        Settings,
        Playing
    }

    public enum SCENE
    {
        Null,
        MarinetteRoom
    }
}

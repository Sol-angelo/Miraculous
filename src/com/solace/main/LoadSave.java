package com.solace.main;

import java.io.*;
import java.util.Scanner;

public class LoadSave {
    private HUD hud;
    public static int level;
    public static float score;
    public static float health;

    public static int state;
    public static int fileAmount;

    private static Game game;

    public LoadSave(final HUD hud, Game game) {
        this.hud = hud;
        this.game = game;
    }

    public static void CreateSaveFile(int saveNumber) {
        File txtFile = new File("res/save/savedata"+saveNumber+".txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println((int)HUD.health);
            pw.println((int)HUD.getStaticscore());
            pw.println(HUD.getStaticlevel());
            pw.print(Game.getCurrentGameStateToInt());
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void ReadFromSaveFile(int saveNumber) {
        File txtFile = new File("res/save/savedata"+saveNumber+".txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader("res/save/savedata"+saveNumber+".txt"));

            health = Integer.parseInt(br.readLine());
            score = Integer.parseInt(br.readLine());
            level = Integer.parseInt(br.readLine());
            state = Integer.parseInt(br.readLine());

            br.close();

            HUD.health = health;
            HUD.setLevel(level);
            HUD.setScore((int) score);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void CreateSettingsFile() {
        File txtFile = new File("res/save/settings.txt");
        try {
            txtFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter pw = new PrintWriter(txtFile);
            pw.println(Game.catCursor);
            pw.println(Game.autoSave);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void ReadFromSettingsFile() {
        File txtFile = new File("res/save/settings.txt");
        try{
            BufferedReader br = new BufferedReader(new FileReader("res/save/settings.txt"));

            Game.catCursor = Boolean.parseBoolean(br.readLine());
            Game.autoSave = Boolean.parseBoolean(br.readLine());

            br.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

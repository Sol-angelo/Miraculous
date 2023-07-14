package com.solace.main;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class LoadSave {
    private HUD hud;
    public static int level;
    public static float score;
    public static float health;

    public static int state;
    public static int fileAmount;

    private static Game game;

    public static final String key = "cayden is slayer";

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";

    public LoadSave(final HUD hud, Game game) {
        this.hud = hud;
        this.game = game;
    }

    public static void writeToSettingsFile() {
        File txtFile = getFileByOS("data", "settings", "txt");
        File eFile = getEncryptedByOS("data", "settings");
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
            encrypt(key, txtFile, eFile);
        } catch (FileNotFoundException | CryptoException e) {
            e.printStackTrace();
        }
    }

    public static void readFromSettingsFile() {
        File txtFile = getFileByOS("data", "settings", "txt");
        File eFile = getEncryptedByOS("data", "settings");
        if (eFile.exists()) {
            try {
                decrypt(key, eFile, txtFile);
                BufferedReader br = new BufferedReader(new FileReader(txtFile));

                Game.catCursor = Boolean.parseBoolean(br.readLine());
                Game.autoSave = Boolean.parseBoolean(br.readLine());

                br.close();
                encrypt(key, txtFile, eFile);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            writeToSettingsFile();
        }
    }

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile, File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();
            inputFile.delete();
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException |
                 IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    public static File getFileByOS(String addpath, String name, String fileType) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+"."+fileType);
            return txt;
        } else if (osname.contains("Window")) {
            Path path = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+"."+fileType);
            return txt;
        }
        return null;
    }

    public static File getEncryptedByOS(String addpath, String name) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".sexinthecorridor");
            return txt;
        } else if (osname.contains("Window")) {
            Path path = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name+".sexinthecorridor");
            return txt;
        }
        return null;
    }

    public static File getFolderByOS(String addpath, String name) {
        String osname = System.getProperty("os.name");
        if (osname.contains("Mac")) {
            Path path = Path.of(System.getProperty("user.home"), "Library", "Application Support", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name);
            return txt;
        } else if (osname.contains("Window")) {
            Path path = Path.of(System.getProperty("user.home"), "AppData", "Solangelo", "Launcher");
            File path2 = new File(path + "/" + addpath);
            path2.mkdirs();
            File txt = new File(path2 + "/"+name);
            return txt;
        }
        return null;
    }
}

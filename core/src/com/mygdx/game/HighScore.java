package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

public class HighScore {
    public static String topPlayerName = "";
    public static int topPlayerScore = 0;

    public static void loadTable () {
        BufferedReader br = null;

        try {
            br = Gdx.files.local("highscore.txt").reader(8192);
            String data = br.readLine();
            topPlayerName = data.split(" ")[0];
            topPlayerScore = Integer.parseInt(data.split(" ")[1]);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createTable () {
        Writer writer = null;
        try {
            writer = Gdx.files.local("highscore.txt").writer(false); // полная перезапись файла
            writer.write("unknown 0");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void updateTable (String name, int score) {
        if (score > topPlayerScore) {
            Writer writer = null;
            try {
                writer = Gdx.files.local("highscore.txt").writer(false); // полная перезапись файла
                writer.write(name + " " + score);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        loadTable();
    }
}

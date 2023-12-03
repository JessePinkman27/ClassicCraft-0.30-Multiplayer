package net.PeytonPlayz585;

import java.io.*;

import net.io.LevelUtils;

public class MinecraftMain {
    public static void main(String[] args) {
        System.out.println("Starting server!");

        try {
            if(!LevelUtils.levelExists()) {
                LevelUtils.generateLevel();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
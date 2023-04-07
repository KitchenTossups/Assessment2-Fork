package com.oshewo.panic;

import com.badlogic.gdx.backends.lwjgl3.*;

import java.util.Arrays;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument

/**
 * The type Desktop launcher.
 */
public class DesktopLauncher {

    private static boolean verbose = false;

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setWindowedMode(1280, 720);
        config.useVsync(true);
        config.setTitle("Piazza Panic");
        argumentHandler(args);
        if (verbose)
            System.out.println(Arrays.toString(args));
        config.setWindowListener(new Lwjgl3WindowListener() {
            @Override
            public void created(Lwjgl3Window window) {

            }

            @Override
            public void iconified(boolean isIconified) {

            }

            @Override
            public void maximized(boolean isMaximized) {

            }

            @Override
            public void focusLost() {

            }

            @Override
            public void focusGained() {

            }

            @Override
            public boolean closeRequested() {
                System.exit(0);
                return true;
            }

            @Override
            public void filesDropped(String[] files) {

            }

            @Override
            public void refreshRequested() {

            }
        });
        new Lwjgl3Application(new PiazzaPanic(verbose), config);
    }

    private static void argumentHandler(String[] args) {
        for (String arg : args) {
            arg = arg.replaceAll("-", "");
            String[] split = arg.split("=");
            if (split.length > 2)
                throw new RuntimeException("Invalid program argument!");
            if (split[0].equals("v")) {
                verbose = true;
            }
        }
    }
}

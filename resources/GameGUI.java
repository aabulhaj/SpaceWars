package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;

public class GameGUI {
    public static final Image SPACESHIP_IMAGE = createImageIcon("spaceship3.gif", "");
    public static final Image SPACESHIP_IMAGE_SHIELD = createImageIcon("spaceship3_shield.gif", "");
    public static final Image ENEMY_SPACESHIP_IMAGE = createImageIcon("spaceship2.gif", "");
    public static final Image ENEMY_SPACESHIP_IMAGE_SHIELD = createImageIcon("spaceship2_shield.gif", "");
    public static final Image SHOT_IMAGE = createImageIcon("shot.gif", "");

    private static final int FRAMES_PER_SEC = 50;
    private static final int DISPLAY_SIZE = 700;

    private JFrame frame;
    private GamePanel panel;
    private JLabel label;
    private KeyboardListener listener;
    private long nextFrameTime;

    public GameGUI() {
        listener = new KeyboardListener();

        frame = new JFrame();
        frame.setIconImage(SPACESHIP_IMAGE);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new GamePanel(DISPLAY_SIZE);
        label = new JLabel();
        label.setText("Test");

        frame.getContentPane().add(panel);
        frame.getContentPane().add(label, "South");
        frame.addKeyListener(listener);
        frame.addFocusListener(listener);

        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        new Thread(new AutoDisposal(Thread.currentThread(), frame)).start();

        nextFrameTime = System.currentTimeMillis();
    }

    public void drawBufferToScreen() {
        panel.postBuffer();
        waitForNextFrame();
        panel.repaint();
    }

    public boolean isLeftPressed() {
        return listener.isKeyPressed(KeyEvent.VK_LEFT) && !listener.isKeyPressed(KeyEvent.VK_RIGHT);
    }

    public boolean isRightPressed() {
        return listener.isKeyPressed(KeyEvent.VK_RIGHT) && !listener.isKeyPressed(KeyEvent.VK_LEFT);
    }

    public boolean isUpPressed() {
        return listener.isKeyPressed(KeyEvent.VK_UP);
    }

    public boolean isShotPressed() {
        return listener.isKeyPressed(KeyEvent.VK_D);
    }

    public boolean isTeleportPressed() {
        return listener.isKeyPressed(KeyEvent.VK_A);
    }

    public boolean isShieldsPressed() {
        return listener.isKeyPressed(KeyEvent.VK_S);
    }

    public boolean isEscPressed() {
        return listener.isKeyPressed(KeyEvent.VK_ESCAPE);
    }

    public void addImageToBuffer(Image img, Physics position) {
        panel.addToBuffer(img, position);
    }

    public void setText(String text) {
        label.setText(text);
    }

    private void waitForNextFrame() {
        long curTime = System.currentTimeMillis();
        while (curTime < nextFrameTime) {
            try {
                Thread.sleep(nextFrameTime - curTime);
            } catch (InterruptedException e) {
            }
            curTime = System.currentTimeMillis();
        }
        nextFrameTime += 1000 / FRAMES_PER_SEC;
    }

    private static Image createImageIcon(String path, String description) {
        URL imgURL = GamePanel.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description).getImage();
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
}

package resources;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

class GamePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private ArrayList<Physics> positions, posBuffer;

    private ArrayList<Image> images, imgBuffer;

    private int displaySize;

    public GamePanel(int displaySize) {
        super();

        setDoubleBuffered(true);
        setBackground(Color.BLACK);

        this.displaySize = displaySize;
        positions = new ArrayList<>();
        posBuffer = new ArrayList<>();
        images = new ArrayList<>();
        imgBuffer = new ArrayList<>();

        setPreferredSize(new Dimension(displaySize, displaySize));
    }

    public synchronized void addToBuffer(Image img, Physics pos) {
        imgBuffer.add(img);
        posBuffer.add(pos);
    }

    public synchronized void postBuffer() {
        ArrayList<Physics> tempPos = positions;
        ArrayList<Image> tempImg = images;

        positions = posBuffer;
        images = imgBuffer;

        imgBuffer = tempImg;
        posBuffer = tempPos;

        imgBuffer.clear();
        posBuffer.clear();
    }

    public synchronized void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < images.size(); i++) {
            drawGameObject(g2d, images.get(i), positions.get(i));
        }
        g2d.draw(new Rectangle(0, 0, displaySize, displaySize));
    }

    private void drawGameObject(Graphics2D g2d, Image image,
                                Physics pos) {
        AffineTransform saveTransform = g2d.getTransform();
        AffineTransform trans = new AffineTransform();

        trans.translate(pos.getX() * displaySize, (1 - pos.getY()) * displaySize);
        trans.rotate(-pos.getAngle() + Math.PI / 2);

        g2d.setTransform(trans);
        g2d.drawImage(image, -image.getWidth(null) / 2, -image.getHeight(null) / 2, null);

        g2d.setTransform(saveTransform);
    }
}

package pacman_demo_v2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Ghost {

    protected int x, y;
    protected int prevX, prevY;
    protected int dx, dy;
    protected BufferedImage image;

    protected BufferedImage upImage1, upImage2;
    protected BufferedImage downImage1, downImage2;
    protected BufferedImage leftImage1, leftImage2;
    protected BufferedImage rightImage1, rightImage2;

    protected int upStatus = 0, downStatus = 0, leftStatus = 0, rightStatus = 0;
    protected final int TILE_SIZE;
    protected final int[][] moveDeltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Lên, Xuống, Trái, Phải

    public Ghost(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
        this.dx = -1;
        this.dy = 0;
    }

    /**
     * Load ảnh con ma từ đường dẫn
     */
    public void updateImage(int frame) {
        
    }

    protected BufferedImage loadImage(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                return ImageIO.read(url);
            } else {
                System.err.println("Image not found: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Di chuyển con ma
     */
    public void move(int[][] mapData) {
        prevX = x;
        prevY = y;

        Collections.shuffle(Arrays.asList(moveDeltas));

        int newX = x + dx;
        int newY = y + dy;

        if (isValidMove(newX, newY, mapData)) {
            x = newX;
            y = newY;
            return;
        }

        for (int[] d : moveDeltas) {
            // tránh quay đầu
            if (d[0] == -dx && d[1] == -dy) {
                continue;
            }

            newX = x + d[0];
            newY = y + d[1];
            if (isValidMove(newX, newY, mapData)) {
                dx = d[0];
                dy = d[1];
                x = newX;
                y = newY;
                return;
            }
        }
    }

    protected boolean isValidMove(int x, int y, int[][] map) {
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return false;
        }
        int tile = map[x][y];
        return tile != 0 && tile != 5; // Không vào tường
    }

    /**
     * Vẽ hình con ma
     */
    public void draw(Graphics2D g2d) {
        if (image != null) {
            g2d.drawImage(image, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
    }

    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = -1;
        this.dy = 0;
    }

    // Getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getPrevX() {
        return prevX;
    }

    public int getPrevY() {
        return prevY;
    }
}

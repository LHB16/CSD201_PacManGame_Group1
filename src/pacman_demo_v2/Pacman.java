// Pacman.java
package pacman_demo_v2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

public class Pacman {

    private int x, y;
    private int dx, dy;
    private int req_dx, req_dy;
    private int prevX, prevY;

    private BufferedImage image;
    private BufferedImage upImage1, upImage2;
    private BufferedImage downImage1, downImage2;
    private BufferedImage leftImage1, leftImage2;
    private BufferedImage rightImage1, rightImage2;

    private int leftStatus = 0;
    private int rightStatus = 0;
    private int upStatus = 0;
    private int downStatus = 0;

    private final int TILE_SIZE;

    public Pacman(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
        this.dx = 0;
        this.dy = 0;
        this.req_dx = 0;
        this.req_dy = 0;
        loadImages();
        this.image = rightImage1; // Hình ảnh ban đầu
    }

    private void loadImages() {
        upImage1 = loadImage("/img/pacman/pacnu1tren.png");
        upImage2 = loadImage("/img/pacman/pacnu2tren.png");
        downImage1 = loadImage("/img/pacman/pacnu1duoi.png");
        downImage2 = loadImage("/img/pacman/pacnu2duoi.png");
        leftImage1 = loadImage("/img/pacman/pacnu1trai.png");
        leftImage2 = loadImage("/img/pacman/pacnu2trai.png");
        rightImage1 = loadImage("/img/pacman/pacnu1phai.png");
        rightImage2 = loadImage("/img/pacman/pacnu2phai.png");
    }

    private BufferedImage loadImage(String path) {
        try {
            URL imageUrl = getClass().getResource(path);
            if (imageUrl != null) {
                return ImageIO.read(imageUrl);
            } else {
                System.err.println("Error loading image: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateImage(int cnt) {
        if (dx == -1) { // Left
            if (cnt % 4 == 0) leftStatus ^= 1;
            image = (leftStatus % 2 == 0) ? leftImage1 : leftImage2;
        } else if (dx == 1) { // Right
            if (cnt % 4 == 0) rightStatus ^= 1;
            image = (rightStatus % 2 == 0) ? rightImage1 : rightImage2;
        } else if (dy == -1) { // Up
            if (cnt % 4 == 0) upStatus ^= 1;
            image = (upStatus % 2 == 0) ? upImage1 : upImage2;
        } else if (dy == 1) { // Down
            if (cnt % 4 == 0) downStatus ^= 1;
            image = (downStatus % 2 == 0) ? downImage1 : downImage2;
        }
    }

    public void move(int[][] mapData, int totalDots) {
        prevX = x;
        prevY = y;

        // Thử di chuyển theo hướng yêu cầu mới
        int newX_req = x + req_dx;
        int newY_req = y + req_dy;

        if (isValidMove(newX_req, newY_req, mapData, totalDots)) {
            dx = req_dx;
            dy = req_dy;
        }

        // Di chuyển theo hướng chính thức (đã được xác thực)
        int newX = x + dx;
        int newY = y + dy;

        if (isValidMove(newX, newY, mapData, totalDots)) {
            x = newX;
            y = newY;
        }
    }

    private boolean isValidMove(int x, int y, int[][] mapData, int totalDots) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            int tile = mapData[x][y];
            if (totalDots > 0) {
                return tile != 0 && tile != 5; // Không thể đi vào tường hoặc tường ẩn
            } else {
                return tile != 0; // Khi hết chấm, có thể đi qua tường ẩn
            }
        }
        return false;
    }
    
    public void draw(Graphics2D g2d) {
        if (image != null) {
            g2d.drawImage(image, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        }
    }
    
    public void resetPosition(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.dx = 0;
        this.dy = 0;
        this.req_dx = 0;
        this.req_dy = 0;
        this.image = rightImage1;
    }

    // Getters and Setters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getPrevX() { return prevX; }
    public int getPrevY() { return prevY; }

    public void setRequestedDirection(int req_dx, int req_dy) {
        this.req_dx = req_dx;
        this.req_dy = req_dy;
    }
}
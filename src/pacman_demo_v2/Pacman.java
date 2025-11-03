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
    // Mỗi hướng có 3 frame
    private BufferedImage upImage0, upImage1, upImage2;
    private BufferedImage downImage0, downImage1, downImage2;
    private BufferedImage leftImage0, leftImage1, leftImage2;
    private BufferedImage rightImage0, rightImage1, rightImage2;

    private int upStatus = 0, downStatus = 0, leftStatus = 0, rightStatus = 0;

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
        this.image = rightImage1; // Hình ảnh ban đầu (miệng đóng, hướng phải)
    }

    /**
     * Tải 3 ảnh cho mỗi hướng
     */
    private void loadImages() {
        upImage0 = loadImage("/img/pacman/pacnu0tren.png");
        upImage1 = loadImage("/img/pacman/pacnu1tren.png");
        upImage2 = loadImage("/img/pacman/pacnu2tren.png");

        // Hướng xuống
        downImage0 = loadImage("/img/pacman/pacnu0duoi.png");
        downImage1 = loadImage("/img/pacman/pacnu1duoi.png");
        downImage2 = loadImage("/img/pacman/pacnu2duoi.png");

        // Hướng trái
        leftImage0 = loadImage("/img/pacman/pacnu0trai.png");
        leftImage1 = loadImage("/img/pacman/pacnu1trai.png");
        leftImage2 = loadImage("/img/pacman/pacnu2trai.png");

        // Hướng phải
        rightImage0 = loadImage("/img/pacman/pacnu0phai.png");
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

    /**
     * Cập nhật frame ảnh theo hướng di chuyển
     */
    public void updateImage(int cnt) {
        int frame = (cnt / 4) % 3; // đổi frame mỗi 4 tick, xoay giữa 0-1-2

        if (dx == -1) { // LEFT
            if (frame == 0) {
                image = leftImage0;
            } else if (frame == 1) {
                image = leftImage1;
            } else {
                image = leftImage2;
            }

        } else if (dx == 1) { // RIGHT
            if (frame == 0) {
                image = rightImage0;
            } else if (frame == 1) {
                image = rightImage1;
            } else {
                image = rightImage2;
            }

        } else if (dy == -1) { // UP
            if (frame == 0) {
                image = upImage0;
            } else if (frame == 1) {
                image = upImage1;
            } else {
                image = upImage2;
            }

        } else if (dy == 1) { // DOWN
            if (frame == 0) {
                image = downImage0;
            } else if (frame == 1) {
                image = downImage1;
            } else {
                image = downImage2;
            }
        }
    }

    /**
     * Di chuyển Pac-Man theo bản đồ
     */
    public void move(int[][] mapData, int totalDots) {
        prevX = x;
        prevY = y;


        // Kiểm tra xem hướng YÊU CẦU có hợp lệ không
        dx = req_dx;
        dy = req_dy;

        int newX = x + dx;
        int newY = y + dy;


        // Di chuyển chính thức

        if (isValidMove(newX, newY, mapData, totalDots)) {
            x = newX;
            y = newY;
        }
    }

    private boolean isValidMove(int x, int y, int[][] mapData, int totalDots) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            int tile = mapData[x][y];
            if (totalDots > 0) {
                return tile != 0 && tile != 5; // tường & tường ẩn
            } else {
                return tile != 0;
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

    // Getters / Setters
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

    public void setRequestedDirection(int req_dx, int req_dy) {
        this.req_dx = req_dx;
        this.req_dy = req_dy;
    }
}

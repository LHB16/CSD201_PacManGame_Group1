// Ghost.java
package pacman_demo_v2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.util.Random;

public class Ghost {
    private int x, y;
    private int prevX, prevY;
    int dx; // Hướng di chuyển hiện tại
    int dy; // Hướng di chuyển hiện tại

    BufferedImage image;
    BufferedImage upImage1;
    BufferedImage upImage2;
    BufferedImage downImage1;
    BufferedImage downImage2;
    BufferedImage leftImage1;
    BufferedImage leftImage2;
    BufferedImage rightImage1;
    BufferedImage rightImage2;
    
    int leftStatus = 0;
    int rightStatus = 0;
    int upStatus = 0;
    int downStatus = 0;

    private final int TILE_SIZE;
    private final int[][] moveDeltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Lên, Xuống, Trái, Phải
    
    protected Random rand;
    
    public Ghost(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
        this.dx = -1; // Bắt đầu đi lên (thay đổi hàng)
        this.dy = 0; 
        loadImages();
        this.image = upImage1;
        this.rand = new Random();
    }

    private void loadImages() {
        upImage1 = loadImage("/img/ghost/red/redup1.png");
        upImage2 = loadImage("/img/ghost/red/redup2.png");
        downImage1 = loadImage("/img/ghost/red/reddown1.png");
        downImage2 = loadImage("/img/ghost/red/reddown2.png");
        leftImage1 = loadImage("/img/ghost/red/redleft1.png");
        leftImage2 = loadImage("/img/ghost/red/redleft2.png");
        rightImage1 = loadImage("/img/ghost/red/redright1.png");
        rightImage2 = loadImage("/img/ghost/red/redright2.png");
    }

    public BufferedImage loadImage(String path) {
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

    /**
     * Di chuyển con ma
     * Logic mới: Ưu tiên rẽ ngẫu nhiên tại các ngã rẽ.
     * @param mapData
     */
    public void move(int[][] mapData) {
        prevX = x;
        prevY = y;

        List<int[]> validMoves = new ArrayList<>();

        // 1. Tìm tất cả các hướng đi hợp lệ (không phải là tường)
        //    NGOẠI TRỪ việc quay đầu 180 độ.
        for (int[] d : moveDeltas) {
            // Bỏ qua hướng đi ngược lại (quay đầu)
            if (d[0] == -dx && d[1] == -dy) {
                continue;
            }

            // Kiểm tra xem nước đi có hợp lệ không (không phải tường)
            if (isValidMove(x + d[0], y + d[1], mapData)) {
                validMoves.add(d);
            }
        }

        // 2. Xử lý các trường hợp
        if (validMoves.isEmpty()) {
            // --- Trường hợp 0: Ngõ cụt ---
            // Buộc phải quay đầu lại
            int newX = x - dx;
            int newY = y - dy;

            if (isValidMove(newX, newY, mapData)) { // Kiểm tra cho chắc
                dx = -dx;
                dy = -dy;
                x = newX;
                y = newY;
            }
            // Nếu không làm gì cả, con ma sẽ bị kẹt (trường hợp map lỗi)

        } else if (validMoves.size() == 1) {
            // --- Trường hợp 1: Hành lang / Chỉ có 1 lối rẽ ---
            // Chỉ có một lựa chọn duy nhất, đi theo hướng đó
            int[] chosenMove = validMoves.get(0);
            dx = chosenMove[0];
            dy = chosenMove[1];
            x = x + dx;
            y = y + dy;

        } else {
            // --- Trường hợp 2: Ngã rẽ (2 lối đi trở lên) ---
            // Đây là lúc áp dụng "xác suất" theo yêu cầu của bạn.
            // Chọn ngẫu nhiên một trong các hướng đi hợp lệ.
            int[] chosenMove = validMoves.get(rand.nextInt(validMoves.size()));
            dx = chosenMove[0];
            dy = chosenMove[1];
            x = x + dx;
            y = y + dy;
        }
    }

    private boolean isValidMove(int x, int y, int[][] mapData) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            int tile = mapData[x][y];
            return tile != 0 && tile != 5; // Ma không thể đi vào tường
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
        this.dx = -1; // Đi lên
        this.dy = 0;
        this.image = upImage1;
    }

    // Getters
    public int getX() { return x; }
    public int getY() { return y; }
    public int getPrevX() { return prevX; }
    public int getPrevY() { return prevY; }
}
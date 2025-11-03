// Ghost.java
package pacman_demo_v2;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;

public class Ghost {
    private int x, y;
    private int prevX, prevY;
    private int dx, dy; // Hướng di chuyển hiện tại

<<<<<<< Updated upstream
    private BufferedImage image;
    private BufferedImage upImage1, upImage2;
    private BufferedImage downImage1, downImage2;
    private BufferedImage leftImage1, leftImage2;
    private BufferedImage rightImage1, rightImage2;
    
    private int leftStatus = 0;
    private int rightStatus = 0;
    private int upStatus = 0;
    private int downStatus = 0;
=======
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
    
    protected Random rand;
>>>>>>> Stashed changes

    private final int TILE_SIZE;
    private final int[][] moveDeltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Lên, Xuống, Trái, Phải
    
    public Ghost(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
<<<<<<< Updated upstream
        this.dx = -1; // Bắt đầu đi lên (thay đổi hàng)
        this.dy = 0; 
        loadImages();
        this.image = upImage1;
=======
        this.dx = -1;
        this.dy = 0;
        this.rand = new Random();
>>>>>>> Stashed changes
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
    
<<<<<<< Updated upstream
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

    public void move(int[][] mapData) {
        prevX = x;
        prevY = y;
        
        Collections.shuffle(Arrays.asList(moveDeltas));
        
        // Ưu tiên đi thẳng nếu có thể
        int newX = x + dx;
        int newY = y + dy;
        if (isValidMove(newX, newY, mapData)) {
            x = newX;
            y = newY;
            return;
        }
        
        // Nếu không đi thẳng được, tìm hướng khác
        for (int[] delta : moveDeltas) {
            // Không quay đầu lại
            if (delta[0] == -dx && delta[1] == -dy) {
                continue;
            }
            newX = x + delta[0];
            newY = y + delta[1];
            if (isValidMove(newX, newY, mapData)) {
                dx = delta[0];
                dy = delta[1];
                x = newX;
                y = newY;
                return;
=======
    /**
     * Di chuyển con ma
     * Logic mới: Ưu tiên rẽ ngẫu nhiên tại các ngã rẽ.
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
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
    private boolean isValidMove(int x, int y, int[][] mapData) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            int tile = mapData[x][y];
            return tile != 0 && tile != 5; // Ma không thể đi vào tường
=======
//    /**
//     * Cũ.
//     * Di chuyển con ma.
//     * Logic chạm tường mới rẽ.
//     */
//    public void move(int[][] mapData) {
//        prevX = x;
//        prevY = y;
//
//        Collections.shuffle(Arrays.asList(moveDeltas));
//
//        int newX = x + dx;
//        int newY = y + dy;
//
//        if (isValidMove(newX, newY, mapData)) {
//            x = newX;
//            y = newY;
//            return;
//        }
//
//        for (int[] d : moveDeltas) {
//            // tránh quay đầu
//            if (d[0] == -dx && d[1] == -dy) {
//                continue;
//            }
//
//            newX = x + d[0];
//            newY = y + d[1];
//            if (isValidMove(newX, newY, mapData)) {
//                dx = d[0];
//                dy = d[1];
//                x = newX;
//                y = newY;
//                return;
//            }
//        }
//    }

    protected boolean isValidMove(int x, int y, int[][] map) {
        if (x < 0 || y < 0 || x >= map.length || y >= map[0].length) {
            return false;
>>>>>>> Stashed changes
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
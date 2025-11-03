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


    private final int TILE_SIZE;
    private final int[][] moveDeltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Lên, Xuống, Trái, Phải

    public Ghost(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
        this.dx = -1;
        this.dy = 0;
        this.rand = new Random();

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

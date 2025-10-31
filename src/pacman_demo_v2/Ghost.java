// Ghost.java
// cmt
package pacman_demo_v2;


    private BufferedImage image;
    private BufferedImage upImage1, upImage2;
    private BufferedImage downImage1, downImage2;
    private BufferedImage leftImage1, leftImage2;
    private BufferedImage rightImage1, rightImage2;

public class Ghost {
    private int x, y;
    private int prevX, prevY;
    private int dx, dy; // Hướng di chuyển hiện tại

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
    private final int[][] moveDeltas = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Lên, Xuống, Trái, Phải
    
    public Ghost(int startX, int startY, int tileSize) {
        this.x = startX;
        this.y = startY;
        this.TILE_SIZE = tileSize;
        this.dx = -1; // Bắt đầu đi lên (thay đổi hàng)
        this.dy = 0; 
        loadImages();
        this.image = upImage1;
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
            }
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
// GhostYellow.java
package pacman_demo_v2;

public class GhostYellow extends Ghost {

    public GhostYellow(int startX, int startY, int tileSize) {
        super(startX, startY, tileSize);
        // Khởi tạo hướng sang phải
        this.dx = 0;
        this.dy = 1;
        loadImages();
        this.image = rightImage1;
    }

    /**
     * Load sprites cho ma vàng (đổi 'yellow' -> 'orange' nếu bạn lưu thư mục là orange)
     */
    private void loadImages() {
        ghostv2 = loadImage("/img/ghost/ghostv2.png");

        upImage1 = loadImage("/img/ghost/yellow/yellowup1.png");
        upImage2 = loadImage("/img/ghost/yellow/yellowup2.png");
        downImage1 = loadImage("/img/ghost/yellow/yellowdown1.png");
        downImage2 = loadImage("/img/ghost/yellow/yellowdown2.png");
        leftImage1 = loadImage("/img/ghost/yellow/yellowleft1.png");
        leftImage2 = loadImage("/img/ghost/yellow/yellowleft2.png");
        rightImage1 = loadImage("/img/ghost/yellow/yellowright1.png");
        rightImage2 = loadImage("/img/ghost/yellow/yellowright2.png");
    }

    /**
     * Cập nhật frame ảnh theo hướng di chuyển
     */
    @Override
    public void updateImage(int frame, int t) {
        if (t == 1) {
            image = ghostv2;
            return;
        }

     
        
        if (dx == -1) {                     // Left
            if (frame % 4 == 0) {
                leftStatus ^= 1;
            }
            image = (leftStatus == 0) ? leftImage1 : leftImage2;
        } else if (dx == 1) {               // Right
            if (frame % 4 == 0) {
                rightStatus ^= 1;
            }
            image = (rightStatus == 0) ? rightImage1 : rightImage2;
        } else if (dy == -1) {              // Up
            if (frame % 4 == 0) {
                upStatus ^= 1;
            }
            image = (upStatus == 0) ? upImage1 : upImage2;
        } else if (dy == 1) {               // Down
            if (frame % 4 == 0) {
                downStatus ^= 1;
            }
            image = (downStatus == 0) ? downImage1 : downImage2;
        }
    }

    /**
     * Reset về vị trí + hướng mặc định (sang phải)
     */
    @Override
    public void resetPosition(int startX, int startY) {
        super.resetPosition(startX, startY);
        this.dx = 0;
        this.dy = 1;
        this.image = rightImage1;
    }
}

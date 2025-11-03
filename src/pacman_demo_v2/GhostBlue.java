// GhostBlue.java
package pacman_demo_v2;

public class GhostBlue extends Ghost {

    public GhostBlue(int startX, int startY, int tileSize) {
        super(startX, startY, tileSize);
        // Hướng khởi tạo: đi sang phải
        this.dx = 1;
        this.dy = 0;
        loadImages();
        this.image = rightImage1;
    }

    /** Load sprite cho ma xanh dương */
    private void loadImages() {
        upImage1    = loadImage("/img/ghost/blue/blueup1.png");
        upImage2    = loadImage("/img/ghost/blue/blueup2.png");
        downImage1  = loadImage("/img/ghost/blue/bluedown1.png");
        downImage2  = loadImage("/img/ghost/blue/bluedown2.png");
        leftImage1  = loadImage("/img/ghost/blue/blueleft1.png");
        leftImage2  = loadImage("/img/ghost/blue/blueleft2.png");
        rightImage1 = loadImage("/img/ghost/blue/blueright1.png");
        rightImage2 = loadImage("/img/ghost/blue/blueright2.png");
    }

    /** Cập nhật frame ảnh theo hướng di chuyển */
    @Override
    public void updateImage(int frame) {
        if (dx == -1) {                     // Left
            if (frame % 4 == 0) leftStatus ^= 1;
            image = (leftStatus == 0) ? leftImage1 : leftImage2;
        } else if (dx == 1) {               // Right
            if (frame % 4 == 0) rightStatus ^= 1;
            image = (rightStatus == 0) ? rightImage1 : rightImage2;
        } else if (dy == -1) {              // Up
            if (frame % 4 == 0) upStatus ^= 1;
            image = (upStatus == 0) ? upImage1 : upImage2;
        } else if (dy == 1) {               // Down
            if (frame % 4 == 0) downStatus ^= 1;
            image = (downStatus == 0) ? downImage1 : downImage2;
        }
    }

    /** Reset về vị trí + hướng mặc định (đi sang phải) */
    @Override
    public void resetPosition(int startX, int startY) {
        super.resetPosition(startX, startY);
        this.dx = 1;
        this.dy = 0;
        this.image = rightImage1;
    }
}

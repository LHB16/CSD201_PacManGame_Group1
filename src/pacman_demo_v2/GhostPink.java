// GhostPink.java
package pacman_demo_v2;

public class GhostPink extends Ghost {

    public GhostPink(int startX, int startY, int tileSize) {
        super(startX, startY, tileSize);
        // Hướng khởi tạo: đi lên
        this.dx = 0;
        this.dy = -1;
        loadImages();
        this.image = upImage1;
    }

    /** Load sprites cho ma hồng */
    private void loadImages() {
        upImage1    = loadImage("/img/ghost/pink/pinkup1.png");
        upImage2    = loadImage("/img/ghost/pink/pinkup2.png");
        downImage1  = loadImage("/img/ghost/pink/pinkdown1.png");
        downImage2  = loadImage("/img/ghost/pink/pinkdown2.png");
        leftImage1  = loadImage("/img/ghost/pink/pinkleft1.png");
        leftImage2  = loadImage("/img/ghost/pink/pinkleft2.png");
        rightImage1 = loadImage("/img/ghost/pink/pinkright1.png");
        rightImage2 = loadImage("/img/ghost/pink/pinkright2.png");
    }

    /** Cập nhật frame theo hướng di chuyển */
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

    /** Reset về vị trí + hướng mặc định (đi lên) */
    @Override
    public void resetPosition(int startX, int startY) {
        super.resetPosition(startX, startY);
        this.dx = 0;
        this.dy = -1;
        this.image = upImage1;
    }
}

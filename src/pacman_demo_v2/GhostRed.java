// GhostRed.java
package pacman_demo_v2;

public class GhostRed extends Ghost {

    public GhostRed(int startX, int startY, int tileSize) {
        super(startX, startY, tileSize);
        // Hướng khởi tạo của Red (giữ như code cũ): đi sang trái
        this.dx = -1;
        this.dy = 0;
        loadImages();
        this.image = leftImage1; // ảnh ban đầu phù hợp hướng trái
    }

    /**
     * Load sprites cho ma đỏ
     */
    private void loadImages() {
        ghostv2 = loadImage("/img/ghost/ghostv2.png");

        upImage1 = loadImage("/img/ghost/red/redup1.png");
        upImage2 = loadImage("/img/ghost/red/redup2.png");
        downImage1 = loadImage("/img/ghost/red/reddown1.png");
        downImage2 = loadImage("/img/ghost/red/reddown2.png");
        leftImage1 = loadImage("/img/ghost/red/redleft1.png");
        leftImage2 = loadImage("/img/ghost/red/redleft2.png");
        rightImage1 = loadImage("/img/ghost/red/redright1.png");
        rightImage2 = loadImage("/img/ghost/red/redright2.png");
    }

    /**
     * Đổi frame theo hướng di chuyển (giống GhostBlue)
     */
    @Override
    public void updateImage(int frame, int t) {
        if (t == 1) {
            image = ghostv2;
            return;
        }

  
        if (dx == -1) {                    // Left
            if (frame % 4 == 0) {
                leftStatus ^= 1;
            }
            image = (leftStatus == 0) ? leftImage1 : leftImage2;
        } else if (dx == 1) {              // Right
            if (frame % 4 == 0) {
                rightStatus ^= 1;
            }
            image = (rightStatus == 0) ? rightImage1 : rightImage2;
        } else if (dy == -1) {             // Up
            if (frame % 4 == 0) {
                upStatus ^= 1;
            }
            image = (upStatus == 0) ? upImage1 : upImage2;
        } else if (dy == 1) {              // Down
            if (frame % 4 == 0) {
                downStatus ^= 1;
            }
            image = (downStatus == 0) ? downImage1 : downImage2;
        }
    }

    /**
     * Nếu muốn khác logic reset, override ở đây (giữ như cũ)
     */
    @Override
    public void resetPosition(int startX, int startY) {
        super.resetPosition(startX, startY);
        this.dx = -1;  // giữ hướng trái khi reset
        this.dy = 0;
        this.image = leftImage1;
    }
}

package pacman_demo_v2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BoardPanel extends JPanel implements ActionListener {

    private PacManMainGame_Frame gameFrame;
    private final int TILE_SIZE = 24;

    // --- Game Objects ---
    private Pacman pacman;

    private int cntGhost = 3;
    private Ghost[] ghosts = new Ghost[105];
    //private redGhost

    // --- Map and Game State ---
    private int mapData[][];
    private int score = 0;
    public int totalLives = 100;
    private int totalDots = 0;
    private int cherryStatus = 0;
    private Timer gameLoopTimer;
    private Timer clockTimer;
    private int seconds = 0, minutes = 0, hours = 0;

    // --- Images ---
    private BufferedImage cherryImage, cherryImage1, cherryImage2;
    private BufferedImage ballImage;
    private BufferedImage verticalImage, horizontalImage;
    private BufferedImage cornerImage1, cornerImage2, cornerImage3, cornerImage4;
    private BufferedImage intersectionImage1, intersectionImage2, intersectionImage3, intersectionImage4;
    private BufferedImage UImage1, UImage2, UImage3, UImage4;

    // --- Super Point ---
    private int superPointX = -1;
    private int superPointY = -1;
    private int superPointStatus = 0;
    private ArrayList<Block> superPoint = new ArrayList<>();
    Random rand = new Random();

    private int cnt = 0; // Counter for animations

    private static final int[][] ORIGINAL_MAP = {
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 3, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 0, 3, 3, 3, 0, 3, 3, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 2, 0, 2, 0, 2, 0, 3, 0, 3, 3, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0},
        {0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0},
        {0, 3, 0, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0},
        {0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0},
        {0, 0, 0, 3, 0, 0, 0, 3, 3, 3, 3, 3, 0, 0, 0, 6, 0, 0, 0, 3, 0},
        {0, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0, 3, 0, 3, 3, 3, 0, 3, 3, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0, 3, 0},
        {0, 3, 0, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0, 0, 0, 3, 0},
        {0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 0, 0, 5, 0, 0, 0, 3, 0, 0, 0, 3, 0, 3, 0},
        {0, 3, 0, 3, 0, 3, 0, 2, 0, 2, 0, 2, 0, 3, 0, 3, 3, 3, 0, 3, 0},
        {0, 3, 3, 3, 3, 3, 0, 2, 2, 2, 2, 2, 0, 3, 3, 3, 0, 3, 3, 3, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public BoardPanel(PacManMainGame_Frame frame) {
        this.gameFrame = frame;
        this.setBackground(Color.BLACK);

        loadMapImages();

        mapData = new int[ORIGINAL_MAP.length][];
        for (int i = 0; i < ORIGINAL_MAP.length; i++) {
            mapData[i] = ORIGINAL_MAP[i].clone();
        }

        // --- Initialize Game Objects ---
        pacman = new Pacman(10, 15, TILE_SIZE); // (row, col)

        for (int i = 0; i < 4; ++i) {
            if (i % 4 == 0) {
                ghosts[i] = new GhostRed(10, 9, TILE_SIZE);
            }

            if (i % 4 == 1) {
                ghosts[i] = new GhostBlue(10, 9, TILE_SIZE);
            }

            if (i % 4 == 2) {
                ghosts[i] = new GhostYellow(10, 9, TILE_SIZE);
            }

            if (i % 4 == 3) {
                ghosts[i] = new GhostPink(10, 9, TILE_SIZE);
            }
        }
        countInitialDots();

        addKeyListener(new TAdapter());
        setFocusable(true);

        gameLoopTimer = new Timer(50, this); // Vòng lặp game nhanh hơn
        clockTimer = new Timer(1000, e -> updateGameTime());
        startTimers();
    }

    private void countInitialDots() {
        totalDots = 0;
        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                if (mapData[x][y] == 3) {
                    totalDots++;
                }
            }
        }
    }

    private void updateGameTime() {
        seconds++;
        if (seconds >= 60) {
            seconds = 0;
            minutes++;
            if (minutes >= 60) {
                minutes = 0;
                hours++;
            }
        }
        String timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        gameFrame.lbCountTime.setText(timeString);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Tăng bộ đếm và cập nhật hình ảnh
        cnt++;
        updateCherryImage();
        pacman.updateImage(cnt);
        for (int i = 0; i < 4; ++i) {
            ghosts[i].updateImage(cnt);

        }

        drawMaze(g2d);
        pacman.draw(g2d);
        for (int i = 0; i < 4; ++i) {
            ghosts[i].draw(g2d);
        }
    }

    // ĐIỀU KHIỂN LOGIC GAME
    @Override
    public void actionPerformed(ActionEvent e) {
        // Pac-Man và ma di chuyển mỗi 4 tick (4 * 50ms = 200ms)
        if (cnt % 4 == 0) {
            movePacman();
            for (int i = 0; i < 4; ++i) {
                ghosts[i].move(mapData);
            }
        }
        checkCollision();
        repaint();
    }

    private void movePacman() {
        int oldX = pacman.getX();
        int oldY = pacman.getY();

        pacman.move(mapData, totalDots);

        int newX = pacman.getX();
        int newY = pacman.getY();

        // Kiểm tra xem Pacman có thực sự di chuyển không
        if (oldX != newX || oldY != newY) {
            // Ăn điểm
            if (mapData[newX][newY] == 3) {
                score += 10;
                gameFrame.lbCountScore.setText(String.valueOf(score));
                totalDots--;
            }
            if (mapData[newX][newY] == 2) {
                score += 1;
                gameFrame.lbCountScore.setText(String.valueOf(score));
            }
            // Ăn siêu điểm
            if (newX == superPointX && newY == superPointY) {
                score += 5;
                superPointX = -1;
                superPointY = -1;
            }

            // Thêm vị trí cũ vào danh sách có thể xuất hiện siêu điểm
            if (mapData[oldX][oldY] != 1) {
                superPoint.add(new Block(oldX, oldY));
            }

            // Cập nhật vị trí trên bản đồ logic
            mapData[oldX][oldY] = 1; // Vị trí cũ trở thành ô trống
            mapData[newX][newY] = 6; // Vị trí mới là Pacman
        }
    }

    private void checkCollision() {
        // Va chạm tại cùng một ô
        for (int i = 0; i < 4; ++i) {
            if (pacman.getX() == ghosts[i].getX() && pacman.getY() == ghosts[i].getY()) {
                gameFrame.pacmanHit();
                return;
            }
            // Va chạm khi đi ngược chiều nhau
            if (pacman.getX() == ghosts[i].getPrevX() && pacman.getY() == ghosts[i].getPrevY()
                    && ghosts[i].getX() == pacman.getPrevX() && ghosts[i].getY() == pacman.getPrevY()) {
                gameFrame.pacmanHit();
            }
        }
    }

    public int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    public void resetPosition() {
        // Đặt lại vị trí logic của Pacman cũ
        mapData[pacman.getX()][pacman.getY()] = 1;

        pacman.resetPosition(10, 15);
        for (int i = 0; i < 4; ++i) {
            ghosts[i].resetPosition(10, 9);
        }

        // Cập nhật vị trí mới trên mapData
        mapData[pacman.getX()][pacman.getY()] = 6;
    }

    public void replayGame() {
        totalLives = 3;

        for (int i = 0; i < ORIGINAL_MAP.length; i++) {
            mapData[i] = ORIGINAL_MAP[i].clone();
        }

        resetPosition();

        score = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        gameFrame.lbCountScore.setText("0");
        gameFrame.lbCountTime.setText("00:00:00");

        countInitialDots();
        startTimers();
    }

    public boolean checkWall(int x, int y) {
        if (!(x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length)) {
            return false;
        }
        
        if (mapData[x][y] == 5 && totalDots <= 0) {
            return false;
        }
        
        if (mapData[x][y] == 0 || mapData[x][y] == 5) {
            return true;
        }
        return false;
    }

    private void drawMaze(Graphics2D g2d) {
        
        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                int value = mapData[x][y];

                if (value == 0) { // Tường
                    //        (-1, 0)
                    // (0, -1) (0, 0) (0, 1)
                    //        (1, 0)
                    boolean check2 = checkWall(x, y + 1);
                    boolean check1 = checkWall(x + 1, y);
                    boolean check4 = checkWall(x, y - 1);
                    boolean check3 = checkWall(x - 1, y);

                    if (check1 == true && check2 == true && check3 == true && check4 == false) {
                        g2d.drawImage(intersectionImage4, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check2 == true && check3 == false && check4 == true) {
                        g2d.drawImage(intersectionImage3, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check2 == false && check3 == true && check4 == true) {
                        g2d.drawImage(intersectionImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == false && check2 == true && check3 == true && check4 == true) {
                        g2d.drawImage(intersectionImage1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check2 == true && check3 == true && check1 == false && check4 == false) {
                        g2d.drawImage(cornerImage1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check3 == true && check4 == true && check1 == false && check2 == false) {
                        g2d.drawImage(cornerImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check4 == true && check2 == false && check3 == false) {
                        g2d.drawImage(cornerImage3, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check2 == true && check3 == false && check4 == false) {
                        g2d.drawImage(cornerImage4, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check3 == true && check2 == false && check4 == false) {
                        g2d.drawImage(horizontalImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check2 == true && check4 == true && check1 == false && check3 == false) {
                        g2d.drawImage(verticalImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == false && check2 == true && check3 == false && check4 == false) {
                        g2d.drawImage(UImage1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == true && check2 == false && check3 == false && check4 == false) {
                        g2d.drawImage(UImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == false && check2 == false && check3 == false && check4 == true) {
                        g2d.drawImage(UImage3, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }

                    if (check1 == false && check2 == false && check3 == true && check4 == false) {
                        g2d.drawImage(UImage4, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        continue;
                    }
                }

                if (value == 2) { // Cherry
                    g2d.drawImage(cherryImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }

                if (value == 3) { // Dot
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fillOval(x * TILE_SIZE + 8, y * TILE_SIZE + 8, 4, 4);
                }

                if (value == 5) { // Hidden Wall
                    if (totalDots > 0) {
                        g2d.setColor(new Color(0, 0, 200));
                        g2d.drawImage(verticalImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }

                if (x == superPointX && y == superPointY) {
                    if (cnt % 4 == 0) {
                        superPointStatus ^= 1;
                    }

                    if (superPointStatus == 1) {
                        g2d.drawImage(ballImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    } else {
                        g2d.drawImage(ballImage, x * TILE_SIZE + 6, y * TILE_SIZE + 6, TILE_SIZE / 2, TILE_SIZE / 2, null);
                    }

                }

            }
        }

        if (cnt % 100 == 0) {
            int x = -1, y = -1;
            if (!superPoint.isEmpty()) {
                for (int i = 1; i <= 5; ++i) {
                    int id = rand.nextInt(superPoint.size());
                    x = superPoint.get(id).getX();
                    y = superPoint.get(id).getY();
                    System.out.println(manhattanDistance(x, y, pacman.getX(), pacman.getY()));
                    if (manhattanDistance(x, y, pacman.getX(), pacman.getY()) > 5) {
                        superPointX = x;
                        superPointY = y;
                        break;
                    }
                }
                System.out.println(superPointX + " " + superPointY);
            }
            if (cnt == 10000000) {
                cnt = 0;
            }
        }
    }

    private void loadMapImages() {
        cherryImage1 = loadImage("/img/food/cherry.png");
        cherryImage2 = loadImage("/img/food/cherry2.png");

        ballImage = loadImage("/img/food/ball.png");

        verticalImage = loadImage("/img/Map/vertical.png");
        horizontalImage = loadImage("/img/Map/horizontal.png");

        cornerImage1 = loadImage("/img/Map/corner1.png");
        cornerImage2 = loadImage("/img/Map/corner2.png");
        cornerImage3 = loadImage("/img/Map/corner3.png");
        cornerImage4 = loadImage("/img/Map/corner4.png");

        intersectionImage1 = loadImage("/img/Map/intersection1.png");
        intersectionImage2 = loadImage("/img/Map/intersection2.png");
        intersectionImage3 = loadImage("/img/Map/intersection3.png");
        intersectionImage4 = loadImage("/img/Map/intersection4.png");

        UImage1 = loadImage("/img/Map/U1.png");
        UImage2 = loadImage("/img/Map/U2.png");
        UImage3 = loadImage("/img/Map/U3.png");
        UImage4 = loadImage("/img/Map/U4.png");
    }

    private BufferedImage loadImage(String m) {
        try {
            URL imageUrl = getClass().getResource(m);
            if (imageUrl != null) {
                return ImageIO.read(imageUrl);
            } else {
                System.err.println("Error loading image: " + m);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateCherryImage() {

        if (cnt % 4 == 0) {
            cherryStatus ^= 1;
        }

        if (cherryStatus == 1) {
            cherryImage = cherryImage1;
        } else {
            cherryImage = cherryImage2;
        }
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                pacman.setRequestedDirection(0, -1); // dx = -1 (Lên)
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                pacman.setRequestedDirection(0, 1);  // dx = 1 (Xuống)
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                pacman.setRequestedDirection(-1, 0); // dy = -1 (Trái)
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                pacman.setRequestedDirection(1, 0);  // dy = 1 (Phải)
            }
        }
    }

    public void stopTimers() {
        gameLoopTimer.stop();
        clockTimer.stop();
    }

    public void startTimers() {
        gameLoopTimer.start();
        clockTimer.start();
    }
}

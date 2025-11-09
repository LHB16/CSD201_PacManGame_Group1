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

    private int ghostStatus = 0;
//    private int ghostTime;
    private int redBullTicks = 0; // Bộ đếm ngược (200 ticks = 10 giây)
    private int cntGhost = 4;
    private int pacManSpeed = 4;
    private int ghostSpeed = 5;
    private Ghost[] ghosts = new Ghost[105];
    //private redGhost

    // --- Map and Game State ---
    private int mapData[][];
    private int score = 0;
    private int defaultLives = 100;
    public int totalLives = defaultLives;
    private int totalDots = 0;
    private int imageStatus = 0;
    private Timer gameLoopTimer;
    private Timer clockTimer;
    private Timer ghostSpawnTimer; // Timer để sinh ghost
    private int seconds = 0, minutes = 0, hours = 0;

    // --- Images ---
    private BufferedImage redBullImage;
    private BufferedImage cherryImage, cherryImage1, cherryImage2;
    private BufferedImage appleImage1;
    private BufferedImage appleImage2;
    private BufferedImage appleGoldImage1;
    private BufferedImage appleGoldImage2;
    private BufferedImage bottleImage;
    private BufferedImage verticalImage, horizontalImage;
    private BufferedImage cornerImage1, cornerImage2, cornerImage3, cornerImage4;
    private BufferedImage intersectionImage1, intersectionImage2, intersectionImage3, intersectionImage4;
    private BufferedImage UImage1, UImage2, UImage3, UImage4;

    public int redBullX = -1;
    public int redBullY = -1;
    public int redBullStatus = 0;
    // --- Super Point ---
    public int appleRedX = -1;
    public int appleRedY = -1;

    public int appleGoldX = -1;
    public int appleGoldY = -1;
    private int appleGoldStatus = 0;

    public int bottleX = -1;
    public int bottleY = -1;
    public int bottleStatus = 0;

    private ArrayList<Block> itemBlock = new ArrayList<>();
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
        pacman = new Pacman(10, 15, TILE_SIZE, frame.getChoice());
        // --- Initialize Game Objects ---
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

        // Khởi tạo timer sinh ghost (4 giây = 4000ms), nhưng chưa start
        ghostSpawnTimer = new Timer(4000, e -> spawnNewGhost());
        startTimers();
    }

    private void countInitialDots() {
        totalDots = 0;
        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                if (mapData[x][y] == 3) {
                    totalDots++;
                    itemBlock.add(new Block(x, y));
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
        for (int i = 0; i < cntGhost; ++i) {
            ghosts[i].updateImage(cnt, ghostStatus);
        }

        drawMaze(g2d);
        pacman.draw(g2d);
        for (int i = 0; i < cntGhost; ++i) {
            ghosts[i].draw(g2d);
        }
    }

    // ĐIỀU KHIỂN LOGIC GAME
    @Override
    public void actionPerformed(ActionEvent e) {
        // Pac-Man di chuyển mỗi 4 tick (4 * 50ms = 200ms)
        if (cnt % pacManSpeed == 0) {
            movePacman();
        }

        // Ghost di chuyển mỗi 5 tick (3 * 50ms = 150ms)
        if (cnt % ghostSpeed == 0) {
            for (int i = 0; i < cntGhost; ++i) {
                ghosts[i].move(mapData);
            }
        }

        // ĐẾM NGƯỢC RED BULL
        if (ghostStatus > 0) {
            if (redBullTicks > 0) {
                redBullTicks--; // Giảm 1 tick (tương đương 50ms)
                
                // Cập nhật thanh tiến trình
                gameFrame.updateRedBullTimer(true, redBullTicks);
                
            } else { // redBullTicks == 0, hết giờ
                ghostStatus = 0;
                gameFrame.updateRedBullTimer(false, 0); // Tắt thanh bar
                
                // Hồi sinh các ghost đã bị ăn
                for (int i = 0; i < cntGhost; ++i){
                    if (ghosts[i].getX() == -1 && ghosts[i].getY() == -1){
                        ghosts[i].setX(10);
                        ghosts[i].setY(9);
                    }
                }
            }
        }

        checkCollision();
        repaint();
    }

    /**
     * Sinh ra một ghost mới tại vị trí mặc định (10, 9)
     */
    private void spawnNewGhost() {
        // Nếu đã đạt giới hạn mảng, không sinh nữa
        if (cntGhost >= ghosts.length) {
            ghostSpawnTimer.stop();
            return;
        }

        // Tạo ghost mới, xoay vòng màu
        int ghostType = cntGhost % 4;
        if (ghostType == 0) {
            ghosts[cntGhost] = new GhostRed(10, 9, TILE_SIZE);
        } else if (ghostType == 1) {
            ghosts[cntGhost] = new GhostBlue(10, 9, TILE_SIZE);
        } else if (ghostType == 2) {
            ghosts[cntGhost] = new GhostYellow(10, 9, TILE_SIZE);
        } else {
            ghosts[cntGhost] = new GhostPink(10, 9, TILE_SIZE);
        }

        // Tăng số lượng ghost đang hoạt động
        cntGhost++;
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

                // Kiểm tra nếu hết chấm
                if (totalDots <= 0) {
                    // Bắt đầu sinh ghost nếu timer chưa chạy
                    if (!ghostSpawnTimer.isRunning()) {
                        ghostSpawnTimer.start();
                        // Đặt số mạng còn lại thành 1
                        totalLives = 1;
                        gameFrame.uploadLives();
                    }
                }

            }

            // tao gold
            if (mapData[newX][newY] == 2) {
                score += 1000;
                gameFrame.lbCountScore.setText(String.valueOf(score));
            }
            // tao do
            if (newX == appleRedX && newY == appleRedY) {
                score += 50;
                gameFrame.lbCountScore.setText(String.valueOf(score));
                appleRedX = -1;
                appleRedY = -1;
            }
            // an tao do
            if (newX == appleGoldX && newY == appleGoldY) {
                ++totalLives;
                gameFrame.uploadLives();
                appleGoldX = -1;
                appleGoldY = -1;
            }

            // an binh thuoc 
            if (newX == bottleX && newY == bottleY) {
                pacman.setChoiceCharacter(3 - pacman.getChoiceCharacter());
                pacman.loadImages();
                bottleX = -1;
                bottleY = -1;
            }

            // an nuoc tang luc
            if (newX == redBullX && newY == redBullY) {
                ghostStatus = 1;
                redBullTicks = 200; // Bắt đầu đếm từ 200 ticks
                gameFrame.updateRedBullTimer(true, redBullTicks); // Gửi 200
                redBullX = -1;
                redBullY = -1;
            }

            // Thêm vị trí cũ vào danh sách có thể xuất hiện siêu điểm
            // Cập nhật vị trí trên bản đồ logic
            mapData[oldX][oldY] = 1; // Vị trí cũ trở thành ô trống
            //mapData[newX][newY] = 6; // Vị trí mới là Pacman
        }
    }

    private void checkCollision() {
        // Va chạm tại cùng một ô
        for (int i = 0; i < cntGhost; ++i) {
            if (pacman.getX() == ghosts[i].getX() && pacman.getY() == ghosts[i].getY()) {
                if (ghostStatus > 0) {
                    score += 200;
                    ghosts[i].setX(-1);
                    ghosts[i].setY(-1);
                    return;
                }
                appleRedX = appleRedY = -1;
                appleGoldX = appleGoldY = -1;
                bottleX = bottleY = -1;
                redBullX = redBullY = -1;
                ghostStatus = 0; 
//                ghostTime = 0;
                redBullTicks = 0;
                gameFrame.updateRedBullTimer(false, 0);
                gameFrame.pacmanHit();
                return;
            }
            // Va chạm khi đi ngược chiều nhau
            if (pacman.getX() == ghosts[i].getPrevX() && pacman.getY() == ghosts[i].getPrevY()
                    && ghosts[i].getX() == pacman.getPrevX() && ghosts[i].getY() == pacman.getPrevY()) {

                if (ghostStatus > 0) {
                    score += 200;
                    ghosts[i].setX(-1);
                    ghosts[i].setY(-1);
                    return;
                }
                appleRedX = appleRedY = -1;
                appleGoldX = appleGoldY = -1;
                bottleX = bottleY = -1;
                redBullX = redBullY = -1;
                ghostStatus = 0; 
//                ghostTime = 0;
                redBullTicks = 0;
                gameFrame.updateRedBullTimer(false, 0);
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

        for (int i = 0; i < cntGhost; ++i) {
            ghosts[i].resetPosition(10, 9);
        }

        // Cập nhật vị trí mới trên mapData
        mapData[pacman.getX()][pacman.getY()] = 6;
    }

    public void replayGame() {
        totalLives = defaultLives;
        
        ghostStatus = 0;
//        ghostTime = 0;
        redBullTicks = 0;
        gameFrame.updateRedBullTimer(false, 0);

        // Reset lại số lượng ghost về 4
        cntGhost = 4;
        // Dừng timer sinh ghost nếu đang chạy
        if (ghostSpawnTimer.isRunning()) {
            ghostSpawnTimer.stop();
        }

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

        //countBlock();
        // System.out.println("aaaaaaaaaaaaaaa");
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
        System.out.println(cnt);
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

                if (x == appleRedX && y == appleRedY) {

                    if (imageStatus == 1) {
                        g2d.drawImage(appleImage1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    } else {
                        g2d.drawImage(appleImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }

                if (x == appleGoldX && y == appleGoldY) {

                    if (imageStatus == 1) {
                        g2d.drawImage(appleGoldImage1, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    } else {
                        g2d.drawImage(appleGoldImage2, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }

                if (x == bottleX && y == bottleY) {

                    if (imageStatus == 1) {
                        g2d.drawImage(bottleImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    } else {
                        g2d.drawImage(bottleImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }

                }

                if (x == redBullX && y == redBullY) {

                    if (imageStatus == 1) {
                        g2d.drawImage(redBullImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    } else {
                        g2d.drawImage(redBullImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }

                }
            }
        }

        // 5s
        if (cnt % 100 == 0) {
            int x = -1, y = -1;
            for (int i = 1; i <= 10; ++i) {
                int id = rand.nextInt(itemBlock.size());
                x = itemBlock.get(id).getX();
                y = itemBlock.get(id).getY();
                //System.out.println(manhattanDistance(x, y, pacman.getX(), pacman.getY()));
                if (manhattanDistance(x, y, pacman.getX(), pacman.getY()) > 5
                        && manhattanDistance(x, y, appleGoldX, appleGoldX) > 5
                        && manhattanDistance(x, y, bottleX, bottleY) > 5
                        && manhattanDistance(x, y, redBullX, redBullY) > 5) {
                    appleRedX = x;
                    appleRedY = y;
                    break;
                }
            }
            System.out.println(appleRedX + " " + appleRedY);
        }

        // 10s xuat hien, 10s an
        if (cnt % 200 == 0) {
            appleGoldStatus ^= 1;
            if (appleGoldStatus == 1) {
                int x = -1, y = -1;
                for (int i = 1; i <= 10; ++i) {
                    int id = rand.nextInt(itemBlock.size());
                    x = itemBlock.get(id).getX();
                    y = itemBlock.get(id).getY();
                    //System.out.println(manhattanDistance(x, y, pacman.getX(), pacman.getY()));
                    if (manhattanDistance(x, y, pacman.getX(), pacman.getY()) > 3
                            && manhattanDistance(x, y, appleRedX, appleRedY) > 5
                            && manhattanDistance(x, y, bottleX, bottleY) > 5
                            && manhattanDistance(x, y, redBullX, redBullY) > 5) {
                        appleGoldX = x;
                        appleGoldY = y;
                        break;
                    }
                }
            } else {
                appleGoldX = appleGoldY = -1;
            }
        }

        // 7s
        if (cnt % 140 == 0) {
            bottleStatus ^= 1;
            if (bottleStatus == 1) {
                int x = -1, y = -1;
                for (int i = 1; i <= 10; ++i) {
                    int id = rand.nextInt(itemBlock.size());
                    x = itemBlock.get(id).getX();
                    y = itemBlock.get(id).getY();
                    //System.out.println(manhattanDistance(x, y, pacman.getX(), pacman.getY()));
                    if (manhattanDistance(x, y, pacman.getX(), pacman.getY()) > 3
                            && manhattanDistance(x, y, appleRedX, appleRedY) > 5
                            && manhattanDistance(x, y, appleGoldX, appleGoldY) > 5
                            && manhattanDistance(x, y, redBullX, redBullY) > 5) {
                        bottleX = x;
                        bottleY = y;
                        break;
                    }
                }
            } else {
                bottleX = bottleY = -1;
            }
        }
        
//        if (cnt % 20 == 0) {
//            if (ghostStatus > 0) {
//                ghostTime = (ghostTime + 1) % 11; // Tăng thời gian đã trôi qua (0 -> 1 ... 10 -> 0)
//                
//                if (ghostTime == 0) { // Đã hết 10 giây (vừa quay về 0)
//                    for (int i = 0; i < cntGhost; ++i){
//                        if (ghosts[i].getX() == -1 && ghosts[i].getY() == -1){
//                            ghosts[i].setX(10);
//                            ghosts[i].setY(9);
//                        }
//                    }
//                    ghostStatus = 0;
//                    gameFrame.updateRedBullTimer(false, 0); // Tắt thanh tiến trình
//                } else {
//                    // Vẫn đang trong thời gian hiệu lực (ghostTime từ 1 đến 10)
//                    int remainingTime = 10 - ghostTime; // Thời gian còn lại (9... 0)
//                    gameFrame.updateRedBullTimer(true, remainingTime); // Cập nhật thanh
//                }
//            }
//        }

//        if (cnt % 20 == 0) {
//            if (ghostStatus > 0) {
//                ghostTime = (ghostTime + 1) % 11;
//                if (ghostTime == 0) {
//                    for (int i = 0; i < cntGhost; ++i){
//                        if (ghosts[i].getX() == -1 && ghosts[i].getY() == -1){
//                            ghosts[i].setX(10);
//                            ghosts[i].setY(9);
//                        }
//                    }
//                    ghostStatus = 0;
//                }
//            }
//        }

        // 12s
        if (cnt % 240 == 0) {

            redBullStatus ^= 1;
            if (redBullStatus == 1) {
                int x = -1, y = -1;
                for (int i = 1; i <= 100; ++i) {
                    int id = rand.nextInt(itemBlock.size());
                    x = itemBlock.get(id).getX();
                    y = itemBlock.get(id).getY();

                    //System.out.println(manhattanDistance(x, y, pacman.getX(), pacman.getY()));
                    if (manhattanDistance(x, y, pacman.getX(), pacman.getY()) > 3
                            && manhattanDistance(x, y, appleRedX, appleRedY) > 3
                            && manhattanDistance(x, y, appleGoldX, appleGoldY) > 3
                            && manhattanDistance(x, y, bottleX, bottleY) > 3) {
                        
                        redBullX = x;
                        redBullY = y;
                        break;
                    }
                }
            } else {
                redBullX = redBullY = -1;
            }
//            System.out.println("");
//            System.out.println("RedBull: " + redBullX + " " + redBullY);
        }

        if (cnt == 10000000) {
            cnt = 0;
        }

    }

    private void loadMapImages() {
        redBullImage = loadImage("/img/item/redBull.png");

        cherryImage1 = loadImage("/img/item/cherry.png");
        cherryImage2 = loadImage("/img/item/cherry2.png");

        appleImage1 = loadImage("/img/item/apple1.png");
        appleImage2 = loadImage("/img/item/apple2.png");
        appleGoldImage1 = loadImage("/img/item/goldenApple1.png");
        appleGoldImage2 = loadImage("/img/item/goldenApple2.png");

        bottleImage = loadImage("/img/item/bottle.png");

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
            imageStatus ^= 1;
        }

        if (imageStatus == 1) {
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
        // Dừng timer sinh ghost nếu nó tồn tại
        if (ghostSpawnTimer != null) {
            ghostSpawnTimer.stop();
        }
    }

    public void startTimers() {
        gameLoopTimer.start();
        clockTimer.start();
    }
}

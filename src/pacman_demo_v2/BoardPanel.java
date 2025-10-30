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
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer; 

public class BoardPanel extends JPanel implements ActionListener {
    private PacManMainGame_Frame gameFrame;
    
    Random rand = new Random();

    private BufferedImage pacmanImage;
    private BufferedImage cherryImage;
    private BufferedImage ballImage;
    private BufferedImage pinkGhostImage;
    private BufferedImage orangeGhostImage;
    private BufferedImage redGhostImage;
    private BufferedImage blueGhostImage;
    private BufferedImage pacmanUpImage, pacmanDownImage, pacmanLeftImage, pacmanRightImage;
    private final int TILE_SIZE = 24;

    // Bản đồ gốc, không thay đổi
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

    private int mapData[][];
    
    private int score = 0;
    public int totalLives = 3;
    private int totalDots = 0;

    private int pacmanX = 10;
    private int pacmanY = 15;
    private int prevPacmanX, prevPacmanY;
    private int dx, dy;
    private int req_dx, req_dy;

    private int ghostX = 10;
    private int ghostY = 9;
    private int prevGhostX, prevGhostY;
    private int preDxGhost = 0;
    private int preDyGhost = -1;
    private int[][] dGhost = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    private Timer gameLoopTimer;
    private Timer clockTimer;
    private int seconds = 0, minutes = 0, hours = 0;

    private int superPointX = -1;
    private int superPointY = -1;
    ArrayList<Block> superPoint = new ArrayList<>();
    
    // BIẾN ĐẾM ĐỂ ĐIỀU CHỈNH TỐC ĐỘ
    private int moveCounter = 0;


    public BoardPanel(PacManMainGame_Frame frame) {
        this.gameFrame = frame;
        this.setBackground(Color.BLACK);
        loadPacmanImages();

        mapData = new int[ORIGINAL_MAP.length][];
        for (int i = 0; i < ORIGINAL_MAP.length; i++) {
            mapData[i] = ORIGINAL_MAP[i].clone();
        }
        pacmanImage = pacmanRightImage; 
        dx = 0; dy = 0; req_dx = 0; req_dy = 0;

        addKeyListener(new TAdapter());
        setFocusable(true);

        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                if (mapData[x][y] == 3) {
                    totalDots++;
                }
            }
        }

        // GIẢM DELAY ĐỂ VÒNG LẶP NHANH HƠN
        gameLoopTimer = new Timer(50, this); 
        
        clockTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateGameTime();
            }
        });
        
        startTimers();
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


    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                req_dx = 0; req_dy = -1;
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                req_dx = 0; req_dy = 1;
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                req_dx = -1; req_dy = 0;
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                req_dx = 1; req_dy = 0;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        drawMaze(g2d);
    }

    private void updatePacmanImage() {
        if (dx == -1) pacmanImage = pacmanLeftImage;
        else if (dx == 1) pacmanImage = pacmanRightImage;
        else if (dy == -1) pacmanImage = pacmanUpImage;
        else if (dy == 1) pacmanImage = pacmanDownImage;
    }

    private int cnt = 0;

    private void drawMaze(Graphics2D g2d) {
        ++cnt;
        updatePacmanImage(); 

        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                int value = mapData[x][y];
                if (value == 0) { // Wall
                    g2d.setColor(new Color(0, 0, 200));
                    g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 3, TILE_SIZE - 3);
                }
                if (value == 2) { // Cherry
                    if (cherryImage != null) g2d.drawImage(cherryImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                if (value == 3) { // Dot
                    g2d.setColor(new Color(255, 255, 255));
                    g2d.fillOval(x * TILE_SIZE + 8, y * TILE_SIZE + 8, 4, 4);
                }
                if (ghostX == x && ghostY == y) {
                    if (pinkGhostImage != null) g2d.drawImage(pinkGhostImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
                if (value == 5) { // Hidden Wall
                    if (totalDots > 0) {
                        g2d.setColor(new Color(0, 0, 200));
                        g2d.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE - 3, TILE_SIZE - 3);
                    }
                }
                if (x == superPointX && y == superPointY) {
                    if (ballImage != null) {
                        if (cnt % 2 == 0) g2d.drawImage(ballImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                        else g2d.drawImage(ballImage, x * TILE_SIZE + 6, y * TILE_SIZE + 6, TILE_SIZE / 2, TILE_SIZE / 2, null);
                    }
                }
                if (value == 6) { // Pacman
                    if (pacmanImage != null) g2d.drawImage(pacmanImage, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        if (cnt % 20 == 0) {
            int x = -1, y = -1;
            if (!superPoint.isEmpty()) {
                for (int i = 1; i <= 5; ++i) {
                    int id = rand.nextInt(superPoint.size());
                    x = superPoint.get(id).getX();
                    y = superPoint.get(id).getY();
                    System.out.println(manhattanDistance(x, y, pacmanX, pacmanY));
                    if (manhattanDistance(x, y, pacmanX, pacmanY) > 5) {
                        superPointX = x;
                        superPointY = y;
                        break;
                    }
                }
                System.out.println(superPointX + " " + superPointY);
            }
            cnt = 0;
        }
    }

    public int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private void loadPacmanImages() {
        pacmanUpImage = loadImage("/img/pacmanUp.png");
        pacmanDownImage = loadImage("/img/pacmanDown.png");
        pacmanLeftImage = loadImage("/img/pacmanLeft.png");
        pacmanRightImage = loadImage("/img/pacmanRight.png");
        cherryImage = loadImage("/img/cherry.png");
        ballImage = loadImage("/img/ball.png");
        pinkGhostImage = loadImage("/img/pinkGhost.png");
    }

    private BufferedImage loadImage(String m) {
        try {
            URL imageUrl = getClass().getResource(m);
            if (imageUrl != null) return ImageIO.read(imageUrl);
            else System.err.println("Error loading image: " + m);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int isValidMoveGhost(int x, int y) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            if (mapData[x][y] == 0 || mapData[x][y] == 5) return 1;
            return 0;
        }
        return -1;
    }

    private void moveGhost() {
        Collections.shuffle(Arrays.asList(dGhost));
        int newX, newY;
        newX = ghostX + preDxGhost;
        newY = ghostY + preDyGhost;
        int status = isValidMoveGhost(newX, newY);
        
        if (status == 1 || status == -1) {
            for (int i = 0; i < 4; ++i) {
                if (dGhost[i][0] == preDxGhost && dGhost[i][1] == preDyGhost) continue;
                newX = ghostX + dGhost[i][0];
                newY = ghostY + dGhost[i][1];
                status = isValidMoveGhost(newX, newY);
                if (status == -1 || status == 1) continue;
                ghostX = newX;
                ghostY = newY;
                preDxGhost = dGhost[i][0];
                preDyGhost = dGhost[i][1];
                return;
            }
        } else {
            ghostX = newX;
            ghostY = newY;
        }
    }
    
    public void resetPosition() {
        mapData[pacmanX][pacmanY] = 1; 
        pacmanX = 10; pacmanY = 15;
        ghostX = 10; ghostY = 9;
        preDxGhost = 0; preDyGhost = -1;
        mapData[pacmanX][pacmanY] = 6;
        pacmanImage = pacmanRightImage;
        dx = 0; dy = 0; req_dx = 0; req_dy = 0;
    }

    private void movePacman() {
        int newX, newY;
        newX = pacmanX + req_dx;
        newY = pacmanY + req_dy;
        
        System.out.println("Pacman: " + pacmanX + " " + pacmanY);
        System.out.println("Ghost: " + ghostX + " " + ghostY);
        
        if (isValidMovePacMan(newX, newY)) {
            dx = req_dx;
            dy = req_dy;
        }

        newX = pacmanX + dx;
        newY = pacmanY + dy;
        if (isValidMovePacMan(newX, newY)) {
            if (newX == superPointX && newY == superPointY) {
                score += 5;
                superPointX = -1;
                superPointY = -1;
            }
            if (mapData[newX][newY] == 3) {
                score += 10;
                gameFrame.lbCountScore.setText(String.valueOf(score));
                totalDots--;
                System.out.println("Dots remaining: " + totalDots);
            }
            if (mapData[newX][newY] == 2) {
                score += 1;
                gameFrame.lbCountScore.setText(String.valueOf(score));
            }
            if (mapData[pacmanX][pacmanY] != 1) {
                superPoint.add(new Block(newX, newY));
            }
            mapData[pacmanX][pacmanY] = 1;
            pacmanX = newX;
            pacmanY = newY;
            mapData[pacmanX][pacmanY] = 6;
        }
    }

    private boolean isValidMovePacMan(int x, int y) {
        if (x >= 0 && x < mapData.length && y >= 0 && y < mapData[0].length) {
            int tile = mapData[x][y];
            if (totalDots > 0) return tile != 0 && tile != 5;
            else return tile != 0;
        }
        return false;
    }

    public void replayGame() {
        totalLives = 3;
        
        for (int i = 0; i < ORIGINAL_MAP.length; i++) {
            mapData[i] = ORIGINAL_MAP[i].clone();
        }

        resetPosition();

        score = 0;
        seconds = 0; minutes = 0; hours = 0;
        gameFrame.lbCountScore.setText("0");
        gameFrame.lbCountTime.setText("00:00:00");

        totalDots = 0;
        for (int x = 0; x < mapData.length; x++) {
            for (int y = 0; y < mapData[x].length; y++) {
                if (mapData[x][y] == 3) {
                    totalDots++;
                }
            }
        }
        startTimers();
    }

    // ĐIỀU KHIỂN LOGIC GAME
    @Override
    public void actionPerformed(ActionEvent e) {
        moveCounter++;

        // Lưu vị trí cũ trước khi di chuyển
        prevPacmanX = pacmanX;
        prevPacmanY = pacmanY;
        prevGhostX = ghostX;
        prevGhostY = ghostY;
        
        // Pac-Man di chuyển mỗi 5 tick (5 * 50ms = 250ms)
        if (moveCounter % 4 == 0) {
            movePacman();
        }
        
        // Ma di chuyển mỗi 4 tick (4 * 50ms = 200ms) -> Nhanh hơn Pac-Man
        if (moveCounter % 3 == 0) {
            moveGhost();
        }

        checkCollision();
        repaint();
    }
    
    private void checkCollision() {
        if (pacmanX == ghostX && pacmanY == ghostY) {
            gameFrame.pacmanHit();
            return;
        }
        if (pacmanX == prevGhostX && pacmanY == prevGhostY && 
            ghostX == prevPacmanX && ghostY == prevPacmanY) {
            gameFrame.pacmanHit();
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
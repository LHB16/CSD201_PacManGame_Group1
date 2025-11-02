package pacman_demo_v2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class GlassPane extends JComponent {
    private final Frame parentFrame;

    public GlassPane(Frame parent) {
        this.parentFrame = parent;
        // Bắt tất cả các sự kiện chuột để không thể click vào game khi dialog hiện ra
        addMouseListener(new MouseAdapter() {});
        addMouseMotionListener(new MouseAdapter() {});
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Vẽ một hình chữ nhật màu đen bán trong suốt che phủ toàn bộ cửa sổ cha
        g.setColor(new Color(0, 0, 0, 150)); // 150 là độ mờ (0-255)
        g.fillRect(0, 0, parentFrame.getWidth(), parentFrame.getHeight());
    }
}
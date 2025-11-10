package pacman_demo_v2;

import java.io.File;
import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Lớp này quản lý việc phát nhạc nền cho toàn bộ game.
 * Nó đảm bảo nhạc chỉ khởi tạo một lần và phát liên tục.
 * @author luuhu (và Gemini)
 */
public class MusicManager {

    private static MediaPlayer menuMusicPlayer;
    private static boolean isPlaying = false;

    // Khởi tạo JavaFX runtime một lần duy nhất
    static {
        try {
             new JFXPanel();
        } catch (Exception e) {
            // Xử lý lỗi nếu JFX không thể khởi tạo
            System.err.println("Không thể khởi tạo JavaFX Runtime.");
            e.printStackTrace();
        }
    }

    /**
     * Bắt đầu phát nhạc nền (nếu nó chưa được phát).
     */
    public static void playMenuMusic() {
        // Nếu nhạc đang phát rồi thì không làm gì cả
        if (isPlaying) {
            return;
        }

        // Chạy trên một luồng riêng
        new Thread(() -> {
            try {
                String[] paths = {"/msc/BGM.mp3", "src/msc/BGM.mp3", "msc/BGM.mp3"};
                URL url = null;
                for (String p : paths) {
                    // Dùng getResource của chính class này
                    url = MusicManager.class.getResource(p); 
                    if (url == null) {
                        File f = new File(p);
                        if (f.exists()) {
                            url = f.toURI().toURL();
                        }
                    }
                    if (url != null) {
                        break;
                    }
                }
                
                if (url != null) {
                    menuMusicPlayer = new MediaPlayer(new Media(url.toExternalForm()));
                    menuMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop vô hạn
                    menuMusicPlayer.play();
                    isPlaying = true; // Đánh dấu là nhạc đang phát
                } else {
                    System.err.println("Không tìm thấy file nhạc BGM.mp3");
                }
            } catch (Exception e) {
                System.err.println("Lỗi khi phát nhạc:");
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Dừng hẳn việc phát nhạc (dùng khi thoát game).
     */
    public static void stopMusic() {
        if (menuMusicPlayer != null && isPlaying) {
            menuMusicPlayer.stop();
            isPlaying = false;
            menuMusicPlayer = null; // Giải phóng tài nguyên
        }
    }
}
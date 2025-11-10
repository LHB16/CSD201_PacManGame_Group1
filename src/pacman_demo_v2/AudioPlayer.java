package pacman_demo_v2;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

public class AudioPlayer {

    static { new JFXPanel(); }

    private static final String[] PATHS = {"/msc/ms.mp3", "src/msc/ms.mp3", "msc/ms.mp3"};

    public static void play() throws Exception {
        URL url = null;
        for (String p : PATHS) {
            url = AudioPlayer.class.getResource(p);
            if (url == null) {
                File f = new File(p);
                if (f.exists()) url = f.toURI().toURL();
            }
            if (url != null) break;
        }
        if (url == null) return;

        CountDownLatch done = new CountDownLatch(1);
        MediaPlayer player = new MediaPlayer(new Media(url.toExternalForm()));
        player.setOnEndOfMedia(() -> { player.stop(); player.dispose(); done.countDown(); });
        player.setOnError(() -> done.countDown());
        player.play();
        done.await();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pacman_demo_v2;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ScoreManager {
    
    private static final String FILE_NAME = "scores.txt";

    // Hàm đọc danh sách điểm từ file
    public List<PlayerScore> readScores() {
        List<PlayerScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        String username = parts[0];
                        long score = Long.parseLong(parts[1]);
                        String time = parts[2];
                        scores.add(new PlayerScore(username, score, time));
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi đọc file điểm (dòng bị lỗi): " + line);
                    }
                }
            }
        } catch (IOException e) {
            // File chưa tồn tại, không sao cả, trả về danh sách rỗng
        }
        
        // Sắp xếp trước khi trả về (dù sao cũng cần)
        Collections.sort(scores);
        return scores;
    }

    // Hàm thêm điểm mới và ghi lại file
    public void addScore(String username, long score, String time) {
        List<PlayerScore> scores = readScores();
        
        PlayerScore existingScore = null;
        for (PlayerScore ps : scores) {
            if (ps.getUsername().equalsIgnoreCase(username)) {
                existingScore = ps;
                break;
            }
        }

        if (existingScore != null) {
            // Tên đã tồn tại, chỉ cập nhật nếu điểm mới cao hơn
            if (score > existingScore.getScore()) {
                scores.remove(existingScore); // Xóa điểm cũ
                scores.add(new PlayerScore(username, score, time)); // Thêm điểm mới
            }
        } else {
            // Tên mới, thêm vào
            scores.add(new PlayerScore(username, score, time));
        }

        // Sắp xếp danh sách
        Collections.sort(scores);

        // Ghi đè toàn bộ file với danh sách đã sắp xếp
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (PlayerScore ps : scores) {
                writer.write(ps.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
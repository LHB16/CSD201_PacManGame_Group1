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
    
    // Đảm bảo tên file khớp với file bạn đang dùng
    private static final String FILE_NAME = "scores.txt";

    /**
     * Hàm đọc danh sách điểm từ file.
     * File có định dạng "Name Time Score"
     */
    public List<PlayerScore> readScores() {
        List<PlayerScore> scores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Phân tách bằng một hoặc nhiều khoảng trắng
                String[] parts = line.split("\\s+"); 
                // Định dạng có 3 phần: Name, Time, Score
                if (parts.length == 3) { 
                    try {
                        String username = parts[0]; // Name ở vị trí đầu tiên (index 0)
                        String time = parts[1];     // Time ở vị trí thứ hai (index 1)
                        long score = Long.parseLong(parts[2]); // Score ở vị trí thứ ba (index 2)
                        scores.add(new PlayerScore(username, score, time));
                    } catch (NumberFormatException e) {
                        System.err.println("Lỗi đọc file điểm (dòng lỗi): " + line);
                    }
                }
            }
        } catch (IOException e) {
            // Nếu file chưa tồn tại, trả về danh sách rỗng
            System.err.println("Không tìm thấy file scores.txt, sẽ tạo file mới khi có điểm.");
        }
        
        // Sắp xếp điểm giảm dần trước khi trả về
        Collections.sort(scores);
        return scores;
    }

    /**
     * Hàm thêm điểm mới và ghi lại file.
     * File sẽ được ghi với định dạng "Name Time Score"
     */
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
                scores.remove(existingScore);
                scores.add(new PlayerScore(username, score, time));
            }
        } else {
            // Tên mới, thêm vào danh sách
            scores.add(new PlayerScore(username, score, time));
        }

        // Sắp xếp lại danh sách
        Collections.sort(scores);

        // Ghi file với định dạng "Name Time Score"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (PlayerScore ps : scores) {
                // Dùng String.format để ghi đúng định dạng, không còn rank ở đầu
                String line = String.format("%s %s %d", ps.getUsername(), ps.getTime(), ps.getScore());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
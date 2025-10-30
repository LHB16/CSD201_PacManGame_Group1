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
    
    // CHANGE: Đổi tên file để thống nhất với RankingBoard_Frame nếu cần,
    // hoặc tốt hơn là để RankingBoard_Frame dùng hằng số này.
    private static final String FILE_NAME = "Scores.txt";

    // Hàm đọc danh sách điểm từ file
    public List<PlayerScore> readScores() {
        List<PlayerScore> scores = new ArrayList<>();
        // CHANGE: Đổi sang định dạng đọc file mới (phân tách bằng khoảng trắng)
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Định dạng mới: "Rank Name Score Time" -> vd: "1 PlayerOne 1200 00:01:30"
                String[] parts = line.split("\\s+"); // Phân tách bằng một hoặc nhiều khoảng trắng
                if (parts.length == 4) { // Bây giờ file sẽ có 4 phần
                    try {
                        String username = parts[1];
                        long score = Long.parseLong(parts[2]);
                        String time = parts[3];
                        scores.add(new PlayerScore(username, score, time));
                    } catch (NumberFormatException e) {
                        System.err.println("Error reading score file (error line): " + line);
                    }
                }
            }
        } catch (IOException e) {
            // File chưa tồn tại, không sao cả, trả về danh sách rỗng
        }
        
        // Sắp xếp trước khi trả về (quan trọng)
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
                scores.remove(existingScore);
                scores.add(new PlayerScore(username, score, time));
            }
        } else {
            // Tên mới, thêm vào
            scores.add(new PlayerScore(username, score, time));
        }

        // Sắp xếp lại danh sách sau khi thêm/cập nhật
        Collections.sort(scores);

        // CHANGE: Ghi đè file với định dạng mới bao gồm cả Rank
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (int i = 0; i < scores.size(); i++) {
                PlayerScore ps = scores.get(i);
                int rank = i + 1;
                // Ghi ra file với định dạng: "Rank Name Score Time"
                // Dùng String.format để dễ nhìn hơn
                String line = String.format("%d %s %d %s", rank, ps.getUsername(), ps.getScore(), ps.getTime());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
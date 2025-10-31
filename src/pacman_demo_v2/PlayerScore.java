/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman_demo_v2;

import java.io.Serializable;

// Dùng 'implements Comparable' để Collections.sort() biết cách sắp xếp
public class PlayerScore implements Comparable<PlayerScore>, Serializable {
    private final String username;
    private final long score;
    private final String time;

    public PlayerScore(String username, long score, String time) {
        this.username = username;
        this.score = score;
        this.time = time;
    }

    // Các hàm Getters
    public String getUsername() { return username; }
    public long getScore() { return score; }
    public String getTime() { return time; }

    @Override
    public int compareTo(PlayerScore other) {
        // Sắp xếp giảm dần theo điểm (cao nhất lên trước)
        int scoreCompare = Long.compare(other.getScore(), this.score);

        // Nếu điểm bằng nhau, thì so sánh thời gian
        if (scoreCompare == 0) {
            // Sắp xếp tăng dần theo thời gian (thời gian ngắn hơn sẽ lên trước)
            // String.compareTo() sẽ so sánh theo thứ tự từ điển, 
            // ví dụ "00:01:30" sẽ nhỏ hơn "00:02:00"
            return this.time.compareTo(other.getTime());
        }

        return scoreCompare;
    }

    @Override
    public String toString() {
        // Dùng để ghi ra file .txt, ví dụ: "player1,5000,00:02:30"
        return username + " " + time + " " + score;
    }
}
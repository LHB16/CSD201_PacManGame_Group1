# 🎮 Pac-Man Game — CSD201 Group Assignment

> **FPT University** | SE1912 – FA25 – CSD201  
> **Group 1** | Java Swing Desktop Game

---

## 👥 Group Members

| Student ID | Full Name | Role |
|---|---|---|
| CE190754 | Trần Minh Phước | Leader |
| CE190593 | Châu Quốc Inh | Secretary |
| CE200089 | Trần Nguyễn Thiên Thanh | Member |
| CE200315 | Lưu Hữu Bình | Member |
| — | Võ Hồng Khanh | Mentor |

---

## 📖 Game Introduction

**Pac-Man** is a classic arcade game originally developed by **Namco (Japan)**, created by **Toru Iwatani** and released in **1980**. This is a reimplementation built with **Java Swing** as a group assignment for the CSD201 Data Structures and Algorithms course.

The player controls a yellow round character navigating through a maze, eating dots and fruits while avoiding (or chasing) ghosts.

---

## 🕹️ How to Play

Use **WASD** or **Arrow Keys** to move Pac-Man through the maze.

| Key | Direction |
|---|---|
| `W` / `↑` | Move Up |
| `A` / `←` | Move Left |
| `S` / `↓` | Move Down |
| `D` / `→` | Move Right |

---

## 📋 Game Rules

- Start with **3 lives** — touching a ghost loses 1 life.
- **Eat all dots** to unlock the hidden door (a new ghost spawns every 5 seconds after).
- **Eat all cherries** to **win the game**.
- Consuming a **potion** will switch your character's gender (Male ↔ Female).
- Use **Red Bull** items to enter chase mode and eat ghosts for bonus points.

### 🏆 Scoring Table

| Item | Points / Effect |
|---|---|
| Dot | +10 pts |
| Red Apple | +50 pts |
| Cherry | +500 pts |
| Ghost (while powered) | +200 pts |
| Golden Apple | +1 life |
| Win screen bonus | +1000 pts |

---

## 🖼️ Frame Interfaces

The game consists of **13 frames**:

1. **Main Menu** — Play, How to Play, Ranking, About Us, Exit
2. **About Us** — Team member info
3–6. **Tutorial (4 pages)** — Controls, pellet scoring, avoid ghosts, collect fruit to win
7. **Ranking Board** — Top 5 scores with name and time
8. **Character Selection** — Choose Male or Female Pac-Man
9. **Gameplay** — Main maze with HUD (Time / Lives / Score)
10. **Quit Confirmation** — Prompt when pressing Home during game
11. **Game Over** — Enter name to save score
12. **Win Screen** — Enter name to save score
13. **Exit Screen** — Thank You screen

---

## ⚙️ Tech Stack

| Component | Technology |
|---|---|
| Language | Java |
| UI Framework | Java Swing |
| Build Tool | NetBeans (nbproject) |
| Data Structure Course | CSD201 |

---

## 🗂️ Project Structure

```
CSD201_PacManGame_Group1/
├── src/          # Source code (Java)
├── build/        # Compiled classes
├── nbproject/    # NetBeans project config
└── README.md
```

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/LHB16/CSD201_PacManGame_Group1.git
   ```
2. Open the project in **NetBeans IDE**.
3. Build and run the project (`F6`).

> **Requirements:** JDK 8+ and NetBeans IDE (or any Java-compatible IDE).

---

## 📄 License

This project is developed for educational purposes as part of the CSD201 course at FPT University. All rights reserved by Group 1 — SE1912 FA25.

package Components;

public class RingSector {
    public double from;
    public double to;
    public boolean valid;
    public int lives;
    public int totalLives;

    public RingSector(double from, double to, int lives) {
        this.from = from;
        this.to = to;
        this.lives = lives;
        this.totalLives = lives;
    }

    public boolean isValid() {
        return lives > 0;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public int getLives() {
        return lives;
    }

    public void hit(int amount) {
        lives -= amount;
    }

    public double getHealth() { return ((double)lives)/totalLives; }
}

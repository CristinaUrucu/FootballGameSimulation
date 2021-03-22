package com.company;
import java.util.Random;
import java.util.Date;

class CoordinateGenerator {
    private Random randomGenerator;

    public CoordinateGenerator() {
        Date now = new Date();
        long sec = now.getTime();
        randomGenerator = new Random(sec);
    }

    public int generateX() {
        int x = randomGenerator.nextInt(101);
        if(x < 5) {
            x = 0;
        } else if(x > 95) {
            x = 100;
        } else {
            x = randomGenerator.nextInt(99) + 1;
        }
        return x;
    }

    public int generateY() {
        int y = randomGenerator.nextInt(101);
        if(y < 5) {
            y = 0;
        } else if(y > 95) {
            y = 50;
        } else {
            y = randomGenerator.nextInt(49) + 1;
        }
        return y;
    }
}

class OutException extends Exception {

    public OutException(String msg) {
        super(msg);
    }
}

class GolException extends Exception {

    public GolException(String msg) {
        super(msg);
    }
}

class CornerException extends Exception {

    public CornerException(String msg) {
        super(msg);
    }
}

class Ball {
    private int X;
    private int Y;

    public Ball(int x, int y) {
        this.X = x;
        this.Y = y;
    }
    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }
    public void kickTheBall() throws OutException, GolException, CornerException{
        CoordinateGenerator aux = new CoordinateGenerator();
        X = aux.generateX();
        Y = aux.generateY();
        if(Y == 0 || Y == 50) throw new OutException("Out.");
        else if((X == 0 || X == 100) && (Y >= 20 || Y <= 30)) throw new GolException("Gol.");
        else if((X == 0 || X == 100) && ((Y > 0 && Y < 20) ||(Y > 30 && Y < 50))) throw new CornerException("Corner");
    }
}

class FootballGame {
    private String teamName1;
    private String teamName2;
    private int numberOfGoals1;
    private int numberOfGoals2;
    private static int out = 0;
    private static int corner = 0;

    public FootballGame(String n1, String n2) {
        this.teamName1 = n1;
        this.teamName2 = n2;
        this.numberOfGoals1 = 0;
        this.numberOfGoals2 = 0;
    }
    public String toString() {
        return this.teamName1 + " " + this.numberOfGoals1 + " - " + this.numberOfGoals2 + " " + this.teamName2 + " -> out: " + this.out + " corner: " + this.corner;
    }
    public void simulation() {
        Ball m = new Ball(50, 25);
        for (int i = 0; i < 1000; i = i + 1) {
            try {
                System.out.println(this.teamName1 + " - " + this.teamName2 + " The ball is at coordinates: (" + m.getX() + "," + m.getY() + ")");
                m.kickTheBall();
            } catch(GolException e) {
                System.out.println("Gol: (" + m.getX() + " ," + m.getY() + ")");
                if(m.getX() == 0 ) {
                    this.numberOfGoals2++;
                } else {
                    this.numberOfGoals1++;
                }
                m = new Ball(50, 25);
            } catch(OutException e) {
                out++;
                System.out.println("out :(" + m.getX() + " ," + m.getY() + ")");
                m = new Ball(m.getX(), m.getY());
            } catch(CornerException e) {
                corner++;
                System.out.println("corner :(" + m.getX() + " ," + m.getY() + ")");
                if(m.getX() == 0) {
                    if(0 < m.getY() && m.getY() < 20) m = new Ball(0, 0);
                    else
                        m = new Ball(0, 50);
                } else {
                    if(0 < m.getY() && m.getY() < 20) m = new Ball(100, 0);
                    else
                        m = new Ball(100, 50);
                }
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        FootballGame game1 = new FootballGame("Real Madrid", "Barcelona");
        game1.simulation();
        System.out.println(game1);
    }
}

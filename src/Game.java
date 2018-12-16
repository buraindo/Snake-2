import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Game {

    private static final int X_OFFSET = 31;
    private static final int Y_OFFSET = 65;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int GAME_SPEED = 80;
    private static final JTextField scoreText = new JTextField("Score: " + getScore());

    static final int SCORE_HEIGHT = 50;
    static final int GAME_SCALE = 25;
    static final int WIDTH_SCALED = WIDTH / GAME_SCALE;
    static final int HEIGHT_SCALED = HEIGHT / GAME_SCALE;
    static final Map<Integer, Integer> keyToXVelocity = new HashMap<>();
    static final Map<Integer, Integer> keyToYVelocity = new HashMap<>();
    static final Random random = new Random();

    private static Painter window;
    private static int score;
    private static World world;
    private static int lastKeyPressed;

    static Painter getWindow() {
        return window;
    }

    private static int getScore() {
        return score;
    }

    static void increaseScore(int by) {
        score += by;
    }

    static void lose() {
        JOptionPane.showMessageDialog(window, "You lost, you score is: " + getScore());
        window.getContentPane().removeAll();
        window.add(scoreText);
        window.repaint();
        world = new World(WIDTH_SCALED / 2, HEIGHT_SCALED / 2);
        score = 0;
    }

    private void update() throws InterruptedException {
        boolean running = true;
        while (running) {
            world.move(lastKeyPressed);
            lastKeyPressed = 0;
            window.paintWorld(world);
            scoreText.setText("Score: " + getScore());
            sleep(Game.GAME_SPEED);
        }
    }

    private void initMap() {
        keyToXVelocity.put (83, 0);
        keyToYVelocity.put (83, 1);
        keyToXVelocity.put (65, -1);
        keyToYVelocity.put (65, 0);
        keyToXVelocity.put (87, 0);
        keyToYVelocity.put (87, -1);
        keyToXVelocity.put (68, 1);
        keyToYVelocity.put (68, 0);
    }

    private void init() throws InterruptedException {
        initMap();
        window = new Painter("Snake", WIDTH + X_OFFSET, HEIGHT + SCORE_HEIGHT + Y_OFFSET);
        window.setLayout(null);
        window.setFocusable(true);
        scoreText.setFont(new Font("Calibri", Font.PLAIN, 35));
        scoreText.setEditable(false);
        scoreText.setBounds(0, 0, WIDTH + X_OFFSET, SCORE_HEIGHT);
        window.add(scoreText);
        window.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                lastKeyPressed = e.getKeyCode();
            }
        });
        world = new World(WIDTH_SCALED / 2, HEIGHT_SCALED / 2);
        window.paintWorld(world);
        update();
    }

    private void run() throws InterruptedException {
        init();
    }

    public static void main(String[] args) throws InterruptedException {
        new Game().run();
    }

}

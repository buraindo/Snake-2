import javax.swing.*;

class Painter extends JFrame {

    private void paint(SnakePart head) {
        SnakePart current = head;
        while (current != null) {
            current.getSquare().setBounds(current.getX() * Game.GAME_SCALE, current.getY() * Game.GAME_SCALE + Game.SCORE_HEIGHT, Game.GAME_SCALE, Game.GAME_SCALE);
            current = current.getNext();
        }
    }

    private void paint(Food food) {
        food.getSquare().setBounds(food.getX() * Game.GAME_SCALE, food.getY() * Game.GAME_SCALE + Game.SCORE_HEIGHT, Game.GAME_SCALE, Game.GAME_SCALE);
    }

    void paintWorld(World world) {
        paint(world.getHead());
        paint(world.getFood());
    }

    Painter(String title, int width, int height) {
        super(title);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
    }
}

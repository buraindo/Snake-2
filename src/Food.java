import java.awt.*;

class Food {

    private int x;
    private int y;

    private Figure square = new Figure(Color.green);

    Food(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int getY() {
        return y;
    }

    void setY(int y) {
        this.y = y;
    }

    int getX() {
        return x;
    }

    void setX(int x) {
        this.x = x;
    }

    Figure getSquare() {
        return square;
    }

}

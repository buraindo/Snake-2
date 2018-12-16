import java.awt.*;
import java.io.IOException;

class SnakePart {

    private int x;
    private int y;
    private int xVelocity;
    private int yVelocity;
    private SnakePart next;
    private Figure square = new Figure(Color.red);

    SnakePart grow() {
        SnakePart next = new SnakePart(getX(),getY());
        this.setNext(next);
        return next;
    }

    void move(int lastKeyPressed, World world) {
        if (lastKeyPressed != 0) {
            Integer newxVelocity = Game.keyToXVelocity.get(lastKeyPressed);
            Integer newyVelocity = Game.keyToYVelocity.get(lastKeyPressed);
            if (newxVelocity != null && newyVelocity != null) {
                if ((newxVelocity != -xVelocity || xVelocity == 0) && (newyVelocity != -yVelocity || yVelocity == 0)) {
                    xVelocity = newxVelocity;
                    yVelocity = newyVelocity;
                }
            }
        }
        int beforeX = x;
        int beforeY = y;
        move();
        int afterX = x;
        int afterY = y;
        world.move(beforeX, beforeY, afterX, afterY);
        SnakePart current = this.getNext();
        while (current != null) {
            afterX = current.getX();
            afterY = current.getY();
            current.move(beforeX, beforeY, world);
            current = current.getNext();
            beforeX = afterX;
            beforeY = afterY;
        }
    }

    private void move() {
        x = (x + xVelocity) >= 0 ? (x + xVelocity) % (Game.WIDTH_SCALED + 1) : Game.WIDTH_SCALED - 1 - (x + xVelocity);
        y = (y + yVelocity) >= 0 ? (y + yVelocity) % (Game.HEIGHT_SCALED + 1) : Game.HEIGHT_SCALED - 1 - (y + yVelocity);
    }

    private void move(int afterX, int afterY, World world) {
        int beforeX = getX();
        int beforeY = getY();
        setX(afterX);
        setY(afterY);
        world.movePart(beforeX, beforeY, afterX, afterY);
    }

    SnakePart getNext() {
        return next;
    }

    private void setNext(SnakePart next) {
        this.next = next;
    }

    Figure getSquare() {
        return square;
    }

    int getX() {
        return x;
    }

    private void setX(int x) {
        this.x = x;
    }

    int getY() {
        return y;
    }

    private void setY(int y) {
        this.y = y;
    }

    SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

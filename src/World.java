import javafx.util.Pair;

class World {

    private SnakePart head;
    private SnakePart tail;
    private Food food;

    void move(int lastKeyPressed) {
        head.move(lastKeyPressed, this);
    }

    private void placeFood() {
        Pair<Integer, Integer> foodAxis = pickLocation();
        food.setX(foodAxis.getKey());
        food.setY(foodAxis.getValue());
    }

    private void checkFoodEaten() {
        if (head.getX() == food.getX() && head.getY() == food.getY()) {
            tail = tail.grow();
            Game.getWindow().add(tail.getSquare());
            Game.increaseScore(1);
            placeFood();
        }
    }

    private void checkSelfEaten() {
        SnakePart current = head.getNext();
        while (current != null) {
            if (current.getX() == head.getX() && current.getY() == head.getY()) {
                Game.lose();
            }
            current = current.getNext();
        }
    }

    void check() {
        checkFoodEaten();
        checkSelfEaten();
    }

    private void instantiateHead(int x, int y) {
        head = new SnakePart(x, y);
        Game.getWindow().add(head.getSquare());
    }

    private Pair<Integer, Integer> pickLocation() {
        int x = Math.abs(Game.random.nextInt()) % (Game.WIDTH_SCALED + 1);
        int y = Math.abs(Game.random.nextInt()) % (Game.HEIGHT_SCALED + 1);
        return new Pair<>(x, y);
    }

    private void instantiateFood() {
        Pair<Integer, Integer> foodAxis = pickLocation();
        food = new Food(foodAxis.getKey(), foodAxis.getValue());
        Game.getWindow().add(food.getSquare());
    }

    private void init(int startX, int startY) {
        instantiateHead(startX, startY);
        instantiateFood();
        tail = head;
    }

    SnakePart getHead() {
        return head;
    }

    Food getFood() {
        return food;
    }

    World(int startX, int startY) {
        init(startX, startY);
    }

}

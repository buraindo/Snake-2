import javafx.util.Pair;

class World {

    private enum PointType {
        EMPTY, SNAKE, FOOD,
    }

    private int[][] worldGrid;

    private SnakePart head;
    private SnakePart tail;
    private Food food;

    void movePart(int beforeX, int beforeY, int afterX, int afterY) {
        worldGrid[beforeX][beforeY] = PointType.EMPTY.ordinal();
        worldGrid[afterX][afterY] = PointType.SNAKE.ordinal();
    }

    void move(int beforeX, int beforeY, int afterX, int afterY) {
        worldGrid[beforeX][beforeY] = PointType.EMPTY.ordinal();
        check();
        worldGrid[afterX][afterY] = PointType.SNAKE.ordinal();
    }

    void move(int lastKeyPressed) {
        head.move(lastKeyPressed, this);
    }

    private void placeFood() {
        Pair<Integer, Integer> foodAxis = pickLocation();
        food.setX(foodAxis.getKey());
        food.setY(foodAxis.getValue());
        worldGrid[food.getX()][food.getY()] = PointType.FOOD.ordinal();
    }

    private void checkFoodEaten() {
        if (worldGrid[head.getX()][head.getY()] == PointType.FOOD.ordinal()) {
            tail = tail.grow();
            Game.getWindow().add(tail.getSquare());
            Game.increaseScore(1);
            placeFood();
        }
    }

    private void checkSelfEaten() {
        if (worldGrid[head.getX()][head.getY()] == PointType.SNAKE.ordinal()) {
            Game.lose();
        }
    }

    private void check() {
        checkFoodEaten();
        checkSelfEaten();
    }

    private void instantiateHead(int x, int y) {
        head = new SnakePart(x, y);
        Game.getWindow().add(head.getSquare());
        worldGrid[head.getX()][head.getY()] = PointType.SNAKE.ordinal();
    }

    private Pair<Integer, Integer> pickLocation() {
        int x = Math.abs(Game.random.nextInt()) % (Game.WIDTH_SCALED + 1);
        int y = Math.abs(Game.random.nextInt()) % (Game.HEIGHT_SCALED + 1);
        while (true) {
            if (worldGrid[x][y] != PointType.EMPTY.ordinal()) {
                x = Math.abs(Game.random.nextInt()) % (Game.WIDTH_SCALED + 1);
                y = Math.abs(Game.random.nextInt()) % (Game.HEIGHT_SCALED + 1);
            } else break;
        }
        return new Pair<>(x, y);
    }

    private void instantiateFood() {
        Pair<Integer, Integer> foodAxis = pickLocation();
        food = new Food(foodAxis.getKey(), foodAxis.getValue());
        Game.getWindow().add(food.getSquare());
        worldGrid[food.getX()][food.getY()] = PointType.FOOD.ordinal();
    }

    private void init(int startX, int startY) {
        int WIDTH = Game.WIDTH_SCALED;
        int HEIGHT = Game.HEIGHT_SCALED;
        worldGrid = new int[WIDTH + 1][HEIGHT + 1];
        instantiateHead(startX, startY);
        instantiateFood();
        tail = head;
    }

    int[][] getWorldGrid() {
        return worldGrid;
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

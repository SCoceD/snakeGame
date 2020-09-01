package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\\uD83D\\uDC7E";
    private static final String BODY_SIGN = "\\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public void draw(Game game){
        if(isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);
            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BLACK, 75);
            }
        }else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);

            for (int i = 1; i < snakeParts.size(); i++) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    public Snake(int x, int y) {
        for (int i = 0; i < 3; i++) {
            snakeParts.add(new GameObject(x + i, y));
        }
    }

    public void setDirection(Direction direction){
            if (!(((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) &&
                    (snakeParts.get(0).x == snakeParts.get(1).x)) ||
                    ((this.direction == Direction.UP || this.direction == Direction.DOWN) &&
                            (snakeParts.get(0).y == snakeParts.get(1).y))))
                this.direction = direction;
        
    }

    public void move(Apple apple){
        GameObject newHead = createNewHead();
        if (newHead.x<0 || newHead.y<0 ||newHead.y>14 || newHead.x>14) {
            isAlive = false;
            return;
        }
        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }
        snakeParts.add(0, newHead);
        if(newHead.x == apple.x && newHead.y == apple.y){
            snakeParts.add(snakeParts.size(), new GameObject(apple.x, apple.y));
            apple.isAlive = false;
            return;
        }
        removeTail();
    }

    public GameObject createNewHead(){
        switch (direction){
            case UP:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
            case DOWN:
                return new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
            case LEFT:
                return new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
            default:
                return new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }
    }

    public void removeTail(){
        snakeParts.remove(snakeParts.size()-1);
    }

    public boolean checkCollision (GameObject gameObject){
        for (GameObject o : snakeParts) {
            if (o.x == gameObject.x && o.y == gameObject.y){
                return true;
            }
        }
        return false;
    }
    public int getLength(){
        return snakeParts.size();
    }
}
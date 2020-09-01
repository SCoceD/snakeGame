package com.javarush.games.snake;

import com.javarush.engine.cell.Color;
import com.javarush.engine.cell.Game;
import com.javarush.engine.cell.Key;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    @Override
    public void setScore(int score) {
        this.score = score;
    }

    private void createGame(){
        score = 0;
        setScore(score);
        isGameStopped = false;
        turnDelay = 300;
        setTurnTimer(turnDelay);
        snake = new Snake(WIDTH/2,HEIGHT/2);
        createNewApple();
        drawScene();
    }

    private void drawScene(){
        for (int i = 0; i < WIDTH; i ++){
            for (int j = 0; j < HEIGHT; j++) {
                setCellValueEx(i , j , Color.DARKSEAGREEN, "");
            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int step) {
        snake.move(apple);
        if (!apple.isAlive){
            this.createNewApple();
            score += 5;
            setScore(score);
            this.turnDelay -= 10;
            setTurnTimer(turnDelay);
        }
        if (!snake.isAlive){this.gameOver();}
        if (snake.getLength() > GOAL){this.win();}
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key){
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                return;
            case LEFT:
                snake.setDirection(Direction.LEFT);
                return;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                return;
            case UP:
                snake.setDirection(Direction.UP);
                return;
            case SPACE:
                if(isGameStopped){
                    createGame();
                    return;
                }
        }
    }

    private void createNewApple(){
        do{
        apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        } while (snake.checkCollision(apple));
    }

    private void gameOver(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "GAME OVER", Color.PINK, 80);
    }

    private void win(){
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "YOU WIN", Color.PINK, 80);
    }
}
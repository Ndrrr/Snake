package com.codenjoy.dojo.snake.client;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.codenjoy.dojo.client.Solver;
import com.codenjoy.dojo.client.WebSocketRunner;
import com.codenjoy.dojo.services.Dice;
import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.services.RandomDice;
import com.codenjoy.dojo.snake.client.handler.AppleHandler;
import com.codenjoy.dojo.snake.client.handler.BaseHandler;
import com.codenjoy.dojo.snake.client.handler.DirectionHandler;
import com.codenjoy.dojo.snake.client.handler.StoneHandler;

import java.util.List;

/**
 * User: Nadir Isgandarov
 */
public class YourSolver implements Solver<Board> {

    private Dice dice;
    private Board board;

    public YourSolver(Dice dice) {
        this.dice = dice;
    }

    @Override
    public String get(Board board) {
        this.board = board;
        return getDirectionBFS().toString();
    }

    public static void main(String[] args) {
        WebSocketRunner.runClient(
                // paste here board page url from browser after registration
                "http://159.89.27.106/codenjoy-contest/board/player/sn035hdk3n46wjcham25?code=7956888871049868697",
                new YourSolver(new RandomDice()),
                new Board());
    }

    public Direction randomDirection() {
        return Direction.valueOf(dice.next(4));
    }

    // previous solution
    public Direction getDirection(){
        DirectionHandler handler = new BaseHandler(new AppleHandler(new StoneHandler()));
        return handler.handle(Direction.UP, board);
    }

    public Direction getDirectionBFS(){
        Point head = board.getHead();
        Point apple = board.getApples().get(0);

        if(apple == null || head == null){
            return Direction.UP;
        }
        int[][] matrix = buildMatrixFromBoard();

        return BfsOnGrid.getDirectionBFSHandler(matrix,
                head.getX(), head.getY(), apple.getX(), apple.getY());
    }

    private int[][] buildMatrixFromBoard() {
        List<Point> stones = board.getStones();
        List<Point> snake = board.getSnake();

        int[][] matrix = new int[15][15];
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                matrix[i][j] = 0;
            }
        }
        for (int i = 0; i < 15; i++){
            matrix[0][i] = 1;
            matrix[14][i] = 1;
            matrix[i][0] = 1;
            matrix[i][14] = 1;
        }
        for (Point stone : stones) {
            matrix[stone.getX()][stone.getY()] = 1;
        }
        for (Point point : snake) {
            matrix[point.getX()][point.getY()] = 1;
        }
        return matrix;
    }

}

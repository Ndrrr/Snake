package com.codenjoy.dojo.snake.client.handler;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2018 - 2022 Codenjoy
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

import com.codenjoy.dojo.services.Direction;
import com.codenjoy.dojo.services.Point;
import com.codenjoy.dojo.snake.client.Board;

public class StoneHandler extends DirectionHandler {

    public StoneHandler() {
    }

    public StoneHandler(DirectionHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public Direction handle(Direction previous, Board board) {
        Point stone = board.getStones().get(0);
        Point head = board.getHead();
        Direction result = previous;
        if(head == null || stone == null) {
            return this.nextHandler.handle(previous,board);
        }
        int x = head.getX();
        int y = head.getY();
        int stoneX = stone.getX();
        int stoneY = stone.getY();
        long random = Math.round(Math.random());

        if(previous == Direction.RIGHT && x + 1 == stoneX && y == stoneY) {
            result = random % 2 == 0 ? Direction.UP : Direction.DOWN;
        }
        if(previous == Direction.LEFT && x - 1 == stoneX && y == stoneY) {
            result = random % 2 == 0 ? Direction.UP : Direction.DOWN;
        }
        if(previous == Direction.UP && x == stoneX && y + 1 == stoneY) {
            result = random % 2 == 0 ? Direction.RIGHT : Direction.LEFT;
        }
        if(previous == Direction.DOWN && x == stoneX && y - 1 == stoneY) {
            result = random % 2 == 0 ? Direction.RIGHT : Direction.LEFT;
        }
        if(nextHandler != null) {
            result = nextHandler.handle(result, board);
        }
        return result;

    }
}

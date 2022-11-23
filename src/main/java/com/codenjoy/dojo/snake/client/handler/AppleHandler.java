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

import java.util.Optional;

public class AppleHandler extends DirectionHandler{

    public AppleHandler() {
    }

    public AppleHandler(DirectionHandler nextHandler) {
        super(nextHandler);
    }
    @Override
    public Direction handle(Direction previous, Board board) {
        Point apple = board.getApples().get(0);
        Point head = board.getHead();
        Direction result = previous;
        if(head == null || apple == null) {
            return this.nextHandler.handle(previous,board);
        }
        int x = head.getX();
        int y = head.getY();
        int appleX = apple.getX();
        int appleY = apple.getY();
        long random = Math.round(Math.random());
        if(random % 2 == 0) {
            result = checkX(x, appleX).orElse(result);
            result = checkY(y, appleY).orElse(result);
        } else {
            result = checkY(y, appleY).orElse(result);
            result = checkX(x, appleX).orElse(result);
        }
        if(nextHandler != null) {
            result = nextHandler.handle(result, board);
        }
        return result;
    }

    private Optional<Direction> checkX(int x, int appleX) {
        if (x < appleX) {
            return Optional.of(Direction.RIGHT);
        } else if (x > appleX) {
            return Optional.of(Direction.LEFT);
        }
        return Optional.empty();
    }

    private Optional<Direction> checkY(int y, int appleY) {
        if (y < appleY) {
            return Optional.of(Direction.UP);
        } else if (y > appleY) {
            return Optional.of(Direction.DOWN);
        }
        return Optional.empty();
    }

}

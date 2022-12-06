package com.codenjoy.dojo.snake.client;

import com.codenjoy.dojo.services.Direction;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsOnGrid {

    private BfsOnGrid() {
    }

    public static Direction getDirectionBFSHandler(int[][] matrix, int startX, int startY, int endX, int endY) {
        // in case smth unexpected happens
        try {
            List<PointWithPath> path = bfs(matrix, startX, startY, endX, endY);
            if (path == null || path.isEmpty()) {
                return stayAlive(matrix, startX, startY);
            }

            PointWithPath nextPoint = path.get(1);
            return getDirection(startX, startY, nextPoint.x, nextPoint.y);
        } catch (Exception e) {
            e.printStackTrace();
            return stayAlive(matrix, startX, startY);
        }
    }

    private static List<PointWithPath> bfs(int[][] matrix, int startX, int startY, int endX, int endY) {
        int[][] visited = new int[matrix.length][matrix[0].length];
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        Queue<PointWithPath> queue = new LinkedList<>();
        queue.add(new PointWithPath(startX, startY));
        visited[startX][startY] = 1;
        while(!queue.isEmpty()){
            PointWithPath point = queue.poll();
            if(point.x == endX && point.y == endY){
                point.path.add(point);
                return point.path;
            }
            for(int i = 0; i < 4; i++){
                int newX = point.x + dx[i];
                int newY = point.y + dy[i];
                if(isValid(matrix, visited, newX, newY)){
                    visited[newX][newY] = 1;
                    PointWithPath newPoint = new PointWithPath(newX, newY);
                    newPoint.path.addAll(point.path);
                    newPoint.path.add(point);
                    queue.add(newPoint);
                }
            }
        }
        return Collections.emptyList();
    }
    
    private static Direction stayAlive(int[][] matrix, int startX, int startY){
        if (matrix[startX+1][startY] != 1) {
            return Direction.RIGHT;
        } else if (matrix[startX-1][startY] != 1) {
            return Direction.LEFT;
        } else if (matrix[startX][startY+1] != 1) {
            return Direction.UP;
        } else if (matrix[startX][startY-1] != 1) {
            return Direction.DOWN;
        }
        // death is inevitable :/
        return Direction.RIGHT;
    }

    private static boolean isValid(int[][] matrix, int[][] visited, int x, int y){
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length && matrix[x][y] == 0 && visited[x][y] == 0;
    }

    private static Direction getDirection(int startX, int startY, int endX, int endY) {
        if (startX == endX) {
            if (startY < endY) {
                return Direction.UP;
            } else {
                return Direction.DOWN;
            }
        } else {
            if (startX < endX) {
                return Direction.RIGHT;
            } else {
                return Direction.LEFT;
            }
        }
    }

    private static class PointWithPath {

        int x;
        int y;
        List<PointWithPath> path;
        public PointWithPath(int x, int y) {
            this.x = x;
            this.y = y;
            path = new LinkedList<>();
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

}

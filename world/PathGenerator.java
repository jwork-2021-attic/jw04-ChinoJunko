package world;

import java.util.Stack;

public class PathGenerator {
    int[][] path;
    int[][] maze;
    int width;
    int height;
    private class Node {
        public final int x;
        public final int y;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    Stack<Node> stack;

    PathGenerator(int[][] maze){
        width = maze.length;
        height = maze[0].length;
        this.maze = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.maze[i][j] = maze[i][j];
            }
        }
        path = new int[width][height];
        stack = new Stack<Node>();
    }

    public void generatePath() {
        stack.push(new Node(width-1,height-1));
        while (!stack.empty()) {
            Node next = stack.pop();
            if(check(next.x+1,next.y)){//up-1, down-2, left-3, right-4
                path[next.x+1][next.y] = 3;
                maze[next.x+1][next.y] = 0;
                stack.push(new Node(next.x+1, next.y));
            }
            if(check(next.x-1,next.y)){//up-1, down-2, left-3, right-4
                path[next.x-1][next.y] = 4;
                maze[next.x-1][next.y] = 0;
                stack.push(new Node(next.x-1, next.y));
            }
            if(check(next.x,next.y+1)){//up-1, down-2, left-3, right-4
                path[next.x][next.y+1] = 1;
                maze[next.x][next.y+1] = 0;
                stack.push(new Node(next.x, next.y+1));
            }
            if(check(next.x,next.y-1)){//up-1, down-2, left-3, right-4
                path[next.x][next.y-1] = 2;
                maze[next.x][next.y-1] = 0;
                stack.push(new Node(next.x, next.y-1));
            }
        }
    }

    private boolean check(int x, int y){
        if(x<0||x>=width||y<0||y>=height)   return false;
        return maze[x][y]>0;
    }

    public int[][] getPath(){
        return path;
    }

}

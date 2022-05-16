package com.company;

import javaSimulation.Head;
import javaSimulation.Process;
import javaSimulation.Random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptimalPath extends Process {


    public void actions() {
        List<Integer> path = Collections.emptyList();
        PathFinder finder = new PathFinder(0, path, 0);
        activate(finder);
        hold(1000000);
    }

    class PathFinder extends Process {
        int[][] matrix = {{0, 5, 7, 0, 0, 0, 0, 0},
                {5, 0, 4, 2, 14, 0, 0, 0},
                {7, 4, 0, 0, 10, 0, 0, 0},
                {0, 2, 0, 0, 0, 6, 0, 0},
                {0, 14, 10, 0, 0, 9, 11, 0},
                {0, 0, 0, 6, 9, 0, 0, 12},
                {0, 0, 0, 0, 11, 0, 0, 15},
                {0, 0, 0, 0, 0, 12, 15, 0}
        };


        int pos;
        int cost;
        List path;


        PathFinder(int pos_, List path_, int cost_) {
            pos = pos_;
            List<Integer> newList = new ArrayList<Integer>(path_);
            path = newList;
            cost = cost_;
        }

        public void actions() {
            hold(cost);
            path.add(pos);
            for (int i = 0; i < 8; i++) {
                Boolean x = path.contains(i);
                if (matrix[pos][i] != 0 && !x) {
                    PathFinder finder = new PathFinder(i, path, matrix[pos][i]);
                    activate(finder);
                }

                if (pos == 7) {
                    int len = 0;

                    for (int j = 1; j < path.size(); j++) {
                        len += matrix[(int) path.get(j - 1)][(int) path.get(j)];
                    }
                    System.out.println("Path was "+path+" and price of path was " +len);

                }
            }
            this.terminated();
        }
    }


    public static void main(String args[]) {
        activate(new OptimalPath());
    }
}

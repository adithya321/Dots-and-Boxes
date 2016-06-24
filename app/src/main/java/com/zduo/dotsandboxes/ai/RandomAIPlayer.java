package com.zduo.dotsandboxes.ai;

import com.zduo.dotsandboxes.model.Direction;
import com.zduo.dotsandboxes.model.Line;
import com.zduo.dotsandboxes.model.Player;

import java.util.ArrayList;
import java.util.List;

public class RandomAIPlayer extends Player {

    protected final ArrayList<Line> safeLines;
    protected final ArrayList<Line> goodLines;
    protected final ArrayList<Line> badLines;

    public RandomAIPlayer(String name) {
        super(name);

        safeLines = new ArrayList<>();
        goodLines = new ArrayList<>();
        badLines = new ArrayList<>();
    }

    protected Line nextMove() {
        if (goodLines.size() != 0) return getBestGoodLine();
        if (safeLines.size() != 0) return getRandomSafeLine();

        return getRandomBadLine();
    }

    public Line move() {
        initialiseLines();
        return nextMove();
    }

    private void initialiseLines() {
        goodLines.clear();
        badLines.clear();
        safeLines.clear();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (!isHorizontalLineOccupied(i, j)) {
                    if (i == 0) {
                        switch (getBox(i, j).occupiedLineCount()) {
                            case 3:
                                goodLines.add(new Line(Direction.HORIZONTAL, i, j));
                                break;
                            case 2:
                                badLines.add(new Line(Direction.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                                safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                        }
                    } else if (i == 5) {
                        switch (getBox(i - 1, j).occupiedLineCount()) {
                            case 3:
                                goodLines.add(new Line(Direction.HORIZONTAL, i, j));
                                break;
                            case 2:
                                badLines.add(new Line(Direction.HORIZONTAL, i, j));
                                break;
                            case 1:
                            case 0:
                                safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                        }
                    } else {
                        if (getBox(i, j).occupiedLineCount() == 3
                                || getBox(i - 1, j).occupiedLineCount() == 3)
                            goodLines.add(new Line(Direction.HORIZONTAL, i, j));

                        if (getBox(i, j).occupiedLineCount() == 2
                                || getBox(i - 1, j).occupiedLineCount() == 2)
                            badLines.add(new Line(Direction.HORIZONTAL, i, j));

                        if (getBox(i, j).occupiedLineCount() < 2
                                && getBox(i - 1, j).occupiedLineCount() < 2)
                            safeLines.add(new Line(Direction.HORIZONTAL, i, j));
                    }
                }

                if (!isVerticalLineOccupied(j, i)) {
                    if (i == 0) {
                        if (getBox(j, i).occupiedLineCount() == 3)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));
                    } else if (i == 5) {
                        if (getBox(j, i - 1).occupiedLineCount() == 3)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));
                    } else {
                        if (getBox(j, i).occupiedLineCount() == 3
                                || getBox(j, i - 1).occupiedLineCount() == 3)
                            goodLines.add(new Line(Direction.VERTICAL, j, i));

                        if (getBox(j, i).occupiedLineCount() == 2
                                || getBox(j, i - 1).occupiedLineCount() == 2)
                            badLines.add(new Line(Direction.VERTICAL, j, i));

                        if (getBox(j, i).occupiedLineCount() < 2
                                && getBox(j, i - 1).occupiedLineCount() < 2)
                            safeLines.add(new Line(Direction.VERTICAL, j, i));
                    }
                }
            }
        }
    }

    protected Box getBox(int row, int column) {
        return new Box(isVerticalLineOccupied(row, column), isHorizontalLineOccupied(row, column),
                isVerticalLineOccupied(row, column + 1), isHorizontalLineOccupied(row + 1, column));
    }

    protected boolean isHorizontalLineOccupied(int row, int column) {
        return getGame().isLineOccupied(Direction.HORIZONTAL, row, column);
    }

    protected boolean isVerticalLineOccupied(int row, int column) {
        return getGame().isLineOccupied(Direction.VERTICAL, row, column);
    }

    protected Line getBestGoodLine() {
        return goodLines.get(0);
    }

    protected Line getRandomSafeLine() {
        return getRandomLine(safeLines);
    }

    protected Line getRandomBadLine() {
        return getRandomLine(badLines);
    }

    private Line getRandomLine(List<Line> list) {
        return list.get((int) (list.size() * Math.random()));
    }
}

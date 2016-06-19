package com.zduo.dotsandboxes.model;

import java.util.Observable;

public class Game extends Observable {
    private Player[] players;
    private int currentPlayerIndex;
    private int width;
    private int height;
    private Player[][] occupied;
    private boolean[][] horizontalLines;
    private boolean[][] verticalLines;
    private Line latestLine;

    public Game(int width, int height, Player[] players) {
        this.width = width;
        this.height = height;
        this.players = players;

        occupied = new Player[height][width];
        horizontalLines = new boolean[height + 1][width];
        verticalLines = new boolean[height][width + 1];

        addPlayersToGame(players);
        currentPlayerIndex = 0;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Line getLatestLine() {
        return latestLine;
    }

    private void addPlayersToGame(Player[] players) {
        for (Player player : players) {
            player.addToGame(this);
        }
    }

    public void start() {
        while (!isGameFinished()) {
            addMove(currentPlayer().move());
            setChanged();
            notifyObservers();
        }
    }

    public void addMove(Line move) {
        if (isLineOccupied(move)) {
            return;
        }
        boolean newBoxOccupied = tryToOccupyBox(move);
        setLineOccupied(move);
        latestLine = move;

        if (!newBoxOccupied)
            toNextPlayer();
    }

    public Player currentPlayer() {
        return players[currentPlayerIndex];
    }

    public boolean isLineOccupied(Direction direction, int row, int column) {
        return isLineOccupied(new Line(direction, row, column));
    }

    public boolean isLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                return horizontalLines[line.row()][line.column()];
            case VERTICAL:
                return verticalLines[line.row()][line.column()];
        }
        throw new IllegalArgumentException(line.direction().toString());
    }

    public Player getBoxOccupier(int row, int column) {
        return occupied[row][column];
    }

    public int getPlayerOccupyingBoxCount(Player player) {
        int count = 0;
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getBoxOccupier(i, j) == player)
                    count++;
            }
        }
        return count;
    }

    private boolean tryToOccupyBox(Line move) {
        boolean rightOccupied = tryToOccupyRightBox(move);
        boolean underOccupied = tryToOccupyUnderBox(move);
        boolean upperOccupied = tryToOccupyUpperBox(move);
        boolean leftOccupied = tryToOccupyLeftBox(move);
        return leftOccupied || rightOccupied || upperOccupied || underOccupied;
    }

    private void setLineOccupied(Line line) {
        switch (line.direction()) {
            case HORIZONTAL:
                horizontalLines[line.row()][line.column()] = true;
                break;
            case VERTICAL:
                verticalLines[line.row()][line.column()] = true;
                break;
        }
    }

    private void setBoxOccupied(int row, int column, Player player) {
        occupied[row][column] = player;
    }

    private boolean tryToOccupyUpperBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() <= 0)
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row() - 1, move.column() + 1)) {
            setBoxOccupied(move.row() - 1, move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyUnderBox(Line move) {
        if (move.direction() != Direction.HORIZONTAL || move.row() >= (height))
            return false;
        if (isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column())
                && isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)) {
            setBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyLeftBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() <= 0)
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column() - 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column() - 1)) {
            setBoxOccupied(move.row(), move.column() - 1, currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private boolean tryToOccupyRightBox(Line move) {
        if (move.direction() != Direction.VERTICAL || move.column() >= (width))
            return false;
        if (isLineOccupied(Direction.VERTICAL, move.row(), move.column() + 1)
                && isLineOccupied(Direction.HORIZONTAL, move.row(), move.column())
                && isLineOccupied(Direction.HORIZONTAL, move.row() + 1, move.column())) {
            setBoxOccupied(move.row(), move.column(), currentPlayer());
            return true;
        } else {
            return false;
        }
    }

    private void toNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }

    protected boolean isGameFinished() {
        for (int i = 0; i < getHeight(); i++) {
            for (int j = 0; j < getWidth(); j++) {
                if (getBoxOccupier(i, j) == null)
                    return false;
            }
        }
        return true;
    }

    public Player getWinner() {
        if (!isGameFinished()) {
            return null;
        }

        int[] playersOccupyingBoxCount = new int[players.length];
        for (int i = 0; i < players.length; i++) {
            playersOccupyingBoxCount[i] = getPlayerOccupyingBoxCount(players[i]);
        }

        if (playersOccupyingBoxCount[0] > playersOccupyingBoxCount[1])
            return players[0];
        else
            return players[1];
    }
}

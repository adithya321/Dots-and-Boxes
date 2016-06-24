package com.zduo.dotsandboxes.model;

public class HumanPlayer extends Player {
    private final Line[] inputBuffer = new Line[1];

    public HumanPlayer(String name) {
        super(name);
    }

    public void add(Line line) {
        synchronized (inputBuffer) {
            inputBuffer[0] = line;
            inputBuffer.notify();
        }
    }

    private Line getInput() {
        synchronized (inputBuffer) {
            if (inputBuffer[0] != null) {
                Line temp = inputBuffer[0];
                inputBuffer[0] = null;
                return temp;
            }
            try {
                inputBuffer.wait();
            } catch (InterruptedException ignored) {
            }
            return this.getInput();
        }
    }

    @Override
    public Line move() {
        return getInput();
    }
}

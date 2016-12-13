/*
 * Dots and Boxes
 * Copyright (C) 2016  zDuo (Adithya J, Vazbloke)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

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

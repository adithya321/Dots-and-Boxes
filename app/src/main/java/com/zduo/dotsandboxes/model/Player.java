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

public abstract class Player {
    protected final String name;
    protected Graph game;

    public Player(String name) {
        this.name = name;
    }

    public static int indexIn(Player player, Player[] players) {
        for (int i = 0; i < players.length; i++) {
            if (player == players[i])
                return i;
        }
        return -1;
    }

    public abstract Line move();

    public Graph getGame() {
        return game;
    }

    public void addToGame(Graph game) {
        this.game = game;
    }

    public String getName() {
        return name;
    }
}

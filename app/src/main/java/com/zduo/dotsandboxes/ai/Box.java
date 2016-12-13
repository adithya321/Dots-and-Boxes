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

package com.zduo.dotsandboxes.ai;

public class Box {
    boolean left;
    boolean top;
    boolean right;
    boolean bottom;
    boolean occupied;

    Box(boolean l, boolean t, boolean r, boolean b) {
        this.left = l;
        this.top = t;
        this.right = r;
        this.bottom = b;

        this.occupied = (l && t && r && b);
    }

    int occupiedLineCount() {
        int count = 0;

        if (this.left) count++;
        if (this.right) count++;
        if (this.top) count++;
        if (this.bottom)
            count++;

        return count;
    }
}

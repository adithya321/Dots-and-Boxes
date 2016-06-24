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

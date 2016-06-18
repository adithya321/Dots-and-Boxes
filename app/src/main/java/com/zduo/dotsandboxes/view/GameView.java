package com.zduo.dotsandboxes.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zduo.dotsandboxes.R;

public class GameView extends View {
    protected static final float radius = (float) 14 / 824;
    protected static final float start = (float) 6 / 824;
    protected static final float stop = (float) 819 / 824;
    protected static final float add1 = (float) 18 / 824;
    protected static final float add2 = (float) 2 / 824;
    protected static final float add3 = (float) 14 / 824;
    protected static final float add4 = (float) 141 / 824;
    protected static final float add5 = (float) 159 / 824;
    protected static final float add6 = (float) 9 / 824;

    protected Paint paint;

    public GameView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(0x00FFFFFF);
        int min = Math.min(getWidth(), getHeight());
        float radius = GameView.radius * min;
        float start = GameView.start * min;
        float stop = GameView.stop * min;
        float add1 = GameView.add1 * min;
        float add2 = GameView.add2 * min;
        float add4 = GameView.add4 * min;
        float add5 = GameView.add5 * min;
        float add6 = GameView.add6 * min;

        //paint board
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        for (int i = 0; i < 6; i++) {
            canvas.drawLine(start + add5 * i, start, start + add5 * i, stop,
                    paint);
            canvas.drawLine(start + add5 * i + add1, start, start + add5 * i
                    + add1, stop, paint);
            canvas.drawLine(start, start + add5 * i, stop, start + add5 * i,
                    paint);
            canvas.drawLine(start, start + add5 * i + add1, stop, start + add5
                    * i + add1, paint);
        }

        //paint lines
        paint.setColor(0xFFFFFFFF);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                canvas.drawRect(start + add5 * j + add1, start + add5 * i
                        + add2, start + add5 * (j + 1), start + add5 * i + add1
                        - add2, paint);

                canvas.drawRect(start + add5 * i + add2, start + add5 * j
                        + add1, start + add5 * i + add1 - add2, start + add5
                        * (j + 1), paint);
            }
        }

        //paint boxes
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                paint.setColor(Color.TRANSPARENT);
                canvas.drawRect(start + add5 * i + add1 + add2, start
                        + add5 * j + add1 + add2, start + add5 * i + add1
                        + add4 - add2, start + add5 * j + add1 + add4
                        - add2, paint);
            }
        }

        //paint points
        paint.setColor(getResources().getColor(R.color.colorAccent));
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                canvas.drawCircle(start + add6 + j * add5 + 1, start + add6 + i
                        * add5 + 1, radius, paint);
            }
        }
    }
}

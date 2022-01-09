package com.example.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyPaint extends View {
    int w, h;
    Paint paint, paintDelete, paintShow;
    ArrayList<Paint> paintColor;
    List<Line> lines;
    Bitmap delete;

    int radius = 20, mode = 1, colorMode = 0, colorMode2 = 0;
    Path path;

    public MyPaint(Context context) {
        super(context);
    }

    public MyPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.w = w;
        this.h = h;
        init();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.red));
        paintDelete = new Paint();
        paintDelete.setColor(getResources().getColor(R.color.white));
        paintShow = new Paint();
        paintShow.setColor(getResources().getColor(R.color.black));
        delete = BitmapFactory.decodeResource(getResources(), R.drawable.toy);
        delete = Bitmap.createScaledBitmap(delete, 25, 25, true);
        paintColor = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            paintColor.add(new Paint());
            paintColor.get(i).setAntiAlias(true);
            paintColor.get(i).setStrokeWidth(radius);
            paintColor.get(i).setStyle(Paint.Style.STROKE);
            paintColor.get(i).setStrokeJoin(Paint.Join.ROUND);
            paintColor.get(i).setStrokeCap(Paint.Cap.ROUND);
        }
        paintColor.get(0).setColor(getResources().getColor(R.color.green));
        paintColor.get(1).setColor(getResources().getColor(R.color.yellow));
        paintColor.get(2).setColor(getResources().getColor(R.color.black));
        paintColor.get(3).setColor(getResources().getColor(R.color.red2));
        paintColor.get(4).setColor(getResources().getColor(R.color.blue));
        paintColor.get(5).setColor(getResources().getColor(R.color.purple));
        paintColor.get(6).setColor(getResources().getColor(R.color.white));

        path = new Path();
        lines = new ArrayList<>();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < lines.size(); i++) {
            paintColor.get(lines.get(i).colorM).setStrokeWidth(lines.get(i).size);
            canvas.drawPath(lines.get(i).path, paintColor.get(lines.get(i).colorM));
        }
        canvas.drawPath(path, paintColor.get(colorMode));
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                path = new Path();
                path.moveTo(touchX, touchY);
                lines.add(new Line(path, colorMode, radius));
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(touchX, touchY);

                break;
            case MotionEvent.ACTION_UP:
                lines.add(new Line(path, colorMode, radius));
                path = new Path();
                break;
        }
        invalidate();
        return true;
    }

    public void Delete() {
        colorMode2 = colorMode;
        colorMode = 6;
    }

    public void stDelete() {
        colorMode = colorMode2;
    }


    public void colorMode(int num) {
        colorMode = num;
    }

    public void clear(){
        lines.clear();
        invalidate();
    }
}

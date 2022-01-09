package com.example.paint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Line {
    Path path;
    int colorM;
    int size;

    public Line(Path path, int colorM, int size) {
        this.path = path;
        this.colorM = colorM;
        this.size = size;
    }
}

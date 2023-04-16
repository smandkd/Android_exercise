package com.example.recordfragement_v1;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MyView extends View {
    private Paint circlePaint;


    public MyView(Context context) {
        this(context, null);

    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Context c = this.getContext();
        Resources r = this.getResources();
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(ContextCompat.getColor(c, R.color.dark_blue));
        circlePaint.setStrokeWidth(5);
        circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureHeight = measureHeight(heightMeasureSpec);
        int measureWidth = measureHeight(widthMeasureSpec);

        int d = Math.min(measureWidth, measureHeight);

        setMeasuredDimension(d, d);
    }

    private int measure(int measureSpec) {
        int result = 0;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if( specMode == MeasureSpec.UNSPECIFIED ) {
            result = 200;
        }
        else {
            result = specSize;
        }

        return result;
    }

    private int measureHeight(int measureSpec) {
        int spectMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        return specSize;
    }
    private int measureWeight(int measureSpec) {
        int spectMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        return specSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int mMeasureWidth = getMeasuredHeight();
        int mMeasureHeight = getMeasuredWidth();

        int px = mMeasureWidth/2;
        int py = mMeasureHeight/2;

        int radius = Math.min(px, py);

        //canvas.drawCircle(px, py, radius, circlePaint);
        //canvas.drawCircle(px, py, radius, circlePaint);
    }
}

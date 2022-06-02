package com.example.fuchart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public
/**
 * com.example.fuchart.views
 * FuChart
 * 2022/06/02 
 * Created by fuyan on Admin
 */

class ScrollLine extends ScrollView {
    private List<Integer> test;
    private int width;
    private float moveX;
    private float moveY;
    private float downX;
    private float downY;

    private OnScrollChangeListener mScrollChangeListener;
    public ScrollLine(Context context) {
        super(context);
    }

    public ScrollLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        test = new ArrayList<>();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        paint.setStrokeWidth(5);
        paint.setColor(Color.RED);
        for (int i1 = 0; i1 < 16; i1++) {
            for (int i = 0; i < width / 55; i++) {
                canvas.drawCircle(i*55,155,5,paint);
            }
        }
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX = event.getRawX();
                downY = event.getRawY();
                break;
                case MotionEvent.ACTION_MOVE:
                    moveX = event.getRawX();
                    moveY = event.getRawY();
                    if (Math.abs(moveX-downX)>10){
                        scrollTo(Math.round(moveX),Math.round(moveY));
                    }
                    break;
            default:
                break;
        }
        return super.onInterceptHoverEvent(event);
    }
}

package com.example.fuchart.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.fuchart.bean.AxisBean;

import java.util.ArrayList;
import java.util.List;

public
/**
 * com.example.fuchart.views
 * FuChart
 * 2022/06/01 
 * Created by fuyan on Admin
 */

class ChartAxis extends View {
    private int mWidth;
    private int mHeight;

    private Paint mXPaint;
    private Paint mYPaint;
    private Paint mCPaint;
    private Paint mBrokenLinePaint;
    private Paint mTextPaint;
    private Paint mPointTextPaint;

    private List<String> xDataList;
    private List<Integer> yDataList;
    private List<Integer> xAxis;
    private List<Integer> yAxis;

    private AxisBean mAxisBean;

    private int xStart;
    private int yStart;
    private int xEnd;
    private int yEnd;
    private int xDown;
    private int yDown;
    private int xMove;

    private onDrawChartListener mOnDrawChartListener;
    private OnLayoutChangeListener mLayoutChangeListener;

    private PointF pStart, pEnd;

    public ChartAxis(Context context) {
        super(context);
    }

    public ChartAxis(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mXPaint = new Paint();
        mYPaint = new Paint();
        mCPaint = new Paint();
        mTextPaint = new Paint();
        mPointTextPaint = new Paint();
        mAxisBean = new AxisBean();
        initBrokenLine();

        setPaintColor(Color.BLACK, Color.BLACK);
        setLineSize(6);
        mYPaint.setStyle(Paint.Style.FILL);
        mXPaint.setStyle(Paint.Style.FILL);
        mXPaint.setAntiAlias(true);
        mYPaint.setAntiAlias(true);
        mTextPaint.setTextSize(40);
        mPointTextPaint.setTextSize(20);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mPointTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setColor(Color.BLACK);
        mPointTextPaint.setColor(Color.BLACK);

        xDataList = new ArrayList<>();
        yDataList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            xDataList.add("2018");
            yDataList.add(55);
        }

    }

    public static ChartAxis newInstance(Context context) {
        return new ChartAxis(context);
    }

    /**
     * 初始化曲线、折线
     */
    private void initBrokenLine() {
        mBrokenLinePaint = new Paint();//折线
        mBrokenLinePaint.setColor(Color.GRAY);
        mBrokenLinePaint.setStrokeWidth(2);
        mBrokenLinePaint.setStyle(Paint.Style.STROKE);
        mBrokenLinePaint.setStrokeCap(Paint.Cap.ROUND);
        mBrokenLinePaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);
        xStart = 40;
        yStart = mHeight - 40;
        xEnd = mWidth - 40;
        yEnd = mHeight - 40;
        getAxisList();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOnDrawChartListener != null) {
            mOnDrawChartListener.onDrawChart();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getRawX();
                yDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = (int) event.getRawX();

                break;
            default:
                break;
        }
        if (Math.abs(xMove - xDown) > 50) {
            Log.d("1eo1iu", "onTouchEvent:"+xMove);

            scrollTo(Math.abs(xMove-xDown),0);
            return true;
        }
        return super.onTouchEvent(event);
    }



    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        super.addOnLayoutChangeListener(listener);
        if (mLayoutChangeListener == null){
            mLayoutChangeListener = listener;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //x
        canvas.drawLine(xStart, yStart, xEnd, yEnd, mXPaint);
        canvas.drawText("年份", xEnd - 20, yEnd - 20, mTextPaint);

        //y
        canvas.drawLine(xStart, yStart, 40, 40, mYPaint);
        canvas.drawText("体重", 40, 30, mTextPaint);
        for (int i = 0; i < 3; i++) {
            int xAxi = mAxisBean.getxAxis().get(i);
            int yAxi = mAxisBean.getyAxis().get(i);
            //画刻度
            canvas.drawLine(xAxi, yStart, xAxi, yStart - 10, mXPaint);
            canvas.drawLine(xStart, yAxi, xStart + 10, yAxi, mYPaint);
            //画文字
            canvas.drawText(String.valueOf(xDataList.get(i)), xAxi - 10, yStart + 30, mPointTextPaint);
            canvas.drawText(String.valueOf(yDataList.get(i)), xStart - 20, yAxi + 10, mPointTextPaint);
            //画点
            canvas.drawCircle(xAxi, yAxi, 5, mCPaint);
            canvas.drawCircle(xAxi + 500, yAxi - 10, 5, mCPaint);
            if (i == 2) {
                return;
            }
            int xNext = mAxisBean.getxAxis().get(i + 1);
            int yNext = mAxisBean.getyAxis().get(i + 1);
            drawScrollLine(canvas);
        }
        if (mOnDrawChartListener != null) {
            mOnDrawChartListener.onDrawChart();
        }
    }

    private void drawScrollLine(Canvas canvas) {

        PointF pStart, pEnd;
        List<PointF> points = getPoints();
        Path path = new Path();
        for (int i = 0; i < points.size() - 1; i++) {
            pStart = points.get(i);
            pEnd = points.get(i + 1);
            PointF point3 = new PointF();
            PointF point4 = new PointF();
            float wd = (pStart.x + pEnd.x) / 2;
            point3.x = wd;
            point3.y = pStart.y;
            point4.x = wd;
            point4.y = pEnd.y;
            path.moveTo(pStart.x, pStart.y);
            path.cubicTo(point3.x, point3.y, point4.x, point4.y, pEnd.x, pEnd.y);
            canvas.drawPath(path, mBrokenLinePaint);
        }
    }

    /**
     * 获取坐标点
     */
    private List<PointF> getPoints() {
        ArrayList<PointF> points = new ArrayList<>();
        for (Integer pair : mAxisBean.getxAxis()) {
            points.add(new PointF((float) pair, (float) pair));
        }
        return points;
    }

    public ChartAxis setPaintColor(int xColor, int yColor) {
        mXPaint.setColor(xColor);
        mYPaint.setColor(yColor);
        mCPaint.setColor(Color.RED);
        return this;
    }

    public ChartAxis setLineSize(int size) {
        mXPaint.setStrokeWidth(size);
        mYPaint.setStrokeWidth(size);
        return this;
    }

    private void getAxisList() {
        xAxis = new ArrayList<>();
        yAxis = new ArrayList<>();
        int x = (mWidth - 80) / xDataList.size();
        int y = (mHeight - 80) / yDataList.size();
        int sum = 0;
        int sumy = 0;
        for (int i = 0; i < xDataList.size(); i++) {
            sum += x;
            sumy += y;
            xAxis.add(sum);
            yAxis.add(sumy);
        }
        mAxisBean.setxAxis(xAxis);
        mAxisBean.setyAxis(yAxis);
    }

    public void setOnDrawListener(onDrawChartListener onDrawListener) {
        if (mOnDrawChartListener != null) {
            this.mOnDrawChartListener = onDrawListener;
        }
    }

    public interface onDrawChartListener {
        void onDrawChart();
    }
}

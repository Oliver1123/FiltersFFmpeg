package com.example.oliver.filtersffmpeg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.TypeVariable;

/**
 * Created by oliver on 10.03.16.
 */
public class GridImageView extends View {
    private int mRowCount, mColCount;
    private float mLineWidth;
    private int mLineColor;
    private Paint mPaint;

    public GridImageView(Context context) {
        super(context);
        init();
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setRowCount(int rowCount) {
        mRowCount = rowCount;
        invalidate();
    }

    public void setColCount(int colCount) {
        mColCount = colCount;
        invalidate();
    }

    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
        mPaint.setStrokeWidth(mLineWidth);
        invalidate();
    }

    public void setLineColor(int color) {
        mLineColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    private void init() {
        mRowCount = 3;
        mColCount = 3;
        mLineColor = Color.WHITE;
        mLineWidth = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(mLineWidth);
        mPaint.setColor(mLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawRect(0, 0, getWidth(),getHeight(), mPaint);
        float rowStep = getHeight() / mRowCount;
        for (int i = 0; i < mRowCount; i++) {
            canvas.drawLine(0, rowStep * i, getWidth(), rowStep * i, mPaint);
        }
        float colStep = getHeight() / mColCount;
        for (int i = 0; i < mColCount; i++) {
            canvas.drawLine(colStep * i, 0, colStep * i, getHeight(), mPaint);
        }
    }
}

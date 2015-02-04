package com.greenapp.todolist.to_dolist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by kabardinov133238 on 04.02.2015.
 */
public class TodoListItemView extends TextView {

    public TodoListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TodoListItemView(Context context) {
        super(context);
        init();
    }

    public TodoListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private Paint marginPaint;
    private Paint linePaint;
    private int paperColor;
    private float margin;

    private void init() {
//получаем ресурсы из файла ресурсов, т н инициализация
        Resources myResources = getResources();
        marginPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        marginPaint.setColor(myResources.getColor(R.color.notepad_margin));
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(myResources.getColor(R.color.notepad_lines));
        paperColor = myResources.getColor(R.color.notepad_paper);
        margin = myResources.getDimension(R.dimen.notepad_margin);

    }

    @Override
    public void onDraw(Canvas canvas) {
        //собственно рисование
        canvas.drawColor(paperColor);

        canvas.drawLine(0, 0, getMeasuredHeight(), 0, linePaint);
        canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(), getMeasuredHeight(), linePaint);

        canvas.drawLine(margin, 0, margin, getMeasuredHeight(), marginPaint);

        canvas.save();
        canvas.translate(margin, 0);

        super.onDraw(canvas);
        canvas.restore();
    }
}

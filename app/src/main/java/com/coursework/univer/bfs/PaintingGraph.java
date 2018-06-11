package com.coursework.univer.bfs;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by user on 13.05.2018.
 */

public class PaintingGraph extends ImageView {

    private boolean drawCustomCanvas;
    private Paint mPaint;
    public static int p;
    final Random random = new Random();
    private List<Integer> vertexForAddText = new ArrayList<>();

    Path path;
    RectF rectF;

    public PaintingGraph(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public PaintingGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public PaintingGraph(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public void setDrawCustomCanvas(boolean drawCustomCanvas) {
        this.drawCustomCanvas = drawCustomCanvas;
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(5);
        path = new Path();
        mPaint.setTextSize(75);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if (!drawCustomCanvas) {
            super.onDraw(canvas);
        } else {
            for (int i = 0; i < Graph.getVertexCount(); i++) {
                for (int j = 0; j < Graph.getVertexCount(); j++) {
                    if(Graph.getGraphMatrix()[i][j] == 1){
                        canvas.drawLine(Graph.getVerticesKoord().get(i).getX(), Graph.getVerticesKoord().get(i).getY(),
                                Graph.getVerticesKoord().get(j).getX(), Graph.getVerticesKoord().get(j).getY(), mPaint);
                    }
                }
            }
            for (int i = 0; i < vertexForAddText.size(); i++) {
                    canvas.drawText(String.valueOf(i+1), Graph.getVerticesKoord().get(vertexForAddText.get(i)).getX(),
                        Graph.getVerticesKoord().get(vertexForAddText.get(i)).getY(), mPaint);
            }
        }

    }
    public void addVertexToVertexForAddText(int k){
        vertexForAddText.add(k);
    }

    public void removeVertexForAddText(){
        vertexForAddText.removeAll(vertexForAddText);
    }


}
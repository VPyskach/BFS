package com.coursework.univer.bfs;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends AppCompatActivity implements View.OnClickListener{

    String LOG_TAG = "PracticeActivity";
    RelativeLayout fabContainer;
    Button button1;
    Button button2;
    TextView textViewResult;
    PaintingGraph imgView;
    GestureDetector gestureDetector;
    List<View> vertexFabList = new ArrayList<>();
    private DisplayMetrics metrics = new DisplayMetrics();
    float x, y, fX, fY;
    Graph graph;
    int firstChangedVertex;
    int secondChangedVertex;
    int idAction = 0; //1 - add edge, 2 - remove edge

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
            public void onLongPress(MotionEvent e) {
                createDialog();
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }
        });

        fabContainer = (RelativeLayout) findViewById(R.id.graph_container);
        imgView = (PaintingGraph) findViewById(R.id.view);

        button1 = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);

        graph = new Graph();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private void createDialog() {
        AlertDialog.Builder builder;
        final String[] action = {"Додати вершину"};
        builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addVertex();
            }
        });
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void createDialogInVertex(final int j) {
        AlertDialog.Builder builder;
        final String[] action = {"Додати зв'язок", "Видалити зв'язок", "Видалити вершину"};
        builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        firstChangedVertex = j;
                        idAction = 1;
                        showToast("Торкніться вершини до якої проводити зв'язок");
                        break;
                    case 1:
                        firstChangedVertex = j;
                        idAction = 2;
                        showToast("Торкніться вершини де потрібно видалити зв'язок");
                        break;
                    case 2:
                        FloatingActionButton f = (FloatingActionButton) vertexFabList.get(j).findViewById(R.id.fab_vertex);
                        fabContainer.removeView(f);
                        vertexFabList.remove(j);
                        graph.removeVertex(j);
                        returnToStart();
                        imgView.setDrawCustomCanvas(true);
                        imgView.invalidate();
                        break;

                    default:
                        return;
                }
            }
        });
        builder.setCancelable(true);
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                showToast("Виберіть стартову вершину");
                returnToStart();
                idAction = 3;
                break;
            case R.id.button2:
                showToast("Виберіть стартову вершину");
                returnToStart();
                idAction = 4;
                break;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addVertex() {
        final FloatingActionButton fabv = new FloatingActionButton(this);
        fabv.setId(R.id.fab_vertex);
        vertexFabList.add(fabv);
        fabContainer.addView(fabv);
        fabv.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        graph.addVertex(fabv.getHeight()/2, fabv.getHeight()/2);
        fabv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        fabv.setX(fabv.getX() + (motionEvent.getX() - x));
                        fabv.setY(fabv.getY() + (motionEvent.getY() - y));
                        graph.setVertexKoord(vertexFabList.indexOf(fabv), fabv.getX() + fabv.getHeight()/2, fabv.getY() + fabv.getHeight()/2);
                        imgView.setDrawCustomCanvas(true);
                        imgView.invalidate();
                        return true;
                    case MotionEvent.ACTION_DOWN:
                        x = motionEvent.getX();
                        y = motionEvent.getY();
                        fX = fabv.getX();
                        fY = fabv.getY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if (!isAction(fabv)) {
                            switch (idAction) {
                                case 0:
                                    createDialogInVertex(vertexFabList.indexOf(fabv));
                                    break;
                                case 1:
                                    secondChangedVertex = vertexFabList.indexOf(fabv);
                                    graph.addEdge(firstChangedVertex, secondChangedVertex);
                                    returnToStart();
                                    idAction = 0;
                                    break;
                                case 2:
                                    secondChangedVertex = vertexFabList.indexOf(fabv);
                                    graph.removeEdge(firstChangedVertex, secondChangedVertex);
                                    returnToStart();
                                    idAction = 0;
                                    break;
                                case 3:
                                    AsyncAnimation asyncAnimation = new AsyncAnimation();
                                    asyncAnimation.execute(vertexFabList.indexOf(fabv));
                                    idAction = 0;
                                    break;
                                case 4:
                                    showToast("Виберіть кінцеву вершину");
                                    firstChangedVertex = vertexFabList.indexOf(fabv);
                                    fabv.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                    idAction = 5;
                                    break;
                                case 5:
                                    secondChangedVertex = vertexFabList.indexOf(fabv);
                                    fabv.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                    createDistDialog(graph.D(firstChangedVertex, secondChangedVertex));
                                    idAction = 0;
                                    break;
                                default:
                                    imgView.setDrawCustomCanvas(true);
                                    imgView.invalidate();
                                    return true;
                            }
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void createDistDialog(int d){
        AlertDialog.Builder builder = new AlertDialog.Builder(PracticeActivity.this);
        builder.setTitle("Дистанція між вершинами")
                .setMessage(String.valueOf(d))
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void showToast(String s){
        Toast.makeText(PracticeActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    private void returnToStart(){
        imgView.removeVertexForAddText();
        imgView.setDrawCustomCanvas(true);
        imgView.invalidate();
        for (int i = 0; i < vertexFabList.size(); i++) {
            FloatingActionButton fab =(FloatingActionButton) vertexFabList.get(i).findViewById(R.id.fab_vertex);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }
    }

    private boolean isAction(FloatingActionButton fab) {
        if ((Math.abs(fX - fab.getX()) <= 10) && (Math.abs(fY - fab.getY()) <= 10))
            return false;
        return true;
    }

    class AsyncAnimation extends AsyncTask<Integer, Integer, Void>{

        FloatingActionButton fab;

        @Override
        protected Void doInBackground(Integer... integers) {

            for (int i = 0; i < graph.getBfs(integers[0]).length; i++) {
                publishProgress(graph.getBfs(integers[0])[i]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled())
                    break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            fab = (FloatingActionButton) vertexFabList.get(values[0]).findViewById(R.id.fab_vertex);
            fab.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            imgView.addVertexToVertexForAddText(values[0]);
            imgView.setDrawCustomCanvas(true);
            imgView.invalidate();
        }
    }
}

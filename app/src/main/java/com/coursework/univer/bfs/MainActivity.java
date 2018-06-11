package com.coursework.univer.bfs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button buttonTheory;
    Button buttonPractice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonTheory = (Button) findViewById(R.id.button_theory);
        buttonPractice = (Button) findViewById(R.id.button_practice);
        buttonTheory.setOnClickListener(this);
        buttonPractice.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_theory:
                changeActivity(TheoryActivity.class);
                break;
            case R.id.button_practice:
                changeActivity(PracticeActivity.class);
                break;

        }
    }

    private void changeActivity(Class mClass){
        Intent intent = new Intent(MainActivity.this, mClass);
        startActivity(intent);
    }
}

package com.example.sylviameow.matquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button start = (Button) findViewById(R.id.btn_start);
        start.setOnClickListener(new Button.OnClickListener() {
            // Jump to the 2nd page when the start button is pressed
            @Override
            public void onClick(View v) {
                // intent.putExtra("name", name.getText().toString()); //添加一些内容，传给Display
                Intent intent=new Intent(MainActivity.this,QuizGenerator.class);
                startActivity(intent);
            }
        });
    }
}

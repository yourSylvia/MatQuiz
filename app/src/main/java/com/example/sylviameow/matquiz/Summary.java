package com.example.sylviameow.matquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Summary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        TextView textView1=(TextView) this.findViewById(R.id.Correct_ans);
        Intent intent=getIntent();
        int correct_ans=intent.getIntExtra("Correct_ans", 0);
        textView1.setText("Number of correct answer\n" + correct_ans);

        TextView textView2=(TextView) this.findViewById(R.id.Wrong_ans);
        int wrong_ans=intent.getIntExtra("Wrong_ans", 0);
        textView2.setText("Number of wrong answer\n" + wrong_ans);

        TextView textView3=(TextView) this.findViewById(R.id.Give_up);
        int give_up=intent.getIntExtra("Give_up_ans", 0);
        textView3.setText("Number of gave up answer\n" + give_up);

        TextView textView4=(TextView) this.findViewById(R.id.Accuracy);
        String accuracy=intent.getStringExtra("Accuracy");
        textView4.setText("Accuracy\n" + accuracy);

        TextView textView5=(TextView) this.findViewById(R.id.Average_li);
        String average_li=intent.getStringExtra("LinearAverage");
        textView5.setText( "Average time spent on linear quiz\n" + average_li + " seconds");

        TextView textView6=(TextView) this.findViewById(R.id.Average_qu);
        String average_qu=intent.getStringExtra("QuadraticAverage");
        textView6.setText( "Average time spent on quadratic quiz\n" + average_qu + " seconds");

        final Button start = (Button) findViewById(R.id.btn_restart);
        start.setOnClickListener(new Button.OnClickListener() {
            // Jump to the 2nd page when the start button is pressed
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Summary.this,QuizGenerator.class);
                startActivity(intent);
            }
        });
    }
}

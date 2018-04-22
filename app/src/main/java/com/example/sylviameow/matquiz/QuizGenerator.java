package com.example.sylviameow.matquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuizGenerator extends AppCompatActivity {
    double ans;
    double ans1, ans2;
    int quizNum = 1;
    double duration_li = 0;
    double duration_qu = 0;
    int correct_ans = 0, give_up = 0;
    long start = 0, end;
    boolean submit_flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_generatoz);

        generate_quiz();
        quizNum++;
        ScrollView layout =(ScrollView) findViewById(R.id.changing_background);
        layout.setBackgroundResource(R.drawable.pic2);

        /* --------------------------- Submit the answer ---------------------------- */
        final Button submit = (Button) findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            // Correct the answer
            @Override
            public void onClick(View v) {
                submitQuiz();
                submit.setEnabled(false);
            }
        });

        /* ---------------------------- next quiz ------------------------------------ */
        final Button next = (Button) findViewById(R.id.btn_proceed);
        next.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setEnabled(true);
                // clear the input
                EditText editText1 = (EditText) findViewById(R.id.inputAnswer1);
                editText1.setText("");
                EditText editText2 = (EditText) findViewById(R.id.inputAnswer2);
                editText2.setText("");

                nextQuiz();

                // change the background
                ScrollView layout =(ScrollView) findViewById(R.id.changing_background);
                switch (quizNum){
                    case 3: layout.setBackgroundResource(R.drawable.pic3); break;
                    case 4: layout.setBackgroundResource(R.drawable.pic4); break;
                    case 5: layout.setBackgroundResource(R.drawable.pic5); break;
                    case 6: layout.setBackgroundResource(R.drawable.pic6); break;
                    case 7: layout.setBackgroundResource(R.drawable.pic7); break;
                    case 8: layout.setBackgroundResource(R.drawable.pic8); break;
                    case 9: layout.setBackgroundResource(R.drawable.pic9); break;
                    case 10: layout.setBackgroundResource(R.drawable.pic10); break;
                    case 11: layout.setBackgroundResource(R.drawable.pic11); break;
                }
            }
        });
    }

    public void submitQuiz(){
        String input_ans1;
        String input_ans2;
        String result;

        end = System.currentTimeMillis();

        if(quizNum < 6){
            duration_li += (double) ((end - start)/1000);
        }
        else{
            duration_qu += (double) ((end - start)/1000);
        }

        // Get the input answer
        EditText editText1 = (EditText) findViewById(R.id.inputAnswer1);
        input_ans1 = editText1.getText().toString();
        EditText editText2 = (EditText) findViewById(R.id.inputAnswer2);
        input_ans2 = editText2.getText().toString();

        if(submit_flag == false){
            result = correctAnswer(quizNum, input_ans1, input_ans2);
            final TextView resultView = (TextView) findViewById(R.id.resultDisplay);
            resultView.setText(result);
        }

        submit_flag = true;
    }

    public void nextQuiz(){
        if(submit_flag == false){
            give_up++;
        }

        submitQuiz();

        if(quizNum >= 2 && quizNum < 6){
            generate_quiz();
        }
        else if(quizNum >=6 && quizNum <11){
            generate_quiz();
        }
        else{
            // jump to the last page
            DecimalFormat df = new DecimalFormat("##.##");

            Intent intent=new Intent(QuizGenerator.this,Summary.class);
            intent.putExtra("Correct_ans", correct_ans);
            intent.putExtra("Wrong_ans", 10-correct_ans-give_up);
            intent.putExtra("Give_up_ans", give_up);
            intent.putExtra("Accuracy", (correct_ans/10d)*100 + "%");
            intent.putExtra("LinearAverage", df.format(duration_li/5));
            intent.putExtra("QuadraticAverage", df.format(duration_qu/5));
            startActivity(intent);
        }

        submit_flag = false;
        quizNum++;
    }

    public void generate_quiz() {
        String random_quiz;
        String string_ans;

        // Display the quiz
        random_quiz = randomQuiz();
        final TextView quizTextView = (TextView) findViewById(R.id.quiz);
        quizTextView.setText(random_quiz);
        start = System.currentTimeMillis();

        // Check the format of the answer
        if(quizNum < 6){
            string_ans = Double.toString(ans);
            checkFormat(string_ans);
        }else{
            string_ans = Double.toString(ans1);
            checkFormat(string_ans);
            string_ans = Double.toString(ans2);
            checkFormat(string_ans);
        }
    }

    /*---------------------------  generate a random quiz  -----------------------*/
    String randomQuiz() {
        int a, b, c;
        int min = -99;
        int max = 99;
        double temp;
        String quiz;

        // Guarantee that it is always satisfy b^2-4ac <=0
        do {
            Random r1 = new Random();
            a = r1.nextInt(max - min + 1) + min;
            Random r2 = new Random();
            b = r2.nextInt(max - min + 1) + min;
            Random r3 = new Random();
            c = r3.nextInt(max - min + 1) + min;
            temp = Math.pow(b, 2) - 4 * a * c;
        } while (temp < 0);

        if (quizNum < 6) {
            if (b < 0) {
                quiz = "Question" + quizNum + "\n" + a + "x" + " - " + Math.abs(b) + " = " + "0" + "\nWhat is x?";
            } else {
                quiz = "Question" + quizNum + "\n" + a + "x" + " + " + b + " = " + "0" + "，" + "\nWhat is x?";
            }
            ans = - (double)b / (double)a;
        } else {
            if (b < 0 && c > 0) {
                quiz = "Question" + quizNum + "\n" + a + "x^2" + " - " + Math.abs(b) + "x" + c + " = " + "0" + "\nWhat is x?";
            } else if (c < 0 && b > 0) {
                quiz = "Question" + quizNum + "\n" + a + "x^2" + "+" + b + "x" + "-" + Math.abs(c) + " = " + "0" + "\nWhat is x?";
            } else if (b < 0 && c < 0) {
                quiz = "Question" + quizNum + "\n" + a + "x^2" + " - " + Math.abs(b) + "x" + " - " + Math.abs(c) +
                        " = " + "0" + "\nWhat is x?";
            } else {
                quiz = "Question" + quizNum + "\n" + a + "x^2" + " + " + b + "x" + c + " = " + "0" + "\nWhat is x?";
            }
            ans1 = (-(double)b + Math.sqrt(Math.pow((double) b, 2) - 4 * (double)a * (double)c)) / 2 * (double) a;
            ans2 = (-(double)b - Math.sqrt(Math.pow((double) b, 2) - 4 * (double)a * (double)c)) / 2 * (double) a;
        }

        return quiz;
    }

    /*------------------------  check the format of the input answer  -------------------*/
    void checkFormat(String input_ans) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input_ans);
        if(mer.find() == false){
            final TextView resultView = (TextView) findViewById(R.id.resultDisplay);
            resultView.setText("Please round the answer to 2 decimal");
        }
        else{
            final TextView resultView = (TextView) findViewById(R.id.resultDisplay);
            resultView.setText("");
        }
    }

    /*-------------------------  correct the input answer  -----------------------*/
    String correctAnswer(int quizNum, String input_ans1, String input_ans2) {
        String result;
        DecimalFormat df = new DecimalFormat("##.##");

        if (quizNum < 7) {
            if (input_ans1.equals(df.format(ans))) {
                result = "Congratulations, Answer is correct.";
                correct_ans++;
            } else {
                result = "The correct answer is " + df.format(ans);
            }
        } else {
            if (input_ans1.equals(df.format(ans1)) && input_ans2.equals(df.format(ans2))) {
                result = "Congratulations, Answer is correct.";
                correct_ans++;
            }
            else {
                result = "The correct answer is " + df.format(ans1) + " and " + df.format(ans2);
            }
        }

        return result;
    }
}


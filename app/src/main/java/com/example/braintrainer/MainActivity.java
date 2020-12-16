package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity
{
    TextView introTextView,questionTextView,progressTextView,timerTextView,resultTextView;
    Button goButton,button0,button1,button2,button3,playagainButton;
    int locationOfCorrectAnswer;
    ArrayList<Integer> answers= new ArrayList<>(); //List of the answers
    int score=0, wrong=0,attempts=0,s;
    long time=30000;
    long updatedTime;
    CountDownTimer countDownTimer;
    boolean counterActive= true;


    public void start(View view)
    {
        introTextView=findViewById(R.id.introTextView);
        introTextView.setVisibility(View.INVISIBLE);
        goButton.setVisibility(View.INVISIBLE);
        timer();
        questions(); //Generating questions
        playagain(view);
    }


    /*Validating and displaying the correct answer*/
    public void chooseAnswer(View view)
    {
            if(Integer.toString(locationOfCorrectAnswer).equals(view.getTag())) //Checking if the tag number stored in locationOfCorrectAnswer is same as view.getTag()
            {
                resultTextView.setText("Correct!");
                resultTextView.setTextColor(Color.parseColor("#8BC34A"));
                score++;
                attempts++;

                /*Attempt of stopping timer but not working*/
//                countDownTimer.cancel();
//                updatedTime=time+2000;
//                countDownTimer= new CountDownTimer(updatedTime, 1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        timerfunction(millisUntilFinished);
//
//                    }
//                    @Override
//                    public void onFinish() {
//
//                    }
//                };
//                countDownTimer.start();
            }
            else
            {
                resultTextView.setText("Wrong!");
                resultTextView.setTextColor(Color.parseColor("#EF0B0B"));
                wrong++;
                attempts++;
            }
            progressTextView.setText(score+"/"+attempts);
            questions();
    }

    /*Generating questions & answers*/
    public void questions()
    {
        /*Generating Random Number for Questions*/
        Random random=new Random();
        int a=random.nextInt(101); //Generating random numbers between 0 & 100
        int b=random.nextInt(101); //Generating random numbers between 0 & 100

        questionTextView.setText(a +"+"+ b);

        /*Generating answers*/
        locationOfCorrectAnswer=random.nextInt(4); //Choosing location of correct answer randomly between button tag 0 & 3.

        answers.clear(); //Clearing everything inside the list

        for (int i=0;i<4;i++)
        {
            if (i==locationOfCorrectAnswer) //giving a random location for correct answer. That is the tag of the button
            {
                answers.add(a+b);
            }
            else
            {
                /*Logic: Answers must be 2x of the selected range of questions. That is if numbers for question is limited from 0 to 100 (i.e. 101)
                then answers limit will be from 0 to 200 since results of adding two numbers between 0 & 100 is always limited between 0 & 200*/
                int wrongAnswer=random.nextInt(201);

                while (wrongAnswer==a+b) //To prevent from generating correct answers more than once
                {
                    wrongAnswer=random.nextInt(201);
                }
                answers.add(wrongAnswer);
            }
        }

        /*Printing the answers by using the list & the value of 'i' (48th line)*/
        button0.setText(Integer.toString(answers.get(0)));
        button1.setText(Integer.toString(answers.get(1)));
        button2.setText(Integer.toString(answers.get(2)));
        button3.setText(Integer.toString(answers.get(3)));
    }

    /*In game Timer*/
    public void timer()
    {
        counterActive=false;
        countDownTimer = new CountDownTimer(time,1000) //Timer for 30s
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                s=(int)millisUntilFinished/1000;
                String seconds=Integer.toString(s);
                if (s<=9)
                {
                    seconds="0"+seconds;
                }
                timerTextView.setText("00:"+seconds);
            }
            @Override
            public void onFinish()
            {
                timerTextView.setVisibility(View.INVISIBLE);
                progressTextView.setVisibility(View.INVISIBLE);
                questionTextView.setVisibility(View.INVISIBLE);
                button0.setVisibility(View.INVISIBLE);
                button1.setVisibility(View.INVISIBLE);
                button2.setVisibility(View.INVISIBLE);
                button3.setVisibility(View.INVISIBLE);
                playagainButton.setVisibility(View.VISIBLE);
                resultTextView.setVisibility(View.INVISIBLE);
                introTextView.setVisibility(View.VISIBLE);
                introTextView.setText("Results\nAnswered:"+(attempts)+"\nCorrect:"+score+"\nWrong:"+wrong);
                introTextView.setTextSize(30);
            }
        }.start();
    }

    /*To start the game again*/
    public void playagain(View view)
    {
        score=0;
        wrong=0;
        attempts=0;
        timerTextView.setVisibility(View.VISIBLE);
        progressTextView.setVisibility(View.VISIBLE);
        progressTextView.setText("0/0");
        questionTextView.setVisibility(View.VISIBLE);
        button0.setVisibility(View.VISIBLE);
        button1.setVisibility(View.VISIBLE);
        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
        playagainButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.VISIBLE);
        introTextView.setVisibility(View.INVISIBLE);
        introTextView.setVisibility(View.INVISIBLE);
        resultTextView.setText("");
        questions();
        timer();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goButton=findViewById(R.id.goButton);
        playagainButton=findViewById(R.id.playagainButton);
        button0=findViewById(R.id.button0);
        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);

        resultTextView= findViewById(R.id.resultTextView);
        questionTextView=findViewById(R.id.questionTextView);
        progressTextView=findViewById(R.id.progressTextView);
        timerTextView=findViewById(R.id.timerTextView);
        introTextView=findViewById(R.id.introTextView);
    }
}

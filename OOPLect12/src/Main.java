package com.paradeeez.truequiz;

import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.AppCompatButton;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.os.TestLooperManager;
        import android.view.View;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Timer;
        import java.util.TimerTask;

//The class extends the AppCompatActivity class, which is a base class for activities that use the
// support library action bar features.
public class QuizActivity extends AppCompatActivity {

    //The class declares several private instance variables, including TextViews for displaying
    // questions and answers, AppCompatButtons for displaying answer options and a next button for
    // moving to the next question, and a Timer for tracking the quiz time.
    private TextView questions;
    private TextView question;
    private AppCompatButton option1, option2, option3, option4;
    private AppCompatButton nextBtn;
    private Timer quizTimer;

    private int totalTimeInMins = 1;
    private int seconds = 0;
    private List<QuestionsList> questionsLists;//The questionsList variable is a list of
    // QuestionsList objects, which likely contain the quiz questions and their answer options.
    private int currentQuestionPosition = 0;
    private String selectedOptionByUser = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //retrieve several UI elements from the layout file, including an ImageView for the back
        // button, TextViews for the timer and selected topic name, and TextViews and
        // AppCompatButton for displaying questions and answer options.

        final ImageView backBtn = findViewById(R.id.backBtn);
        final TextView timer = findViewById(R.id.timer);
        final TextView selectedTopicName = findViewById(R.id.topicName);

        questions = findViewById(R.id.questions);
        question = findViewById(R.id.question);

        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        nextBtn = findViewById(R.id.nextBtn);

        //It gets the selected topic name from the previous activity using an intent extra and sets
        // the selectedTopicName TextView.
        final String getSelectedTopicName = getIntent().getStringExtra("selectedTopic");

        selectedTopicName.setText(getSelectedTopicName);

        //It retrieves a list of questions for the selected topic using the QuestionBank.getQuestions
        // method.
        questionsLists = QuestionBank.getQuestions(getSelectedTopicName);
        startTimer(timer);

        questions.setText(currentQuestionPosition+1+"/"+questionsLists.size()); //display question number
        question.setText(questionsLists.get(0).getQuestion());                  //display question
        option1.setText(questionsLists.get(0).getOption1());
        option2.setText(questionsLists.get(0).getOption2());
        option3.setText(questionsLists.get(0).getOption3());
        option4.setText(questionsLists.get(0).getOption4());

        //It adds click listeners to each answer option button that sets the selectedOptionByUser
        // variable to the clicked option, changes the UI to indicate the selected option, and
        // updates the user's answer in the question list.
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option1.getText().toString();

                    option1.setBackgroundResource(R.drawable.round_back_red10);
                    option1.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option2.getText().toString();

                    option2.setBackgroundResource(R.drawable.round_back_red10);
                    option2.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }
            }
        });

        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option3.getText().toString();

                    option3.setBackgroundResource(R.drawable.round_back_red10);
                    option3.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }

            }
        });

        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedOptionByUser.isEmpty()) {

                    selectedOptionByUser = option4.getText().toString();

                    option4.setBackgroundResource(R.drawable.round_back_red10);
                    option4.setTextColor(Color.WHITE);

                    revealAnswer();

                    questionsLists.get(currentQuestionPosition).setUserSelectedAnswer(selectedOptionByUser);
                }

            }
        });

        //It adds a click listener to the next button that checks if an option has been selected,
        // displays an error if not, and changes the UI to show the next question if an option has been selected.
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(selectedOptionByUser.isEmpty()) {
                    Toast.makeText(QuizActivity.this, "Please select an option", Toast.LENGTH_SHORT).show();
                }
                else {
                    changeNextQuestion();
                }
            }
        });

        //It adds a click listener to the back button that stops the quiz timer, cancels the quiz timer,
        // and starts the MainActivity.
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                quizTimer.purge();
                quizTimer.cancel();

                startActivity((new Intent(QuizActivity.this, MainActivity.class)));
                finish();
            }
        });
    }

    private void changeNextQuestion() {

        currentQuestionPosition++;

        //It also checks whether the current question is the last question in the quiz. If it is,
        // it changes the text of the "Next" button to "Submit Quiz"
        if((currentQuestionPosition+1) == questionsLists.size()) {
            nextBtn.setText("Submit Quiz");
        }

        if(currentQuestionPosition < questionsLists.size()) {

            //t updates the UI with the new question, resets the selectedOptionByUser variable to
            // an empty string, and resets the background color and text color of all the option
            // buttons to their original state.
            selectedOptionByUser = "";

            option1.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option1.setTextColor(Color.parseColor("#1F6BB8"));

            option2.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option2.setTextColor(Color.parseColor("#1F6BB8"));

            option3.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option3.setTextColor(Color.parseColor("#1F6BB8"));

            option4.setBackgroundResource(R.drawable.round_back_white_stroke2_10);
            option4.setTextColor(Color.parseColor("#1F6BB8"));

            //Display the next question in the array
            questions.setText((currentQuestionPosition+1+"/"+questionsLists.size()));
            question.setText(questionsLists.get(currentQuestionPosition).getQuestion());
            option1.setText(questionsLists.get(currentQuestionPosition).getOption1());
            option2.setText(questionsLists.get(currentQuestionPosition).getOption2());
            option3.setText(questionsLists.get(currentQuestionPosition).getOption3());
            option4.setText(questionsLists.get(currentQuestionPosition).getOption4());

        }
        else {

            //if it is the last question, when the submit button is pressed, it starts the
            // QuizResults activity by passing the number of correct and incorrect answers as extras
            // in the intent. Finally, it finishes the QuizActivity.
            Intent intent = new Intent(QuizActivity.this, QuizResults.class);
            intent.putExtra("correct", getCorrectAnswers());
            intent.putExtra("incorrect", getInCorrectAnswers());
            startActivity(intent);

            finish();
        }
    }

    //This is a method that starts a timer for the quiz. It takes in a TextView as a parameter,
    // which is used to display the time remaining for the quiz.
    private void startTimer(TextView timerTextView) {

        quizTimer = new Timer();

        quizTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if(seconds == 0) {
                    totalTimeInMins--;
                    seconds = 59;
                }
                else if (seconds == 0 && totalTimeInMins == 0) {

                    quizTimer.purge();
                    quizTimer.cancel();

                    Toast.makeText(QuizActivity.this, "Time Over", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(QuizActivity.this, QuizResults.class);
                    intent.putExtra("correct", getCorrectAnswers());
                    intent.putExtra("incorrect", getCorrectAnswers());
                    startActivity(intent);

                    finish();

                }
                else {
                    seconds--;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        String finalMinutes = String.valueOf(totalTimeInMins);
                        String finalSeconds = String.valueOf(seconds);

                        if(finalMinutes.length() == 1) {
                            finalMinutes = "0"+finalMinutes;
                        }

                        if(finalSeconds.length() == 1) {
                            finalSeconds = "0" +finalSeconds;
                        }

                        timerTextView.setText(finalMinutes + ":" + finalSeconds);
                    }


                });
            }
        }, 1000, 1000);
    }


    //This is a method that returns an integer value, which represents the number of questions that
    // the user answered correctly. It loops through the list of questions, and for each question,
    // it compares the answer selected by the user to the correct answer
    private int getCorrectAnswers() {

        int correctAnswers = 0;

        for(int i=0;i<questionsLists.size();i++) {

            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            //If the user selected the correct answer, it increments the counter for the number of correct answers.
            if(getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }

        return correctAnswers; //Finally, it returns the total count of correct answers.
    }

    private int getInCorrectAnswers() {

        int correctAnswers = 0;

        for(int i=0;i<questionsLists.size();i++) {

            final String getUserSelectedAnswer = questionsLists.get(i).getUserSelectedAnswer();
            final String getAnswer = questionsLists.get(i).getAnswer();

            if(!getUserSelectedAnswer.equals(getAnswer)) {
                correctAnswers++;
            }
        }

        return correctAnswers;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        quizTimer.purge(); //Next, it cancels the quizTimer using the purge() and cancel() methods
        quizTimer.cancel();//to prevent the timer from running in the background.


        //Then, it starts a new intent to launch the MainActivity using the startActivity() method.
        startActivity(new Intent(QuizActivity.this, MainActivity.class));
        finish(); //Finally, it calls the finish() method to close the current QuizActivity.
    }

    private void revealAnswer() {

        //The revealAnswer() method changes the background color and text color of the option that
        // contains the correct answer for the current question.
        final String getAnswer = questionsLists.get(currentQuestionPosition).getAnswer();

        if(option1.getText().toString().equals(getAnswer)) {
            option1.setBackgroundResource(R.drawable.round_back_green10);
            option1.setTextColor(Color.WHITE);
        }
        else if(option2.getText().toString().equals(getAnswer)) {
            option2.setBackgroundResource(R.drawable.round_back_green10);
            option2.setTextColor(Color.WHITE);
        }
        else if(option3.getText().toString().equals(getAnswer)) {
            option3.setBackgroundResource(R.drawable.round_back_green10);
            option3.setTextColor(Color.WHITE);
        }
        else if(option4.getText().toString().equals(getAnswer)) {
            option4.setBackgroundResource(R.drawable.round_back_green10);
            option4.setTextColor(Color.WHITE);
        }
    }
}
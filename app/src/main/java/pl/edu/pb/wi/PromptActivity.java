package pl.edu.pb.wi;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import android.view.View;

public class PromptActivity extends AppCompatActivity {

    private boolean correctAnswer;
    private Button showAnswerButton; // Deklaracja przycisku "Pokaż odpowiedź"
    private TextView answerTextView;
public static final String KEY_EXTRA_ANSWER_SHOWN="pb.edu.pl.wi.quiz.answerShown";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prompt);
        correctAnswer=getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);
        showAnswerButton = findViewById(R.id.button); // Zakładać, że używasz przycisku o identyfikatorze "button"
        answerTextView = findViewById(R.id.answer_text_view);
        showAnswerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v){
                int answer=correctAnswer?R.string.button_true:R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });}
        private void setAnswerShownResult(boolean answerWasShown) {
            Intent resultIntent=new Intent();
            resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
            setResult(RESULT_OK,resultIntent);
        }





    }






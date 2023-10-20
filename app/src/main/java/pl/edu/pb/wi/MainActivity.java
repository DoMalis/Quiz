package pl.edu.pb.wi;

import static android.icu.lang.UCharacter.DecompositionType.FINAL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_CURRENT_INDEX="currentIndex";
    public static final String KEY_EXTRA_ANSWER="pl.edu.pb.wi.quiz.correctAnswer";
    private static final int REQUEST_CODE_PROMPT=0;
    private boolean answerWasShown;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("MojaActivity", "Wywolana została metoda: onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private TextView questiontextView;

    private Question[] questions=new Question[]{
            new Question(R.string.q_activity, true),
            new Question(R.string.q_find_resources, false),
            new Question(R.string.q_listner, true),
            new Question(R.string.q_resources, true),
            new Question(R.string.q_version, false)
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("MojaActivity", "Metoda onCreate() została wywołana");
        if(savedInstanceState!=null){
            currentIndex=savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }


        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        questiontextView=findViewById(R.id.question_text_view);
        Button button2 = findViewById(R.id.button2);


        button2.setOnClickListener((v) -> {
            Intent intent=new Intent(MainActivity.this, PromptActivity.class);
            boolean correctAnswer=questions[currentIndex].isTrueAnswer();
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivityForResult(intent,REQUEST_CODE_PROMPT);
                }        );
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(true);
            }
        });

        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex=(currentIndex+1)%questions.length;
                answerWasShown=false;
                setNextQuestion();
                if(currentIndex==questions.length-1)
                {
                    Toast.makeText(view.getContext(), "Liczba poprawnych odpowiedzi: " + howmany, Toast.LENGTH_SHORT).show();
                howmany=0;
                }
            }
        });
        setNextQuestion();

    }
    private int howmany=0;
    private int currentIndex=0;

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MojaActivity", "Metoda onPause() została wywołana");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){return;}
        if(requestCode==REQUEST_CODE_PROMPT){
            if(data==null){return;}
            answerWasShown=data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MojaActivity", "Metoda onDestroy() została wywołana");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MojaActivity", "Metoda onResume() została wywołana");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MojaActivity", "Metoda onStart() została wywołana");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MojaActivity", "Metoda onStop() została wywołana");

    }


    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer=questions[currentIndex].isTrueAnswer();
        int resultMessageId=0;
        if(answerWasShown){
            resultMessageId=R.string.answer_was_shown;
        }else{
        if(userAnswer==correctAnswer){
            {resultMessageId=R.string.correct_answer;
                howmany++;
            }
        }else{
            resultMessageId=R.string.incorrect_answer;
        }}
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();


    }
    private void setNextQuestion()
    {
        questiontextView.setText(questions[currentIndex].getQuestionId());
    }


}

package android.bignerdranch.com;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWER = "KEY_ANSWER";
    private Button mTrueButton;
    private Button mFlaseButton;
    private Button mCheatButton;
    private Button mNextButton;
    private Button mPrevButton;
    private int trueCount = 0;
    private int falseCount = 0;
    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_africa, false, 0),
            new Question(R.string.question_americas, true, 0),
            new Question(R.string.question_asia, true, 0),
            new Question(R.string.question_australia, true, 0),
            new Question(R.string.question_mideast, false, 0),
            new Question(R.string.question_oceans, true, 0)
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            int[] questionStatus = savedInstanceState.getIntArray(KEY_ANSWER);
            for(int i=0; i<mQuestionBank.length; i++){
                mQuestionBank[i].setMisAnswered(questionStatus[i]);
                if(questionStatus[i] == 1) {
                    trueCount += 1;
                }else if(questionStatus[i] == -1){
                    falseCount += 1;
                }
            }
        }


        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                updateQuestion();
            }
        });

        mFlaseButton = (Button) findViewById(R.id.false_button);
        mFlaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // challenge,set toast location
                checkAnswer(false);
                updateQuestion();
            }
        });

        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivity(intent);
            }
        });
        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton = (Button)findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex == 0){
                    mCurrentIndex = mQuestionBank.length;
                }
                mCurrentIndex = mCurrentIndex - 1;
                updateQuestion();
            }
        });
        updateQuestion();   //初始化
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        int[] answerList = new int[mQuestionBank.length];
        for(int i=0; i<mQuestionBank.length; i++){
            answerList[i] = mQuestionBank[i].getMisAnswered();
        }
        savedInstanceState.putIntArray(KEY_ANSWER, answerList);
    }
    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestory() called");
    }

    private void updateQuestion(){
        // Log.d(TAG, "Updating question text ", new Exception());
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        ButtonEnabled();

    }

    private void checkAnswer(boolean userPressedTrue){
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue){
            messageResId = R.string.correct_toast;
            mQuestionBank[mCurrentIndex].setMisAnswered(1);
            trueCount += 1;
        }else{
            mQuestionBank[mCurrentIndex].setMisAnswered(-1);
            messageResId = R.string.incorrect_taost;
            falseCount += 1;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        if(trueCount + falseCount == mQuestionBank.length) {
            int result = (int)((float)trueCount / mQuestionBank.length * 100.0);
            Toast.makeText(this, String.valueOf(result), Toast.LENGTH_SHORT).show();
        }

    }

    private void ButtonEnabled(){
        if(mQuestionBank[mCurrentIndex].getMisAnswered() != 0){
            mTrueButton.setEnabled(false);
            mFlaseButton.setEnabled(false);
        }else{
            mTrueButton.setEnabled(true);
            mFlaseButton.setEnabled(true);
        }
    }
}

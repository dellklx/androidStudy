package android.bignerdranch.com;

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;
    private int misAnswered;

    public int getMisAnswered() {
        return misAnswered;
    }

    public void setMisAnswered(int misAnswered) {
        this.misAnswered = misAnswered;
    }

    public int getTextResId() {
        return mTextResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public Question(int textResID, boolean answerTrue, int isAnswered){
        mTextResId = textResID;
        mAnswerTrue = answerTrue;
        misAnswered = isAnswered;
    }
}

package devseth.questionnaire;

/**
 * Created by Dev Seth on 6/28/2017.
 */

public class QuestionFeeder {

    private String q1, q2, q3, q4, q5, q6, q7, q8, q9, q10;     //the questions
    private String[] questions;                                 //array storing questions
    private int questionIndex;                                  //current position in array

    public QuestionFeeder() {

        q1 = "Question 1";
        q2 = "Question 2";
        q3 = "Question 3";
        q4 = "Question 4";
        q5 = "Question 5";
        q6 = "Question 6";
        q7 = "Question 7";
        q8 = "Question 8";
        q9 = "Question 9";
        q10 = "Question 10";

        questions = new String[]{q1, q2, q3, q4, q5, q6, q7, q8, q9, q10};

        questionIndex = 0;
    }

    public String nextQuestion() {

        questionIndex++;                                        //goto next question

        if (questionIndex==questions.length)                    //have we reached the last question?
            return "end";                                       //if yes, return end

        return questions[questionIndex];                        //or else return the next question
    }

}

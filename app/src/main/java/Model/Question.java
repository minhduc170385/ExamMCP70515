package Model;



/**
 * Created by hermes on 2/16/15.
 */
public class Question {

    private String questionNumber= "questionNumber";
    private String contentQuestion ="contentQuestion";
    private String explainQuestion="explainQuestion";
    private String answer1 = "answer1";
    private String answer2 ="answer2";
    private String answer3 = "answer3";
    private String answer4 ="answer4";
    private String answer5 ="answer5";
    private String answer6 ="answer6";
    private String languageQuestion ="languageQuestion";
    private String correctAnswer = "correctAnswer";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id=1;


    public String getQuestionNumber() {
        return questionNumber;
    }
    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }
    public String getContentQuestion() {
        return contentQuestion;
    }
    public void setContentQuestion(String contentQuestion) {
        this.contentQuestion = contentQuestion;
    }
    public String getExplainQuestion() {
        return explainQuestion;
    }
    public void setExplainQuestion(String explainQuestion) {
        this.explainQuestion = explainQuestion;
    }
    public String getAnswer1() {
        return answer1;
    }
    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }
    public String getAnswer3() {
        return answer3;
    }
    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }
    public String getAnswer4() {
        return answer4;
    }
    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }
    public String getAnswer5() {
        return answer5;
    }
    public void setAnswer5(String answer5) {
        this.answer5 = answer5;
    }
    public String getAnswer6() {
        return answer6;
    }
    public void setAnswer6(String answer6) {
        this.answer6 = answer6;
    }
    public String getLanguageQuestion() {
        return languageQuestion;
    }
    public void setLanguageQuestion(String languageQuestion) {
        this.languageQuestion = languageQuestion;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}

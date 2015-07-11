package adapter;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleJSON {



    private String countQuestion="4";

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

    private String urlString = null;


    private int id = 1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCountQuestion() {
        return countQuestion;
    }

    public void setCountQuestion(String countQuestion) {
        this.countQuestion = countQuestion;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }


    public volatile boolean parsingComplete = true;
    public HandleJSON(String url){
        this.urlString = url;
    }




    @SuppressLint("NewApi")
    public void readAndParseJSON(String in,String type) {
        try {
            if(type=="Question")
            {
                Log.v("data1 in ", in);
                JSONObject reader = new JSONObject(in);
                questionNumber=reader.optString("questionNumber");
                contentQuestion=reader.optString("contentQuestion");
                explainQuestion=reader.optString("explainQuestion");
                answer1=reader.optString("answer1");
                answer2=reader.optString("answer2");
                answer3=reader.optString("answer3");
                answer4=reader.optString("answer4");
                answer5=reader.optString("answer5");
                answer6=reader.optString("answer6");
                languageQuestion=reader.optString("languageQuestion");
                correctAnswer=reader.optString("correctAnswer");
                id=reader.optInt("id");
                parsingComplete = false;
            }
            else if(type=="Count")
            {
                Log.v("data1 in ", in);
                JSONObject reader = new JSONObject(in);
                countQuestion=reader.optString("count");
                parsingComplete = false;
            }




        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void fetchJSON(final String typeUri){
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    // Starts the query
                    conn.connect();
                    InputStream stream = conn.getInputStream();

                    String data = convertStreamToString(stream);

                    readAndParseJSON(data,typeUri);
                    stream.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
    static String convertStreamToString(InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
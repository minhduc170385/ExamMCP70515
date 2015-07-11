package com.ducbm.exammcp70515.free;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ducbm.exammcp70515.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import Model.Question;
import adapter.ExamAdapter;
import adapter.HandleJSON;

/**
 * Created by hermes on 2/13/15.
 */
public class ExamActivity  extends ActionBarActivity {

    private String url1 = "http://default-environment-vephmhstfc.elasticbeanstalk.com/question/questionByQuestionNumberAndLanguage/%s/%s";
    private String urlCount = "http://default-environment-vephmhstfc.elasticbeanstalk.com/question/countByLanguage/%s";
    //http://api.openweathermap.org/data/2.5/weather?q=";
    Question q = new Question();
    private HandleJSON obj;
    private String questionNumber,contentQuestion,explainQuestion,answer1,answer2,answer3,answer4,answer5,answer6,languageQuestion,correctanswer;
    ListView examListView;
    private String subjectlanguage;
    int number=1;
    int totalQuestion=0;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        //Get Parameter
        Intent intent=getIntent();
        subjectlanguage=intent.getStringExtra("Subject Language");
        totalQuestion = Integer.parseInt(intent.getStringExtra("Total Question"));

        if(subjectlanguage.indexOf("#")>0)
        {
            subjectlanguage=subjectlanguage.replace("#","");
        }

        //Begin Action
        SetupQuestionNumber();

        examListView=(ListView) findViewById(R.id.lvExam);
        SetupContentExam();
        //Setup View
        SetupAdView();
        SetupViewExam();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        SetupButton();
        initAd();
        displayAd();

    }
    //Banner
    private void SetupAdView()
    {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void displayAd() {
        // Show the ad if it's ready. Otherwise toast and restart the game.
        if (interstitial != null && interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    private void initAd() {
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId("ca-app-pub-8938377850078456/2209647727");

        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder().build();

        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);
    }
    private void SetupQuestionNumber()
    {
        //String finalUrl = String.format(urlCount, subjectlanguage);
        //country.setText(finalUrl);
       // obj = new HandleJSON(finalUrl);
       // obj.fetchJSON("Count");
       // while(obj.parsingComplete);
      //  String count=obj.getCountQuestion();
      //  totalNumber = Integer.parseInt(count);
    }
    private void SetupContentExam()
    {
        if(subjectlanguage.indexOf("#")>0)
        {
            subjectlanguage=subjectlanguage.replace("#","");
        }

        String finalUrl = String.format(url1, Integer.toString(number),subjectlanguage);
        //country.setText(finalUrl);
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON("Question");

        while(obj.parsingComplete);
        q = new Question();
        q.setQuestionNumber(obj.getQuestionNumber());
        q.setContentQuestion(obj.getContentQuestion());
        q.setExplainQuestion(obj.getExplainQuestion());
        q.setAnswer1(obj.getAnswer1());
        q.setAnswer2(obj.getAnswer2());
        q.setAnswer3(obj.getAnswer3());
        q.setAnswer4(obj.getAnswer4());
        q.setAnswer5(obj.getAnswer5());
        q.setAnswer6(obj.getAnswer6());
        q.setLanguageQuestion(obj.getLanguageQuestion());
        q.setCorrectAnswer(obj.getCorrectAnswer());
        q.setId(obj.getId());

    }

    private void SetupViewExam()
    {


        ExamAdapter examAdapter = new ExamAdapter(this,q,totalQuestion);
        examListView.setAdapter(examAdapter);
        examAdapter.notifyDataSetChanged();

    }


    private void SetupButton()
    {
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(onClickListener);

        Button btnNext = (Button) findViewById(R.id.btnNext);
        btnNext.setOnClickListener(onClickListener);

        Button btnExplain = (Button) findViewById(R.id.btnExplain);
        btnExplain.setOnClickListener(onClickListener);

        Button btnResult = (Button) findViewById(R.id.btnResult);
        btnResult.setOnClickListener(onClickListener);

    }
    private void ShowExplain()
    {
        View viewAction =(View) examListView.getChildAt(0);
        TextView lblExplain = (TextView) viewAction.findViewById(R.id.lblExplain);

        if(lblExplain.isShown()==true)
        {
            lblExplain.setVisibility(View.INVISIBLE);
        }
        else
        {
            lblExplain.setVisibility(View.VISIBLE);
        }

    }
    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }

    private void NextQuestion()
    {
        if(number%10==0)
        {
            displayAd();
        }



        if(number < totalQuestion)
        {
            number++;
            SetupContentExam();
            SetupViewExam();
        }
        else
        {
            //Display ToollTip
        }

    }
    private  void BackQuestion()
    {
        if(number > 1)
        {
            number--;
            SetupContentExam();
            SetupViewExam();
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.btnBack:
                    BackQuestion();
                    break;
                case R.id.btnNext:
                    NextQuestion();
                    break;
                case R.id.btnExplain:
                    //DO something
                    ShowExplain();
                    break;
                case R.id.btnResult:
                   // Intent resultView = new Intent("com.MCPExam.RESULT");
                   // Bundle extras = new Bundle();
                    //resultView.putExtra("Subject Language",subjectlanguage);
                    //resultView.putExtra("Total Question",Integer.toString(totalQuestion));
                    //startActivity(resultView);

                    Intent i = new Intent(ExamActivity.this, ResultActivity.class);
                    i.putExtra("Subject Language",subjectlanguage);
                    startActivityForResult(i, 0);

                    break;
            }
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_refresh) {
            Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }
        if(id== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data)
    {
        //ListView examListView;
        //subjectlanguage="70-516C";
        //number=2;
        if(resultCode == RESULT_OK) {
            number = Integer.parseInt(data.getStringExtra("Question Number"));
            SetupContentExam();
            SetupViewExam();
        }
        //edtText.setText(data.getStringExtra("NAMA_PERASAT"));
    }

}

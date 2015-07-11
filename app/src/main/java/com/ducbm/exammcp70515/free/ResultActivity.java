package com.ducbm.exammcp70515.free;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ducbm.exammcp70515.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import Model.AnswerDao;
import adapter.SubjectAdapter;


public class ResultActivity extends ActionBarActivity {

    private InterstitialAd interstitial;
    private String subjectlanguage;

  //  int totalQuestion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //Get Parameter
        Intent intent=getIntent();
        subjectlanguage=intent.getStringExtra("Subject Language");
        //totalQuestion = Integer.parseInt(intent.getStringExtra("Total Question"));
        // Find the ListView resource.
        SetupListViewResult();
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        initAd();

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
    public String GetAnswer(Cursor c) {


       String temp= c.getString(1) + "-" + c.getString(2);
       return temp;

    }
    private void SetupListViewResult()
    {
        final ListView lvResult=(ListView)findViewById(R.id.lvResult);
        ArrayList<String> values = new ArrayList<String>();

        try
        {
            AnswerDao entry = new AnswerDao(ResultActivity.this);
            entry.open();
            Cursor c = entry.getAnswer(subjectlanguage);
            int i=0;
            if (c.moveToFirst()) {
                do {

                  values.add(GetAnswer(c));

                } while (c.moveToNext());
            }
            entry.close();
        }
        catch (Exception e)
        {
            //didItWork=false;
             Dialog d= new Dialog(this);
             d.setTitle("Error");
             TextView tv= new TextView(this);
             tv.setText(e.getMessage());
             d.setContentView(tv);
             d.show();
        }
        finally
        {

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, values);

            lvResult.setAdapter(adapter);

            lvResult.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    displayAd();
                    String  keyword    = (String) lvResult.getItemAtPosition(position);
                   // Toast.makeText(getApplicationContext(),
                    //        "Position :"+position+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                    //        .show();



                    //String keyword=txt.getText().toString();
                    int firstIndex = keyword.indexOf("-");
                    String numberQuestion=keyword.substring(0,firstIndex);
                    Intent i = new Intent();
                    i.putExtra("Question Number", numberQuestion);
                    setResult(RESULT_OK,i);
                    finish();


                }
            });

        }
    }

    private void SetupListViewSubject()
    {
        ListView subjectListView=(ListView) findViewById(R.id.listView);
        // chapterListAdapter = new CodeLearnAdapter();
        SubjectAdapter subAdapter = new SubjectAdapter(this);
        subjectListView.setAdapter(subAdapter);

        subjectListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent openLanguage = new Intent("com.MCP70515.LANGUAGE");

                TextView txt = (TextView) parent.getChildAt(position).findViewById(R.id.lblCode);
                String keyword = txt.getText().toString();
                openLanguage.putExtra("Subject Choose",keyword);
                Log.v("value ", "result is " + keyword);
                startActivity(openLanguage);



            }
        });
    }

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

        return super.onOptionsItemSelected(item);
    }
}

package com.ducbm.exammcp70515.free;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ducbm.exammcp70515.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import Model.AnswerDao;
import adapter.HandleJSON;


/**
 * Created by hermes on 2/13/15.
 */
public class LanguageActivity extends ActionBarActivity {

    private String urlCount = "http://default-environment-vephmhstfc.elasticbeanstalk.com/question/countByLanguage/%s";
    private String keyword;
    private String subjectChoose;
    private HandleJSON obj;
    int totalQuesion=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        Intent intent=getIntent();
        subjectChoose=intent.getStringExtra("Subject Choose");
        SetupListViewLanguage();
        SetupAdView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void SetupAdView()
    {
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private void SetupListViewLanguage()
    {
        ListView lvLanguage=(ListView)findViewById(R.id.lvLanguage);
        String[] values = new String[] { "C#","VB.NET"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        lvLanguage.setAdapter(adapter);

        lvLanguage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                keyword="";
                TextView txt = (TextView) parent.getChildAt(position).findViewById(android.R.id.text1);
                keyword = subjectChoose+txt.getText().toString();
                if(keyword.indexOf("#")>0)
                {
                    keyword=keyword.replace("#","");
                }
                if(keyword.indexOf(".")>0)
                {
                    keyword=keyword.replace(".","");
                }
                DoExam();

            }
        });
    }
    private void DoExam()
    {
        AlertDialog al = AskOption();
        al.show();
    }

    private void createNewAnswer()
    {
        boolean didItWork=true;
        try
        {
            AnswerDao entry = new AnswerDao(LanguageActivity.this);
            entry.open();
            entry.deleteAnswer(keyword);
            for(int i=1;i<=totalQuesion;i++)
            {
                entry.createEntry(Integer.toString(i),keyword,"");
            }

            entry.close();
        }
        catch (Exception e)
        {
            didItWork=false;
            Dialog d= new Dialog(this);
            d.setTitle("Error");
            TextView tv= new TextView(this);
            tv.setText(e.getMessage());
            d.setContentView(tv);
            d.show();

        }
        finally {

            if(didItWork)
            {
               // Dialog d= new Dialog(this);
             //   d.setTitle("Warnning");
              //  TextView tv= new TextView(this);
             //   tv.setText("Success");
              //  d.setContentView(tv);
              //  d.show();
            }

        }



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
        if(id== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private void GetTotalQuestion()
    {
        Log.v("keyword>>>",keyword);
        String finalUrl = String.format(urlCount, keyword);
        Log.v("keyword>>>",finalUrl);
        //country.setText(finalUrl);
        obj = new HandleJSON(finalUrl);
        obj.fetchJSON("Count");
        while(obj.parsingComplete);
        String count=obj.getCountQuestion();
        totalQuesion = Integer.parseInt(count);
    }

    private AlertDialog AskOption()
    {
        GetTotalQuestion();
        if(totalQuesion >0)//Neu co cau hoi
        {
            AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Message")
                    .setMessage("Please choose action:")
                            // .setIcon(R.drawable.icon)
                    .setNegativeButton("New Exam", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            // GetTotalQuestion();
                            createNewAnswer();
                            Intent openLanguage = new Intent("com.MCP70515.EXAM");
                            Bundle extras = new Bundle();
                            openLanguage.putExtra("Subject Language",keyword);
                            openLanguage.putExtra("Total Question",Integer.toString(totalQuesion));
                            startActivity(openLanguage);



                        }
                    })
                    .setPositiveButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    })
                    .setNeutralButton("Continue", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {
                            // GetTotalQuestion();
                            Intent openLanguage = new Intent("com.MCP70515.EXAM");
                            Bundle extras = new Bundle();
                            openLanguage.putExtra("Subject Language",keyword);
                            openLanguage.putExtra("Total Question",Integer.toString(totalQuesion));
                            startActivity(openLanguage);
                        }
                    })
                    .create();
            return myQuittingDialogBox;
        }
        else
        {
            AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                    //set message, title, and icon
                    .setTitle("Message")
                    .setMessage("We will update soon.")
                            // .setIcon(R.drawable.icon)

                    .setPositiveButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int whichButton) {

                            dialog.dismiss();
                        }
                    })

                    .create();
            return myQuittingDialogBox;

        }


        //return null;
    }

}

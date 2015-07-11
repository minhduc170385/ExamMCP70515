package com.ducbm.exammcp70515.free;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ducbm.exammcp70515.R;

/**
 * Created by hermes on 2/12/15.
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread time = new Thread(){
            @Override
            public void run(){
                try {

                    sleep(2000);


                }catch (InterruptedException e){
                    e.printStackTrace();


                }
                finally {

                    Intent openSubject = new Intent("com.MCP70515.SUBJECT");
                    startActivity(openSubject);

                }
            }
        };
        time.start();
        //Intent openSubject = new Intent("com.MCPExam.SUBJECT");
      //  startActivity(openSubject);
    }
}

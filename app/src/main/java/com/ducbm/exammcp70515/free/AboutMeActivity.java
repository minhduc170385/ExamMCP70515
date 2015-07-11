package com.ducbm.exammcp70515.free;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.ducbm.exammcp70515.R;


public class AboutMeActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutme);
        TextView tv = (TextView) findViewById(R.id.txtIntroduce);
        String content="Hi- \n "+"" +
                "\t I am a people spend a lot of money for dumps Microsoft Certificate and now, I try to share it." +
                "I'm sure almost question in my application is copy right from dumps and it is the question in exam Microsoft Certificate./n"
                + "If you have a dumps and want to share all people, please send it to me and I will update it for you."
                + "\n\n\t If you have any question, please send email for me following:  ducexammcp@gmail.com"
                + "\n\n Best and Regards," +
                "\n Duc Minh Bui";
        tv.setText(content);

    }

}

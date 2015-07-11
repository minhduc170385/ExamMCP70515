package adapter;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.ducbm.exammcp70515.R;
import Model.AnswerDao;
import Model.Question;


/**
 * Created by hermes on 2/13/15.
 */
public class ExamAdapter extends BaseAdapter {


    //List<Subject> subjects=getDataForListView();
    private LayoutInflater mInflater;
    private Context ctx;
    private Question question = new Question();
    private int totalQuestion=0;
    private String correctAnswer="";
    private String currentAnswer="";
    public ExamAdapter(Context context, Question q, int totalCount) {
        mInflater = LayoutInflater.from(context);
        ctx = context;
        totalQuestion=totalCount;
        question =q;
        getCurrentAnswer();
    }
    private void getCurrentAnswer()
    {

        try
        {
            AnswerDao entry = new AnswerDao(ctx);
            entry.open();
            Cursor c = entry.getAnswerByLanguageandQuestionNumber(question.getLanguageQuestion(), question.getQuestionNumber());

            if (c.moveToFirst()) {
                do {

                   currentAnswer=c.getString(2);

                } while (c.moveToNext());
            }
            entry.close();
        }
        catch (Exception e)
        {

        }



    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    //   @Override
  //  public Subject getItem(int position) {
   //     return subjects.get(position);
   // }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowexam, parent,false);
        }

        TextView lblTitle =(TextView) convertView.findViewById(R.id.lblTitle);
        TextView lblQuestion =(TextView) convertView.findViewById(R.id.lblQuestion);
        //lblQuestion.setSingleLine(false);
        TextView lblExplain =(TextView) convertView.findViewById(R.id.lblExplain);


        CheckBox chkAnswer1 =(CheckBox) convertView.findViewById(R.id.cbAnswer1);
        CheckBox chkAnswer2 =(CheckBox) convertView.findViewById(R.id.cbAnswer2);
        CheckBox chkAnswer3 =(CheckBox) convertView.findViewById(R.id.cbAnswer3);
        CheckBox chkAnswer4 =(CheckBox) convertView.findViewById(R.id.cbAnswer4);
        CheckBox chkAnswer5 =(CheckBox) convertView.findViewById(R.id.cbAnswer5);
        CheckBox chkAnswer6 =(CheckBox) convertView.findViewById(R.id.cbAnswer6);
        chkAnswer1.setOnClickListener(onClickListener);
        chkAnswer2.setOnClickListener(onClickListener);
        chkAnswer3.setOnClickListener(onClickListener);
        chkAnswer4.setOnClickListener(onClickListener);
        chkAnswer5.setOnClickListener(onClickListener);
        chkAnswer6.setOnClickListener(onClickListener);


        chkAnswer5.setVisibility(View.INVISIBLE);
        chkAnswer6.setVisibility(View.INVISIBLE);



        //String dataQuestion=question.getContentQuestion().replace("\\n", System.getProperty("line.separator"));
        lblTitle.setText("Question "+ question.getQuestionNumber()+" in "+ Integer.toString(totalQuestion));
        lblQuestion.setText(question.getContentQuestion().replace("\\n", System.getProperty("line.separator")));
        lblExplain.setText(question.getExplainQuestion().replace("\\n", System.getProperty("line.separator")));
        chkAnswer1.setText(question.getAnswer1().replace("\\n", System.getProperty("line.separator")));
        chkAnswer2.setText(question.getAnswer2().replace("\\n", System.getProperty("line.separator")));
        chkAnswer3.setText(question.getAnswer3().replace("\\n", System.getProperty("line.separator")));
        chkAnswer4.setText(question.getAnswer4().replace("\\n", System.getProperty("line.separator")));
        chkAnswer5.setText(question.getAnswer5().replace("\\n", System.getProperty("line.separator")));
        chkAnswer6.setText(question.getAnswer6().replace("\\n", System.getProperty("line.separator")));


        lblExplain.setVisibility(View.INVISIBLE);

        if(!question.getAnswer5().toString().equals("null"))
        {
            chkAnswer5.setVisibility(View.VISIBLE);

           // chkAnswer5.setText(question.getAnswer5().replace("\\n", System.getProperty("line.separator")));

        }
        if(!question.getAnswer6().toString().equals("null"))
        {
            chkAnswer6.setVisibility(View.VISIBLE);
            //chkAnswer6.setText(question.getAnswer6().replace("\\n", System.getProperty("line.separator")));
        }
        correctAnswer=question.getCorrectAnswer();
       // Log.v("correctAnswer" , correctAnswer);

        switch (currentAnswer)
        {
            case "A":
                chkAnswer1.setChecked(true);
                break;
            case "B":
                chkAnswer2.setChecked(true);
                break;
            case "C":
                chkAnswer3.setChecked(true);
                break;
            case "D":
                chkAnswer4.setChecked(true);
                break;
            case "E":
                chkAnswer5.setChecked(true);
                break;
            case "F":
                chkAnswer6.setChecked(true);
                break;

        }
        return convertView;

    }

    private void DisplayAnswer(String answer)
    {
        boolean didItWork=true;
        try
        {
            AnswerDao entry = new AnswerDao(ctx);
            entry.open();
            entry.updateAnswer(question.getLanguageQuestion(),question.getQuestionNumber(),answer);

            entry.close();
        }
        catch (Exception e)
        {
            didItWork=false;
            Dialog d= new Dialog(ctx);
            d.setTitle("Error");
            TextView tv= new TextView(ctx);
            tv.setText(e.getMessage());
            d.setContentView(tv);
            d.show();

        }
        finally {

            if(didItWork)
            {
                //"Correct Answer: D""
                String message;
                String answerChoose ="Correct Answer: "+answer;
                // Log.v("answerChoose" , answerChoose);
                // Log.v("answerChoose" , correctAnswer);
                if(answerChoose.equals(correctAnswer.trim()))
                {
                    message="Correct Answer.";
                }
                else
                {
                    message="Wrong Answer. "+ correctAnswer +".Please click [Explain].";
                }
                final Toast toast = Toast.makeText(ctx, message, Toast.LENGTH_SHORT);
                toast.show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toast.cancel();
                    }
                }, 5000);
            }

        }

    }
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            switch(v.getId()){
                case R.id.cbAnswer1:
                   // BackQuestion();
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("A");
                    }

                    break;
                case R.id.cbAnswer2:
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("B");
                    }
                    break;
                case R.id.cbAnswer3:
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("C");
                    }
                    break;
                case R.id.cbAnswer4:
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("D");
                    };
                    break;
                case R.id.cbAnswer5:
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("E");
                    }
                    break;
                case R.id.cbAnswer6:
                    if (((CheckBox) v).isChecked())
                    {
                        DisplayAnswer("F");
                    }
                    break;
            }
        }
    };
}

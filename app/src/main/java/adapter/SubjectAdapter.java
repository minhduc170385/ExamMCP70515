package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ducbm.exammcp70515.R;

import java.util.ArrayList;
import java.util.List;

import Model.Subject;


/**
 * Created by hermes on 2/13/15.
 */
public class SubjectAdapter extends BaseAdapter {


    List<Subject> subjects=getDataForListView();
    private LayoutInflater mInflater;
    private Context ctx;
    public SubjectAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        ctx = context;
    }
    public List<Subject> getDataForListView()
    {
        List<Subject> subjects = new ArrayList<Subject>();

        Subject sub= new Subject();

        sub= new Subject();
        sub.setTitle("TS: Web Applications Development with Microsoft .NET Framework 4");
        sub.setCode("70-515");
        subjects.add(sub);

        return subjects;

    }
    @Override
    public int getCount() {
        return subjects.size();
    }

    @Override
    public Subject getItem(int position) {
        return subjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rowsubject, parent,false);
        }

        TextView chapterName = (TextView)convertView.findViewById(R.id.lblCode);
        TextView chapterDesc = (TextView)convertView.findViewById(R.id.lblTitle);

        Subject chapter = subjects.get(position);

       // if(position==0)
       // {
       //     chapterName.setTextColor(Color.RED);
       //     chapterDesc.setTextColor(Color.RED);
       // }
       // else
       // {
            chapterName.setTypeface(null, Typeface.BOLD_ITALIC);
            chapterDesc.setTypeface(null, Typeface.ITALIC);
       // }


        chapterName.setText(chapter.getCode());
        chapterDesc.setText(chapter.getTitle());

        return convertView;

    }
}

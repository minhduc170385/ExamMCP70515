package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ducbm.exammcp70515.R;

import java.util.ArrayList;


/**
 * Created by hermes on 2/13/15.
 */
public class LanguageAdapter extends BaseAdapter {


    ArrayList<String> languages= new ArrayList<String>();

    private LayoutInflater mInflater;
    private Context ctx;
    public LanguageAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        ctx = context;
        languages.add("AWS");
        //languages.add("VB.NET");
    }

    @Override
    public int getCount() {
        return languages.size();
    }

    @Override
    public String getItem(int position) {
        return languages.get(position);
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
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent,false);
        }

        TextView chapterName = (TextView)convertView.findViewById(R.id.lblCode);
        TextView chapterDesc = (TextView)convertView.findViewById(R.id.lblTitle);

        //Subject chapter = subjects.get(position);

       // chapterName.setText(chapter.getCode());
       // chapterDesc.setText(chapter.getTitle());

        return convertView;

    }
}

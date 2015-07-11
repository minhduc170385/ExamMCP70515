package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hermes on 2/16/15.
 */
public class Question_DAO {



    public static final String KEY_ROWID="_id";
    public static final String KEY_CONTENT="question_content";
    public static final String KEY_ANSWER1="answer_1";
    public static final String KEY_ANSWER2="answer_2";
    public static final String KEY_ANSWER3="answer_3";
    public static final String KEY_ANSWER4="answer_4";
    public static final String KEY_ANSWER5="answer_5";
    public static final String KEY_ANSWER6="answer_6";
    public static final String KEY_EXPLAIN="explain";
    public static final String KEY_LANGUAGE="language";
    public static final String KEY_USERANSWER="user_answer";

    private static final String DATABASE_NAME="ExamMCP";
    private static final String DATABASE_TABLE="questionTable";

    private static final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    private static class DBHelper extends SQLiteOpenHelper
    {
        public DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            String query="CREATE TABLE " + DATABASE_TABLE +" (" +
                    KEY_ROWID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_CONTENT + " TEXT NOT NULL, " +
                    KEY_ANSWER1  +" TEXT, " +
                    KEY_ANSWER2  +" TEXT, " +
                    KEY_ANSWER3  +" TEXT, " +
                    KEY_ANSWER4  +" TEXT, " +
                    KEY_ANSWER5  +" TEXT, " +
                    KEY_ANSWER6  +" TEXT, " +
                    KEY_EXPLAIN  +" TEXT, " +
                    KEY_LANGUAGE + " TEXT NOT NULL, " +
                    KEY_USERANSWER + " TEXT )" ;




            Log.v("Query:",query);
            db.execSQL(query);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public Question_DAO(Context c)
    {
        ourContext=c;
    }

    public Question_DAO open()
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public void createEntry(String questionContent)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_CONTENT,questionContent);
        ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData()
    {
        String[] columns = new String[] {KEY_ROWID,KEY_CONTENT};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result="";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iContent = c.getColumnIndex(KEY_CONTENT);

        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
        {
            result  = result  + c.getString(iRow) + " " + c.getString(iContent) + "\n";
        }

        return result;
    }





}

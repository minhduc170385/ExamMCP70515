package Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;


/**
 * Created by hermes on 2/16/15.
 */
public class AnswerDao {



    public static final String KEY_ROWID="_id";
    public static final String KEY_QUESTIONNUMBER="question_number";
    public static final String KEY_LANGUAGE="language";
    public static final String KEY_ANSWER="answer";

    private static final String DATABASE_NAME="ExamMCP";
    private static final String DATABASE_TABLE="answerTable";

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
                    KEY_QUESTIONNUMBER + " TEXT NOT NULL, " +
                    KEY_LANGUAGE  +" TEXT, " +
                    KEY_ANSWER + " TEXT )" ;

            Log.v("Query:", query);
            db.execSQL(query);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public AnswerDao(Context c)
    {
        ourContext=c;
    }

    public AnswerDao open()
    {
        ourHelper = new DBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }
    public void close()
    {
        ourHelper.close();
    }

    public void createEntry(String questionNumber,String language,String answer)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_QUESTIONNUMBER,questionNumber);
        cv.put(KEY_LANGUAGE,language);
        cv.put(KEY_ANSWER,answer);
        ourDatabase.insert(DATABASE_TABLE,null,cv);
    }
    public boolean deleteAnswer(String language) {
        return ourDatabase.delete(DATABASE_TABLE, KEY_LANGUAGE + "=" + "\""+language+"\"", null) > 0;
    }
    public boolean updateAnswer(String language,String questionNumber, String answer) {
        ContentValues args = new ContentValues();
        args.put(KEY_ANSWER, answer);
        //args.put(KEY_EMAIL, email);
        int i= ourDatabase.update(DATABASE_TABLE, args, KEY_LANGUAGE + "=\"" + language +"\" and " + KEY_QUESTIONNUMBER +"=\""+questionNumber+"\"", null);
        return i>0;
    }

    public String getData()
    {
        String[] columns = new String[] {KEY_ROWID,KEY_ANSWER};
        Cursor c = ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result="";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iContent = c.getColumnIndex(KEY_ANSWER);

        for(c.moveToFirst(); !c.isAfterLast();c.moveToNext())
        {
            result  = result  + c.getString(iRow) + " " + c.getString(iContent) + "\n";
        }

        return result;
    }

    public Cursor getAnswer(String language) throws SQLException {
        return  ourDatabase.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID, KEY_QUESTIONNUMBER, KEY_ANSWER }, KEY_LANGUAGE + "=" + "\""+language+ "\"",
                null, null, null, null, null);

    }
    public Cursor getAnswerByLanguageandQuestionNumber(String language,String quesitonNumber) throws SQLException {
        Cursor c=  ourDatabase.query(true, DATABASE_TABLE, new String[] {
                        KEY_ROWID, KEY_QUESTIONNUMBER, KEY_ANSWER },  KEY_LANGUAGE + "=\"" + language +"\" and " + KEY_QUESTIONNUMBER +"=\""+quesitonNumber+"\"",
                null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;

    }





}


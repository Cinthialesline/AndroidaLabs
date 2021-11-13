package algonquin.cst2335.dz0001;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyOpenHelper extends SQLiteOpenHelper {
    public static final String name="MyDatabase";
    public static final int version=1;
    public static final String TABLE_NAME="MyData";
    public static final String COL_ID = "_id";
    public static final String COL_MESSAGE="MessageText";
    public static final String COL_SEND_RECEIVE="SendOrRecieve";
    public static final String COL_TIME_SENT="TimeSent";


    public MyOpenHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( String.format( "Create table %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s  INTEGER, %s TEXT );"
                , TABLE_NAME, COL_ID,                       COL_MESSAGE, COL_SEND_RECEIVE, COL_TIME_SENT ) );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }
}

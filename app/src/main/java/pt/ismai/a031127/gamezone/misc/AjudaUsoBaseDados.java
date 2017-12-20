package pt.ismai.a031127.gamezone.misc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AjudaUsoBaseDados extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "GameZone.db";
    public static final int VERSION = 1;

    public AjudaUsoBaseDados(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String s = "CREATE TABLE games(id INTEGER primary key autoincrement, game_id varchar(255), name varchar(255), summary varchar(255), releaseDate varchar(255), rating varchar(255), esrb varchar(255), personScore varchar(255), coverURL varchar(255), status varchar(1));";
        /* games(
        id INTEGER
        game_id varchar(255)
        name varchar(255)
        summary varchar(255
        releaseDate varchar(255
        rating varchar(255
        esrb varchar(255
        personScore varchar(255
        coverURL varchar(255)
        status varchar(1)
         */
        sqLiteDatabase.execSQL(s);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //DOING NOTHING
    }
}
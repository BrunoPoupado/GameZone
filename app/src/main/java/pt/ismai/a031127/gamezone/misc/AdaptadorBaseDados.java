package pt.ismai.a031127.gamezone.misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class AdaptadorBaseDados {
    private AjudaUsoBaseDados dbHelper;
    private SQLiteDatabase database;

    public AdaptadorBaseDados(Context context) {
        dbHelper = new AjudaUsoBaseDados(context.getApplicationContext());
    }

    public int getFavorites(List<Integer> id, List<String> game_id, List<String> name, List<String> summary, List<String> releaseDate, List<String> rating, List<String> esrb, List<String> personScore, List<String> coverURL, List<Integer> status) {
        String[] colunas = new String[50];
        colunas[0] = "id";
        colunas[1] = "game_id";
        colunas[2] = "name";
        colunas[3] = "summary";
        colunas[4] = "releaseDate";
        colunas[5] = "rating";
        colunas[6] = "esrb";
        colunas[7] = "personScore";
        colunas[8] = "coverURL";
        colunas[9] = "status";

        final String QUERY = "SELECT * FROM games WHERE status=?;";

        Cursor c = database.rawQuery(QUERY, new String[]{"1"});

        if (c.moveToFirst()) {
            do {
                id.add(c.getInt(0));
                game_id.add(c.getString(1));
                name.add(c.getString(2));
                summary.add(c.getString(3));
                releaseDate.add(c.getString(4));
                rating.add(c.getString(5));
                esrb.add(c.getString(6));
                personScore.add(c.getString(7));
                game_id.add(c.getString(8));
                status.add(c.getInt(9));
            } while (c.moveToNext());
        }

        c.close();
        return id.size();
    }

    public int getWishList(List<Integer> id, List<String> game_id, List<String> name, List<String> summary, List<String> releaseDate, List<String> rating, List<String> esrb, List<String> personScore, List<String> coverURL, List<Integer> status) {
        String[] colunas = new String[50];
        colunas[0] = "id";
        colunas[1] = "game_id";
        colunas[2] = "name";
        colunas[3] = "summary";
        colunas[4] = "releaseDate";
        colunas[5] = "rating";
        colunas[6] = "esrb";
        colunas[7] = "personScore";
        colunas[8] = "coverURL";
        colunas[9] = "status";

        final String QUERY = "SELECT * FROM games WHERE status=?;";

        Cursor c = database.rawQuery(QUERY, new String[]{"2"});

        if (c.moveToFirst()) {
            do {
                id.add(c.getInt(0));
                game_id.add(c.getString(1));
                name.add(c.getString(2));
                summary.add(c.getString(3));
                releaseDate.add(c.getString(4));
                rating.add(c.getString(5));
                esrb.add(c.getString(6));
                personScore.add(c.getString(7));
                game_id.add(c.getString(8));
                status.add(c.getInt(9));
            } while (c.moveToNext());
        }

        c.close();
        return id.size();
    }

    private Cursor getStatus(String gameName) {

        final String QUERY = "SELECT status FROM games WHERE name=?;";

        return database.rawQuery(QUERY, new String[]{gameName});
    }

    public boolean existsFavorites(String game_id) {
        final String QUERY = "SELECT game_id FROM games where game_id=? AND status=1;";
        Cursor cursor = database.rawQuery(QUERY, new String[]{game_id});
        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public boolean existsWishList(String game_id) {
        final String QUERY = "SELECT game_id FROM games where game_id=? AND status=2;";
        Cursor cursor = database.rawQuery(QUERY, new String[]{game_id});
        boolean b = cursor.getCount() >= 1;
        cursor.close();
        return b;
    }

    public AdaptadorBaseDados open() {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public long insertData(String game_id, String name, String summary, String releaseDate, String rating, String esrb, String personScore, String coverURL, Integer status) {
        ContentValues values = new ContentValues();
        values.put("game_id", game_id);
        values.put("name", name);
        values.put("summary", summary);
        values.put("releaseDate", releaseDate);
        values.put("rating", rating);
        values.put("esrb", esrb);
        values.put("personScore", personScore);
        values.put("coverURL", coverURL);
        values.put("status", status);
        return database.insert("games", null, values);
    }

    public int updateData(String game_id, String status, String personScore) {
        String whereCaluse = "game_id = ?";
        String[] whereArgs = new String[1];
        whereArgs[0] = game_id;
        ContentValues values = new ContentValues();

        values.put("status", status);
        values.put("personScore", personScore);
        return database.update("games", values, whereCaluse, whereArgs);
    }

    public void close() {
        dbHelper.close();
    }
}
package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import pt.ismai.a031127.gamezone.misc.Game;

public class Search extends ListActivity {

    protected ArrayList<Game> gameList;
    protected Intent intent;

    protected ArrayAdapter<Game> arrayAdapter;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        activity = this;
        intent = getIntent();
        Bundle bundle = intent.getBundleExtra("searchBundle");
        gameList = (ArrayList<Game>) bundle.getSerializable("search");
    }

    @Override
    protected void onStart() {
        super.onStart();
        arrayAdapter = new ArrayAdapter<Game>(this, android.R.layout.simple_list_item_1, gameList);
        setListAdapter(arrayAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        OpenActivity(GameInformation.class, gameList.get(position).getId());
    }

    protected void OpenActivity(Class<?> activity, String id) {
        Intent gameInfoIntent = new Intent(this, activity);
        gameInfoIntent.putExtra("id", id);
        startActivity(gameInfoIntent);
    }
}

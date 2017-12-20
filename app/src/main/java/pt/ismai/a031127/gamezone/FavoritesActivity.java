package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import pt.ismai.a031127.gamezone.misc.AdaptadorBaseDados;

public class FavoritesActivity extends Activity {

    protected TextView gameName, personScore;
    protected Spinner spinner;
    protected ArrayList<Integer> idArray, statusArray;
    protected ArrayList<String> game_idArray, game_nameArray, scoreArray, summaryArray, releaseDateArray, ratingArray, esrbArray, personScoreArray, coverURLArray;

    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        idArray = new ArrayList<Integer>();
        statusArray = new ArrayList<Integer>();
        game_idArray = new ArrayList<String>();
        game_nameArray = new ArrayList<String>();
        scoreArray = new ArrayList<String>();
        summaryArray = new ArrayList<String>();
        releaseDateArray = new ArrayList<String>();
        ratingArray = new ArrayList<String>();
        esrbArray = new ArrayList<String>();
        personScoreArray = new ArrayList<String>();
        coverURLArray = new ArrayList<String>();

        gameName = findViewById(R.id.gameName);
        personScore = findViewById(R.id.personScoreSaved);
        spinner = findViewById(R.id.spinner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        adaptadorBaseDados.getFavorites(idArray, game_idArray, game_nameArray, summaryArray, releaseDateArray, ratingArray, esrbArray, personScoreArray, coverURLArray, statusArray);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, game_nameArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (game_nameArray.size() <= 0) {
            spinner.setEnabled(false);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gameName.setText(game_nameArray.get(i));
                personScore.setText(personScoreArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }
}

package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import pt.ismai.a031127.gamezone.misc.AdaptadorBaseDados;

public class WishListActivity extends Activity {

    protected TextView gameName;
    protected EditText score;
    protected Button addToCollection;
    protected Spinner spinner;

    protected int value;

    protected ArrayList<Integer> idArray, statusArray;
    protected ArrayList<String> game_idArray, game_nameArray, scoreArray, summaryArray, releaseDateArray, ratingArray, esrbArray, personScoreArray, coverURLArray;

    protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        gameName = findViewById(R.id.gameName);
        score = findViewById(R.id.personScore);
        addToCollection = findViewById(R.id.addToCollectionButton);
        spinner = findViewById(R.id.spinner);

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
    }

    @Override
    protected void onStart() {
        super.onStart();
        adaptadorBaseDados = new AdaptadorBaseDados(this).open();
        adaptadorBaseDados.getWishList(idArray, game_idArray, game_nameArray, summaryArray, releaseDateArray, ratingArray, esrbArray, personScoreArray, coverURLArray, statusArray);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, game_nameArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        if (game_nameArray.size() <= 0) {
            spinner.setEnabled(false);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                value = i;
                gameName.setText(game_nameArray.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addToCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (score.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(WishListActivity.this, "Add an numerical score", Toast.LENGTH_SHORT).show();
                } else {
                    adaptadorBaseDados.updateData(game_idArray.get(value).toString(), "1", score.getText().toString());
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        adaptadorBaseDados.close();
    }
}
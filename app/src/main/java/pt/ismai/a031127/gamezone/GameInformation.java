package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import pt.ismai.a031127.gamezone.misc.Game;

public class GameInformation extends Activity {

    protected AsyncGenerator backgroundTask;
    protected Intent intent;
    protected String id;
    protected ArrayList<Game> gameList;

    protected TextView name, summary, releaseDate, rating, esrb;
    protected Button addToFavorites, addToWishList;
    protected EditText personScore;
    protected ImageView cover;

    protected Activity activity;

    //protected AdaptadorBaseDados adaptadorBaseDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_information);

        name = findViewById(R.id.name);
        summary = findViewById(R.id.summary);
        releaseDate = findViewById(R.id.releaseDate);
        rating = findViewById(R.id.rating);
        esrb = findViewById(R.id.esrb);
        addToFavorites = findViewById(R.id.addToFavoritesButton);
        addToWishList = findViewById(R.id.addToWishListButton);
        personScore = findViewById(R.id.personScore);
        cover = findViewById(R.id.cover);
    }

    @Override
    protected void onStart() {
        super.onStart();

        intent = getIntent();
        id = intent.getStringExtra("id");
        activity = this;

        APIWrapper wrapper = new APIWrapper(activity, "7bc57ccf5be9754d51756bdd23675688");
        Parameters parameters = new Parameters().addIds(id).addFields("*");

        wrapper.games(parameters, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray jsonArray) {
                try {
                    name.setText(jsonArray.getJSONObject(0).getString("name"));
                    summary.setText(jsonArray.getJSONObject(0).getString("summary"));
                    long timestamp = jsonArray.getJSONObject(0).getLong("first_release_date");
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    releaseDate.setText(releaseDate.getText() + sdf.format(new Date(timestamp)));
                    DecimalFormat df = new DecimalFormat(".##");
                    rating.setText(rating.getText() + df.format(jsonArray.getJSONObject(0).getDouble("rating")) + "%");
                    esrb.setText(esrb.getText() + jsonArray.getJSONObject(0).getJSONObject("esrb").getString("rating"));
                    Toast.makeText(activity, jsonArray.getJSONObject(0).getJSONObject("cover").getString("url"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    protected class AsyncGenerator extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}

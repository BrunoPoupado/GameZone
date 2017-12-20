package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import pt.ismai.a031127.gamezone.misc.AdaptadorBaseDados;

public class GameInformation extends Activity {

    protected Intent intent;
    protected String id;

    protected TextView name, summary, releaseDate, rating, esrb;
    protected Button addToFavorites, addToWishList;
    protected EditText personScore;
    protected ImageView cover;

    protected Activity activity;

    protected AdaptadorBaseDados adaptadorBaseDados;

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
                    final String game_id = jsonArray.getJSONObject(0).getString("id");
                    final String nameValue = jsonArray.getJSONObject(0).getString("name");
                    final String summaryValue = jsonArray.getJSONObject(0).getString("summary");
                    DecimalFormat df = new DecimalFormat(".##");
                    final String ratingValue = df.format(jsonArray.getJSONObject(0).getDouble("rating"));
                    final String esrbValue = jsonArray.getJSONObject(0).getJSONObject("esrb").getString("rating");
                    final long timestamp = jsonArray.getJSONObject(0).getLong("first_release_date");
                    final String coverURLValue = jsonArray.getJSONObject(0).getJSONObject("cover").getString("url");
                    new AsyncGenerator(cover).execute(coverURLValue);
                    name.setText(nameValue);
                    summary.setText(summaryValue);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    releaseDate.setText(releaseDate.getText() + sdf.format(new Date(timestamp)));
                    rating.setText(rating.getText() + ratingValue + "%");
                    esrb.setText(esrb.getText() + esrbValue);

                    SystemClock.sleep(3);

                    adaptadorBaseDados = new AdaptadorBaseDados(activity).open();

                    if (adaptadorBaseDados.existsFavorites(game_id)) {
                        addToFavorites.setText("ADDED");
                        addToFavorites.setEnabled(false);
                        addToWishList.setEnabled(false);
                    } else {
                        addToFavorites.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adaptadorBaseDados.insertData(game_id, nameValue, summaryValue, String.valueOf(timestamp), ratingValue, esrbValue, personScore.getText().toString(), coverURLValue, 1);
                                addToFavorites.setText("ADDED");
                                addToFavorites.setEnabled(false);
                                addToWishList.setEnabled(false);
                            }
                        });
                    }

                    if (adaptadorBaseDados.existsWishList(game_id)) {
                        addToWishList.setText("ADDED");
                        addToWishList.setEnabled(false);
                        addToFavorites.setEnabled(false);
                    } else {
                        addToWishList.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adaptadorBaseDados.insertData(game_id, nameValue, summaryValue, String.valueOf(timestamp), ratingValue, esrbValue, personScore.getText().toString(), coverURLValue, 2);
                                addToWishList.setText("ADDED");
                                addToWishList.setEnabled(false);
                                addToFavorites.setEnabled(false);
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError volleyError) {

            }
        });
    }

    protected class AsyncGenerator extends AsyncTask<String, Void, Bitmap> {

        protected ImageView cover;

        public AsyncGenerator(ImageView cover) {
            this.cover = cover;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap coverImage = null;
            try {
                InputStream inputStream = new URL("http:" + url).openStream();
                coverImage = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return coverImage;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            cover.setImageBitmap(bitmap);
        }
    }
}

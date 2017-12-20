package pt.ismai.a031127.gamezone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.callback.onSuccessCallback;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import pt.ismai.a031127.gamezone.misc.Game;

public class MainActivity extends Activity {

    //https://api-2445582011268.apicast.io/games/?search=Halo&fields=*
    //7bc57ccf5be9754d51756bdd23675688

    protected EditText searchField;
    protected Button searchButton, favoritesButton, wishListButton;

    protected ArrayList<Game> gameList;
    protected Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchField = findViewById(R.id.searchField);
        searchButton = findViewById(R.id.searchButton);
        favoritesButton = findViewById(R.id.favoriteButton);
        wishListButton = findViewById(R.id.wishListButton);
    }

    @Override
    protected void onStart() {
        super.onStart();

        activity = this;
        gameList = new ArrayList<Game>();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                APIWrapper wrapper = new APIWrapper(activity, "7bc57ccf5be9754d51756bdd23675688");
                Parameters parameters = new Parameters().addFields("*").addSearch(searchField.getText().toString());

                wrapper.games(parameters, new onSuccessCallback() {
                    @Override
                    public void onSuccess(JSONArray jsonArray) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject rec = null;
                            try {
                                rec = jsonArray.getJSONObject(i);
                                String id = rec.getString("id");
                                String name = rec.getString("name");
                                gameList.add(new Game(id, name));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        Intent intent = new Intent(activity, Search.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("search", (Serializable) gameList);
                        intent.putExtra("searchBundle", bundle);
                        startActivity(intent);
                        //gameList.add(new Game(jsonArray.getString(), ""));
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        Toast.makeText(activity, "No Game Found!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(FavoritesActivity.class);
            }
        });

        wishListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenActivity(WishListActivity.class);
            }
        });
    }

    protected void OpenActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}

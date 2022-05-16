package com.anusha.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainActivity2 extends AppCompatActivity{
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView tvOne;
    TextInputEditText editText;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvOne = findViewById(R.id.tv_one);
        editText = findViewById(R.id.et_search);
        progressBar = findViewById(R.id.progress_bar);

        ////initializations for google to access user data
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            String name = account.getDisplayName();
            tvOne.setText("Hi "+name);
        }

        //initializations for facebook to access user data
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            if(accessToken != null && accessToken.isExpired() == false) {
                                String name = object.getString("name");
                                tvOne.setText("Hi "+name);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Application code
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link");
        request.setParameters(parameters);
        request.executeAsync();

        progressBar.setVisibility(View.VISIBLE);
        //to fetch data from url and put into pojos
        new AsynTask(progressBar).execute("https://www.googleapis.com/books/v1/volumes?q=%7Bbooktite/bookkind%7D");
    }

    public void signOut(View view) {

        //sign out the google account
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                moveToFirstActivity();
            }
        });

        //sign out the facebook account
        LoginManager.getInstance().logOut();
        moveToFirstActivity();
    }

    private void moveToFirstActivity() {
        finish();
        startActivity(new Intent(MainActivity2.this,MainActivity.class));

    }

    public void moveToNextActivity() {
        startActivity(new Intent(MainActivity2.this,MainActivity3.class));

    }

    public void GoingForSearch(View view) {
        if(!editText.getText().toString().isEmpty()){
            List<Item> filteredList = ApiResultRootHolder.root.items.stream().filter(item -> checkCtgryForEachItem(item)).collect(Collectors.toList());
            if(filteredList.isEmpty())
                Toast.makeText(this,"No books found with this category!!",Toast.LENGTH_LONG).show();
            else {
                CategoryFilteredListHolder.filteredList = filteredList;
                moveToNextActivity();
            }
        }
        else{
            Toast.makeText(this,"Enter book category!!!",Toast.LENGTH_LONG).show();
        }
    }


    private boolean checkCtgryForEachItem(Item item) {
        String category = editText.getText().toString().trim();
        List<String> list = item.volumeInfo.categories.stream().filter(cat->(cat.toLowerCase(Locale.ROOT)).contains(category.toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        return !list.isEmpty();
    }
}
package com.anusha.books;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.AsynchronousChannelGroup;

import javax.net.ssl.HttpsURLConnection;

public class AsynTask extends AsyncTask<String,Void,String> {
    ProgressBar progressBar;

    public AsynTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected String doInBackground(String... strings) {
        StringBuffer result = new StringBuffer();
        String link = strings[0];
        try {
            URL url = new URL(link);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressBar.setVisibility(View.GONE);
        Gson gson = new Gson();
        Root root = gson.fromJson(s, Root.class);
        if(root != null){
            ApiResultRootHolder.root = root;
        }
    }

}

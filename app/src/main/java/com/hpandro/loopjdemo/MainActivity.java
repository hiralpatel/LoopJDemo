package com.hpandro.loopjdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hpandro.loopjdemo.loopjclient.LoopJGet;
import com.hpandro.loopjdemo.loopjclient.LoopJPost;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    Context context;
    private String filepath; // Any file path to send server

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        loopJGetAPICall();

        loopJPostAPICall();
    }

    /**
     * LoopJ GET api call
     */
    private void loopJGetAPICall() {
        //TODO You can show progressbar here
        new LoopJGet(context, "", onLoopJGetAPICallComplete, "YOUR API URL HERE");
    }

    /**
     * LoopJ GET api call
     */
    LoopJGet.OnLoopJGetCallComplete onLoopJGetAPICallComplete = new LoopJGet.OnLoopJGetCallComplete() {
        @Override
        public void response(String result) {
            //TODO You can hide progressbar here
            try {
                JSONObject jobj = new JSONObject(result);
                //TODO your code here
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * LoopJ POST api call
     */
    private void loopJPostAPICall() {
        //TODO Your parameter required for send to POST method
        RequestParams params = new RequestParams();
        params.put("param1", "value here");
        params.put("param2", "value here");
        params.put("param3", "value here");
        try {
            if (filepath.length() > 0)
                params.put("param_file", new File(filepath)); //TODO for send any file to server
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //TODO You can show progressbar here
        new LoopJPost(context, "", onLoopJPostAPICallComplet, "YOUR API URL HERE", params);
    }

    /**
     * API parsing of POST call
     */
    LoopJPost.OnLoopJPostCallComplete onLoopJPostAPICallComplet = new LoopJPost.OnLoopJPostCallComplete() {

        @Override
        public void response(String result) {
            //TODO You can hide progressbar here
            try {
                JSONObject jobj = new JSONObject(result);
                //TODO your code here
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
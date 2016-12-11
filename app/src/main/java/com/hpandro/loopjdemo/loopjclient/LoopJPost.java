package com.hpandro.loopjdemo.loopjclient;

import android.app.ProgressDialog;
import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoopJPost {


    private final OnLoopJPostCallComplete onLoopJPostCallComplete;
    AsyncHttpClient client = new AsyncHttpClient();
    private ProgressDialog dialog;
    private String message;
    private Context context;
    int DEFAULT_TIMEOUT = 60 * 1000; // Timeout for api call

    /**
     * @param ctx
     * @param progressMessage
     * @param onLoopJPostCallComplete
     * @param url
     * @param requestParams
     */
    public LoopJPost(Context ctx, String progressMessage, final OnLoopJPostCallComplete onLoopJPostCallComplete, String url, RequestParams requestParams) {
        client.setTimeout(DEFAULT_TIMEOUT);
        message = progressMessage;
        this.context = ctx;
        this.onLoopJPostCallComplete = onLoopJPostCallComplete;
        if (!(message.equals(""))) {
            dialog = new ProgressDialog(ctx);
            dialog.setMessage(progressMessage);
        }

        System.out.println("++++++ Request url: " + url);
        System.out.println("++++++ Request requestParams: " + requestParams);
        client.post(url.replace(" ", "%20"), requestParams, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
                if (!(message.equals(""))) {
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
//                System.out.println("onSuccess:" + response.toString());
                if (!(message.equals(""))) {
                    if (dialog != null) {
                        dialog.hide();
                        dialog.dismiss();
                    }
                }
                System.out.println("++++++ Response onSuccess: " + response.toString());
                onLoopJPostCallComplete.response(response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                System.out.println("++++++ Response onFailure:" + responseString);
                if (!(message.equals(""))) {
                    if (dialog != null) {
                        dialog.hide();
                        dialog.dismiss();
                    }
                }
                onLoopJPostCallComplete.response(responseString);
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }


    public interface OnLoopJPostCallComplete {
        void response(String result);
    }

    public void cancelRequest() {
        client.cancelAllRequests(true);
    }
}
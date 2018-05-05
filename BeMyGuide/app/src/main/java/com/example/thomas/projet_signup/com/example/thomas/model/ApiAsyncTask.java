package com.example.thomas.projet_signup.com.example.thomas.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.config.Registry;
import cz.msebera.android.httpclient.config.RegistryBuilder;
import cz.msebera.android.httpclient.conn.HttpClientConnectionManager;
import cz.msebera.android.httpclient.conn.socket.ConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLConnectionSocketFactory;
import cz.msebera.android.httpclient.conn.ssl.SSLSocketFactory;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.impl.conn.BasicHttpClientConnectionManager;

public class ApiAsyncTask extends AsyncTask<String, Void, Void> {
    // Attributs
    private String response;
    private List<NameValuePair> nameValuePairs;
    // This is the reference to the associated listener
    private final TaskListener taskListener;


    // Constructeurs
    public ApiAsyncTask(TaskListener taskListener){
        this.taskListener = taskListener;
    }
    public ApiAsyncTask(List<NameValuePair> nameValuePairs, TaskListener taskListener)
    {
        this.nameValuePairs = nameValuePairs;
        this.taskListener = taskListener;
    }

    // Méthodes
    protected Void doInBackground(String... urls){
        response = "";

        // For each url
        for(String url:urls) {

            SSLContext sslContext = null;
            try {
                sslContext = SSLContext.getInstance("SSLv3");

                // set up a TrustManager that trusts everything
                sslContext.init(null,  new TrustManager[] { new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        Log.d("AcceptedIssuers","getAcceptedIssuers =============");
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        Log.d("checkClientTrusted","checkClientTrusted =============");
                    }

                    public void checkServerTrusted(X509Certificate[] certs,String authType) {
                        Log.d("checkServerTrusted","checkServerTrusted =============");
                    }
                } }, new SecureRandom());
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }

            // Create client.
            HttpClientBuilder builder = HttpClientBuilder.create();

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext);
            builder.setSSLSocketFactory(sslConnectionSocketFactory);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnectionSocketFactory)
                    .build();

            HttpClientConnectionManager ccm = new BasicHttpClientConnectionManager(registry);

            builder.setConnectionManager(ccm);
            HttpClient httpClient = builder.build();
            HttpPost httpPost = new HttpPost(url);
            Log.e("mainToPost", "mainToPost " + nameValuePairs.toString());

            try {

                // Use UrlEncodedFormEntity to send in proper format which we need
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute http request
                HttpResponse execute = httpClient.execute(httpPost);

                //
                InputStream content = execute.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // In onPostExecute we check if the listener is valid
        if(this.taskListener != null) {

            // And if it is we call the callback function on it.
            this.taskListener.onFinished();
        }
        Log.e("finish", response);
    }

    // Getters & Setters
    public String getResponse(){return response;}
    public JSONObject getResponseJSON(){
        JSONObject responseJSON = new JSONObject();
        try {
            responseJSON = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseJSON;
    }
    public JSONArray getResponseJSONArray(){
        JSONArray responseJSON = new JSONArray();

        try {
            responseJSON = new JSONArray(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  responseJSON;
    }
    public List<NameValuePair> getNameValuePairs(){return nameValuePairs;}

    public void setResponse(String value){response = value;}
    public void setNameValuePairs(List<NameValuePair> value){nameValuePairs = value;}


    public interface TaskListener{
        public void onFinished();
    }
}


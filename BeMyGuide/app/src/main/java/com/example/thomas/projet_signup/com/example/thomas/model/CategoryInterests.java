package com.example.thomas.projet_signup.com.example.thomas.model;

import android.content.Context;
import android.graphics.Point;
import android.util.Log;

import com.example.thomas.projet_signup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cz.msebera.android.httpclient.NameValuePair;

/**
 * Created by Erdrixx on 31/03/2016.
 */
public class CategoryInterests {
    // ATTRIBUTS statics
    static private TreeMap<String,ArrayList<PointOfInterest>> LISTPOINTS;    // Contient la liste des points d'intérêts catégorisés.
    static private ApiAsyncTask REQUESTASYNC;
    static private Context MCONTEXT;
    static private int CODE = 200;

    // ATTRIBUTS
    private String nameCategory;
    private Context myContext;

    // Constructeur


    // METHODES staticS
    static public void Init(Context context, ApiAsyncTask.TaskListener taskListener){
        // Récupération de l'url de création.
        MCONTEXT= context;
        String request = MCONTEXT.getResources().getString(R.string.points_urlGet);


        // Insertion des données dans le POST.
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        // Lancement de la requête HTTP.
        GetTaskListener getTaskListener = new GetTaskListener(taskListener);
        REQUESTASYNC = new ApiAsyncTask(nameValuePairs, getTaskListener);
        REQUESTASYNC.execute(request);
    }
    static public TreeMap<String,ArrayList<PointOfInterest>> GetListPoints(){return LISTPOINTS;}

    /**
     * Task with callback to Authenticate http.
     */
    private static class GetTaskListener implements ApiAsyncTask.TaskListener {
        ApiAsyncTask.TaskListener taskListener;

        public GetTaskListener(ApiAsyncTask.TaskListener taskListener)
        {
            this.taskListener = taskListener;
        }
        @Override
        public void onFinished() {
            String codeString = MCONTEXT.getResources().getString(R.string.user_code);

            // On récupère la réponse sous format JSON.

            JSONObject responseObj = REQUESTASYNC.getResponseJSON();
            JSONArray responseArray = REQUESTASYNC.getResponseJSONArray();
            Log.d("Category", responseObj.toString());

            try {
                // Contrôle la présence d'erreur
                if (responseObj.has(codeString)) {

                    CODE = responseObj.getInt(codeString);
                }
                else {
                    LISTPOINTS = new TreeMap<>();
                    for(int i = 0; i < responseArray.length(); i++)
                    {
                        JSONObject obj = responseArray.getJSONObject(i);
                        if(obj.has("nameType")) {
                            ArrayList<PointOfInterest> listPoint = new ArrayList<>();
                            JSONArray list = obj.getJSONArray("listePoint");
                            for (int j = 0; j < list.length(); j++) {
                                JSONObject point = list.getJSONObject(j);
                                if (point.has("nameGoogle") && point.has("namePoint")) {
                                    String namePoint = point.getString("namePoint");
                                    String nameGoogle = point.getString("nameGoogle");
                                    String checkboxStyle = (point.has("checkboxStyle")) ? point.getString("checkboxStyle") : "checkbox_cinema";
                                    String color = (point.has("color")) ? point.getString("color") : "cinema_color";
                                    PointOfInterest nouvPoint = new PointOfInterest(namePoint, nameGoogle, checkboxStyle, color);
                                    listPoint.add(nouvPoint);
                                }
                            }
                            LISTPOINTS.put(obj.getString("nameType"), listPoint);
                        }

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(taskListener != null)
                taskListener.onFinished();
        }
    }
}

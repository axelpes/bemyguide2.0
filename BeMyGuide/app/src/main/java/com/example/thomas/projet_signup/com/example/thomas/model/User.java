package com.example.thomas.projet_signup.com.example.thomas.model;


import android.content.Context;
import android.content.res.Resources;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;

import com.example.thomas.projet_signup.R;
import com.example.thomas.projet_signup.com.example.thomas.model.ApiAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

/**
 * Created by Erdrixx on 22/03/2016.
 */

public class User {


    // Attributes
    private String name;
    private String firstName;
    private String urlImage;
    private String eMail;
    private String connexionMode;
    private Location currentLocation;
    private String token;
    private String accessToken;
    private String refreshToken;
    protected JSONObject jsonObject;
    protected List<NameValuePair> nameValuePairs;
    private Context myContext;
    private int codeAuthenticate = 400;
    private int codeInscription = 400;
    private ApiAsyncTask requestAsync;


    // Resources acces
    private Resources resources;
    private String eMailString;
    private String nameString;
    private String firstNameString;
    private String tokenString;
    private String refreshTokenString;
    private String urlImageString;
    private String passwordString;
    private String urlAddUserString;
    private String urlAuthenticateString;
    private String userString;
    private String modeAppliString;
    private String connectionModeString;
    private String codeString;

    // Empty Constructor

    public User(Context context){
        jsonObject = new JSONObject();
        myContext = context;
        Resources resources   = myContext.getResources();
        eMailString           = resources.getString(R.string.user_email);
        nameString            = resources.getString(R.string.user_Name);
        firstNameString       = resources.getString(R.string.user_firstName);
        tokenString           = resources.getString(R.string.user_token);
        refreshTokenString    = resources.getString(R.string.user_refreshToken);
        urlImageString        = resources.getString(R.string.user_urlImage);
        passwordString        = resources.getString(R.string.user_password);
        urlAddUserString      = resources.getString(R.string.user_urlAddUser);
        urlAuthenticateString = resources.getString(R.string.user_urlAuthenticate);
        userString            = resources.getString(R.string.user);
        modeAppliString       = resources.getString(R.string.user_modeAppli);
        connectionModeString  = resources.getString(R.string.user_connectionMode);
        codeString            = resources.getString(R.string.user_code);
    }

    // After Signup Constructor

    public User(String name, String firstName, String eMail, String connexionMode, Context myContext){

        this.name = name;
        this.myContext = myContext;
        this.firstName = firstName;
        this.eMail = eMail;
        this.connexionMode = connexionMode;
        this.urlImage = "http://cancerspreventions.fr/wp-content/themes/cancer-et-prevention/skeleton/images/silhouette.jpg";
        jsonObject = new JSONObject();
        Resources resources   = myContext.getResources();
        eMailString           = resources.getString(R.string.user_email);
        nameString            = resources.getString(R.string.user_Name);
        firstNameString       = resources.getString(R.string.user_firstName);
        tokenString           = resources.getString(R.string.user_token);
        refreshTokenString    = resources.getString(R.string.user_refreshToken);
        urlImageString        = resources.getString(R.string.user_urlImage);
        passwordString        = resources.getString(R.string.user_password);
        urlAddUserString      = resources.getString(R.string.user_urlAddUser);
        urlAuthenticateString = resources.getString(R.string.user_urlAuthenticate);
        userString            = resources.getString(R.string.user);
        modeAppliString       = resources.getString(R.string.user_modeAppli);
        connectionModeString  = resources.getString(R.string.user_connectionMode);
        codeString            = resources.getString(R.string.user_code);
    }

    /***
     * Méthode permettant la création d'un utilisateur à partir des données de l'utilisateur.
     * @param password
     * @return int : représentant le code d'erreur retourné par l'API ou 200 si tout est ok
     * @throws JSONException
     */
    public void addUserJSON(String password, ApiAsyncTask.TaskListener taskListener) throws JSONException {

        // Récupération de l'url de création.
        String request = urlAddUserString+connexionMode;

        // Insertion des informations relatives à la création de compte.
        jsonObject = new JSONObject();
        jsonObject.put(eMailString, eMail);
        jsonObject.put(nameString, name);
        jsonObject.put(firstNameString, firstName);
        jsonObject.put(passwordString, password);
        jsonObject.put(urlImageString, urlImage);

        // Insertion des données dans le POST.
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(userString, jsonObject.toString()));

        // Lancement de la requête HTTP.
        InscriptionTaskListener inscriptionTaskListener = new InscriptionTaskListener(taskListener);
        requestAsync = new ApiAsyncTask(nameValuePairs, inscriptionTaskListener);
        requestAsync.execute(request);

    }

    /**
     * Connexion de l'utilisateur.
     * @param password
     * @return
     * @throws JSONException
     */
    public void authenticateJSON(String password, ApiAsyncTask.TaskListener taskListener) throws JSONException {

        // Récupération url authentication.
        String request = urlAuthenticateString;

        // Insertion des informations relatives à la connexion.
        jsonObject = new JSONObject();
        jsonObject.put(eMailString, eMail);
        jsonObject.put(passwordString, password);

        // Insert data into request Post.
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair(userString, jsonObject.toString()));

        // Lancement de la requette HTTP.
        AuthenticateTaskListener authenticateTaskListener = new AuthenticateTaskListener(taskListener);
        requestAsync = new ApiAsyncTask(nameValuePairs, authenticateTaskListener);
        requestAsync.execute(request);

    }

    public void updateProfilJSON(ApiAsyncTask.TaskListener taskListener){
        if(token != null){

        }
    }
    // Getters & setters
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getUrlImage() {return urlImage;}
    public void setUrlImage(String urlImage) {this.urlImage = urlImage;}

    public String geteMail() {return eMail;}
    public void seteMail(String eMail) {this.eMail = eMail;}

    public String getConnexionMode() {return connexionMode;}
    public void setConnexionMode(String connexionMode) {this.connexionMode = connexionMode;}

    public Location getCurrentLocation() {return currentLocation;}

    public void setCurrentLocation(Location currentLocation) {this.currentLocation = currentLocation;}

    public String getToken() {return token;}
    public void setToken(String token) {this.token = token;}

    public String getAccessToken() {return accessToken;}
    public void setAccessToken(String accessToken) {this.accessToken = accessToken;}

    public String getRefreshToken() {return refreshToken;}
    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}

    public int getCodeAuthenticate(){return codeAuthenticate;}
    public void setCodeAuthenticate(int codeAuthenticate){this.codeAuthenticate = codeAuthenticate;}

    public int getCodeInscription(){return codeInscription;}
    public void setCodeInscription(int codeInscription){this.codeInscription = codeInscription;}

    /**
     * Task with callback to Inscription
     */
    private class InscriptionTaskListener implements ApiAsyncTask.TaskListener{
        ApiAsyncTask.TaskListener taskListener;

        public InscriptionTaskListener(ApiAsyncTask.TaskListener taskListener){
            this.taskListener = taskListener;
        }

        @Override
        public void onFinished() {
            // Récupére résultat sous format JSON.
            JSONObject responseObj = requestAsync.getResponseJSON();
            try {
                codeInscription =  responseObj.has(codeString)?responseObj.getInt(codeString):200;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            taskListener.onFinished();
        }
    }

    /**
     * Task with callback to Authenticate http.
     */
    private class AuthenticateTaskListener implements ApiAsyncTask.TaskListener {
        ApiAsyncTask.TaskListener taskListener;

        public AuthenticateTaskListener(ApiAsyncTask.TaskListener taskListener)
        {
            this.taskListener = taskListener;
        }
        @Override
        public void onFinished() {
            // On récupère la réponse sous format JSON.
            JSONObject responseObj = requestAsync.getResponseJSON();
            try {
                // Contrôle la présence d'erreur
                if (responseObj.has(codeString)) {

                    codeAuthenticate = responseObj.getInt(codeString);
                }
                // Contrôle la présence du token.
                else if (responseObj.has(tokenString) && responseObj.has(refreshTokenString)) {
                    //Contrôle la présence d'un utilisateur
                    if (responseObj.has(userString)) {
                        JSONObject user = responseObj.getJSONObject(userString);
                        // Vérifie la présence des champs indispensables.
                        if (user.has(eMailString) && user.has(nameString) && user.has(firstNameString) && user.has(connectionModeString)) {

                            // Cas du mode de connexion appli:
                            if (user.getString(connectionModeString) == modeAppliString) {

                                token = responseObj.getString(tokenString);
                                refreshToken = responseObj.getString(refreshToken);
                                eMail = responseObj.getString(eMailString);
                                name = responseObj.getString(nameString);
                                firstName = responseObj.getString(firstNameString);
                                connexionMode = responseObj.getString(connexionMode);
                                urlImage = (responseObj.has(urlImageString)) ? responseObj.getString(urlImageString) : "";
                            }
                            codeAuthenticate = 200;
                        } else
                            codeAuthenticate = 400;
                    } else
                        codeAuthenticate = 400;
                } else
                    codeAuthenticate = 400;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            taskListener.onFinished();
        }
    }
}





package com.example.thomas.projet_signup.com.example.thomas.controller;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thomas.projet_signup.R;
import com.example.thomas.projet_signup.com.example.thomas.model.ApiAsyncTask;
import com.example.thomas.projet_signup.com.example.thomas.model.CategoryInterests;
import com.example.thomas.projet_signup.com.example.thomas.model.User;

import org.json.JSONException;

public class SigninActivity extends AppCompatActivity implements ApiAsyncTask.TaskListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    // UI Attributes
    private Button login_button;
    private EditText email_input;
    private EditText password_input;
    private TextView signup_link;
    private ProgressDialog dialog;

    // User Attributes
    private User user;
    private int codeAuthenticate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        login_button = (Button)findViewById(R.id.login_button);
        email_input= (EditText)findViewById(R.id.email_input);
        password_input = (EditText)findViewById(R.id.password_input);
        signup_link = (TextView)findViewById(R.id.signup_link);

        CategoryInterests.Init(this,null);
        CategoryInterests.GetListPoints();
        // Login click
        login_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {

        if (!validate()) {
            onLoginFailed();
            return;
        }

        login_button.setEnabled(false);

        user = new User(this);
        user.seteMail(email_input.getText().toString());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.show();

        codeAuthenticate = 400;
        try {
            user.authenticateJSON(password_input.getText().toString(), this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    public void onLoginSuccess() {
        login_button.setEnabled(true);
        Toast.makeText(getBaseContext(), "Login succeed", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivityForResult(intent, 0);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_button.setEnabled(true);
    }

    // Vérification des champs
    public boolean validate() {
        boolean valid = true;

        String emailstring = email_input.getText().toString();
        String passwordstring = password_input.getText().toString();

        if (emailstring.isEmpty()) {
            email_input.setError("Please enter your email address");
            valid = false;
        } else {
            email_input.setError(null);
        }

        if (passwordstring.isEmpty()) {
            password_input.setError("Please enter your password");
            valid = false;
        } else {
            password_input.setError(null);
        }

        return valid;
    }
    // Callback call by AsyncTask.
    @Override
    public void onFinished() {
        codeAuthenticate = user.getCodeAuthenticate();
        if(dialog.isShowing())
            dialog.dismiss();
        if(codeAuthenticate != 200)
        {
            onLoginFailed();
        }
        else
        {
            onLoginSuccess();
        }
    }
}
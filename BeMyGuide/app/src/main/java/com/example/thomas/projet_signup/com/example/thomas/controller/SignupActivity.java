package com.example.thomas.projet_signup.com.example.thomas.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.thomas.projet_signup.R;
import com.example.thomas.projet_signup.com.example.thomas.model.ApiAsyncTask;
import com.example.thomas.projet_signup.com.example.thomas.model.User;

import org.json.JSONException;

public class SignupActivity extends AppCompatActivity implements ApiAsyncTask.TaskListener {

    private Button signup_button;
    private EditText name_input;
    private EditText firstName_input;
    private EditText eMail_input;
    private EditText password_input;
    private EditText password_second_input;
    private ProgressDialog dialog;

    private User user;
    private int codeInscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_button = (Button)findViewById(R.id.signup_button);
        name_input = (EditText)findViewById(R.id.name_input);
        firstName_input = (EditText)findViewById(R.id.firstName_input);
        eMail_input = (EditText)findViewById(R.id.email_input);
        password_input = (EditText)findViewById(R.id.password_input);
        password_second_input = (EditText)findViewById(R.id.password_second_input);


        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signup_button.setEnabled(false);

        //Inscription

        user = new User(name_input.getText().toString(), firstName_input.getText().toString(), eMail_input.getText().toString(), getString(R.string.user_modeAppli), this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.show();

        codeInscription = 400;
        try {
            user.addUserJSON(password_input.getText().toString(), this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void onSignupSuccess() {
        Toast.makeText(getBaseContext(), "Signup succeed", Toast.LENGTH_LONG).show();
        signup_button.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivityForResult(intent, 0);


    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
        signup_button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String namestring = name_input.getText().toString();
        String firstNamestring = firstName_input.getText().toString();
        String emailstring = eMail_input.getText().toString();
        String passwordstring = password_input.getText().toString();
        String passwordsecondstring = password_second_input.getText().toString();

        // Name condition
        if (namestring.isEmpty()) {
            name_input.setError("Please enter your name");
            valid = false;
        } else {
            name_input.setError(null);
        }

        // First Name condition
        if (firstNamestring.isEmpty()) {
            name_input.setError("Please enter your first name");
            valid = false;
        } else {
            name_input.setError(null);
        }

        // Email condition
        if (emailstring.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(emailstring).matches()) {
            eMail_input.setError("Please enter a valid email address");
            valid = false;
        } else {
            eMail_input.setError(null);
        }

        if (passwordstring.isEmpty() || passwordstring.length() < 6) {
            password_input.setError("Please enter a password with a minimum of 6 characters");
            valid = false;
        }
        else if (passwordstring.length() > 60){
            password_input.setError("Please enter a password with a maximum of 60 characters");
            valid = false;
        }

        else if (!passwordstring.equals(passwordsecondstring)){
            password_second_input.setError("The second password is different from the first one");
            valid = false;
        }
        else {
            password_input.setError(null);
        }

        return valid;
    }

    // Callback call by AsyncTask.
    @Override
    public void onFinished() {
        codeInscription = user.getCodeInscription();
        if(dialog.isShowing())
            dialog.dismiss();

        if(codeInscription != 200){
            onSignupFailed();
            return;
        }
        onSignupSuccess();
    }
}

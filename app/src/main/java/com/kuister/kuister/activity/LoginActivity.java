package com.kuister.kuister.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kuister.kuister.R;
import com.kuister.kuister.helper.ReferenceUrl;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity: ";
    private EditText emailUser;
    private EditText passUser;
    private Button btn_login;
    private Button btn_to_register;
    private Button btn_to_reset;
    private Firebase mRef;
    private Firebase.AuthStateListener mAuthStateListener;
    private AuthData mAuthData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);

        mAuthStateListener = new Firebase.AuthStateListener(){
            @Override
            public void onAuthStateChanged(AuthData authData) {
                mAuthData = authData;
                if(authData != null){
                    Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };

        mRef.addAuthStateListener(mAuthStateListener);

        emailUser = (EditText)findViewById(R.id.email);
        passUser = (EditText)findViewById(R.id.password);
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_to_register = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btn_to_reset = (Button) findViewById(R.id.btnLinkToReset);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Logging in ...");

        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailUser.getText().toString();
                String pass = passUser.getText().toString();

                email = email.trim();
                pass = pass.trim();

                if(!validateForm()){
                    return;
                }
                else{
                    showDialog();

                    mRef.authWithPassword(email, pass, authResultHandler);
                }
            }
        });

        btn_to_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
    }

    Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler() {
        @Override
        public void onAuthenticated(AuthData authData) {
            // Authenticated successfully with payload authData
            // Go to main activity
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            hideDialog();
            startActivity(intent);
        }

        @Override
        public void onAuthenticationError(FirebaseError firebaseError) {
            showErrorMessageToUser(firebaseError.getMessage());
        }
    };

    public void showErrorMessageToUser(String ErrorMessage){
        // Authenticated failed with error firebaseError
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage(ErrorMessage)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        hideDialog();
    }

    public boolean validateForm(){
        boolean valid = true;
        
        String email = emailUser.getText().toString();
        if (TextUtils.isEmpty(email)){
            emailUser.setError("Required.");
            valid = false;
        }
        else {
            emailUser.setError(null);
        }

        String password = passUser.getText().toString();
        if (TextUtils.isEmpty(password)){
            passUser.setError("Required");
            valid = false;
        }
        else {
            passUser.setError(null);
        }

        return valid;
    }

    private void showDialog(){
        if(!pDialog.isShowing())
            pDialog.show();
    }
    private void hideDialog(){
        if(pDialog.isShowing())
            pDialog.dismiss();
    }
}
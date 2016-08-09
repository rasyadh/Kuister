package com.kuister.kuister.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kuister.kuister.R;
import com.kuister.kuister.helper.ReferenceUrl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity: ";
    private Firebase mRef;
    private Button btn_register;
    private Button btn_to_login;
    private EditText emailRegister;
    private EditText passRegister;
    private EditText username;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        username = (EditText)findViewById(R.id.name);
        emailRegister = (EditText)findViewById(R.id.email);
        passRegister = (EditText)findViewById(R.id.password);
        btn_register = (Button)findViewById(R.id.btnRegister);
        btn_to_login = (Button)findViewById(R.id.btnLinkToLoginScreen);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        pDialog.setMessage("Registering ...");

        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailRegister.getText().toString();
                String pass = passRegister.getText().toString();
                String userFullname = username.getText().toString();

                email = email.trim();
                pass = pass.trim();
                userFullname = userFullname.trim();

                if(!validateForm()){
                    return;
                }
                else{
                    showDialog();

                    final String finalemail = email;
                    final String finalpassword = pass;
                    final String finaluserFullname = userFullname ;

                    mRef.createUser(email, pass, new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> result) {
                            mRef.authWithPassword(finalemail, finalpassword, new Firebase.AuthResultHandler() {
                                @Override
                                public void onAuthenticated(AuthData authData) {
                                    Map<String,Object> map = new HashMap<String, Object>();
                                    map.put(ReferenceUrl.KEY_PROVIDER,authData.getProvider());
                                    map.put(ReferenceUrl.KEY_FULL_NAME,finaluserFullname);
                                    map.put(ReferenceUrl.KEY_USER_EMAIL, (String) authData.getProviderData().get(ReferenceUrl.KEY_EMAIL));
                                    map.put(ReferenceUrl.CHILD_CONNECTION, ReferenceUrl.KEY_OFFLINE);

                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
                                    simpleDateFormat.setTimeZone(TimeZone.getDefault());
                                    String dateTime = simpleDateFormat.format(new Date());
                                    map.put(ReferenceUrl.KEY_TIMESTAMP, String.valueOf(dateTime));

                                    mRef.child(ReferenceUrl.CHILD_USERS).child(authData.getUid()).setValue(map);

                                    Toast.makeText(RegisterActivity.this, "Succesfully Registerd.", Toast.LENGTH_SHORT).show();
                                    // After storing, go to main activity
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    hideDialog();
                                    startActivity(intent);
                                }

                                @Override
                                public void onAuthenticationError(FirebaseError firebaseError) {
                                    // There is an error, and close the screen
                                    Toast.makeText(RegisterActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "onAuthenticationError:" + firebaseError);
                                    finish();
                                }
                            });
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            hideDialog();
                            showErrorMessageToUser(firebaseError.getMessage());
                            Log.d(TAG, "onError:" + firebaseError);
                        }
                    });
                }
            }
        });

    }

    private void showErrorMessageToUser(String errorMessage){
        // Create an AlertDialog to show error message
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(RegisterActivity.this);
        builder.setMessage(errorMessage)
                .setTitle("Error")
                .setPositiveButton(android.R.string.ok, null);
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
        hideDialog();
    }

    public boolean validateForm(){
        boolean valid = true;

        String name = username.getText().toString();
        if (TextUtils.isEmpty(name)){
            username.setError("Required.");
            valid = false;
        }
        else {
            username.setError(null);
        }

        String email = emailRegister.getText().toString();
        if (TextUtils.isEmpty(email)){
            emailRegister.setError("Required.");
            valid = false;
        }
        else {
            emailRegister.setError(null);
        }

        String password = passRegister.getText().toString();
        if (TextUtils.isEmpty(password)){
            passRegister.setError("Required");
            valid = false;
        }
        else {
            passRegister.setError(null);
        }

        return valid;
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
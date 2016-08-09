package com.kuister.kuister.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kuister.kuister.R;
import com.kuister.kuister.helper.ReferenceUrl;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailReset;
    private Button resetPass, back;
    private final static String TAG = "ResetPasswordActivity ";
    private Firebase mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        emailReset = (EditText) findViewById(R.id.emailReset);
        resetPass = (Button) findViewById(R.id.btnReset);
        back = (Button) findViewById(R.id.btnBack);

        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailReset.getText().toString().trim();

                if (!validateForm()){
                    return;
                }

                mRef.resetPassword(email, new Firebase.ResultHandler(){
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ResetPasswordActivity.this, "Email reset password confirmation send.", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(ResetPasswordActivity.this, "Reset Password Failed" + firebaseError, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public boolean validateForm(){
        boolean valid = true;

        String email = emailReset.getText().toString();
        if (TextUtils.isEmpty(email)){
            emailReset.setError("Required.");
            valid = false;
        }
        else {
            emailReset.setError(null);
        }

        return valid;
    }
}

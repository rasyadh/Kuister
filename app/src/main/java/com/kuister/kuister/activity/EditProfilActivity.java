package com.kuister.kuister.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kuister.kuister.R;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;

import java.util.HashMap;

public class EditProfilActivity extends AppCompatActivity {
    Firebase mRef;
    Firebase mUserRef;
    private Firebase.AuthStateListener mAuthStateListener;
    Firebase UserDataRef ;
    Firebase fullName;

    private String mCurrentUserUid;
    private EditText userName, userEmail;
    private ImageButton done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);
        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        mUserRef = mRef.child(ReferenceUrl.CHILD_USERS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfil);
        setSupportActionBar(toolbar);

        Spinner spinnerGender = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_gender,R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);

        Intent data = getIntent();
        mCurrentUserUid = data.getStringExtra("userUid");
        UserDataRef = mUserRef.child(mCurrentUserUid);

        userName = (EditText)findViewById(R.id.edit_nama);
        userEmail = (EditText)findViewById(R.id.edit_email);
        done = (ImageButton)findViewById(R.id.done);

        UserDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LoadData data = dataSnapshot.getValue(LoadData.class);
                String uFullname = data.getFullName();
                String uEmail = data.getUserEmail();
                userName.setText(uFullname);
                userEmail.setText(uEmail);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = UserDataRef.child(ReferenceUrl.KEY_FULL_NAME);
                fullName.setValue(userName.getText().toString());
                finish();
            }
        });
    }
}

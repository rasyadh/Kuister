package com.kuister.kuister.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kuister.kuister.R;
import com.kuister.kuister.adapter.AktifitasItemAdapter;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;
import com.kuister.kuister.model.ItemAktifitas;

public class ProfilActivity extends AppCompatActivity {
    private Firebase mRef;
    private Firebase mRefUser; //User now
    private Firebase.AuthStateListener mAuthStateListener; // session firebase
    Firebase myConnectionsStatusRef;
    Firebase UserDataRef;

    private RecyclerView recyclerAktifitas;
    private String mCurrentUserUid;

    TextView namauser;
    TextView poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarProfil);
        setSupportActionBar(toolbar);
        Intent data = getIntent();
        namauser = (TextView)findViewById(R.id.nama_user);
        poin = (TextView)findViewById(R.id.poin_user);
        recyclerAktifitas = (RecyclerView) findViewById(R.id.recyclerAktifitas);
        AktifitasItemAdapter aktifitasItemAdapter = new AktifitasItemAdapter(this, ItemAktifitas.getData());
        recyclerAktifitas.setAdapter(aktifitasItemAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAktifitas.setLayoutManager(linearLayoutManager);


        poin.setText(ItemAktifitas.getSkorTotal());

        mCurrentUserUid = data.getStringExtra("userUid");

        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        mRefUser = mRef.child(ReferenceUrl.CHILD_USERS);
        UserDataRef = mRefUser.child(mCurrentUserUid);
        UserDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LoadData data = dataSnapshot.getValue(LoadData.class);
                String uFullname = data.getFullName();
                namauser.setText(uFullname);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        FloatingActionButton floatbtn = (FloatingActionButton) findViewById(R.id.edit_profil_user);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilActivity.this,EditProfilActivity.class);
                intent.putExtra("userUid",mCurrentUserUid);
                startActivity(intent);
            }
        });
    }
}
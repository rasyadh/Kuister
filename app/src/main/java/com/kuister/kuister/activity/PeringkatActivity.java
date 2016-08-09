package com.kuister.kuister.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kuister.kuister.R;
import com.kuister.kuister.adapter.PeringkatAdapter;
import com.kuister.kuister.adapter.TemanAdapter;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;

import java.util.ArrayList;
import java.util.List;

public class PeringkatActivity extends AppCompatActivity {

    private Firebase mRef;
    private Firebase mRefUser; //User now
    private Firebase.AuthStateListener mAuthStateListener; // session firebase
    Firebase UserDataRef;
    private ChildEventListener mListenerUsers;

    private AuthData mAuthData;
    private PeringkatAdapter mPeringkatAdapter;
    private List<String> mUsersKeyList;

    private String mCurrentUserUid;
    private String mCurrentUserEmail;

    private RecyclerView PeringkatRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peringkat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPeringkat);
        setSupportActionBar(toolbar);
        PeringkatRecyclerView = (RecyclerView)findViewById(R.id.recyclerPeringkat);

        Intent data = getIntent();
        mCurrentUserUid = data.getStringExtra("userUid");
        mCurrentUserEmail = data.getStringExtra("userEmail");
        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        mRefUser = mRef.child(ReferenceUrl.CHILD_USERS);


        List<LoadData> emptyListFriend = new ArrayList<LoadData>();
        mPeringkatAdapter = new PeringkatAdapter(this,emptyListFriend);

        PeringkatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PeringkatRecyclerView.setHasFixedSize(true);
        PeringkatRecyclerView.setAdapter(mPeringkatAdapter);

        mUsersKeyList = new ArrayList<String>();

        queryTemanUser();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void queryTemanUser(){
        mListenerUsers = mRefUser.limitToFirst(50).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    String userUid = dataSnapshot.getKey();

                    if(!userUid.equals(mCurrentUserUid)){
                        LoadData Peringkat = dataSnapshot.getValue(LoadData.class);

                        Peringkat.setFriendUserUid(userUid);

                        Peringkat.setPlayerUserEmail(mCurrentUserEmail);
                        Peringkat.setPlayerUserUid(mCurrentUserUid);
                        mUsersKeyList.add(userUid);
                        mPeringkatAdapter.refill(Peringkat);
                    }
                    else{
                        LoadData Peringkat = dataSnapshot.getValue(LoadData.class);
                        String userName = Peringkat.getFullName();
                        String createdAt = Peringkat.getCreatedAt();
                        mPeringkatAdapter.setNameAndCreatedAt(userName,createdAt);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    String userUid = dataSnapshot.getKey();
                    if(!userUid.equals(mCurrentUserUid)){
                        LoadData Peringkat = dataSnapshot.getValue(LoadData.class);

                        Peringkat.setFriendUserUid(userUid);
                        Peringkat.setPlayerUserEmail(mCurrentUserEmail);
                        Peringkat.setPlayerUserUid(mCurrentUserUid);
                        int index = mUsersKeyList.indexOf(userUid);
                        mPeringkatAdapter.changeUser(index, Peringkat);
                    }
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}

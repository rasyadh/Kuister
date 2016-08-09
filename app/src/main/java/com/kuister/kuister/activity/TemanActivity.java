package com.kuister.kuister.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.kuister.kuister.R;
import com.kuister.kuister.adapter.TemanAdapter;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;

import java.util.ArrayList;
import java.util.List;

public class TemanActivity extends AppCompatActivity {
    private Firebase mRef;
    private Firebase mRefUser; //User now
    private Firebase.AuthStateListener mAuthStateListener; // session firebase
    Firebase UserDataRef;
    private ChildEventListener mListenerUsers;

    private AuthData mAuthData;
    private TemanAdapter mTemanUsersAdapter;
    private List<String> mUsersKeyList;

    private String mCurrentUserUid;
    private String mCurrentUserEmail;

    private RecyclerView temanUserRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teman);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTeman);
        setSupportActionBar(toolbar);
        temanUserRecyclerView = (RecyclerView)findViewById(R.id.recycleTeman);

        Intent data = getIntent();
        mCurrentUserUid = data.getStringExtra("userUid");
        mCurrentUserEmail = data.getStringExtra("userEmail");
        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        mRefUser = mRef.child(ReferenceUrl.CHILD_USERS);


        List<LoadData> emptyListFriend = new ArrayList<LoadData>();
        mTemanUsersAdapter = new TemanAdapter(this,emptyListFriend);

        temanUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        temanUserRecyclerView.setHasFixedSize(true);
        temanUserRecyclerView.setAdapter(mTemanUsersAdapter);

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
                        LoadData teman = dataSnapshot.getValue(LoadData.class);

                        teman.setFriendUserUid(userUid);

                        teman.setPlayerUserEmail(mCurrentUserEmail);
                        teman.setPlayerUserUid(mCurrentUserUid);
                        mUsersKeyList.add(userUid);
                        mTemanUsersAdapter.refill(teman);
                    }
                    else{
                        LoadData friend = dataSnapshot.getValue(LoadData.class);
                        String userName = friend.getFullName();
                        String createdAt = friend.getCreatedAt();
                        mTemanUsersAdapter.setNameAndCreatedAt(userName,createdAt);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()){
                    String userUid = dataSnapshot.getKey();
                    if(!userUid.equals(mCurrentUserUid)){
                        LoadData teman = dataSnapshot.getValue(LoadData.class);

                        teman.setFriendUserUid(userUid);
                        teman.setPlayerUserEmail(mCurrentUserEmail);
                        teman.setPlayerUserUid(mCurrentUserUid);
                        int index = mUsersKeyList.indexOf(userUid);
                        mTemanUsersAdapter.changeUser(index, teman);
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
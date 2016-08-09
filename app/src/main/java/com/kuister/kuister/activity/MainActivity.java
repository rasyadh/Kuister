package com.kuister.kuister.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.kuister.kuister.R;
import com.kuister.kuister.adapter.TabFragmentPagerAdapter;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    /* firebase url */
    private Firebase mRef;
    private Firebase mRefUser; //User now
    private Firebase.AuthStateListener mAuthStateListener; // session firebase
    Firebase myConnectionsStatusRef;
    Firebase UserDataRef;

    private View mProgressBarForUsers;
    private AuthData mAuthData;
    private String mCurrentUserUid;
    private String mCurrentUserEmail;
    private ChildEventListener mListenerUsers;
    private ValueEventListener mConnectedListener;
    private ValueEventListener mUserDataListener;

    private TextView name_user_drawer;
    private TextView email_user_drawer;
    private TextView koneksi;
    private static final String TAG = "MainActivity: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRef = new Firebase(ReferenceUrl.FIREBASE_URL);
        mRefUser = mRef.child(ReferenceUrl.CHILD_USERS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        pager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));
        tabs.setTabTextColors(getResources().getColor(android.R.color.darker_gray), getResources().getColor(android.R.color.white));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        tabs.setupWithViewPager(pager);

        mProgressBarForUsers = findViewById(R.id.progress_bar_users);
        //  koneksi = (TextView) findViewById(R.id.txvMenuItem);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        name_user_drawer = (TextView) header.findViewById(R.id.name_user_drawer);
        email_user_drawer = (TextView) header.findViewById(R.id.email_user_drawer);

        mAuthStateListener = new Firebase.AuthStateListener(){
            @Override
            public void onAuthStateChanged(AuthData authData) {
                setAuthenticatedUser(authData);
            }
        };

        mRef.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

            if (id == R.id.nav_profil) {
            Intent intent = new Intent(MainActivity.this,ProfilActivity.class);
            intent.putExtra("userUid",mCurrentUserUid);
            startActivity(intent);

        } else if (id == R.id.nav_kuis_sekarang) {



        } else if (id == R.id.nav_atur_Waktu_Kuis) {


        } else if (id == R.id.nav_teman) {
            Intent intent = new Intent(MainActivity.this,TemanActivity.class);
            intent.putExtra("userUid",mCurrentUserUid);
            intent.putExtra("userEmail",mCurrentUserEmail);
            startActivity(intent);

        } else if (id == R.id.nav_peringkat) {
            Intent intent = new Intent(MainActivity.this,PeringkatActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_pengaturan) {
            Intent intent = new Intent(MainActivity.this,PengaturanActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_bantuan) {
            Intent intent = new Intent(MainActivity.this,BantuanActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_info) {
            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
            startActivity(intent);

        }else if (id == R.id.nav_keluar) {
            logout();

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadDataUser(){

        myConnectionsStatusRef = mRefUser.child(mCurrentUserUid).child(ReferenceUrl.CHILD_CONNECTION);
        mConnectedListener = mRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if(connected){
                    myConnectionsStatusRef.setValue(ReferenceUrl.KEY_ONLINE);
                    myConnectionsStatusRef.onDisconnect().setValue(ReferenceUrl.KEY_OFFLINE);

                    //Toast.makeText(MainActivity.this, "Connected to Firebase", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onDataChange:" +connected);
                }
                else Log.d(TAG, "onDataChange:" +connected);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        UserDataRef = mRefUser.child(mCurrentUserUid);
        mUserDataListener = UserDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LoadData data = dataSnapshot.getValue(LoadData.class);
                String uFullname = data.getFullName();
                String uEmail = data.getUserEmail();
                String uConnection = data.getConnection();

                //koneksi.setText(uConnection);
                name_user_drawer.setText(uFullname);
                email_user_drawer.setText(uEmail);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void setAuthenticatedUser (AuthData authData){
        mAuthData = authData;
        if(authData != null){
            mCurrentUserUid = authData.getUid();
            mCurrentUserEmail = (String) authData.getProviderData().get(ReferenceUrl.KEY_EMAIL);
            //        mRefUser.child(mCurrentUserUid).child(ReferenceUrl.CHILD_CONNECTION).setValue(ReferenceUrl.KEY_ONLINE);

            loadDataUser();
        }
        else loadLoginView();
    }

    public void loadLoginView(){
        Intent intent= new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void logout(){
        if(this.mAuthData != null){
            // kondisi user offline
            myConnectionsStatusRef.setValue(ReferenceUrl.KEY_OFFLINE);
            // kondisi session hilang
            mRef.unauth();
            //update Authenticated user dan balik ke login
            setAuthenticatedUser(null);
        }
    }

    /*Progress Bar */

    private void showProgressBarForUser(){
        mProgressBarForUsers.setVisibility(View.VISIBLE);
    }
    private void hideProgressBarForUser(){
        if(mProgressBarForUsers.getVisibility() == View.VISIBLE){mProgressBarForUsers.setVisibility(View.GONE);}
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mRef.removeAuthStateListener(mAuthStateListener);
/*
       if(mListenerUsers!=null){
            mRefUser.removeEventListener(mListenerUsers);
        }*/
        if(mConnectedListener!=null){
            myConnectionsStatusRef.removeEventListener(mConnectedListener);
        }
        if(mUserDataListener!=null){
            UserDataRef.removeEventListener(mUserDataListener);
        }
    }
}
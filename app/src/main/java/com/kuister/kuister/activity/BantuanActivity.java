package com.kuister.kuister.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kuister.kuister.R;

public class BantuanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bantuan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarBantuan);
        setSupportActionBar(toolbar);
    }
}

package com.kuister.kuister.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.kuister.kuister.R;
import com.kuister.kuister.fragment.PengaturanFragment;

public class PengaturanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengaturan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPengaturan);
        setSupportActionBar(toolbar);

        getFragmentManager().beginTransaction().replace(R.id.konten_frame, new PengaturanFragment()).commit();
    }
}

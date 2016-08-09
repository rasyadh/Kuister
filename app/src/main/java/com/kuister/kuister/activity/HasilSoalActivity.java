package com.kuister.kuister.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.kuister.kuister.R;

public class HasilSoalActivity extends AppCompatActivity {

    private int soalBenar;
    private int soalSalah;
    private int kodeSoal;

    private TextView textBenar;
    private TextView textSalah;
    private TextView textSkor;
    private TextView judulSoal;
    private android.support.v7.widget.Toolbar toolbarHasilSoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_soal);

        Intent intent = getIntent();
        soalBenar = intent.getIntExtra("soal_benar",0);
        soalSalah = intent.getIntExtra("soal_salah",0);
        kodeSoal = intent.getIntExtra("kode_soal",0);

//        textMapel = (TextView)findViewById(R.id.toolbarHasilSoal);
        textBenar = (TextView)findViewById(R.id.textBenar);
        textSalah = (TextView)findViewById(R.id.textSalah);
        textSkor = (TextView)findViewById(R.id.textSkor);
        judulSoal = (TextView)findViewById(R.id.JudulSoal);
        toolbarHasilSoal = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarHasilSoal);
        setSupportActionBar(toolbarHasilSoal);

        if(kodeSoal == 1)
        {
            judulSoal.setText("Matematika IPA");
        } else if (kodeSoal == 2)
        {
            judulSoal.setText("Fisika");
        } else if (kodeSoal == 3)
        {
            judulSoal.setText("Kimia");
        } else if (kodeSoal == 4)
        {
            judulSoal.setText("Biologi");
        } else if (kodeSoal == 5)
        {
            judulSoal.setText("Matematika IPS");
        } else if (kodeSoal == 6)
        {
            judulSoal.setText("Geografi");
        } else if (kodeSoal == 7)
        {
            judulSoal.setText("Ekonomi");
        } else if (kodeSoal == 8)
        {
            judulSoal.setText("Sosiologi");
        } else if(kodeSoal == 9)
        {
            judulSoal.setText("Sejarah");
        }

        textBenar.setText("Jumlah Soal Benar = " + soalBenar);
        textSalah.setText("Jumlah Soal Salah = " + soalSalah);
        textSkor.setText("Total Skor = " + (soalBenar*10));
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(HasilSoalActivity.this, MainActivity.class));
        finish();
    }
}

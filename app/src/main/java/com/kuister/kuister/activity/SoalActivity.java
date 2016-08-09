package com.kuister.kuister.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.kuister.kuister.AppConfig;
import com.kuister.kuister.AppController;
import com.kuister.kuister.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class SoalActivity extends Activity implements View.OnClickListener{

    private static final String TAG = SoalActivity.class
            .getSimpleName();
    private Button btnSoal1;
    private Button btnSoal2;
    private Button btnSoal3;
    private Button btnSoal4;
    private Button btnSoal5;
    private Button btnSoal6;
    private Button btnSoal7;
    private Button btnSoal8;
    private Button btnSoal9;
    private Button btnSoal10;
    private ImageView btnSubmit;
    private RadioGroup rg;
    private RadioButton radioButton;
    private RadioButton jawabanA;
    private RadioButton jawabanB;
    private RadioButton jawabanC;
    private RadioButton jawabanD;
    private RadioButton jawabanE;
    private RadioButton jawabanSKIP;
    private int idSoal = 0;
    private int kodeSoal = 0;
    private int soalTerjawab = 0;
    private android.support.v7.widget.Toolbar toolbarSoal;

    private String jawaban;

    private String url;

    private HashMap<Integer, String> kunciJawaban;
    private HashMap<Integer, String> jawabanUser;

    private int noSoal = 0;

    private NetworkImageView imgNetWorkView;
    private ImageView imageView;

    private TextView sisaWaktu;
    private final long startTime = 120000;
    private final long interval = 1000;
    private long timeElapsed;
    private CountDown countDown;
    private int minute, second = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);

        sisaWaktu = (TextView) findViewById(R.id.sisaWaktu);
        countDown = new CountDown(startTime, interval);
        countDown.start();

        Intent intent = getIntent();
        kodeSoal = intent.getIntExtra("posisi",0) + 1;


        btnSoal1 = (Button) findViewById(R.id.soal1);
        btnSoal2 = (Button) findViewById(R.id.soal2);
        btnSoal3 = (Button) findViewById(R.id.soal3);
        btnSoal4 = (Button) findViewById(R.id.soal4);
        btnSoal5 = (Button) findViewById(R.id.soal5);
        btnSoal6 = (Button) findViewById(R.id.soal6);
        btnSoal7 = (Button) findViewById(R.id.soal7);
        btnSoal8 = (Button) findViewById(R.id.soal8);
        btnSoal9 = (Button) findViewById(R.id.soal9);
        btnSoal10 = (Button) findViewById(R.id.soal10);
        btnSubmit = (ImageView) findViewById(R.id.finish);
        toolbarSoal = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarSoal);

        gantiNamaTololbar(kodeSoal);

        //imgNetWorkView = (NetworkImageView) findViewById(R.id.imgNetwork);
        imageView = (ImageView) findViewById(R.id.imgSoal);

        rg = (RadioGroup)findViewById(R.id.jawabanUser);
        jawabanA = (RadioButton) findViewById(R.id.JawabanA);
        jawabanB = (RadioButton) findViewById(R.id.JawabanB);
        jawabanC = (RadioButton) findViewById(R.id.JawabanC);
        jawabanD = (RadioButton) findViewById(R.id.JawabanD);
        jawabanE = (RadioButton) findViewById(R.id.JawabanE);
        jawabanSKIP = (RadioButton) findViewById(R.id.JawabanSkip);

        kunciJawaban = new HashMap<Integer, String>();
        jawabanUser = new HashMap<Integer, String>();

        btnSoal1.setOnClickListener(this);
        btnSoal2.setOnClickListener(this);
        btnSoal3.setOnClickListener(this);
        btnSoal4.setOnClickListener(this);
        btnSoal5.setOnClickListener(this);
        btnSoal6.setOnClickListener(this);
        btnSoal7.setOnClickListener(this);
        btnSoal8.setOnClickListener(this);
        btnSoal9.setOnClickListener(this);
        btnSoal10.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);


        idSoal = 1;
        getURL(idSoal);
        getJawabanUser(noSoal);
        noSoal = 1;
        checkJawabanTerakhir(noSoal);

    }

    private void gantiNamaTololbar(int kodeSoal) {
        if(kodeSoal == 1)
        {
//            textMapel.setText("Matematika IPA");
            toolbarSoal.setTitle("Matematika IPA");
        } else if (kodeSoal == 2)
        {
//            textMapel.setText("Fisika");
            toolbarSoal.setTitle("Fisika");
        } else if (kodeSoal == 3)
        {
//            textMapel.setText("Kimia");
            toolbarSoal.setTitle("Kimia");
        } else if (kodeSoal == 4)
        {
//            textMapel.setText("Biologi");
            toolbarSoal.setTitle("Biologi");
        } else if (kodeSoal == 5)
        {
//            textMapel.setText("Matematika IPS");
            toolbarSoal.setTitle("Matematika IPS");
        } else if (kodeSoal == 6)
        {
//            textMapel.setText("Geografi");
            toolbarSoal.setTitle("Geografi");
        } else if (kodeSoal == 7)
        {
//            textMapel.setText("Ekonomi");
            toolbarSoal.setTitle("Ekonomi");
        } else if (kodeSoal == 8)
        {
//            textMapel.setText("Sosiologi");
            toolbarSoal.setTitle("Sosiologi");
        } else if(kodeSoal == 9)
        {
//            textMapel.setText("Sejarah");
            toolbarSoal.setTitle("Sejarah");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.soal1:
                idSoal = 1;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 1;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal2:
                idSoal = 2;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 2;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal3:
                idSoal = 3;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 3;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal4:
                idSoal = 4;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 4;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal5:
                idSoal = 5;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 5;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal6:
                idSoal = 6;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 6;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal7:
                idSoal = 7;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 7;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal8:
                idSoal = 8;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 8;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal9:
                idSoal = 9;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 9;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.soal10:
                idSoal = 10;
                getURL(idSoal);
                getJawabanUser(noSoal);
                noSoal = 10;
                checkJawabanTerakhir(noSoal);
                break;
            case R.id.finish:
                Submit();
                break;
            default:
                break;
        }
    }

    private void Submit() {
        getJawabanUser(noSoal);

        String jwbUser;
        for(int i = 1; i <= 10; i++ )
        {
            jwbUser = jawabanUser.get(i);
            if(jwbUser != null)
            {
                soalTerjawab++;
            }
            Log.d("cekjawaban","soal ke " + i + ", jawaban user = " + jwbUser);
            Log.d("soalterjawab",""+soalTerjawab);
        }

        if(soalTerjawab < 10)
        {
            Toast.makeText(this, "ada soal yang belum terjawab", Toast.LENGTH_SHORT).show();
            soalTerjawab = 0;
        }else
        {
            hitungNilai();
        }
    }

    private void hitungNilai() {
        int i;
        int soalBenar = 0;
        int soalSalah = 0;

        for (i = 1; i <= 10;i++)
        {

            String jwbUser = jawabanUser.get(i);
            String kunciJwbn = kunciJawaban.get(i);
            Log.d("koreksi","soal ke " + i + ", jawaban user = " + jwbUser + ", kunci jawaban = " + kunciJwbn );
            if(jwbUser!=null&& kunciJwbn !=null)
            {
                if(!jwbUser.equals("SKIP"))
                {
                    if(jwbUser.equals(kunciJwbn)){
                        soalBenar++;
                    }
                    else
                    {
                        soalSalah++;
                    }
                }
            }
        }
        countDown.cancel();

        Intent intentMp = new Intent(this, HasilSoalActivity.class);
        intentMp.putExtra("soal_benar",soalBenar);
        intentMp.putExtra("soal_salah",soalSalah);
        intentMp.putExtra("kode_soal",kodeSoal);
        startActivity(intentMp);
    }

    private void getURL(final int idSoal) {
        String tag_string_req = "req_Soal";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_SOAL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (!error) {
                        jawaban = jObj.getString("jawaban");
                        url = jObj.getString("soal");

                        makeImageRequest(jawaban, noSoal);
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", ""+idSoal);
                params.put("kodeSoal", ""+kodeSoal);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void checkJawabanTerakhir(int noSoal) {
        String jwbUser = jawabanUser.get(noSoal);
//        Toast.makeText(this,
//                jwbUser, Toast.LENGTH_SHORT).show();
        if(jwbUser != null)
        {
            if(jwbUser.equals("A")) {
                jawabanA.setChecked(true);
            } else if(jwbUser.equals("B")){
                jawabanB.setChecked(true);
            } else if(jwbUser.equals("C")){
                jawabanC.setChecked(true);
            } else if(jwbUser.equals("D")){
                jawabanD.setChecked(true);
            } else if(jwbUser.equals("E")){
                jawabanE.setChecked(true);
            } else if(jwbUser.equals("SKIP")){
                jawabanSKIP.setChecked(true);
            }
        }
    }

    private void getJawabanUser(int noSoal)
    {
        // get selected radio button from radioGroup
        int selectedId = rg.getCheckedRadioButtonId();

        if(selectedId>= 0)
        {
            // find the radiobutton by returned id
            radioButton = (RadioButton) findViewById(selectedId);

            jawabanUser.put(noSoal,String.valueOf(radioButton.getText()));
            setWarnaNomorSoal(noSoal);
            rg.clearCheck();
//            Toast.makeText(this,
//                    "soal :" + noSoal + "jawaban: " + String.valueOf(radioButton.getText()), Toast.LENGTH_SHORT).show();
        }

    }

    private void setWarnaNomorSoal(int noSoal) {
        switch (noSoal)
        {
            case 1:
                btnSoal1.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal1.setTextColor(Color.WHITE);
                break;
            case 2:
                btnSoal2.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal2.setTextColor(Color.WHITE);
                break;
            case 3:
                btnSoal3.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal3.setTextColor(Color.WHITE);
                break;
            case 4:
                btnSoal4.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal4.setTextColor(Color.WHITE);
                break;
            case 5:
                btnSoal5.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal5.setTextColor(Color.WHITE);
                break;
            case 6:
                btnSoal6.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal6.setTextColor(Color.WHITE);
                break;
            case 7:
                btnSoal7.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal7.setTextColor(Color.WHITE);
                break;
            case 8:
                btnSoal8.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal8.setTextColor(Color.WHITE);
                break;
            case 9:
                btnSoal9.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal9.setTextColor(Color.WHITE);
                break;
            case 10:
                btnSoal10.setBackgroundColor(Color.parseColor("#EE4571"));
                btnSoal10.setTextColor(Color.WHITE);
                break;
        }
    }


    private void makeImageRequest(String jawaban, int noSoal) {
        kunciJawaban.put(noSoal,jawaban);


        ImageLoader imageLoader = AppController.getInstance().getImageLoader();

        // Loading image with placeholder and error image
        imageLoader.get(AppConfig.IP_SERVER+url, ImageLoader.getImageListener(
                imageView, 400, 400));

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(AppConfig.IP_SERVER+url);
        if(entry != null){
            try {
                String data = new String(entry.data, "UTF-8");
                // handle data, like converting it to xml, json, bitmap etc.,
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else{
            // cached response doesn't exists. Make a network call here
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        Reset();
    }

    private void Reset() {
        idSoal = 0;
        kodeSoal = 0;
        soalTerjawab = 0;
        noSoal = 0;
        kunciJawaban = new HashMap<Integer, String>();
        jawabanUser = new HashMap<Integer, String>();
    }

    public class CountDown extends CountDownTimer {

        public CountDown (long startTime, long interval){super(startTime,interval);}

        @Override
        public void onTick(long millisUntilFinished) {
            int menit;
            int detik;
            long cd;

            cd = millisUntilFinished;

            menit = (int) (millisUntilFinished/(interval * 60));
            detik = (int) ((millisUntilFinished/interval)%60);

            sisaWaktu.setText( menit + " : " + detik);
            timeElapsed = startTime - millisUntilFinished;

            if ((millisUntilFinished / interval) == 1)
                sisaWaktu.setText("0");

        }

        @Override
        public void onFinish() {
            Toast.makeText(SoalActivity.this, "Waktu Mengerjakan Habis", Toast.LENGTH_SHORT).show();
            hitungNilai();
        }
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Yakin ingin keluar ?\nSemua progres pengerjaan akan di hilang !");

        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                countDown.cancel();
                SoalActivity.super.onBackPressed();
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
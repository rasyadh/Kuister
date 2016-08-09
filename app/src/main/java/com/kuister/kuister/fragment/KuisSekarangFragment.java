package com.kuister.kuister.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.kuister.kuister.R;
import com.kuister.kuister.activity.SoalActivity;
import com.kuister.kuister.adapter.ItemKuisAdapter;
import com.kuister.kuister.helper.RecycleItemClickListener;
import com.kuister.kuister.model.ItemKuis;

public class KuisSekarangFragment extends Fragment {

    public KuisSekarangFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_kuis_sekarang, container, false);

        final Spinner spinnerTingkatPendidikan = (Spinner) rootView.findViewById(R.id.spinner_tingkat_pendidikan);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_tingkat_pendidikan, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerTingkatPendidikan.setAdapter(adapter);

        spinnerTingkatPendidikan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String data = parent.getItemAtPosition(position).toString();
                setUpRecycler(rootView, data);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return rootView;
    }

    public void setUpRecycler(View rootView, String data) {
        RecyclerView recycle = (RecyclerView) rootView.findViewById(R.id.recycle_kuis_sekarang);
        ItemKuisAdapter itemAdapter = new ItemKuisAdapter(getActivity(), ItemKuis.getData(data));
        recycle.setAdapter(itemAdapter);

        LinearLayoutManager linearLM = new LinearLayoutManager(getActivity());
        linearLM.setOrientation(LinearLayoutManager.VERTICAL);
        recycle.setLayoutManager(linearLM);
        recycle.setItemAnimator(new DefaultItemAnimator());

        recycle.addOnItemTouchListener(new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                alertDialog(position);
            }
        }));
    }

    public void alertDialog(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage("Yakin ingin mengerjakan ?");

        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intentMp = new Intent(getActivity(), SoalActivity.class);
                intentMp.putExtra("posisi", position);
                startActivity(intentMp);
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

    /*recycle.addOnItemTouchListener(new RecycleItemClickListener(getActivity(), new RecycleItemClickListener.OnItemClickListener(){
        @Override
        public void onItemClick(View view, int position){
            switch(position){
                case 0:
                    Intent intentMp = new Intent(getActivity(), ProfilActivity.class);
                    startActivity(intentMp);
                    break;
                case 1:
                    break;
                default:
                    break;
            }
        }
    })); */
}


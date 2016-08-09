package com.kuister.kuister.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuister.kuister.R;
import com.kuister.kuister.model.ItemAktifitas;

import java.util.List;

/**
 * Created by Rasyadh A Aziz on 14/07/2016.
 */
public class AktifitasItemAdapter extends RecyclerView.Adapter<AktifitasItemAdapter.AktifitasItemViewHolder> {

    private List<ItemAktifitas> mData;
    private LayoutInflater inflater;
    private String TAG;
    private Context context;

    public AktifitasItemAdapter(Context context, List<ItemAktifitas> data){
        this.mData = data;
        this.inflater = LayoutInflater.from(context);
    }

    class AktifitasItemViewHolder extends RecyclerView.ViewHolder {
        TextView tingkatPendidikan, mataPelajaran, skor, waktu;
        int position;
        ItemAktifitas current;
        public View view;

        public AktifitasItemViewHolder(View itemView) {
            super(itemView);
            tingkatPendidikan = (TextView) itemView.findViewById(R.id.aktifitas_tingkat_pendidikan);
            mataPelajaran = (TextView) itemView.findViewById(R.id.aktifitas_mata_pelajaran);
            skor = (TextView) itemView.findViewById(R.id.aktifitas_poin_mp);
            waktu = (TextView) itemView.findViewById(R.id.aktifitas_waktu);
        }

        public void setData(ItemAktifitas currentObj, int position) {
            this.current = currentObj;
            this.tingkatPendidikan.setText(current.getTingkatPendidikan());
            this.mataPelajaran.setText(current.getMataPelajaran());
            this.skor.setText(current.getSkor());
            this.waktu.setText(current.getWaktu());
            this.position = position;
        }
    }

    @Override
    public AktifitasItemAdapter.AktifitasItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        View view = inflater.inflate(R.layout.item_aktifitas, parent, false);
        AktifitasItemViewHolder holder = new AktifitasItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AktifitasItemAdapter.AktifitasItemViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder");
        ItemAktifitas currentObj = mData.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

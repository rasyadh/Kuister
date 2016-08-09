package com.kuister.kuister.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuister.kuister.R;
import com.kuister.kuister.model.ItemKuis;

import java.util.List;

/**
 * Created by Rasyadh A Aziz on 13/07/2016.
 */
public class ItemKuisAdapter extends RecyclerView.Adapter<ItemKuisAdapter.ItemKuisViewHolder> {
    private List<ItemKuis> mData;
    private LayoutInflater inflater;
    private String TAG;
    private Context context;

    public ItemKuisAdapter(Context context, List<ItemKuis> data){
        this.mData = data;
        this.inflater = LayoutInflater.from(context);
    }

    class ItemKuisViewHolder extends RecyclerView.ViewHolder {
        TextView itemMP;
        int position;
        ItemKuis current;
        public View view;

        public ItemKuisViewHolder(View itemView) {
            super(itemView);
            itemMP = (TextView) itemView.findViewById(R.id.MPitem);
        }

        public void setData(ItemKuis currentObj, int position) {
            this.current = currentObj;
            this.itemMP.setText(current.getItem());
            this.position = position;
        }
    }

    @Override
    public ItemKuisViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i(TAG, "onCreateViewHolder");
        View view = inflater.inflate(R.layout.item_kuis_sekarang, parent, false);
        ItemKuisViewHolder holder = new ItemKuisViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemKuisViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder" + position);

        ItemKuis currentObj = mData.get(position);
        holder.setData(currentObj, position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}

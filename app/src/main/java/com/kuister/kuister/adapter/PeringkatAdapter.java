package com.kuister.kuister.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kuister.kuister.R;
import com.kuister.kuister.helper.LoadData;
import com.kuister.kuister.helper.ReferenceUrl;

import java.util.List;

/**
 * Created by Rasyadh A Aziz on 13/07/2016.
 */
public class PeringkatAdapter extends RecyclerView.Adapter<PeringkatAdapter.ViewHolderUsers> {

    private List<LoadData> mPeringkatUsers;
    private Context mContext;
    private String fUserName;
    private String fUserCreatedAt;

    public PeringkatAdapter(Context context, List<LoadData> peringkatUser){
        mContext = context;
        mPeringkatUsers = peringkatUser ;

    }

    @Override
    public ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout for each row
        return new ViewHolderUsers(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peringkat, parent, false));
    }



    @Override
    public void onBindViewHolder(ViewHolderUsers holder, int position) {
        LoadData peringkat = mPeringkatUsers.get(position);
        holder.getUsersFirstName().setText(peringkat.getFullName());
    }

    @Override
    public int getItemCount() {
        return mPeringkatUsers.size();
    }

    public void refill(LoadData users){
        mPeringkatUsers.add(users);
        notifyDataSetChanged();

    }
    public void setNameAndCreatedAt(String userName, String userCreatedAt){
        fUserName = userName;
        fUserCreatedAt=userCreatedAt;
    }
    public void changeUser(int index, LoadData user){
        mPeringkatUsers.set(index,user);
        notifyDataSetChanged();

    }

    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView currentUsersFirstName;
        private Context mContextViewHolder;

        public ViewHolderUsers(Context context,View itemView) {
            super(itemView);
            currentUsersFirstName=(TextView)itemView.findViewById(R.id.userFirstNameProfile);
            mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

        public TextView getUsersFirstName(){return currentUsersFirstName;}

        @Override
        public void onClick(View v) {
            // Handle click on each row

            int position=getLayoutPosition(); // Get row position

            // LoadData teman = mTemanUsers.get(position);

            //   teman.setPlayerFullname(currentUsersFirstName);
            // teman.setPlayerCreatedAt(currentStatusConnection);

        }
    }
}


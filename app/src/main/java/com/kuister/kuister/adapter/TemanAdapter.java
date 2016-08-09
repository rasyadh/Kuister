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
public class TemanAdapter extends RecyclerView.Adapter<TemanAdapter.ViewHolderUsers> {

    private List<LoadData> mTemanUsers;
    private Context mContext;
    private String fUserName;
    private String fUserCreatedAt;

    public TemanAdapter(Context context, List<LoadData> temanUser){
        mContext = context;
        mTemanUsers = temanUser ;

    }

    @Override
    public ViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate layout for each row
        return new ViewHolderUsers(mContext, LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friends, parent, false));
    }



    @Override
    public void onBindViewHolder(ViewHolderUsers holder, int position) {
        LoadData teman = mTemanUsers.get(position);
        holder.getUsersFirstName().setText(teman.getFullName());
        holder.getStatusConnection().setText(teman.getConnection());


        if(teman.getConnection().equals(ReferenceUrl.KEY_ONLINE)) {
            // Green color
            holder.getStatusConnection().setTextColor(Color.parseColor("#00FF00"));
        }else {
            // Red color
            holder.getStatusConnection().setTextColor(Color.parseColor("#FF0000"));
        }

    }

    @Override
    public int getItemCount() {
        return mTemanUsers.size();
    }

    public void refill(LoadData users){
        mTemanUsers.add(users);
        notifyDataSetChanged();
    }

    public void setNameAndCreatedAt(String userName, String userCreatedAt){
        fUserName = userName;
        fUserCreatedAt=userCreatedAt;
    }
    public void changeUser(int index, LoadData user){
        mTemanUsers.set(index,user);
        notifyDataSetChanged();

    }

    public class ViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView currentUsersFirstName;
        private TextView currentStatusConnection;
        private Context mContextViewHolder;

        public ViewHolderUsers(Context context,View itemView) {
            super(itemView);
            currentUsersFirstName=(TextView)itemView.findViewById(R.id.userFirstNameProfile);
            currentStatusConnection=(TextView)itemView.findViewById(R.id.connectionStatus);
            mContextViewHolder = context;

            itemView.setOnClickListener(this);
        }

        public TextView getUsersFirstName(){return currentUsersFirstName;}
        public TextView getStatusConnection(){return currentStatusConnection;}

        @Override
        public void onClick(View v) {
            // Handle click on each row

            int position=getLayoutPosition(); // Get row position

        }
    }
}


package com.example.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {
    private Context mContext;
    private List<A_DATA> datas;
    private String mNum;

    public mAdapter(Context mContext,List<A_DATA> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView texttel;
        public TextView textname;
        public ImageView imageView;


        public ViewHolder(View view) {
            super(view);

            texttel = (TextView)view.findViewById(R.id.textTel);
            textname = (TextView)view.findViewById(R.id.textName);
            imageView = (ImageView)view.findViewById(R.id.contactImage);
        }
    }

    @Override
    public mAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        A_DATA data = datas.get(position);
        holder.texttel.setText(data.getTel());
        holder.textname.setText(data.getName());

        if (data.getImage()!=null)
            holder.imageView.setImageURI(Uri.parse(data.getImage()));
        else
            holder.imageView.setImageResource(R.drawable.people);

        holder.texttel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNum = datas.get(position).getTel();
                String tel = "tel:" + mNum;
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(tel));
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {return datas.size();}
}

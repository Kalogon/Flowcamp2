package com.example.loginactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class mAdapter extends RecyclerView.Adapter<mAdapter.ViewHolder> {
    private Context mContext;
    private List<A_DATA> datas;
    private String mNum;
    JSONObject user;
    String userid="";

    public mAdapter(Context mContext,List<A_DATA> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    public void delcontact (String name_edt, String number_edt,String temp) throws JSONException {
        JSONObject delcontact=new JSONObject();
        delcontact.accumulate("user_id",temp);
        delcontact.accumulate("friend_name",name_edt);
        delcontact.accumulate("friend_number",number_edt);
        new JSONTaskdel(delcontact).execute("http://192.249.19.254:7180/delcontact",  name_edt, number_edt);
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
        user=SubActivity.user;
        try {
            userid = user.getString("userid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = datas.get(position).getName();
                final String number = datas.get(position).getTel();


//                mNum = datas.get(position).getTel();
//                String tel = "tel:" + mNum;
//                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(tel));
//                mContext.startActivity(intent);

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                try {
                                    delcontact(name, number,userid);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                //Yes 버튼을 클릭했을때 처리
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No 버튼을 클릭했을때 처리
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage("Are you sure to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();



            }
        });

    }

    @Override
    public int getItemCount() {return datas.size();}
}

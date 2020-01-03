package com.example.loginactivity;


import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {

    private View v;


    public tab1() {
        // Required empty public constructor
    }

    private void start() {
        RecyclerView rv = v.findViewById(R.id.contact);

        List<A_DATA> datas = new ArrayList<>();

        ContentResolver resolver = getContext().getContentResolver();

        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        String [] proj = {ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI};


        Cursor cursor = resolver.query(phoneUri, proj, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(proj[0]));
                String name = cursor.getString(cursor.getColumnIndex(proj[1]));
                String tel = cursor.getString(cursor.getColumnIndex(proj[2]));
                String image = cursor.getString(cursor.getColumnIndex(proj[3]));

                A_DATA data = new A_DATA(id, name, tel, image);
                datas.add(data);
            }
        }
        cursor.close();

        mAdapter my_adapter = new mAdapter(getContext(), datas);

        rv.setAdapter(my_adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.fragment_tab1, container, false);

        ArrayList<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS))
                Toast.makeText(getContext(), "We need your permission for reading your contacts.", Toast.LENGTH_SHORT).show();
            permissions.add(Manifest.permission.READ_CONTACTS);
        }
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CALL_PHONE))
                Toast.makeText(getContext(), "We need your permission for calling.", Toast.LENGTH_SHORT).show();
            permissions.add(Manifest.permission.CALL_PHONE);
        }
        if (permissions.size() > 0) {
            String[] per_array = new String[permissions.size()];
            per_array = permissions.toArray(per_array);
            requestPermissions(per_array, 1);
        }
        else
            start();
        return v;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                start();
            }else if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                start();
            }else
                getActivity().finish();
        }
    }

}

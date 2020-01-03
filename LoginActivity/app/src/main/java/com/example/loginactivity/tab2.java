package com.example.loginactivity;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {

    private static final int PICK_FROM_ALBUM = 1;
    private static final int SHOW_FULL_IMAGE = 2;
    private boolean is_delete;
    private ArrayList<Bitmap> im_array;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private ImageAdapter imageAdapter;

    public tab2() {
        sf = SubActivity.sharedPreferences;
        editor = sf.edit();

        int image_number = sf.getInt("size", 0);

        im_array = SubActivity.im_array;
        for (int i = 0; i < image_number; i++) {
            String image = sf.getString(String.valueOf(i), "");
            im_array.add(StringToBitmap(image));
        }
        is_delete = false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab2, container, false);
        GridView gv = v.findViewById(R.id.grid_view);
        final Button delete = v.findViewById(R.id.d_button);
        Button gallery = v.findViewById(R.id.g_button);
        Button saving = v.findViewById(R.id.s_button);

        imageAdapter = new ImageAdapter(getActivity(), im_array);
        gv.setAdapter(imageAdapter);

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                final int index = position;
                if (is_delete) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setTitle("IMAGE DELETE")
                            .setMessage("Do you really want to delete the image?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    im_array.remove(index);

                                    imageAdapter.notifyDataSetChanged();

                                    delete.setClickable(true);
                                    delete.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), "Delete image successly", Toast.LENGTH_LONG).show();
                                    is_delete = false;
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delete.setClickable(true);
                                    delete.setVisibility(View.VISIBLE);
                                    is_delete = false;
                                }
                            }).show();
                }else {
                    Intent i = new Intent(getContext(), make_full.class);
                    i.putExtra("id", position);
                    startActivityForResult(i, SHOW_FULL_IMAGE);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click the image what you want to delete", Toast.LENGTH_SHORT).show();
                is_delete = true;
                delete.setClickable(false);
                delete.setVisibility(View.INVISIBLE);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE))
                        Toast.makeText(getContext(), "We need your permission for reading your gallery.", Toast.LENGTH_SHORT).show();
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                } else
                    go_album();
            }
        });

        saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "We are now saving.", Toast.LENGTH_SHORT).show();
                editor.clear();
                for (int i = 0; i < im_array.size(); i++) {
                    editor.putString(String.valueOf(i), BitmapToString(im_array.get(i)));
                }
                editor.putInt("size", im_array.size());
                editor.commit();
                Toast.makeText(getContext(), "saving finished.", Toast.LENGTH_SHORT).show();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                go_album();
            }else
                getActivity().finish();
        }
    }

    private void go_album() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(getContext(), "ALBUM CANCEL" ,Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            ClipData clipData = data.getClipData();

            if (clipData != null) {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), clipData.getItemAt(i).getUri());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Cursor cursor = getContext().getContentResolver().query(clipData.getItemAt(i).getUri(), null, null, null, null);
                    cursor.moveToNext();
                    String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                    cursor.close();
                    ExifInterface exifInterface = null;
                    try {
                        exifInterface = new ExifInterface(imagePath);
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                    int a = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int degree = 0;
                    if (a == ExifInterface.ORIENTATION_ROTATE_90)
                        degree = 90;
                    else if (a == ExifInterface.ORIENTATION_ROTATE_180)
                        degree = 180;
                    else if (a == ExifInterface.ORIENTATION_ROTATE_270)
                        degree = 270;

                    Matrix matrix = new Matrix();
                    matrix.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

                    Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    if (bitmap != tmpBitmap) {
                        bitmap.recycle();
                        bitmap = tmpBitmap;
                    }

                    im_array.add(bitmap);
                }
            }
            else if (photoUri != null){
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Cursor cursor = getContext().getContentResolver().query(photoUri, null, null, null, null);
                cursor.moveToNext();
                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();
                ExifInterface exifInterface = null;
                try {
                    exifInterface = new ExifInterface(imagePath);
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }catch(IOException e) {
                    e.printStackTrace();
                }
                int a = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                int degree = 0;
                if (a == ExifInterface.ORIENTATION_ROTATE_90)
                    degree = 90;
                else if (a == ExifInterface.ORIENTATION_ROTATE_180)
                    degree = 180;
                else if (a == ExifInterface.ORIENTATION_ROTATE_270)
                    degree = 270;

                Matrix matrix = new Matrix();
                matrix.setRotate(degree, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);

                Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                if (bitmap != tmpBitmap) {
                    bitmap.recycle();
                    bitmap = tmpBitmap;
                }

                im_array.add(bitmap);
            }
            imageAdapter.notifyDataSetChanged();
        }
    }

    private Bitmap StringToBitmap(String string) {
        byte [] bytes = Base64.decode(string, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    private String BitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayOutputStream);
        byte [] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }
}

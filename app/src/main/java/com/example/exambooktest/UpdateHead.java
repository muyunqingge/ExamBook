package com.example.exambooktest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateHead extends AppCompatActivity implements View.OnClickListener {

    public static final int CHOOSE_PHOTO = 2;
    private Button btn_head;
    private static final String TAG = "UpdateHead";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_head);

        btn_head = findViewById(R.id.btn_head);
        btn_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_head:
                if (ContextCompat.checkSelfPermission(UpdateHead.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UpdateHead.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                } else {
                    openAlbum();
                }
                break;
        }
    }

    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);        //????????????
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
             } else {
                Toast.makeText(this,"?????????????????????",Toast.LENGTH_SHORT).show();
            }
            break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    //???????????????????????????
                    if (Build.VERSION.SDK_INT >= 19){
                        //4.4?????????????????????????????????????????????
                        handleImageOnKitKat(data);
                    } else {
                        //4.4?????????????????????????????????????????????
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
        }
    }

    private void handleImageBeforeKitKat(Intent data) {

        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);

        //???????????? ??????path??????
        conservePath(imagePath);

    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){

            //?????????document?????????Uri????????????document id ??????
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];    //????????????????????????id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        } else if("content".equalsIgnoreCase(uri.getScheme())){
            //?????????content?????????Uri??????????????????????????????
            imagePath = getImagePath(uri,null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }

        //???????????? ??????path??????
        conservePath(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //??????Uri???selection ??????????????????????????????
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void conservePath(String imagePath) {

        Uri imageUri;
        
        SharedPreferences myShare = getSharedPreferences("user",MODE_PRIVATE);
        String userId = myShare.getString("studentId","");

        
//
//        Bitmap bitmap = null;
//
//        try {
//            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(imagePath));
//            bitmap = BitmapFactory.decodeStream(bis);
//            saveToSystemGallery(bitmap);
//            bis.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this,"???????????????",Toast.LENGTH_SHORT).show();
//        }
        File outputImage = new File(imagePath);
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e){
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(UpdateHead.this,"com.example.exambooktest.fileprovider",outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }

        SharedPreferences imageShare = getSharedPreferences("imagePath",MODE_PRIVATE);
        SharedPreferences.Editor editor = imageShare.edit();
        editor.putString("imagePath" + userId,imageUri.toString());

        Log.d(TAG, "conservePath: "  + imageUri.toString());
        editor.commit();

        Toast.makeText(this,"??????????????????",Toast.LENGTH_SHORT).show();

    }

    public void saveToSystemGallery(Bitmap bmp) {
        // ??????????????????
        File appDir = new File(Environment.getExternalStorageDirectory(), "MyAlbums");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ????????????????????????????????????
        try {
            MediaStore.Images.Media.insertImage(getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        sendBroadcast(intent); // ?????????????????????????????????
    }

}
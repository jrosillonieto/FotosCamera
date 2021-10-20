package com.example.fotoscamera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_foto = (Button) findViewById(R.id.btn_foto);
        
        btn_foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

    }



    public void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
            // display error state to the user

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final ImageView imageView = findViewById(R.id.imageView);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            
            File path = getApplicationContext().getFilesDir();
            File foto = new File(path, "foto.jpg");

            try {
                FileOutputStream os = new FileOutputStream(foto);
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
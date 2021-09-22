package com.example.intent_acitivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
public class MainActivity2 extends AppCompatActivity {
    Button btn1;
    Button pickbtnn;
    ImageView imgg;
    public static final int RESULT_IMAGE=1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn1 = (Button) findViewById(R.id.btn2);
        pickbtnn = (Button) findViewById(R.id.pickbtn);
        imgg = (ImageView) findViewById(R.id.img);
        askPermission();
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(i);
            }
        });
        pickbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery , RESULT_IMAGE);
    }
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            final Uri imguri =data.getData();//to take data as uri
            try {
                //to show image in image view uri have to convert into bitmap
                // so first convert it into inputStream and then it convert into bitmap;
                final InputStream imgstream = getContentResolver().openInputStream(imguri);//this will convert in

                final Bitmap selectImage = BitmapFactory.decodeStream(imgstream);//this will convert in Bitmap.
                imgg.setImageBitmap(selectImage);//this will show that image in imageview.
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(this, "Somthing Went Wrong!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "No Image Selected!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void askPermission() {
        Dexter.withContext(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void
                    onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                    }
                    @Override
                    public void
                    onPermissionRationaleShouldBeShown(List<PermissionRequest> list,
                                                       PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }
}

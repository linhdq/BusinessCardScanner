package com.ldz.fpt.businesscardscanner.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ldz.fpt.businesscardscanner.R;
import com.ldz.fpt.businesscardscanner.crop_image.CropImageView;
import com.ldz.fpt.businesscardscanner.utils.Constant;

public class CropImageActivity extends AppCompatActivity implements CropImageView.OnSetImageUriCompleteListener,
        CropImageView.OnCropImageCompleteListener, View.OnClickListener {
    //view
    private CropImageView imvImage;
    private LinearLayout itemRotate;
    private LinearLayout itemCrop;
    //
    private String imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        //show back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Crop Business Card");
        //
        init();
        addListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        imvImage.setOnSetImageUriCompleteListener(this);
        imvImage.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        imvImage.setOnSetImageUriCompleteListener(null);
        imvImage.setOnCropImageCompleteListener(null);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void init() {
        //view
        imvImage = (CropImageView) findViewById(R.id.imv_image);
        itemCrop = (LinearLayout) findViewById(R.id.item_crop);
        itemRotate = (LinearLayout) findViewById(R.id.item_rotate);
        //get data from bundle
        imageUri = getIntent().getStringExtra(Constant.INTENT_IMAGE_URI);
        try {
            imvImage.setImageUriAsync(Uri.parse(imageUri));
        } catch (Exception e) {
            e.printStackTrace();
            onBackPressed();
        }
    }

    private void addListener() {
        itemCrop.setOnClickListener(this);
        itemRotate.setOnClickListener(this);
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error != null) {
            Toast.makeText(this, "Failed to load image by URI", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        if (result.getBitmap() != null) {
            Intent intent = new Intent(CropImageActivity.this, FinalImageActivity.class);
            FinalImageActivity.cropImage = result.getBitmap();
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
        Toast.makeText(this, "Image is cropped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_crop:
                imvImage.getCroppedImageAsync();
                break;
            case R.id.item_rotate:
                imvImage.rotateImage(90);
                break;
            default:
                break;
        }
    }
}

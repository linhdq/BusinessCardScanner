package com.ldz.fpt.businesscardscanner.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.googlecode.leptonica.android.Pix;
import com.googlecode.leptonica.android.ReadFile;
import com.googlecode.leptonica.android.Scale;
import com.ldz.fpt.businesscardscanner.R;

public class FinalImageActivity extends AppCompatActivity {
    private static final int MIN_PIXEL_COUNT = 3 * 1024 * 1024;
    //view
    private ImageView imageView;
    //
    public static Bitmap cropImage;
    //
    private long nativePix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_image);
        //
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cropImage != null) {
            imageView.setImageBitmap(cropImage);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void init() {
        //view
        imageView = (ImageView) findViewById(R.id.imv_image);
    }

//    private long getNativepix() {
//        Pix p = ReadFile.readBitmap(cropImage);
//        final long pixPixelCount = p.getWidth() * p.getHeight();
//        if (pixPixelCount < MIN_PIXEL_COUNT) {
//            double scale = Math.sqrt(((double) MIN_PIXEL_COUNT) / pixPixelCount);
//            Pix scaledPix = Scale.scale(p, (float) scale);
//            if (scaledPix.getNativePix() != 0) {
//                p.recycle();
//                p = scaledPix;
//            }
//        }
//        return p.getNativePix();
//    }
}

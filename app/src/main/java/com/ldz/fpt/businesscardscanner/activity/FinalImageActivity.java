package com.ldz.fpt.businesscardscanner.activity;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.ldz.fpt.businesscardscanner.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FinalImageActivity extends AppCompatActivity {
    private static final String TAG = "FinalImageActivity";
    private static final int MIN_PIXEL_COUNT = 3 * 1024 * 1024;
    //view
    private ImageView imageView;
    //
    public static Bitmap cropImage;
    //
    private long nativePix;
    // OCR
    private TessBaseAPI mTess;
    private String datapath;
    private String language;

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
            checkFile(new File(datapath + "tessdata/"));
            mTess.init(datapath, language);
            processImage();
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
        // OCR
        language = "eng";
        datapath = getFilesDir()+ "/tesseract/";
        mTess = new TessBaseAPI();
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

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = getAssets();

            //open byte streams for reading/writing
            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            //copy the file to the location specified by filepath
            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkFile(File dir) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        //The directory exists, but there is no data file in it
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    public void processImage(){
        String OCRresult = "";
        mTess.setImage(cropImage);
        OCRresult = mTess.getUTF8Text();
        Log.d(TAG, OCRresult);
    }
}

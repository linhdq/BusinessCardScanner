package com.ldz.fpt.businesscardscanner.activity;

import android.app.ProgressDialog;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

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
    private TextView txtResult;
    private ScrollView scrollView;
    private ProgressDialog progressDialog;
    //
    public static Bitmap cropImage;
    //calculate time execute
    private long timeStart;
    private long timeEnd;
    private int totalTimeExecute;
    //
    private String languageCode;
    private boolean isFinished;

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
        if (cropImage != null && !isFinished) {
            //start count time execute
            timeStart = System.currentTimeMillis();
            //
            imageView.setImageBitmap(cropImage);
            new OCRProcessingImage().execute(cropImage);
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
        txtResult = (TextView) findViewById(R.id.txt_result);
        scrollView = (ScrollView) findViewById(R.id.layout_result);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Processing image ...");
        // OCR
        languageCode = "eng";
        //
        isFinished = false;
    }

    private void copyFiles(String datapath) {
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

    private void checkFile(File dir, String datapath) {
        //directory does not exist, but we can successfully create it
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles(datapath);
        }
        //The directory exists, but there is no data file in it
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);
            if (!datafile.exists()) {
                copyFiles(datapath);
            }
        }
    }

    private class OCRProcessingImage extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            String datapath = getFilesDir() + "/tesseract/";
            TessBaseAPI mTess = new TessBaseAPI();
            checkFile(new File(datapath + "tessdata/"), datapath);
            mTess.init(datapath, languageCode);
            mTess.setImage(params[0]);
            return mTess.getUTF8Text();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //end count time execute
            timeEnd = System.currentTimeMillis();
            totalTimeExecute = (int) ((timeEnd - timeStart) / 1000);
            s = String.format("Time execute: %d seconds\n", totalTimeExecute) + s;
            //
            txtResult.setText(s);
            scrollView.setVisibility(View.VISIBLE);
            progressDialog.dismiss();
            isFinished = true;
        }
    }

}

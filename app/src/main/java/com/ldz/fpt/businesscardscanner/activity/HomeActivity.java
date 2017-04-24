package com.ldz.fpt.businesscardscanner.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.ldz.fpt.businesscardscanner.R;
import com.ldz.fpt.businesscardscanner.adapter.ListCardAdapter;
import com.ldz.fpt.businesscardscanner.adapter.ListDrawerApdapter;
import com.ldz.fpt.businesscardscanner.model.CardItemModel;
import com.ldz.fpt.businesscardscanner.model.DrawerItemModel;
import com.ldz.fpt.businesscardscanner.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_REQUEST_CODE = 404;
    private static final int GALLERY_REQUEST_CODE = 405;
    //view
    private DrawerLayout drawerLayout;
    private ListView listActionDrawer;
    private RecyclerView listCard;
    private ImageView btnCamera;
    private ImageView btnQRCode;
    private ImageView btnGallery;

    //adapter
    private ListDrawerApdapter listDrawerApdapter;
    private ListCardAdapter listCardAdapter;
    //collections
    private List<DrawerItemModel> drawerItemModelList;
    private List<CardItemModel> cardItemModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //
        init();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        if (listCardAdapter == null) {
            bindDataToListCard();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void init() {
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        //view
        listActionDrawer = (ListView) findViewById(R.id.list_menu_drawer);
        listCard = (RecyclerView) findViewById(R.id.list_card);
        btnCamera = (ImageView) findViewById(R.id.imv_camera_button);
        btnQRCode = (ImageView) findViewById(R.id.imv_qr_code_button);
        btnGallery = (ImageView) findViewById(R.id.imv_gallery_button);

        //collections
        drawerItemModelList = new ArrayList<>();
        drawerItemModelList.add(new DrawerItemModel(R.mipmap.ic_language, getString(R.string.add_language)));
        drawerItemModelList.add(new DrawerItemModel(R.mipmap.ic_send, getString(R.string.feedback)));
        drawerItemModelList.add(new DrawerItemModel(R.mipmap.ic_about, getString(R.string.about)));
        //
        listDrawerApdapter = new ListDrawerApdapter(this, drawerItemModelList);
        listActionDrawer.setAdapter(listDrawerApdapter);
    }

    private void addListener() {
        //
        btnGallery.setOnClickListener(this);
        btnQRCode.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        //
        listActionDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(HomeActivity.this, LanguageManagerActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void bindDataToListCard() {
        //dump data
        cardItemModelList = new ArrayList<>();
        cardItemModelList.add(new CardItemModel(R.mipmap.card4, "Nguyen Van A", "0123456789", "nguyen.van.a@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card12, "Nguyen Van B", "1253245634", "nguyen.van.b@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card5, "Nguyen Thi C", "0976678444", "nguyen.thi.c@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card7, "Nguyen Thi D", "0976678454", "nguyen.thi.d@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card7, "Nguyen Thi E", "0976678454", "nguyen.thi.d@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card7, "Nguyen Thi F", "0976678454", "nguyen.thi.d@gmail.com"));
        cardItemModelList.add(new CardItemModel(R.mipmap.card7, "Nguyen Thi G", "0976678454", "nguyen.thi.d@gmail.com"));
        //
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listCardAdapter = new ListCardAdapter(cardItemModelList, this);
        listCard.setLayoutManager(layoutManager);
        listCard.setAdapter(listCardAdapter);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_qr_code_button:

                break;
            case R.id.imv_camera_button:
                openCamera();
                break;
            case R.id.imv_gallery_button:
                openGallery();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                case CAMERA_REQUEST_CODE:
                    String uri = data.getData().toString();
                    if (uri != null && !uri.isEmpty()) {
                        Intent cropImageIntent = new Intent(HomeActivity.this, CropImageActivity.class);
                        cropImageIntent.putExtra(Constant.INTENT_IMAGE_URI, uri);
                        startActivity(cropImageIntent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}

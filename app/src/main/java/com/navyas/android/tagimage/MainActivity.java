package com.navyas.android.tagimage;

import android.Manifest;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private static int columnIndex;
    private static final String[] proj = {MediaStore.Images.Media.DATA};
    public static final String ClientId = "id";
    public static final String ClientSecret = "secret";
    public static List<String> string = new ArrayList<String>();
    private static List<File> files = new ArrayList<>();
    private static List<String> grid = new ArrayList<>();
    public static int stop = 0;
    public static ProgressBar mProgress;
    public static TextView mStatus;
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
        menu.setTitle("");

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
           getData();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "External Storage Permission Required", Toast.LENGTH_SHORT).show();
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);

        }

        Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ClarifaiService.class);
        startService(intent);


        if (isMyServiceRunning(ClarifaiService.class)) {
            mProgress = (ProgressBar) findViewById(R.id.progress_bar);
            mStatus = (TextView) findViewById(R.id.status);
            mProgress.setIndeterminate(true);
            mProgress.setProgress(0);
        }


        ImageButton btnSearch = (ImageButton) findViewById(R.id.search_button);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    grid.clear();
                    String projection[] = {ClarifaiContract.DataEntry._ID,
                            ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION,
                    };

                    String selection = ClarifaiContract.DataEntry.COLUMN_TAG1 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG2 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG3 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG4 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG5 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG6 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG7 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG8 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG9 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG10 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG11 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG12 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG13 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG14 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG15 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG16 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG17 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG18 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG19 + "=? OR " +
                            ClarifaiContract.DataEntry.COLUMN_TAG20 + "=?";

                    String query = ((EditText) findViewById(R.id.edit_query)).getText().toString().toLowerCase();
                    query = query.trim();
                    String[] selectionArgs = {query, query, query, query, query, query, query, query, query, query
                            , query, query, query, query, query, query, query, query, query, query};
                    Cursor c;
                    String sortOrder =
                            ClarifaiContract.DataEntry._ID + " ASC";
                    ClarifaiDbHelper mDbHelper = new ClarifaiDbHelper(v.getContext());
                    SQLiteDatabase db = mDbHelper.getWritableDatabase();
                    c = db.query(ClarifaiContract.DataEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    c.moveToFirst();

                    grid.add(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                    while (c.moveToNext()) {
                        grid.add(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                    }


                    Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                    intent.putExtra("grid", (Serializable) grid);

                    startActivity(intent);


                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "No result found", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    private void getData() {
        try {
            string.clear();
            cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
            columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            string.add(cursor.getString(columnIndex));
            while (cursor.moveToNext()) {
                string.add(cursor.getString(columnIndex));
            }

            for (String attr : string) {
                File file = new File(attr);
                files.add(file);
            }
        }
        catch (Exception e){
            Toast.makeText(this, "No images found", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getData();
                Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ClarifaiService.class);
                startService(intent);
            } else {
                Toast.makeText(this, "External read permission has not been granted, cannot read images", Toast.LENGTH_SHORT).show();
                turnOffProgressBar();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //For Debugging
       /* if (id == R.id.action_delete) {
            ClarifaiDbHelper mDbHelper = new ClarifaiDbHelper(this);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(ClarifaiContract.DataEntry.TABLE_NAME, null, null);
            return true;
        }*/

        if (id == R.id.action_resync) {
            stop = 0;
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "External read permission has not been granted, cannot read images", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Intent.ACTION_SYNC, null, this, ClarifaiService.class);
            startService(intent);

            mProgress.setVisibility(View.VISIBLE);
            mStatus.setVisibility(View.VISIBLE);

            return true;
        }

        if (id == R.id.action_stop) {
            stop = 1;
            turnOffProgressBar();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void turnOffProgressBar() {
        mProgress.setVisibility(View.INVISIBLE);
        mStatus.setVisibility(View.INVISIBLE);
    }

    private BroadcastReceiver onBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent i) {
            turnOffProgressBar();
            if(!isNetworkAvailable()) Toast.makeText(ctxt, "No internet", Toast.LENGTH_SHORT).show();
        }
    };

    protected void onResume() {
        super.onResume();
        registerReceiver(onBroadcast, new IntentFilter("mymessage"));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(onBroadcast);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }


}

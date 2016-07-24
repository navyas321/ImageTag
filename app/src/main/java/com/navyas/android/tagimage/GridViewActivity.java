package com.navyas.android.tagimage;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    public static List<String> gridlist = new ArrayList<String>();
    public static int screenSize;
    public static String[]gridArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        Toast.makeText(this, "Long press on an image to share it", Toast.LENGTH_LONG).show();

        gridlist = (ArrayList<String>) getIntent().getSerializableExtra("grid");
        gridArray = gridlist.toArray(new String[gridlist.size()]);

        screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




    }


}

package com.navyas.android.tagimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    public static List<String> gridlist = new ArrayList<String>();

    public static String[]gridArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);

        gridlist = (ArrayList<String>) getIntent().getSerializableExtra("grid");
        gridArray = gridlist.toArray(new String[gridlist.size()]);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));





    }

   /* @Override
    protected void onDestroy(){
        gridlist.clear();
        super.onDestroy();
    }*/

}

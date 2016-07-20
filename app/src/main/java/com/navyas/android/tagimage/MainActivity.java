package com.navyas.android.tagimage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Cursor cursor;
    private static int columnIndex;
    private static final String[] proj = {MediaStore.Images.Media.DATA};
    public static final String ClientId = "-0tf0EPVrK7qaqFM5mGSr5x6RGfxTfYj2HuBRQ3O";
    public static final String ClientSecret = "s7ZGulJ7JNJZaBsNkZtWmk0Rrhi4W7xAyiGCiQjO";
    private static Uri uri;
    private static String[] tagName = new String[20];
    public static List<String> string = new ArrayList<String>();
    private static List<String> grid = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnSync = (Button) findViewById(R.id.sync_button);

        btnSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                List<File> files = new ArrayList<>();

                cursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, null, null, MediaStore.Images.Media.DEFAULT_SORT_ORDER);
                columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                string.add(cursor.getString(columnIndex));


                while (cursor.moveToNext()){
                    string.add(cursor.getString(columnIndex));
                }
               // cursor.close();

                for (String attr: string) {
                    uri = Uri.fromFile(new File(attr));
                    File file = new File(attr);
                    files.add(file);
                    //Log.e("Tag", uri.toString());
                }


                /*File[] fileArray = new File[files.size()];
                fileArray = files.toArray(fileArray);
                final File[] finalFileArray = fileArray;
                new ClarifaiRecognize(v.getContext()).execute(finalFileArray);*/
                Intent intent = new Intent(Intent.ACTION_SYNC, null, v.getContext(), ClarifaiService.class);
                //intent.putExtra("files", (Serializable) string);
                startService(intent);
            }
        });

        Button btnSearch = (Button) findViewById(R.id.search_button);
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
                    // System.out.println(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                    grid.add(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                    while (c.moveToNext()) {
                        //System.out.println(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                        grid.add(c.getString(c.getColumnIndex(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION)));
                    }
                    //   c.close();


                    Intent intent = new Intent(MainActivity.this, GridViewActivity.class);
                    intent.putExtra("grid", (Serializable) grid);
                    //grid.clear();
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(v.getContext(), "No result found", Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ClarifaiRecognize extends AsyncTask<File, Void, Void>{

        private Context mContext;


        private ClarifaiRecognize(Context context){
            mContext = context;
        }

        protected Void doInBackground(File ... files){
            ClarifaiClient client = new ClarifaiClient(ClientId, ClientSecret);
            ClarifaiDbHelper mDbHelper = new ClarifaiDbHelper(mContext);
            SQLiteDatabase db = mDbHelper.getWritableDatabase();
            db.delete(ClarifaiContract.DataEntry.TABLE_NAME, null, null);
            ContentValues values = new ContentValues();

            int i;
            for (File file: files) {
                i = 0;
                List<RecognitionResult> results = client.recognize(new RecognitionRequest(file));
                for (Tag tag : results.get(0).getTags()) {
                    System.out.println(i + ": " + tag.getName() + ": " + tag.getProbability());
                    tagName[i] = tag.getName();
                    i++;
                }

                Uri uri = Uri.fromFile(file);
                values.put(ClarifaiContract.DataEntry.COLUMN_IMAGE_LOCATION, uri.toString());
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG1, tagName[0]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG2, tagName[1]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG3, tagName[2]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG4, tagName[3]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG5, tagName[4]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG6, tagName[5]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG7, tagName[6]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG8, tagName[7]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG9, tagName[8]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG10, tagName[9]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG11, tagName[10]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG12, tagName[11]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG13, tagName[12]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG14, tagName[13]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG15, tagName[14]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG16, tagName[15]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG17, tagName[16]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG18, tagName[17]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG19, tagName[18]);
                values.put(ClarifaiContract.DataEntry.COLUMN_TAG20, tagName[19]);

                long newRowId;
                newRowId = db.insert(ClarifaiContract.DataEntry.TABLE_NAME, null, values);
//                if (newRowId < 0) throw new android.database.SQLException("Failed to insert row into ");
            }

//            testDb(db);

            return null;
        }



    }

}

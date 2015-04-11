package com.example.student.mylist;

import android.graphics.Color;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Myactivity";
    String[] colourNames;
    String fcolour=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        colourNames = getResources().getStringArray(R.array.listArray);
        ListView lv = (ListView) findViewById(R.id.listView);
        ArrayAdapter aa = new ArrayAdapter(this, R.layout.activity_listview, colourNames);
        lv.setAdapter(aa);
        try{

            File sdcard = Environment.getExternalStorageDirectory();
            File myfile = new File(sdcard,"colorfile.txt");

            FileInputStream fis = new FileInputStream(myfile);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String st = br.readLine();
            int color = Color.parseColor(st);
            RelativeLayout lay = (RelativeLayout) findViewById(R.id.mylayout);
            lay.setBackgroundColor(color);

            br.close();

        } catch (Exception e){

        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                String[] col = getResources().getStringArray(R.array.listValues);
                RelativeLayout mylay = (RelativeLayout) findViewById(R.id.mylayout);
                setColor(col[position]);
                int c = Color.parseColor(col[position]);
                mylay.setBackgroundColor(c);
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(), Toast.LENGTH_SHORT).show();

            }
        });
        registerForContextMenu(lv);
    }

    public void setColor(String col){
         fcolour = col;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Write Colour to SD Card");
        menu.add(0, v.getId(), 0, "Read Colour from SD Card");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Write Colour to SD Card") {
            try {
                File root = Environment.getExternalStorageDirectory();
                if (root.canWrite()){
                    File gpxfile = new File(root, "colorfile.txt");
                    FileWriter gpxwriter = new FileWriter(gpxfile);
                    BufferedWriter out = new BufferedWriter(gpxwriter);
                    out.write(fcolour);
                    out.close();
                }
            } catch (Exception e) {

            }
            Toast.makeText(getApplicationContext(), fcolour, Toast.LENGTH_LONG).show();
        } else if (item.getTitle() == "Read Colour from SD Card") {
            try {
                File sdcard = Environment.getExternalStorageDirectory();

                    File myfile = new File(sdcard, "colorfile.txt");
                    FileReader freader = new FileReader(myfile);
                    BufferedReader in = new BufferedReader(freader);
                    String c =in.readLine();
                    int color= Color.parseColor(c);
                    findViewById(R.id.mylayout).setBackgroundColor(color);
                    in.close();

            } catch (Exception e) {

            }

            Toast.makeText(getApplicationContext(), "Read colour from Sd Card", Toast.LENGTH_LONG).show();
        } else {
            return false;
        }
        return true;
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
}

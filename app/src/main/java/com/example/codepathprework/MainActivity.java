package com.example.codepathprework;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<String> items;

    Button addbtn;
    EditText edittxt;
    RecyclerView rvitems;
    ItemsAdaptor ia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addbtn = findViewById(R.id.addbtn);
        edittxt = findViewById(R.id.editTextTextPersonName4);
        rvitems = findViewById(R.id.itemlist);

        //starts file and list, no need for mock data anymore
        loadItems();

        ItemsAdaptor.OnLongClickListener olcl = new ItemsAdaptor.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete item
                items.remove(position);
                //tell adaptor position the item was removed from
                ia.notifyItemRemoved(position);
                //toast item was removed
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        ia = new ItemsAdaptor(items, olcl);
        rvitems.setAdapter(ia);
        //makes list vertical
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        //adding functionality to the addbtn
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //puts String that was in the edittxt box into a string variable
                String todoitem = edittxt.getText().toString();
                //add item to model
                items.add(todoitem);
                //tell adapter we added an item
                ia.notifyItemInserted(items.size()-1);
                //clear text in edittxt
                edittxt.setText("");
                //make a toast to tell user item was added (toast - a short banner message)
                //.show() makes sure the toast shows
                Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
                saveItems();


            }
        });{


        }

    }
    //adding in code to help with persistence (so when app closes, it doesn't reset to the original list)

    //creates the file (named data.txt)
    private File getDataFile(){
        //will return the file where list of to do items is kept
        return new File(getFilesDir(), "data.txt");

    }

    //reads and loads the data.txt file
    //call when app is opened
    private void loadItems(){
        //read all items and put them into an array list
        try {
            List<String> dataFromFile = FileUtils.readLines(getDataFile(), Charset.defaultCharset());
            items = new ArrayList<>(dataFromFile);
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    //writing the data.txt file
    //call when any change is made (add, remove, etc.)
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(),items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }

    }


}
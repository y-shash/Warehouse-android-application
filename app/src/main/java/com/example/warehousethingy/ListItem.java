package com.example.warehousethingy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.example.warehousethingy.provider.ItemViewModel;

public class ListItem extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    ArrayListAdapter adapter;
    private ItemViewModel mItemViewModel;


    //private List<ArrayForListItem> arrays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);
        //String jsonString = getIntent().getStringExtra(MainActivity.Array_list);
        //initializeArrayList(jsonString);

        // Initializing the toolbar

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initializing the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView = findViewById(R.id.item_list);
        recyclerView.setLayoutManager(layoutManager);

        // Initializing the adapter
        adapter = new ArrayListAdapter();
       // adapter.setData(arrays);
        recyclerView.setAdapter(adapter);

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
                    adapter.setArrays(newData);
                    adapter.notifyDataSetChanged();
                    //tv.setText(newData.size() + "");
    });
    //private void initializeArrayList(String jsonString) {
        //Type type = new TypeToken<List<ArrayForListItem>>() {}.getType();
        //Gson gson = new Gson();
        //arrays = gson.fromJson(jsonString, type);

    }
    }



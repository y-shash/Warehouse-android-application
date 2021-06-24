package com.example.warehousethingy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.ToggleButton;

import com.example.warehousethingy.provider.ArrayForListItem;
import com.example.warehousethingy.provider.ItemViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.StringTokenizer;


public class MainActivity extends AppCompatActivity {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private TextView item_text, quantity_text, cost_text, des_text;
    private ToggleButton frozen_boolean;
    private DrawerLayout drawerlayout;
    public FloatingActionButton fab;
    public boolean choice;
    View myactivity;
    int xdown, ydown;
    //ArrayList<ArrayForListItem> array;
    //final static String Array_list="array";
    ArrayListAdapter adapter;
    private ItemViewModel mItemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_main);

        item_text = findViewById(R.id.item_text);
        quantity_text = findViewById(R.id.quantity_text);
        cost_text = findViewById(R.id.cost_text);
        des_text = findViewById(R.id.des_text);
        frozen_boolean = findViewById(R.id.toggleButton);
        myactivity = findViewById(R.id.main_activity);
        fab=findViewById(R.id.fab);
        drawerlayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerlayout, toolbar, R.string.add_item_text,
                R.string.clear_item_text);
        drawerlayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());

        resShraredPref();

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);
        MyBroadCastReceiver myBroadCastReceiver = new MyBroadCastReceiver();
        registerReceiver(myBroadCastReceiver, new IntentFilter(SMSReceiver.SMS_FILTER));
        choice=false;
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View view){
                choice=true;
                addItem(view);
                choice=false;
            }
        });
        //array=new ArrayList<>();

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        mItemViewModel.getAllItems().observe(this, newData -> {
            adapter.setArrays(newData);
            adapter.notifyDataSetChanged();
            //tv.setText(newData.size() + "");

        });

        adapter = new ArrayListAdapter();


        myactivity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getActionMasked();

                switch(action){
                    case MotionEvent.ACTION_DOWN:
                        xdown = (int)event.getX();
                        ydown = (int)event.getY();
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        return true;

                    case MotionEvent.ACTION_UP:
                        if(Math.abs(ydown-event.getY())<40) {
                            if(xdown-event.getX()<0){
                                Toast.makeText(getApplicationContext(), "New item has been added", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                item_text.setText("");
                                quantity_text.setText("");
                                cost_text.setText("");
                                des_text.setText("");
                                frozen_boolean.setChecked(false);
                                Toast.makeText(getApplicationContext(), "Item is cleared", Toast.LENGTH_SHORT).show();
                            }
                        }
                    default:
                        return false;
                }

            }
        });
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.add_item:
                    addItem(getCurrentFocus());
                    break;
                case R.id.clear_text:
                    clearAll(getCurrentFocus());
                    break;
                case R.id.list_items:
                    itemList();
                    break;
            }
            drawerlayout.closeDrawers();
            return true;
        }
    }


    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
//      moved to onRestore instead of onCreate
        super.onRestoreInstanceState(savedInstanceState);
        item_text.setText(savedInstanceState.getString("key1", ""));
        quantity_text.setText(savedInstanceState.getString("key2", "0"));
        cost_text.setText(savedInstanceState.getString("key3", "0.00"));
        des_text.setText(savedInstanceState.getString("key4", ""));
        frozen_boolean.setChecked(savedInstanceState.getBoolean("boolean", false));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id) {
            case R.id.add_item:
                addItem(getCurrentFocus());
                break;
            case R.id.clear_text:
                clearAll(getCurrentFocus());
                break;
        }
        return true;
    }

    class MyBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(SMSReceiver.SMS_MSG_KEY);
            StringTokenizer sT = new StringTokenizer(msg, ";");

            String item1 = sT.nextToken();
            String quantity1 = sT.nextToken();
            String cost1 = sT.nextToken();
            String des1 = sT.nextToken();
            boolean frozen1 = Boolean.parseBoolean(sT.nextToken());

            item_text.setText(item1);
            quantity_text.setText(quantity1);
            cost_text.setText(cost1);
            des_text.setText(des1);
            frozen_boolean.setChecked(frozen1);
        }
    }

    protected void onPause() {
        super.onPause();
        saveSharedPref();
    }

    protected void onResume() {
        super.onResume();
        resShraredPref();
    }

    private void saveSharedPref() {
//        when app gt killed save persistent data(if theres any)
//        run as a separate func
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        editor.putString("key1", item_text.getText().toString());
        editor.putString("key2", quantity_text.getText().toString());
        editor.putString("key3", cost_text.getText().toString());
        editor.putString("key4", des_text.getText().toString());
        editor.putBoolean("boolean", frozen_boolean.isChecked());
        editor.apply();
    }

    private void resShraredPref() {
//      run as separate func in onCreate
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        item_text.setText(sharedPreferences.getString("key1", ""));
        quantity_text.setText(sharedPreferences.getString("key2", "0"));
        cost_text.setText(sharedPreferences.getString("key3", "0.00"));
        des_text.setText(sharedPreferences.getString("key4", ""));
        frozen_boolean.setChecked(sharedPreferences.getBoolean("boolean", false));
    }

    public void addItem(View view) {
        if(choice){
            Snackbar.make(view, "Item saved", Snackbar.LENGTH_SHORT).show();}
        else{
            Toast.makeText(this, "New Item (" +
                    item_text.getText() + ") has been added", Toast.LENGTH_SHORT).show();}
        saveSharedPref();

        String item=item_text.getText().toString();
        String quantity=quantity_text.getText().toString();
        String cost=cost_text.getText().toString();
        String des=des_text.getText().toString();
        boolean frozen=frozen_boolean.isChecked();

        ArrayForListItem Mitem = new ArrayForListItem(item, quantity, cost, des, frozen);
        mItemViewModel.insert(Mitem);
        //ArrayForListItem array_list=new ArrayForListItem(item,quantity,cost,des,frozen);
        //array.add(array_list);

    }

    public void deleteAll(View v)
    {
        mItemViewModel.deleteAll();
    }

    public void clearAll(View view) {
        item_text.setText("");
        quantity_text.setText("");
        cost_text.setText("");
        des_text.setText("");
        frozen_boolean.setChecked(false);
        saveSharedPref();
    }



    public void itemList(){
        Intent intent = new Intent(this, ListItem.class);
        //Gson gson = new Gson();
        //String jsonString = gson.toJson(array);
        //intent.putExtra(Array_list, jsonString);
        startActivity(intent);
    }


}
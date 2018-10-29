package com.omelchenkoaleks.sqlitetest.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.omelchenkoaleks.sqlitetest.R;
import com.omelchenkoaleks.sqlitetest.database.DBHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lvAllContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvAllContacts = findViewById(R.id.lv_contacts);
        lvAllContacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,
                        ContactDetailsActivity.class);
                Bundle b = new Bundle();
                b.putInt("position", position);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(MainActivity.this, AddActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        DBHelper db = new DBHelper(this);
        ArrayList<String> names = new ArrayList<>();

        for (int i = 0; i < db.getAllContacts().size(); i ++)
            names.add(db.getAllContacts().get(i).getName());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_list, names);
        lvAllContacts.setAdapter(adapter);

        super.onResume();
    }
}

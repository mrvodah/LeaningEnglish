package com.example.vietvan.leaningenglish.activities;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.example.vietvan.leaningenglish.SettingActivity;
import com.example.vietvan.leaningenglish.adapter.TopicExpandableListViewAdapter;
import com.example.vietvan.leaningenglish.databases.DatabaseManager;
import com.example.vietvan.leaningenglish.R;
import com.example.vietvan.leaningenglish.databases.models.TopicModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DatabaseManager db;
    ExpandableListView ex;
    TopicExpandableListViewAdapter topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = DatabaseManager.getInstance(this);
        ex = findViewById(R.id.elv_toeic);
        topic = new TopicExpandableListViewAdapter(this, db.getListCategory(), db.getHashMap(db.getListTopic(), db.getListCategory()));
        ex.setAdapter(topic);

        ex.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int group, int child, long l) {

                TopicModel topicModel = db.getListTopic().get(group * 5 + child);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String lastTime = simpleDateFormat.format(Calendar.getInstance().getTime());
                DatabaseManager.getInstance(MainActivity.this).updateLastTime(topicModel, lastTime);

                Intent intent = new Intent(MainActivity.this, StudyActivity.class);
                intent.putExtra("topic", topicModel);
                startActivity(intent);

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_setting: {
                Intent i = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(i);
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        topic.refreshList(this);
    }
}

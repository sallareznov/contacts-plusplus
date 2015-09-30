package com.example.tello.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tello
 */
public class MainActivity extends OrmLiteBaseActivity<Database> {

    private final String LOG_TAG = getClass().getSimpleName();
    private RuntimeExceptionDao<Data, Integer> dao;
    private TextView textView;
    private static int COUNTER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(LOG_TAG, "creating " + getClass() + " at " + System.currentTimeMillis());
        setContentView(R.layout.contacts_list);
        dao = getHelper().getSimpleDataDao();
        DatabaseGestionnaire DBG = new DatabaseGestionnaire(dao);
        List<Data>  listData = DBG.readData();
        for (final Data data : listData){
            textView = new TextView(this);
            textView.setId(COUNTER++);
            textView.setMovementMethod(new ScrollingMovementMethod());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ContactDetail.class);
                    intent.putExtra("id", data.id);
                    startActivity(intent);
                }

            });
            ((LinearLayout) findViewById(R.id.mainLayout)).addView(textView);
            DBG.printDataToTextView(data, textView);
        }

        Button goToAddingMenu = (Button) findViewById(R.id.button);
        goToAddingMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddingContact.class);
                startActivity(intent);
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
}

package com.example.tello.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

public class ContactDetail extends OrmLiteBaseActivity<Database> {
    private Data contact;
    private DatabaseGestionnaire DBG;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);
        DBG =  new DatabaseGestionnaire(getHelper().getSimpleDataDao());
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            contact = DBG.getDataById(extras.getInt("id"));
        }
        TextView textView = new TextView(this);
        textView.setText(contact.toString());
        ((FrameLayout) findViewById(R.id.mainLayout)).addView(textView);

        Button deleteingContact = (Button) findViewById(R.id.button);
        deleteingContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                DBG.deleteData(contact);
                startActivity(intent);
            }
        });

        Button calculatingDistance = (Button) findViewById(R.id.distance);
        deleteingContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
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

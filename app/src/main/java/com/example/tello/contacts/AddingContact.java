package com.example.tello.contacts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

public class AddingContact extends OrmLiteBaseActivity<Database> {

    private RuntimeExceptionDao<Data, Integer> dao;
    private final String LOG_TAG = getClass().getSimpleName();
    private DatabaseGestionnaire DBG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contact);
        dao = getHelper().getSimpleDataDao();
        DBG = new DatabaseGestionnaire(dao);

        final EditText nom = (EditText) findViewById(R.id.nom);
        final EditText mail = (EditText) findViewById(R.id.mail);
        final EditText telephone = (EditText) findViewById(R.id.phone);
        final EditText lat = (EditText) findViewById(R.id.lat);
        final EditText lon = (EditText) findViewById(R.id.lon);

        Button adding = (Button) findViewById(R.id.add);
        adding.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!isEmpty(nom) && !isEmpty(telephone) && !isEmpty(mail) && !isEmpty(lat) && !isEmpty(lon)) {
                    Log.i(LOG_TAG, "inserting..");
                    DBG.insertData(new Data(nom.getText().toString(),
                            Integer.valueOf(telephone.getText().toString()),
                            mail.getText().toString(),
                            Double.valueOf(lat.getText().toString()),
                            Double.valueOf(lon.getText().toString())));
                }else {
                    new MyDialog("You shoud fill the formula", getApplicationContext());
                }
               // Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               // startActivity(intent);
            }
        });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adding_contact, menu);
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

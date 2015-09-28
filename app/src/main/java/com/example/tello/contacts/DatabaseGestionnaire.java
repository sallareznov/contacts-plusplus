package com.example.tello.contacts;

import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * Created by tello on 28/09/15.
 */
public class DatabaseGestionnaire extends OrmLiteBaseActivity<Database> {
    RuntimeExceptionDao<Data, Integer> dao;

    public DatabaseGestionnaire(RuntimeExceptionDao<Data, Integer> Dao){
        this.dao = Dao;
    }

    public void insertData(Data data) {
        dao.create(data);
    }

    public void deleteData(Data data) {
        dao.delete(data);
    }

    public List<Data> readData() {
        return dao.queryForAll();
    }

    public void printDataToTextView(List<Data> data, TextView tv) {
        StringBuilder sb = new StringBuilder();
        for (Data simple : data) {
            sb.append('#').append(simple).append('\n');
        }
        tv.setText(sb.toString());
    }
}
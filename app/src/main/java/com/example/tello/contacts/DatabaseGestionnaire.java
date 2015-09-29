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

    public void printDataToTextView(Data data, TextView tv) {
        StringBuilder sb = new StringBuilder();
        sb.append(data.nom).append('\n').append(data.telephone);
        tv.setText(sb.toString());
    }
}

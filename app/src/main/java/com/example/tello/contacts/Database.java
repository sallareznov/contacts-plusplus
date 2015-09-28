package com.example.tello.contacts;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by tello
 */
public class Database extends OrmLiteSqliteOpenHelper {


    // name of the database file for your application
    private static final String DATABASE_NAME = "contact.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the Data table
    private Dao<Data, Integer> dao = null;
    private RuntimeExceptionDao<Data, Integer> simpleRuntimeDao = null;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(Database.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, Data.class);

        } catch (SQLException e) {
            Log.e(Database.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }

        // here we try inserting data in the on-create as a test
        RuntimeExceptionDao<Data, Integer> dao = getSimpleDataDao();
        long millis = System.currentTimeMillis();
        // create some entries in the onCreate
        Data simple = new Data("anis", 1234567 ,"anis@anis", 33.30, 36.18);
        dao.create(simple);
        simple = new Data("salla", 7654321 ,"salla@salla", 14.41, 17.26);
        dao.create(simple);
        Log.i(Database.class.getName(), "created new entries in onCreate: " + millis);
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(Database.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Data.class, true);
            // after we drop the old databases, we create the new ones
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(Database.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Returns the Database Access Object (DAO) for our SimpleData class. It will create it or just give the cached
     * value.
     */
    public Dao<Data, Integer> getDao() throws SQLException {
        if (dao == null) {
            dao = getDao(Data.class);
        }
        return dao;
    }

    /**
     * Returns the RuntimeExceptionDao (Database Access Object) version of a Dao for our SimpleData class. It will
     * create it or just give the cached value. RuntimeExceptionDao only through RuntimeExceptions.
     */
    public RuntimeExceptionDao<Data, Integer> getSimpleDataDao() {
        if (simpleRuntimeDao == null) {
            simpleRuntimeDao = getRuntimeExceptionDao(Data.class);
        }
        return simpleRuntimeDao;
    }

    /**
     * Close the database connections and clear any cached DAOs.
     */
    @Override
    public void close() {
        super.close();
        dao = null;
        simpleRuntimeDao = null;
    }
}

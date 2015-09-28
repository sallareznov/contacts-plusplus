package com.example.tello.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * Created by tello on 28/09/15.
 */
public class MyDialog {
    public MyDialog(String message, Context context) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // set title
        alertDialogBuilder.setTitle("Warrning");

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        // if this button is clicked, close
                        // current activity
                        //AddingContact.this.finish();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        Log.e(this.getClass().toString(), "here...");
        // show it
        alertDialog.show();
    }
}

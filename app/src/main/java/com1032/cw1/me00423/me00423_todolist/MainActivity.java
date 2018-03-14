package com1032.cw1.me00423.me00423_todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * MainActivity is the starting screen where user sees display of multiple lists.
 * User must click an ImageButton, whereby an intent is made, to proceed to respective list.
 */
public class MainActivity extends AppCompatActivity {

    static  DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.doddiBase = DoddiBase.getFileDatabase(this);
    }

    /**
     * Access to General List
     * @param view
     */
    public void jmpGeneral(View view) {
        Intent intent = new Intent(getApplicationContext(), GeneralActivity.class);
        startActivity(intent);
    }

    /**
     * Access to Shopping List
     * @param view
     */
    public void jmpShop(View view) {
        Intent intent = new Intent(getApplicationContext(), ShoppingActivity.class);
        startActivity(intent);
    }

    /**
     * Access to Party/Invite List
     * @param view
     */
    public void jmpParty(View view) {
        Intent intent = new Intent(getApplicationContext(), PartyActivity.class);
        startActivity(intent);
    }

    /**
     * Access to Secret List
     * (First must go through passcode check procedure)
     * @param view
     */
    public void jmpSecret(View view) {
        Intent intent = new Intent(getApplicationContext(), PassCheckActivity.class);
        startActivity(intent);
    }

}

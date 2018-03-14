package com1032.cw1.me00423.me00423_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

/**
 * PassCheckActivity is always referred to before accessing the Secret Lists Activity.
 * Its main purpose is to direct the user to either enter their passcode, or create a new one.
 * This condition is determined by whether a passcode is found in the database.
 * Consequently, checkPass() method used to redirect user to appropriate screen: create passcode vs. enter passcode.
 *
 * Disclaimer: a handler was used initially to send a Runnable object to run the intent outside the main thread.
 * This, however, proved to be unnecessary since the TextViews for the loading screen
 * and create passode screen/enter passcode screen are placed in different activities, and hence do not need
 * external threads to be running while the main thread updates the TextView's state.
 */
public class PassCheckActivity extends AppCompatActivity {

    private String passcode = null;
    private DoddiBase doddiBase = null;

    /**
     * Upon creation, connect to database.
     * Check if passcode exists in database.
     * If so, define passcode as first index (ie only acceptable value according to predefined limits in DoddiBaseDao).
     * Move to checkPass() method.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_check);

        this.doddiBase = DoddiBase.getFileDatabase(this);

        if (!doddiBase.DoddiData().getPasscode().isEmpty()) {
            passcode = doddiBase.DoddiData().getPasscode().get(0).toString();
        }
        checkPass();
    }

    /**
     * Assuming back button pressed, need to ensure checkPass() is called.
     */
    @Override
    public void onResume() {
        super.onResume();
        checkPass();
    }

    /**
     * Method that determines whether user needs to create new passcode, or enter preexisting one.
     * If passcode is null or its length is 0, ie nonexistent, need to create new passcode.
     * Otherwise, need to enter passcode.
     * The above are executed in different activities, directed here through intents.
     */
    public void checkPass() {
        if (passcode == null || passcode.length() == 0) {
            Intent intent = new Intent(getApplicationContext(), CreatePasscodeActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(getApplicationContext(), EnterPasscodeActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

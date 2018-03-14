package com1032.cw1.me00423.me00423_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * EnterPasscodeActivity accessible if user has already created a passcode, and wants to enter the Secret List.
 * After connecting to the database, and setting the stored passcode as first index,
 * onClick listener checks if entered passcode is equivalent to stored passcode.
 */
public class EnterPasscodeActivity extends AppCompatActivity {

    private EditText input;
    private Button access;
    private String savedPass;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_passcode);

        this.doddiBase = DoddiBase.getFileDatabase(this);
        savedPass = doddiBase.DoddiData().getPasscode().get(0).getPasscode();

        input = (EditText) findViewById(R.id.passcode);
        access = (Button) findViewById(R.id.confirm);

        /**
         * If the input passcode is equivalent to stored passcode, user has access to Secret List.
         * Otherwise, Toast message appears, informing user has entered wrong passcode.
         */
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String attempt = input.getText().toString();

                if (attempt.equals(savedPass)) {
                    Intent intent = new Intent(getApplicationContext(), SecretActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EnterPasscodeActivity.this, "Wrong passcode! Try again.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

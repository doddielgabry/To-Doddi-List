package com1032.cw1.me00423.me00423_todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This activity allows user to create new passcode.
 * Two stages to completing process: entering new passcode, and confirming new passcode.
 * View visibility manipulated to show relevant View.
 * Access given to Secret List/passcode stored only when both stages completed successfully.
 */
public class CreatePasscodeActivity extends AppCompatActivity {

    private EditText pass1, pass2 = null;
    private TextView enterText, confirmText = null;
    private Button mOk = null;
    private Button mUpdate = null;
    private String attempt1 = null;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        enterText = (TextView) findViewById(R.id.enterText);
        confirmText = (TextView) findViewById(R.id.confirmText);
        pass1 = (EditText) findViewById(R.id.pass1);
        pass2 = (EditText) findViewById(R.id.pass2);
        mOk = (Button) findViewById(R.id.ok);
        mUpdate = (Button) findViewById(R.id.update);

        this.doddiBase = DoddiBase.getFileDatabase(this);

        /**
         * Check for onCreate, to ensure that passcode does not already exist.
         * Important when user navigates BACK from Secret List to Create Passcode.
         * Avoids recreating new passcode.
         * So if a passcode exists, redirect instead to Enter Passcode activity.
         */
        if (!doddiBase.DoddiData().getPasscode().isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), EnterPasscodeActivity.class);
            startActivity(intent);
            finish();
        }

        /**
         * Initially set View for first password entry visible,
         * while hiding passcode-confirmation inputs.
         * Toast appears if button pressed without passcode entered.
         */
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attempt1 = pass1.getText().toString();

                if (attempt1 != null && attempt1.length() > 0) {
                    enterText.setVisibility(View.INVISIBLE);
                    pass1.setVisibility(View.INVISIBLE);
                    mOk.setVisibility(View.INVISIBLE);
                    confirmText.setVisibility(View.VISIBLE);
                    pass2.setVisibility(View.VISIBLE);
                    mUpdate.setVisibility(View.VISIBLE);

                } else {
                    Toast.makeText(CreatePasscodeActivity.this, "Need to enter a passcode", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**
         * In second stage, first passcode must be equivalent to confirmation passcode.
         * If equal, passcode inserted in database and suer gets access to Secret List.
         * Else user moves back to stage one, where an additional Toast appears explaining that passcodes do not match.
         */
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String attempt2 = pass2.getText().toString();

                if (attempt2.equals(attempt1)) {
                    doddiBase.DoddiData().insertPass(new Passcode(attempt2));

                    Intent intent = new Intent(getApplicationContext(), SecretActivity.class);
                    intent.putExtra("passcode", attempt2);
                    startActivity(intent);
                    finish();
                } else {
                    enterText.setVisibility(View.VISIBLE);
                    pass1.setVisibility(View.VISIBLE);
                    mOk.setVisibility(View.VISIBLE);
                    confirmText.setVisibility(View.INVISIBLE);
                    pass2.setVisibility(View.INVISIBLE);
                    mUpdate.setVisibility(View.INVISIBLE);

                    Toast.makeText(CreatePasscodeActivity.this, "Passcodes do not match!", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}

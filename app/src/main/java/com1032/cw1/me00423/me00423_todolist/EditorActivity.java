package com1032.cw1.me00423.me00423_todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * The EditorActivity is a special class that is accessed by all lists when an item is added or edited.
 * This class is responsible for saving the state of the list when the above actions occur.
 * It is efficient because it manages to cater for multiple lists within one activity.
 */
public class EditorActivity extends AppCompatActivity {

    private EditText editText;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        this.doddiBase = DoddiBase.getFileDatabase(this);
        editText = findViewById(R.id.editText);

        /**
         * Checking contents of intent, and setting value of editText as item's value if intent not empty.
         */
        Intent intent = getIntent();
        final Bundle extra = intent.getExtras();
        if (extra.get("noteContent") != null) {
            editText.setText(extra.getString("noteContent"));
        }

        /**
         * When pressing on an already existing listview item, the content of the listview is updated through the editContent(),
         * specified in the DoddiBaseDao interface to update values in a table.
         */
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (extra.get("noteContent") != null) {
                    doddiBase.DoddiData().editContent(textView.getText().toString(), extra.getInt("id"));
                } else {
                    doddiBase.DoddiData().insertItem(new ListItems(extra.getString("category"), textView.getText().toString()));
                }
                Log.d("Typing:", "complete");
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        Log.d("Editor", "onResume: Resumed!");
        super.onResume();
    }

}

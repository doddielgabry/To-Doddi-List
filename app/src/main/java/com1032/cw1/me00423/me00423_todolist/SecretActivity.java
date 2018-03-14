package com1032.cw1.me00423.me00423_todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * See GeneralActivity.
 */
public class SecretActivity extends AppCompatActivity {

    static List<String> secretList = null;
    static ArrayAdapter secretAdapter = null;
    private ListView secretListView = null;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        secretList = new ArrayList<String>();

        this.secretListView = (ListView) findViewById(R.id.secretListView);
        TextView helpMssg = (TextView) findViewById(R.id.secretTextView);
        secretListView.setEmptyView(helpMssg);

        this.doddiBase = DoddiBase.getFileDatabase(this);

        refreshListItems();

        secretListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("id", getAllGeneral().get(i).getId());
                intent.putExtra("noteContent", getAllGeneral().get(i).getNote());
                startActivity(intent);
            }
        });

        secretListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int deleteItem = i;

                new AlertDialog.Builder(SecretActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                doddiBase.DoddiData().deleteItem(getAllGeneral().get(deleteItem));
                                refreshStrings();
                                secretAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.secret_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.general:
                Intent shopIntent = new Intent(getApplicationContext(), GeneralActivity.class);
                startActivity(shopIntent);
                return true;
            case R.id.shopping:
                Intent inviteIntent = new Intent(getApplicationContext(), ShoppingActivity.class);
                startActivity(inviteIntent);
                return true;
            case R.id.invite:
                Intent secretIntent = new Intent(getApplicationContext(), PartyActivity.class);
                startActivity(secretIntent);
                return true;
            default:
                return false;
        }
    }

    public void jmpMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void addSecret(View view) {
        Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
        intent.putExtra("category", "secret");
        startActivity(intent);
    }

    public List<ListItems> getAllGeneral() {
        return this.doddiBase.DoddiData().getNotesFromCategory("secret");
    }

    public void refreshStrings() {
        secretList.clear();
        for (ListItems item : this.getAllGeneral()) {
            secretList.add(item.getNote());
        }
    }

    public void refreshListItems() {
        refreshStrings();
        secretAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, secretList);
        secretListView.setAdapter(secretAdapter);
    }

    @Override
    protected void onResume() {
        refreshListItems();
        Log.d("SecretActivity", "onResume: Resumed ");
        for (ListItems item : this.doddiBase.DoddiData().loadAllLists()) {
            Log.d("All items:", item.getNote());
        }
        super.onResume();
    }
}

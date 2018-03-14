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
public class ShoppingActivity extends AppCompatActivity {

    static List<String> shoppingList = null;
    static ArrayAdapter shoppingAdapter = null;
    private ListView shoppingListView = null;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        shoppingList = new ArrayList<String>();

        this.shoppingListView = (ListView) findViewById(R.id.shoppingListView);
        TextView helpMssg = (TextView) findViewById(R.id.shoppingTextView);
        shoppingListView.setEmptyView(helpMssg);

        this.doddiBase = DoddiBase.getFileDatabase(this);

        refreshListItems();

        shoppingListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("id", getAllGeneral().get(i).getId());
                intent.putExtra("noteContent", getAllGeneral().get(i).getNote());
                startActivity(intent);
            }
        });

        shoppingListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int deleteItem = i;

                new AlertDialog.Builder(ShoppingActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                doddiBase.DoddiData().deleteItem(getAllGeneral().get(deleteItem));
                                refreshStrings();
                                shoppingAdapter.notifyDataSetChanged();

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
        menuInflater.inflate(R.menu.shopping_menu, menu);
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
            case R.id.invite:
                Intent inviteIntent = new Intent(getApplicationContext(), PartyActivity.class);
                startActivity(inviteIntent);
                return true;
            case R.id.secret:
                Intent secretIntent = new Intent(getApplicationContext(), PassCheckActivity.class);
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

    public void addItem(View view) {
        Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
        intent.putExtra("category", "shopping");
        startActivity(intent);
    }

    public List<ListItems> getAllGeneral() {
        return this.doddiBase.DoddiData().getNotesFromCategory("shopping");
    }

    public void refreshStrings() {
        shoppingList.clear();
        for (ListItems item : this.getAllGeneral()) {
            shoppingList.add(item.getNote());
        }
    }

    public void refreshListItems() {
        refreshStrings();
        shoppingAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingList);
        shoppingListView.setAdapter(shoppingAdapter);
    }

    @Override
    protected void onResume() {
        refreshListItems();
        Log.d("ShoppingActivity", "onResume: Resumed ");
        for (ListItems item : this.doddiBase.DoddiData().loadAllLists()) {
            Log.d("All items:", item.getNote());
        }
        super.onResume();
    }

}

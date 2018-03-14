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
 * This is the General List whereby a user is exposed to UI elements for addition, deletion and editing of list items.
 * The Shopping, Party and Secret List Activities are identical to the GeneralActivity in format,
 * where they differ in terms or specific redirection and content setups.
 * <p>
 * By loading the database first, and then calling refreshListItems,
 * the data on display is updated to cater for any changes caused by the EditorActivity (adding/editing).
 * Similarly, the refreshListItems method is also called when items are deleted.
 * Deletion occurs within this class as a user instigates a long item click listener, and selection of the positive alert value.
 * <p>
 * When adding items, however, intents redirect the user to the EditorActivity, but by also including an extra with the intent.
 * This extra value is crucial in defining what row new information will go to when querying the database.
 */
public class GeneralActivity extends AppCompatActivity {

    /**
     * Array-list of items in to-do list.
     * Adapter to convert from array-list to list-view item.
     * List-view for each list element.
     * Database connecting all notes.
     */
    static List<String> generalList = null;
    static ArrayAdapter generalAdapter = null;
    private ListView generalListView = null;
    private DoddiBase doddiBase = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        generalList = new ArrayList<String>();
        this.generalListView = (ListView) findViewById(R.id.generalListView);
        TextView helpMssg = (TextView) findViewById(R.id.generalTextView);
        /**
         * Set default visibility of the help message TextView as visible when there are no list items.
         */
        generalListView.setEmptyView(helpMssg);

        this.doddiBase = DoddiBase.getFileDatabase(this);

        refreshListItems();

        /**
         * When clicking on items, assuming there are already-created items in the list,
         * redirect to EditorActivity where contents are edited.
         * EditorActivity checks whether intent contains value in noteContent.
         * For this reason the note is edited, and not recreated as a new note.
         */
        generalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
                intent.putExtra("id", getAllGeneral().get(i).getId());
                intent.putExtra("noteContent", getAllGeneral().get(i).getNote());
                startActivity(intent);
            }
        });

        /**
         * If a user long-clicks a list item, they are displayed an alert dialogue,
         * whereby they must choose whether or not they wish to delete the selected item.
         * As soon as the positive option is chosen, the item gets deleted from the database,
         * and the array adapter is updated after calling the refreshStrings() method.
         */
        generalListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int deleteItem = i;

                new AlertDialog.Builder(GeneralActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Delete item?")
                        .setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                doddiBase.DoddiData().deleteItem(getAllGeneral().get(deleteItem));
                                refreshStrings();
                                generalAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }

    /**
     * Creating a menu for the action bar.
     * Action bar requires a menu inflator to appear upon activity's opening.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        /**
         * Refer to general_menu xml file in the newly created menu directory under res.
         */
        menuInflater.inflate(R.menu.general_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Menu's options and effects when selected.
     * Based on option, app redirects to respective activity for easy access to different lists.
     * Switch cases used for varying (yet limited) options taking effect upon user's choice.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.shopping:
                Intent shopIntent = new Intent(getApplicationContext(), ShoppingActivity.class);
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

    /**
     * Exit button redirecting to main menu (MainActivity).
     * @param view
     */
    public void jmpMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    /**
     * Button for adding items.
     * Redirects to EditorActivity, but specifies extra value as "general"
     * (This value differs across different lists, to specify where info is stored in database).
     * @param view
     */
    public void addItem(View view) {
        Intent intent = new Intent(getApplicationContext(), EditorActivity.class);
        intent.putExtra("category", "general");
        startActivity(intent);
    }

    /**
     * Retrieves all notes from general row of ListItems table.
     * @return
     */
    public List<ListItems> getAllGeneral() {
        return this.doddiBase.DoddiData().getNotesFromCategory("general");
    }

    /**
     * Ensuring that the contents of the listview items (ie the notes) are up to date following any deletion or addition of items.
     * Accomplished by clearing current status, then iterating through all list items (from getAllGeneral()),
     * finally adding iterated object.
     */
    public void refreshStrings() {
        generalList.clear();
        for (ListItems item : this.getAllGeneral()) {
            generalList.add(item.getNote());
        }
    }

    /**
     * Ensuring listview items are up to date following any deletion or addition of items.
     */
    public void refreshListItems() {
        refreshStrings();
        generalAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, generalList);
        generalListView.setAdapter(generalAdapter);
    }

    @Override
    protected void onResume() {
        refreshListItems();
        Log.d("GeneralActivity", "onResume: Resumed ");
        for (ListItems item : this.doddiBase.DoddiData().loadAllLists()) {
            Log.d("All items:", item.getNote());
        }
        super.onResume();
    }
}

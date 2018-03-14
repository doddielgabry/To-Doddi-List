package com1032.cw1.me00423.me00423_todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Class of ListItems table in Doddibase, with rows id, category and note,
 * where id is primary key that autoincrements to accommodate additional values.
 * Category represents category of list (ie general, shopping, party, secret).
 * Note represents contents of note item in list.
 *
 * Getters and setters defined to access these attributes in other classes.
 *
 * Created by doddielgabry on 11/03/2018.
 */
@Entity
public class ListItems {

    @PrimaryKey(autoGenerate = true)
    int id;

    private String category = null;
    private String note = null;

    public ListItems(String category, String note) {
        this.category = category;
        this.note = note;
    }

    public int getId(){
        return  this.id;
    }

    public String getCategory() {
        return this.category;
    }

    public String getNote() {
        return this.note;
    }

    public void setId(int id){
        this.id = id;
    }

    public  void setCategory(String category){
        this.category = category;
    }

    public void setNote(String note){
        this.note = note;
    }

}

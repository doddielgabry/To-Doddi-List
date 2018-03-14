package com1032.cw1.me00423.me00423_todolist;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Class of Passcode table in Doddibase, with rows id and passcode,
 * where id is primary key and passcode is user's String passcode.
 * No autogenerate needed since only first (and only) index of passcode used.
 *
 * Getters and setters defined to access these attributes in other classes.
 *
 * Created by doddielgabry on 12/03/2018.
 */
@Entity
public class Passcode {

    @PrimaryKey
    private int id = 0;
    private String passcode = null;

    public Passcode(String passcode) {
        super();
        this.passcode = passcode;
    }

    public int getId() { return  this.id;}

    public void setId(int id){this.id = id; }

    public String getPasscode() {
        return this.passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }
}

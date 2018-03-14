package com1032.cw1.me00423.me00423_todolist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Instruction manual for interacting with the database using an object-oriented approach, provided by Room.
 * Includes essential queries to select, add, delete and update information in database.
 * Used when connecting and interacting with database in other Activities for permanently storing/deleting data.
 * Defined methods for each query called in respective classes/activities.
 * <p>
 * Created by doddielgabry on 11/03/2018.
 */
@Dao
public interface DoddiBaseDao {

    @Query("SELECT * FROM ListItems")
    List<ListItems> loadAllLists();

    @Query("SELECT * FROM ListItems WHERE category = :category")
    List<ListItems> getNotesFromCategory(String category);

    @Insert
    void insertItem(ListItems item);

    @Delete
    void deleteItem(ListItems item);

    @Query("UPDATE ListItems SET note = :note WHERE id = :id")
    void editContent(String note, int id);

    @Query("SELECT * FROM ListItems WHERE id = :id")
    ListItems item(int id);

    @Query("SELECT * FROM Passcode")
    List<Passcode> getPasscode();

    @Query("UPDATE Passcode SET passcode = :passcode WHERE id = 0")
    void updatePass(String passcode);

    @Insert
    void insertPass(Passcode passcode);

    @Delete
    void deletePass(Passcode passcode);

}

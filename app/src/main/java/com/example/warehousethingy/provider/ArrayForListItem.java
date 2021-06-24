package com.example.warehousethingy.provider;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ArrayForListItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "itemID")
    private int itemID;
    @ColumnInfo(name = "item")
    private String item;
    @ColumnInfo(name = "Quantity")
    private String Quantity;
    @ColumnInfo(name = "Cost")
    private  String cost;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "frozen")
    private boolean frozen;


    //    initialize all variables and takes in arguments and can be accessed by getters
    public ArrayForListItem(String item, String Quantity, String cost,
                            String description, boolean frozen) {
        this.itemID = itemID;
        this.item = item;
        this.Quantity = Quantity;
        this.cost = cost;
        this.description = description;
        this.frozen = frozen;
    }


    public int getItemID() {return itemID;}

    public void setItemID(@NonNull int itemID) {
        this.itemID = itemID;
    }

    public String getItem() {
        return item;
    }

    public void setItemName(String item) {
        this.item = item;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String Quantity) {
        this.Quantity = Quantity;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFrozen() { return frozen;}

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }
}

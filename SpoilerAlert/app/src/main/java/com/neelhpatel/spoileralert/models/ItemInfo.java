package com.neelhpatel.spoileralert.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "items",
    indices = {@Index("location_id")},
    foreignKeys = @ForeignKey(entity = LocationInfo.class,
    parentColumns = "l_id",
    childColumns = "location_id"))

public class ItemInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "u_id")
    public int id;

    @ColumnInfo(name = "item_name")
    public String itemName;

    @ColumnInfo(name = "expire_date")
    public Date expireDate;

    @ColumnInfo(name = "purchase_date")
    public Date purchaseDate;

    @ColumnInfo(name = "image_path")
    public String imagePath;

    @ColumnInfo(name = "price")
    public double price;

    @ColumnInfo(name = "quantity")
    public String quantity;

    @ColumnInfo(name = "location_id")
    public int locationId;

    @Ignore
    public ItemInfo(String itemName, Date expireDate, Date purchaseDate, String imagePath, double price, String quantity, int locationId) {
        this.itemName = itemName;
        this.expireDate = expireDate;
        this.purchaseDate = purchaseDate;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
        this.locationId = locationId;
    }

    @Ignore
    public ItemInfo(String itemName, Date expireDate, Date purchaseDate, String imagePath, double price, String quantity) {
        this.itemName = itemName;
        this.expireDate = expireDate;
        this.purchaseDate = purchaseDate;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
        this.locationId = 1;
    }

    public ItemInfo(int id, String itemName, Date expireDate, Date purchaseDate, String imagePath, double price, String quantity, int locationId) {
        this.id = id;
        this.itemName = itemName;
        this.expireDate = expireDate;
        this.purchaseDate = purchaseDate;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
        this.locationId = locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }
}

package com.neelhpatel.spoileralert.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

@Entity(tableName = "items",
    indices = {@Index("location_id")},
    foreignKeys = @ForeignKey(entity = LocationInfo.class,
    parentColumns = "l_id",
    childColumns = "location_id"))

public class ItemInfo implements Parcelable{

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

    @Ignore
    public ItemInfo(Parcel in) {
        this.id = in.readInt();
        this.itemName = in.readString();
        this.expireDate = new Date(in.readLong());
        this.purchaseDate = new Date(in.readLong());
        this.imagePath = in.readString();
        this.price = in.readDouble();
        this.quantity = in.readString();
        this.locationId = in.readInt();
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



    public String getItemName() {
        return itemName;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public int getLocationId() {
        return locationId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.itemName);
        dest.writeLong(this.expireDate.getTime());
        dest.writeLong(this.purchaseDate.getTime());
        dest.writeString(this.imagePath);
        dest.writeDouble(this.price);
        dest.writeString(this.quantity);
        dest.writeInt(this.locationId);
    }

    public static final Creator<ItemInfo> CREATOR = new Creator<ItemInfo>() {
        @Override
        public ItemInfo createFromParcel(Parcel in) {
            return new ItemInfo(in);
        }

        @Override
        public ItemInfo[] newArray(int size) {
            return new ItemInfo[size];
        }
    };

}


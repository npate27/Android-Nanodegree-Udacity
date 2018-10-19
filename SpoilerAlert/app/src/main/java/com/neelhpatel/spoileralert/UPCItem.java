package com.neelhpatel.spoileralert;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UPCItem {

    @Expose
    private String upcnumber;
    private String st0s;
    private String newupc;
    private String type;
    @Expose
    private String title;
    private String alias;
    @Expose
    private String description;
    @Expose
    private String brand;
    @Expose
    private String category;
    @Expose
    private String size;
    private String color;
    private String gender;
    private String age;
    private String unit;
    private String msrp;
    private String rateUp;
    private String rateDown;
    @Expose
    private Integer status;
    @Expose
    private Boolean error;

    /**
     * No args constructor for use in serialization
     *
     */
    public UPCItem() {
    }

    /**
     *
     * @param newupc
     * @param rateUp
     * @param error
     * @param status
     * @param alias
     * @param upcnumber
     * @param type
     * @param size
     * @param unit
     * @param st0s
     * @param title
     * @param category
     * @param msrp
     * @param color
     * @param description
     * @param age
     * @param brand
     * @param gender
     * @param rateDown
     */
    public UPCItem(String upcnumber, String st0s, String newupc, String type, String title, String alias, String description, String brand, String category, String size, String color, String gender, String age, String unit, String msrp, String rateUp, String rateDown, Integer status, Boolean error) {
        super();
        this.upcnumber = upcnumber;
        this.st0s = st0s;
        this.newupc = newupc;
        this.type = type;
        this.title = title;
        this.alias = alias;
        this.description = description;
        this.brand = brand;
        this.category = category;
        this.size = size;
        this.color = color;
        this.gender = gender;
        this.age = age;
        this.unit = unit;
        this.msrp = msrp;
        this.rateUp = rateUp;
        this.rateDown = rateDown;
        this.status = status;
        this.error = error;
    }

    public String getUpcnumber() {
        return upcnumber;
    }

    public void setUpcnumber(String upcnumber) {
        this.upcnumber = upcnumber;
    }

    public UPCItem withUpcnumber(String upcnumber) {
        this.upcnumber = upcnumber;
        return this;
    }

    public String getSt0s() {
        return st0s;
    }

    public void setSt0s(String st0s) {
        this.st0s = st0s;
    }

    public UPCItem withSt0s(String st0s) {
        this.st0s = st0s;
        return this;
    }

    public String getNewupc() {
        return newupc;
    }

    public void setNewupc(String newupc) {
        this.newupc = newupc;
    }

    public UPCItem withNewupc(String newupc) {
        this.newupc = newupc;
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UPCItem withType(String type) {
        this.type = type;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UPCItem withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public UPCItem withAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UPCItem withDescription(String description) {
        this.description = description;
        return this;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public UPCItem withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public UPCItem withCategory(String category) {
        this.category = category;
        return this;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public UPCItem withSize(String size) {
        this.size = size;
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UPCItem withColor(String color) {
        this.color = color;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UPCItem withGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public UPCItem withAge(String age) {
        this.age = age;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public UPCItem withUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public String getMsrp() {
        return msrp;
    }

    public void setMsrp(String msrp) {
        this.msrp = msrp;
    }

    public UPCItem withMsrp(String msrp) {
        this.msrp = msrp;
        return this;
    }

    public String getRateUp() {
        return rateUp;
    }

    public void setRateUp(String rateUp) {
        this.rateUp = rateUp;
    }

    public UPCItem withRateUp(String rateUp) {
        this.rateUp = rateUp;
        return this;
    }

    public String getRateDown() {
        return rateDown;
    }

    public void setRateDown(String rateDown) {
        this.rateDown = rateDown;
    }

    public UPCItem withRateDown(String rateDown) {
        this.rateDown = rateDown;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UPCItem withStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public UPCItem withError(Boolean error) {
        this.error = error;
        return this;
    }

}
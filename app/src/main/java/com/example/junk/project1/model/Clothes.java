package com.example.junk.project1.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.junk.project1.R;


import java.util.ArrayList;


public class Clothes implements Parcelable {

    public String name;
    public double price;
    private String description;
    public String size;
    public String quantity;
    private boolean isChecked;
    int sizeSelection;
    int quantitySelection;
    private int img;

    // constructor for creating instances of clothes
    public Clothes(String name, double price, String description,
                   String size, String quantity, int img) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.size = size;
        this.quantity = quantity;
        this.img = img;

    }

    //getters and setters for all clothes field variable
    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getSizeSelection() {
        return sizeSelection;
    }

    public void setSizeSelection(int sizeSelection) {
        this.sizeSelection = sizeSelection;
    }

    public int getQuantitySelection() {
        return quantitySelection;
    }

    public void setQuantitySelection(int quantitySelection) {
        this.quantitySelection = quantitySelection;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImg() {
        return img;
    }

    // 'toString' to return textual representation of an object
    @Override
    public String toString() {
        return (name + price + description + size + quantity);
    }

    public String getClothes() {
        return name + price + description + size + quantity;
    }


    // creating instances of clothes and adding it to jackets, sweaters, boots arraylist
    public static ArrayList<Clothes> getJackets() {

        ArrayList<Clothes> jackets = new ArrayList<>();
        // DecimalFormat decimalFormat = new DecimalFormat("0.00");

        jackets.add(new Clothes("Blazer Jacket" + "    $", 60.00,
                "\nResembles a suit jacket, representing more formal garment\n",
                "XS", "1", R.drawable.clothes0));
        jackets.add(new Clothes("Trench Coat" + "   $", 80.00,
                "\nWaterproof heavy-duty cotton gabardine drill and leather\n",
                "XS", "1", R.drawable.clothes1));

        return jackets;

    }

    public static ArrayList<Clothes> getSweaters() {

        ArrayList<Clothes> sweaters = new ArrayList<>();

        sweaters.add(new Clothes("Crew neck Sweater" + "    $", 15.00,
                "\n Casual layer for cooler days\n",
                "XS", "1", R.drawable.clothes2));
        sweaters.add(new Clothes("Fleece Sweater" + "   $", 40.00,
                "\nLightweight casual sweater made of a polyester synthetic wool\n",
                "XS", "1", R.drawable.clothes3));

        return sweaters;

    }

    public static ArrayList<Clothes> getBoots() {

        ArrayList<Clothes> boots = new ArrayList<>();

        boots.add(new Clothes("Chelsea Winter Boots" + " $", 85.00,
                "\nClose-fitting, ankle-high boots with an elastic side panel\n",
                "XS", "1", R.drawable.clothes4));

        boots.add(new Clothes("Dr.Martens Boots" + "    $", 100.00,
                "\nVery durable boots with 100% waterproof quality\n",
                "XS", "1", R.drawable.clothes5));

        return boots;

    }

    protected Clothes(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        description = in.readString();
        size = in.readString();
        quantity = in.readString();
    }

    // interface to generate instances of clothes class from parcel
    public static final Creator<Clothes> CREATOR = new Creator<Clothes>() {
        @Override
        public Clothes createFromParcel(Parcel in) {
            return new Clothes(in);
        }

        @Override
        public Clothes[] newArray(int size) {
            return new Clothes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    // flatten clothes object in to a Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeString(size);
        dest.writeString(quantity);
    }
}




package com.anita.myapplication.Model;


//import androidx.recyclerview.widget.RecyclerView;

public class Data {
    //extends RecyclerView.ViewHolder
    public int amount;
    public String type;
    public String note;
    public String id;

    public Data(int amount, String type, String note, String id, String date)
    {

        this.amount = amount;
        this.type = type;
        this.note = note;
        this.id = id;
        this.date = date;
    }



    private String date;
    public Data(){}



    public int getAmount(){return amount;}

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

}

package com.example.fnilofar.firstandriodapplication;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fnilofar on 8/14/2015.
 */
public class Items implements Serializable {

    public int id;
    public String text;
    public String dueDate;


    @Override
    public String toString() {
        return this.text + " Due Date: "+ this.dueDate;
    }
}

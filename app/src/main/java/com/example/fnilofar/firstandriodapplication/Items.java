package com.example.fnilofar.firstandriodapplication;

import java.io.Serializable;

/**
 * Created by fnilofar on 8/14/2015.
 */
public class Items implements Serializable {

    public String text;

    @Override
    public String toString() {
        return this.text;
    }
}

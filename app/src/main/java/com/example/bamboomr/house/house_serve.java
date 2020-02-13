package com.example.bamboomr.house;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

//存储类，通过格式化来进行存储
public class house_serve {
    Context context;
    ArrayList<house> houses=new ArrayList<house>();

    public ArrayList<house> getHouses() {
        return houses;
    }

    public house_serve(Context context) {
        this.context = context;
    }

    public void save()
    {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput("Serializable.txt",Context.MODE_PRIVATE)
            );
            outputStream.writeObject(houses);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<house> load()
    {
        try{
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            houses = (ArrayList<house>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return houses;
    }
}

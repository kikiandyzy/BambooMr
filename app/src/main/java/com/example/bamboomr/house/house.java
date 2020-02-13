package com.example.bamboomr.house;

import java.io.Serializable;

//房子类，存放x，y坐标和图片，还有目标信息
public class house implements Serializable {
    private float x;
    private float y;
    private int picture;
    private int id;

    private boolean empty;
    private Aim aim;

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public Aim getAim() {
        return aim;
    }

    public void setAim(Aim aim) {
        this.aim = aim;
    }

    public house(float x, float y, int picture, int id) {
        this.x = x;
        this.y = y;
        this.picture = picture;
        this.id = id;
        this.empty=true;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

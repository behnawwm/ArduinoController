package com.example.arduinocontroller.DB.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ButtonWidgetItem {
    public final static int TYPE_PUSH = 0;
    public final static int TYPE_TOGGLE = 1;

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    // 0 -> push
    // 1 -> toggle
    @ColumnInfo(name = "type")
    private int type;


    @ColumnInfo(name = "on")
    private String onCommand;

    @ColumnInfo(name = "off")
    private String offCommand;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOnCommand() {
        return onCommand;
    }

    public void setOnCommand(String onCommand) {
        this.onCommand = onCommand;
    }

    public String getOffCommand() {
        return offCommand;
    }

    public void setOffCommand(String offCommand) {
        this.offCommand = offCommand;
    }

    public ButtonWidgetItem(String text, int type, String onCommand, String offCommand) {
        this.text = text;
        this.type = type;
        this.onCommand = onCommand;
        this.offCommand = offCommand;
    }
}


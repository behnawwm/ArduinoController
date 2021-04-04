package com.example.arduinocontroller.Widgets.Button;

public class ButtonWidgetItem {
    private String text;
    private int type;
    private String onCommand;
    private String offCommand;

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


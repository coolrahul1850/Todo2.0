package org.snake.android.datamodels;

import java.io.Serializable;


public class TodoObject implements Serializable {

    private int id;
    private String text;
    private String priority;


    public TodoObject(int id, String description, String priority)
    {
       this.id = id;
        this.text = description;
        this.priority = priority;
    }


    public TodoObject(String description, String priority)
    {

        this.text = description;
        this.priority = priority;
    }

    public int getId(int position)
    {
        this.id =position;
        return this.id;
    }

    public int getId()
    {
        return this.id;
    }
    public String getText()
    {
        return this.text;
    }

    public String getPriority()
    {
        return this.priority;
    }
    public void setText(String text)
    {
        this.text = text;
    }

    public void setPriority (String priority)
    {
        this.priority = priority;

    }

    public String toString()
    {
        String str = "[id: " + Integer.toString(getId()) +
                "text: " + getText() +
                "priority: " + getText();

        return str;
    }


}

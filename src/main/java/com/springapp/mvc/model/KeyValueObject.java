package com.springapp.mvc.model;

/**
 * Created by Raquibul on 1/28/2016.
 */
public class KeyValueObject
{
    String key;
    String value;


    public String getTimeline()
    {
        return timeline;
    }


    public void setTimeline(String timeline)
    {
        this.timeline = timeline;
    }


    String timeline;


    public KeyValueObject(){}

    public KeyValueObject(String key, String value)
    {
        this.key = key;
        this.value = value;
    }


    public String getKey()
    {
        return key;
    }


    public void setKey(String key)
    {
        this.key = key;
    }


    public String getValue()
    {
        return value;
    }


    public void setValue(String value)
    {
        this.value = value;
    }
}

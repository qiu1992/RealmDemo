package com.qiu.realm.bean;

import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import io.realm.annotations.Required;

/**
 * Created by qiu on 2016/9/3 0003.
 */
@RealmClass
public class User2 implements RealmModel
{
    /**
     * 不为空
     */
    @Required
    private String name;

    private float age;

    private String desc;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public float getAge ()
    {
        return age;
    }

    public void setAge (float age)
    {
        this.age = age;
    }

    public String getDesc ()
    {
        return desc;
    }

    public void setDesc (String desc)
    {
        this.desc = desc;
    }
}

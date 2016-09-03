package com.qiu.realm.bean;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by qiu on 2016/9/3 0003.
 */
// 一旦一个实体类继承了RealmObject，就不允许继承其他类了。而且，默认
// 默认构造函数必须为空。或者通过implements RealmModel的方式避免继承
public class User extends RealmObject
{
    /**
     * 主键，而且不为空。@PrimaryKey隐藏着该属性被
     * @Index注解
     * Realm.createObject()不适用于有主键的对象
     */
    @Required
    @PrimaryKey
    private String name;

    private float age;

    /**
     * 不会被持久化
     */
    @Ignore
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

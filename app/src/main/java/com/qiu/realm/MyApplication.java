package com.qiu.realm;

import android.app.Application;

import java.security.SecureRandom;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by qiu on 2016/9/3 0003.
 */
public class MyApplication extends Application
{
    @Override
    public void onCreate ()
    {
        super.onCreate ();

        initRealmConfig ();
    }

    private void initRealmConfig ()
    {
        byte [] key = new byte[64];
        new SecureRandom ().nextBytes (key);
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder (this)
                .name ("qiu.realm")
                .schemaVersion (1)
                .encryptionKey (key)
                .build ();
        Realm.setDefaultConfiguration (realmConfiguration);
    }
}

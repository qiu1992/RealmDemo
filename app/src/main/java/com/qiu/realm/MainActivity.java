package com.qiu.realm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.qiu.realm.bean.User;
import com.qiu.realm.bean.User2;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button extendsWriteBtn;
    private Button implementsWriteBtn;
    private Button extendsReadBtn;
    private Button implementsReadBtn;
    private Button batchWriteBtn;
    private Button batchReadBtn;
    private Button delBtn;
    private Button updateBtn;
    private TextView resTv;

    private String TAG = "qiuqiu";

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        findViews ();
    }

    private void findViews ()
    {
        extendsWriteBtn = (Button) findViewById (R.id.extends_write_btn);
        implementsWriteBtn = (Button) findViewById (R.id.implements_write_btn);
        extendsReadBtn = (Button) findViewById (R.id.extends_read_btn);
        implementsReadBtn = (Button) findViewById (R.id.implements_read_btn);
        batchWriteBtn = (Button) findViewById (R.id.batch_write_btn);
        batchReadBtn = (Button) findViewById (R.id.batch_read_btn);
        delBtn = (Button) findViewById (R.id.del_btn);
        updateBtn = (Button) findViewById (R.id.update_btn);
        resTv = (TextView) findViewById (R.id.res_tv);

        extendsWriteBtn.setOnClickListener (this);
        implementsWriteBtn.setOnClickListener (this);
        extendsReadBtn.setOnClickListener (this);
        implementsReadBtn.setOnClickListener (this);
        batchWriteBtn.setOnClickListener (this);
        batchReadBtn.setOnClickListener (this);
        delBtn.setOnClickListener (this);
        updateBtn.setOnClickListener (this);
    }

    @Override
    public void onClick (View v)
    {
        if (v == extendsWriteBtn)
        {
            final User user = new User ();
            user.setName ("qiu" + System.currentTimeMillis ());
            user.setAge (24.2f);
            user.setDesc (String.valueOf (System.currentTimeMillis ()));

            // sync
//            Realm realm = Realm.getDefaultInstance ();
//            realm.beginTransaction ();
//            realm.copyToRealm (user);
//            realm.commitTransaction ();

            // async,onsuccess or onerror must work on thread have Looper
            final Realm realmAsync = Realm.getDefaultInstance ();
            realmAsync.executeTransactionAsync (new Realm.Transaction ()
            {
                @Override
                public void execute (Realm realm)
                {
                    realm.copyToRealm (user);
                    Log.d (TAG, "execute: ");
                }
            }, new Realm.Transaction.OnSuccess ()
            {
                @Override
                public void onSuccess ()
                {
                    Log.d (TAG, "onSuccess: ");
                }
            }, new Realm.Transaction.OnError ()
            {
                @Override
                public void onError (Throwable error)
                {
                    Log.d (TAG, "onError: ");
                }
            });
        }
        else if (v == implementsWriteBtn)
        {
            Realm realm = Realm.getDefaultInstance ();
            realm.beginTransaction ();
            User2 user2 = realm.createObject (User2.class);
            user2.setName ("qiu" + System.currentTimeMillis ());
            user2.setAge (24.5f);
            user2.setDesc (String.valueOf (System.currentTimeMillis ()));
            realm.commitTransaction ();
        }
        else if (v == extendsReadBtn)
        {
//            int position = (int) Realm.getDefaultInstance ().where (User.class).count ();
//            User user = Realm
//                    .getDefaultInstance ()
//                    .where (User.class).findAll ().get (position - 1);
//            resTv.setText (user.getName () + "/" + user.getAge () + "/" + user.getDesc ());


            Realm.getDefaultInstance ()
                    .where (User.class)
                    .findAllAsync ()
                    .asObservable ()
                    .subscribeOn (AndroidSchedulers.mainThread ())
                    .observeOn (AndroidSchedulers.mainThread ())
                    .subscribe (new Subscriber<RealmResults<User>> ()
                    {
                        @Override
                        public void onCompleted ()
                        {
                            Log.d (TAG, "onCompleted: ");
                        }

                        @Override
                        public void onError (Throwable e)
                        {
                            Log.d (TAG, "onError: ");
                        }

                        @Override
                        public void onNext (RealmResults<User> users)
                        {
                            int position = (int) Realm.getDefaultInstance ().where (User.class).count ();
                            User user = users.get (position-1);
                            resTv.setText (user.getName () + "/" + user.getAge () + "/" + user.getDesc ());
                        }
                    });
        }
        else if (v == implementsReadBtn)
        {
            // Handle clicks for implementsReadBtn
            int position = (int) Realm.getDefaultInstance ().where (User2.class).count ();
            User2 user2 = Realm
                    .getDefaultInstance ()
                    .where (User2.class).findAll ().get (position - 1);
            resTv.setText (user2.getName () + "/" + user2.getAge () + "/" + user2.getDesc ());
        }
        else if (v == batchWriteBtn)
        {
            // Handle clicks for batchWriteBtn
        }
        else if (v == batchReadBtn)
        {
            // Handle clicks for batchReadBtn
        }
        else if (v == delBtn)
        {
            // Handle clicks for delBtn
        }
        else if (v == updateBtn)
        {
            // Handle clicks for updateBtn
        }
    }

    private void getObservable (final User user)
    {
        final Realm realmAsync = Realm.getDefaultInstance ();
        realmAsync.executeTransactionAsync (new Realm.Transaction ()
        {
            @Override
            public void execute (Realm realm)
            {
                realm.copyToRealm (user);
                Log.d (TAG, "execute: ");
            }
        }, new Realm.Transaction.OnSuccess ()
        {
            @Override
            public void onSuccess ()
            {
                Log.d (TAG, "onSuccess: ");
            }
        }, new Realm.Transaction.OnError ()
        {
            @Override
            public void onError (Throwable error)
            {
                Log.d (TAG, "onError: ");
            }
        });
    }
}

package com.rj_gowda.admin.services;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity implements Message_Passer{

    Number_Print number_print;

    double latitude;
    double longitude;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Number_Print.LocalBinder binder = (Number_Print.LocalBinder) service;
            number_print = binder.getService();
            number_print.getCategories(getApplicationContext());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectCategoryService();
    }

    private void connectCategoryService() {
        Intent _intent = new Intent(this, Number_Print.class);
        bindService(_intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Coordinate cor) {
        latitude=cor.getLatitude();
        longitude=cor.getLongitude();

    }

    @Override
    public void SendMessage(Coordinate cor) {
        latitude=cor.getLatitude();
        longitude=cor.getLongitude();

        System.out.println(latitude+"------>"+longitude);

    }
}
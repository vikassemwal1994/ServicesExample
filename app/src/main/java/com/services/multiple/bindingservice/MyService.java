package com.services.multiple.bindingservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.services.multiple.R;
import java.util.Random;

public class MyService extends Service {
    private int mRandomNumber;
    private boolean mIsRandomGeneratorOn;
    private final int MIN=0;
    private final int MAX=100;
    public static final int GET_COUNT=0;


    class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }

    private IBinder mBinder = new MyServiceBinder();

    private Messenger randomNumberMessenger = new Messenger(new RandomNumberRequestHandler());

    public class RandomNumberRequestHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.e(getString(R.string.service_demo_tag),"Message intercepted");
            switch (msg.what){
                case GET_COUNT : Message messageSendRandomNumber = Message.obtain(null, GET_COUNT);
                    messageSendRandomNumber.arg1 = getRandomNumber();
                    try {
                        Log.e(getString(R.string.service_demo_tag),"Replaying with random number to requester");
                        msg.replyTo.send(messageSendRandomNumber);
                    }catch (Exception e){
                        Log.e(getString(R.string.service_demo_tag),""+e.getMessage());
                    }
            }
            super.handleMessage(msg);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(getString(R.string.service_demo_tag),"In OnBind");
        if(intent.getPackage() == "com.services.multiple"){
            return randomNumberMessenger.getBinder();
        }else{
            return mBinder;
        }

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(getString(R.string.service_demo_tag),"In onStartCommend, thread id: "+Thread.currentThread().getId());
        mIsRandomGeneratorOn =true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRandomNumberGenerator();
        Log.e(getString(R.string.service_demo_tag),"Service Destroyed");

    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }


    private void startRandomNumberGenerator(){
        while (mIsRandomGeneratorOn){
            try{
                Thread.sleep(1000);
                if(mIsRandomGeneratorOn){
                    mRandomNumber = new Random().nextInt(MAX)+MIN;
                    Log.e(getString(R.string.service_demo_tag),"Thread id: "+Thread.currentThread().getId()
                            +", Random Number: "+ mRandomNumber);
                }
            }catch (InterruptedException e){
                Log.e(getString(R.string.service_demo_tag),"Thread Interrupted");
            }

        }
    }

    private void stopRandomNumberGenerator(){
        mIsRandomGeneratorOn =false;
    }

    public int getRandomNumber(){
        return mRandomNumber;
    }

}

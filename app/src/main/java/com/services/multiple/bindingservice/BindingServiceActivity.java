package com.services.multiple.bindingservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.services.multiple.R;

public class BindingServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = BindingServiceActivity.class.getSimpleName();

    private Button buttonStart, buttonStop, buttonBind,buttonUnBind,buttonGetRandomNumber;
    private TextView textViewthreadCount;
    int count = 0;
    private MyAsyncTask myAsyncTask;

    private MyService myService;
    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    /*Handler handler;*/
    private Intent serviceIntent;
    private boolean mStopLoop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bind_service);

        Log.e(getString(R.string.service_demo_tag), "MainActivity thread id: " + Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopthread);
        buttonBind=(Button)findViewById(R.id.buttonBindService);
        buttonUnBind=(Button)findViewById(R.id.buttonUnBindService);
        buttonGetRandomNumber=(Button)findViewById(R.id.buttonGetRandomNumber);

        textViewthreadCount = (TextView) findViewById(R.id.textViewthreadCount);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);
        buttonBind.setOnClickListener(this);
        buttonUnBind.setOnClickListener(this);
        buttonGetRandomNumber.setOnClickListener(this);

        serviceIntent=new Intent(getApplicationContext(),MyService.class);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.buttonThreadStarter:
                mStopLoop = true;

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mStopLoop){
                            try{
                                Thread.sleep(1000);
                                count++;
                            }catch (InterruptedException e){
                                Log.i(TAG,e.getMessage());
                            }
                            Log.i(TAG,"Thread id in while loop: "+Thread.currentThread().getId()+", Count : "+count);
                            textViewthreadCount.post(new Runnable() {
                                @Override
                                public void run() {
                                    textViewthreadCount.setText(" "+count);
                                }
                            });
                        }
                    }
                }).start();*/
                /*myAsyncTask=new MyAsyncTask();
                myAsyncTask.execute(count);*/

                startService(serviceIntent);
                break;

            case R.id.buttonStopthread:
                /*mStopLoop = false;*/
                /* myAsyncTask.cancel(true);*/
                stopService(serviceIntent);
                break;

            case R.id.buttonBindService:
                bindService();
                    break;
            case R.id.buttonUnBindService:
                unbindService();
                    break;
            case R.id.buttonGetRandomNumber:
                setRandomNumber();
                    break;

        }

    }


    private void bindService(){
        if(serviceConnection==null){
            serviceConnection=new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                    MyService.MyServiceBinder myServiceBinder=(MyService.MyServiceBinder)iBinder;
                    myService=myServiceBinder.getService();
                    isServiceBound=true;

                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isServiceBound=false;
                }
            };
        }

        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private void unbindService(){
        if(isServiceBound){
            unbindService(serviceConnection);
            isServiceBound=false;
        }
    }

    private void setRandomNumber(){
        if(isServiceBound){
            textViewthreadCount.setText("Random number: "+myService.getRandomNumber());
        }else{
            textViewthreadCount.setText("Service not bound");
        }
    }

    private class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        private int customCounter;

        @Override
        protected Integer doInBackground(Integer... integers) {
            customCounter = integers[0];
            while (mStopLoop) {
                try {
                    Thread.sleep(1000);
                    customCounter++;
                    publishProgress(customCounter);
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }

            }
            return customCounter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            customCounter = 0;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            textViewthreadCount.setText(""+integer);
            count=integer;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            textViewthreadCount.setText(""+values[0]);
        }
    }



}

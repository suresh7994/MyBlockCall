package com.example.myblockcall;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.telecom.TelecomManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.myblockcall.modal.DataModal;
import com.example.myblockcall.modal.NumberList;
import com.example.myblockcall.modal.Result;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallReceiver extends BroadcastReceiver {
    boolean AlreadyOnCall=false;

    @Override
    public void onReceive(final Context context, final Intent intent) {
//            if (TelephonyManager.ACTION_PHONE_STATE_CHANGED.equals(intent.getAction()) && intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_RINGING)){
        final List<String> state=new ArrayList<>();
        Call<NumberList> call = RetrofitClient
                .getInstance(DataActivity.GET_CONTACT_URL).getApi().getContact();
        call.enqueue(new Callback<NumberList>() {
            @Override
            public void onResponse(Call<NumberList> call, Response<NumberList> response) {
                ITelephony telephony;

                if (response.isSuccessful()){
                            //    state=response.body().getResult()
                            for (Result result:response.body().getResult()){
                                state.add(result.getMobile().toString());
                            }

                            for (int i=0;i<state.size();i++){
                                String incoming=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                        TelecomManager telecomManager= (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                                        if (state.get(i).equals(incoming)) {
                                            telecomManager.endCall();
                                        }
                                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                        try {
                                            Class clazz = Class.forName(tm.getClass().getName());
                                            Method  m = clazz.getDeclaredMethod("getITelephony");
                                            m.setAccessible(true);

//                                            setResultData(null);
                                            telephony = (ITelephony) m.invoke(tm);

                                            if (incoming.equals(state.get(i))) {
                                                telephony.endCall();
                                                telephony.silenceRinger();

                                                Toast.makeText(context, "Ending the call from: " + incoming, Toast.LENGTH_LONG).show();
                                                //Toast.makeText(context, "Your NUmber is "+incoming, Toast.LENGTH_SHORT).show();
                                                telephony.endCall();
                                            }else {
                                                Toast.makeText(context, "cant connect your number", Toast.LENGTH_LONG).show();
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }


                                }

                                if(state.get(i).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING)){
                                    TelecomManager telecomManager= null;
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                        telecomManager = (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                            telecomManager.endCall();
                                            Toast.makeText(context, "Call Ended", Toast.LENGTH_SHORT).show();
                                        }
                                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                        try {
                                            Class clazz = Class.forName(tm.getClass().getName());
                                            Method  m = clazz.getDeclaredMethod("getITelephony");
                                            m.setAccessible(true);

//                                            setResultData(null);
                                            telephony = (ITelephony) m.invoke(tm);

                                                telephony.endCall();

                                                Toast.makeText(context, "Ending the call from: " + incoming, Toast.LENGTH_LONG).show();
                                                //Toast.makeText(context, "Your NUmber is "+incoming, Toast.LENGTH_SHORT).show();
                                                telephony.endCall();

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    Toast.makeText(context, "disconneted "+ incoming, Toast.LENGTH_SHORT).show();
                                }
                                    if(state.get(i).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                                    Toast.makeText(context, "Answered " + incoming, Toast.LENGTH_SHORT).show();
                                }
                                if(state.get(i).equalsIgnoreCase(TelephonyManager.EXTRA_STATE_IDLE)){
                                    Toast.makeText(context, "Idle "+ incoming, Toast.LENGTH_SHORT).show();
                                }

                            }
                    }
            }

            @Override
            public void onFailure(Call<NumberList> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



 }
//        if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) AlreadyOnCall = true;
//        else if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(TelephonyManager.EXTRA_STATE_IDLE))    AlreadyOnCall = false;
//    }

    private void rejectCall(Context context, String number) {
        if (!AlreadyOnCall){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                TelecomManager telecomManager= (TelecomManager) context.getSystemService(Context.TELECOM_SERVICE);
                    telecomManager.endCall();
                Toast.makeText(context, "invoked End call on Telecom Manager", Toast.LENGTH_SHORT).show();

            }
            else {
                ContentValues contentValues=new ContentValues();
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

                try {
                    Class clazz = Class.forName(tm.getClass().getName());
                  Method  m = clazz.getDeclaredMethod("getITelephony");
                    m.setAccessible(true);

                    ITelephony telephony = (ITelephony) m.invoke(tm);
                        telephony.endCall();
                    if (number!=null){
                         telephony = (ITelephony) m.invoke(tm);
                          telephony.silenceRinger();
                        Toast.makeText(context, "Your NUmber is "+number, Toast.LENGTH_SHORT).show();
                        telephony.endCall();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }


    }
}

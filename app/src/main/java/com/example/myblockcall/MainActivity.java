package com.example.myblockcall;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    private static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1;
   EditText name_1;
    EditText mobile_1;
    CheckBox checkBox;
    public static Uri uri;
    ContentValues contentValues;
SharedPreferences sharedPreferences;
    public static final String Contact_URL = "https://mynavigation.000webhostapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_1=findViewById(R.id.name_1);
        mobile_1=findViewById(R.id.number_1);
        checkBox=findViewById(R.id.check);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_DENIED || checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_DENIED) {
                String[] permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.READ_CALL_LOG,Manifest.permission.WRITE_CALL_LOG,Manifest.permission.CALL_PHONE,Manifest.permission.ANSWER_PHONE_CALLS};
                requestPermissions(permissions, PERMISSION_REQUEST_READ_PHONE_STATE);
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void blockNumber(View view) {
        String n=name_1.getText().toString();
        String c=mobile_1.getText().toString();

        if (n.equals("")){
            name_1.setError("Empty");
            name_1.requestFocus();
        }else if (c.equals("")){
            mobile_1.setError("Empty");
            mobile_1.requestFocus();
        }else {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            contentValues=new ContentValues();
            contentValues.put("number","+91"+mobile_1.getText().toString());


            Call<ResponseContact> call = RetrofitClient
                    .getInstance(Contact_URL).getApi().insertBlocknumber(name_1.getText().toString(),
                            "+91"+mobile_1.getText().toString());
            call.enqueue(new Callback<ResponseContact>() {
                @Override
                public void onResponse(Call<ResponseContact> call, Response<ResponseContact> response) {
                    if (response.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    //    startActivity(new Intent(MainActivity.this, DataActivity.class));
                    } else {
                        progressDialog.cancel();
                        //Toast.makeText(Register.this,response.toString() , Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, response.body().getMessage().toString(), Toast.LENGTH_LONG).show();


                    }
                }

                @Override
                public void onFailure(Call<ResponseContact> call, Throwable t) {
                    progressDialog.cancel();
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });



        }



//        startActivity(new Intent(MainActivity.this,DataActivity.class));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission NOT granted: " + PERMISSION_REQUEST_READ_PHONE_STATE, Toast.LENGTH_SHORT).show();
                }

                return;
            }
        }
    }


    public void blockListDetails(View view) {
        startActivity(new Intent(this,DataActivity.class));
    }
}

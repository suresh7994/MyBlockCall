package com.example.myblockcall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myblockcall.modal.AdapterHolder;
import com.example.myblockcall.modal.DataModal;
import com.example.myblockcall.modal.NumberList;
import com.example.myblockcall.modal.Result;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataActivity extends AppCompatActivity {
RecyclerView recyclerView;
    ContentValues contentValues;
  List<DataModal> data;
  AdapterHolder adapterHolder;
LinearLayout linearLayout;
    public  static  String GET_CONTACT_URL="https://mynavigation.000webhostapp.com/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        recyclerView=findViewById(R.id.list);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        linearLayout=findViewById(R.id.linearlayout);
        data=new ArrayList<>();
        //str=new ArrayList();
        getData();
        onrequestPermissions();
    }

    private void onrequestPermissions() {

        List<String> requiredPermissions = new ArrayList<>();
        requiredPermissions.add(Manifest.permission.CALL_PHONE);
        requiredPermissions.add(Manifest.permission.READ_PHONE_STATE);
        requiredPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        requiredPermissions.add(Manifest.permission.READ_CALL_LOG);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            requiredPermissions.add(Manifest.permission.ANSWER_PHONE_CALLS);
        }

        List<String> missingPermissions = new ArrayList<>();

        for (String permission : requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        if (!missingPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    missingPermissions.toArray(new String[0]), 0);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean ok = true;
        if (grantResults.length != 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    ok = false;
                    break;
                }
            }
        } else {
            // treat cancellation as failure
            ok = false;
        }

        if (!ok)
            Snackbar.make(linearLayout, R.string.blacklist_permissions_required, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.blacklist_request_permissions, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                           onrequestPermissions();
                        }
                    })
                    .show();

    }

    private void getData() {
        Call<NumberList> call = RetrofitClient
                .getInstance(GET_CONTACT_URL).getApi().getContact();
        call.enqueue(new Callback<NumberList>() {
            @Override
            public void onResponse(Call<NumberList> call, Response<NumberList> response) {

                if (response.isSuccessful()) {
                    for (Result result : response.body().getResult()) {
                    //    str.add(result.getMobile().toString()) ;
                        data.add(new DataModal(result.getName(),result.getMobile()));

                    }

                }

                adapterHolder=new AdapterHolder(DataActivity.this,data);

                recyclerView.setAdapter(adapterHolder);

                //  adapter=new AdapterList(str,DataActivity.this);

            }

            @Override
            public void onFailure(Call<NumberList> call, Throwable t) {
                Toast.makeText(DataActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}

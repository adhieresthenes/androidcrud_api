package com.posapp.nutech;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.posapp.nutech.adapter.AdapterData;
import com.posapp.nutech.api.ApiRequestData;
import com.posapp.nutech.api.Retroserver;
import com.posapp.nutech.model.DataModel;
import com.posapp.nutech.model.ResponsModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TampilData extends AppCompatActivity {
    private RecyclerView mRecycler;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<DataModel> mItems = new ArrayList<>();
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_data);

        pd = new ProgressDialog(this);
        mRecycler = (RecyclerView) findViewById(R.id.recyclerTemp);
        mManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        pd.setMessage("Loading ...");
        pd.setCancelable(false);
        pd.show();

        ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
        Call<ResponsModel> getdata = api.getData();
        getdata.enqueue(new Callback<ResponsModel>() {
            @Override
            public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                pd.hide();
                Log.d("RETRO", "RESPONSE : " + response.body().getKode());
                mItems = response.body().getResult();

                mAdapter = new AdapterData(TampilData.this,mItems);
                mRecycler.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ResponsModel> call, Throwable t) {
                pd.hide();
                Log.d("RETRO", "FAILED : respon gagal");

            }
        });

    }
}

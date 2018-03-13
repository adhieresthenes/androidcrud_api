package com.posapp.nutech;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.posapp.nutech.api.ApiRequestData;
import com.posapp.nutech.api.Retroserver;
import com.posapp.nutech.model.ResponsModel;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    EditText name, price, description;
    Button btnsave, btnTampildata, btnupdate, btndelete,selectImage, btnUpload;
    ProgressDialog pd, pd2;
    ImageView viewImage;
    String mediaPath;
    String[] mediaColumn = {MediaStore.Video.Media._ID};

    private int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //select image
        selectImage = (Button) findViewById(R.id.btn_BrowseImg);
        viewImage = (ImageView) findViewById(R.id.imgView);
        

        name = (EditText) findViewById(R.id.edt_namaItem);
        price = (EditText) findViewById(R.id.edt_harga);
        description = (EditText) findViewById(R.id.edt_deskrip);
        btnTampildata = (Button) findViewById(R.id.btntampildata);
        btnupdate =(Button) findViewById(R.id.btnUpdate);
        btnsave = (Button) findViewById(R.id.btn_insertdata);
        btndelete=(Button) findViewById(R.id.btnhapus);
        btnUpload =(Button)  findViewById(R.id.btnUpload);

        Intent data = getIntent();
        final String iddata = data.getStringExtra("id");
        if(iddata != null) {
            btnsave.setVisibility(View.GONE);
            btnTampildata.setVisibility(View.GONE);
            btnupdate.setVisibility(View.VISIBLE);
            btndelete.setVisibility(View.VISIBLE);
            name.setText(data.getStringExtra("name"));
            price.setText(data.getStringExtra("price"));
            description.setText(data.getStringExtra("description"));
        }

        pd = new ProgressDialog(this);

        pd2 = new ProgressDialog(this);
        pd2.setMessage("Uploading...");

        //event select image
        selectImage.setOnClickListener(new View.OnClickListener(){
         @Override
            public  void onClick(View v){

             Intent intent = new Intent();
             intent.setType("image/*");
             intent.setAction(Intent.ACTION_GET_CONTENT);
             startActivityForResult(Intent.createChooser(intent,"select picture"), REQUEST_CODE);
            }

        });

        //event upload image  not set
        //code


        btnTampildata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godata = new Intent(MainActivity.this, TampilData.class);
                startActivity(godata);
            }
        });

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Loading Hapus ...");
                pd.setCancelable(false);
                pd.show();

                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponsModel> del  = api.deleteData(iddata);
                del.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        Log.d("Retro", "onResponse");
                        Toast.makeText(MainActivity.this, response.body().getPesan(),Toast.LENGTH_SHORT).show();
                        Intent gotampil = new Intent(MainActivity.this,TampilData.class);
                        startActivity(gotampil);

                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.hide();
                        Log.d("Retro", "onFailure");
                    }
                });
            }
        });

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("update ....");
                pd.setCancelable(false);
                pd.show();

                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);
                Call<ResponsModel> update = api.updateData(iddata,name.getText().toString(),price.getText().toString(),description.getText().toString());
                update.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        Log.d("Retro", "Response");
                        Toast.makeText(MainActivity.this,response.body().getPesan(),Toast.LENGTH_SHORT).show();
                        pd.hide();
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.hide();
                        Log.d("Retro", "OnFailure");

                    }
                });
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("send data ... ");
                pd.setCancelable(false);
                pd.show();

                String sname = name.getText().toString();
                String sprice = price.getText().toString();
                String sdescription = description.getText().toString();
                ApiRequestData api = Retroserver.getClient().create(ApiRequestData.class);

                Call<ResponsModel> sendItems = api.sendData(sname,sprice,sdescription);
                sendItems.enqueue(new Callback<ResponsModel>() {
                    @Override
                    public void onResponse(Call<ResponsModel> call, Response<ResponsModel> response) {
                        pd.hide();
                        Log.d("RETRO", "response : " + response.body().toString());
                        String kode = response.body().getKode();

                        if(kode.equals("1"))
                        {
                            Toast.makeText(MainActivity.this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(MainActivity.this, "Data Error tidak berhasil disimpan", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsModel> call, Throwable t) {
                        pd.hide();
                        Log.d("RETRO", "Falure : " + "Gagal Mengirim Request");
                    }
                });
            }
        });
    }

    //method view image from storage
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode ==RESULT_OK && data != null && data.getData() != null){
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                viewImage.setImageBitmap(bitmap);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

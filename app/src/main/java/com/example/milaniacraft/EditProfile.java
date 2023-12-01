package com.example.milaniacraft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.milaniacraft.API.ApiClient;
import com.example.milaniacraft.API.ApiInterface;
import com.example.milaniacraft.Adapter.RealPathUtil;
import com.example.milaniacraft.ModelAkun.ResponseEditUser;
import com.example.milaniacraft.ModelAkun.ResponseHapusFoto;
import com.example.milaniacraft.ModelAkun.ResponseImg;
import com.example.milaniacraft.ModelAkun.ResponseUser;
import com.squareup.picasso.Picasso;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {
    ImageView back;
    CircleImageView FotoProfil;
    EditText nama,email,telp;
    CardView alerd;
    Button savePic;
    AlertDialog dialog;
    ApiInterface apiInterface;
    String path = "";
    final int  REQUEST_GALLERY = 9544;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        notif(EditProfile.this);
        delimage();
        setData();
        UploadImage();

        back = findViewById(R.id.kembaliProfil);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void delimage(){
        alerd = findViewById(R.id.alertUplod);
        alerd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.show();
            }
        });
    }

    public void confirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        View view = getLayoutInflater().inflate(R.layout.alert_upload, null);
        builder.setView(view);
        dialog = builder.create();
        TextView UbahProfil, HapusFoto;

        UbahProfil = view.findViewById(R.id.EditFotoProfil);
        UbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Open Gallery"),REQUEST_GALLERY);
                    Toast.makeText(EditProfile.this, "Open Galleerrtyy", Toast.LENGTH_SHORT).show();
                } else {
                    ActivityCompat.requestPermissions(EditProfile.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    Toast.makeText(EditProfile.this, "Not Open Your galery", Toast.LENGTH_SHORT).show();
                }
            }
        });

        HapusFoto = view.findViewById(R.id.HapusFoto);
        HapusFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<ResponseHapusFoto> hapusFotoCall = apiInterface.HapusFoto(getIntent().getStringExtra("id_user"));
                hapusFotoCall.enqueue(new Callback<ResponseHapusFoto>() {
                    @Override
                    public void onResponse(Call<ResponseHapusFoto> call, Response<ResponseHapusFoto> response) {
                        if (response.body().getKode() == 1){
                            Toast.makeText(EditProfile.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            FotoProfil.setImageResource(R.drawable.ic_baseline_account_circle_24);
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseHapusFoto> call, Throwable t) {

                    }
                });

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getData();
            Context context = EditProfile.this;
            path = RealPathUtil.getRealPath(context, uri);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            FotoProfil.setImageBitmap(bitmap);
            System.out.println("Tes Pathhh");
            dialog.dismiss();

        }else{
            System.out.println("gagal merubah");
        }
    }

    public void setData(){
        email = (EditText) findViewById(R.id.editEmail);
        nama = (EditText) findViewById(R.id.editNama);
        telp = (EditText) findViewById(R.id.editTelp);
        FotoProfil = (CircleImageView) findViewById(R.id.imgProfile);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseUser> userCall = apiInterface.getDataUser(getIntent().getStringExtra("id_user"));
        userCall.enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                email.setText(response.body().getData().getEmail());
                email.setEnabled(false);
                nama.setText(response.body().getData().getNama());
                telp.setText(response.body().getData().getTelp());
                Picasso.get().load(ApiClient.IMAGES_PROF+response.body().getData().getImageUser()).error(R.drawable.ic_baseline_account_circle_24).into(FotoProfil);
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {

            }
        });

    }

    public void UploadImage(){
        savePic = (Button) findViewById(R.id.edit);
        savePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (path.equals("")){
                    System.out.println("PATH KOSONGGG");
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseEditUser> userCall = apiInterface.getUpdateDataUser(getIntent().getStringExtra("id_user"),nama.getText().toString(),email.getText().toString(),telp.getText().toString());
                    userCall.enqueue(new Callback<ResponseEditUser>() {
                        @Override
                        public void onResponse(Call<ResponseEditUser> call, Response<ResponseEditUser> response) {
                            if (response.body().getKode() == 1){
                                Toast.makeText(EditProfile.this, "DATA SAJA BERHASIL UPDATE", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println(response.body().getKode());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseEditUser> call, Throwable t) {

                        }
                    });
                } else {
                    File file = new File(path);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("imageupload", file.getName(), requestFile);
                    RequestBody cus_name = RequestBody.create(MediaType.parse("multipart/form-data"),getIntent().getStringExtra("id_user"));
                    RequestBody namaUSer = RequestBody.create(MediaType.parse("multipart/form-data"),nama.getText().toString());
                    RequestBody emailUser = RequestBody.create(MediaType.parse("multipart/form-data"),email.getText().toString());
                    RequestBody telpUSer = RequestBody.create(MediaType.parse("multipart/form-data"),telp.getText().toString());
                    apiInterface = ApiClient.getClient().create(ApiInterface.class);
                    Call<ResponseImg> imgCall = apiInterface.uploadImage(body,cus_name,namaUSer,emailUser,telpUSer);
                    imgCall.enqueue(new Callback<ResponseImg>() {
                        @Override
                        public void onResponse(Call<ResponseImg> call, Response<ResponseImg> response) {
                            if (response.body().getKode() == 1){
                                Toast.makeText(EditProfile.this, "Berhasil Upload", Toast.LENGTH_SHORT).show();
                            } else {
                                System.out.println(response.body().getKode());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseImg> call, Throwable t) {
                            Toast.makeText(EditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            System.out.println(t.getMessage());
                        }
                    });
                }
            }
        });
    }

    public void notif(Activity activity){
        //change color notif bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.coklat));
        //set icons notifbar
        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

}
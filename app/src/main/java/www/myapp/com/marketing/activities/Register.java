package www.myapp.com.marketing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.send.Send_data_registed;

public class Register extends AppCompatActivity {
    EditText fname, pass, emaill;
    ImageView select_photo;
    Button create;
    RequestQueue requestQueue;
    String encodimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = (EditText) findViewById(R.id.fname);
        pass = (EditText) findViewById(R.id.u_password);
        emaill = (EditText) findViewById(R.id.u_email);

        create = (Button) findViewById(R.id.create_btn);
        select_photo = (ImageView) findViewById(R.id.select_img);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText().toString().equals("") || emaill.getText().toString().equals("") || pass.getText().toString().equals("") ) {
                    Toast.makeText(Register.this, "enter all data", Toast.LENGTH_SHORT).show();
                } else {


                    sendData();

                }
            }
        });
        select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "select picture"), 1);

            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                select_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendData() {

        sendImages();
        String firstn = fname.getText().toString();
        String email_d = emaill.getText().toString();
        String password = pass.getText().toString();



        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(Register.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Register.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Send_data_registed Send_Data_registed = new Send_data_registed(firstn, email_d, password, encodimg, r_listener);
        requestQueue = Volley.newRequestQueue(Register.this);
        requestQueue.add(Send_Data_registed);
        sweetdailog2();


    }

    void sendImages() {
        Bitmap bitmap = ((BitmapDrawable) select_photo.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


    }

    void sweetdailog2() {

        final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(this);
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setTitleText("Loading...");
        sweetAlertDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(2000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent intent = new Intent(Register.this, MainActivity.class);

                                SharedPreferences preferences=getSharedPreferences("file", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putString("email",emaill.getText().toString());
                                editor.putString("password",pass.getText().toString());
                                editor.apply();
                                new publicUserNmae(emaill.getText().toString());
                                startActivity(intent);

                                finish();


                            }
                        });

                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
            }
        }).start();

    }


}

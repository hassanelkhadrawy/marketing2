package www.myapp.com.marketing.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.send.update;

public class Setting extends AppCompatActivity {

    EditText s_name, s_pass, s_emaill;
    ImageView s_photo;
    Button save;
    RequestQueue requestQueue;
    String encodimg;

    ArrayList<item> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        calling();
        handling();

    }

    void calling() {

        s_name = (EditText) findViewById(R.id.s_name);
        s_emaill = (EditText) findViewById(R.id.s_email);
        s_pass = (EditText) findViewById(R.id.s_password);
        s_photo = (ImageView) findViewById(R.id.s_img);
        save = (Button) findViewById(R.id.save_btn);


    }

    void handling() {
        select();
        s_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "select picture"), 1);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();

            }
        });
    }


    void select() {
        Bundle b=getIntent().getExtras();
        String test_username=b.getString("username");

        String url = "https://hassan-elkhadrawy.000webhostapp.com/select_account.php?username=" + test_username;

        requestQueue = Volley.newRequestQueue(Setting.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            array.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                String name1 = getData.getString("name");
                                String username1 = getData.getString("username");
                                String password1 = getData.getString("password");
                                String image1 = getData.getString("p_image");

                                s_name.setText(name1);
                                s_emaill.setText(username1);
                                s_pass.setText(password1);

                                Picasso.with(Setting.this).load("http://hassan-elkhadrawy.000webhostapp.com/" + image1 + "").into(s_photo);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    void sendData() {

        sendImages();
        String firstn = s_name.getText().toString();
        String email_d = s_emaill.getText().toString();
        String password = s_pass.getText().toString();


        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(Setting.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Setting.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        String old_email = publicUserNmae.getU_sername();
        update update_data = new update(firstn, email_d, password, encodimg, old_email, r_listener);

        requestQueue = Volley.newRequestQueue(Setting.this);
        requestQueue.add(update_data);
        Toast.makeText(this, " Data updated", Toast.LENGTH_SHORT).show();
        sweetdailog2();
    }

    void sendImages() {
        Bitmap bitmap = ((BitmapDrawable) s_photo.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        encodimg = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                s_photo.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
                                Intent intent = new Intent(Setting.this, MainActivity.class);
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


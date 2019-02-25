package www.myapp.com.marketing.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.Tabs.Tab3;
import www.myapp.com.marketing.control.itemImageControl;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.customitem.display_Image_Adabter;
import www.myapp.com.marketing.send.addImageToGallary;
import www.myapp.com.marketing.send.sendUsernametoDelete;
import www.myapp.com.marketing.send.send_delete_images;

public class PersonalPostImages extends AppCompatActivity {

    RecyclerView recyclerView_personal;
    RequestQueue requestQueue;
    String username;
    display_Image_Adabter displayimageadabter;
    ArrayList<itemImageControl> arrayList = new ArrayList<itemImageControl>();
    View view_addimage;
    ImageView select_photo;
    Button choose;
    String encoding_gallary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_post_images);

        recyclerView_personal = (RecyclerView) findViewById(R.id.recycle_personal_images);

        view_addimage = LayoutInflater.from(this).inflate(R.layout.addimages, null);
        select_photo = (ImageView) view_addimage.findViewById(R.id.select_phto);

        choose = (Button) view_addimage.findViewById(R.id.choose);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "select picture"), 1);
            }
        });

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        select();
    }


    void select() {

        String url = "http://hassan-elkhadrawy.000webhostapp.com/galary.php?u_username=" + username;


        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            arrayList.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                String name = getData.getString("name");
                                String image = getData.getString("image");

                                arrayList.add(new itemImageControl(name, image));


                            }

                            displayimageadabter = new display_Image_Adabter(PersonalPostImages.this, arrayList);
                            recyclerView_personal.setLayoutManager(new GridLayoutManager(PersonalPostImages.this, 2));
                            recyclerView_personal.setAdapter(displayimageadabter);
                            //galary.setAdapter(new item_images(displayImages.this, android.R.layout.activity_list_item, arrayList));


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {

            addimage();
        } else if (id == R.id.action_delete) {
            deletedailog();
        }


        return super.onOptionsItemSelected(item);
    }


    void addimage() {
        AlertDialog.Builder abuilder = new AlertDialog.Builder(this);
        abuilder.setView(view_addimage);
        abuilder.setCancelable(false);
        abuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendData();
                Intent intent = new Intent(PersonalPostImages.this, MainActivity.class);
                startActivity(intent);
            }
        });
        abuilder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        abuilder.show();
    }


    void sendImages() {
        Bitmap bitmap = ((BitmapDrawable) select_photo.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        encoding_gallary = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


    }

    void sendData() {

        sendImages();

        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(PersonalPostImages.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PersonalPostImages.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        addImageToGallary addImageToGallary = new addImageToGallary(publicUserNmae.getU_sername(), encoding_gallary, r_listener);
        requestQueue = Volley.newRequestQueue(PersonalPostImages.this);
        requestQueue.add(addImageToGallary);


    }


    void deleteImages() {


        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(PersonalPostImages.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(PersonalPostImages.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        send_delete_images send_delete_images = new send_delete_images(publicUserNmae.getU_sername(), r_listener);
        requestQueue = Volley.newRequestQueue(PersonalPostImages.this);
        requestQueue.add(send_delete_images);
        Toast.makeText(PersonalPostImages.this, "Post deleted", Toast.LENGTH_SHORT).show();


    }

    void deletedailog() {
        AlertDialog.Builder abulder = new AlertDialog.Builder(PersonalPostImages.this);
        abulder.setMessage("Do you want to delete your images");
        abulder.setCancelable(false);
        abulder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteImages();
                Intent intent = new Intent(PersonalPostImages.this, MainActivity.class);
                startActivity(intent);

            }
        });
        abulder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        abulder.show();


    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            select_photo.setImageURI(uri);


        }
    }

}

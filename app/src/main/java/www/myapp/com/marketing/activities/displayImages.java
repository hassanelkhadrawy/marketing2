package www.myapp.com.marketing.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.control.itemImageControl;
import www.myapp.com.marketing.customitem.display_Image_Adabter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class displayImages extends AppCompatActivity {
    RecyclerView galary;
    RequestQueue requestQueue;
    String username;
    display_Image_Adabter displayimageadabter;


    ArrayList<itemImageControl> arrayList = new ArrayList<itemImageControl>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_display_images);

        galary = (RecyclerView) findViewById(R.id.recycle_gallary);

        Bundle bundle=getIntent().getExtras();
        username=bundle.getString("username");
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

                            displayimageadabter=new display_Image_Adabter(displayImages.this,arrayList);
                            galary.setLayoutManager(new GridLayoutManager(displayImages.this,2));
                            galary.setAdapter(displayimageadabter);
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


}

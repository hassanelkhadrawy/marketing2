package www.myapp.com.marketing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import www.myapp.com.marketing.R;
import www.myapp.com.marketing.Tabs.Tab1;
import www.myapp.com.marketing.control.Chicking;
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.customitem.adabter;
import www.myapp.com.marketing.customitem.user_adabter;

public class User extends AppCompatActivity {
    ArrayList<item> array = new ArrayList<item>();
    ArrayList<Chicking> arrayChicking = new ArrayList<Chicking>();
    RecyclerView recyclerView;
    String url = "https://hassan-elkhadrawy.000webhostapp.com/post.php";
    String url_chicking = "http://hassan-elkhadrawy.000webhostapp.com/checklogin.php";

    String phone,recive_passwords, recive_emails;
    www.myapp.com.marketing.customitem.user_adabter adabter;
    WaveSwipeRefreshLayout refreshLayout;
    RequestQueue requestQueue;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPrefrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        recyclerView = (RecyclerView) findViewById(R.id.recyclelist);
        refreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.rfreshmain);

        sharedPrefrance = getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sharedPrefrance.edit();
        selectforchicking();
        select();
        refresh();

    }

    void select() {
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            array.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                String name = getData.getString("name");
                                String username = getData.getString("username");
                                String address = getData.getString("address");
                                phone = getData.getString("phone");
                                String image = getData.getString("image");

                                array.add(new item(name, username, address, phone, image));


                            }

                            adabter = new user_adabter(User.this, array);
                            recyclerView.setLayoutManager(new LinearLayoutManager(User.this));
                            recyclerView.setAdapter(adabter);

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


    void refresh() {

        refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            new Taskmain().execute();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });

    }

    public void action(View view) {

        Intent i=new Intent(this,Login.class);
        startActivity(i);
    }

    private class Taskmain extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {

            select();
            refreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }


    }



    void selectforchicking() {
        requestQueue = Volley.newRequestQueue(User.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url_chicking,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                recive_emails = getData.getString("username");

                                recive_passwords = getData.getString("password");

                                arrayChicking.add(new Chicking(recive_emails, recive_passwords));

                            }


                            String email_shared = sharedPrefrance.getString("email", "no data found");
                            String password_shared = sharedPrefrance.getString("password", "no data found");


                            for (int w = 0; w < arrayChicking.size(); w++) {

                                if (password_shared.equals(arrayChicking.get(w).username) && email_shared.equals(arrayChicking.get(w).passwordd)) {

                                    new publicUserNmae(password_shared);
                                    sweetdailog2();

                                    break;

                                } else {
                                    if (w == array.size() - 1) {
                                        Toast.makeText(User.this, "Please enter correct username or password", Toast.LENGTH_SHORT).show();
                                    }


                                }


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
                                Intent intent = new Intent(User.this, MainActivity.class);
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

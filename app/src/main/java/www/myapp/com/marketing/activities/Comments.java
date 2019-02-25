package www.myapp.com.marketing.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import www.myapp.com.marketing.R;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.control.show_comments;
import www.myapp.com.marketing.customitem.commentAdabter;
import www.myapp.com.marketing.send.s_comments;
import www.myapp.com.marketing.send.send_like;

public class Comments extends AppCompatActivity {
    EditText W_comment;
    ImageView send, like;
    public static String phone;
    RecyclerView recyclerViewcomments;
    commentAdabter commentAdabter;
    RequestQueue requestQueue;
    ArrayList<show_comments> show_comments = new ArrayList<>();
    String name, u_username, imag, x, y;
    TextView numComments, numLikes;
//    SwipeRefreshLayout refreshLayout;
    WaveSwipeRefreshLayout  refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        W_comment = (EditText) findViewById(R.id.writecommment);
        send = (ImageView) findViewById(R.id.send);
        recyclerViewcomments = (RecyclerView) findViewById(R.id.recyclelistcomments);
        numComments = (TextView) findViewById(R.id.commentnum1);
        numLikes = (TextView) findViewById(R.id.likenum1);
        refreshLayout= (WaveSwipeRefreshLayout) findViewById(R.id.refresh);
        like = (ImageView) findViewById(R.id.like);
        Bundle b = getIntent().getExtras();
        phone = b.getString("phone");
        likes_num();
        select_comments();
        selectp_image();
        refrsh();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment();
                select_comments();

            }
        });
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendlike();

            }
        });


    }

    void sendComment() {
        if (W_comment.getText().toString().isEmpty()) {

            W_comment.setError("enter comment before send");
        } else {
            String comment = W_comment.getText().toString();
            String username = publicUserNmae.getU_sername();
            W_comment.setText("");


            Response.Listener<String> r_listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            Toast.makeText(Comments.this, "done", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(Comments.this, "failed", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            s_comments s_comments = new s_comments(name, username, phone, comment, imag, r_listener);

            requestQueue = Volley.newRequestQueue(Comments.this);
            requestQueue.add(s_comments);

        }

    }

    void select_comments() {


        String url = "http://hassan-elkhadrawy.000webhostapp.com/showcomments.php?phone=" + phone;

        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            show_comments.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            numComments.setText("" + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);


                                String comment = getData.getString("comment");
                                String name1 = getData.getString("name");
                                String image1 = getData.getString("image");


                                show_comments.add(new show_comments(comment, name1, image1));


                            }
                            commentAdabter = new commentAdabter(Comments.this, show_comments);
                            recyclerViewcomments.setLayoutManager(new LinearLayoutManager(Comments.this));
                            recyclerViewcomments.setAdapter(commentAdabter);

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

    void selectp_image() {
        u_username = publicUserNmae.getU_sername();
        String urlp_image = "http://hassan-elkhadrawy.000webhostapp.com/p_image.php?p_phone=" + u_username;


        requestQueue = Volley.newRequestQueue(Comments.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlp_image,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("customers");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                x = getData.getString("name");
                                y = getData.getString("p_image");

                            }
                            name = x;
                            imag = y;


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

    void sendlike() {

        String username = publicUserNmae.getU_sername();

        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(Comments.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Comments.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        send_like s_like = new send_like(name, username, phone, r_listener);

        like.setBackgroundResource(R.drawable.smalllike);
        likes_num();
        requestQueue = Volley.newRequestQueue(Comments.this);
        requestQueue.add(s_like);



    }

    void likes_num() {


        String url = "https://hassan-elkhadrawy.000webhostapp.com/num_likes.php?phone=" + phone;
        final StringBuilder text = new StringBuilder();
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("customers");
                            numLikes.setText("" + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);


                                String username1 = getData.getString("username");
                                text.append(username1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (text.toString().contains(publicUserNmae.getU_sername()))

                        {
                            like.setBackgroundResource(R.drawable.smalllike);
                            like.setEnabled(false);


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

   void refrsh(){

       refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {

               new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            new Task().execute();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();



           }
       });

  }
    private class Task extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {

            select_comments();
             likes_num();
            refreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }
    }



}



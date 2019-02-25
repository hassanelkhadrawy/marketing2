package www.myapp.com.marketing.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.control.Chicking;

public class Sureing extends AppCompatActivity {
EditText sure_email,sure_password;
    Button ok;
    String email,password;
    RequestQueue requestQueue;
    String url = "http://hassan-elkhadrawy.000webhostapp.com/checklogin.php";
    ArrayList<Chicking> array = new ArrayList<Chicking>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sureing);
    calling();
        handling();
    }
    void calling(){
        sure_email= (EditText) findViewById(R.id.sure_email);
        sure_password= (EditText) findViewById(R.id.sure_password);
        ok= (Button) findViewById(R.id.ok);
    }
    void handling(){
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check();
            }
        });

    }
    void check(){
        select();

        if (sure_email.getText().toString().isEmpty()) {
            sure_email.setError("please enter email");
        } else if (sure_password.getText().toString().isEmpty()) {
            sure_password.setError("please enter password");
        } else {
            String s1 = sure_email.getText().toString();
            String s2 = sure_password.getText().toString();

            for (int x = 0; x < array.size(); x++) {


                if (s2.equals(array.get(x).username) && s1.equals(array.get(x).passwordd)) {


                sweetdailog2();



                    break;
                } else {
                    if (x == array.size() - 1) {
                        Toast.makeText(Sureing.this, "Please enter correct username or password", Toast.LENGTH_SHORT).show();
                    }


                }
            }


        }

    }
    void select() {
        requestQueue = Volley.newRequestQueue(Sureing.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                password = getData.getString("username");

                                email = getData.getString("password");

                                array.add(new Chicking(email, password));

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
                    synchronized (this){
                        wait(2000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                sweetAlertDialog.dismissWithAnimation();
                                Intent intent = new Intent(Sureing.this, Setting.class);
                               intent.putExtra("username",sure_email.getText().toString());
                                startActivity(intent);
                                sure_email.setText("");
                                sure_password.setText("");
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

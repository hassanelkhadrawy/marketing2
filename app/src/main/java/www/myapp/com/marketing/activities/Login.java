package www.myapp.com.marketing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import www.myapp.com.marketing.control.publicUserNmae;

public class Login extends AppCompatActivity {
    Button login;
    EditText email, password;
    TextView create_one;
    RequestQueue requestQueue;
    String check_password, check_email;
    ArrayList<Chicking> array = new ArrayList<Chicking>();
    SharedPreferences.Editor editor;
    SharedPreferences sharedPrefrance;

    String url = "http://hassan-elkhadrawy.000webhostapp.com/checklogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = (Button) findViewById(R.id.login);
        email = (EditText) findViewById(R.id.l_email);
        password = (EditText) findViewById(R.id.l_password);
        create_one = (TextView) findViewById(R.id.create_one);

        sharedPrefrance = getSharedPreferences("file", Context.MODE_PRIVATE);
        editor = sharedPrefrance.edit();




        select();

      loginAction();

    }

    void loginAction() {

        select();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("please enter email");
                } else if (password.getText().toString().isEmpty()) {
                    password.setError("please enter password");
                } else {
                    String s1 = email.getText().toString();
                    String s2 = password.getText().toString();

                    for (int x = 0; x < array.size(); x++) {



                        if (s2.equals(array.get(x).username) && s1.equals(array.get(x).passwordd)) {

                            new publicUserNmae(s1);
                            editor.putString("email", s2);
                            editor.putString("password", s1);
                            editor.apply();

                            sweetdailog2();


                            break;
                        } else {
                            if (x == array.size() - 1) {
                                Toast.makeText(Login.this, "Please enter correct username or password", Toast.LENGTH_SHORT).show();
                            }


                        }
                    }


                }
            }
        });
        create_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });


    }

    void select() {
        requestQueue = Volley.newRequestQueue(Login.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                check_password = getData.getString("username");

                                check_email = getData.getString("password");

                                array.add(new Chicking(check_email, check_password));

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
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                startActivity(intent);
                                email.setText(" ");
                                password.setText(" ");
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






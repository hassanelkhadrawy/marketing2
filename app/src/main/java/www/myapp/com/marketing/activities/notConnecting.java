package www.myapp.com.marketing.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import www.myapp.com.marketing.R;

public class notConnecting extends AppCompatActivity {
    WaveSwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_connecting);
        refreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.refrshconnection);
        check_connection();
        refrsh();
    }

    void check_connection() {
        checkInternet checkInternet = new checkInternet(getApplicationContext());
        Boolean check = checkInternet.isconnectToInternet();
        if (!check) {

            Toast.makeText(this, "please connect the Internet", Toast.LENGTH_SHORT).show();
        } else {


            Intent intent = new Intent(this, User.class);
            startActivity(intent);
            finish();
        }
    }

    void refrsh() {

        refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
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

        @Override
        protected void onPostExecute(String[] result) {

            check_connection();
            refreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }
    }


}

package www.myapp.com.marketing.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import www.myapp.com.marketing.R;

public class about extends AppCompatActivity {
    private String facebookPageID = "Softizone1";
    private String linkedinPageID = "hassan ragab";
    private String facebookUrl = "https://www.facebook.com/Softizone1/?ref=br_rs";

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void action(View view) {
        int id=view.getId();
        if (id==R.id.face){

            intent=new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("fb://facewebmodal/f?href=" + facebookUrl));
            startActivity(intent);
        }else if (id==R.id.linked){


                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData( Uri.parse("https://www.linkedin.com/in/hassan-ragab-33129b140/"));
                intent.setPackage("com.linkedin.android");
                startActivity(intent);

        }


    }
}

package www.myapp.com.marketing.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;
import www.myapp.com.marketing.R;

public class Full_Image_Activity extends AppCompatActivity {

ImageView fullimageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full__image_);
       //fullimageView= (ImageView) findViewById(R.id.full_view);
        PhotoView photoView= (PhotoView) findViewById(R.id.full_view);
       // photoView.setImageResource(R.drawable.a1);
        savedInstanceState=getIntent().getExtras();
        String img=savedInstanceState.getString("full_image");
      Picasso.with(this).load("http://hassan-elkhadrawy.000webhostapp.com/"+img).into(photoView);


    }


}

package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Full_Image_Activity;
import www.myapp.com.marketing.control.itemImageControl;

/**
 * Created by 7aSSan on 3/20/2018.
 */


public class display_Image_Adabter extends RecyclerView.Adapter<display_Image_Adabter.ViewHolder_display> {


    public static class ViewHolder_display extends RecyclerView.ViewHolder {

        ImageView d_image;

        public ViewHolder_display(View itemView) {
            super(itemView);
            d_image = itemView.findViewById(R.id.d_image);
        }
    }
    Context context;
    ArrayList<itemImageControl> arrayList=new ArrayList<>();

    public display_Image_Adabter(Context context, ArrayList<itemImageControl> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder_display onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_image,null);
        return new  ViewHolder_display(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder_display holder, final int position) {

        Picasso.with(context).load("http://hassan-elkhadrawy.000webhostapp.com/"+arrayList.get(position).image+"").into(holder.d_image);
         holder.d_image.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(context,Full_Image_Activity.class);
                 i.putExtra("full_image",  arrayList.get(position).image);
                 context.startActivity(i);
             }
         });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


}



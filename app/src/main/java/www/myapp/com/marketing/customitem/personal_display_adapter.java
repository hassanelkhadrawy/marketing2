package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Comments;
import www.myapp.com.marketing.activities.Full_Image_Activity;
import www.myapp.com.marketing.activities.PersonalPostImages;
import www.myapp.com.marketing.activities.displayImages;
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.control.itemImageControl;

public class personal_display_adapter extends RecyclerView.Adapter<personal_display_adapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView, likecomment;
        TextView address,  phone;
        String url = "http://hassan-elkhadrawy.000webhostapp.com/";


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.addrees);
            phone = itemView.findViewById(R.id.phone);
            imageView = itemView.findViewById(R.id.img_item);
            likecomment = itemView.findViewById(R.id.likeandcomment);


        }
    }


    Context context;
    ArrayList<item> listitem;

    public personal_display_adapter(Context context, ArrayList<item> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public personal_display_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemlist, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final personal_display_adapter.ViewHolder holder, final int position) {
        new Comments();
        holder.name.setText(listitem.get(position).name);
        holder.phone.setText(listitem.get(position).phone);
        holder.address.setText(listitem.get(position).addrees);
        Picasso.with(context).load(holder.url + listitem.get(position).image).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PersonalPostImages.class);
                i.putExtra("username", listitem.get(position).username);
                context.startActivity(i);
            }
        });

        holder.likecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Comments.class);
                intent.putExtra("phone", listitem.get(position).phone);
                context.startActivity(intent);
            }
        });




    }



    @Override
    public int getItemCount() {

        return listitem.size();
    }



}

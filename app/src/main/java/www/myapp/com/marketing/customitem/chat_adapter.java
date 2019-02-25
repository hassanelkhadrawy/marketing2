package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Comments;
import www.myapp.com.marketing.activities.displayImages;
import www.myapp.com.marketing.control.chat_item;
import www.myapp.com.marketing.control.item;

/**
 * Created by 7aSSan on 3/8/2018.
 */

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView  chat_name;
        ImageView chat_image;
        String url = "http://hassan-elkhadrawy.000webhostapp.com/";


        public ViewHolder(View itemView) {
            super(itemView);
            chat_name = itemView.findViewById(R.id.chat_name);

            chat_image = itemView.findViewById(R.id.chat_image);


        }
    }


    Context context;
    ArrayList<chat_item> listitem = new ArrayList<>();

    public chat_adapter(Context context, ArrayList<chat_item> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public chat_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_chating, null);
        return new chat_adapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final chat_adapter.ViewHolder holder, final int position) {
        new Comments();
        holder.chat_name.setText(listitem.get(position).name);

        Picasso.with(context).load(holder.url + listitem.get(position).image).into(holder.chat_image);



    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }


}

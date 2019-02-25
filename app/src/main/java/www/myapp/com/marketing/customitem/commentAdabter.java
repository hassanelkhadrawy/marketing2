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
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.control.show_comments;

/**
 * Created by 7aSSan on 3/1/2018.
 */

public class commentAdabter extends RecyclerView.Adapter<commentAdabter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView uname,commenttext;
        ImageView u_image;
        String url = "http://hassan-elkhadrawy.000webhostapp.com/";


        public ViewHolder(View itemView) {
            super(itemView);
            uname = itemView.findViewById(R.id.show_name);
            commenttext = itemView.findViewById(R.id.show_comments);
            u_image=itemView.findViewById(R.id.commentimage);


        }
    }


    Context context;
    ArrayList<show_comments> listitem = new ArrayList<>();

    public commentAdabter(Context context, ArrayList<show_comments> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public commentAdabter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_item, null);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.uname.setText(listitem.get(position).name);
        holder.commenttext.setText(listitem.get(position).comment);

        Picasso.with(context).load(holder.url + listitem.get(position).image).into(holder.u_image);

    }


    @Override
    public int getItemCount() {
        return listitem.size();
    }


}

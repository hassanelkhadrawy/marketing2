package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Login;
import www.myapp.com.marketing.activities.displayImages;
import www.myapp.com.marketing.control.item;

/**
 * Created by 7aSSan on 4/20/2018.
 */

public class user_adabter extends RecyclerView.Adapter<user_adabter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageView, likecomment;
        TextView address, phone;
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
    ArrayList<item> listitem = new ArrayList<>();

    public user_adabter(Context context, ArrayList<item> listitem) {
        this.context = context;
        this.listitem = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.itemlist, null);
        return new user_adabter.ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        holder.name.setText(listitem.get(position).name);
        holder.phone.setText(listitem.get(position).phone);
        holder.address.setText(listitem.get(position).addrees);
        Picasso.with(context).load(holder.url + listitem.get(position).image).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, displayImages.class);
                i.putExtra("username", listitem.get(position).username);
                context.startActivity(i);
            }
        });

        holder.likecomment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Login First")
                        .setCancelText("Cancel")
                        .setConfirmText("Login")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {

                        Intent i=new Intent(context, Login.class);
                        context.startActivity(i);
                        sweetAlertDialog.cancel();
                    }
                })

                        .show();




            }
        });


    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }


}

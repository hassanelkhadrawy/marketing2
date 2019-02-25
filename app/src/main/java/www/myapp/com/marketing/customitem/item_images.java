package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Full_Image_Activity;
import www.myapp.com.marketing.control.itemImageControl;

/**
 * Created by Hassan_Abo_Ali on 13/02/2018.
 */

public class item_images extends BaseAdapter {

    Context context;
    ArrayList<itemImageControl> arrayList=new ArrayList<itemImageControl>();

    public item_images(Context context, int simple_list_item_1, ArrayList<itemImageControl> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.item_image,null);
        ImageView d_iamge=(ImageView)view.findViewById(R.id.d_image);
        TextView d_text=(TextView)view.findViewById(R.id.d_name);

        Picasso.with(context).load("http://hassan-elkhadrawy.000webhostapp.com/"+arrayList.get(i).image+"").into(d_iamge);
        d_text.setText(arrayList.get(i).name);

        return view;

    }
}

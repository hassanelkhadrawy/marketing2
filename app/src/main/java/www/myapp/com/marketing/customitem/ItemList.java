package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Comments;
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.control.num_comments;
import www.myapp.com.marketing.control.show_comments;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by Hassan_Abo_Ali on 10/02/2018.
 */

public class ItemList {}
// extends BaseAdapter {
//    Context context;
//    ArrayList<item> arrayList = new ArrayList<item>();
//    public static TextView phone;
//    ArrayList<num_comments> num_comm = new ArrayList<>();
//    String getcomment;
//    String U_Phone;
//    RequestQueue requestQueue;
//    String url = "http://hassan-elkhadrawy.000webhostapp.com/";
//
//    public ItemList(Context context, int simple_list_item_1, ArrayList<item> arrayList) {
//        this.context = context;
//        this.arrayList = arrayList;
//    }
//
//    @Override
//    public int getCount() {
//        return arrayList.size();
//    }
//
//    @Override
//    public Object getItem(int i) {
//        return i;
//    }
//
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//
//    @Override
//    public View getView(final int i, View view, ViewGroup viewGroup) {
//        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        view = inflater.inflate(R.layout.itemlist, null);
//        TextView name = (TextView) view.findViewById(R.id.name);
//        TextView address = (TextView) view.findViewById(R.id.addrees);
//        phone = (TextView) view.findViewById(R.id.phone);
//        ImageView imageView = (ImageView) view.findViewById(R.id.img_item);
//        ImageView like = (ImageView) view.findViewById(R.id.like);
//        ImageView comment = (ImageView) view.findViewById(R.id.comment);
//        TextView likenum = (TextView) view.findViewById(R.id.likenum);
//       TextView commentnum = (TextView) view.findViewById(R.id.commentnum);
//
//        name.setText(arrayList.get(i).name);
//        address.setText(arrayList.get(i).addrees);
//        phone.setText(arrayList.get(i).phone);
//        Picasso.with(context).load(url + arrayList.get(i).image).into(imageView);
//        like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, "liiiiiiiiike", Toast.LENGTH_SHORT).show();
//            }
//        });
//        comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, Comments.class);
//                intent.putExtra("phone", arrayList.get(i).phone);
//                startActivity(context, intent, null);
//
//            }
//        });
//        U_Phone=Comments.test;
//        commentnum.setText(U_Phone);
//
//        return view;
//    }



//}

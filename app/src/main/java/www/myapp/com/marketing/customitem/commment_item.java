package www.myapp.com.marketing.customitem;

import android.content.Context;
import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Comments;
import www.myapp.com.marketing.control.show_comments;

/**
 * Created by Hassan_Abo_Ali on 24/02/2018.
 */

public class commment_item extends BaseAdapter {
    Context context;
            ArrayList<show_comments>show_comments=new ArrayList<>();

    public commment_item(Context context, int activity_list_item, ArrayList<www.myapp.com.marketing.control.show_comments> show_comments) {
        this.context = context;
        this.show_comments = show_comments;
    }

    @Override
    public int getCount() {
        return show_comments.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=LayoutInflater.from(context).inflate(R.layout.comment_item,null);
        TextView comment_txt=(TextView)view.findViewById(R.id.show_comments);
        comment_txt.setText(show_comments.get(i).comment);

        return view;
    }
}

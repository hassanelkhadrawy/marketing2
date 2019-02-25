package www.myapp.com.marketing.send;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class Post_Data extends StringRequest {

    private static final String send_uel="https://hassan-elkhadrawy.000webhostapp.com/insert_post.php";
    private Map<String,String> m_array;

    public Post_Data(String name,String username,String address,String phone,String image, Response.Listener<String> listener) {
        super(Request.Method.POST, send_uel, listener,null);
        m_array=new HashMap<>();
        m_array.put("name",name);
        m_array.put("username",username);
        m_array.put("address",address);
        m_array.put("phone",phone);
        m_array.put("image",image);

    }

    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}

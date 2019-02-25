package www.myapp.com.marketing.send;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 7aSSan on 3/9/2018.
 */

public class send_like extends StringRequest {
  private static final   String url="https://hassan-elkhadrawy.000webhostapp.com/likes.php";
    private Map<String,String> m_array;
    public send_like(String name,String username,String phone, Response.Listener<String> listener) {
        super(Method.POST, url, listener, null);
        m_array=new HashMap<>();
        m_array.put("name",name);
        m_array.put("username",username);
        m_array.put("phone",phone);
    }
    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}


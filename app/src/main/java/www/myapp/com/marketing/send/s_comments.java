package www.myapp.com.marketing.send;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hassan_Abo_Ali on 24/02/2018.
 */

public class s_comments extends StringRequest {

    private static final String send_uel = "http://hassan-elkhadrawy.000webhostapp.com/comments.php";
    private Map<String, String> m_array;

    public s_comments(String name,String username, String phone, String comment,String image, Response.Listener<String> listener) {
        super(Request.Method.POST, send_uel, listener, null);
        m_array = new HashMap<>();
        m_array.put("name",name);
        m_array.put("username", username);
        m_array.put("phone", phone);
        m_array.put("comment", comment);
        m_array.put("image",image);

    }

    @Override
    public Map<String, String> getParams() {
        return m_array;
    }

}

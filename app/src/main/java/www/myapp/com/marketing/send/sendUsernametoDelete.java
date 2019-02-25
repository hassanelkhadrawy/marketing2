package www.myapp.com.marketing.send;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 7aSSan on 3/2/2018.
 */

public class sendUsernametoDelete extends StringRequest {
    private static final String send_uel="http://hassan-elkhadrawy.000webhostapp.com/delete_post.php";
    private Map<String,String> m_array;

    public sendUsernametoDelete(String username, Response.Listener<String> listener) {
        super(Method.POST, send_uel, listener,null);
        m_array=new HashMap<>();
        m_array.put("username",username);

    }

    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}

package www.myapp.com.marketing.send;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class send_delete_images extends StringRequest {
    private static final String send_uel="http://hassan-elkhadrawy.000webhostapp.com/delete.php";
    private Map<String,String> m_array;

    public send_delete_images(String username, Response.Listener<String> listener) {
        super(Method.POST, send_uel, listener,null);
        m_array=new HashMap<>();
        m_array.put("username",username);

    }

    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}

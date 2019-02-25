package www.myapp.com.marketing.send;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hassan_Abo_Ali on 19/02/2018.
 */

public class sendPhonetop_image extends StringRequest {
    private static final String send_uel="http://hassan-elkhadrawy.000webhostapp.com/p_image.php";
    private Map<String,String> m_array;

    public sendPhonetop_image(String p_phone, Response.Listener<String> listener) {
        super(Request.Method.POST, send_uel, listener,null);
        m_array=new HashMap<>();
        m_array.put("p_phone",p_phone);

    }

    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}

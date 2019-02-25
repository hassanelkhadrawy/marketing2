package www.myapp.com.marketing.send;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hassan_Abo_Ali on 08/02/2018.
 */

public class Send_data_registed extends StringRequest {
    private static final String send_uel="http://hassan-elkhadrawy.000webhostapp.com/addacuntinfo.php";
    private Map<String,String>m_array;

    public Send_data_registed(String uname ,String uemail,String upassword,String p_image, Response.Listener<String> listener) {
        super(Method.POST, send_uel, listener,null);
        m_array=new HashMap<>();
        m_array.put("uname",uname);
        m_array.put("uemail",uemail);
        m_array.put("upassword",upassword);
        m_array.put("p_image",p_image);
    }

    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}

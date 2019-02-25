package www.myapp.com.marketing.send;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import www.myapp.com.marketing.control.publicUserNmae;

/**
 * Created by 7aSSan on 3/8/2018.
 */

public class update extends StringRequest {

    private static final String update_uel="http://hassan-elkhadrawy.000webhostapp.com/update.php";
    private Map<String,String> m_array;


    public update(String uname ,String uemail,String upassword, String p_image,String old_email, Response.Listener<String> listener) {
        super(Method.POST, update_uel, listener, null);
        m_array=new HashMap<>();
        m_array.put("uname",uname);
        m_array.put("uemail",uemail);
        m_array.put("upassword",upassword);
//        m_array.put("uphone",uphone);
//        m_array.put("uaddress",uaddress);
        m_array.put("p_image",p_image);
        m_array.put("old_email",old_email);
        publicUserNmae.setU_sername(uemail);
    }
    @Override
    public Map<String, String> getParams()  {
        return m_array;
    }
}


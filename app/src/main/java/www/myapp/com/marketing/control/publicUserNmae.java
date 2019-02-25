package www.myapp.com.marketing.control;

/**
 * Created by Hassan_Abo_Ali on 19/02/2018.
 */

public class publicUserNmae {
   public static String u_sername;

    public publicUserNmae(String u_username) {
        this.u_sername = u_username;
    }

    public static String getU_sername() {
        return u_sername;
    }

    public static void setU_sername(String u_sername) {
        publicUserNmae.u_sername = u_sername;
    }
}

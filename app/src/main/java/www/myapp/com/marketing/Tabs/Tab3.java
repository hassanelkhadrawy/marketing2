package www.myapp.com.marketing.Tabs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import www.myapp.com.marketing.R;
import www.myapp.com.marketing.activities.Login;
import www.myapp.com.marketing.activities.MainActivity;
import www.myapp.com.marketing.control.Chicking;
import www.myapp.com.marketing.control.publicUserNmae;
import www.myapp.com.marketing.customitem.adabter;
import www.myapp.com.marketing.control.item;
import www.myapp.com.marketing.customitem.personal_display_adapter;
import www.myapp.com.marketing.send.Post_Data;
import www.myapp.com.marketing.send.addImageToGallary;
import www.myapp.com.marketing.send.sendUsernametoDelete;

import static android.app.Activity.RESULT_OK;


public class Tab3 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    ListView listv_profile;
    RecyclerView recyclerView_profile;
    RequestQueue requestQueue;
     String name, u_username, imag;
    ImageView profile;
    TextView user_name;
    ArrayList<item> array = new ArrayList<item>();
    ArrayList<Chicking> arraytest = new ArrayList<Chicking>();
    FloatingActionMenu floatingActionMenu;
    com.github.clans.fab.FloatingActionButton add, delete;
    View view_addimage;
    View v_post;
    EditText editText_name,editText_address,editText_phone;

    ImageView select_photo,post_image;
    Button choose,create;
    String encoding_gallary,encoding_p_image;
    static WaveSwipeRefreshLayout refreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Tab3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Tab3.
     */
    // TODO: Rename and change types and number of parameters
    public static Tab3 newInstance(String param1, String param2) {
        Tab3 fragment = new Tab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v_profile = inflater.inflate(R.layout.fragment_tab3, container, false);

        profile = (ImageView) v_profile.findViewById(R.id.img_profile);
        user_name = (TextView) v_profile.findViewById(R.id.user_name);
        create=v_profile.findViewById(R.id.create_post);
        recyclerView_profile = (RecyclerView) v_profile.findViewById(R.id.recyclelistprofile);
        u_username = publicUserNmae.getU_sername();
        view_addimage = LayoutInflater.from(getActivity()).inflate(R.layout.addimages, null);
        select_photo = (ImageView) view_addimage.findViewById(R.id.select_phto);
        refreshLayout = (WaveSwipeRefreshLayout) v_profile.findViewById(R.id.profile_refresh);

        choose = (Button) view_addimage.findViewById(R.id.choose);

                floatingActionMenu = (FloatingActionMenu) v_profile.findViewById(R.id.floatingMenu);
//        add = (com.github.clans.fab.FloatingActionButton) v_profile.findViewById(R.id.floatingbtnAdd);
        delete = (com.github.clans.fab.FloatingActionButton)v_profile.findViewById(R.id.floatingbtndelete);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletedailog();

            }
        });

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "select picture"), 1);
            }
        });






        select();
        selectp_image();
        refresh();

        create.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           create_Post();
       }

   });


        return v_profile;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    void selectp_image() {
        u_username = publicUserNmae.getU_sername();
        String urlp_image = "http://hassan-elkhadrawy.000webhostapp.com/p_image.php?p_phone=" + u_username;


        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlp_image,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            array.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                name = getData.getString("name");
                                imag = getData.getString("p_image");

                            }

                            Picasso.with(getActivity()).load("http://hassan-elkhadrawy.000webhostapp.com/" + imag + "").into(profile);
                            user_name.setText(name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

     void select() {

        String url = "https://hassan-elkhadrawy.000webhostapp.com/selectP_info.php?username=" +publicUserNmae.getU_sername() ;

        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            array.clear();
                            JSONArray jsonArray = response.getJSONArray("customers");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);

                                String name1 = getData.getString("name");
                                String username1 = getData.getString("username");
                                String address1 = getData.getString("address");
                                String phone1 = getData.getString("phone");
                                String image1 = getData.getString("image");
                                array.add(new item(name1, username1, address1, phone1, image1));


                            }
                            personal_display_adapter adabter = new personal_display_adapter(getActivity(),array);
                            recyclerView_profile.setLayoutManager(new LinearLayoutManager(getActivity()));
                            recyclerView_profile.setAdapter(adabter);

                            for (int x = 0; x < array.size(); x++) {

                                if (publicUserNmae.getU_sername().equals(array.get(x).username)) {
                                    create.setEnabled(false);
                                    create.setHint("already you created post");

                                    break;
                                }


                            }







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    void deletePost() {


        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        sendUsernametoDelete sendAdresstoDelete = new sendUsernametoDelete(publicUserNmae.getU_sername(), r_listener);
        requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(sendAdresstoDelete);
        Toast.makeText(getActivity(), "Post deleted", Toast.LENGTH_SHORT).show();



    }
    void deletedailog() {
        AlertDialog.Builder abulder = new AlertDialog.Builder(getActivity());
        abulder.setMessage("Do you want to delete your Post");
        abulder.setCancelable(false);
        abulder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deletePost();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);

            }
        });
        abulder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        abulder.show();


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            select_photo.setImageURI(uri);


        }else if (requestCode == 2 && resultCode == RESULT_OK && data != null && data.getData() != null){

            Uri uri=data.getData();
            post_image.setImageURI(uri);
        }
    }

    public void create_Post(){


        v_post=LayoutInflater.from(getActivity()).inflate(R.layout.create_post_dialog,null);
        editText_name=v_post.findViewById(R.id.post_name);
        editText_address=v_post.findViewById(R.id.post_address);
        editText_phone=v_post.findViewById(R.id.post_phone);
        post_image=v_post.findViewById(R.id.post_image);



        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "select picture"), 2);
            }
        });

        AlertDialog.Builder abuilder=new AlertDialog.Builder(getActivity());
        abuilder.setView(v_post);

        abuilder.setCancelable(false);
        abuilder.setPositiveButton("create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                  Send_Data_Post();

                dialogInterface.dismiss();

            }
        });
        abuilder.setNegativeButton("cansle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "cancled", Toast.LENGTH_SHORT).show();
            }
        });
        abuilder.show();


    }
    void  Send_Data_Post (){
        String p_name=editText_name.getText().toString();
        String p_username=publicUserNmae.getU_sername();
        String p_address=editText_address.getText().toString();
        String p_phone=editText_phone.getText().toString();


    Bitmap bitmap = ((BitmapDrawable) post_image.getDrawable()).getBitmap();
    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
    encoding_p_image = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);





    Response.Listener<String> r_listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {

                JSONObject jsonObject = new JSONObject(response);
                boolean success = jsonObject.getBoolean("success");
                if (success) {
                    Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
   Post_Data post_data=new Post_Data(p_name,p_username,p_address,p_phone,encoding_p_image,r_listener);
    requestQueue = Volley.newRequestQueue(getActivity());
    requestQueue.add(post_data);
}



    void refresh() {

        refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            new Tab3.Taskmain().execute();

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });

    }

    public class Taskmain extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            return new String[0];
        }

        @Override
        protected void onPostExecute(String[] result) {

            selectp_image();
            select();
            refreshLayout.setRefreshing(false);

            super.onPostExecute(result);
        }


    }



}
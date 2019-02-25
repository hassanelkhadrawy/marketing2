package www.myapp.com.marketing.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import www.myapp.com.marketing.R;
import www.myapp.com.marketing.Tabs.PagerAdapter;
import www.myapp.com.marketing.Tabs.Tab1;
import www.myapp.com.marketing.Tabs.Tab2;
import www.myapp.com.marketing.Tabs.Tab3;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Tab1.OnFragmentInteractionListener, Tab2.OnFragmentInteractionListener, Tab3.OnFragmentInteractionListener {

    ImageView select_photo, u_profile;

    TextView txtname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // select_photo = (ImageView) findViewById(R.id.test_image);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//        u_profile = (ImageView) findViewById(R.id.u_profile);
//        txtname = (TextView) findViewById(R.id.uuu);



        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_chat_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_person_black_24dp));
        tabLayout.setBackgroundColor(Color.green(1));
        tabLayout.setBackgroundResource(R.drawable.backdround);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewpager = (ViewPager) findViewById(R.id.viewpager);

        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewpager.setAdapter(adapter);
        viewpager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setSelectedTabIndicatorColor(Color.GRAY);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         if (id == R.id.setting) {
            Intent i = new Intent(MainActivity.this, Sureing.class);
            startActivity(i);

        } else if (id == R.id.nav_share) {


            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("mailto:"));
            intent.setType("*/*");

            startActivity(intent);


        } else if (id == R.id.about) {
            Intent i=new Intent(this,about.class);
            startActivity(i);

        } else if (id == R.id.logout) {
            Intent i = new Intent(MainActivity.this, Login.class);
            SharedPreferences preferences=getSharedPreferences("file", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("email"," ");
            editor.putString("password"," ");
            editor.apply();

            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }






}

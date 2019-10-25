package com.example.sportshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.sportshop.Activities.AboutUsActivity;
import com.example.sportshop.Activities.CustomerServiceActivity;
import com.example.sportshop.Activities.FavoritesActivity;
import com.example.sportshop.Activities.MyOrdersActivity;
import com.example.sportshop.Activities.SettingsActivity;
import com.example.sportshop.Activities.ShoppingCartActivity;
import com.example.sportshop.Adapters.MyFragmentPagerAdapter;
import com.example.sportshop.Data.Product;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ViewPager.OnPageChangeListener{

    private static long back_pressed;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        pager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), this);
        pager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(android.support.design.widget.TabLayout.MODE_SCROLLABLE);// Make TabLayout scrollable
        pager.addOnPageChangeListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //toggle.getToolbarNavigationClickListener().onClick();
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        }

        back_pressed = System.currentTimeMillis();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (id){
            case R.id.nav_home:

                break;
            case R.id.nav_cart:
                Intent cart = new Intent(this, ShoppingCartActivity.class);
                //startActivity(cart);
                startActivity(cart);
                //overridePendingTransition(R.transition.activity_tranition_anim_on,R.transition.activity_tranition_anim_off);
                finish();
                break;
            case R.id.nav_order:
                Intent order = new Intent(this, MyOrdersActivity.class);
                startActivity(order);
                finish();
                break;
            case R.id.nav_favorite:
                Intent favorite = new Intent(this, FavoritesActivity.class);
                startActivity(favorite);
                finish();
                break;
            case R.id.nav_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                finish();
                break;
            case R.id.nav_service:
                Intent service = new Intent(this, CustomerServiceActivity.class);
                startActivity(service);
                finish();
                break;
            case R.id.nav_info:
                Intent info = new Intent(this, AboutUsActivity.class);
                startActivity(info);
                finish();
                break;
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
    }
    @Override
    public void onPageSelected(int position) {
        //Log.d("myLog", "onPageSelected, position = " + position);
    }
    @Override
    public void onPageScrollStateChanged(int i) {
    }


}
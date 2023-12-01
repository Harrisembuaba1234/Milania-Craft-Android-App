package com.example.milaniacraft.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.milaniacraft.API.NetworkChangeListener;
import com.example.milaniacraft.Adapter.AdapterFragment;
import com.example.milaniacraft.Fragment.FavoritFragment;
import com.example.milaniacraft.Fragment.HomeFragment;
import com.example.milaniacraft.Fragment.NotifyFragment;
import com.example.milaniacraft.Fragment.ProfileFragment;
import com.example.milaniacraft.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class BottomNavigation extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HomeFragment home_fragment;
    private FavoritFragment favorit_fragment;
    private NotifyFragment notif_fragment;
    private ProfileFragment profile_fragment;
    NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        notif(BottomNavigation.this);
        switchMenu();
        
    }

    public void switchMenu(){
        tabLayout = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);

        AdapterFragment swtch = new AdapterFragment(getSupportFragmentManager(), 0);
        swtch.addFragment(new HomeFragment(),"");
        swtch.addFragment(new FavoritFragment(),"");
        swtch.addFragment(new NotifyFragment(),"");
        swtch.addFragment(new ProfileFragment(),"");
        viewPager.setAdapter(swtch);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.heart);
        tabLayout.getTabAt(2).setIcon(R.drawable.bell);
        tabLayout.getTabAt(3).setIcon(R.drawable.user);

    }
    public void notif(Activity activity){
        //change color notif bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(this.getResources().getColor(R.color.coklat));
        //set icons notifbar
        View decor = activity.getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}
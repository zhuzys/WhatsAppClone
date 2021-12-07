package com.example.whatsapp.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.whatsapp.R;
import com.example.whatsapp.TabsAccessorAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ViewPager viewPager;
    private TabLayout  myTabLayout;
    private TabsAccessorAdapter tabsAccessorAdapter;
    private FirebaseAuth mAuth;

   private FirebaseUser currentUser ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("WhatsApp");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        viewPager = findViewById(R.id.viewPager);
        tabsAccessorAdapter = new TabsAccessorAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tabsAccessorAdapter);

        myTabLayout = findViewById(R.id.tabLayout);
        myTabLayout.setupWithViewPager(viewPager);
        }

    @Override
    protected void onStart() {
        super.onStart();

        if (currentUser == null) {
            SendUserToLoginActivity();
        }
    }
    private void SendUserToLoginActivity () {
        Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginActivity);

    }
//Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId() == R.id.main_logout_option) {
             mAuth.signOut();
             SendUserToLoginActivity();

         }
        if(item.getItemId() == R.id.settings) {



        }
        if(item.getItemId() == R.id.main_logout_option) {

        }
       return  true;
    }
}

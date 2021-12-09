package com.example.projecttp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.projecttp.R;
import com.example.projecttp.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    Fragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        setContentView(R.layout.fragment_home);
        homeFragment = new HomeFragment();
        loadFragment(homeFragment);



    }

    private void loadFragment(Fragment homeFragment) {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.slider, homeFragment,homeFragment.getTag()).commit();;
    }

}
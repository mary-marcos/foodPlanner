package com.example.foodplanner.view.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.SplashScreenFragment;

public class MainActivity extends AppCompatActivity {
    Fragment splashFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        splashFragment=new SplashScreenFragment();
       // hideActionBar();
        FragmentNavigator.addFragment(splashFragment,this,R.id.fragment_container);

    }
}
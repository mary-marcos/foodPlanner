package com.example.foodplanner.view.activity;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.SplashScreenFragment;
import com.example.foodplanner.view.auth.login.LoginFragment;

public class MainActivity extends AppCompatActivity {
    Fragment splashFragment;
    Fragment login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        splashFragment=new SplashScreenFragment();
        login=new LoginFragment();
        boolean navigateToLogin = getIntent().getBooleanExtra("navigateToLogin", false);
       // hideActionBar();
        if (navigateToLogin) {
            // Navigate to LoginFragment
            FragmentNavigator.addFragment(login, this, R.id.fragment_container);
        }
        else{
        FragmentNavigator.addFragment(splashFragment,this,R.id.fragment_container);
        }

    }
}
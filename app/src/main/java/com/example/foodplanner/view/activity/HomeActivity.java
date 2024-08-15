package com.example.foodplanner.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.foodplanner.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.db.remoteDB.DBHelper;
import com.example.foodplanner.view.auth.login.LoginFragment;
import com.example.foodplanner.view.meal_details.MealDetailsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity{
    NavController navController;
    BottomNavigationView bottomNavigationView;
   public static boolean  isGuestMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            Toast.makeText(this, "load to room", Toast.LENGTH_SHORT).show();
            new DBHelper().getAllFavorite(this);
            new DBHelper().getAllWeelPlan(this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=findViewById(R.id.bottom_navigation);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("guestMode")) {
            isGuestMode = extras.getBoolean("guestMode", false);
            if (isGuestMode==true){
            Toast.makeText(this, "is guest true", Toast.LENGTH_SHORT).show();}

        }

        initViews();


    }

    private void initViews() {
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();

    }

    public  void showGuestModeAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Guest Mode");
        builder.setMessage("You need to sign in to perform this action. Do you want to sign in?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeActivity.this, LoginFragment .class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);

    }
}
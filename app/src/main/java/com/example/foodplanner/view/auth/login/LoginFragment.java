package com.example.foodplanner.view.auth.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.activity.HomeActivity;
import com.example.foodplanner.view.auth.register.RegisterFreagment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment
        //implements LoginView
{

    Button signInBtn;
    EditText ediemail, edipassword;
    ImageView googleImg;
    TextView haveAccountText,guestTv;
  //  LoginPresenter loginPresenter;
    ProgressDialog progressDialog;
    TextInputLayout inputLayoutEmail,inputLayoutPass;
    FirebaseAuth mAuth;
   // private GoogleSignInClient googleSignInClient;
   @Override
   public void onStart() {
       super.onStart();
       // Check if user is signed in (non-null) and update UI accordingly.
       FirebaseUser currentUser = mAuth.getCurrentUser();
       if(currentUser != null){
           startHomeActivity();

       }
   }
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        signInBtn = view.findViewById(R.id.sign_in_btn);
        ediemail= view.findViewById(R.id.email_login);
        edipassword=view.findViewById(R.id.pass_login);
        haveAccountText=view.findViewById(R.id.dohaveacc);


        haveAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginFragment();
            }
        });

        signInBtn.setOnClickListener(view1 ->
        {
            String email=ediemail.getText().toString();
            String password=edipassword.getText().toString();
            if(TextUtils.isEmpty(email)){
                Toast.makeText(requireContext(),"please enter email",Toast.LENGTH_SHORT).show();
                return;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(requireContext(),"please enter password",Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginFragment.this.getContext(), "done.",
                                        Toast.LENGTH_SHORT).show();
                                startHomeActivity();
                                //  FirebaseUser user = mAuth.getCurrentUser();
                                // updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                // Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginFragment.this.getContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                // updateUI(null);
                            }

                        }
                    });



        });

    }

    private void startHomeActivity() {
        Intent intent = new Intent(this.getContext(), HomeActivity.class);
        startActivity(intent);
    }

    private void navigateToLoginFragment() {
        FragmentNavigator.addFragment(new RegisterFreagment(),this.requireActivity(),R.id.fragment_container);
    }



}








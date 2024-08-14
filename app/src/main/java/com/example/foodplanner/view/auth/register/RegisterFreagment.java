package com.example.foodplanner.view.auth.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodplanner.R;
import com.example.foodplanner.view.FragmentNavigator;
import com.example.foodplanner.view.auth.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterFreagment extends Fragment {

    Button signUpBtn;
    EditText editemail,editpassword,confirmPass;
    TextView haveAccountText;
    TextInputLayout inputLayoutEmail,inputLayoutPass,inputLayoutConfirmPass;
FirebaseAuth mAuth;

    public RegisterFreagment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_freagment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
mAuth=FirebaseAuth.getInstance();
        haveAccountText=view.findViewById(R.id.have_account_tv);
        signUpBtn=view.findViewById(R.id.signup_btn);
        editemail=view.findViewById(R.id.email_et);
        editpassword=view.findViewById(R.id.pass_et);

        //confirmPass;
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=editemail.getText().toString();
                String password=editpassword.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(requireContext(),"please enter email",Toast.LENGTH_SHORT).show();
                return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(requireContext(),"please enter password",Toast.LENGTH_SHORT).show();
               return;
                }
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(RegisterFreagment.this.getContext(), "Account Created.",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Toast.makeText(RegisterFreagment.this.getContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //  updateUI(null);
                                }
                            }




                        });



            }


        });


        haveAccountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToLoginFragment();
            }
        });
    }

    private void navigateToLoginFragment() {
        FragmentNavigator.addFragment(new LoginFragment(),this.requireActivity(),R.id.fragment_container);
    }

}
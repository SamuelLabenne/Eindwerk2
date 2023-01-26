package be.ehb.eindwerk2.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import be.ehb.eindwerk2.R;
import be.ehb.eindwerk2.databinding.FragmentLoginBinding;
import be.ehb.eindwerk2.util.Utility;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    EditText emailET,passwordET;
    Button loginBtn;
    ProgressBar pb;
    TextView createAccBtnTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater,container,false);

        emailET = binding.emailEt;
        passwordET = binding.passwordEt;
        loginBtn = binding.btnLogin;
        pb = binding.pB;
        createAccBtnTV = binding.createAccTV;

        loginBtn.setOnClickListener(v -> loginUser());

        return binding.getRoot();
    }

    void loginUser(){

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();


        boolean validated = validateData(email, password);
        if (!validated) {
            return;
        }
        loginFirebase(email,password);

    }

    void loginFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        changeInProgress(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "logged in", Toast.LENGTH_SHORT).show();
                    NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_overviewFragment);

                }else{
                    Toast.makeText(getActivity(), task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    void changeInProgress(boolean inProgress ){
        if(inProgress){
            pb.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
        }else{
            pb.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }

    }

    boolean validateData(String email, String password){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Email invalid");
            return false;
        }
        if (password.length()<5){
            passwordET.setError("Password has to be > 5 char");
            return false;
        }

        return true;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {   super.onViewCreated(view, savedInstanceState);
        binding.createAccTV.setOnClickListener((View v) -> {
            NavHostFragment.findNavController(LoginFragment.this).navigate(R.id.action_loginFragment_to_createAccountFragment);
        });
    }
}

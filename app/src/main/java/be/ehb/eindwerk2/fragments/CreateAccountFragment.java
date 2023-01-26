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
import be.ehb.eindwerk2.databinding.ActivityCreateAccountBinding;


public class CreateAccountFragment extends Fragment {
    private ActivityCreateAccountBinding binding;

    EditText emailET,passwordET, confirmPassET;
    Button createAccBtn;
    ProgressBar pb;
    TextView loginBtnTV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = ActivityCreateAccountBinding.inflate(inflater,container,false);

        emailET = binding.emailEt;
        passwordET = binding.passwordEt;
        confirmPassET = binding.confirmPasswordEt;
        createAccBtn = binding.btnSignUp;
        pb = binding.pB;
        loginBtnTV = binding.loginTV;

        createAccBtn.setOnClickListener(v -> createAccount());
        loginBtnTV.setOnClickListener(v->onDestroyView());

        return binding.getRoot();
    }

    void createAccount() {

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();
        String confirmPass = confirmPassET.getText().toString();

        boolean validated = validateData(email, password, confirmPass);
        if (!validated) {
            return;
        }
        createAccountFirebase(email, password);
    }



    void changeInProgress(boolean inProgress ){
        if(inProgress){
            pb.setVisibility(View.VISIBLE);
            createAccBtn.setVisibility(View.GONE);
        }else{
            pb.setVisibility(View.GONE);
            createAccBtn.setVisibility(View.VISIBLE);
        }

    }

    void createAccountFirebase(String email, String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                changeInProgress(false);
                if (task.isSuccessful()){
                    Toast.makeText(getActivity(), "account made with success",Toast.LENGTH_SHORT).show();
                    /*firebaseAuth.getCurrentUser().sendEmailVerification();
                    firebaseAuth.signOut();*/
                    getActivity().finish();

                }else{
                    Toast.makeText(getActivity(), task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    boolean validateData(String email, String password, String confirmPass){
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailET.setError("Email invalid");
            return false;
        }
        if (password.length()<5){
            passwordET.setError("Password has to be > 5 char");
            return false;
        }
        if(!password.equals(confirmPass)){
            confirmPassET.setError("Passwords don't match, are you dumb?");
        }
        return true;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {   super.onViewCreated(view, savedInstanceState);
        binding.loginTV.setOnClickListener((View v) -> {
        NavHostFragment.findNavController(CreateAccountFragment.this).navigate(R.id.action_createAccountFragment_to_loginFragment);
        });
    }
}

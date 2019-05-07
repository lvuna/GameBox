package fall18_207project.GameCenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/***
 *  the login activity for app.
 */
public class LoginActivity extends AppCompatActivity implements ValidateFormatActivity {

    private FirebaseAuth firebaseAuth;
    private String emailValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        addLoginButtonListener();
        addRegisterButtonListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            String emailValue = currentUser.getEmail();
            CurrentAccountController.readSavedData(this, emailValue);
            firebaseAuth.signOut();
        }
    }

    private void addLoginButtonListener() {
        Button login = findViewById(R.id.loginBtn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email = findViewById(R.id.EmailLogin);
                EditText password = findViewById(R.id.passwordtext);
                emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                userLogin(emailValue, passwordValue);
            }
        });
    }

    private void addRegisterButtonListener() {
        Button register = findViewById(R.id.registerbtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(goToRegister);
            }
        });
    }

    /**
     * Log the user in and get their account information.
     *
     * @param email    input email;
     * @param password input password;
     */
    private void userLogin(final String email, String password) {
        if (validateFormat()) {
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("LoginActivity", "sign in successful!");
                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

                            CurrentAccountController.readSavedData(LoginActivity.this, email);
                            switchToGameCentre();
                        } else {
                            Log.d("LoginActivity", "sign in failed");
                            Toast.makeText(LoginActivity.this, "Sign in failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void switchToGameCentre() {
        Intent goToCenter = new Intent(getApplicationContext(), GameCentreActivity.class);
        startActivity(goToCenter);
    }

    /**
     * Check if the input information is not empty.
     *
     * @return whether the form is validate
     */
    public boolean validateFormat() {
        boolean valid = true;
        EditText emailValue = findViewById(R.id.EmailLogin);
        String email = emailValue.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailValue.setError("Required.");
            valid = false;
        } else {
            emailValue.setError(null);
        }

        EditText passwordValue = findViewById(R.id.passwordtext);
        String password = passwordValue.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordValue.setError("Required.");
            valid = false;
        } else {
            passwordValue.setError(null);
        }

        return !valid;
    }
}

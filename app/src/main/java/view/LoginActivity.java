package view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ViewLogic.slidingmenu.R;
import model.dropbox.DBSuper;

/**
 * Created by Eden on 09-Jan-16.
 */

/**
 * This activity is represent the functionality of login
 */
public class LoginActivity extends Activity {

    /** The button signup */
    Button signup;
    /** The button signin */
    Button signin;
    /** The user name **/
    EditText etUsername;
    /** the password **/
    EditText etPassword;

    public final static int LOGIN  = 1002;

    /** Shared prefernces for user Acount **/
    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private SharedPreferences prefs = null;

    /** Global variables **/
    String user;
    String password;

    /**
     * on create
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        prefs = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        setContentView(R.layout.activity_login);

        signup = (Button)findViewById(R.id.btnSingUp);
        signup.setOnClickListener(signUpListener);
        signin = (Button)findViewById(R.id.btnSignIn);
        signin.setOnClickListener(signInListener);
        etUsername = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPass);

        /** get the user and password from SharedPrefernce - if its the first time will get null **/
        user = prefs.getString(USERNAME, null);
        password = prefs.getString(PASSWORD,null);

        /** check if is not the first time of the user in the app **/
        if (user != null)
        {
            /** enabled only the sign in button . the user dont need to add is username or password **/
            etUsername.setText(user);
            etPassword.setText(password);
            signup.setEnabled(false);
        }else
        {
            /** the first time of the user - enabled only registration **/
            signin.setEnabled(false);
            etUsername.setEnabled(false);
            etPassword.setEnabled(false);
        }
    }
    /**
     * Listener of signup button for registration
     */
    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, UpdateProfileFragmentActivity.class);
            intent.putExtra("calling-activity",LOGIN);
            startActivity(intent);

        }

    };
    /**
     * Listener of signin button for regular sing in
     */
    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent intent = new Intent(LoginActivity.this, DBSuper.class);
        startActivity(intent);
        }
    };
}

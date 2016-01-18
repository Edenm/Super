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
import model.Helper;
import model.Item;
import model.ModelLogic;
import model.SuperMarket;
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

    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private SharedPreferences prefs = null;
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

        user = prefs.getString(USERNAME, null);
        password = prefs.getString(PASSWORD,null);

        if (user != null)
        {
            etUsername.setText(user);
            etPassword.setText(password);
            signup.setEnabled(false);
        }else
        {
            signin.setEnabled(false);
            etUsername.setEnabled(false);
            etPassword.setEnabled(false);
        }
    }
    /**
     * Listener of signup button
     */
    private View.OnClickListener signUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(LoginActivity.this, UpdateProfileFragmentActivity.class));

        }

    };

    private View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {



            /*if (checkValidateOfPassword(password))
            {*/
                Intent intent = new Intent(LoginActivity.this, DBSuper.class);
                startActivity(intent);
            /*}else
            {
                Toast.makeText(getApplicationContext(), "user name or password are invalid" , Toast.LENGTH_LONG).show();
            }*/
        }
    };

    /*private boolean checkValidateOfPassword(String password){
        boolean result;
        String pass = prefs.getString(PASSWORD, null);

        if ((pass.equals(password))) {
            result = true;
        }else {
            result = false;
        }
        return result;
    }*/

    /*public static void writeUserJasonFile(String username, String password) {
        JSONObject jsonUser = new JSONObject();
        try {

            jsonUser.put("userName", username);
            jsonUser.put("password", password);
            jsonUser.put("address", null);
            jsonUser.put("marketList", null);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Helper.writeFile(jsonUser.toString(), "User.txt");
    }*/

        /*String path= "/storage/emulated/0/dbSuperZol/User";
        File file = new File(path);*/

       /* if ( file.exists() )
        {
            if (checkValidate(ml,userName, password)) {
                Intent intent = new Intent(LoginActivity.this,DBSuper.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "user name or password are invalid" , Toast.LENGTH_LONG).show();
            }

        }
        else
        {
            writeUserJasonFile(userName, password);
            Intent intent = new Intent(LoginActivity.this,DBSuper.class);
            startActivity(intent);
        }*/

}

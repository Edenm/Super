package view;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.security.auth.PrivateCredentialPermission;

import ViewLogic.slidingmenu.R;
import model.dropbox.DBSuper;

/**
 * TBD
 */
public class UpdateProfileFragmentActivity extends FragmentActivity {

    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private static String ADRESS = "address";
    private SharedPreferences prefs;


    /** button for registration **/
    Button btnRegister;

    EditText etUser;
    EditText etPass;
    EditText etAdress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity_update_profile);

        prefs = getSharedPreferences("ACCOUNT", MODE_PRIVATE);

        btnRegister = (Button)findViewById(R.id.btnReg);
        etUser = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        etAdress = (EditText)findViewById(R.id.etAdress);

        btnRegister.setOnClickListener(registerListener);

    }


    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            String userName = etUser.getText().toString();
            String password = etPass.getText().toString();
            String address = etAdress.getText().toString();

            if (!checkValidityOfData(userName,password))
            {
                Toast.makeText(getApplicationContext(), "user name or password are invalid", Toast.LENGTH_LONG).show();
            }else {

                SavePreferences(USERNAME, userName);
                SavePreferences(PASSWORD, password);
                SavePreferences(ADRESS, address);

                Intent intent = new Intent(UpdateProfileFragmentActivity.this, DBSuper.class);
                startActivity(intent);
            }
        }
    };

    public void SavePreferences(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public void ClearPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public boolean checkValidityOfData(String user, String pass)
    {
        boolean validity = true;
        if (user.isEmpty() || pass.isEmpty()) {
            validity = false;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            validity = false;
        }
        if (pass.length() < 8) {
            validity = false;
        }
        return validity;
    }


}

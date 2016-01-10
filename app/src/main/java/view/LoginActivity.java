package view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

    /**
     * on create
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = (Button)findViewById(R.id.btnSingIn);
        signup.setOnClickListener(signUpListener);
    }

    /**
     * Listener of signup button
     */
    private View.OnClickListener signUpListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,DBSuper.class);
            startActivity(intent);
        }
    };
}

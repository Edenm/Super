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
public class LoginActivity extends Activity {

    Button signup;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signup = (Button)findViewById(R.id.btnSingIn);
        signup.setOnClickListener(signUpListener);
    }


    private View.OnClickListener signUpListener = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this,DBSuper.class);
            startActivity(intent);
        }
    };
}

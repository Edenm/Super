package view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import ViewLogic.slidingmenu.R;

/**
 * this Activity is for giving details to user about us - SuperZol
 */
public class AboutFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_about);
    }
}

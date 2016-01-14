package view;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import ViewLogic.slidingmenu.R;

/**
 * TBD
 */
public class AboutFragmentActivity extends FragmentActivity {

    TextView abouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_about);
        abouts = (TextView)findViewById(R.id.txtcommLabel);
        String aboutSuperzol = getString(R.string.aboutSuperZol);
        abouts.setText(aboutSuperzol);

    }


	/*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        //LayoutInflater lf = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.fragment_activity_about, container, false);

        //about.getDisplay();
         
        return rootView;
    }*/
}

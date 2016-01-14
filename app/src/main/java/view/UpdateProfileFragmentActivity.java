package view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ViewLogic.slidingmenu.R;

/**
 * TBD
 */
public class UpdateProfileFragmentActivity extends Fragment {
	
	public UpdateProfileFragmentActivity(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_activity_update_profile, container, false);
         
        return rootView;
    }
}

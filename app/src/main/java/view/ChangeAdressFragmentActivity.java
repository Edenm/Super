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
public class ChangeAdressFragmentActivity extends Fragment {
	
	public ChangeAdressFragmentActivity(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_activity_change_address, container, false);
         
        return rootView;
    }
}

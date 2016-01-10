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
public class PhotosFragment extends Fragment {
	
	public PhotosFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_photos, container, false);
         
        return rootView;
    }
}

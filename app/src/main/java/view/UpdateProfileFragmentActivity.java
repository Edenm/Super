package view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import ViewLogic.slidingmenu.R;
import model.dropbox.DBSuper;

/**
 * this Activity is for first time registration and for updating user profile
 */
public class UpdateProfileFragmentActivity extends FragmentActivity {

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private static final String LOG_TAG = "Google Places Autocomplete";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyCLk8zfUv1KNqCF9ZRts7UX6wXKoes6OYM";

    /** ShredPreferences for editing and save in memory **/
    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private static String ADRESS = "address";
    private SharedPreferences prefs;


    /** Global variables **/
    Button btnRegister;
    EditText etUser;
    EditText etPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity_update_profile);
        prefs = getSharedPreferences("ACCOUNT", MODE_PRIVATE);

        btnRegister = (Button)findViewById(R.id.registerButton);
        etUser = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        //atvPlaces = (AutoCompleteTextView)findViewById(R.id.etAdress);

        btnRegister.setOnClickListener(registerListener);


        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                //Log.i(TAG, "Place: " + place.getName());

                String placeDetailsStr = place.getName() + "\n"
                        + place.getId() + "\n"
                        + place.getLatLng().toString() + "\n"
                        + place.getAddress() + "\n"
                        + place.getAttributions();
                //txtPlaceDetails.setText(placeDetailsStr);
            }

            @Override
            public void onError(Status status) {

            }
        });
    }


        private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /** get the user choices for email, password and address **/
            String userName = etUser.getText().toString();
            String password = etPass.getText().toString();
            //String address = etAdress.getText().toString();

            /** check validate of data **/
            if (!checkValidityOfData(userName,password))
            {
                Toast.makeText(UpdateProfileFragmentActivity.this, R.string.invalid_data, Toast.LENGTH_LONG).show();
            }else {

                /** save the user name, password and address to SharedPreference **/
                SavePreferences(USERNAME, userName);
                SavePreferences(PASSWORD, password);
                //SavePreferences(ADRESS, address);

                /** start the SuperZol app **/
                Intent intent = new Intent(UpdateProfileFragmentActivity.this, DBSuper.class);
                startActivity(intent);
            }
        }
        };

    /** save data to SharedPreferences **/
    public void SavePreferences(String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /** clean the last saving data for update to new data **/
    public void ClearPreferences() {
        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /** check validity of data e.g - Mail is like standard, password is more than 8 characters **/
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

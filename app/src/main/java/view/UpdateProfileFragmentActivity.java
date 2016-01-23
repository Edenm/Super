package view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import ViewLogic.slidingmenu.R;
import model.dropbox.DBSuper;

/**
 * this Activity is for first time registration and for updating user profile
 */
public class UpdateProfileFragmentActivity extends FragmentActivity {

    int PLACE_PICKER_REQUEST = 1;

    /** ShredPreferences for editing and save in memory **/
    private static String USERNAME = "user";
    private static String PASSWORD = "password";
    private static String ADRESS = "address";
    private static String LAT = "LAT";
    private static String LONG = "LONG";
    private static String RADIUS = "RADIUS";
    private SharedPreferences prefs;

    /** Global variables **/
    Button btnRegister;
    EditText etUser;
    EditText etPass;

    String type = "";

    TextView addressTextView;
    Spinner spRadius;
    ArrayAdapter adapterFillClass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_activity_update_profile);
        prefs = getSharedPreferences("ACCOUNT", MODE_PRIVATE);

        btnRegister = (Button) findViewById(R.id.registerButton);
        etUser = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        addressTextView = (TextView) findViewById(R.id.txtAddres);
        spRadius = (Spinner) findViewById(R.id.spRadius);

        btnRegister.setOnClickListener(registerListener);
        addressTextView.setOnClickListener(clickAddressListener);
        addressTextView.setOnFocusChangeListener(focusAddressListener);

        /** set the combo-box choises from array in string.xml **/
        adapterFillClass = ArrayAdapter.createFromResource(this,
                R.array.radius_choices,
                android.R.layout.simple_spinner_dropdown_item);
        spRadius.setAdapter(adapterFillClass);

        int cameFrom = getIntent().getIntExtra("calling-activity", 0);

        switch (cameFrom) {
            case MainActivity.MAIN:
                btnRegister.setText(R.string.button_update);
                type = "update";
                setUserDetails();
                break;
            default:
                type = "register";
                btnRegister.setText(R.string.button_register);
                break;
        }
    }

    private View.OnFocusChangeListener focusAddressListener = new View.OnFocusChangeListener(){
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            activatePlacePicker();
        }
    };

    private View.OnClickListener clickAddressListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            activatePlacePicker();
        }
    };

    private void activatePlacePicker()
    {
        PLACE_PICKER_REQUEST = 1;
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                addressTextView.setText(place.getAddress());
                SavePreferences(LAT, String.valueOf(place.getLatLng().latitude));
                SavePreferences(LAT, String.valueOf(place.getLatLng().latitude));
                SavePreferences(LONG, String.valueOf(place.getLatLng().longitude));
            }
        }
    }


    private View.OnClickListener registerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            /** get the user choices for email, password and address **/
            String userName = etUser.getText().toString();
            String password = etPass.getText().toString();
            String address = addressTextView.getText().toString();
            String radius = spRadius.getSelectedItem().toString();

            /** check validate of data **/
            try{
                checkValidityOfData(userName, password);

                /** save the user name, password and address to SharedPreference **/
                SavePreferences(USERNAME, userName);
                SavePreferences(PASSWORD, password);
                SavePreferences(ADRESS, address);
                SavePreferences(RADIUS, radius);

                if (type.equals("update"))
                {
                    /** Message update success **/
                    Toast.makeText(UpdateProfileFragmentActivity.this, "הפרטים התעדכנו בהצלחה", Toast.LENGTH_LONG).show();
                }
                else{
                    /** start the SuperZol app **/
                    Intent intent = new Intent(UpdateProfileFragmentActivity.this, DBSuper.class);
                    startActivity(intent);
                }


            }catch (Exception e){
                Toast.makeText(UpdateProfileFragmentActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
    public void checkValidityOfData(String user, String pass) throws Exception
    {
        if (user.isEmpty() || pass.isEmpty() || addressTextView.getText().toString().isEmpty()/*or adress not empty */) {
            throw new Exception("כל השדות הם שדות חובה");
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user).matches()){
            throw new Exception("מייל לא תקין");
        }
        if (pass.length() < 8) {
            throw new Exception("סיסמא לא תקינה");
        }
    }

    /**
     * The method set all details of user in preference
     */
    private void setUserDetails() {
        etUser.setText(prefs.getString(USERNAME, null));
        etPass.setText(prefs.getString(PASSWORD, null));
        addressTextView.setText(prefs.getString(ADRESS, null));
        int spinnerPosition = adapterFillClass.getPosition(prefs.getString(RADIUS, null));
        spRadius.setSelection(spinnerPosition);
    }

}

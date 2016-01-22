package view;



import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

/**
 * This activity is represent the functionality of view all items in specified super
 */
public class SuperTabActivity extends Activity {
    /** The item list */
    ListView list;

    /** The search view for search item */
    private AutoCompleteTextView superSearch;

    /** Array of all super address*/
    String [] supers;
    /** Array of all string represent supers for adapter*/
    String [] supersString;

    /**
     * On create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_super);

        superSearch = (AutoCompleteTextView) findViewById(R.id.superSearchView);

        setListeners();

    }

    /**
     * Set listenes of the list and of product in list
     */
    private void setListeners(){
        supers = ModelLogic.getInstance().getAllSuperAddress();
        supersString = ModelLogic.getInstance().getStringRepresentSuperNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, supersString);
        superSearch.setAdapter(adapter);

        superSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id)
            {
                String superAdress = "";
                String superText = (String)parent.getItemAtPosition(pos);
                int location = 0;
                for (String s:supersString) {
                    if (s.equals(superText)) {
                        superAdress = supers[location];
                        break;
                    }
                    location++;
                }

                TextView superName= (TextView)findViewById(R.id.txtSuperName);
                superName.setText(supersString[location]);

                ModelLogic ml = ModelLogic.getInstance();

                HashMap<Item, Float> items = ml.getAllItemsBySuper(superAdress);

                String[] itemname = new String[items.size()];
                String[] price = new String[items.size()];
                Integer[] imgid = new Integer[items.size()];
                int count = 0;

                for(Map.Entry<Item, Float> i:items.entrySet()) {
                    itemname[count]=i.getKey().getItemName() ;
                    price[count]=i.getValue().toString() ;
                    imgid[count++]= R.drawable.no_image;
                }

                CustomListAdapter adapter= new CustomListAdapter(SuperTabActivity.this,  imgid,itemname , price,"super");
                list = (ListView) findViewById(R.id.listViewSuper);
                list.setAdapter(adapter);
            }
        });

    }
}

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


        TextView superName= (TextView)findViewById(R.id.txtSuperName);
        String ramiLeviSuperName = getString(R.string.shivuk_hashikma);
        superName.setText(ramiLeviSuperName);


        ModelLogic ml = ModelLogic.getInstance();

        ArrayList <Item> items = new ArrayList <Item>();

        for(Item i:ml.data.getItems().values()) {
            items.add(i);
        }

        String[] itemname = new String[items.size()];
        String[] price = new String[items.size()];
        Integer[] imgid = new Integer[items.size()];
        for(int i=0 ; i < items.size() ;i++) {
            itemname[i]=items.get(i).getItemName() ;
            price[i]=items.get(i).getItemPrice().toString() ;
            imgid[i]= R.drawable.no_image;
        }

        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname , price,"super");
        list = (ListView) findViewById(R.id.listViewSuper);
        list.setAdapter(adapter);

    }

    /**
     * Set listenes of the list and of product in list
     */
    private void setListeners(){
        String [] supers = ModelLogic.getInstance().getAllSuperNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, supers);
        superSearch.setAdapter(adapter);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
//                //TextView txtTitle = (TextView) (parent).findViewById(R.id.prodName);
//                //String itemName = txtTitle.getText().toString();
//                quantityIntent.putExtra("type", "MarketList");
//                quantityIntent.putExtra("word", "1");
//                startActivity(quantityIntent);
//            }
//        });

        //SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        //superSearch.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

//        superSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                //HashMap<Item, Float> itemsInSpecifiedSuper = ModelLogic.getInstance().getAllItemsBySuper(query);
//
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                //loadAutoComplete(newText);
//                return true;
//            }
//        });
    }
}

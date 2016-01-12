package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.MatrixCursor;
import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ViewLogic.slidingmenu.R;
import model.Item;
import model.MarketList;
import model.ModelLogic;
import view.adapter.AutoCompleteAdapter;
import view.adapter.CustomListAdapter;

/**
 * This activity is represent the functionality of search item in DB and manage market list
 */
public class SearchTabActivity extends Activity {

    /** The market list */
    private ListView list;
    /** The search view for search item */
    private SearchView productSearchView;


    /**
     * On create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);
        list = (ListView) findViewById(R.id.listViewSearch);
        productSearchView = (SearchView) findViewById(R.id.searchView);

        loadData();
        setListeners();
    }

    /**
     * On resume
     */
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    /**
     * Load all the data of search tab activity
     */
    public void loadData(){
        MarketList ml = MarketList.getInstance();


        int counter = 0;
        HashMap<Item,Integer> listItem =  ml.getItems();
        String[] itemname = new String[listItem.size()];
        String[] price = new String[listItem.size()];
        Integer[] imgid = new Integer[listItem.size()];
        for(Map.Entry<Item,Integer> i:listItem.entrySet()) {
            itemname[counter] = i.getKey().getItemName();
            price[counter] = i.getValue().toString();
            imgid[counter] = R.drawable.no_image;
            counter++;
        }

        CustomListAdapter adapter = new CustomListAdapter(this, imgid, itemname, price, "list");
        list.setAdapter(adapter);




        //ArrayAdapter <String> adapterToComp = new ArrayAdapter <String>(this, R.layout.activity_combo_autocomplete,itemsTocomplete);

        //CursorAdapter adapterToComp2 = new CursorAdapter()
        //productSearchView.setSuggestionsAdapter(adapterToComp);
    }

    /**
     * Set listenes of the list and of product in list
     */
    private void setListeners(){

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
                //TextView txtTitle = (TextView) (parent).findViewById(R.id.prodName);
                //String itemName = txtTitle.getText().toString();
                quantityIntent.putExtra("type", "MarketList");
                quantityIntent.putExtra("word", "1");
                startActivity(quantityIntent);
            }
        });

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        productSearchView.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

        productSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
                quantityIntent.putExtra("type","Word");
                quantityIntent.putExtra("word",query);
                startActivity(quantityIntent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadAutoComplete(newText);
                return true;
            }
        });
    }


    private void loadAutoComplete(String query) {

        ModelLogic model = ModelLogic.getInstance();
        List<String> itemsTocomplete = new ArrayList<String>();
        int counter=0;
        for(Item i:model.getSysData().getItems().values()) {
            itemsTocomplete.add(i.getItemName());
            counter++;
        }

            // Cursor
            String[] columns = new String[] { "_id", "text" };
            Object[] temp = new Object[] { 0, "default" };

            MatrixCursor cursor = new MatrixCursor(columns);

            for(int i = 0; i < itemsTocomplete.size(); i++) {

                temp[0] = i;
                temp[1] = itemsTocomplete.get(i); //replaced s with i as s not used anywhere.

                cursor.addRow(temp);

            }

            // SearchView
            SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

//            final SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();

            productSearchView.setSuggestionsAdapter(new AutoCompleteAdapter(this, cursor, itemsTocomplete));

    }




}


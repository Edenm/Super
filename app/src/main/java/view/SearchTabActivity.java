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
import android.widget.Toast;

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
    private AutoCompleteTextView productSearchView;
    /** Button to search product */
    private Button searchButton;
    /** Button to save market list */
    private Button btnSaveMarket;
    /** Contain all item names */
    private String [] itemNames;

    /**
     * On create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);
        list = (ListView) findViewById(R.id.listViewSearch);
        productSearchView = (AutoCompleteTextView) findViewById(R.id.searchView);
        searchButton = (Button) findViewById(R.id.btnSearch);
        btnSaveMarket = (Button) findViewById(R.id.btnSave);

        ModelLogic ml = ModelLogic.getInstance();
        ml.loadMarketList();

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
        MarketList ml = ModelLogic.getInstance().getMarketList();


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

        itemNames = ModelLogic.getInstance().getAllItemNames();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, itemNames);
        productSearchView.setAdapter(adapter);

        productSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int pos, long id) {
                String itemName = (String) parent.getItemAtPosition(pos);
                onSearchAction(itemName);
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = productSearchView.getText().toString();
                onSearchAction(word);
            }
        });

        btnSaveMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ModelLogic.getInstance().saveMarketList())
                    Toast.makeText(SearchTabActivity.this, R.string.saveListMsg, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * The method is helping for 2 listeners, find item in list adapter
     * and to find item by any word
     * @param wordToSearch
     */
    private void onSearchAction(String wordToSearch)
    {
        ModelLogic ml = ModelLogic.getInstance();

        Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
        quantityIntent.putExtra("type", "Word");
        quantityIntent.putExtra("word", wordToSearch);
        startActivity(quantityIntent);
    }

}


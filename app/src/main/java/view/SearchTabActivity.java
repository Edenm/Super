package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import java.util.HashMap;
import java.util.Map;
import ViewLogic.slidingmenu.R;
import model.Item;
import model.MarketList;
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

                return false;
            }
        });

    }





}


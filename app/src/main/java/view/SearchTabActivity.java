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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.MarketList;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

public class SearchTabActivity extends Activity {

    private ListView list;
    private SearchView productSearchView;
    private Button btnEditQuantity;
    //private ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);

        list = (ListView) findViewById(R.id.listViewSearch);
        productSearchView = (SearchView) findViewById(R.id.searchView);



        loadData();
        setListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

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

            /*btnEditQuantity = (Button) findViewById(R.id.btnEdit);

            btnEditQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);

                    View parent = (View) v.getParent();
                    TextView txtTitle = (TextView) (parent).findViewById(R.id.prodName);
                    String itemName = txtTitle.getText().toString();
                    quantityIntent.putExtra("word", itemName);
                    startActivity(quantityIntent);
                }
            });*/


        CustomListAdapter adapter = new CustomListAdapter(this, imgid, itemname, price, "list");

        list.setAdapter(adapter);

        //adapter.notifyDataSetChanged();
    }

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


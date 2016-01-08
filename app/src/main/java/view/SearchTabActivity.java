package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.MarketList;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

public class SearchTabActivity extends Activity {

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);

        loadData();


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
                startActivity(quantityIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData(){
        MarketList ml = MarketList.getInstance();
        ArrayList<Item> items = new ArrayList <Item>();

        for(Item i:ml.getItems().keySet()) {
            items.add(i);
        }

        String[] itemname;
        String[] price;
        Integer[] imgid;
        if ( items.size()!=0 ) {
            itemname = new String[items.size()];
            price = new String[items.size()];
            imgid = new Integer[items.size()];
            for (int i = 0; i < items.size(); i++) {
                itemname[i] = items.get(i).getItemName();
                price[i] = items.get(i).getItemPrice().toString();
                imgid[i] = R.drawable.no_image;
            }
        }

        else {
            itemname = new String[1];
            price = new String[1];
            imgid = new Integer[1];

            itemname[0]= "None";
            price[0]= "0";
            imgid[0]= R.drawable.no_image;
        }


        CustomListAdapter adapter = new CustomListAdapter(this, imgid, itemname, price, "list");
        list = (ListView) findViewById(R.id.listViewSearch);
        list.setAdapter(adapter);
    }

}


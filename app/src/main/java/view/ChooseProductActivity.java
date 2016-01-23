package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import ViewLogic.slidingmenu.R;
import model.Item;
import model.MarketList;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

/**
 * This activity is represent the functionality of increase and decrease the amount of the items in the item list
 */
public class ChooseProductActivity extends Activity {

    /** The list item */
    ListView list;
    /** The search view */
    TextView searchProduct;
    /** The button to return last activity */
    Button btnBack;

    /**
     * on create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);

        list = (ListView) findViewById(R.id.listView);
        searchProduct = (TextView)findViewById(R.id.txtSearchProduct);
        btnBack = (Button)findViewById(R.id.btnReturn);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String wordToSearch = intent.getStringExtra("word");

        if (type.equals("MarketList")){
            searchProduct.setText(R.string.market_list);
            loadDataByMarketList();
        }
        else{
            String search = getString(R.string.search);
            searchProduct.setText(search + ": " + wordToSearch);
            loadDataByWord(wordToSearch);
        }

        setListener();

    }

    /**
     * The method is load all data in that activity by search word
     * @param wordToSearch
     */
    private void loadDataByWord(String wordToSearch){

       ArrayList<Item> items = new ArrayList<Item>(ModelLogic.getInstance().getAllResultByWord(wordToSearch));

        String[] itemname = new String[items.size()];
        String[] price = new String[items.size()];
        Integer[] imgid = new Integer[items.size()];

        if (items.size()==0){
            String notFound = getString(R.string.not_found_item);
            searchProduct.setText(notFound+ " " + wordToSearch);
        }

        for(int i=0 ; i < items.size() ;i++) {
            itemname[i]=items.get(i).getItemName() ;
            price[i]=items.get(i).getItemPrice().toString() ;
            imgid[i]= R.drawable.no_image;
        }

        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname, price,"quantity");
        list.setAdapter(adapter);
    }

    /**
     * The method is load all data in that activity by marketlist
     */
    private void loadDataByMarketList(){

        MarketList ml = ModelLogic.getInstance().getMarketList();

        int counter = 0;
        HashMap<Item,Integer> listItem =  ml.getItems();
        String[] itemname = new String[listItem.size()];
        String[] price = new String[listItem.size()];
        Integer[] imgid = new Integer[listItem.size()];
        for(Map.Entry<Item,Integer> i:listItem.entrySet()) {
            itemname[counter] = i.getKey().getItemName();
            price[counter] = i.getKey().getItemPrice().toString();
            imgid[counter] = R.drawable.no_image;
            counter++;
        }

        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname, price,"quantity");
        list.setAdapter(adapter);
    }


    /**
     * This method contains set of listeners
     */
    private void setListener(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.Helper;
import model.MarketList;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

public class ChooseProductActivity extends Activity {

    ListView list;
    TextView searchProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);

        list = (ListView) findViewById(R.id.listView);
        searchProduct= (TextView)findViewById(R.id.txtSearchProduct);

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        String wordToSearch = intent.getStringExtra("word");

        if (type.equals("MarketList")){
            searchProduct.setText("רשימת קניות");
            loadDataByMarketList();
        }
        else{
            searchProduct.setText(wordToSearch);
            loadDataByWord(wordToSearch);
        }


//        switch (type){
//            case "MarketList": searchProduct.setText("רשימת קניות");
//                               loadDataByMarketList();
//                               break;
//            case "Word":       searchProduct.setText(wordToSearch);
//                               loadDataByWord(wordToSearch);
//                               break;
//            default:
//                break;
//        }





    }

    private void loadDataByWord(String wordToSearch){

       // ModelLogic ml = ModelLogic.getInstance();
        //ArrayList<Item> items = new ArrayList<Item>(ml.getSysData().getItems().values());

       ArrayList<Item> items = new ArrayList<Item>(Helper.getAllResultByWord(wordToSearch));

        String[] itemname = new String[items.size()];
        String[] price = new String[items.size()];
        Integer[] imgid = new Integer[items.size()];
        for(int i=0 ; i < items.size() ;i++) {
            itemname[i]=items.get(i).getItemName() ;
            price[i]=items.get(i).getItemPrice().toString() ;
            imgid[i]= R.drawable.no_image;
        }

        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname, price,"quantity");
        list.setAdapter(adapter);
    }

    private void loadDataByMarketList(){

        MarketList ml = MarketList.getInstance();


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


}

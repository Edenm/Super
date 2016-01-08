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
import android.widget.TextView;

import java.util.ArrayList;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

public class ChooseProductActivity extends Activity {

    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_product);

        TextView searchProduct= (TextView)findViewById(R.id.txtSearchProduct);
        searchProduct.setText("חלב");


        ModelLogic ml = ModelLogic.getInstance();
        ArrayList<Item> items = new ArrayList <Item>();

        for(Item i:ml.data.getItems().values()) {
            items.add(i);
        }

        String[] itemname = new String[items.size()];
        String[] price = new String[items.size()];
        Integer[] imgid = new Integer[items.size()];
        for(int i=0 ; i < items.size() ;i++) {
            itemname[i]=items.get(i).getItemCode() ;
            price[i]=items.get(i).getItemPrice().toString() ;
            imgid[i]= R.drawable.no_image;
        }

        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname, price,"quantity");
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
    }

}

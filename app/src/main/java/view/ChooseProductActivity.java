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
import ViewLogic.slidingmenu.R;
import model.Item;
import model.Helper;
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
        String wordToSearch = intent.getStringExtra("word");

        searchProduct.setText(wordToSearch);


        loadData(wordToSearch);
        //loadData();
    }

    private void loadData(String wordToSearch){

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

}

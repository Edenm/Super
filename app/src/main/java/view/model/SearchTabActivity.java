package view.model;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import ViewLogic.slidingmenu.*;
import view.ChooseProductActivity;
import view.adapter.CustomListAdapter;

public class SearchTabActivity extends Activity {

    ListView list;
    String[] itemname = {"חומוס אחלה 500 גרם", "מלפפון", "חלב טרה 3% שומן", "קוקה קולה"};
    String[] price = {"10", "4.10", "4.32", "5.99"};
    Integer[] imgid = { R.drawable.hummus_classic, R.drawable.cucumber, R.drawable.tara, R.drawable.cocacola};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_search);

        CustomListAdapter adapter = new CustomListAdapter(this, imgid, itemname, price, "list");
        list = (ListView) findViewById(R.id.listViewSearch);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent quantityIntent = new Intent(SearchTabActivity.this, ChooseProductActivity.class);
                startActivity(quantityIntent);
            }
        });
    }

}


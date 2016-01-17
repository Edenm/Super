package view;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;
import model.MarketList;
import model.ModelLogic;
import model.SuperMarket;
import view.adapter.CustomListAdapter;

/**
 * This activity is represent the functionality of compare the market list in all closed supers
 * TBD- in this point of time is not implement yet
 */
public class ComparisonTabActivity extends Activity {

    ListView list;
    Integer[] logoid;  // = { R.drawable.ramilevi_logo, R.drawable.supersal_logo, R.drawable.bitan_logo};
    String[] supername;  //ddd/ = {"רמי לוי שיווק השקמה - 250 ש''ח", "שופרסל דיל - 300 ש''ח", "יינות ביתן - 315 ש''ח"};
    String[] amounts;//= {"5.55", "7.08", "4.32"};



    /**
     * On create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_comparison);

        TextView title= (TextView) findViewById(R.id.txtTitleSuper);
        title.setText("שלושת הסופרים הכי זולים");

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData()
    {
        ModelLogic ml = ModelLogic.getInstance();
        MarketList marketList = ModelLogic.getInstance().getMarketList();
        Map <String,SuperMarket> supers = ml.getSysData().getSupers();

        logoid = new Integer[supers.size()];
        supername = new String[supers.size()];
        amounts = new String[supers.size()];

        int count = 0;
        for (Map.Entry<String,SuperMarket> e:supers.entrySet())
        {
            logoid[count] = ml.getLogoBySuperName(e.getValue().getName());
            supername[count++] = e.getValue().getAdress()+" - "+String.format("%.02f", marketList.getTotalPrice(e.getKey()))+ " ש''ח " ;
        }

        CustomListAdapter adapter = new CustomListAdapter(this, logoid, supername, amounts, "superList");
        list = (ListView) findViewById(R.id.listViewBestSuper);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData.Item i = (ClipData.Item) parent.getAdapter().getItem(position);
                HashMap<String, Object> obj = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                String name = (String) obj.get("name");

                String pName=parent.getItemAtPosition(position).toString();
            }
        });
    }

}


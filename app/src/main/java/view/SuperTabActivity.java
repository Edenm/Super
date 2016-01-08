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

import java.util.ArrayList;
import java.util.HashMap;

import ViewLogic.slidingmenu.R;
import model.Item;
import model.ModelLogic;
import view.adapter.CustomListAdapter;


public class SuperTabActivity extends Activity {
    ListView list;
    //String[] itemname = {"חלב דל לקטוז תנובה", "גבינה בולגרית גד", "חומוס אחלה 500 גרם","שמן זית יד מרדכי","קוקה קולה"};
    //String[] price = {"5.55", "14.80", "10.0","34.3","5.99"};
    //Integer[] imgid = { R.drawable.dallaktoz, R.drawable.bulgarit, R.drawable.hummus_classic, R.drawable.olive_oil,R.drawable.cocacola};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_super);

        TextView superName= (TextView)findViewById(R.id.txtSuperName);
        superName.setText("רמי לוי שיווק השקמה - נשר");

        ModelLogic ml = ModelLogic.getInstance();

        //Helper.readXmlFile(ml, "Ramilevi.xml");
        //Helper.writeJasonFileForItem(ml);
        //Helper.writeJasonFileForSuper(ml);
        

        ArrayList <Item> items = new ArrayList <Item>();

        for(Item i:ml.data.getItems().values()) {
            items.add(i);
        }

        String[] itemname = new String[items.size()];
        String[] price = new String[items.size()];
        Integer[] imgid = new Integer[items.size()];
        for(int i=0 ; i < items.size() ;i++) {
            itemname[i]=items.get(i).getItemName() ;
            price[i]=items.get(i).getItemPrice().toString() ;
            imgid[i]= R.drawable.no_image;
        }



        CustomListAdapter adapter= new CustomListAdapter(this,  imgid,itemname , price,"super");
        list = (ListView) findViewById(R.id.listViewSuper);
        list.setAdapter(adapter);



        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClipData.Item i = (ClipData.Item) parent.getAdapter().getItem(position);
                HashMap<String, Object> obj = (HashMap<String, Object>) parent.getAdapter().getItem(position);
                String name = (String) obj.get("name");

                String pName = parent.getItemAtPosition(position).toString();
            }
        });
    }
}

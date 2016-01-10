package view;



import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import ViewLogic.slidingmenu.R;
import model.Item;
import model.ModelLogic;
import view.adapter.CustomListAdapter;

/**
 * This activity is represent the functionality of view all items in specified super
 */
public class SuperTabActivity extends Activity {
    /** The item list */
    ListView list;

    /**
     * On create
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_super);

        TextView superName= (TextView)findViewById(R.id.txtSuperName);
        String ramiLeviSuperName = getString(R.string.shivuk_hashikma);
        superName.setText(ramiLeviSuperName);


        ModelLogic ml = ModelLogic.getInstance();

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

    }
}

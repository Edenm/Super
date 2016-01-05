package view;

import ViewLogic.slidingmenu.R;
import view.adapter.CustomListAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class WhatsHotFragment extends Fragment {
	
	public WhatsHotFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_whats_hot, container, false);
         
        return rootView;
    }

    public static class SearchTabActivity extends Activity {

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
}

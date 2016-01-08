package view.adapter;

/**
 * Created by Eden on 03-Jan-16.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import ViewLogic.slidingmenu.*;
import model.Item;
import model.MarketList;
import model.ModelLogic;

/**
 * Created by MOR on 12/24/2015.
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] itemname;
    private final Integer[] imgid;
    private final String [] price;
    private String pListType;

    private View rowView= null;

    private Button btnIncremrnt;
    private Button btnDecrement;

    public CustomListAdapter(Activity context,Integer[] imgid, String[] itemname, String[] price, String pListType) {
        super(context, R.layout.productlist_quantity, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.price=price;
        this.pListType=pListType;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();


        if (pListType.equals("list"))
            rowView=inflater.inflate(R.layout.productlist, null,true);
        if (pListType.equals("quantity"))
            rowView=inflater.inflate(R.layout.productlist_quantity, null,true);
        if (pListType.equals("super"))
            rowView=inflater.inflate(R.layout.productlist_super, null,true);
        if (pListType.equals("superList"))
            rowView=inflater.inflate(R.layout.supermarketlist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.prodName);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.prodImg);

        if (!pListType.equals("superList")) {
            TextView extratxt = (TextView) rowView.findViewById(R.id.priceTxt);
            extratxt.setText(price[position]);
        }

        txtTitle.setText(itemname[position]);
        imageView.setImageResource(imgid[position]);

        if (pListType.equals("quantity"))
                        setListeners(position);

        return rowView;
    };

    private void setListeners(final int position){
        btnIncremrnt = (Button) rowView.findViewById(R.id.btnInc);
        btnDecrement = (Button) rowView.findViewById(R.id.btnDec);

        btnIncremrnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                View grantFather = (View)parent.getParent();
                TextView txtTitle = (TextView) (grantFather).findViewById(R.id.prodName);

                TextView txtCounter = (TextView) parent.findViewById(R.id.txtCounter);
                Integer count = Integer.parseInt(txtCounter.getText().toString());
                count++;
                txtCounter.setText(count.toString());

                String itemName = txtTitle.getText().toString();

                ModelLogic ml = ModelLogic.getInstance();
                Item tempItem = ml.getSysData().getItems().get(itemName);

                MarketList tempMarketList = MarketList.getInstance();
                tempMarketList.addItemToMarketList(tempItem);
            }
        });

        btnDecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                View grantFather = (View)parent.getParent();
                TextView txtTitle = (TextView) (grantFather).findViewById(R.id.prodName);

                TextView txtCounter = (TextView) parent.findViewById(R.id.txtCounter);
                Integer count = Integer.parseInt(txtCounter.getText().toString());
                if ( count>0 ){
                    count--;
                    txtCounter.setText(count.toString());
                }


                String itemName = txtTitle.getText().toString();

                ModelLogic ml = ModelLogic.getInstance();
                Item tempItem = ml.getSysData().getItems().get(itemName);

                MarketList tempMarketList = MarketList.getInstance();
                tempMarketList.RemoveItemToMarketList(tempItem, count);
            }
        });
    }

}


package view.adapter;


/**
 * Created by MOR on 12/24/2015.
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
import view.SearchTabActivity;

/**
 * This class is a helper class to create diffrent type of custom list item
 */
public class CustomListAdapter extends ArrayAdapter<String> {

    /** The context of callback activity*/
    private final Activity context;
    /** Contains the name of item*/
    private final String[] itemname;
    /** Contains the image of item*/
    private final Integer[] imgid;
    /** Contains the price of item*/
    private final String [] price;
    /** The type of list we want to create */
    private String pListType;
    /** One view of list item */
    private View rowView= null;
    /** Button increment */
    private Button btnIncremrnt;
    /** Button decrement */
    private Button btnDecrement;

    /**
     * Full cto'r
     * @param context
     * @param imgid
     * @param itemname
     * @param price
     * @param pListType
     */
    public CustomListAdapter(Activity context,Integer[] imgid, String[] itemname, String[] price, String pListType) {
        super(context, R.layout.productlist_quantity, itemname);

        this.context=context;
        this.itemname=itemname;
        this.imgid=imgid;
        this.price=price;
        this.pListType=pListType;
    }

    /**
     * @param position
     * @param view
     * @param parent
     * @return view of one list item
     */
    @Override
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

        if (pListType.equals("quantity")) {
            View nextChild = ((ViewGroup)rowView).getChildAt(1);
            nextChild = ((ViewGroup)nextChild).getChildAt(1);
            nextChild = ((ViewGroup)nextChild).getChildAt(1);
            TextView txtCounter = (TextView) nextChild.findViewById(R.id.txtCounter);
            String name = txtTitle.getText().toString();
            String count = ModelLogic.getInstance().getMarketList().getQuantity(name).toString();
            txtCounter.setText(count);
            setListeners();
        }

        if (pListType.equals("list"))
            setRemoveProductListener();

        return rowView;
    };

    /**
     * The function set listener to buttons btnIncremrnt and btnDecrement
     */
    private void setListeners(){
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

                MarketList tempMarketList = ModelLogic.getInstance().getMarketList();
                tempMarketList.increaseCountOfItemMarketList(tempItem);
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

                MarketList tempMarketList = ModelLogic.getInstance().getMarketList();
                tempMarketList.decreaseCountOfItemMarketList(tempItem, count);
            }
        });
    }

    /**
     *  The function set listener to button btnRemoveProduct
     */
    private void setRemoveProductListener()
    {
        Button btnRemoveProduct = (Button)rowView.findViewById(R.id.btnRemove);

        btnRemoveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                View grantFather = (View) parent.getParent();
                TextView txtTitle = (TextView) (grantFather).findViewById(R.id.prodName);
                String itemName = txtTitle.getText().toString();

                ModelLogic ml = ModelLogic.getInstance();
                Item tempItem = ml.getSysData().getItems().get(itemName);

                MarketList tempMarketList = ModelLogic.getInstance().getMarketList();
                tempMarketList.removeItemFromMarketList(itemName);

                ((SearchTabActivity) context).loadData();
            }
        });


    }

}


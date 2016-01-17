package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *  this class included singelton of MarketList of user.
 *  all is choices are saving in this singelton.
 *  for each user we create a new Market list.
 *  TBD - save the MarketList for resume.
 */
public class MarketList implements Serializable {

    /** keep the instance of MarketList */
    public static MarketList instance;

    /** all Chosen items of user and quantity of each item **/
    HashMap <Item,Integer> items;

    /**
     * singletone function
     * @return instance of MarketList
     */
    public static MarketList getInstance(){
        if	(instance == null){
            instance = new MarketList();
        }
        return instance;
    }

    /**
     * Private cto'r
     */
    private MarketList() {
        super();
        items= new HashMap<Item, Integer>();
    }

    /**
     * @return items
     */
    public HashMap<Item, Integer> getItems(){
        return items;
    }

    /**
     * increasing the cohsen quantity of specific item
     * @param item
     */
    public void increaseCountOfItemMarketList(Item item){
        if( items.containsKey(item) )
        {
            items.put(item, items.get(item)+1);
        }
        else
        {
            items.put(item, 1);
        }
    }

    /**
     * decreaing the chosen quantity of specific product
     * @param item
     * @param count
     */
    public void decreaseCountOfItemMarketList(Item item, Integer count){
        items.put(item, count);
        if (count == 0){
            removeItemFromMarketList(item.getItemName());
        }
    }

    /**
     * remove specific item from marketList
     * @param itemName
     */
    public void removeItemFromMarketList(String itemName){
        Item temp = new Item(itemName);
        items.remove(temp);
    }

    /**
     * get the chosen quantity from specific item
     * @param product
     * @return
     */
    public Integer getQuantity (String product)
    {
        Item temp = new Item(product);
        Integer amount = items.get(temp);
        return amount == null ? 0: amount;
    }

    /**
     * @return The price of the market list
     */
    public Float getTotalPrice(String superAddress)
    {
        Float amount = 0.0f;
        for (Map.Entry<Item,Integer> e: items.entrySet()){
            amount += e.getKey().getPriceBySuper(superAddress)*e.getValue();
        }
        return amount;
    }
}

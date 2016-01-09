package model;

import java.util.HashMap;

/**
 * Created by Eden on 08-Jan-16.
 */
public class MarketList {

    public static MarketList instance;

    HashMap <Item,Integer> items;
    
    public static MarketList getInstance(){
        if	(instance == null){
            instance = new MarketList();
        }
        return instance;
    }

    private MarketList() {
        super();
        items= new HashMap<Item, Integer>();
    }

    public HashMap<Item, Integer> getItems(){
        return items;
    }

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

    public void decreaseCountOfItemMarketList(Item item, Integer count){
        items.put(item, count);
        if (count == 0){
            removeItemFromMarketList(item.getItemName());
        }
    }

    public void removeItemFromMarketList(String itemName){
        Item temp = new Item(itemName);
        items.remove(temp);
    }

    public Integer getQuantity (String product)
    {
        Item temp = new Item(product);
        Integer amount = items.get(temp);
        return amount == null ? 0: amount;
    }
}

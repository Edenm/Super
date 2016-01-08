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

    public void addItemToMarketList(Item item){
        if( items.containsKey(item) )
        {
            items.put(item, items.get(item)+1);
        }
        else
        {
            items.put(item, 1);
        }
    }

    public void RemoveItemToMarketList(Item item, Integer count){
        items.put(item, count);
    }
}

package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;

/**
 * SysData class included all DataBase of items and supers.
 * TBD - save all users in DB.
 *
 */
public class SysData implements Serializable {

	/** Contains all items in system*/
	private Map <String,Item> items;
	/** Contains all super markets in system*/
	private Map <String,SuperMarket> supers;

	/**
	 * Full cto'r
	 */
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
	}

	/**
	 * @return items
	 */
	public Map<String, Item> getItems() {
		return items;
	}

	/**
	 * @return supers
	 */
	public Map<String, SuperMarket> getSupers() {
		return supers;
	}

	/**
	 * The method add item to system
	 * @param item
	 */
	public void addItem(Item item)
	{
		items.put(item.getItemName(), item);
	}

	/**
	 * The method add super market to system
	 * @param sm
	 */
	public void addSuperMarket(SuperMarket sm)
	{
		supers.put(sm.getAdress(), sm);
	}


}

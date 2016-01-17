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

	/** Contains the user details **/
	private User user;
	/** Contains logos of the supers*/
	private Map <String,Integer> superLogos;

	/**
	 * Full cto'r
	 */
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
		//users = new HashMap<String, User>();
		user = null;
		superLogos = new HashMap<String, Integer>();
		fillSupers();
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
	 * Initialize all supers in system
	 */
	private void fillSupers()
	{
		supers.put("דרך השלום 13 נשר", new SuperMarket("רמי לוי","דרך השלום 13 נשר"));
		supers.put("שלמה המלך 55 חיפה", new SuperMarket("שופרסל","שלמה המלך 55 חיפה"));
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

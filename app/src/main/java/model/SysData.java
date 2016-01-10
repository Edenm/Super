package model;

import java.util.HashMap;
import java.util.Map;

/**
 * SysData class included all DataBase of items and supers.
 * TBD - save all users in DB.
 *
 */
public class SysData {

	/** Contains all items in system*/
	Map <String,Item> items;
	/** Contains all super markets in system*/
	Map <String,SuperMarket> supers;
	/** Contains all users in system*/
	Map <String,User> users;

	/**
	 * Full cto'r
	 */
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
		users = new HashMap<String, User>();
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
	 * @return users
	 */
	public Map<String, User> getUsers(User user)
	{
		return users;
	}

	/**
	 * The method add item to system
	 * @param item
	 */
	public void addItem(Item item){
		items.put(item.getItemName(), item);
	}

	/**
	 * The method add super market to system
	 * @param sm
	 */
	public void addSuperMarket(SuperMarket sm){
		supers.put(sm.getAdress(), sm);
	}


}

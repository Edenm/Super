package model;

import java.util.HashMap;
import java.util.Map;

/**
 * SysData class included all DataBase of items and supers.
 * TBD - save all users in DB.
 *
 */
public class SysData {

	Map <String,Item> items;
	Map <String,SuperMarket> supers;
	Map <String,User> users;
	
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
		users = new HashMap<String, User>();
	}

	public Map<String, Item> getItems() {
		return items;
	}

	public Map<String, SuperMarket> getSupers() {
		return supers;
	}

	public void addItem(Item item){
		items.put(item.getItemName(), item);
	}
	
	public void addSuperMarket(SuperMarket sm){
		supers.put(sm.getAdress(), sm);
	}

	public Map<String, User> getUsers(User user){return users;}


}

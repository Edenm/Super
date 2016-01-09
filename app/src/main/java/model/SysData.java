package model;

import java.util.HashMap;
import java.util.Map;


public class SysData {

	Map <String,User> users;
	Map <String,Item> items;
	Map <String,SuperMarket> supers;
	
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

package model;

import java.util.HashMap;
import java.util.Map;


public class SysData {

	Map <String,Item> items;
	Map <String,SuperMarket> supers;
	
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
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
	
}

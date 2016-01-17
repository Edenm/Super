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
	/** Contains all users in system*/
	private Map <String,User> users;

	/** Contains logos of the supers*/
	private Map <String,Integer> superLogos;

	/**
	 * Full cto'r
	 */
	public SysData() {
		items= new HashMap<String,Item>();
		supers= new HashMap<String,SuperMarket>();
		users = new HashMap<String, User>();
		superLogos = new HashMap<String, Integer>();
		fillSupers();
		fillLogo();
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
	 * @return superLogos
	 */
	public Map<String, Integer> getLogos(){
		return superLogos;
	}

	/**
	 * Initialize all supers in system
	 */
	private void fillSupers()
	{
		supers.put("דרך השלום 13 נשר", new SuperMarket("רמי לוי","דרך השלום 13 נשר"));
	}

	/**
	 * Initialize all logo of supermarkets
	 */
	private void fillLogo()
	{
		superLogos.put("rami Levi", R.drawable.ramilevi_logo);
		superLogos.put("supersal", R.drawable.supersal_logo);
		superLogos.put("bitan", R.drawable.bitan_logo);
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

package model;

import java.util.ArrayList;

/**
 * the ModelLogic class is a Singelton contains SysData
 * and manage all the DB.
 * add or remove Objects from our DB is managed here.
 */

public class ModelLogic {
	
	private static ModelLogic instance = null;
	
	public SysData data;

	/**
	 * singletone function
	 * @return instance of ModelLogic
	 */
	public static ModelLogic getInstance(){
		if	(instance == null){
			instance = new ModelLogic();
			Helper.readJasonFile(instance);
		}
		return instance;
	}

	/**
	 * private cto'r
	 */
	private ModelLogic() {
		super();
		data= new SysData();
	}

	/**
	 * This methd get item and adress of supermarket.
	 * the method add the item to DB
	 * @param item
	 * @param superMarketAdress
	 */
	public void addNewItem(Item item, String superMarketAdress){
		SuperMarket sm = data.getSupers().get(superMarketAdress);
		if (!data.getItems().containsKey(item.getItemCode())){
			item.addUpdatePriceToItem(sm, item.getItemPrice());
			data.addItem(item);
		}
		else
		{
			Item existItem = data.getItems().get(item.getItemCode());
			existItem.addUpdatePriceToItem(sm, item.getItemPrice());
		}
	}

	/**
	 * This methd get supermarket.
	 * the method add the supermarket to DB
	 * @param sm
	 */
	public void addNewSuperMarket(SuperMarket sm){
		if (!data.getSupers().containsKey(sm.getAdress())){
			data.addSuperMarket(sm);
		}
	}

	/**
	 * The method print all data in system.
	 */
	public void printDataBase(){
		
		System.out.println("All items: ");
		
		for (Item item : data.getItems().values()) {
			System.out.println(item);
		}
		
		System.out.println("All supers: ");
		
		for (SuperMarket item : data.getSupers().values()) {
			System.out.println(item);
		}
	}

	/**
	 * @return reference of sysData
	 */
	public SysData getSysData(){
		return data;
	}

	/**
	 * This method get word to search.
	 * @param word
	 * @return The method retunr list of items that contains that word inside
	 */
	public static ArrayList<Item> getAllResultByWord(String word){
		ArrayList <Item> items = new ArrayList <Item>();

		for (Item item:ModelLogic.getInstance().getSysData().getItems().values())
		{
			if (item.getItemName().contains(word)){
				items.add(item);
			}
		}
		return items;
	}

}

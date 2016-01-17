package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ViewLogic.slidingmenu.R;
import model.dropbox.manager.NetworkManager;

/**
 * the ModelLogic class is a Singelton contains SysData
 * and manage all the DB.
 * add or remove Objects from our DB is managed here.
 */

public class ModelLogic implements Serializable {
	
	private static ModelLogic instance = null;
	
	public static SysData data;

	/**
	 * singletone function
	 * @return instance of ModelLogic
	 */
	public static ModelLogic getInstance(){
		if	(instance == null){
			instance = new ModelLogic();
			//Helper.readJasonFile(instance);
			//writeSer();
			readSer();
			//instance.addNewSuperMarket(new SuperMarket("רמי לוי","דרך השלום 13 נשר"));
			//Helper.readXmlFile(instance, "Ramilevi.xml", "דרך השלום 13 נשר");
			writeSer();
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

	/**
	 * The method get super adress
	 * @param superAdress
	 * @return all item of the super with this address
	 */
	public HashMap<Item, Float> getAllItemsBySuper(String superAdress)
	{
		HashMap <Item, Float> itemInSpecifiedSuper = new HashMap <Item, Float>();
		ArrayList<Item> items = new ArrayList<Item>();
		SuperMarket sm = new SuperMarket(superAdress);

		for (Item i: data.getItems().values()){
			for (Map.Entry<SuperMarket, Float> s:i.getPrices().entrySet()){
				if (sm.equals(s.getKey())){
					itemInSpecifiedSuper.put(i,s.getValue());
				}
			}
		}

		return itemInSpecifiedSuper;
	}

	/**
	 * @return represent string for each super
	 */
	public String [] getStringRepresentSuperNames()
	{
		Map<String, SuperMarket> superData =  data.getSupers();
		String [] supers = new String[superData.size()];
		int i = 0;
		for (SuperMarket s: superData.values()){
			supers[i++] = s.getName() +"- "+s.getAdress();
		}
		return supers;
	}

	/**
	 * @return all the super address
	 */
	public String [] getAllSuperAddress()
	{
		Map<String, SuperMarket> superData =  data.getSupers();
		String [] supers = new String[superData.size()];
		int i = 0;
		for (SuperMarket s: superData.values()){
			supers[i++] = s.getAdress();
		}
		return supers;
	}

	/**
	 * @return all the item names
	 */
	public String [] getAllItemNames()
	{
		Map<String, Item> itemsData =  data.getItems();
		String [] items = new String[itemsData.size()];
		int i = 0;
		for (Item s: itemsData.values()){
			items[i++] = s.getItemName();
		}
		return items;
	}


	/** -----------------------Serializable---------------------------*/
	/**
	 * The method read data form srl file to the system when the system is up
	 */
	public static void readSer()  {
		FileInputStream f_in = null;
		ObjectInputStream inputStream = null;
		try {
			f_in = new FileInputStream("/storage/emulated/0/dbSuperZol/data.srl");
			inputStream= new ObjectInputStream(f_in);
			data=(SysData)inputStream.readObject();
			System.out.println(data.getItems());
		} catch (Exception ex) {
		}
		finally{
			try {
				inputStream.close();
			} catch (Exception e) {
			}
		}
	}
	/**
	 * The method write data form the system to srl file when the system is close
	 */
	public static void writeSer() {
		FileOutputStream f_out = null;
		ObjectOutputStream outputStream = null;
		try {
			SysData sData = ModelLogic.getInstance().data;
			f_out = new FileOutputStream("/storage/emulated/0/dbSuperZol/data.srl");
			outputStream = new ObjectOutputStream(f_out);
			outputStream.writeObject(data);

			/** Upload file to dropbox*/
			String localFilePath=NetworkManager.getInstance().getAppDirName()+"/"+"data.srl";
			NetworkManager.getInstance().uploadRes(localFilePath);
		} catch (Exception ex) {
		}
		finally{
			try {
				outputStream.close();
			} catch (Exception e) {
			}
		}

	}

}

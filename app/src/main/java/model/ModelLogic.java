package model;

import android.widget.Toast;
import android.content.SharedPreferences;
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
	/** Instance of ModelLogic*/
	private static ModelLogic instance = null;
	/** Instance of SysData*/
	public static SysData data;
	/** Instance of market list*/
	public static MarketList marketList;

	/** Contains logos of the supers*/
	private Map <String,Integer> superLogos;

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

		}
		return instance;
	}

	/**
	 * private cto'r
	 */
	private ModelLogic() {
		super();
		data= new SysData();
		marketList = new MarketList();
		superLogos = new HashMap<String, Integer>();
		fillLogo();
	}

	/**
	 * Initialize all logo of supermarkets
	 */
	private void fillLogo()
	{
		superLogos.put("רמי לוי", R.drawable.ramilevi_logo);
		superLogos.put("שופרסל", R.drawable.bitan_logo);
		superLogos.put("יינות ביתן", R.drawable.bitan_logo);
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
	 * @return reference of marketList
	 */
	public MarketList getMarketList(){
		return marketList;
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

	public Integer getLogoBySuperName(String name)
	{
		return superLogos.get(name);
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

	/**
	 * The method read last market list
	 */
	public static void loadMarketList()  {
		FileInputStream f_in = null;
		ObjectInputStream inputStream = null;
		try {
			f_in = new FileInputStream("/storage/emulated/0/dbSuperZol/marketlist.srl");
			inputStream= new ObjectInputStream(f_in);
			marketList=(MarketList)inputStream.readObject();
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
	public static boolean saveMarketList() {
		FileOutputStream f_out = null;
		ObjectOutputStream outputStream = null;
		boolean isSaved = false;
		try {
			SysData sData = ModelLogic.getInstance().data;
			f_out = new FileOutputStream("/storage/emulated/0/dbSuperZol/marketlist.srl");
			outputStream = new ObjectOutputStream(f_out);
			outputStream.writeObject(marketList);
			isSaved = true;
		} catch (Exception ex) {
		}
		finally{
			try {
				outputStream.close();
			} catch (Exception e) {
			}
		}
		return isSaved;

	}

	/**
	 * The method update the db dropbox
	 */
	private void uploadFile() {

		// create testFile
		String localFilePath=NetworkManager.getInstance().getAppDirName()+"/"+"data.srl";
		//File localfile = new File(localFilePath);
		//String content = "abc2";
		//NetworkManager.getInstance().writeLocalCopy(localfile, content.getBytes());

		// try to upload
		NetworkManager.getInstance().uploadRes(localFilePath);

	}

}

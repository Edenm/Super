package model;

public class ModelLogic {
	
	private static ModelLogic instance = null;
	
	public SysData data;

	public static ModelLogic getInstance(){
		if	(instance == null){
			instance = new ModelLogic();
		}
		return instance;
	}
	
	private ModelLogic() {
		super();
		data= new SysData();
	}
	
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
	
	public void addNewSuperMarket(SuperMarket sm){
		if (!data.getSupers().containsKey(sm.getAdress())){
			data.addSuperMarket(sm);
		}
	}
	
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

	public SysData getSysData(){
		return data;
	}

}

package model;

public class SuperMarket {
	
	private String name;
	private String adress;
	//private Map <String,Item> items= new HashMap<>();
	
	public SuperMarket(String name, String adress) {
		super();
		this.name = name;
		this.adress = adress;
	}

	public String getName() {
		return name;
	}

	public String getAdress() {
		return adress;
	}

	//public Map<String, Item> getItems() {
	//	return items;
	//}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof SuperMarket) {
			if (((SuperMarket)obj).adress.equals(adress))
			{
				return true;
			}
		}
		return false;
	}

	public void addNewItemToSuper(Item item){
		
	}

	@Override
	public String toString() {
		return "SuperMarket [name=" + name + ", adress=" + adress + "]";
	}
	
	
}

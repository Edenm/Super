package model;

import java.io.Serializable;

/**
 * This class is represent one Super Market in system
 * SuperMarket class included all data about super market like name and address
 */
public class SuperMarket implements Serializable {

	/** The name of super market*/
	private String name;
	/** The adress of super market*/
	private String adress;
	//private Map <String,Item> items= new HashMap<>();

	/**
	 * Partial cto'r
	 * @param adress
	 */
	public SuperMarket(String adress) {
		super();
		this.adress = adress;
	}

	/**
	 * Full cto'r
	 * @param name
	 * @param adress
	 */
	public SuperMarket(String name, String adress) {
		super();
		this.name = name;
		this.adress = adress;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return adress
	 */
	public String getAdress() {
		return adress;
	}

	//public Map<String, Item> getItems() {
	//	return items;
	//}

	/**
	 * Hash map function
	 * @return hash map value
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		return result;
	}

	/**
	 * @param obj
	 * @return true if two object are equals according to adress
	 */
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

	/**
	 * TBD
	 * @param item
	 */
	public void addNewItemToSuper(Item item){
		
	}

	/**
	 * @return string that represent super market
	 */
	@Override
	public String toString() {
		return "SuperMarket [name=" + name + ", adress=" + adress + "]";
	}
	
	
}

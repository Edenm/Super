package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Item class included all data about item like name, code, price ...
 * is also contains HashMap with all supermarkets that have this item, and the item price
 * in each supermarket.
 */

public class Item {
/*1 */	private Date priceUpdateDate;				//	<PriceUpdateDate>2015-12-29 08:15</PriceUpdateDate>
/*2 */	private String itemCode;					//    <ItemCode>16000177710</ItemCode>
/*3 */												//    <ItemType>1</ItemType>
/*4 */	private String itemName;						//    <ItemName>נייצ'ר וואלי פירות+שקד5י</ItemName>
/*5 */	private String manufacturerName;			//    <ManufacturerName>ג'נרל מילס</ManufacturerName>
/*6 */												//    <ManufactureCountry>US</ManufactureCountry>
/*7 */	private String manufacturerItemDescription;//    <ManufacturerItemDescription>חטיף נייצ'ר ואלי</ManufacturerItemDescription>
/*8 */	private String unitQty;						//    <UnitQty>גרמים</UnitQty>
/*9 */	private Float quantity;						//    <Quantity>175.00</Quantity>
/*10 */												//    <bIsWeighted>0</bIsWeighted>
/*11 */												//    <UnitOfMeasure>100 גרם</UnitOfMeasure>
/*12 */												//    <QtyInPackage>0</QtyInPackage>
/*13 */	private Float itemPrice;					//    <ItemPrice>15.90</ItemPrice>
/*14 */												//    <UnitOfMeasurePrice>9.09</UnitOfMeasurePrice>
/*15 */												//    <AllowDiscount>1</AllowDiscount>
/*16 */												//    <ItemStatus>1</ItemStatus>
		private Map <SuperMarket,Float> prices;

	//semi- constructor
	public Item(String name){
		this.itemName = name;
	}

	//constructor
	public Item(Date priceUpdateDate, String itemCode, String name,
			String manufacturerName, String manufacturerItemDescription,
			String qnitQty, Float quantity, Float itemPrice) {
		super();
		this.priceUpdateDate = priceUpdateDate;
		this.itemCode = itemCode;
		this.itemName = name;
		this.manufacturerName = manufacturerName;
		this.manufacturerItemDescription = manufacturerItemDescription;
		this.unitQty = qnitQty;
		this.quantity = quantity;
		this.itemPrice = itemPrice;
		
		this.prices = new HashMap<SuperMarket,Float>();
	}

	public Item() {
	}

	/**
	 * Getter and Setter
	 * @return
	 */
	public Date getPriceUpdateDate() {
		return priceUpdateDate;
	}


	public String getItemCode() {
		return itemCode;
	}


	public String getItemName() {
		return itemName;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}


	public String getManufacturerItemDescription() {
		return manufacturerItemDescription;
	}


	public String getUnitQty() {
		return unitQty;
	}


	public Float getQuantity() {
		return quantity;
	}


	public Float getItemPrice() {
		return itemPrice;
	}

	

	public Map<SuperMarket, Float> getPrices() {
		return prices;
	}

	/**
	 * hashCode
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemName == null) ? 0 : itemName.hashCode());
		return result;
	}

	/**
	 *
	 * @param obj
	 * @return true if two object are equals according to itemName
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			if (((Item)obj).itemName.equals(getItemName()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * if the item exists update is price if needed else add this item.
	 *
	 * @param sp
	 * @param price
	 *
	 *  */
	public void addUpdatePriceToItem(SuperMarket sp, Float price)
	{
		prices.put(sp, price);
	}

	/**
	 * TBD - this function for compering between prices of same items in diffrenet supers.
	 * @return
	 */
	public Float getBestPrice(){
		Float bestPrice=0.0f;
		//SuperMarket bestSuperMarket=null;
		for (Entry<SuperMarket,Float> item : prices.entrySet()) {
			if (item.getValue()<bestPrice){
				bestPrice=item.getValue();
				//bestSuperMarket=item.getKey();
			}
		}
		return bestPrice;
	}


	@Override
	public String toString() {
		return "Item [ItemCode=" + itemCode + ", name=" + itemName +
				 ", quantity=" + quantity+" "+unitQty + ", itemPrice="
				+ itemPrice + "\n prices=" + pricesToString() + "]";
	}
	
	
	public String pricesToString(){
		String pricesString = "";
		
		for(Entry<SuperMarket,Float> entry : prices.entrySet()){
			pricesString += "Super Name: "+entry.getKey().getName()+ " Adress: "+entry.getKey().getAdress()+
								" Price: "+entry.getValue() +"\n";
		}
		
		return pricesString;
	}
	
}

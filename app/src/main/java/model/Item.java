package model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * This class is represent one item in system
 * Item class included all data about item like name, code, price ...
 * is also contains HashMap with all supermarkets that have this item, and the item price
 * in each supermarket.
 */

public class Item implements Serializable {

/*1 */	private Date priceUpdateDate;				//	<PriceUpdateDate>2015-12-29 08:15</PriceUpdateDate>
/*2 */	private String itemCode;					//    <ItemCode>16000177710</ItemCode>
/*3 */												//    <ItemType>1</ItemType>
/*4 */	private String itemName;					//    <ItemName>נייצ'ר וואלי פירות+שקד5י</ItemName>
/*5 */	private String manufacturerName;			//    <ManufacturerName>ג'נרל מילס</ManufacturerName>
/*6 */												//    <ManufactureCountry>US</ManufactureCountry>
/*7 */	private String manufacturerItemDescription; //    <ManufacturerItemDescription>חטיף נייצ'ר ואלי</ManufacturerItemDescription>
/*8 */	private String unitQty;						//    <UnitQty>גרמים</UnitQty>
/*9 */	private Float quantity;						//    <Quantity>175.00</Quantity>
/*10 */												//    <bIsWeighted>0</bIsWeighted>
/*11 */												//    <UnitOfMeasure>100 גרם</UnitOfMeasure>
/*12 */												//    <QtyInPackage>0</QtyInPackage>
/*13 */	private Float itemPrice;					//    <ItemPrice>15.90</ItemPrice>
/*14 */												//    <UnitOfMeasurePrice>9.09</UnitOfMeasurePrice>
/*15 */												//    <AllowDiscount>1</AllowDiscount>
/*16 */												//    <ItemStatus>1</ItemStatus>

	/** This map contains all prices of this item in all supers */
	private Map <SuperMarket,Float> prices;

	/**
	 * Partial cto'r
	 * @param name
	 */
	public Item(String name)
	{
		this.itemName = name;
	}

	/**
	 * Full cto'r
	 * @param priceUpdateDate
	 * @param itemCode
	 * @param name
	 * @param manufacturerName
	 * @param manufacturerItemDescription
	 * @param qnitQty
	 * @param quantity
	 * @param itemPrice
	 */
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
	 * @return priceUpdateDate
	 */
	public Date getPriceUpdateDate()
	{
		return priceUpdateDate;
	}

	/**
	 * @return itemCode
	 */
	public String getItemCode()
	{
		return itemCode;
	}

	/**
	 * @return itemName
	 */
	public String getItemName()
	{
		return itemName;
	}

	/**
	 * @return manufacturerName
	 */
	public String getManufacturerName()
	{
		return manufacturerName;
	}

	/**
	 * @return manufacturerItemDescription
	 */
	public String getManufacturerItemDescription()
	{
		return manufacturerItemDescription;
	}

	/**
	 * @return unitQty
	 */
	public String getUnitQty()
	{
		return unitQty;
	}

	/**
	 * @return quantity
	 */
	public Float getQuantity()
	{
		return quantity;
	}

	/**
	 * @return itemPrice
	 */
	public Float getItemPrice()
	{
		return itemPrice;
	}

	/**
	 * @return prices
	 */
	public Map<SuperMarket, Float> getPrices()
	{
		return prices;
	}

	/**
	 * HashCode function
	 * @return HashCode result
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
	 * @param sp
	 * @param price
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
		for (Entry<SuperMarket,Float> price : prices.entrySet()) {
			if (price.getValue()<bestPrice){
				bestPrice=price.getValue();
				//bestSuperMarket=item.getKey();
			}
		}
		return bestPrice;
	}

	/**
	 * The method get adress of super
	 * @param superAdress
	 * @return the price of current item on that super
	 */
	public Float getPriceBySuper(String superAdress)
	{
		SuperMarket sm = new SuperMarket(superAdress);
		return prices.get(sm);
	}


	/**
	 * @return string that represent item
	 */
	@Override
	public String toString() {
		return "Item [ItemCode=" + itemCode + ", name=" + itemName +
				 ", quantity=" + quantity+" "+unitQty + ", itemPrice="
				+ itemPrice + "\n prices=" + pricesToString() + "]";
	}

	/**
	 * @return All the prices of the item in string
	 */
	public String pricesToString(){
		String pricesString = "";
		for(Entry<SuperMarket,Float> entry : prices.entrySet()){
			pricesString += "Super Name: "+entry.getKey().getName()+ " Adress: "+entry.getKey().getAdress()+
								" Price: "+entry.getValue() +"\n";
		}
		return pricesString;
	}
	
}

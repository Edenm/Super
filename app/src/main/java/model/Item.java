package model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((itemCode == null) ? 0 : itemCode.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Item) {
			if (((Item)obj).itemCode.equals(getItemCode()))
			{
				return true;
			}
		}
		return false;
	}
	
	public void addUpdatePriceToItem(SuperMarket sp, Float price){
		prices.put(sp, price);
	}
	
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

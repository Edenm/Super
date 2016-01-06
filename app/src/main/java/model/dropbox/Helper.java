package model.dropbox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import model.Item;
import model.ModelLogic;
import model.SuperMarket;

public class Helper {

		public static void readXmlFile(ModelLogic ml, String name)
		{
			 try {
					File fXmlFile = new File(name);
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(fXmlFile);
							
					doc.getDocumentElement().normalize();
							
					NodeList nList = doc.getElementsByTagName("Item");

					for (int temp = 0; temp < nList.getLength(); temp++) {

						Node nNode = nList.item(temp);
						
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
					
							String target = eElement.getElementsByTagName("PriceUpdateDate").item(0).getTextContent();
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date result =  df.parse(target);
							
							Item item = new Item(result,
											     eElement.getElementsByTagName("ItemCode").item(0).getTextContent(),
											     eElement.getElementsByTagName("ItemName").item(0).getTextContent(), 
											     eElement.getElementsByTagName("ManufacturerName").item(0).getTextContent(), 
											     eElement.getElementsByTagName("ManufacturerItemDescription").item(0).getTextContent(), 
											     eElement.getElementsByTagName("UnitQty").item(0).getTextContent(), 
											     Float.valueOf(eElement.getElementsByTagName("Quantity").item(0).getTextContent()), 
											     Float.valueOf(eElement.getElementsByTagName("ItemPrice").item(0).getTextContent()));
							ml.addNewItem(item, "המושבה 7 נשר");
						
						}
					}
				 } catch (Exception e) {
				    e.printStackTrace();
				 }
		}
		
		/* The function write json file of all items */
		public static void writeJasonFileForItem(ModelLogic ml)
		{
			try {
				JSONArray jsonItems = new JSONArray();

				for (Item item : ml.data.getItems().values()) {
					JSONObject jsonItem = new JSONObject();
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String result = df.format(item.getPriceUpdateDate());

					jsonItem.put("PriceUpdateDate", result);
					jsonItem.put("ItemCode", item.getItemCode());
					jsonItem.put("ItemName", item.getItemName());
					jsonItem.put("ManufacturerName", item.getManufacturerName());
					jsonItem.put("ManufacturerItemDescription", item.getManufacturerItemDescription());
					jsonItem.put("UnitQty", item.getUnitQty());
					jsonItem.put("Quantity", item.getQuantity().toString());
					jsonItem.put("ItemPrice", item.getItemPrice().toString());

					JSONArray arrPrices = new JSONArray();
					JSONObject jsonEntry = new JSONObject();

					for (Entry<SuperMarket, Float> entry : item.getPrices().entrySet()) {
						jsonEntry.put("Adress", ((SuperMarket) entry.getKey()).getAdress());
						jsonEntry.put("Price", entry.getValue().toString());

						arrPrices.put(jsonEntry);
					}

					jsonItem.put("Prices", arrPrices);
					jsonItems.put(jsonItem);

				}

				writeFile(jsonItems.toString(), "Items.txt");
			}catch (Exception ex){

			}
		}
		
		/* The function write json file of all Supers */
		public static void writeJasonFileForSuper(ModelLogic ml)
		{
			try{
				JSONArray mapSupers = new JSONArray();

				for (SuperMarket sm : ml.getSysData().getSupers().values()) {
					JSONObject smJson = new JSONObject();
					smJson.put("name", sm.getName());
					smJson.put("adress", sm.getAdress());
					mapSupers.put(smJson);
				}

				writeFile(mapSupers.toString(),"Supermarkets.txt");
			}catch (Exception ex){

			}
		}
		
		public static void readJasonFile(ModelLogic ml){
			JSONTokener readFrom; 
			JSONArray jsonItems;
			
			try{
				/* ---------------- Read Super Markets ---------------------------*/
				String st =readFile("Supermarkets.txt");
				readFrom = new JSONTokener(st);
				jsonItems = new JSONArray(readFrom);
					
				for (int i=0; i<jsonItems.length(); i++) 
				{
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					ml.data.addSuperMarket(new SuperMarket(jsonItem.getString("name"),jsonItem.getString("adress")));
				}
				
				
				/* ---------------- Read Items -----------------------------------*/
				readFrom = new JSONTokener(readFile("Items.txt"));
				jsonItems = new JSONArray(readFrom);
				
				for (int i=0; i<jsonItems.length(); i++) 
				{
					JSONObject jsonItem = jsonItems.getJSONObject(i);
					
					String target = jsonItem.getString("PriceUpdateDate");
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date result =  df.parse(target);
					
					Item item = new Item(result, jsonItem.getString("ItemCode"), jsonItem.getString("ItemName"), jsonItem.getString("ManufacturerName"), jsonItem.getString("ManufacturerItemDescription"), jsonItem.getString("UnitQty"), Float.valueOf(jsonItem.getString("Quantity")), Float.valueOf(jsonItem.getString("ItemPrice")));
					
					JSONArray arrPrices = jsonItem.getJSONArray("Prices");
					
					for (int j=0; j<arrPrices.length(); j++) 
					{
						JSONObject jsonEntry = arrPrices.getJSONObject(j);
						
						String adress = jsonEntry.getString("Adress");
						Float price = Float.valueOf(jsonEntry.getString("Price"));
						SuperMarket sm = ml.data.getSupers().get(adress);
						
						item.addUpdatePriceToItem(sm, price);
						
						ml.data.addItem(item);
					}
					
					
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
		
		private static void writeFile(String st, String fileName){
			FileOutputStream fos = null;
			PrintWriter pw = null;
			
			//if (Environment)
			try {
				File rootDir = new File("C:/Users/Eden/AndroidStudioProjects/Super/app/src/main/assets/");
			
				fos = new FileOutputStream(new File(rootDir, fileName));
				pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(fos)));
				
				pw.println(st);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (pw != null){
					pw.close();
				}
				if (fos !=null){
					try {
						fos.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
					
			}
		}
		
		private static String readFile(String fileName){
			FileInputStream fis = null;
			BufferedReader br = null;
			StringBuffer sb = new StringBuffer();

			//if (Environment)
			try {

				File rootDir = new File("C:/Users/Eden/AndroidStudioProjects/Super/app/src/main/assets/");

				fis = new FileInputStream(new File(rootDir, fileName));
				br = new BufferedReader(new InputStreamReader(fis));
				
				String line = "";
				while (null != (line = br.readLine()))
				{
					sb.append(line + "\n");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try {
					if (br != null){
						
							br.close();
					}
					if (fis !=null){
							
								fis.close();
							
					}
				} catch (Exception e) {
				}
					
			}
			return sb.toString();
		}
}

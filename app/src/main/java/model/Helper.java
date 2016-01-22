package model;

import android.content.Intent;

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
import java.util.ArrayList;
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

import ViewLogic.slidingmenu.R;
import model.Item;
import model.ModelLogic;
import model.SuperMarket;
import model.dropbox.manager.NetworkManager;

/**
 * This class is handle in all read and write local db from files in device into db in app
 */
public class Helper {

	/**
	 * This method get folder name of super with xml files and read all of them to specified super
	 * @param folderName
	 * @param superAdress
	 */
	public static void readAllXml(String folderName, String superAdress)
	{
		ModelLogic ml = ModelLogic.getInstance();
		File fXmlFileDir = new File("/storage/emulated/0/dbSuperZol/"+folderName+"/");
		for (final File fileEntry : fXmlFileDir.listFiles()) {
			readXmlFile(ml, fileEntry, superAdress,folderName);
		}
	}


	/**
	 * This method get instance of model logic and the name of xml file,
	 * The method read all the data from the xml into the model logic Structures
	 * @param ml
	 * @param fXmlFile
	 * @param superAdress
	 * @param typeOfSuper
	 */
		public static void readXmlFile(ModelLogic ml, File fXmlFile, String superAdress, String typeOfSuper)
		{
			 try {
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
							if (typeOfSuper.equals("Ramilevi"))
							{
								df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							}
							if (typeOfSuper.equals("Shopersal"))
							{
								df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							}


							Date result =  df.parse(target);
							
							Item item = new Item(result,
											     eElement.getElementsByTagName("ItemCode").item(0).getTextContent(),
											     eElement.getElementsByTagName("ItemName").item(0).getTextContent(), 
											     eElement.getElementsByTagName("ManufacturerName").item(0).getTextContent(), 
											     eElement.getElementsByTagName("ManufacturerItemDescription").item(0).getTextContent(), 
											     eElement.getElementsByTagName("UnitQty").item(0).getTextContent(), 
											     Float.valueOf(eElement.getElementsByTagName("Quantity").item(0).getTextContent()), 
											     Float.valueOf(eElement.getElementsByTagName("ItemPrice").item(0).getTextContent()));

							ml.addNewItem(item, (superAdress));
						
						}
					}

					 /** After finish to read xml need to write new ser file to dropbox */
					 ml.writeSer();
				 } catch (Exception e) {
				    e.printStackTrace();
				 }
		}

	/**
	 * This method get instance of model logic,
	 * The method write all the data of items to the json file of the items (Items.txt) from the model logic Structures
	 * @use writeFile(String st, String fileName) to write data to new file
	 * @param ml
	 */
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

	/**
	 * This method get instance of model logic,
	 * The method write all the data of supers to the json file of the supermarkets (Supermarkets.txt) from the model logic Structures
	 * @use writeFile(String st, String fileName) to write data to new file
	 * @param ml
	 */
		/* The function write json file of all Supers */
		public static void writeJasonFileForSuper(ModelLogic ml)
		{
			try{
				JSONArray mapSupers = new JSONArray();

				for (SuperMarket sm : ml.getSysData().getSupers().values()) {
					JSONObject smJson = new JSONObject();
					smJson.put("name", sm.getName());
					smJson.put("adress", sm.getAdress());
					smJson.put("lat", sm.getLat().toString());
					smJson.put("lon", sm.getLon().toString());
					mapSupers.put(smJson);
				}

				writeFile(mapSupers.toString(),"Supermarkets.txt");
			}catch (Exception ex){

			}
		}

	/**
	 * This method get instance of model logic,
	 * The method read all the data of supers and items from
	 * the json file of the supermarkets (Supermarkets.txt) and the json file of the items (Items.txt)
	 * to the model logic Structures
	 * @use  readFile(String fileName) to get the all data (as string) from file
	 * @param ml
	 */
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
					ml.data.addSuperMarket(new SuperMarket(jsonItem.getString("name"),jsonItem.getString("adress"),Double.valueOf(jsonItem.getString("lat")),Double.valueOf(jsonItem.getString("lon"))));
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

	/**
	 * This method get string and create local file with that string
	 * @param st
	 * @param fileName
	 */
		private static void writeFile(String st, String fileName){
			FileOutputStream fos = null;
			PrintWriter pw = null;

			try {
				File rootDir = new File("/storage/emulated/0/dbSuperZol/");
			
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

	/**
	 * This method get file name and return all the data in this file as a string
	 * @param fileName
	 */
		private static String readFile(String fileName){
			FileInputStream fis = null;
			BufferedReader br = null;
			StringBuffer sb = new StringBuffer();

			try {

				File file = new File("/storage/emulated/0/dbSuperZol/", fileName);//new File(name);

				fis = new FileInputStream(file);
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

	/*
	 * Calculate distance between two points in latitude and longitude taking
	 * into account height difference. If you are not interested in height
	 * difference pass 0.0. Uses Haversine method as its base.
	 *
	 * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
	 * el2 End altitude in meters
	 * @returns Distance in Meters
	 */
	public static Double getDistance(double lat1, double lat2, double lon1, double lon2, double el1, double el2) {

		final int R = 6371; // Radius of the earth

		Double latDistance = Math.toRadians(lat2 - lat1);
		Double lonDistance = Math.toRadians(lon2 - lon1);
		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
				+ Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
				* Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = R * c ; // convert to meters

		double height = el1 - el2;

		distance = Math.pow(distance, 2) + Math.pow(height, 2);

		return Math.sqrt(distance);
	}
}


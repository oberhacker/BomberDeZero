package xnetcom.bomber.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xnetcom.bomber.BomberGame;

public class ParserTMX {

	BomberGame context;
	public ParserTMX(BomberGame context){
		this.context=context;
	}
	
	
	public void getDatosMapa(int mapa){
		String rutamapa ="tmx/mapa0.tmx";
		rutamapa =rutamapa.replace("mapa0", "mapa"+mapa);
		
		
		Document dom=null;
		DocumentBuilder builder=null;
		
		
		try {
			InputStream inputStream = context.getAssets().open(rutamapa);
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			dom = builder.parse(inputStream);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		// leemos las tiles que tiene propiedades
		ArrayList<TileProperties> propiedades=new ArrayList<ParserTMX.TileProperties>();
		
		Element nodoMapa = (Element) dom.getElementsByTagName("tileset").item(0);
		
		NodeList tilesConPropiedades =  dom.getElementsByTagName("tile");
		for (int i = 0; i < tilesConPropiedades.getLength(); i++) {
			Element tileConPropiedades = (Element)tilesConPropiedades.item(i);	
			
			
			try{
				int tileId=Integer.parseInt(tileConPropiedades.getAttributes().getNamedItem("id").getNodeValue());
				String name =tileConPropiedades.getElementsByTagName("property").item(0).getAttributes().getNamedItem("name").getNodeValue();
				String value =tileConPropiedades.getElementsByTagName("property").item(0).getAttributes().getNamedItem("value").getNodeValue();	
				TileProperties prop= new TileProperties(tileId, name, value);
				propiedades.add(prop);
			}catch (Exception e){
				break;
			}

		}		
		
		System.out.println();
		
	}
	
	
	public class TileProperties{
		
		public int id;
		public String name;
		public String value;
		
		public TileProperties(int id, String name, String value) {
			this.id=id;
			this.value=value;
			this.name=name;
		}
	}
	
	
	
}

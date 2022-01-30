package xnetcom.bomber.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xnetcom.bomber.BomberGame;
import android.content.res.AssetManager;

public class MiTMXMap {

	private Document dom;
	private DocumentBuilder builder;
	private BomberGame context;

	private final AssetManager mAssetManager;
	
	public SpriteGroup spriteGroupSuelo;

	public MiTMXMap(BomberGame context) {
		this.context = context;
		mAssetManager = context.getAssets();
	}

	public void cargaMapa(String rutamapa) {
		
		BitmapTextureAtlas bta = new BitmapTextureAtlas(context.getTextureManager(),1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		TiledTextureRegion pTexture=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bta, context, "gfx/tileset64.png",0,0,13, 12);
		bta.load();
		
		spriteGroupSuelo = new SpriteGroup(bta,400,context.getVertexBufferObjectManager());	
		spriteGroupSuelo.setOffsetCenter(0, 0);
		spriteGroupSuelo.setScaleCenter(0, 0);
		spriteGroupSuelo.setZIndex(-1);
		
		try {
			InputStream inputStream = this.mAssetManager.open(rutamapa);
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

		Element nodoMapa = (Element) dom.getElementsByTagName("map").item(0);
		NodeList capas =  dom.getElementsByTagName("layer");
		Element layerSuelo = (Element)capas.item(0);
		NodeList tiles = layerSuelo.getElementsByTagName("tile");
//		for (int i = 0; i < tiles.getLength(); i++) {
//			Element elementTile = (Element)tiles.item(i);
//			
//			TileCoordenada coordenadas = getCoordenadas(i);
//			int gid =Integer.parseInt(elementTile.getAttribute("gid"));
//			final TiledSprite lSprite = new TiledSprite(0, 0, pTexture.deepCopy(),context.getVertexBufferObjectManager());
//			lSprite.setOffsetCenter(0, 0);
//			lSprite.setCurrentTileIndex(56);
//			lSprite.setPosition(coordenadas.getX(), coordenadas.getY());
//			lSprite.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO-1);
//			spriteGroupSuelo.attachChild(lSprite);			
//			
//		}
		
		for (int columna = 0; columna < 25; columna++) {
			for (int fila = 0; fila < 15; fila++) {				
				
				TileCoordenada mcoordenadas= new TileCoordenada(columna, fila);
				final TiledSprite lSprite = new TiledSprite(0, 0, pTexture.deepCopy(),context.getVertexBufferObjectManager());
				lSprite.setOffsetCenter(0, 0);
				lSprite.setCurrentTileIndex(56);
				lSprite.setPosition(mcoordenadas.getX(), mcoordenadas.getY());
//				lSprite.setZIndex(Constantes.ZINDEX_BOMBERMAN_ABAJO-1);
				spriteGroupSuelo.attachChild(lSprite);		
			}		
		}
		
		spriteGroupSuelo.setScaleX(Constantes.FARTOR_FORMA);
		
		
		
		
		System.out.println();
	}

	
	
	public TileCoordenada getCoordenadas(int tileNum){
		Double nTileNum= Double.valueOf(tileNum);
		Double division = nTileNum/25;
		Double parteDecimal = division % 1;
		Double parteEntera = division - parteDecimal;
		int fila =parteEntera.intValue();
		int columna = Double.valueOf((parteDecimal*25)).intValue();		
		return new TileCoordenada(columna,fila);
	}
	
	
	
	
	public class TileCoordenada {
		int columna;
		int fila;
		public TileCoordenada(int columna, int fila) {
			this.columna=columna;
			this.fila=fila;
		}
		
		public int getY(){			
			int yFila=(14-fila)*64;
			return yFila;
		}
		public int getX(){
			int xColumna=columna*64;
			return xColumna;
		}
		
	}

}

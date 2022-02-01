package xnetcom.bomber.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
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
import android.util.Log;

public class MiTMXMap {

	private Document dom;
	private DocumentBuilder builder;
	private BomberGame context;

	private final AssetManager mAssetManager;
	
	public SpriteGroup spriteGroupSuelo;
	public SpriteGroup piedrasSombra;

	public MiTMXMap(BomberGame context) {
		this.context = context;
		mAssetManager = context.getAssets();
	}

	public void cargaMapa(String rutamapa) {
		
		BitmapTextureAtlas bta = new BitmapTextureAtlas(context.getTextureManager(),1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		TiledTextureRegion pTexture=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bta, context, "tmx/gfx/tileset642.png",0,0,13, 12);
		bta.load();
		
		spriteGroupSuelo = new SpriteGroup(bta,400,context.getVertexBufferObjectManager());	
		spriteGroupSuelo.setOffsetCenter(0, 0);
		spriteGroupSuelo.setScaleCenter(0, 0);
		spriteGroupSuelo.setZIndex(Constantes.ZINDEX_CAPA_SUELO);
		
		
		piedrasSombra = new SpriteGroup(bta,400,context.getVertexBufferObjectManager());	
		piedrasSombra.setOffsetCenter(0, 0);
		piedrasSombra.setScaleCenter(0, 0);
		piedrasSombra.setZIndex(Constantes.ZINDEX_CAPA_PIEDRAS_SOMBRA);
		
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
		NodeList tilesSuelo = layerSuelo.getElementsByTagName("tile");
		
		for (int i = 0; i < tilesSuelo.getLength(); i++) {
			Element elementTile = (Element)tilesSuelo.item(i);
			TileCoordenada coordenadas = getCoordenadas(i);			
			int gid =Integer.parseInt(elementTile.getAttribute("gid"));
			if (gid!=0){
				final TiledSprite lSprite = new TiledSprite(0, 0, pTexture.deepCopy(),context.getVertexBufferObjectManager());
				lSprite.setOffsetCenter(0, 0);
				lSprite.setCurrentTileIndex(gid-1);
				lSprite.setPosition(coordenadas.getX(), coordenadas.getY());
				spriteGroupSuelo.attachChild(lSprite);	
			}
		
		}		
		spriteGroupSuelo.setScaleX(Constantes.FARTOR_FORMA);
		
		Element layerPiedrasSombra = (Element)capas.item(1);
		NodeList tilesPiedrasSombra= layerPiedrasSombra.getElementsByTagName("tile");
		
		for (int i = 0; i < tilesPiedrasSombra.getLength(); i++) {
			
			if (i==77){//77 es la sombra de la derecha fila 3 columna 2
				System.out.println();
			}
			
			Element elementTile = (Element)tilesPiedrasSombra.item(i);
			TileCoordenada coordenadas = getCoordenadas(i);			
			int gid =Integer.parseInt(elementTile.getAttribute("gid"));
			if (gid!=0){
				final TiledSprite lSprite = new TiledSprite(0, 0, pTexture.deepCopy(),context.getVertexBufferObjectManager());
				lSprite.setOffsetCenter(0, 0);
				lSprite.setCurrentTileIndex(gid-1);
				lSprite.setPosition(coordenadas.getX(), coordenadas.getY());
				piedrasSombra.attachChild(lSprite);	
			}
		
		}		
		piedrasSombra.setScaleX(Constantes.FARTOR_FORMA);
		
		

	}

	
	public void onSceneCreated(){
//		context.escenaJuego.scene.attachChild(spriteGroupSuelo);
//		context.escenaJuego.scene.attachChild(piedrasSombra);
		
	}

	
	public TileCoordenada getCoordenadas(int tileNum){
		BigDecimal bgTileNum= BigDecimal.valueOf(tileNum);
		BigDecimal division = bgTileNum.divide(BigDecimal.valueOf(25));
		BigDecimal parteEntera=BigDecimal.valueOf(division.intValue());
		BigDecimal parteDecimal=division.subtract(parteEntera);
		
		int fila=parteEntera.intValue();
		int columna = parteDecimal.multiply(BigDecimal.valueOf(25)).intValue();
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

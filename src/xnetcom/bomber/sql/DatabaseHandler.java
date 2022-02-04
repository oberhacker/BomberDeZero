package xnetcom.bomber.sql;

import xnetcom.bomber.BomberGame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "mapasDB";

	// Contacts table name
	private static final String TABLA_MAPAS = "MAPAS";

	// Contacts Table Columns names
	private static final String MAPA = "mapa";
	private static final String M_BOMBA = "m_bomba";
	private static final String M_CORAZON = "m_corazon";
	private static final String M_CORRER = "m_correr";
	private static final String M_DETONADOR = "m_detonador";
	private static final String M_FANTASMA = "m_fantasma";
	private static final String M_FUERZA = "m_fuerza";
	private static final String M_POTENCIADOR = "m_potenciador";	
	private static final String ESTRELLAS = "estrellas";
	
	
	
	private static final String ENEMIGO_MOCO = "ENEMIGO_MOCO";	
	private static final String ENEMIGO_MONEDA="ENEMIGO_MONEDA";
	private static final String ENEMIGO_GOTA="ENEMIGO_GOTA";
	private static final String ENEMIGO_GLOBO="ENEMIGO_GLOBO";
	private static final String ENEMIGO_FANTASMA="ENEMIGO_FANTASMA";
	private static final String ENEMIGO_GOTANARANJA="ENEMIGO_GOTANARANJA";
	private static final String ENEMIGO_GLOBOAZUL="ENEMIGO_GLOBOAZUL";
	private static final String ENEMIGO_MOCOROJO="ENEMIGO_MOCOROJO";
	private static final String ENEMIGO_MONEDAMARRON="ENEMIGO_MONEDAMARRON";
	private static final String ENEMIGO_GOTAROJA="ENEMIGO_GOTAROJA";
	
	
	

	
	private BomberGame context;

	public DatabaseHandler(BomberGame context) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context= context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLA_MAPAS + 
					" ("
					+ MAPA + " INTEGER PRIMARY KEY, "	
					+ ESTRELLAS +" INTEGER, "
					+ M_BOMBA + " INTEGER, "
					+ M_CORAZON + " INTEGER, "
					+ M_CORRER + " INTEGER, "
					+ M_DETONADOR + " INTEGER, "
					+ M_FANTASMA + " INTEGER, "
					+ M_FUERZA + " INTEGER, "
					+ M_POTENCIADOR + " INTEGER, "
					+ ENEMIGO_MOCO + " INTEGER, "
					+ ENEMIGO_MONEDA + " INTEGER, "
					+ ENEMIGO_GOTA + " INTEGER, "
					+ ENEMIGO_GLOBO + " INTEGER, "
					+ ENEMIGO_FANTASMA + " INTEGER, "
					+ ENEMIGO_GOTANARANJA + " INTEGER, "
					+ ENEMIGO_GLOBOAZUL + " INTEGER, "
					+ ENEMIGO_MOCOROJO + " INTEGER, "
					+ ENEMIGO_MONEDAMARRON + " INTEGER, "
					+ ENEMIGO_GOTAROJA + " INTEGER "

					+ ")";
		System.out.println(CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLA_MAPAS);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	
	
	public void addMapa(DatosMapa datos) {
		Log.d("sql", "addMapa getNumeroMapa ="+datos.getNumeroMapa());
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(MAPA, datos.getNumeroMapa()); 
		values.put(ESTRELLAS, datos.getEstrellas());
		values.put(M_BOMBA, datos.getM_bomba()); 
		values.put(M_CORAZON, datos.getM_corazon()); 
		values.put(M_CORRER, datos.getM_correr()); 
		values.put(M_DETONADOR, datos.getM_detonador()); 
		values.put(M_FANTASMA, datos.getM_fantasma()); 
		values.put(M_FUERZA, datos.getM_fuerza()); 
		values.put(M_POTENCIADOR, datos.getM_potenciador()); 
		
		values.put(ENEMIGO_MOCO, datos.getEnemigo_moco()); 
		values.put(ENEMIGO_MONEDA, datos.getEnemigo_moneda()); 
		values.put(ENEMIGO_GOTA, datos.getEnemigo_gota()); 
		values.put(ENEMIGO_GLOBO, datos.getEnemigo_globo()); 
		values.put(ENEMIGO_FANTASMA, datos.getEnemigo_fantasma()); 
		values.put(ENEMIGO_GOTANARANJA, datos.getEnemigo_gotaNaranja()); 
		values.put(ENEMIGO_GLOBOAZUL, datos.getEnemigo_globoAzul()); 
		values.put(ENEMIGO_MOCOROJO, datos.getEnemigo_mocoRojo()); 
		values.put(ENEMIGO_MONEDAMARRON, datos.getEnemigo_monedaMarron()); 
		values.put(ENEMIGO_GOTAROJA, datos.getEnemigo_gotaRoja()); 
		
		db.insert(TABLA_MAPAS, null, values);
		db.close(); // Closing database connection		
		
		
	}
	
	public void desbloqueaMapa(int numMapa) {
		if (numMapa>=46)return;
		try {
			DatosMapa datos = getMapa(numMapa);
			if (datos.getEstrellas() == -1) {
				datos.setEstrellas(0);
				actualizaMapa(datos);
			}
		} catch (Exception e) {
		}
	}
	
	
	public void actualizaMapa(DatosMapa datos){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(ESTRELLAS, datos.getEstrellas());
		// updating row
		db.update(TABLA_MAPAS, values, MAPA + " = ?",	new String[] { String.valueOf(datos.getNumeroMapa())});
		db.close(); // Closing database connection	
	}
	
	
	public DatosMapa getMapa(int numeroMapa) {
		Log.d("sql", "DatosMapa getMapa(int numeroMapa)"+numeroMapa);
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String[] Recuperar =new String[] { MAPA,ESTRELLAS, M_BOMBA,M_CORAZON,M_CORRER,M_DETONADOR,M_FANTASMA, M_FUERZA,M_POTENCIADOR,ENEMIGO_MOCO
	    		,ENEMIGO_MONEDA,ENEMIGO_GOTA,ENEMIGO_GLOBO,ENEMIGO_FANTASMA,ENEMIGO_GOTANARANJA,ENEMIGO_GLOBOAZUL,ENEMIGO_MOCOROJO,ENEMIGO_MONEDAMARRON,ENEMIGO_GOTAROJA};
	    
	    Cursor cursor = db.query(TABLA_MAPAS,Recuperar , MAPA + "=?", new String[] { String.valueOf(numeroMapa) }, null, null, null, null);
	    System.out.println("cursor"+cursor.toString());
	    DatosMapa mapa= new DatosMapa();
	    if (cursor != null && cursor.moveToFirst()){	    	
			mapa.setNumeroMapa(cursor.getInt(0));
			mapa.setEstrellas(cursor.getInt(1));
			mapa.setM_bomba(cursor.getInt(2));
			mapa.setM_corazon(cursor.getInt(3));
			mapa.setM_correr(cursor.getInt(4));
			mapa.setM_detonador(cursor.getInt(5));
			mapa.setM_fantasma(cursor.getInt(6));
			mapa.setM_fuerza(cursor.getInt(7));
			mapa.setM_potenciador(cursor.getInt(8));
			mapa.setEnemigo_moco(cursor.getInt(9));
			mapa.setEnemigo_moneda(cursor.getInt(10));
			mapa.setEnemigo_gota(cursor.getInt(11));
			mapa.setEnemigo_globo(cursor.getInt(12));
			mapa.setEnemigo_fantasma(cursor.getInt(13));
			
			mapa.setEnemigo_gotaNaranja(cursor.getInt(14));
			mapa.setEnemigo_globoAzul(cursor.getInt(15));
			mapa.setEnemigo_mocoRojo(cursor.getInt(16));
			mapa.setEnemigo_monedaMarron(cursor.getInt(17));
			mapa.setEnemigo_gotaRoja(cursor.getInt(18));
			
			}else{
				//Debug.e("no deberia entrar aki deberian estar todos los mapas inicializados ");
			}	  
	    db.close(); // Closing database connection	
	    Log.d("sql", "DatosMapa retorno getNumeroMapa "+mapa.getNumeroMapa());
	    return mapa;
	}
}

package xnetcom.bomber.sql;



public class DatosMapa {

	private int numeroMapa=0;
	private int estrellas=-1;
	private int m_bomba=0;
	private int m_corazon=0;
	private int m_correr=0;
	private int m_detonador=0;
	private int m_fantasma=0;
	private int m_fuerza=0;
	private int m_potenciador=0;

	
	
	private int enemigo_moco=0;
	private int enemigo_moneda=0;
	private int enemigo_gota=0;
	private int enemigo_globo=0;
	private int enemigo_fantasma=0;
	private int enemigo_gotaNaranja=0;
	private int enemigo_globoAzul=0;
	private int enemigo_mocoRojo=0;
	private int enemigo_monedaMarron=0;
	private int enemigo_gotaRoja=0;


	

	
	public int getEnemigo_moco() {
		return enemigo_moco;
	}

	public void setEnemigo_moco(int enemigo_moco) {
		this.enemigo_moco = enemigo_moco;
	}

	public int getEnemigo_moneda() {
		return enemigo_moneda;
	}

	public void setEnemigo_moneda(int enemigo_moneda) {
		this.enemigo_moneda = enemigo_moneda;
	}

	public int getEnemigo_gota() {
		return enemigo_gota;
	}

	public void setEnemigo_gota(int enemigo_gota) {
		this.enemigo_gota = enemigo_gota;
	}

	public int getEnemigo_globo() {
		return enemigo_globo;
	}

	public void setEnemigo_globo(int enemigo_globo) {
		this.enemigo_globo = enemigo_globo;
	}

	public int getEnemigo_fantasma() {
		return enemigo_fantasma;
	}

	public void setEnemigo_fantasma(int enemigo_fantasma) {
		this.enemigo_fantasma = enemigo_fantasma;
	}

	public int getEnemigo_gotaNaranja() {
		return enemigo_gotaNaranja;
	}

	public void setEnemigo_gotaNaranja(int enemigo_gotaNaranja) {
		this.enemigo_gotaNaranja = enemigo_gotaNaranja;
	}

	public int getEnemigo_globoAzul() {
		return enemigo_globoAzul;
	}

	public void setEnemigo_globoAzul(int enemigo_globoAzul) {
		this.enemigo_globoAzul = enemigo_globoAzul;
	}

	public int getEnemigo_mocoRojo() {
		return enemigo_mocoRojo;
	}

	public void setEnemigo_mocoRojo(int enemigo_mocoRojo) {
		this.enemigo_mocoRojo = enemigo_mocoRojo;
	}

	public int getEnemigo_monedaMarron() {
		return enemigo_monedaMarron;
	}

	public void setEnemigo_monedaMarron(int enemigo_monedaMarron) {
		this.enemigo_monedaMarron = enemigo_monedaMarron;
	}

	public int getEnemigo_gotaRoja() {
		return enemigo_gotaRoja;
	}

	public void setEnemigo_gotaRoja(int enemigo_gotaRoja) {
		this.enemigo_gotaRoja = enemigo_gotaRoja;
	}

	public  int getBoosterTotales(){
		return (m_bomba+m_corazon+m_correr+m_detonador+m_fantasma+m_fuerza+m_potenciador);
	}

	public int getEnemigosTotal(){
		return enemigo_moco+enemigo_moneda+enemigo_gota+enemigo_globo+enemigo_fantasma+enemigo_gotaNaranja+enemigo_globoAzul+enemigo_mocoRojo+enemigo_monedaMarron+enemigo_gotaRoja;
	}
	
	public int getNumeroMapa() {
		return numeroMapa;
	}
	public int getEstrellas() {
		return estrellas;
	}

	public int getM_bomba() {
		return m_bomba;
	}
	public int getM_corazon() {
		return m_corazon;
	}
	public int getM_correr() {
		return m_correr;
	}
	public int getM_detonador() {
		return m_detonador;
	}
	public int getM_fantasma() {
		return m_fantasma;
	}
	public int getM_fuerza() {
		return m_fuerza;
	}
	public int getM_potenciador() {
		return m_potenciador;
	}
	
	public void setNumeroMapa(int numeroMapa) {
		this.numeroMapa = numeroMapa;
		
	}
	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;		
	}

	public void setM_bomba(int m_bomba) {
		this.m_bomba = m_bomba;		
	}
	public void setM_corazon(int m_corazon) {
		this.m_corazon = m_corazon;		
	}
	public void setM_correr(int m_correr) {
		this.m_correr = m_correr;		
	}
	public void setM_detonador(int m_detonador) {
		this.m_detonador = m_detonador;		
	}
	public void setM_fantasma(int m_fantasma) {
		this.m_fantasma = m_fantasma;		
	}
	public void setM_fuerza(int m_fuerza) {
		this.m_fuerza = m_fuerza;		
	}
	public void setM_potenciador(int m_potenciador) {
		this.m_potenciador = m_potenciador;		
	}



	
	
}

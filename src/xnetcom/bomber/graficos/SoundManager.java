package xnetcom.bomber.graficos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.entidades.Bomba;

public class SoundManager {

	public BomberGame context;

	private Sound bomset;
	private Sound estrellas;

	private ArrayList<Sound> explosion = new ArrayList<Sound>();
	AtomicInteger nexExplosion = new AtomicInteger(0);
	
	private Sound monedasSound;
	private Sound campanaFinal;
	private Sound pasosB;
	private Sound mecha;

	boolean sonido = true;

	public SoundManager(BomberGame context) {
		this.context = context;
	}

	public void carga() {

		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.monedasSound = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "ITEM_GET.wav");
			this.bomset = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "SE_08.wav");
			this.campanaFinal = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "arpa.mp3");
			for (int i = 0; i < 15; i++) {
				Sound mexplosion = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "BOM_11_M_bajos.wav");
				explosion.add(mexplosion);
			}

			this.estrellas = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "estrellas.mp3");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		this.estrellas.setVolume(1);
		bomset.setVolume(1);
		// musica1.setVolume(0.1f);

		for (Sound explosion : explosion) {
			explosion.setVolume(2f);
		}

		MusicFactory.setAssetBasePath("mfx/");
		try {
			// this.musica1 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "410140_Bomberman_2___Music.mp3");
			// this.musica2 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "ARYX.mp3");
			// this.musica3 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "BLeH.mp3");
			// this.musica4 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "BRD.mp3");
			// this.musica5 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "DESPERATE .mp3");
			// this.musica6 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "FFF.mp3");
			// this.musica7 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "iNFERNO.mp3");
			// this.musica8 =
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "tPORt.mp3");
			// this.booster=
			// MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(),
			// context, "booster.mp3");

			this.mecha = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "mecha.mp3");
			mecha.setLooping(true);

		} catch (final IOException e) {
			e.printStackTrace();
		}

		// jukebox();
		// cargarPreferencias();

	}

	public void plantaBomba() {
		if (sonido) {
			try {
				bomset.play();
			} catch (Exception e) {
			}
		}
	}

	

	public void sonidoExplosion() {
		if (sonido) {
			try {
				if (nexExplosion.get() >= explosion.size()-2) {
					nexExplosion.set(0);
				}
				explosion.get(nexExplosion.getAndIncrement()).play();
			} catch (Exception e) {
				Log.e("Errrorrrrrrrrrrrrrrr", "", e);
			}
		}
	}

	int bombas = 0;

	public int sonarMecha() {
		// float vol= Preferencias.leerPreferenciasInt("sound_level");
		// if (vol==-1){
		// vol=5;
		// }
		// mecha.setVolume((vol/10)*2);
		if (bombas == 0)
			mecha.play();
		bombas++;
		System.out.println("bombas" + bombas);
		return bombas;
	}

	public int pararMecha() {

		bombas--;
		System.out.println("bombas" + bombas);
		if (bombas == 0) {
			mecha.pause();
		}
		return bombas;
	}

}

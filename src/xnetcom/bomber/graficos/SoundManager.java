package xnetcom.bomber.graficos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.preferencias.Preferencias;
import xnetcom.bomber.util.Util;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class SoundManager {

	public BomberGame context;

	private ArrayList<Sound> bomset = new ArrayList<Sound>();;
	private Sound estrellas;

	private ArrayList<Sound> explosion = new ArrayList<Sound>();
	AtomicInteger nexExplosion = new AtomicInteger(0);
	AtomicInteger nexBomset = new AtomicInteger(0);

	private Sound monedasSound;
	private Sound campanaFinal;
	private Sound pasosB;
	private Sound mecha;

	boolean sonido = true;

	float pasosVolumenBase = 0.3f;
	float explosionVolumenBase = 2;
	float bomsetVolumentBase = 1;
	float estrellasVolumenBase = 1;
	float mechaVolumenBase = 1;

	float monedaVolumenBase = 0.5f;

	float soundVolumen = 5;
	float musicVolumen = 5;

	float volumenBase = 5;

	public Music musica1;
	public Music musica2;
	public Music musica3;
	public Music musica4;
	public Music musica5;
	public Music musica6;
	public Music musica7;
	public Music musica8;
	public Music booster;

	public SoundManager(BomberGame context) {
		this.context = context;
	}

	public void aplicaVolumen() {
		for (Sound explosion : explosion) {
			explosion.setVolume(explosionVolumenBase * (soundVolumen / volumenBase));
		}

		for (Sound bom : bomset) {
			bom.setVolume(bomsetVolumentBase * (soundVolumen / volumenBase));
		}

		pasosB.setVolume(pasosVolumenBase * (soundVolumen / volumenBase));
		mecha.setVolume(mechaVolumenBase * (soundVolumen / volumenBase));
		monedasSound.setVolume(monedaVolumenBase * (soundVolumen / volumenBase));
		estrellas.setVolume(estrellasVolumenBase * (soundVolumen / volumenBase));

		musica1.setVolume(1 * (musicVolumen / volumenBase));
		musica2.setVolume(1 * (musicVolumen / volumenBase));
		musica3.setVolume(1 * (musicVolumen / volumenBase));
		musica4.setVolume(1 * (musicVolumen / volumenBase));
		musica5.setVolume(1 * (musicVolumen / volumenBase));
		musica6.setVolume(1 * (musicVolumen / volumenBase));
		musica7.setVolume(1 * (musicVolumen / volumenBase));
		musica8.setVolume(1 * (musicVolumen / volumenBase));
		booster.setVolume(1 * (musicVolumen / volumenBase));

	}

	public void playBooster(){
		if (musica1.isPlaying()){
			try{musica1.pause();}catch(Exception e){};
		}
		if (musica2.isPlaying()){
			try{musica2.pause();}catch(Exception e){};
		}
		if (musica3.isPlaying()){
			try{musica3.pause();}catch(Exception e){};
		}
		if (musica4.isPlaying()){
			try{musica4.pause();}catch(Exception e){};
		}
		if (musica5.isPlaying()){
			try{musica5.pause();}catch(Exception e){};
		}
		if (musica6.isPlaying()){
			try{musica6.pause();}catch(Exception e){};
		}
		if (musica7.isPlaying()){
			try{musica7.pause();}catch(Exception e){};
		}
		if (musica8.isPlaying()){
			try{musica8.pause();}catch(Exception e){};
		}
		booster.seekTo(0);
		booster.play();
	}



	public void playMusicaRandom(){
		Log.i("mierda", "playMusicaRandom");
		int aleatorio =Util.tomaDecision(1, 8);
		if (aleatorio!=1)aleatorio =Util.tomaDecision(1, 8);
		if (aleatorio!=1)aleatorio =Util.tomaDecision(1, 8);
		stopMusica();
		playMusica(aleatorio);
	}

	public void jukebox() {

		musica1.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(1);
			}
		});
		musica2.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(2);
			}
		});

		musica3.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(3);
			}
		});
		musica4.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(4);
			}
		});
		musica5.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(5);
			}
		});
		musica6.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(6);
			}
		});
		musica7.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(7);
			}
		});
		musica8.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(8);
			}
		});
		booster.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoBooster();
			}
		});

	}

	public void terminadoCancion(final int cancion){		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.i("mierda", "terminado cancion");
				int aleatorio =cancion;
				while(aleatorio==cancion){
					aleatorio =Util.tomaDecision(1, 8);		
				}
				playMusica(aleatorio);
				
			};
			
		}.start();
		
		
		
		
	}

	int musicaSonando = 0;
	boolean playinMusica=false;
	public void playMusica( int cancion){
		playinMusica=true;
		Log.i("mierda", "playMusica playinMusica="+playinMusica);
		

				// TODO Auto-generated method stub
				switch (cancion) {
				case 1:		
					musicaSonando=1;
					musica1.seekTo(0);
					musica1.play();		
					break;
				case 2:		
					musicaSonando=2;
					musica2.seekTo(0);
					musica2.play();		
					break;
				case 3:		
					musicaSonando=3;
					musica3.seekTo(0);
					musica3.play();		
					break;
				case 4:		
					musicaSonando=4;
					musica4.seekTo(0);
					musica4.play();			
					break;
				case 5:		
					musicaSonando=5;
					musica5.seekTo(0);
					musica5.play();				
					break;
				case 6:		
					musicaSonando=6;
					musica6.seekTo(0);
					musica6.play();		
					break;
				case 7:		
					musicaSonando=7;
					musica7.seekTo(0);
					musica7.play();		
					break;			
				default:  	
					musicaSonando=8;
					musica8.seekTo(0);
					musica8.play();					
					break;
				}
		

	}

	public void stopMusica(){
		Log.i("mierda", "stopMusica "+playinMusica);
		playinMusica=false;
		if (musica1.isPlaying()){
			try{musica1.pause();}catch(Exception e){};
		}
		if (musica2.isPlaying()){
			try{musica2.pause();}catch(Exception e){};
		}
		if (musica3.isPlaying()){
			try{musica3.pause();}catch(Exception e){};
		}
		if (musica4.isPlaying()){
			try{musica4.pause();}catch(Exception e){};
		}
		if (musica5.isPlaying()){
			try{musica5.pause();}catch(Exception e){};
		}
		if (musica6.isPlaying()){
			try{musica6.pause();}catch(Exception e){};
		}
		if (musica7.isPlaying()){
			try{musica7.pause();}catch(Exception e){};
		}
		if (musica8.isPlaying()){
			try{musica8.pause();}catch(Exception e){};
		}
		if (booster.isPlaying()){
			try{booster.pause();}catch(Exception e){};
		}

		playinMusica=false;
		
	}

	public void pararBoosterMuertoBomberman() {
		if (!booster.isPlaying())
			return;
		try {
			booster.pause();
		} catch (Exception e) {
		}

		terminadoBooster();
	}

	public void terminadoBooster() {
		System.out.println("Terrminado boossterrrr");
		new Thread() {
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				switch (musicaSonando) {
				case 1:
					musica1.resume();
					break;

				case 2:
					musica2.resume();
					break;
				case 3:
					musica4.resume();
					break;
				case 5:
					musica5.resume();
					break;
				case 6:
					musica6.resume();
					break;
				case 7:
					musica7.resume();
					break;
				default:
					musica8.resume();
					break;
				}

			};

		}.start();
	}

	public void pasosRapidos() {
		pasosB.setRate(1.25f);
	}

	public void pasosNormales() {
		pasosB.setRate(1f);
	}

	public void carga() {

		MusicFactory.setAssetBasePath("mfx/");
		SoundFactory.setAssetBasePath("mfx/");
		try {
			this.monedasSound = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "ITEM_GET.wav");
			this.campanaFinal = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "arpa.mp3");

			for (int i = 0; i < 15; i++) {
				Sound plantada = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "SE_08.wav");
				Sound mexplosion = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "BOM_11_M_bajos.wav");
				explosion.add(mexplosion);
				bomset.add(plantada);

			}
			this.estrellas = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "estrellas.mp3");
		} catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			this.pasosB = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "pasos7.wav");
		} catch (final IOException e) {
			e.printStackTrace();
		}
		// this.pasosB.setVolume(1);
		pasosB.setLooping(true);

		MusicFactory.setAssetBasePath("mfx/");
		try {
			this.musica1 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "410140_Bomberman_2___Music.mp3");
			this.musica2 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "ARYX.mp3");
			this.musica3 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "BLeH.mp3");
			this.musica4 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "BRD.mp3");
			this.musica5 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "DESPERATE .mp3");
			this.musica6 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "FFF.mp3");
			this.musica7 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "iNFERNO.mp3");
			this.musica8 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "tPORt.mp3");
			this.booster = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "booster.mp3");
			this.mecha = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "mecha.mp3");
			mecha.setLooping(true);

		} catch (final IOException e) {
			e.printStackTrace();
		}
		jukebox();
		cargaPreferencias();
		aplicaVolumen();

	}

	private void cargaPreferencias() {
		int sound_volumen = Preferencias.leerPreferenciasInt("sound_volumen");
		int music_volumen = Preferencias.leerPreferenciasInt("music_volumen");
		if (sound_volumen != -1) {
			soundVolumen = sound_volumen;
		}
		if (music_volumen != -1) {
			musicVolumen = music_volumen;
		}

	}

	public void sonarCogerMoneda() {
		new Thread() {
			public void run() {
				try {
					monedasSound.play();
				} catch (Exception e) {
					Log.e("Errrorrrrrrrrrrrrrrr", "", e);
				}
			};
		}.start();
	}

	public void plantaBomba() {
		if (sonido) {
			new Thread() {
				public void run() {
					try {
						if (nexBomset.get() >= bomset.size() - 2) {
							nexBomset.set(0);
						}
						bomset.get(nexBomset.getAndIncrement()).play();
					} catch (Exception e) {
						Log.e("Errrorrrrrrrrrrrrrrr", "", e);
					}
				};
			}.start();
		}
	}

	public void sonidoExplosion() {
		if (sonido) {
			new Thread() {
				public void run() {
					try {
						if (nexExplosion.get() >= explosion.size() - 2) {
							nexExplosion.set(0);
						}
						explosion.get(nexExplosion.getAndIncrement()).play();
					} catch (Exception e) {
						Log.e("Errrorrrrrrrrrrrrrrr", "", e);
					}
				};
			}.start();
		}
	}

	int bombas = 0;

	boolean sonandoPasos = false;

	public void sonarPasos() {
		new Thread() {
			public void run() {
				if (!sonandoPasos) {
					sonandoPasos = true;
					pasosB.play();
				}
			};
		}.start();
	}

	public void pararPasos() {
		new Thread() {
			public void run() {
				sonandoPasos = false;
				pasosB.pause();
			};
		}.start();
	}

	public void sonarMecha() {
		new Thread() {
			public void run() {
				if (bombas == 0)
					mecha.play();
				bombas++;
			};
		}.start();
	}
	
	public void pararMecha() {

		new Thread() {
			public void run() {
				bombas--;
				if (bombas <= 0) {
					mecha.pause();
				}
				if (bombas < 0) {
					bombas = 0;
				}
			};
		}.start();

	}

	public void setSoundVolumen(int sound_volumen) {
		soundVolumen = sound_volumen;
		aplicaVolumen();
	}

	public void setMusicVolumen(int music_volumen) {
		musicVolumen = music_volumen;
		aplicaVolumen();
	}

}

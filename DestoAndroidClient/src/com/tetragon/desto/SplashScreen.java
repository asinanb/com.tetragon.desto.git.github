package com.tetragon.desto;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.google.cloud.backend.core.CloudQuery.Order;
import com.google.cloud.backend.core.CloudQuery.Scope;
import com.tetragon.desto.model.Konum;
import com.tetragon.desto.model.Kullanici;
import com.tetragon.desto.model.Kullanici_grubu;
import com.tetragon.desto.model.Marka;
import com.tetragon.desto.model.Model;
import com.tetragon.desto.model.Musteri;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.model.Stok;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.model.Stok_yeri;
import com.tetragon.desto.model.Urun_tip;
import com.tetragon.desto.model.Urungrubu;

public class SplashScreen extends Activity {

	private Context context = this;
	
	private int mProgressStatus = 3;
	private int mProgressRate = 10;
	private ProgressBar loginProgressBar;
	private ProgressBar newSplashProgressBar;
	private EditText kullaniciEditText;
	private EditText sifreEditText;
	private Button girisyapButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		loginProgressBar= (ProgressBar) findViewById(R.id.loginProgressBar);
		newSplashProgressBar= (ProgressBar) findViewById(R.id.newSplashProgressBar);
		kullaniciEditText= (EditText) findViewById(R.id.kullaiciEditText);
		sifreEditText= (EditText) findViewById(R.id.sifreEditText);
		girisyapButton= (Button) findViewById(R.id.girisyapButton);
		kullaniciEditText.setVisibility(View.GONE);
		sifreEditText.setVisibility(View.GONE);
		girisyapButton.setVisibility(View.GONE);
		girisyapButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				kullaniciEditText.setVisibility(View.GONE);
				sifreEditText.setVisibility(View.GONE);
				girisyapButton.setVisibility(View.GONE);
				loginProgressBar.setVisibility(View.GONE);
				newSplashProgressBar.setVisibility(View.VISIBLE);
				fillMarkaData();
			}
		});
		newSplashProgressBar.setVisibility(View.GONE);
		loginProgressBar.setVisibility(View.VISIBLE);
		fillKullanici_grupData();
	}

	private void fillKullanici_grupData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setKullanici_grubuList(dbObjects
						.createKullanici_grubuList(results));
				fillKonumData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
				if (exception.getClass()==UnknownHostException.class){
					AlertDialog dialog = new AlertDialog.Builder(getContext(),
							AlertDialog.THEME_HOLO_DARK).create();
					dialog.setTitle("Internete Eriþilemiyor");
					dialog.setMessage( "Lütfen Internet baðlantýsýný kontrol edip tekrar çalýþtýrýn.");
					dialog.setCancelable(false);
					dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Kapat",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int buttonId) {

									System.exit(0);
								}
							});
					
					dialog.setIcon(android.R.drawable.ic_dialog_alert);
					dialog.show(); 
				}
					
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Kullanici_grubu.KIND_KULLANICIGRUBU,
				Kullanici_grubu.PROP_IDKEY, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);

	}

	private void fillKonumData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setKonumList(dbObjects.createKonumList(results));
				fillKullaniciData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Konum.KIND_KONUM,
				Konum.PROP_IDKEY, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);

	}
	
	private void fillKullaniciData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setKullaniciList(dbObjects.createKullaniciList(results));
				kullaniciEditText.setVisibility(View.VISIBLE);
				sifreEditText.setVisibility(View.VISIBLE);
				girisyapButton.setVisibility(View.VISIBLE);
				loginProgressBar.setVisibility(View.GONE);
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Kullanici.KIND_KULLANICI,
				Kullanici.PROP_IDKEY, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);

	}
	
	private void fillMarkaData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setMarkaItemList(dbObjects.createMarkaItemList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillUrungrubuData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Marka.KIND_MARKA, Marka.PROP_MARKA, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);

	}

	private void fillUrungrubuData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setUrungrubuItemList(dbObjects
						.createUrungrubuItemList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillUrun_tipData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Urungrubu.KIND_URUNGRUBU,
				Urungrubu.PROP_URUNGRUBU, Order.ASC, 50, Scope.FUTURE_AND_PAST,
				handler);
	}

	private void fillUrun_tipData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setUrunTipItemList(dbObjects.createUrunTipItemList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillModelData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Urun_tip.KIND_URUN_TIP, Urun_tip.PROP_URUN_TIP,
				Order.ASC, 50, Scope.FUTURE_AND_PAST, handler);
	}

	private void fillModelData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setModelItemList(dbObjects.createModelItemList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillSiparisData();
				
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Model.KIND_MODEL, Model.PROP_MODEL, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);
	}

	private void fillSiparisData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				DbObjects.setSiparisList(dbObjects.createSiparisList(results));
				fillSiparisAyrintiData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Siparis.KIND_SIPARIS, CloudEntity.PROP_CREATED_AT,
				Order.ASC, 50, Scope.FUTURE_AND_PAST, handler);
	}

	private void fillSiparisAyrintiData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setSiparisAyrintiList(dbObjects
						.createSiparisAyrintiList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillMusteriData();
				
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Siparis_ayrinti.KIND_SIPARISAYRINTI,
				CloudEntity.PROP_CREATED_AT, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);
	}
	private void fillMusteriData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setMusteriList((dbObjects.createMusteriList(results)));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillStok_yeriData();
				
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Musteri.KIND_MUSTERI,
				CloudEntity.PROP_CREATED_AT, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);
	}
	private void fillStok_yeriData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				DbObjects.setStok_yeriList(dbObjects.createStok_yeriList(results));
				newSplashProgressBar.setProgress(mProgressStatus += 100 / mProgressRate);
				fillStokData();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Stok_yeri.KIND_STOK_YERI, Stok_yeri.PROP_STOKYERI, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);
	}
	private void fillStokData() {
		final DbObjects dbObjects = new DbObjects(context);
		final CloudCallbackHandler<List<CloudEntity>> handler = new CloudCallbackHandler<List<CloudEntity>>() {
			@Override
			public void onComplete(List<CloudEntity> results) {
				StokItemList stokItemList=dbObjects.createStokItemList(results);
				((DestoApplication) getApplication()).setStokItemList(stokItemList);
				
				newSplashProgressBar.setProgress(100);
				// Start your app main activity
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}

			@Override
			public void onError(IOException exception) {
				Log.i("Hata", exception.toString());
			}

		};
		// execute the query with the handler
		dbObjects.listByKind(Stok.KIND_STOK, Stok.PROP_MODEL_ID, Order.ASC, 50,
				Scope.FUTURE_AND_PAST, handler);
	}
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
}
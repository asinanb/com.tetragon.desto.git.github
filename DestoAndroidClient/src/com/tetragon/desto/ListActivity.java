package com.tetragon.desto;

import com.tetragon.desto.eventHandler.MarkaEventHandler;
import com.tetragon.desto.eventHandler.ModelEventHandler;
import com.tetragon.desto.eventHandler.MusteriEventHandler;
import com.tetragon.desto.eventHandler.SiparisAyrintiEventHandler;
import com.tetragon.desto.eventHandler.SiparisEventHandler;
import com.tetragon.desto.eventHandler.StokEventHandler;
import com.tetragon.desto.eventHandler.Stok_yeriEventHandler;
import com.tetragon.desto.eventHandler.Urun_tipEventHandler;
import com.tetragon.desto.eventHandler.UrungrubuEventHandler;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.params.MarkaListFragment;
import com.tetragon.desto.params.ModelListFragment;
import com.tetragon.desto.params.MusteriListFragment;
import com.tetragon.desto.params.Stok_yeriListFragment;
import com.tetragon.desto.params.Urun_tipListFragment;
import com.tetragon.desto.params.UrungrubuListFragment;
import com.tetragon.desto.satis.SatisListFragment;
import com.tetragon.desto.test.TestFragment;
import com.tetragon.desto.util.DestoConstants;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends FragmentActivity {
	private MarkaEventHandler markaEventHandler= new MarkaEventHandler();
	private Urun_tipEventHandler urunEventHandler= new Urun_tipEventHandler();
	private UrungrubuEventHandler urungrubuEventHandler= new UrungrubuEventHandler();
	private ModelEventHandler modelEventHandler= new ModelEventHandler();
	private SiparisEventHandler siparisEventHandler= new SiparisEventHandler();
	private SiparisAyrintiEventHandler siparisAyrintiEventHandler = new SiparisAyrintiEventHandler();
	private Stok_yeriEventHandler stok_yeriEventHandler = new Stok_yeriEventHandler();
	private StokEventHandler stokEventHandler = new StokEventHandler();
	private MusteriEventHandler musteriEventHandler = new MusteriEventHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desto);
		
		if (savedInstanceState == null) {
			FragmentManager fragmentManager= getFragmentManager();
			FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
			
			Intent intent = getIntent();
			String tag = intent.getStringExtra("TAG");
			
			if (tag.equals(DestoConstants.MARKA)){
				MarkaListFragment fragment= new MarkaListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.URUNGRUBU)){
				UrungrubuListFragment fragment= new UrungrubuListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.URUN_TIP)){
				Urun_tipListFragment fragment= new Urun_tipListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.MODEL)){
				ModelListFragment fragment= new ModelListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.STOK_YERI)){
				Stok_yeriListFragment fragment= new Stok_yeriListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.SIPARIS)){
				SiparisListFragment fragment= new SiparisListFragment();	
				fragmentTransaction.add(R.id.menu_frame,fragment);
			}else if (tag.equals(DestoConstants.SIPARIS_AYRINTI)){
				String siparisId=intent.getStringExtra(Siparis.PROP_IDKEY);
				SiparisAyrintiListFragment fragment= new SiparisAyrintiListFragment(siparisId);	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.DEPOURUNGIR)){
				SiparistenDepoyaList fragment= new SiparistenDepoyaList();
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.STOK)){
				StokListFragment fragment= new StokListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.SATIS)){
				SatisListFragment fragment= new SatisListFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.TEST)){
				TestFragment fragment= new TestFragment();	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.MUSTERI)){
				MusteriListFragment fragment= new MusteriListFragment(DestoConstants.MUSTERI);	
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}else if (tag.equals(DestoConstants.MUSTERI_SEC)){
				MusteriListFragment fragment= new MusteriListFragment(DestoConstants.MUSTERI_SEC);	
//				fragmentTransaction.add(R.id.menu_frame,fragment);
//				fragmentTransaction.addToBackStack("myFrag");
				fragmentTransaction.replace(R.id.menu_frame,fragment);
				fragmentTransaction.addToBackStack(null);
			}
			
			fragmentTransaction.commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public MarkaEventHandler getMarkaEventHandler() {
		return markaEventHandler;
	}

	public void setMarkaEventHandler(MarkaEventHandler markaEventHandler) {
		this.markaEventHandler = markaEventHandler;
	}

	public Urun_tipEventHandler getUrunEventHandler() {
		return urunEventHandler;
	}

	public void setUrunEventHandler(Urun_tipEventHandler urunEventHandler) {
		this.urunEventHandler = urunEventHandler;
	}

	public UrungrubuEventHandler getUrungrubuEventHandler() {
		return urungrubuEventHandler;
	}

	public void setUrungrubuEventHandler(UrungrubuEventHandler urungrubuEventHandler) {
		this.urungrubuEventHandler = urungrubuEventHandler;
	}

	public ModelEventHandler getModelEventHandler() {
		return modelEventHandler;
	}

	public void setModelEventHandler(ModelEventHandler modelEventHandler) {
		this.modelEventHandler = modelEventHandler;
	}

	public SiparisEventHandler getSiparisEventHandler() {
		return siparisEventHandler;
	}

	public void setSiparisEventHandler(SiparisEventHandler siparisEventHandler) {
		this.siparisEventHandler = siparisEventHandler;
	}

	public SiparisAyrintiEventHandler getSiparisAyrintiEventHandler() {
		return siparisAyrintiEventHandler;
	}

	public void setSiparisAyrintiEventHandler(SiparisAyrintiEventHandler siparisAyrintiEventHandler) {
		this.siparisAyrintiEventHandler = siparisAyrintiEventHandler;
	}

	public Stok_yeriEventHandler getStok_yeriEventHandler() {
		return stok_yeriEventHandler;
	}

	public void setStok_yeriEventHandler(Stok_yeriEventHandler stok_yeriEventHandler) {
		this.stok_yeriEventHandler = stok_yeriEventHandler;
	}

	public StokEventHandler getStokEventHandler() {
		return stokEventHandler;
	}

	public void setStokEventHandler(StokEventHandler stokEventHandler) {
		this.stokEventHandler = stokEventHandler;
	}

	public MusteriEventHandler getMusteriEventHandler() {
		return musteriEventHandler;
	}

	public void setMusteriEventHandler(MusteriEventHandler musteriEventHandler) {
		this.musteriEventHandler = musteriEventHandler;
	}

}

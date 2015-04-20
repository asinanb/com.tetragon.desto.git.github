package com.tetragon.desto;

import com.tetragon.desto.eventHandler.SatisItemEventHandler;
import com.tetragon.desto.satis.SatisIslemiFragment;
import com.tetragon.desto.satis.SatisTamamlaFragment;
import com.tetragon.desto.util.DestoConstants;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class SubMenuActivity extends FragmentActivity {

	private SatisItemEventHandler satisEventHandler = new SatisItemEventHandler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_desto);
		FragmentManager fragmentManager= getFragmentManager();
		FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
		
		Intent intent = getIntent();
		String tag = intent.getStringExtra("TAG");
		FrameLayout frameLayout = (FrameLayout) findViewById(R.id.menu_frame);
		if (tag.equals(DestoConstants.DBMENU)){
			frameLayout.setBackgroundResource(R.color.destoBeige);
			DbMenuFragment fragment= new DbMenuFragment();	
			fragmentTransaction.replace(R.id.menu_frame,fragment);
			fragmentTransaction.addToBackStack(null);
		}else if (tag.equals(DestoConstants.DEPOMENU)){
			frameLayout.setBackgroundResource(R.color.destoPink);
			DepoMenuFragment fragment= new DepoMenuFragment();	
			fragmentTransaction.replace(R.id.menu_frame,fragment);
			fragmentTransaction.addToBackStack(null);
		}else if (tag.equals(DestoConstants.SATISISLEMI)){
			frameLayout.setBackgroundResource(R.color.destoGreen);
			SatisIslemiFragment fragment= new SatisIslemiFragment();
			fragmentTransaction.replace(R.id.menu_frame,fragment);
			fragmentTransaction.addToBackStack(null);
		}else if (tag.equals(DestoConstants.SATISTAMAMLA)){
			frameLayout.setBackgroundResource(R.color.destoPink);
			SatisTamamlaFragment fragment= new SatisTamamlaFragment();	
			fragmentTransaction.replace(R.id.menu_frame,fragment);
			fragmentTransaction.addToBackStack(null);
		}
		fragmentTransaction.commit();
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
	public SatisItemEventHandler getSatisEventHandler() {
		return satisEventHandler;
	}

	public void setSatisEventHandler(SatisItemEventHandler satisEventHandler) {
		this.satisEventHandler = satisEventHandler;
	}

	
}

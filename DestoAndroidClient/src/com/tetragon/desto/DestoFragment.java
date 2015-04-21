package com.tetragon.desto;

import com.tetragon.desto.util.DestoApplication;
import com.tetragon.desto.util.DestoConstants;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class DestoFragment extends Fragment implements OnClickListener {

	/*
	 * UI components
	 */
	private ImageView satisLink;
	private ImageView stokLink;
	private ImageView depoLink;
	private ImageView dbLink;
	private ImageView siparisLink;
	private ImageView testLink;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view= inflater.inflate(R.layout.desto_menu, container,false);
		
		satisLink = (ImageView) view.findViewById(R.id.satisView);
		satisLink.setOnClickListener(this);
		stokLink = (ImageView) view.findViewById(R.id.stokView);
		stokLink.setOnClickListener(this);
		depoLink = (ImageView) view.findViewById(R.id.depoView);
		depoLink.setOnClickListener(this);
		dbLink = (ImageView) view.findViewById(R.id.dbView);
		dbLink.setOnClickListener(this);
		siparisLink = (ImageView) view.findViewById(R.id.siparisView);
		siparisLink.setOnClickListener(this);
		testLink = (ImageView) view.findViewById(R.id.testView);
		testLink.setOnClickListener(this);
		return view;
	}
	

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.satisView) {
			((DestoApplication) getActivity().getApplication())
			.setSatisTamamlandi(false);
			Intent intent = new Intent(getActivity(), ListActivity.class);
			intent.putExtra("TAG", DestoConstants.SATIS);
			startActivity(intent);  
		}else if (v.getId() == R.id.stokView) {
			Intent intent = new Intent(getActivity(), ListActivity.class);
			intent.putExtra("TAG", DestoConstants.STOK);
			startActivity(intent);  
		}else if (v.getId() == R.id.depoView) {
			Intent intent = new Intent(getActivity(), SubMenuActivity.class);
			intent.putExtra("TAG", DestoConstants.DEPOMENU);
			startActivity(intent);   
		}else if (v.getId() == R.id.dbView) {
			Intent intent = new Intent(getActivity(), SubMenuActivity.class);
			intent.putExtra("TAG", DestoConstants.DBMENU);
			startActivity(intent);   
		}else if (v.getId() == R.id.siparisView) {
			Intent intent = new Intent(getActivity(), ListActivity.class);
			intent.putExtra("TAG", DestoConstants.SIPARIS);
			startActivity(intent);   
		}else if (v.getId() == R.id.testView) {
			Intent intent = new Intent(getActivity(), ListActivity.class);
			intent.putExtra("TAG", DestoConstants.TEST);
			startActivity(intent);   
		}
		
	}
}

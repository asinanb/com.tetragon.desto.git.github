package com.tetragon.desto.satis;

import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.DestoApplication;
import com.tetragon.desto.R;
import com.tetragon.desto.SubMenuActivity;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;
import com.tetragon.desto.util.InputFilterMinMax;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SatisIslemiFragment extends Fragment implements
		OnItemClickListener, DataListener {

	private static final int TAKSIT_UST_SINIR = 9;
	private static final float KUSURAT = 10;
	private ListView satisListView;
	private EditText toplamFiyatView;
	private EditText nakitFiyatView;
	private EditText kkartiView;
	private EditText taksitSayisiView;
	private EditText taksitTlView;
	private SatisItem satisItem;

	private CheckBox nakitCheckBox;
	private CheckBox kkartiCheckBox;
	private CheckBox taksitSayisiCheckBox;
	private CheckBox taksitTlCheckBox;
	private TextView satisOzetiTextView;
	private TextView listeFiyatiToplamOzetView;
	private TextView toplamFiyatOzetView;
	private RelativeLayout ozetLayoutView;
	private Button hesaplaButton;
	private Button satisButton;
	private int colorKey=0;

	public SatisIslemiFragment() {
		satisItem = DbObjects.createSatisItem(DbObjects.getSelectedStokList());
	}

	@Override
	public void onResume() {
		super.onResume();
		boolean satisTamam=((DestoApplication) getActivity().getApplication()).isSatisTamamlandi();
		if (satisTamam){
			getActivity().onBackPressed();
		}else{
			// Set title
			getActivity().getActionBar().setTitle(R.string.satisTitle);
			getActivity().getActionBar().setIcon(R.drawable.ic_satis);
		}
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		((SubMenuActivity) getActivity()).getSatisEventHandler().addListener(
				this);
		// Set view
		View view = inflater.inflate(R.layout.satis_fragment, container, false);
		satisItem = DbObjects.createSatisItem(DbObjects.getSelectedStokList());
		addVisualItems(view);
		addListeners(view);
		hesapla(0);
		updateEditBoxValues();
		ozetYazdir();
		setMinMaxValues();
		enableDisablesatisButton();
		return view;
	}

	private void addListeners(View view) {
		toplamFiyatView.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				satisButton.setEnabled(false);
				
			}
		});
		nakitFiyatView.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(android.text.Editable arg0) {
				satisButton.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			};
		});
		kkartiView.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(android.text.Editable arg0) {
				satisButton.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			};
		});
		taksitSayisiView.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(android.text.Editable arg0) {
				satisButton.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			};
		});
		taksitTlView.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(android.text.Editable arg0) {
				satisButton.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			};
		});
		nakitCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					nakitFiyatView.setVisibility(View.VISIBLE);
				} else {
					nakitFiyatView.setVisibility(View.INVISIBLE);
				}
			}
		});
		kkartiCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					kkartiView.setVisibility(View.VISIBLE);
				} else {
					kkartiView.setVisibility(View.INVISIBLE);
				}
			}
		});
		taksitSayisiCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					taksitSayisiView.setVisibility(View.VISIBLE);
					taksitTlView.setVisibility(View.VISIBLE);
					taksitTlCheckBox.setChecked(false);
					taksitSayisiView.setEnabled(true);
					taksitTlView.setEnabled(false);
				} else {
					taksitSayisiView.setVisibility(View.INVISIBLE);
					taksitTlView.setVisibility(View.INVISIBLE);
				}

			}
		});

		taksitTlCheckBox.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (((CheckBox) v).isChecked()) {
					taksitSayisiView.setVisibility(View.VISIBLE);
					taksitTlView.setVisibility(View.VISIBLE);
					taksitSayisiCheckBox.setChecked(false);
					taksitTlView.setEnabled(true);
					taksitSayisiView.setEnabled(false);
				} else {
					taksitSayisiView.setVisibility(View.INVISIBLE);
					taksitTlView.setVisibility(View.INVISIBLE);
				}
			}
		});

		hesaplaButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				hesapla(1);
				updateEditBoxValues();
				enableDisablesatisButton();
				ozetYazdir();
				
			}
		});
		satisButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				openSatisTamamlaFragment();
			}
		});
	}
	
	private void openSatisTamamlaFragment() {
		// ArrayList<Stok> satislistesi) {
		DbObjects.setSatisItem(satisItem);
		Intent intent = new Intent(getActivity(), SubMenuActivity.class);
		intent.putExtra("TAG", DestoConstants.SATISTAMAMLA);
		startActivity(intent);
	}
	
	protected void ozetYazdir() {
		float sonTaksit = satisItem.getSonTaksit();
		
		String taksitOzeti = "";
		if (sonTaksit>0)
			taksitOzeti= satisItem.getTaksitSayisi() +" X "+satisItem.getTaksitTl() + " TL\n"
				+ "1 X " + sonTaksit + " TL\n";
		else 
			taksitOzeti= satisItem.getTaksitSayisi() +" X "+satisItem.getTaksitTl() + " TL\n";
		
		String nak= satisItem.getNakit()>0?"Peþinat : "+satisItem.getNakit() + " TL\n":"" ;
		String kk= satisItem.getKkarti()>0?"Kredi Kartý : "+satisItem.getKkarti() + " TL\n":"" ;
		String satisOzeti=nak + kk + "Taksitler : "+taksitOzeti;
		
		satisOzetiTextView.setText(satisOzeti);
		listeFiyatiToplamOzetView.setText( String.valueOf(toplamHesapla()));
		listeFiyatiToplamOzetView.setPaintFlags( Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		toplamFiyatOzetView.setText("Toplam : " +String.valueOf(satisItem.getToplamFiyat())+ " TL");
		changeOzetColors();
	}

	private void addVisualItems(View view) {
		nakitCheckBox = (CheckBox) view.findViewById(R.id.nakitCheckBox);
		kkartiCheckBox = (CheckBox) view.findViewById(R.id.kkartiCheckBox);
		taksitSayisiCheckBox = (CheckBox) view
				.findViewById(R.id.taksitSayisiCheckBox);
		taksitTlCheckBox = (CheckBox) view.findViewById(R.id.taksitTlCheckBox);

		satisListView = (ListView) view.findViewById(R.id.satisListView);
		satisListView.setAdapter(new SatisIslemiPostAdapter(getActivity(),
				android.R.layout.simple_list_item_1, satisItem.getStokItemList(),satisItem, DestoConstants.SATISISLEMI));

		satisListView.setOnItemClickListener(this);

		toplamFiyatView = (EditText) view.findViewById(R.id.toplamFiyatView);
		nakitFiyatView = (EditText) view.findViewById(R.id.nakitFiyatView);
		kkartiView = (EditText) view.findViewById(R.id.kkartiView);
		taksitSayisiView = (EditText) view.findViewById(R.id.taksitSayisiView);
		taksitTlView = (EditText) view.findViewById(R.id.taksitTlView);

		hesaplaButton = (Button) view.findViewById(R.id.hesaplaButton);
		ozetLayoutView = (RelativeLayout) view.findViewById(R.id.ozetLayoutView);
		satisOzetiTextView = (TextView) view.findViewById(R.id.satisOzetiTextView);
		listeFiyatiToplamOzetView = (TextView) view.findViewById(R.id.listeFiyatiToplamOzetView);
		toplamFiyatOzetView = (TextView) view.findViewById(R.id.toplamFiyatOzetView);
		
		satisButton = (Button) view.findViewById(R.id.satisButton);
		satisButton.setEnabled(false);

		taksitTlView.setEnabled(false);		
		nakitCheckBox.setChecked(true);
		kkartiCheckBox.setChecked(true);
		taksitSayisiCheckBox.setChecked(true);
		
	}

	private void changeOzetColors() {
		colorKey = colorKey%3;
		
		switch (colorKey) {
		case 0:{
			ozetLayoutView.setBackgroundResource(R.color.destoPink);
			satisOzetiTextView.setTextColor(getResources().getColor(R.color.destoBlue));
		}
			break;
		case 1:{
			ozetLayoutView.setBackgroundResource(R.color.destoLightBlue);
			satisOzetiTextView.setTextColor(getResources().getColor(R.color.destoRed));
		}
			break;

		case 2:{
			ozetLayoutView.setBackgroundResource(R.color.destoBeige);
			satisOzetiTextView.setTextColor(getResources().getColor(R.color.destoBlue));
		}
			break;
		default:{
			ozetLayoutView.setBackgroundResource(R.color.destoPink);
			satisOzetiTextView.setTextColor(getResources().getColor(R.color.destoBlue));
		}
			break;
		}
		colorKey++;
	}

	protected void updateEditBoxValues() {
		float toplamFiyat = satisItem.getToplamFiyat();
		float nakit = satisItem.getNakit();
		float kkarti = satisItem.getKkarti();
		int taksitSayisi = satisItem.getTaksitSayisi();
		float taksitTl = satisItem.getTaksitTl();
		
		if (toplamFiyatView != null)
			toplamFiyatView.setText(String.valueOf((int) toplamFiyat));
		if (nakitFiyatView != null)
			nakitFiyatView.setText(String.valueOf((int) nakit));
		if (kkartiView != null)
			kkartiView.setText(String.valueOf((int) kkarti));
		if (taksitSayisiView != null)
			taksitSayisiView.setText(String.valueOf(taksitSayisi));
		if (taksitTlView != null)
			taksitTlView.setText(String.valueOf((int) taksitTl));
	}

	public void setMinMaxValues() {

//		toplamFiyatView.setFilters(new InputFilter[] { new InputFilterMinMax(
//				"0", String.valueOf((int)satisList.getToplamFiyat())) });
//
//		nakitFiyatView.setFilters(new InputFilter[] { new InputFilterMinMax(
//				"0", String.valueOf((int)satisList.getToplamFiyat())) });
//
//		kkartiView.setFilters(new InputFilter[] { new InputFilterMinMax("0",
//				String.valueOf((int)satisList.getToplamFiyat())) });
//
		taksitSayisiView.setFilters(new InputFilter[] { new InputFilterMinMax(
				"0", String.valueOf(TAKSIT_UST_SINIR)) });
//
//		taksitTlView.setFilters(new InputFilter[] { new InputFilterMinMax("0",
//				String.valueOf((int)satisList.getToplamFiyat())) });
	}

	private void hesapla(int type) {
		float toplamFiyat = 0;
		float nakit = 0;
		int taksitSayisi = 0;
		float taksitTl = 0;
		float sonTaksit = 0;
		float kalan = 0;
		float kkarti = 0;
		if (type == 0) {
			toplamFiyat = toplamHesapla();
			nakit = toplamFiyat * 20 / 100;
			kalan = toplamFiyat - nakit;
			taksitSayisi = TAKSIT_UST_SINIR;
			taksitTl = kalan / taksitSayisi;
			
			nakit =(float)(int)(nakit/10)*10;
			kkarti =(float)(int)(kkarti/10)*10;
			taksitTl =(float)(int)(taksitTl/10)*10;
			toplamFiyat =(float)(int)(toplamFiyat/10)*10;
			
			kalan = toplamFiyat - nakit - (taksitSayisi * taksitTl);
			
			if (kalan<KUSURAT){
				toplamFiyat-=kalan;
			}else{
				if (taksitSayisi>8)
					nakit+=kalan;
				else{
					sonTaksit=kalan;
				}
			}

		} else if (type == 1) {
			toplamFiyat = DestoUtil.stringToFloat(toplamFiyatView.getText()
					.toString());

			if (nakitCheckBox.isChecked())
				nakit = DestoUtil.stringToFloat(nakitFiyatView.getText()
						.toString());
			else
				nakit = 0;

			kalan = toplamFiyat - nakit;

			if (kkartiCheckBox.isChecked()) {
				kkarti = DestoUtil.stringToFloat(kkartiView.getText()
						.toString());
				kalan -= kkarti;
			} else
				kkarti = 0;

			if (taksitSayisiCheckBox.isChecked()) {
				taksitSayisi = DestoUtil.stringToInt(taksitSayisiView.getText()
						.toString());
				if (taksitSayisi > TAKSIT_UST_SINIR) {
					taksitSayisi = TAKSIT_UST_SINIR;
				} 
				taksitTl = (float)(int)(kalan / taksitSayisi);
				
			}else if (taksitTlCheckBox.isChecked()) {
				taksitTl = DestoUtil.stringToFloat(taksitTlView.getText()
						.toString());
				taksitSayisi = (int) (kalan / taksitTl);
				if (taksitSayisi > TAKSIT_UST_SINIR) {
					taksitSayisi = TAKSIT_UST_SINIR;
					taksitTl =  (float)(int) (kalan / taksitSayisi);
				} 
			}
			
			nakit =(float)(int)(nakit/10)*10;
			kkarti =(float)(int)(kkarti/10)*10;
			taksitTl =(float)(int)(taksitTl/10)*10;
			toplamFiyat =(float)(int)(toplamFiyat/10)*10;
			
			kalan = toplamFiyat - (nakit + kkarti + (taksitSayisi * taksitTl));
			
			if (kalan<KUSURAT){
				toplamFiyat-=kalan;
			}else{
				if (taksitSayisi>8)
					nakit+=kalan;
				else{
					sonTaksit=kalan;
				}
			}
		}

		setSatisLisValues(toplamFiyat, nakit, kkarti, taksitSayisi, taksitTl,
				sonTaksit);
	}

	private void setSatisLisValues(float toplamFiyat, float nakitOdeme,
			float kkOdeme, int taksitSayisi, float taksitTl, float sonTaksit) {
		satisItem.setToplamFiyat(toplamFiyat);
		satisItem.setNakit(nakitOdeme);
		satisItem.setKkarti(kkOdeme);
		satisItem.setTaksitSayisi(taksitSayisi);
		satisItem.setTaksitTl(taksitTl);
		satisItem.setSonTaksit(sonTaksit);
	}

	private float toplamHesapla() {
		float toplamFi = 0;
		StokItemList stokItemList= satisItem.getStokItemList();
		for (StokItem stokItem : stokItemList) {
			float fi = DestoUtil.stringToInt(stokItem.getModelItem()
					.getListeFiyati());
			if (stokItem.isSelected())
				toplamFi += fi;
		}
		return toplamFi;
	}
	
	private boolean validateSatis() {

		if (!toplamFiyatIndirimValidate())
			return false;

		if (validateOdemePlani())
			return true;

		return false;

	}

	private boolean validateOdemePlani() {
		int toplamFiyat = DestoUtil.stringToInt(toplamFiyatView.getText()
				.toString());
		int nakitOdeme = DestoUtil.stringToInt(nakitFiyatView.getText()
				.toString());
		int kkOdeme = DestoUtil.stringToInt(kkartiView.getText().toString());
		int taksitSayisi = DestoUtil.stringToInt(taksitSayisiView.getText()
				.toString());
		int taksitTl = DestoUtil.stringToInt(taksitTlView.getText().toString());

		int odemePlaniToplami = nakitOdeme + kkOdeme
				+ (taksitSayisi * taksitTl);

		if (toplamFiyat == odemePlaniToplami)
			return true;

		return false;
	}

	private boolean toplamFiyatIndirimValidate() {
		float toplamListeFiyati = toplamHesapla();
		float satisToplam = DestoUtil.stringToInt(toplamFiyatView.getText()
				.toString());
		float fark = toplamListeFiyati - satisToplam;
		float yuzde10indirim = toplamListeFiyati * 10 / 100;

		if (fark > yuzde10indirim)
			return false;
		else
			return true;

	}

	private void enableDisablesatisButton() {
		if (validateSatis())
			satisButton.setEnabled(true);
		else
			satisButton.setEnabled(false);

	}

	public EditText getNakitFiyatView() {
		return nakitFiyatView;
	}

	public void setNakitFiyatView(EditText nakitFiyatView) {
		this.nakitFiyatView = nakitFiyatView;
	}

	public EditText getKkartiView() {
		return kkartiView;
	}

	public void setKkartiView(EditText kkartiView) {
		this.kkartiView = kkartiView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		StokItemList stokList = (StokItemList) e.getList();
		boolean noneSelected = true;
		for (StokItem stok : stokList) {
			if (stok.isSelected()) {
				noneSelected = false;
				break;
			}

		}
		if (noneSelected) {
			setSatisLisValues(0, 0, 0, 0, 0, 0);

		} else {
			hesapla(0);
		}
		updateEditBoxValues();
		enableDisablesatisButton();
		ozetYazdir();
		setMinMaxValues();
	}

}

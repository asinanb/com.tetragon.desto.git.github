package com.tetragon.desto.satis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Satis;
import com.tetragon.desto.model.SatisItem;
import com.tetragon.desto.model.Stok;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.util.DestoApplication;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SatisTamamlaFragment extends Fragment {

	private ListView satisListView;
	private SatisItem satisItem;

	private TextView satisOzetiTextView;
	private TextView listeFiyatiToplamOzetView;
	private TextView toplamFiyatOzetView;
	private TextView musteriASView;
	private TextView musteriAdresView;
	private Button satisButton;
	private Button musteriButton;
	private boolean musteriSecici = false;

	public SatisTamamlaFragment() {
		satisItem = DbObjects.getSatisItem();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Set title
		getActivity().getActionBar().setTitle(R.string.satisTitle);
		getActivity().getActionBar().setIcon(R.drawable.ic_satis);
		if (isMusteriSecici()) {
			satisItem.setMusteri(DbObjects.getSatisItem().getMusteri());
			musteriASView.setText(satisItem.getMusteri().getAdi() + " "
					+ satisItem.getMusteri().getSoyadi());
			musteriAdresView.setText(satisItem.getMusteri().getAdres());
			setMusteriSecici(false);
		}
		enableDisablesatisButton();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Set view
		View view = inflater.inflate(R.layout.satis_tamamla, container, false);
		satisItem = DbObjects.getSatisItem();
		addVisualItems(view);
		addListeners(view);
		ozetYazdir();
		enableDisablesatisButton();
		return view;
	}

	private void addListeners(View view) {
		musteriButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				setMusteriSecici(true);
				Intent intent = new Intent(getActivity(), ListActivity.class);
				intent.putExtra("TAG", DestoConstants.MUSTERI_SEC);
				startActivity(intent);

			}
		});
		satisButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				satisYap();
			}
		});
	}

	private void satisYap() {
		stoktanEksilt();
		// stoktanCikar(satis);
		// satýþ tablosuna yaz
		// taksittablosuna yaz
		// mesaj yolla

	}

	private void stoktanEksilt() {

		final DbObjects dbObjects = new DbObjects(getActivity());
		CloudCallbackHandler<List<CloudEntity>> mhandler = new CloudCallbackHandler<List<CloudEntity>>() {

			@Override
			public void onComplete(List<CloudEntity> results) {
				Toast toast = Toast
						.makeText(getActivity(), Stok.LABEL_STOK
								+ " kaydedildi.",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				satisTablosunaYaz();

			}

			@Override
			public void onError(IOException exception) {
				Toast toast = Toast.makeText(getActivity(), Stok.LABEL_STOK
						+ " kayýt edilemedi!",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

			}

		};
		StokItemList stokItemList = satisItem.getStokItemList();
		List<CloudEntity> stoklist2save = new ArrayList<CloudEntity>();
		for (StokItem item : stokItemList) {
			int kalan = item.getAdet() - item.getSatisAdet();
			if (kalan < 0) {

			} else if (kalan == 0) {
				item.setAdet(kalan);
			} else if (kalan > 0) {
				item.setAdet(kalan);
			}
			stoklist2save.add(item.getStokCE());
		}
		dbObjects.updateAll(stoklist2save, mhandler);
	}

	protected void satisTablosunaYaz() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			@Override
			public void onComplete(CloudEntity result) {
				Toast toast = Toast
						.makeText(getActivity(), Satis.LABEL_SATIS
								+ " kaydedildi.",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

				taksitleri_yaz();
			}

			@Override
			public void onError(IOException exception) {
				Toast toast = Toast.makeText(getActivity(), Satis.LABEL_SATIS
						+ " Kayýt edilemedi!.",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

			}
		};
		Satis satisToSave = new Satis(satisItem);
		dbObjects.insert(satisToSave, mhandler);
	}

	protected void taksitleri_yaz() {
		if (satisItem.getTaksitSayisi() > 0) {

		}
		//set global variable satisTamamlandi
		((DestoApplication) getActivity().getApplication())
				.setSatisTamamlandi(true);
		getActivity().onBackPressed();
	}

	protected void ozetYazdir() {
		float sonTaksit = satisItem.getSonTaksit();

		String taksitOzeti = "";
		if (sonTaksit > 0)
			taksitOzeti = satisItem.getTaksitSayisi() + " X "
					+ satisItem.getTaksitTl() + " TL\n" + "1 X " + sonTaksit
					+ " TL\n";
		else
			taksitOzeti = satisItem.getTaksitSayisi() + " X "
					+ satisItem.getTaksitTl() + " TL\n";

		String nak = satisItem.getNakit() > 0 ? "Peþinat : "
				+ satisItem.getNakit() + " TL\n" : "";
		String kk = satisItem.getKkarti() > 0 ? "Kredi Kartý : "
				+ satisItem.getKkarti() + " TL\n" : "";
		String satisOzeti = nak + kk + "Taksitler : " + taksitOzeti;

		satisOzetiTextView.setText(satisOzeti);
		listeFiyatiToplamOzetView.setText(String.valueOf(toplamHesapla()));
		listeFiyatiToplamOzetView.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG
				| Paint.ANTI_ALIAS_FLAG);
		toplamFiyatOzetView.setText("Toplam : "
				+ String.valueOf(satisItem.getToplamFiyat()) + " TL");
	}

	private float toplamHesapla() {
		float toplamFi = 0;
		StokItemList stokList = satisItem.getStokItemList();
		for (StokItem stok : stokList) {
			float fi = DestoUtil.stringToInt(stok.getModelItem()
					.getListeFiyati());
			if (stok.isSelected())
				toplamFi += fi;
		}
		return toplamFi;
	}

	private void addVisualItems(View view) {

		satisListView = (ListView) view.findViewById(R.id.satisListView);
		satisListView.setAdapter(new SatisIslemiPostAdapter(getActivity(),
				android.R.layout.simple_list_item_1, satisItem
						.getStokItemList(), satisItem,
				DestoConstants.SATISTAMAMLA));

		satisOzetiTextView = (TextView) view
				.findViewById(R.id.satisOzetiTextView);
		listeFiyatiToplamOzetView = (TextView) view
				.findViewById(R.id.listeFiyatiToplamOzetView);
		toplamFiyatOzetView = (TextView) view
				.findViewById(R.id.toplamFiyatOzetView);
		musteriASView = (TextView) view.findViewById(R.id.musteriASView);
		musteriAdresView = (TextView) view.findViewById(R.id.musteriAdresView);
		musteriButton = (Button) view.findViewById(R.id.musteriButton1);
		satisButton = (Button) view.findViewById(R.id.satisButton);
		satisButton.setEnabled(false);

	}

	private void enableDisablesatisButton() {
		if (validateSatis())
			satisButton.setEnabled(true);
		else
			satisButton.setEnabled(false);

	}

	private boolean validateSatis() {
		boolean validated = false;
		musteriASView.getText().toString();
		if ((satisItem.getMusteri() != null)
				&& (!satisItem.getMusteri().toString().equals("")))
			validated = true;
		return validated;
	}

	public boolean isMusteriSecici() {
		return musteriSecici;
	}

	public void setMusteriSecici(boolean musteriSecici) {
		this.musteriSecici = musteriSecici;
	}

}

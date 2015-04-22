package com.tetragon.desto;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.model.Stok;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.model.Stok_yeri;
import com.tetragon.desto.model.Stok_yeriList;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.io.IOException;
import java.util.List;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class SiparistenDepoyaPostAdapter extends ArrayAdapter<Siparis_ayrinti> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	private int gelenAdet = 0;
	private int siparisAdet = 0;
	private Spinner stok_yeriSpinner;
	private Context context;

	private Stok_yeriList stok_yeriList = DbObjects.getStok_yeriList();

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param listCE
	 */
	public SiparistenDepoyaPostAdapter(Context context, int textViewResourceId,
			List<Siparis_ayrinti> listCE) {
		super(context, textViewResourceId, listCE);
		setContext(context);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.siparistendepoya_post, parent, false);

		final Siparis_ayrinti ce = getItem(position);
		final int pos = position;
		if (ce != null) {
			// ilk degerler
			setSiparisAdet(ce);
			setGelenAdet(ce);

			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);

			TextView urunTextView = (TextView) view
					.findViewById(R.id.urunTipTextView);

			TextView modelTextView = (TextView) view
					.findViewById(R.id.modelTextView);

			TextView siparisAdetTextView = (TextView) view
					.findViewById(R.id.siparisAdetTextView);

			TextView gelenAdetTextView = (TextView) view
					.findViewById(R.id.gelenAdetTextView);

			RelativeLayout postLayoutView = (RelativeLayout) view
					.findViewById(R.id.postLayoutView);

			ImageView depoyaalImg = (ImageView) view
					.findViewById(R.id.ic_depoyaal);

			depoyaalImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					openEditDialog(pos, ce);
				}

			});

			if ((modelTextView != null) && (ce.getModelItem() != null)) {
				modelTextView.setText(ce.getModelItem().getModel());
			}

			ModelItem model = ce.getModelItem();

			if ((markaTextView != null) && (model.getMarkaItem() != null)) {
				markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((urunTextView != null) && (model.getUrun_tipItem() != null)) {
				urunTextView.setText(" " + model.getUrun_tipItem().getUrun_tip());
			}
			if (siparisAdetTextView != null)
				siparisAdetTextView.setText(String.valueOf(getSiparisAdet()));
			if (gelenAdetTextView != null)
				gelenAdetTextView.setText(String.valueOf(getGelenAdet()));

			if (postLayoutView != null) {
				postLayoutView.setBackgroundResource(R.color.white);
				if (getSiparisAdet() == getGelenAdet()) {
					postLayoutView.setBackgroundResource(R.color.destoBeige);
					depoyaalImg.setImageResource(R.drawable.ic_tamamlandi);
					depoyaalImg.setEnabled(false);

				} else if (getGelenAdet() > 0) {
					postLayoutView.setBackgroundResource(R.color.destoGreen);
					depoyaalImg.setImageResource(R.drawable.ic_depoyaal);
					depoyaalImg.setEnabled(true);
				} else if (getGelenAdet() == 0) {
					postLayoutView.setBackgroundResource(R.color.white);
					depoyaalImg.setImageResource(R.drawable.ic_depoyaal);
					depoyaalImg.setEnabled(true);
				}

			}

		}

		return view;
	}

	private void openEditDialog(final int position,
			final Siparis_ayrinti siparis_ayrinti) {

		// secilen siparise ait
		setSiparisAdet(siparis_ayrinti);
		setGelenAdet(siparis_ayrinti);

		final Dialog editSiparisDialog = new Dialog(getListActivity());

		editSiparisDialog.setContentView(R.layout.siparistendepoya_save);
		editSiparisDialog.setTitle("Sipariþi Depoya Alma");

		final ProgressBar progressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.siparistenDepoyaProgressBar);
		progressBar.setVisibility(View.GONE);

		TextView markaTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaMarkaTextView);

		TextView urunTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaUruntipTextView);

		TextView modelTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaModelTextView);

		TextView siparisAdediLabelTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaSiparisAdetLabTextView);

		stok_yeriSpinner = (Spinner) editSiparisDialog
				.findViewById(R.id.stok_yeriSpinner);

		ArrayAdapter<Stok_yeri> adapter = new ArrayAdapter<Stok_yeri>(
				getContext(), android.R.layout.simple_spinner_item,
				stok_yeriList);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		stok_yeriSpinner.setAdapter(adapter);
		stok_yeriSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						Log.i("uyarý", String.valueOf(arg2));
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

		TextView siparisAdediTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaSiparisAdetTextView);

		TextView depoyaGirenAdetTextView = (TextView) editSiparisDialog
				.findViewById(R.id.depoyaGirenAdetLabTextView);

		final NumberPicker depoyaGirenAdetPicker = (NumberPicker) editSiparisDialog
				.findViewById(R.id.depoyaGirenAdetPicker);

		final Button vazgecButton = (Button) editSiparisDialog
				.findViewById(R.id.vazgecButton);
		final Button saveButton = (Button) editSiparisDialog
				.findViewById(R.id.saveButton);

		if (siparis_ayrinti.getSiparis().getMarkaItem() != null)
			markaTextView.setText(siparis_ayrinti.getModelItem().getMarkaItem()
					.getMarka()
					+ " ");
		if (siparis_ayrinti.getModelItem() != null) {
			modelTextView.setText(siparis_ayrinti.getModelItem().getModel());
			urunTextView.setText(siparis_ayrinti.getModelItem().getUrun_tipItem()
					.getUrun_tip());
			siparisAdediTextView.setText(siparis_ayrinti.getSiparis_adedi());
		}

		int depoyaGirecekAdet = getSiparisAdet() - getGelenAdet();

		depoyaGirenAdetPicker.setValue(depoyaGirecekAdet);
		depoyaGirenAdetPicker.setMaxValue(depoyaGirecekAdet);
		depoyaGirenAdetPicker.setMinValue(1);

		if (depoyaGirenAdetPicker.getValue() < 1)
			saveButton.setEnabled(false);
		else
			saveButton.setEnabled(true);

		depoyaGirenAdetPicker
				.setOnScrollListener(new NumberPicker.OnScrollListener() {

					@Override
					public void onScrollStateChange(NumberPicker numberPicker,
							int scrollState) {
						if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
							if (numberPicker.getValue() < 1)
								saveButton.setEnabled(false);
							else
								saveButton.setEnabled(true);
						}
					}
				});
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editSiparisDialog.dismiss();
			}
		});
		saveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				
				// siparis ayrýntýda siparis durumunu güncelle
				int toplam = getGelenAdet() + depoyaGirenAdetPicker.getValue();
				setGelenAdet(toplam);
				siparis_ayrinti.setGelen_adet(String.valueOf(toplam));
				siparis_ayrinti.put(Siparis_ayrinti.PROP_GELEN_ADET, toplam);

				updateSiparisAyrinti(editSiparisDialog, siparis_ayrinti,
						position);
				
				// gelen urunu Stoka kaydet
				updateStok(editSiparisDialog, siparis_ayrinti,
						depoyaGirenAdetPicker.getValue());
			}
		});
		editSiparisDialog.show();

	}

	protected void updateStok(final Dialog editSiparisDialog,
			Siparis_ayrinti siparis_ayrinti, int ekAdet) {
		final DbObjects dbObjects = new DbObjects(getListActivity());
		final ProgressBar dlgProgressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.siparistenDepoyaProgressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			@Override
			public void onComplete(CloudEntity result) {
				Toast toast = Toast.makeText(getListActivity(),
						" stok kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

				String idkey=(String) result.get(Stok.PROP_IDKEY);
				StokItemList list=((DestoApplication) getListActivity().getApplication()).getStokItemList();
				StokItem stokToSave = list.findStokByIdkey(idkey);//DbObjects.getStokItemList().findStokByIdkey(idkey);//
				//bu stokItem ilk defa giriliyorsa, stoklistesinde bulunmaz, nullolur 
				if (stokToSave==null)
					stokToSave=new StokItem();
				stokToSave.assign(new StokItem(result));
				list.addOrUpdate(stokToSave);
				getListActivity().getStokEventHandler().fireStokChanged();

			}

		};

		dlgProgressBar.setVisibility(View.VISIBLE);
		StokItemList stokList = ((DestoApplication) getListActivity().getApplication()).getStokItemList();//DbObjects.getStokItemList();
		Stok_yeri stok_yeri = (Stok_yeri) stok_yeriSpinner.getSelectedItem();

		if (!stokList.isEmpty()) {

			StokItem stokItem = stokList
					.findStokByStokYeriAndModelId(stok_yeri.getIdkey(),
							siparis_ayrinti.getModelItem().getIdkey());
			if (stokItem != null) {
				stokItem.setAdet(stokItem.getAdet()+ ekAdet);
				dbObjects.update(stokItem.getStokCE(), mhandler);
			} else {

				stokItem = new StokItem();
				stokItem.setModelItem(siparis_ayrinti.getModelItem());
				stokItem.setStok_yeri(stok_yeri);
				stokItem.setAdet(ekAdet);
				dbObjects.insert(stokItem.getStokCE(), mhandler);
			}

		} else {
			StokItem stokItem = new StokItem();
			stokItem.setModelItem(siparis_ayrinti.getModelItem());
			stokItem.setStok_yeri(stok_yeri);
			stokItem.setAdet(ekAdet);
			dbObjects.insert(stokItem.getStokCE(), mhandler);

		}
	}

	// private CloudBackendMessaging backendMessaging;
	private void updateSiparisAyrinti(final Dialog editSiparisDialog,
			final Siparis_ayrinti siparis_ayrinti, final int position) {
		final DbObjects dbObjects = new DbObjects(getListActivity());
		final ProgressBar dlgProgressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.siparistenDepoyaProgressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editSiparisDialog
						.findViewById(R.id.linearLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getListActivity(),
						siparis_ayrinti.getModelItem() + " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

				DbObjects.getSiparisAyrintiList().setSiparis_ayrinti(
						siparis_ayrinti);
				DbObjects.getSiparisAyrintiList().sort();
				((ListActivity) getListActivity())
						.getSiparisAyrintiEventHandler()
						.fireSiparisAyrintiChanged();

				dlgProgressBar.setVisibility(View.VISIBLE);
				editSiparisDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Siparis_ayrinti.KIND_SIPARISAYRINTI + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getListActivity(),
						Siparis_ayrinti.LABEL_SIPARISAYRINTI
								+ " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

			}

		};

		dlgProgressBar.setVisibility(View.VISIBLE);
		dbObjects.update(siparis_ayrinti, mhandler);
	}

	public int getGelenAdet() {
		return gelenAdet;
	}

	public void setGelenAdet(int gelenAdet) {
		this.gelenAdet = gelenAdet;
	}

	public void setGelenAdet(Siparis_ayrinti siparis_ayrinti) {
		int adet = 0;
		if ((siparis_ayrinti.getGelen_adet() != null)
				&& (!siparis_ayrinti.getGelen_adet().isEmpty())
				&& (DestoUtil.isNumeric(siparis_ayrinti.getGelen_adet())))
			adet = Integer.valueOf(siparis_ayrinti.getGelen_adet());

		this.gelenAdet = adet;
	}

	public int getSiparisAdet() {
		return siparisAdet;
	}

	public void setSiparisAdet(int siparisAdet) {
		this.siparisAdet = siparisAdet;
	}

	public void setSiparisAdet(Siparis_ayrinti siparis_ayrinti) {
		int adet = 0;
		if ((siparis_ayrinti.getSiparis_adedi() != null)
				&& (!siparis_ayrinti.getSiparis_adedi().isEmpty())
				&& (DestoUtil.isNumeric(siparis_ayrinti.getSiparis_adedi())))
			adet = Integer.valueOf(siparis_ayrinti.getSiparis_adedi());

		this.siparisAdet = adet;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

	public ListActivity getListActivity() {
		return listActivity;
	}

	public void setListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

}

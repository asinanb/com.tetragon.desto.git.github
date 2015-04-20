package com.tetragon.desto;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class SiparisAyrintiPostAdapter extends ArrayAdapter<Siparis_ayrinti> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param listCE
	 */
	public SiparisAyrintiPostAdapter(Context context, int textViewResourceId,
			List<Siparis_ayrinti> listCE) {
		super(context, textViewResourceId, listCE);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.siparis_ayrinti_post, parent, false);

		final Siparis_ayrinti siparis_ayrintiItem = getItem(position);
		if (siparis_ayrintiItem != null) {

			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);

			TextView urunTextView = (TextView) view
					.findViewById(R.id.urunTipTextView);

			TextView modelTextView = (TextView) view
					.findViewById(R.id.modelTextView);

			TextView adetTextView = (TextView) view
					.findViewById(R.id.adetTextView);
			
			TextView gelenAdetTextView = (TextView) view
					.findViewById(R.id.gelenAdetTextView);
			
			if ((modelTextView != null) && (siparis_ayrintiItem.getModelItem() != null)) {
				modelTextView.setText(siparis_ayrintiItem.getModelItem().getModel());
			}
			
			ModelItem model = siparis_ayrintiItem.getModelItem() ;
			
			if ((markaTextView != null) && (model.getMarkaItem()!= null)) {
				markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((urunTextView != null) && (model.getUrun_tipItem() != null)) {
				urunTextView.setText(" " + model.getUrun_tipItem().getUrun_tip());
			}
			if ((adetTextView != null) && (siparis_ayrintiItem.get(Siparis_ayrinti.PROP_SIPARIS_ADEDI) != null)) {
				adetTextView.setText(siparis_ayrintiItem.get(Siparis_ayrinti.PROP_SIPARIS_ADEDI).toString());
			}
			int gelenAdet=findGelenAdet(siparis_ayrintiItem);
			if (gelenAdetTextView != null) {
				gelenAdetTextView.setText(String.valueOf(gelenAdet));
			}
			
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			
			if (gelenAdet>0) {
				deleteImg.setImageResource(R.drawable.ic_delete_disabled);
				deleteImg.setEnabled(false);
			}
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteSiparisAyrinti(siparis_ayrintiItem);
				}

			});
		}

		return view;
	}
	public int findGelenAdet(Siparis_ayrinti siparis_ayrinti) {
		int adet = 0;
		if ((siparis_ayrinti.getGelen_adet() != null)
				&& (!siparis_ayrinti.getGelen_adet().isEmpty())
				&& (DestoUtil.isNumeric(siparis_ayrinti.getGelen_adet())))
			adet = Integer.valueOf(siparis_ayrinti.getGelen_adet());

		return adet;
	}
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

	private void deleteSiparisAyrinti(final Siparis_ayrinti entity) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						entity.getModelItem()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getSiparisAyrintiList().remove(entity);
				getListActivity().getSiparisAyrintiEventHandler()
						.fireSiparisAyrintiChanged(
								DbObjects.getSiparisAyrintiList());

			}
			
			@Override
			public void onError(final IOException exception) {
				Log.i(Siparis_ayrinti.KIND_SIPARISAYRINTI + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						entity.getModelItem() + " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();

			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Siparis_ayrinti.PROP_IDKEY + " Silinecek");
		dialog.setMessage(entity.getModelItem()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(entity, mhandler);
						dialog.dismiss();
					}
				});
		dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Silme",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {
						dialog.dismiss();
					}
				});
		dialog.setIcon(android.R.drawable.ic_delete);
		dialog.show();
	}

	public ListActivity getListActivity() {
		return listActivity;
	}

	public void setListActivity(ListActivity listActivity) {
		this.listActivity = listActivity;
	}
}

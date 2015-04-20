package com.tetragon.desto;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.Siparis_ayrintiList;
import com.tetragon.desto.util.DestoConstants;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
public class SiparisPostAdapter extends ArrayAdapter<Siparis> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;

	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param siparisList
	 */
	public SiparisPostAdapter(Context context, int textViewResourceId,
			List<Siparis> siparisList) {
		super(context, textViewResourceId, siparisList);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.siparis_row_post, parent, false);

		final Siparis siparisItem = getItem(position);
		if (siparisItem != null) {

			TextView siparisTextView = (TextView) view
					.findViewById(R.id.siparisTextView);

			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);

			TextView siparisTarihiTextView = (TextView) view
					.findViewById(R.id.siparisTarihiTextView);

			if ((siparisTextView != null)
					&& (siparisItem.getEtiket() != null)) {
				siparisTextView.setText(siparisItem.getEtiket());
			}

			if ((markaTextView != null) && (siparisItem.getMarkaItem() != null)) {
				markaTextView.setText(siparisItem.getMarkaItem().getMarka());
			}

			if ((siparisTarihiTextView != null)
					&& (siparisItem.get(Siparis.PROP_SIPARIS_TARIHI) != null)) {
				siparisTarihiTextView.setText(siparisItem.getSiparisTarihi());
			}
			
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteSiparis(siparisItem);
				}

			});
			/*
			 * Kayýtlý siparis ayrintisi varsa siparis listesinden silinmesin
			 */
			Siparis_ayrintiList siparis_ayrintiList= DbObjects.getSiparisAyrintiList(siparisItem);
			if ((siparis_ayrintiList!= null)&&(!siparis_ayrintiList.isEmpty())) {
				deleteImg.setImageResource(R.drawable.ic_delete_disabled);
				deleteImg.setEnabled(false);
			}
			ImageView editImg = (ImageView) view.findViewById(R.id.editSiparis);
			editImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getListActivity(), ListActivity.class);
					intent.putExtra("TAG", DestoConstants.SIPARIS_AYRINTI);
					intent.putExtra(Siparis.PROP_IDKEY, siparisItem.getIdkey());
					getContext().startActivity(intent);   
				}

			});
			
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}

	private void deleteSiparis(final Siparis siparis) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						siparis.getEtiket()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getSiparisList().remove(siparis);
				getListActivity().getSiparisEventHandler().fireSiparisChanged();

			}
			
			@Override
			public void onError(final IOException exception) {
				Log.i(Siparis.KIND_SIPARIS + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						siparis.getEtiket() + " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Siparis.LABEL_SIPARIS + " Silinecek");
		dialog.setMessage(siparis.getEtiket()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(siparis, mhandler);
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

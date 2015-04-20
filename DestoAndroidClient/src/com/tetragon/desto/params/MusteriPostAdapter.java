package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Musteri;
import com.tetragon.desto.util.DestoConstants;

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
 * the musteri. Layout uses row.xml.
 *
 */
public class MusteriPostAdapter extends ArrayAdapter<Musteri> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;
	private boolean musteriSecici = false;
	
	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param objects
	 */
	public MusteriPostAdapter(Context context, int textViewResourceId,
			List<Musteri> objects,boolean musteriSec) {
		super(context, textViewResourceId, objects);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
		setMusteriSecici(musteriSec);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.musteri_row_post, parent, false);

		final Musteri musteri = getItem(position);
		if (musteri != null) {
			TextView adsoyadTextView = (TextView) view.findViewById(R.id.adSoyadTextView);
			if (adsoyadTextView != null) {
				adsoyadTextView.setText(musteri.getAdi()+" "+musteri.getSoyadi());
			}
			TextView adresTextView = (TextView) view.findViewById(R.id.adresTextView);
			if (adresTextView != null) {
				adresTextView.setText(musteri.getAdres());
			}
			
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteMusteri(musteri);
				}

			});
			if (isMusteriSecici()) {
				deleteImg.setVisibility(View.GONE);
			}
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	
	private void deleteMusteri(final Musteri musteri) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						musteri.toString()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getMusteriList().remove(musteri);
				getListActivity().getMusteriEventHandler().fireMusteriChanged();

			}
			
			@Override
			public void onError(final IOException exception) {
				Log.i(Musteri.KIND_MUSTERI + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						musteri.toString()+ " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Musteri.LABEL_MUSTERI+ " Silinecek");
		dialog.setMessage(musteri.toString()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(musteri, mhandler);
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

	public boolean isMusteriSecici() {
		return musteriSecici;
	}

	public void setMusteriSecici(boolean musteriSecici) {
		this.musteriSecici = musteriSecici;
	}
}

package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Urungrubu;
import com.tetragon.desto.model.UrungrubuItem;
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
 * the urungrubu. Layout uses row.xml.
 *
 */
public class UrungrubuPostAdapter extends ArrayAdapter<UrungrubuItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;
	
	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param list
	 */
	public UrungrubuPostAdapter(Context context, int textViewResourceId,
			List<UrungrubuItem> list) {
		super(context, textViewResourceId, list);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.urungrubu_row_post, parent, false);

		final UrungrubuItem urungrubuItem = getItem(position);
		if (urungrubuItem != null) {
			TextView urungrubuTextView = (TextView) view
					.findViewById(R.id.urungrubuTextView);
			if (urungrubuTextView != null) {
				urungrubuTextView.setText(urungrubuItem.getUrungrubu());
			}
			ImageView image = (ImageView) view.findViewById(R.id.ic_urungrubu);

			DestoUtil.changeUGImage(image, urungrubuItem.getUrungrubu());
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteUrungrubu(urungrubuItem);
				}

			});
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	private void deleteUrungrubu(final UrungrubuItem urungrubuItem) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						urungrubuItem.getUrungrubu()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getUrungrubuItemList().remove(urungrubuItem);
				getListActivity().getUrungrubuEventHandler().fireUrungrubuChanged();

			}
			@Override
			public void onError(final IOException exception) {
				Log.i(Urungrubu.KIND_URUNGRUBU+ " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						urungrubuItem.getUrungrubu() + " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}
		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Urungrubu.LABEL_URUNGRUBU + " Silinecek");
		dialog.setMessage(urungrubuItem.getUrungrubu() + " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {

						dbObjects.delete(urungrubuItem.getUrungrubuCE(), mhandler);
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

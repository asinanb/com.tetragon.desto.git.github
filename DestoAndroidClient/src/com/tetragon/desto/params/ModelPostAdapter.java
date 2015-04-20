package com.tetragon.desto.params;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.model.Model;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.ModelItemList;
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

/**
 * This ArrayAdapter uses CloudEntities as items and displays them as a post in
 * the model. Layout uses row.xml.
 *
 */
public class ModelPostAdapter extends ArrayAdapter<ModelItem> {

	private LayoutInflater mInflater;
	private ListActivity listActivity;
	
	/**
	 * Creates a new instance of this adapter.
	 *
	 * @param context
	 * @param textViewResourceId
	 * @param modelList
	 */
	public ModelPostAdapter(Context context, int textViewResourceId,
			ModelItemList modelList) {
		super(context, textViewResourceId, modelList);
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		setListActivity((ListActivity) context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView != null ? convertView : mInflater.inflate(
				R.layout.model_row_post, parent, false);

		final ModelItem model = getItem(position);
		if (model != null) {
			TextView markaTextView = (TextView) view
					.findViewById(R.id.markaTextView);

			TextView urunTipTextView = (TextView) view
					.findViewById(R.id.urunTipTextView);

			TextView modelTextView = (TextView) view
					.findViewById(R.id.modelTextView);

			if ((markaTextView != null) && (model.getMarkaItem()!=null)) {
				markaTextView.setText(model.getMarkaItem().getMarka());
			}
			if ((urunTipTextView != null) && (model.getUrun_tipItem()!=null)) {
				urunTipTextView.setText(model.getUrun_tipItem().getUrun_tip());
			}
			if ((modelTextView != null) && (model.getModel()!=null)) {
				modelTextView.setText(model.getModel());
			}
			ImageView image = (ImageView) view.findViewById(R.id.ic_model);

			DestoUtil.changeUrunImage(image, model.getUrun_tipItem().getUrun_tip());
			ImageView deleteImg = (ImageView) view.findViewById(R.id.ic_delete);
			deleteImg.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteModel(model);
				}

			});
		}

		return view;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return this.getView(position, convertView, parent);
	}
	private void deleteModel(final ModelItem model) {
		final DbObjects dbObjects = new DbObjects(getContext());
		final CloudCallbackHandler<Void> mhandler = new CloudCallbackHandler<Void>() {

			@Override
			public void onComplete(Void results) {
				Toast toast = Toast.makeText(getContext(),
						model.getModel()
								+ " Silindi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getModelItemList().remove(model);
				getListActivity().getModelEventHandler().fireModelChanged();
			}
			@Override
			public void onError(final IOException exception) {
				Log.i(Model.KIND_MODEL + " Silme hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getContext(),
						model.getModel() + " Silinemedi! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
			}

		};

		AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
		dialog.setTitle(Model.LABEL_MODEL + " Silinecek");
		dialog.setMessage(model.getModel()
				+ " Silinsin mi?");
		dialog.setCancelable(false);
		dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sil",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int buttonId) {
						dbObjects.delete(model.getModelCE(), mhandler);
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

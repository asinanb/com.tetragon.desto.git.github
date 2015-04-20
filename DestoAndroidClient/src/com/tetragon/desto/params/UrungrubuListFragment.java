package com.tetragon.desto.params;

import java.io.IOException;
import java.util.List;

import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.ListActivity;
import com.tetragon.desto.R;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Urungrubu;
import com.tetragon.desto.model.UrungrubuItem;
import com.tetragon.desto.model.UrungrubuItemList;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class UrungrubuListFragment extends Fragment implements
		OnItemClickListener, DataListener {

	private ListView urungrubuListView;
	private UrungrubuItemList urungrubuItemList = DbObjects.getUrungrubuItemList();

	private UrungrubuItemList suggestionList = new UrungrubuItemList();
	private boolean suggestion = false;

	private ImageView addImg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Ürün Gruplarý");

		// Urungrubu deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getUrungrubuEventHandler().addListener(this);

		urungrubuListView = (ListView) view.findViewById(R.id.listView);
		urungrubuListView.setOnItemClickListener(this);
		updateUrungrubuList();
		addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUrungrubu();
			}

		});
		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final Dialog editUrungrubuDialog = new Dialog(getActivity());

		final UrungrubuItem urungrubuItem;

		if (suggestion)
			urungrubuItem = suggestionList.get(position);
		else
			urungrubuItem = urungrubuItemList.get(position);

		editUrungrubuDialog.setContentView(R.layout.urungrubu_save);
		editUrungrubuDialog.setTitle(Urungrubu.LABEL_URUNGRUBU + " Kayýtlarý");
		final ProgressBar progressBar = (ProgressBar) editUrungrubuDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editUrungrubuDialog.show();

		final EditText urungrubuEditText = (EditText) editUrungrubuDialog
				.findViewById(R.id.urungrubuEditText);
		urungrubuEditText.setText(urungrubuItem.getUrungrubu());
		final Button vazgecButton = (Button) editUrungrubuDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editUrungrubuDialog.dismiss();
			}
		});
		final Button saveUrungrubuButton = (Button) editUrungrubuDialog
				.findViewById(R.id.saveUrungrubuButton);
		if (urungrubuEditText.getText().toString().trim().isEmpty())
			saveUrungrubuButton.setEnabled(false);
		else
			saveUrungrubuButton.setEnabled(true);
		urungrubuEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (urungrubuEditText.getText().toString().trim().isEmpty())
					saveUrungrubuButton.setEnabled(false);
				else
					saveUrungrubuButton.setEnabled(true);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		});
		saveUrungrubuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveUrungrubuButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				urungrubuItem.setUrungrubu(urungrubuEditText.getText()
				.toString());
				updateUrungrubu(editUrungrubuDialog, urungrubuItem,
						urungrubuEditText);
			}
		});

	}

	private void addUrungrubu() {
		final DbObjects dbObjects = new DbObjects(getActivity());
		final Dialog addUrungrubuDialog = new Dialog(getActivity());
		addUrungrubuDialog.setContentView(R.layout.urungrubu_save);
		addUrungrubuDialog.setTitle(Urungrubu.LABEL_URUNGRUBU + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addUrungrubuDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		addUrungrubuDialog.show();

		final EditText urungrubuEditText = (EditText) addUrungrubuDialog
				.findViewById(R.id.urungrubuEditText);
		final Button vazgecButton = (Button) addUrungrubuDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUrungrubuDialog.dismiss();
			}
		});
		final Button saveUrungrubuButton = (Button) addUrungrubuDialog
				.findViewById(R.id.saveUrungrubuButton);

		if (urungrubuEditText.getText().toString().trim().isEmpty())
			saveUrungrubuButton.setEnabled(false);
		else
			saveUrungrubuButton.setEnabled(true);
		urungrubuEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (urungrubuEditText.getText().toString().trim().isEmpty())
					saveUrungrubuButton.setEnabled(false);
				else
					saveUrungrubuButton.setEnabled(true);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		saveUrungrubuButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final UrungrubuItem urungrubuItem = new UrungrubuItem();
				if (urungrubuEditText != null) {
					urungrubuItem.setIdkey(DestoUtil.generateId());
					urungrubuItem.setUrungrubu(urungrubuEditText
							.getText().toString());
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(final CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addUrungrubuDialog
								.findViewById(R.id.urungrubuEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								result.get(Urungrubu.PROP_URUNGRUBU).toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
					
						DbObjects.getUrungrubuItemList().add(urungrubuItem);
						((ListActivity) getActivity()).getUrungrubuEventHandler()
								.fireUrungrubuChanged();
						addUrungrubuDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Urungrubu.LABEL_URUNGRUBU + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Urungrubu.KIND_URUNGRUBU + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveUrungrubuButton.setEnabled(true);
						vazgecButton.setEnabled(true);
						urungrubuEditText.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				saveUrungrubuButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				urungrubuEditText.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				dbObjects.insert(urungrubuItem.getUrungrubuCE(), mhandler);
			}
		});
	}

	private void updateUrungrubu(final Dialog editUrungrubuDialog,
			final UrungrubuItem urungrubuItem, final TextView urungrubuEditText) {

		final DbObjects dbObjects = new DbObjects(getActivity());
		final ProgressBar progressBar = (ProgressBar) editUrungrubuDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editUrungrubuDialog
						.findViewById(R.id.urungrubuEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getActivity(),
						result.get(Urungrubu.PROP_URUNGRUBU).toString()
								+ " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				
				String idkey=(String) result.get(Urungrubu.PROP_IDKEY);
				UrungrubuItem item2save = DbObjects.getUrungrubuItemList().findUrungrubuItemByIdkey(idkey);//
				item2save.assign(new UrungrubuItem(result));
				DbObjects.getUrungrubuItemList().addOrUpdate(item2save);
				((ListActivity) getActivity()).getUrungrubuEventHandler()
						.fireUrungrubuChanged();
				editUrungrubuDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Urungrubu.KIND_URUNGRUBU + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(),
						Urungrubu.KIND_URUNGRUBU + " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				urungrubuEditText.setEnabled(true);
				progressBar.setVisibility(View.GONE);
			}

		};
		urungrubuEditText.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		dbObjects.update(urungrubuItem.getUrungrubuCE(), mhandler);
	}

	private void updateUrungrubuList(List<UrungrubuItem> list) {
		if (!list.isEmpty()) {
			suggestion = true;
			urungrubuListView.setVisibility(View.VISIBLE);
			urungrubuListView.setAdapter(new UrungrubuPostAdapter(
					getActivity(), android.R.layout.simple_list_item_1, list));
		} else {
			urungrubuListView.setVisibility(View.GONE);
		}
	}

	private void updateUrungrubuList() {
		suggestion = false;
		urungrubuItemList = DbObjects.getUrungrubuItemList();
		if (!urungrubuItemList.isEmpty()) {
			urungrubuListView.setVisibility(View.VISIBLE);
			urungrubuListView.setAdapter(new UrungrubuPostAdapter(
					getActivity(), android.R.layout.simple_list_item_1,
					urungrubuItemList));
		} else {
			urungrubuListView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);

		SearchManager searchManager = (SearchManager) getActivity()
				.getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.action_search).getActionView();

		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getActivity().getComponentName()));

		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {
				loadSuggestions(query, searchView);
				updateUrungrubuList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});
	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new UrungrubuItemList();
		for (UrungrubuItem item : urungrubuItemList) {
			if (item.getUrungrubu().contains(query)) {
				suggestionList.add(item);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateUrungrubuList(suggestionList);
		else updateUrungrubuList();
	}
}

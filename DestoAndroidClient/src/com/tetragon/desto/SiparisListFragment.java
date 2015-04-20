package com.tetragon.desto;

import java.io.IOException;
import java.util.List;

import com.google.cloud.backend.core.CloudBackendMessaging;
import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.MarkaItem;
import com.tetragon.desto.model.MarkaItemList;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.Marka;
import com.tetragon.desto.model.SiparisList;
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
import android.widget.ImageButton;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SiparisListFragment extends Fragment implements
		OnItemClickListener, DataListener {

	private ListView siparisListView;
	private SiparisList siparisList = DbObjects.getSiparisList();
	private MarkaItemList markaItemList = DbObjects.getMarkaItemList();

	private SiparisList suggestionList = new SiparisList();
	private boolean suggestion = false;

	private CloudBackendMessaging backendMessaging;
	private Spinner markaspinner;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);
		getActivity().setTitle("Sipariþ Listesi");

		// Siparis deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getSiparisEventHandler().addListener(this);

		siparisListView = (ListView) view.findViewById(R.id.listView);
		siparisListView.setOnItemClickListener(this);
		updateSiparisList();
		ImageView addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addSiparis();
			}

		});
		final Dialog editSiparisDialog = new Dialog(getActivity());
		editSiparisDialog.setContentView(R.layout.siparis_save);
		editSiparisDialog.setTitle(R.string.title_siparis_list);

		ProgressBar progressBar = (ProgressBar) view
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final Dialog editSiparisDialog = new Dialog(getActivity());
		
		final Siparis siparis;
		if (suggestion)
			siparis = suggestionList.get(position);
		else
			siparis = siparisList.get(position);


		editSiparisDialog.setContentView(R.layout.siparis_save);
		editSiparisDialog.setTitle(Siparis.LABEL_SIPARIS + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editSiparisDialog.show();

		final EditText siparisEditText = (EditText) editSiparisDialog
				.findViewById(R.id.siparisEditText);
		siparisEditText.setText(siparis.getEtiket());

		// Marka spinner
		markaspinner = (Spinner) editSiparisDialog
				.findViewById(R.id.markaspinner);

		ArrayAdapter<MarkaItem> madapter = new ArrayAdapter<MarkaItem>(getActivity(),
				android.R.layout.simple_spinner_item, markaItemList);

		madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		markaspinner.setAdapter(madapter);

		// Siparis Tarihi
		final TextView siparisTarihiSpinner = (TextView) editSiparisDialog
				.findViewById(R.id.siparisTarihiPicker);

		if (siparis.getEtiket() != null)
			siparisEditText.setText(siparis.getEtiket());

		if (siparis.getSiparisTarihi() != null)
			siparisTarihiSpinner.setText(siparis.getSiparisTarihi());

		if (siparis.getMarkaItem() != null)
			markaspinner.setSelection(madapter.getPosition(siparis.getMarkaItem()));

		final ImageButton vazgecButton = (ImageButton) editSiparisDialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				editSiparisDialog.dismiss();
			}
		});
		final ImageButton saveSiparisButton = (ImageButton) editSiparisDialog
				.findViewById(R.id.saveButton);
		if (siparisEditText.getText().toString().trim().isEmpty())
			saveSiparisButton.setEnabled(false);
		else
			saveSiparisButton.setEnabled(true);
		siparisEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (siparisEditText.getText().toString().trim().isEmpty())
					saveSiparisButton.setEnabled(false);
				else
					saveSiparisButton.setEnabled(true);
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

		saveSiparisButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSiparisButton.setEnabled(false);
				vazgecButton.setEnabled(false);

				int mpos = markaspinner.getSelectedItemPosition();
				MarkaItem pmarkaItem = markaItemList.get(mpos);
				siparis.setMarkaItem(pmarkaItem);
				siparis.setSiparisTarihi((String) siparisTarihiSpinner.getText());
				updateSiparis(editSiparisDialog, siparis,
						siparisEditText, position);
			}
		});
		updateSiparisList();
	}
	
	private void updateSiparis(final Dialog editSiparisDialog,
			final Siparis siparis, final TextView siparisEditText, final int position) {
		
		siparis.put(Siparis.PROP_IDKEY, siparis.getIdkey());
		siparis.put(Siparis.PROP_ETIKET, siparisEditText.getText().toString());
		siparis.put(Siparis.PROP_MARKA_ID,siparis.getMarkaItem().getIdkey());
		siparis.put(Siparis.PROP_SIPARIS_TARIHI,siparis.getSiparisTarihi());
		
		final ProgressBar progressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editSiparisDialog
						.findViewById(R.id.siparisEditLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getActivity(),
						result.get(Siparis.PROP_ETIKET).toString()
								+ " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getSiparisList().setSiparis(siparis);;
				((ListActivity) getActivity()).getSiparisEventHandler()
						.fireSiparisChanged();
				editSiparisDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Siparis.KIND_SIPARIS + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast.makeText(getActivity(),
						Siparis.LABEL_SIPARIS + " kayýt hatasý! ",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				progressBar.setVisibility(View.GONE);
			}

		};
		if (backendMessaging == null) {
			backendMessaging = new CloudBackendMessaging(getActivity());
		}
		siparisEditText.setEnabled(false);
		markaspinner.setEnabled(false);
		progressBar.setVisibility(View.VISIBLE);
		backendMessaging.update(siparis, mhandler);
	}

	private void addSiparis() {
		final Dialog addSiparisDialog = new Dialog(getActivity());
		addSiparisDialog.setContentView(R.layout.siparis_save);
		addSiparisDialog.setTitle(Siparis.LABEL_SIPARIS + " Giriþi");
		final ProgressBar progressBar = (ProgressBar) addSiparisDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		addSiparisDialog.show();
		final Siparis siparis = new Siparis(Siparis.KIND_SIPARIS);

		final EditText siparisEditText = (EditText) addSiparisDialog
				.findViewById(R.id.siparisEditText);
		final ImageButton vazgecButton = (ImageButton) addSiparisDialog
				.findViewById(R.id.vazgecButton);

		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addSiparisDialog.dismiss();
			}
		});
		markaspinner = (Spinner) addSiparisDialog
				.findViewById(R.id.markaspinner);

		ArrayAdapter<MarkaItem> markaadapter = new ArrayAdapter<MarkaItem>(
				getActivity(), android.R.layout.simple_spinner_item, markaItemList);
		markaadapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		markaspinner.setAdapter(markaadapter);

		// Siparis Tarihi spinner
		final TextView siparisTarihiSpinner = (TextView) addSiparisDialog
				.findViewById(R.id.siparisTarihiPicker);
		siparisTarihiSpinner.setText(DestoUtil.getToday());

		final ImageButton saveSiparisButton = (ImageButton) addSiparisDialog
				.findViewById(R.id.saveButton);

		if (siparisEditText.getText().toString().trim().isEmpty())
			saveSiparisButton.setEnabled(false);
		else
			saveSiparisButton.setEnabled(true);
		siparisEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (siparisEditText.getText().toString().trim().isEmpty())
					saveSiparisButton.setEnabled(false);
				else
					saveSiparisButton.setEnabled(true);
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
		saveSiparisButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (siparisEditText != null) {

					siparis.put(Siparis.PROP_IDKEY, DestoUtil.generateId());
					siparis.put(Siparis.PROP_ETIKET, siparisEditText.getText()
							.toString());
					siparis.put(Siparis.PROP_SIPARIS_TARIHI,
							siparisTarihiSpinner.getText().toString());

					int mpos = markaspinner.getSelectedItemPosition();
					MarkaItem pmarkaItem = markaItemList.get(mpos);
					siparis.put(Marka.PROP_MARKA, pmarkaItem.getIdkey());
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(final CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) addSiparisDialog
								.findViewById(R.id.siparisEditLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(), result
								.get(Siparis.PROP_ETIKET).toString()
								+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						Siparis siparisToSave = new Siparis(result.getKindName(),
								result);
						DbObjects.getSiparisList().add(siparisToSave);
						((ListActivity) getActivity()).getSiparisEventHandler()
								.fireSiparisChanged();
						addSiparisDialog.dismiss();
					}

					@Override
					public void onError(final IOException exception) {
						Log.i(Siparis.LABEL_SIPARIS + " kayýt hatasý! :",
								exception.toString());
						Toast toast = Toast.makeText(getActivity(),
								Siparis.LABEL_SIPARIS + " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						saveSiparisButton.setEnabled(true);
						markaspinner.setEnabled(true);
						vazgecButton.setEnabled(true);
						siparisEditText.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				if (backendMessaging == null) {
					backendMessaging = new CloudBackendMessaging(getActivity());
				}
				saveSiparisButton.setEnabled(false);
				markaspinner.setEnabled(false);
				vazgecButton.setEnabled(false);
				siparisEditText.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				backendMessaging.insert(siparis, mhandler);
			}
		});

	}

	
	private void updateSiparisList() {
		suggestion = false;
		siparisList = DbObjects.getSiparisList();
		if (!siparisList.isEmpty()) {
			siparisListView.setVisibility(View.VISIBLE);
			siparisListView.setAdapter(new SiparisPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, siparisList));
		} else {
			siparisListView.setVisibility(View.GONE);
		}
	}

	private void updateSiparisList(List<Siparis> list) {
		if (!list.isEmpty()) {
			suggestion = false;
			siparisListView.setVisibility(View.VISIBLE);
			siparisListView.setAdapter(new SiparisPostAdapter(getActivity(),
					android.R.layout.simple_list_item_1, list));
		} else {
			siparisListView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_main_actions, menu);
		super.onCreateOptionsMenu(menu, inflater);

		// Associate searchable configuration with the SearchView
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
				updateSiparisList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new SiparisList();
		for (Siparis siparis : siparisList) {
			if (siparis.getEtiket().contains(query)) {
				suggestionList.add(siparis);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateSiparisList(suggestionList);
		else
			updateSiparisList();
	}
}

package com.tetragon.desto;

import java.io.IOException;
import java.util.List;

import com.google.cloud.backend.core.CloudBackendMessaging;
import com.google.cloud.backend.core.CloudCallbackHandler;
import com.google.cloud.backend.core.CloudEntity;
import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.ModelItem;
import com.tetragon.desto.model.ModelItemList;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.SiparisList;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.model.Siparis_ayrintiList;
import com.tetragon.desto.util.DestoConstants;
import com.tetragon.desto.util.DestoUtil;

import android.app.Dialog;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
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
import android.widget.NumberPicker;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

public class SiparisAyrintiListFragment extends Fragment implements
		OnItemClickListener, DataListener {

	private ListView siparisAyrintiListView;
	private Siparis_ayrintiList siparisAyrintiList = DbObjects
			.getSiparisAyrintiList();
	private SiparisList siparisList = DbObjects
			.getSiparisList();
	private ModelItemList modelList = DbObjects.getModelItemList();

	private Siparis_ayrintiList suggestionList = new Siparis_ayrintiList();
	private boolean suggestion = false;

	private ProgressBar progressBar;

	private CloudBackendMessaging backendMessaging;
	private Siparis siparisCE = new Siparis(Siparis.KIND_SIPARIS);

	public SiparisAyrintiListFragment(String siparisId) {
		siparisCE = siparisList.findSiparisByIdkey(siparisId);
		siparisAyrintiList = DbObjects.getSiparisAyrintiList(siparisCE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = inflater.inflate(R.layout.desto_list, container, false);
		setHasOptionsMenu(true);

		// Siparis deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getSiparisAyrintiEventHandler().addListener(this);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

		siparisAyrintiListView = (ListView) view.findViewById(R.id.listView);
		siparisAyrintiListView.setOnItemClickListener(this);
		if (siparisCE.getEtiket()!=null)
			getActivity().setTitle(siparisCE.getEtiket());
		updateSiparisAyrintiList();
		progressBar.setVisibility(View.GONE);
		ImageView addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addSiparis();
			}

		});
		final Dialog editSiparisDialog = new Dialog(getActivity());
		editSiparisDialog.setContentView(R.layout.siparis_ayrinti_save);
		editSiparisDialog.setTitle(R.string.title_siparis_list);

		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		final Dialog editSiparisDialog = new Dialog(getActivity());
		
		final Siparis_ayrinti siparis_ayrinti;
		if (suggestion)
			siparis_ayrinti = suggestionList.get(position);
		else
			siparis_ayrinti = siparisAyrintiList.get(position);
		
		editSiparisDialog.setContentView(R.layout.siparis_ayrinti_save);
		editSiparisDialog.setTitle("Sipariþ Ekleme");

		final ProgressBar progressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		editSiparisDialog.show();
		final NumberPicker siparisAdetPicker = (NumberPicker) editSiparisDialog
				.findViewById(R.id.adetPicker);
		siparisAdetPicker.setMaxValue(Siparis.SIPARIS_ADET_MAX);
		siparisAdetPicker.setMinValue(findGelenAdet(siparis_ayrinti));

		// Model spinner
		final Spinner modelSpinner = (Spinner) editSiparisDialog
				.findViewById(R.id.modelSpinner);

		ArrayAdapter<ModelItem> madapter = new ArrayAdapter<ModelItem>(getActivity(),
				android.R.layout.simple_spinner_item, modelList);

		madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		modelSpinner.setAdapter(madapter);

		if (siparis_ayrinti.getSiparis_adedi() != null) {
			int adet = Integer.valueOf((String) siparis_ayrinti.getSiparis_adedi());
			siparisAdetPicker.setValue(adet);

		}

		if (siparis_ayrinti.getModelItem() != null)
			modelSpinner.setSelection(madapter.getPosition(siparis_ayrinti
					.getModelItem()));

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

		if (siparisAdetPicker.getValue() < 1)
			saveSiparisButton.setEnabled(false);
		else
			saveSiparisButton.setEnabled(true);

		siparisAdetPicker
				.setOnScrollListener(new NumberPicker.OnScrollListener() {

					@Override
					public void onScrollStateChange(NumberPicker numberPicker,
							int scrollState) {
						if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
							if (numberPicker.getValue() < 1)
								saveSiparisButton.setEnabled(false);
							else
								saveSiparisButton.setEnabled(true);
						}
					}
				});

		saveSiparisButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSiparisButton.setEnabled(false);
				vazgecButton.setEnabled(false);

				int mpos = modelSpinner.getSelectedItemPosition();
				ModelItem pmodel = modelList.get(mpos);
				siparis_ayrinti.setModelItem(pmodel);
				siparis_ayrinti.setSiparis_adedi(String.valueOf(siparisAdetPicker
						.getValue()));

				updateSiparisAyrinti(editSiparisDialog, siparis_ayrinti,
						position);
			}
		});
		updateSiparisAyrintiList();
	}
	public int findGelenAdet(Siparis_ayrinti siparis_ayrinti) {
		int adet = 0;
		if ((siparis_ayrinti.getGelen_adet() != null)
				&& (!siparis_ayrinti.getGelen_adet().isEmpty())
				&& (DestoUtil.isNumeric(siparis_ayrinti.getGelen_adet())))
			adet = Integer.valueOf(siparis_ayrinti.getGelen_adet());

		return adet;
	}
	private void updateSiparisAyrinti(final Dialog editSiparisDialog,
			final Siparis_ayrinti siparisAyrinti, final int position) {

		siparisAyrinti.put(Siparis_ayrinti.PROP_MODEL_ID,
				siparisAyrinti.getModelItem().getIdkey());
		siparisAyrinti.put(Siparis_ayrinti.PROP_SIPARIS_ADEDI, siparisAyrinti.getSiparis_adedi());
		siparisAyrinti.put(Siparis_ayrinti.PROP_SIPARIS_ID,
				siparisCE.get(Siparis.PROP_IDKEY));

		final ProgressBar progressBar = (ProgressBar) editSiparisDialog
				.findViewById(R.id.progressBar);
		CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {

			public void onComplete(final CloudEntity result) {
				LinearLayout linearLayout = (LinearLayout) editSiparisDialog
						.findViewById(R.id.linearLayout);
				linearLayout.setBackgroundResource(R.color.blue);
				Toast toast = Toast.makeText(getActivity(), siparisAyrinti
						.getModelItem() + " kaydedildi",
						DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				DbObjects.getSiparisAyrintiList().setSiparis_ayrinti(
						siparisAyrinti);
				((ListActivity) getActivity()).getSiparisAyrintiEventHandler()
						.fireSiparisAyrintiChanged();

				editSiparisDialog.dismiss();
			}

			@Override
			public void onError(final IOException exception) {
				Log.i(Siparis_ayrinti.KIND_SIPARISAYRINTI + " kayýt hatasý! :",
						exception.toString());
				Toast toast = Toast
						.makeText(getActivity(),
								Siparis_ayrinti.LABEL_SIPARISAYRINTI
										+ " kayýt hatasý! ",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
				toast.show();
				progressBar.setVisibility(View.GONE);
			}

		};
		if (backendMessaging == null) {
			backendMessaging = new CloudBackendMessaging(getActivity());
		}
		progressBar.setVisibility(View.VISIBLE);
		backendMessaging.update(siparisAyrinti, mhandler);
	}

	private void addSiparis() {
		final Dialog dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.siparis_ayrinti_save);
		dialog.setTitle(siparisCE.getEtiket() + ": Ürün Ekleme");
		final ProgressBar progressBar = (ProgressBar) dialog
				.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.GONE);
		dialog.show();

		final Siparis_ayrinti siparisAyrinti = new Siparis_ayrinti(
				Siparis_ayrinti.KIND_SIPARISAYRINTI);

		final NumberPicker siparisAdetPicker = (NumberPicker) dialog
				.findViewById(R.id.adetPicker);
		siparisAdetPicker.setMaxValue(100);
		siparisAdetPicker.setMinValue(0);

		// Model spinner
		final Spinner modelSpinner = (Spinner) dialog
				.findViewById(R.id.modelSpinner);

		ArrayAdapter<ModelItem> madapter = new ArrayAdapter<ModelItem>(getActivity(),
				android.R.layout.simple_spinner_item, modelList);

		madapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		modelSpinner.setAdapter(madapter);

		if (siparisAyrinti.getSiparis_adedi() != null) {
			int adet = Integer.valueOf((String) siparisAyrinti.getSiparis_adedi());

			siparisAdetPicker.setValue(adet);

		}

		if (siparisAyrinti.getModelItem() != null)
			modelSpinner.setSelection(madapter.getPosition(siparisAyrinti
					.getModelItem()));

		final ImageButton vazgecButton = (ImageButton) dialog
				.findViewById(R.id.vazgecButton);
		vazgecButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		final ImageButton saveSiparisButton = (ImageButton) dialog
				.findViewById(R.id.saveButton);

		if (siparisAdetPicker.getValue() < 1)
			saveSiparisButton.setEnabled(false);
		else
			saveSiparisButton.setEnabled(true);

		siparisAdetPicker
				.setOnScrollListener(new NumberPicker.OnScrollListener() {

					@Override
					public void onScrollStateChange(NumberPicker numberPicker,
							int scrollState) {
						if (scrollState == NumberPicker.OnScrollListener.SCROLL_STATE_IDLE) {
							if (numberPicker.getValue() < 1)
								saveSiparisButton.setEnabled(false);
							else
								saveSiparisButton.setEnabled(true);
						}
					}
				});
		saveSiparisButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				saveSiparisButton.setEnabled(false);
				vazgecButton.setEnabled(false);

				int mpos = modelSpinner.getSelectedItemPosition();
				ModelItem pmodel = modelList.get(mpos);

				if (siparisAdetPicker.getValue() > 0) {

					siparisAyrinti.put(Siparis_ayrinti.PROP_IDKEY,
							DestoUtil.generateId());
					siparisAyrinti.put(Siparis_ayrinti.PROP_SIPARIS_ID,
							siparisCE.get(Siparis.PROP_IDKEY));
					siparisAyrinti.put(Siparis_ayrinti.PROP_SIPARIS_ADEDI,
							siparisAdetPicker.getValue());
					siparisAyrinti.put(Siparis_ayrinti.PROP_MODEL_ID,
							pmodel.getIdkey());
				}

				CloudCallbackHandler<CloudEntity> mhandler = new CloudCallbackHandler<CloudEntity>() {
					public void onComplete(final CloudEntity result) {

						LinearLayout linearLayout = (LinearLayout) dialog
								.findViewById(R.id.linearLayout);
						linearLayout.setBackgroundResource(R.color.blue);
						Toast toast = Toast.makeText(getActivity(),
								modelSpinner.getSelectedItem().toString()
										+ " kaydedildi",
								DestoConstants.TOAST_MESSAGE_SHOWTIME);
						toast.show();
						Siparis_ayrinti siparisAyrintiToSave = new Siparis_ayrinti(
								result.getKindName(), result);
						DbObjects.getSiparisAyrintiList().add(siparisAyrintiToSave);
						((ListActivity) getActivity()).getSiparisAyrintiEventHandler()
								.fireSiparisAyrintiChanged();
						dialog.dismiss();
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
						vazgecButton.setEnabled(true);
						siparisAdetPicker.setEnabled(true);
						modelSpinner.setEnabled(true);
						progressBar.setVisibility(View.GONE);
					}
				};
				if (backendMessaging == null) {
					backendMessaging = new CloudBackendMessaging(getActivity());
				}
				saveSiparisButton.setEnabled(false);
				vazgecButton.setEnabled(false);
				siparisAdetPicker.setEnabled(false);
				modelSpinner.setEnabled(false);
				progressBar.setVisibility(View.VISIBLE);
				backendMessaging.insert(siparisAyrinti, mhandler);
			}
		});

	}

	private void updateSiparisAyrintiList() {
		suggestion = false;
		siparisAyrintiList = DbObjects.getSiparisAyrintiList(siparisCE);
		if (!siparisAyrintiList.isEmpty()) {
			siparisAyrintiListView.setVisibility(View.VISIBLE);
			siparisAyrintiListView.setAdapter(new SiparisAyrintiPostAdapter(
					getActivity(), android.R.layout.simple_list_item_1,
					siparisAyrintiList));
		} else {
			siparisAyrintiListView.setVisibility(View.GONE);
		}
	}

	private void updateSiparisAyrintiList(List<Siparis_ayrinti> list) {
		if (!list.isEmpty()) {
			suggestion = false;
			siparisAyrintiListView.setVisibility(View.VISIBLE);
			siparisAyrintiListView
					.setAdapter(new SiparisAyrintiPostAdapter(getActivity(),
							android.R.layout.simple_list_item_1, list));
		} else {
			siparisAyrintiListView.setVisibility(View.GONE);
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
				updateSiparisAyrintiList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new Siparis_ayrintiList();
		for (Siparis_ayrinti siparis_ayrinti : siparisAyrintiList) {
			if (siparis_ayrinti.getModelItem().getModel().contains(query)) {
				suggestionList.add(siparis_ayrinti);
			}

		}

	}

	public Siparis getSiparisCE() {
		return siparisCE;
	}

	public void setSiparisCE(Siparis siparisCE) {
		this.siparisCE = siparisCE;
	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateSiparisAyrintiList(suggestionList);
		else 
			updateSiparisAyrintiList();

	}
}

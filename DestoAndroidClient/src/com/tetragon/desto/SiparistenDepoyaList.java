package com.tetragon.desto;

import java.util.List;

import com.google.cloud.backend.core.DbObjects;
import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.Siparis;
import com.tetragon.desto.model.Siparis_ayrinti;
import com.tetragon.desto.model.Siparis_ayrintiList;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

public class SiparistenDepoyaList extends Fragment implements
		OnItemClickListener, DataListener {

	private ListView siparisAyrintiListView;
	private Siparis_ayrintiList siparisAyrintiList = DbObjects
			.getSiparisAyrintiList();

	private Siparis_ayrintiList suggestionList = new Siparis_ayrintiList();
	private boolean suggestion = false;

	private ProgressBar progressBar;

	private Siparis siparisCE = new Siparis(Siparis.KIND_SIPARIS);

	public SiparistenDepoyaList() {
		siparisAyrintiList = DbObjects.getSiparisAyrintiList();
		siparisAyrintiList=siparisAyrintiList.sort();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Set view
		View view = inflater.inflate(R.layout.desto_list, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		setHasOptionsMenu(true);

		// Siparis deðiþikliklerini dinle
		addSiparisListener();
		
		// Create visual items
		addVisualItems(view);
		
		// Update List view
		updateSiparisAyrintiList();
		progressBar.setVisibility(View.GONE);
		
		return view;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			final int position, long id) {
		
//		openEditDialog(position);
		updateSiparisAyrintiList();
	}
	



	private void updateSiparisAyrintiList() {
		suggestion = false;
		if (siparisCE.getId()!=null){
			siparisAyrintiList = DbObjects.getSiparisAyrintiList(siparisCE);
		}else
			siparisAyrintiList = DbObjects.getSiparisAyrintiList();
		if (!siparisAyrintiList.isEmpty()) {
			siparisAyrintiListView.setVisibility(View.VISIBLE);
			siparisAyrintiListView.setAdapter(new SiparistenDepoyaPostAdapter(
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
					.setAdapter(new SiparistenDepoyaPostAdapter(getActivity(),
							android.R.layout.simple_list_item_1, list));
		} else {
			siparisAyrintiListView.setVisibility(View.GONE);
		}
	}

	
	private void addVisualItems(View view) {
		
		siparisAyrintiListView = (ListView) view.findViewById(R.id.listView);
		siparisAyrintiListView.setOnItemClickListener(this);
		ImageView addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setVisibility(View.GONE);
	}

	private void addSiparisListener() {
		// Siparis deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getSiparisAyrintiEventHandler().addListener(this);
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
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.activity_main_actions, menu);
// 
//        return super.onCreateOptionsMenu(menu);
//    }

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

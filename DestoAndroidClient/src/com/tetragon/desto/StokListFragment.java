package com.tetragon.desto;

import java.util.List;

import com.tetragon.desto.eventHandler.DataChangedEvent;
import com.tetragon.desto.eventHandler.DataListener;
import com.tetragon.desto.model.StokItem;
import com.tetragon.desto.model.StokItemList;
import com.tetragon.desto.util.DestoUtil;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

public class StokListFragment extends Fragment implements
		DataListener {

	private ListView stokListView;
	private StokItemList stokItemList;// = ((DestoApplication) getActivity().getApplication()).getStokItemList();//DbObjects.getStokItemList();

	private StokItemList suggestionList = new StokItemList();
	private boolean suggestion = false;

	private ProgressBar progressBar;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		stokItemList = ((DestoApplication) getActivity().getApplication()).getStokItemList();//DbObjects.getStokItemList();
		// Set view
		View view = inflater.inflate(R.layout.desto_list, container, false);
		progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
		setHasOptionsMenu(true);

		// Stok deðiþikliklerini dinle
		addStokListener();
		
		// Create visual items
		addVisualItems(view);
		
		// Update List view
		updateStokList();
		progressBar.setVisibility(View.GONE);
		
		return view;
	}

	private void updateStokList() {
		suggestion = false;
		stokItemList = ((DestoApplication) getActivity().getApplication()).getStokItemList();//DbObjects.getStokItemList();
		if (!stokItemList.isEmpty()) {
			stokListView.setVisibility(View.VISIBLE);
			stokListView.setAdapter(new StokPostAdapter(
					getActivity(), android.R.layout.simple_list_item_1,
					stokItemList));
		} else {
			stokListView.setVisibility(View.GONE);
		}
	}

	private void updateStokList(List<StokItem> list) {
		if (!list.isEmpty()) {
			suggestion = false;
			stokListView.setVisibility(View.VISIBLE);
			stokListView
					.setAdapter(new StokPostAdapter(getActivity(),
							android.R.layout.simple_list_item_1, list));
		} else {
			stokListView.setVisibility(View.GONE);
		}
	}

	
	private void addVisualItems(View view) {
		
		stokListView = (ListView) view.findViewById(R.id.listView);
		ImageView addImg = (ImageView) view.findViewById(R.id.ic_add);
		addImg.setVisibility(View.GONE);
	}

	private void addStokListener() {
		// Stok deðiþikliklerini dinle
		ListActivity activity = (ListActivity) getActivity();
		activity.getStokEventHandler().addListener(this);
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
				updateStokList(suggestionList);
				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;

			}

		});

	}

	private void loadSuggestions(String query, SearchView searchView) {
		suggestionList = new StokItemList();
		for (StokItem stok : stokItemList) {
			if (stok.getModelItem().getModel()
					.toLowerCase(DestoUtil.getLocale())
					.contains(query.toLowerCase(DestoUtil.getLocale()))) {
				suggestionList.add(stok);
			}if (stok.getModelItem().getMarkaItem().getMarka()
					.toLowerCase(DestoUtil.getLocale())
					.contains(query.toLowerCase(DestoUtil.getLocale()))) {
				suggestionList.add(stok);
			}if (stok.getModelItem().getUrun_tipItem().getUrun_tip()
					.toLowerCase(DestoUtil.getLocale())
					.contains(query.toLowerCase(DestoUtil.getLocale()))) {
				suggestionList.add(stok);
			}if (stok.getStok_yeri().getStok_yeri()
					.toLowerCase(DestoUtil.getLocale())
					.contains(query.toLowerCase(DestoUtil.getLocale()))) {
				suggestionList.add(stok);
			}

		}

	}

	@Override
	public <T> void dataChanged(DataChangedEvent<T> e) {
		if (suggestion)
			updateStokList(suggestionList);
		else 
			updateStokList();

	}
}

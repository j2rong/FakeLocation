package com.rong.library.widget.mapsearchbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rong.library.widget.HighlightTextView;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Copyright (c) 2016-2017, j2Rong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> implements
		OnSearchResultItemClickListener {

	interface OnSuggestionItemClickListener {
		void onSuggestionItemClick(int pos, View v, SearchResult data);
	}

	private List<SearchResult> mList = null;
	private String currentQuery;

	private WeakReference<OnSuggestionItemClickListener> mListener;
	private Context mContext;
	private View calcView = null;

	private String defEmptyStr;
	private Drawable iconHistory;
	private Drawable iconSearch;
	private Drawable iconPlace;

	SearchResultAdapter(Context c, OnSuggestionItemClickListener listener, String empty) {
		mContext = c;
		mListener = new WeakReference<>(listener);
		defEmptyStr = empty;

		iconHistory = ContextCompat.getDrawable(c, R.drawable.ic_history_white_24dp);
		iconSearch = ContextCompat.getDrawable(c, R.drawable.ic_search_white_24dp);
		iconPlace = ContextCompat.getDrawable(c, R.drawable.ic_place_white_24dp);
	}


	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false);
		return new ViewHolder(v, this);
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		SearchResult r = getItem(position);

		if (r != null) {
			holder.txt1st.setText(r.displayFirst);
			holder.txt2nd.setText(r.displaySecond);

			// highlight
			if (!TextUtils.isEmpty(currentQuery)) {
				holder.txt1st.highlight(currentQuery);
				holder.txt2nd.highlight(currentQuery);
			}

			switch (r.type) {
				case SearchResult.TYPE_LOCATION_RECENT: {
					if (TextUtils.isEmpty(r.displayFirst)) {
						holder.txt1st.setText(defEmptyStr);
					}

					holder.icon.setImageDrawable(iconHistory);
					break;
				}
				case SearchResult.TYPE_MAP_SEARCH_PLACE: {
					holder.icon.setImageDrawable(iconPlace);
					break;
				}
				case SearchResult.TYPE_MAP_SEARCH_WITHOUT_LOCATION: {
					holder.icon.setImageDrawable(iconSearch);
				}
			}
		}
	}

	private SearchResult getItem(int position) {
		if (mList == null || mList.size() == 0)
			return null;
		else
			return mList.get(position);
	}

	@Override
	public int getItemCount() {
		return (mList == null) ? 0 : mList.size();
	}

	void setList(List<SearchResult> list, String query) {
		mList = list;
		currentQuery = query;
	}

	int getSuggestedHeight(RecyclerView parent, int max) {
		if (getItemCount() == 0)
			return 0;

		if (calcView == null)
			calcView = LayoutInflater.from(mContext).inflate(R.layout.item_search_result, parent, false);

		HighlightTextView txt1st = calcView.findViewById(R.id.txt_1st);
		HighlightTextView txt2nd = calcView.findViewById(R.id.txt_2nd);

		int total = 0;

		// 1dp separator
		DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
		total += ((int)(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, metrics)));

		for (int i = 0; i < getItemCount(); i++) {
			SearchResult r = getItem(i);
			if (r != null) {
				txt1st.setText(r.displayFirst);
				txt2nd.setText(r.displaySecond);

				calcView.measure(View.MeasureSpec.makeMeasureSpec(parent.getMeasuredWidth(), View.MeasureSpec.EXACTLY),
						View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
				total += calcView.getMeasuredHeight();

				if (total > max) {
					total = max;
					break;
				}
			}
		}

		return total;
	}

	@Override
	public void onItemViewClick(int position, View v) {
		if (mListener != null) {
			if (mListener.get() != null) {
				mListener.get().onSuggestionItemClick(position, v, getItem(position));
			}
		}
	}

	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

		AppCompatImageView icon;
		HighlightTextView txt1st;
		HighlightTextView txt2nd;

		private WeakReference<OnSearchResultItemClickListener> listener;

		ViewHolder(View itemView, OnSearchResultItemClickListener l) {
			super(itemView);

			icon = itemView.findViewById(R.id.image);
			txt1st = itemView.findViewById(R.id.txt_1st);
			txt2nd = itemView.findViewById(R.id.txt_2nd);

			listener = new WeakReference<>(l);
			itemView.setOnClickListener(this);
		}

		@Override
		public void onClick(View v) {
			if (listener != null) {
				if (listener.get() != null)
					listener.get().onItemViewClick(getAdapterPosition(), v);
			}
		}
	}
}

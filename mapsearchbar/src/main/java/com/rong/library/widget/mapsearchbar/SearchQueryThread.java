package com.rong.library.widget.mapsearchbar;

import java.lang.ref.WeakReference;

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
class SearchQueryThread extends Thread {

	private volatile boolean bStopping = false;

	private String cachedQuery = null;
	private String strQuerying = "";
	private WeakReference<MapSearchBar.MapSearchActionListener> listener = null;

	void pushQuery(String query) {
		strQuerying = query;
	}

	void updateListener(MapSearchBar.MapSearchActionListener l) {
		if (l != null) {
			listener = new WeakReference<>(l);
		} else {
			listener = null;
		}
	}

	public void quit() {
		if (!bStopping) {
			bStopping = true;
			this.interrupt();
		}
	}

	@Override
	public void run() {
		while (!bStopping) {
			if (strQuerying != null) {
				if (!strQuerying.equals(cachedQuery)) {
					cachedQuery = strQuerying;

					if (listener != null) {
						MapSearchBar.MapSearchActionListener l = listener.get();
						if (l != null)
							l.onSearchQueryChangedAsync(cachedQuery);
					}
				}
			}

			try {
				Thread.sleep(500);
			} catch (InterruptedException ignored) {
				ignored.printStackTrace();
			}
		}
	}

}

package com.rong.library.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rong.library.widget.mapsearchbar.R;

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
public class HighlightTextView extends android.support.v7.widget.AppCompatTextView {

	public HighlightTextView(Context context) {
		super(context);
		init(null, 0);
	}

	public HighlightTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs, 0);
	}

	public HighlightTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs, defStyle);
	}


	private int mHighlightTextColor = Color.BLACK;
	private int mHighlightBackgroundColor = Color.TRANSPARENT;
	private boolean mCaseSensitive = false;
	private boolean mHighlightAllOccurrences = true;
	private boolean mHighlightTextBold = false;

	private void init(AttributeSet attrs, int defStyle) {
		final TypedArray a = getContext().obtainStyledAttributes(
				attrs, R.styleable.HighlightTextView, defStyle, 0);

		mHighlightTextColor = a.getColor(R.styleable.HighlightTextView_htv_textHighlightColor, getCurrentTextColor());
		mHighlightBackgroundColor = a.getColor(R.styleable.HighlightTextView_htv_textHighlightBackgroundColor, Color.TRANSPARENT);
		mCaseSensitive = a.getBoolean(R.styleable.HighlightTextView_htv_caseSensitive, false);
		mHighlightAllOccurrences = a.getBoolean(R.styleable.HighlightTextView_htv_highlightAllOccurrences, true);
		mHighlightTextBold = a.getBoolean(R.styleable.HighlightTextView_htv_highlightTextBold, false);

		a.recycle();
	}

	public void setHighlightTextColor(@ColorInt int color) {
		this.mHighlightTextColor = color;
	}

	public int getHighlightTextColor() {
		return mHighlightTextColor;
	}

	public void setHighlightBackgroundColor(@ColorInt int color) {
		this.mHighlightBackgroundColor = color;
	}

	public int getHighlightBackgroundColor() {
		return mHighlightBackgroundColor;
	}

	public boolean isCaseSensitive() {
		return mCaseSensitive;
	}

	public void setCaseSensitive(boolean mCaseSensitive) {
		this.mCaseSensitive = mCaseSensitive;
	}

	public boolean isHighlightAllOccurrences() {
		return mHighlightAllOccurrences;
	}

	public void setHighlightAllOccurrences(boolean mHighlightAllOccurrences) {
		this.mHighlightAllOccurrences = mHighlightAllOccurrences;
	}

	public void highlight(String textToHighlight) {
		if (textToHighlight == null) {
			// clear highlight
			String text = getText().toString();
			setText(text);
			return;
		}

		if (!TextUtils.isEmpty(getText()) && !textToHighlight.isEmpty()) {
			final String text = getText().toString();
			final Spannable spannableString = new SpannableString(text);

			String textCopy;
			String textHighlightCopy;
			if (!mCaseSensitive) {
				textCopy = text.toLowerCase();
				textHighlightCopy = textToHighlight.toLowerCase();
			} else {
				textCopy = text;
				textHighlightCopy = textToHighlight;
			}

			int matchStart = textCopy.indexOf(textHighlightCopy, 0);
			if (matchStart == -1) {
				// no match , return
				return;
			}

			int searchStart;
			do {
				searchStart = matchStart + textHighlightCopy.length();

				spannableString.setSpan(new ForegroundColorSpan(mHighlightTextColor),
						matchStart, searchStart, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				spannableString.setSpan(new BackgroundColorSpan(mHighlightBackgroundColor),
						matchStart, searchStart, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
				if (mHighlightTextBold)
					spannableString.setSpan(new StyleSpan(Typeface.BOLD),
							matchStart, searchStart, Spanned.SPAN_INCLUSIVE_INCLUSIVE);

				if (!mHighlightAllOccurrences) {
					break;
				}
			} while ((matchStart = textCopy.indexOf(textHighlightCopy, searchStart)) != -1);

			setText(spannableString);
		}
	}

}
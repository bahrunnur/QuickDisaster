package com.google.disasterrecovery;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

/**
 * @author bahrunnur
 * 
 */
public class MyCardScrollAdapter extends CardScrollAdapter {

	private List<Card> mCards;

	public MyCardScrollAdapter(List<Card> cards) {
		this.mCards = cards;
	}

	@Override
	public int getCount() {
		return mCards.size();
	}

	@Override
	public Object getItem(int position) {
		return mCards.get(position);
	}

	@Override
	public int getPosition(Object item) {
		return mCards.indexOf(item);
	}

	@Override
	public int getViewTypeCount() {
		return Card.getViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		return mCards.get(position).getItemViewType();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return mCards.get(position).getView(convertView, parent);
	}

}

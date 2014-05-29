/*
 * Magic.java
 * @author Cody Engel
 * http://codyengel.info
 * 
 * This is the service which is started from HelloGlass.java, this is where the magic happens.
 */
package com.google.disasterrecovery;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

public class Oops extends Activity {
	private List<Card> mCards;
    private CardScrollView mCardScrollView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		/*
		 * We're creating a card for the interface.
		 * 
		 * More info here: http://developer.android.com/guide/topics/ui/themes.html
		 */
		createCards();

        mCardScrollView = new CardScrollView(this);
        ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
        mCardScrollView.setAdapter(adapter);
        mCardScrollView.activate();
        setContentView(mCardScrollView);
	}
	private void createCards() {
		 mCards = new ArrayList<Card>();
		 Card card;
		 
	        card = new Card(this);
	        card.setText("Oops, the solution you asked is not available now");
	        card.setFootnote("Something went wrong");
	        card.setImageLayout(Card.ImageLayout.LEFT);
	        card.addImage(R.drawable.oops);
	        mCards.add(card);

	}
	private class ExampleCardScrollAdapter extends CardScrollAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mCards.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mCards.get(position);
		}

		@Override
		public int getPosition(Object item) {
			// TODO Auto-generated method stub
			return mCards.indexOf(item);
		}
		@Override
		public int getViewTypeCount() {
            return Card.getViewTypeCount();
        }
		@Override
        public int getItemViewType(int position){
            return mCards.get(position).getItemViewType();
        }
		@Override
        public View getView(int position, View convertView,ViewGroup parent) {
            return  mCards.get(position).getView(convertView, parent);
        }
		
	}

}

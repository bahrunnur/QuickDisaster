package com.google.disasterrecovery;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.widget.CardScrollView;
import com.google.disasterrecovery.nearby.NearbyActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
	private static final String LIVE_CARD_TAG = "Disaster Recovery";

	private LiveCard mLiveCard;
	private RemoteViews mLiveCardView;
	private List<Card> mCards;
	private CardScrollView mCardScrollView;
	private String[] mSolutionStepArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("info", "onCreate");
		int resource = 0;
		ArrayList<String> results = getIntent().getExtras().getStringArrayList(
				RecognizerIntent.EXTRA_RESULTS);
		String spokenText = results.get(0);

		if (spokenText.toString().equals("earthquake")) {
			resource = R.array.earthquake;
			
		} else if (spokenText.toString().equals("flood")) {
			resource = R.array.flood;
			
		} else if (spokenText.toString().equals("tsunami")) {
			resource = R.array.tsunami;
			
		} else if (spokenText.toString().equals("volcanic ash")) {
			resource = R.array.volcano;
			
		} else if (spokenText.toString().equals("volcano")) {
			resource = R.array.volcano;
			
		} else if (spokenText.toString().equals("landslide")) {
			resource = R.array.volcanic_ash;
			
		} else if (spokenText.toString().equals("typhoon")) {
			resource = R.array.typhoon;
			
		} else if (spokenText.toString().equals("tornado")) {
			resource = R.array.tornado;
			
		} else if (spokenText.toString().equals("fire")) {
			resource = R.array.fire;
			
		} else if (spokenText.toString().equals("nearby")) {
			Intent i = new Intent(this, NearbyActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
			
		} else {
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
			Intent i = new Intent(this, Oops.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
		}

		mSolutionStepArray = getResources().getStringArray(resource);
		int solutionLength = mSolutionStepArray.length;

		mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
		createCards(solutionLength, mSolutionStepArray, spokenText);

	}

	private void createCards(int length, String[] resource, String keyword) {
		Log.w("info", "createCards");
		int imageResource = 0;
		String cardTitleText = "";
		mCards = new ArrayList<Card>();
		Card card;

		if (keyword.toString().equals("earthquake")) {
			imageResource = R.drawable.earthquake_bg;
			cardTitleText = "Earthquake solution";
			
		} else if (keyword.toString().equals("flood")) {
			imageResource = R.drawable.flood_bg;
			cardTitleText = "Flood solution";
			
		} else if (keyword.toString().equals("tsunami")) {
			imageResource = R.drawable.tsunami_bg;
			cardTitleText = "Tsunami solution";
			
		} else if (keyword.toString().equals("volcanic ash")) {
			imageResource = R.drawable.volcanic_ash_bg;
			cardTitleText = "Volcanic ash solution";
			
		} else if (keyword.toString().equals("volcano")) {
			imageResource = R.drawable.volcano_bg;
			cardTitleText = "Volcano solution";
			
		} else if (keyword.toString().equals("landslide")) {
			imageResource = R.drawable.landslide_bg;
			cardTitleText = "Landslide solution";
			
		} else if (keyword.toString().equals("fire")) {
			imageResource = R.drawable.fire_bg;
			cardTitleText = "Fire solution";
			
		} else if (keyword.toString().equals("tornado")) {
			imageResource = R.drawable.tornado_bg;
			cardTitleText = "Tornado solution";
			
		} else {
			imageResource = R.drawable.typhoon_bg;
			cardTitleText = "Typhoon solution";
			
		}
		
		// First Card (Title)
		card = new Card(this);
		card.setImageLayout(Card.ImageLayout.LEFT);
		card.addImage(imageResource);
		card.setText(cardTitleText);
		mCards.add(card);
		
		// The rest of the cards
		for (int i = 0; i <= length - 1; i++) {

			card = new Card(this);
			card.setText(resource[i].toString());
			card.setFootnote(keyword.toString() + " recovery");
			card.setImageLayout(Card.ImageLayout.LEFT);

			if (keyword.toString().equals("earthquake")) {
				card.addImage(R.drawable.earthquake);
			} else if (keyword.toString().equals("flood")) {
				card.addImage(R.drawable.flood);
			} else if (keyword.toString().equals("tsunami")) {
				card.addImage(R.drawable.tsunami);
			} else if (keyword.toString().equals("volcanic ash")) {
				card.addImage(R.drawable.volcano);
			} else if (keyword.toString().equals("volcano")) {
				card.addImage(R.drawable.volcano);
			} else if (keyword.toString().equals("landslide")) {
				card.addImage(R.drawable.landslide);
			} else if (keyword.toString().equals("tornado")) {
				card.addImage(R.drawable.tornado);
			} else if (keyword.toString().equals("fire")) {
				card.addImage(R.drawable.fire);
			} else {
				card.addImage(R.drawable.tornado);
			}

			mCards.add(card);
		}

		mCardScrollView = new CardScrollView(this);
		MyCardScrollAdapter adapter = new MyCardScrollAdapter(mCards);
		mCardScrollView.setAdapter(adapter);
		mCardScrollView.activate();
		setContentView(mCardScrollView);
	}

	@Override
	protected void onResume() {
		Log.w("info", "onResume");
		super.onResume();
		ArrayList<String> voiceResults = getIntent().getExtras()
				.getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
	}

	@Override
	protected void onPause() {
		Log.w("info", "onPause");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.w("info", "onStop");
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private static final int SPEECH_REQUEST = 0;

	private void displaySpeechRecognizer() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		startActivityForResult(intent, SPEECH_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
			List<String> results = data
					.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			String spokenText = results.get(0);
			Log.w("spoken", spokenText.toString());
			// Do something with spokenText.
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
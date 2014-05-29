package com.google.disasterrecovery;

import java.util.ArrayList;
import java.util.List;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.LiveCard;
import com.google.android.glass.timeline.LiveCard.PublishMode;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

public class MainActivity extends Activity {
	private static final String LIVE_CARD_TAG="Disaster Recovery";
	
	private LiveCard mLiveCard;
	private RemoteViews mLiveCardView;
	private List<Card> mCards;
    private CardScrollView mCardScrollView;
    private String[] mSolutionStepArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.w("info","onCreate");
		int resource=0;
		ArrayList<String> results = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
		String spokenText = results.get(0);
		
        if(spokenText.toString().equals("earthquake")){
        	
			resource=R.array.earthquake;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
			Log.w("Info", "before createCards");
        	createCards(solutionLength,mSolutionStepArray,spokenText);                
            
        }else if(spokenText.toString().equals("flood")){
        	resource=R.array.flood;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("tsunami")){
        	resource=R.array.tsunami;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("volcanic ash")){
        	resource=R.array.volcano;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("volcano")){
        	resource=R.array.volcano;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("landslide")){
        	resource=R.array.volcanic_ash;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("typhoon")){
        	resource=R.array.typhoon;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("tornado")){
        	resource=R.array.tornado;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else if(spokenText.toString().equals("fire")){
        	resource=R.array.fire;
			mSolutionStepArray=getResources().getStringArray(resource);
			int solutionLength=mSolutionStepArray.length;
			
			mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
        	createCards(solutionLength,mSolutionStepArray,spokenText);
        }else{
        	mLiveCard = new LiveCard(this, LIVE_CARD_TAG);
			Intent i = new Intent(this,Oops.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(i);
        }
	}
	private void createCards(int length,String[] resource, String keyword) {
		// TODO Auto-generated method stub
		Log.w("info","createCards");
		mCards = new ArrayList<Card>();
 		 Card card;
 		 
 		 if(keyword.toString().equals("earthquake")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.earthquake_bg);
 	 		 card.setText("Earthquake solution");
 	 		 mCards.add(card); 
 		 }else if(keyword.toString().equals("flood")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.flood_bg);
 	 		card.setText("Flood solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("tsunami")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.tsunami_bg);
 	 		card.setText("Tsunami solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("volcanic ash")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.volcanic_ash_bg);
 	 		card.setText("Volcanic ash solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("volcano")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.volcano_bg);
 	 		card.setText("Volcano solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("landslide")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.landslide_bg);
 	 		card.setText("Landslide solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("fire")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.fire_bg);
 	 		card.setText("Fire solution");
 	 		 mCards.add(card);
 		 }else if(keyword.toString().equals("tornado")){
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.tornado_bg);
 	 		card.setText("Tornado solution");
 	 		 mCards.add(card);
 		 }else{
 			 card=new Card(this);
 	 		 card.setImageLayout(Card.ImageLayout.LEFT);
 	 		 card.addImage(R.drawable.typhoon_bg);
 	 		card.setText("Typhoon solution");
 	 		 mCards.add(card);
 		 }
 		 
       for(int i=0;i<=length-1;i++){
  		 
  	        card = new Card(this);
  	        card.setText(resource[i].toString());
  	        card.setFootnote(keyword.toString()+" recovery");
  	        card.setImageLayout(Card.ImageLayout.LEFT);
  	        if(keyword.toString().equals("earthquake")){
  	        	card.addImage(R.drawable.earthquake);
  	        }else if(keyword.toString().equals("flood")){
  	        	card.addImage(R.drawable.flood);
  	        }else if(keyword.toString().equals("tsunami")){
  	        	card.addImage(R.drawable.tsunami);
  	        }else if(keyword.toString().equals("volcanic ash")){
  	        	card.addImage(R.drawable.volcano);
  	        }else if(keyword.toString().equals("volcano")){
  	        	card.addImage(R.drawable.volcano);
  	        }else if(keyword.toString().equals("landslide")){
  	        	card.addImage(R.drawable.landslide);
  	        }else if(keyword.toString().equals("tornado")){
  	        	card.addImage(R.drawable.tornado);
  	        }else if(keyword.toString().equals("fire")){
  	        	card.addImage(R.drawable.fire);
  	        }else{
  	        	card.addImage(R.drawable.tornado);
  	        }
  	        mCards.add(card);
       }
       mCardScrollView = new CardScrollView(this);
       ExampleCardScrollAdapter adapter = new ExampleCardScrollAdapter();
       mCardScrollView.setAdapter(adapter);
       mCardScrollView.activate();
       setContentView(mCardScrollView);
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
	@Override
	protected void onResume(){
		Log.w("info","onResume");
		super.onResume();
		ArrayList<String> voiceResults = getIntent().getExtras().getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
	}
	@Override
	protected void onPause(){
		Log.w("info","onPause");
		super.onPause();
	}
	@Override
	protected void onStop(){
		Log.w("info","onStop");
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
	protected void onActivityResult(int requestCode, int resultCode,Intent data) {
	    if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
	        String spokenText = results.get(0);
	        Log.w("spoken", spokenText.toString());
	        // Do something with spokenText.
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}

}
package com.gamemodule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	//private Button tacoyaki;
	//private Button gameAbout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button chatNoir = (Button) findViewById(R.id.chat_noir);
		//tacoyaki = (Button) findViewById(R.id.tacoyaki);
		//gameAbout = (Button) findViewById(R.id.game_about);
		
		chatNoir.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(MainActivity.this, ChatNoirActivity.class));
			}
		});
		
		/*
		tacoyaki.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				startActivity(new Intent(MainActivity.this, TacoyakiActivity.class));
			}
		});
		gameAbout.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				// TODO
			}
		});
		*/

	}// func
		

}// class ends

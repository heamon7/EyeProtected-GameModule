package com.gamemodule;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gamemodule.tacoyaki.Game;
import com.gamemodule.tacoyaki.GameConfig;
import com.gamemodule.tacoyaki.GameView;

public class TacoyakiActivity extends Activity { 

	private static final String TAG = "tacoyaki Activity";

	private GameView mGameView;
	private Game mGame;
	private int gameType;
	private BootstrapButton tacoyaki1;
	private BootstrapButton tacoyaki2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tacoyaki);
		initView();
		gameType = GameConfig.TA_DEFAULT;
		initGame(gameType, GameConfig.NORMAL_MOD);
		
	}

	private void initView() {
		mGameView = (GameView) findViewById(R.id.ta_view);
		tacoyaki1 = (BootstrapButton) findViewById(R.id.tacoyakiBtn1);
		tacoyaki1.setBootstrapButtonEnabled(false);
		tacoyaki2 = (BootstrapButton ) findViewById(R.id.tacoyakiBtn2);
		
		// buttn clicked event
		tacoyaki1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameType = mGame.getGameType();
				if(gameType != GameConfig.TA_DEFAULT){
					Log.d("BButton", "pressed button 1");	
					tacoyaki1.setBootstrapButtonEnabled(false);
					tacoyaki2.setBootstrapButtonEnabled(true);
					mGameView.switchGame(GameConfig.TA_DEFAULT);
				}// if
			}
		});
		
		tacoyaki2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameType = mGame.getGameType();
				if(gameType != GameConfig.TA_PLUS){
					Log.d("BButton", "pressed button 2");	
					tacoyaki2.setBootstrapButtonEnabled(false);
					tacoyaki1.setBootstrapButtonEnabled(true);
					mGameView.switchGame(GameConfig.TA_PLUS);
				}// if
			}// func onclick
		});
	}

	private void initGame(int type, int mod) {
		mGame = new Game(mRefreshHandler, type, mod);
		if(mGame == null){
			Log.d(TAG, "mgame is null");
		}
		mGameView.setGame(mGame);
	}
	
	private Handler mRefreshHandler = new Handler(){
    	public void handleMessage(Message msg) {
    		Log.d(TAG, "msg: what: "+msg.what);
    		switch (msg.what) {
    		case GameConfig.PASS:
    			int mod = mGame.getGameMod();
    			if(mod == GameConfig.HARD_MOD){
    				showWinDialog();
    			}else{
    				mGameView.levelUP();
    			}
    			break;
    		case GameConfig.ADD_STEPS:
    			// TODO
    			break;
    		default:
    			break;
    		}
    	};
    };
    
    private void showWinDialog(){
    	AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setMessage("Congratulations!");
        b.setPositiveButton("Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	gameType = mGame.getGameType();
				if(gameType == GameConfig.TA_PLUS){
					tacoyaki1.setBootstrapButtonEnabled(false);
					tacoyaki2.setBootstrapButtonEnabled(true);
					mGameView.switchGame(GameConfig.TA_DEFAULT);
				} else {
					tacoyaki1.setBootstrapButtonEnabled(true);
					tacoyaki2.setBootstrapButtonEnabled(false);
					mGameView.switchGame(GameConfig.TA_PLUS);
				}
            	
            }
        });
        
        b.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        b.show();
    }

    /*
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
        case R.id.tacoyakiBtn1:
        	if(gameType != mGame.getGameType())
        		mGameView.switchGame(GameConfig.TA_PLUS);
            break;
        case R.id.tacoyakiBtn2:
        	Log.d(TAG,"btn pressed");
        	if(gameType != mGame.getGameType())
        		mGameView.switchGame(GameConfig.TA_DEFAULT);
            break;
        default:
            break;
        }
	}
	*/
    
   
    
}

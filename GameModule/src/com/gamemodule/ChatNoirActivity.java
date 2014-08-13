package com.gamemodule;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gamemodule.chatnoir.Game;
import com.gamemodule.chatnoir.GameConfig;
import com.gamemodule.chatnoir.GameView;
import com.gamemodule.chatnoir.Player;


@SuppressLint("NewApi")
public class ChatNoirActivity extends Activity implements OnClickListener {

	private final static String TAG = "chatNoir activity";
    
    private GameView mGameView;
    private Game mGame;
    private Player me; //user
    
    private Button restart;
    private Button undo;
    private int undoNr = 1;
    
    private TextView steps;
    
    @SuppressLint("HandlerLeak") private Handler mRefreshHandler = new Handler(){

    	public void handleMessage(Message msg) {
    		Log.d(TAG, "refresh action="+ msg.what);
    		switch (msg.what) {
    		case GameConfig.GAME_OVER:
    			if (msg.arg1 == GameConfig.WIN){
    				showResultDialog("WIN! ");
    				me.win();
    			} else if (msg.arg1 == GameConfig.LOSE) {
    				showResultDialog("LOSE...");
    			} 
    			break;
    		case GameConfig.ADD_STEPS:
    			updateSteps();
    			break;
    		default:
    			break;
    		}
    	};
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_noir);
        initView();
        initGame();
    }
    
    private void initView(){
    	mGameView = (GameView) findViewById(R.id.game_view);
        restart = (Button) findViewById(R.id.restart_button);
        undo = (Button) findViewById(R.id.undo_button);
        
        steps = (TextView) findViewById(R.id.steps);
        
        restart.setOnClickListener(this);
        undo.setOnClickListener(this);
    }
    
    private void initGame(){
    	me = new Player("ALSO", 1);
        mGame = new Game(mRefreshHandler, me);
        mGameView.setGame(mGame);
    	
    }
    
    @SuppressLint("NewApi")
	@Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.restart_button:
            mGame.reset();
            mGameView.drawGame();
            updateSteps();
            undoNr = 1;
            undo.setBackground(getResources().getDrawable(R.drawable.bbuton_primary_rounded));
            break;
        case R.id.undo_button:
        	if(undoNr == 1 && rollback()){
        		undoNr = 0;
        		undo.setBackground(getResources().getDrawable(R.drawable.bbuton_default_rounded));
        	}
            break;
        default:
            break;
        }
    }
    

    private void showResultDialog(String message){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setCancelable(false);
        b.setMessage(message);
        b.setPositiveButton("Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mGame.reset();
                mGameView.drawGame();
                undoNr = 1;// !
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
    
    private void updateSteps(){
    	steps.setText(mGame.getSteps());
    }
    
    private boolean rollback(){
    	if(mGame.rollback()){ 
    		mGameView.drawGame();
    		return true;
    	} else {
    		return false;
    	}
    }
    
}
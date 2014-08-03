package com.gamemodule.chatnoir;

import java.util.LinkedList;

import android.util.Log;

public class AiCat {

	private final static String TAG = "AI_CAT";
	private int[][] gameMap;
	private Coordinate lastStep = null;



	public AiCat() {
	}

	public AiCat(int[][] map) {
		this.gameMap = map;
	}

	public void updateGameMap(int[][] map) {
		if (map == null)
			return;
		this.gameMap = map;
	}

	public Coordinate getNextPos() {
		Coordinate cpos = getCatPos();
		if (cpos == null) {
			return null;
		}

		return findPath(cpos);
	}

	

	private Coordinate findPath(Coordinate cpos) {

		if (cpos.x == 0 || cpos.x == gameMap.length - 1 || cpos.y == 0
				|| cpos.y == gameMap[0].length - 1) {
			return new Coordinate(GameConfig.LOSE, GameConfig.LOSE);
		}
		
	    LinkedList<Coordinate> posList = new LinkedList<Coordinate>();
	    boolean[][] visited = new boolean[gameMap.length][gameMap[0].length];
		boolean flag = false;
		
		visited[cpos.x][cpos.y] = true; 
		
		posList.add(cpos);
		while(!posList.isEmpty()){
			Log.d(TAG, "poslist size: "+ posList.size());
			Coordinate curr = posList.removeFirst();
			LinkedList<Coordinate> neighbor = getNeighbor(curr);
			// check neighbors
			for(int i = 0; i < neighbor.size(); i++){
				Coordinate tmp = (Coordinate) neighbor.get(i);
				if(!checkPos(tmp)) continue;
				if(gameMap[tmp.x][tmp.y] == GameConfig.TYPE_OBSTACLE) continue; 
				if(isDest(tmp) && gameMap[tmp.x][tmp.y] == GameConfig.TYPE_EMPTY){
					tmp.setParent(curr);
					return getFirstStep(cpos, tmp);
				}
				
				if(visited[tmp.x][tmp.y] == false && gameMap[tmp.x][tmp.y] == GameConfig.TYPE_EMPTY){
					visited[tmp.x][tmp.y] = true; 
					tmp.setParent(curr);
					posList.add(tmp);
				}
			}
		}// while
		
		if(flag) return lastStep;
		
		Log.d(TAG, "almost catched!");
		////////////////////////////////////////////////////////////////
		LinkedList<Coordinate> neighbor = getNeighbor(cpos);
		// check neighbors
		for(int i = 0; i < neighbor.size(); i++){
			Coordinate tmp = (Coordinate) neighbor.get(i);
			if(gameMap[tmp.x][tmp.y] == GameConfig.TYPE_EMPTY ){
				return tmp;
			}
		}// for
		return new Coordinate(GameConfig.WIN, GameConfig.WIN);
		//////////////////////////////////////////////////////////////
		
	}
	
	private LinkedList<Coordinate> getNeighbor(Coordinate center) {
		LinkedList<Coordinate> neighbor = new LinkedList<Coordinate>();
		int x = center.x;
		int y = center.y;
		int ex = y % 2 == 0 ? -1 : 1;
		
		neighbor.add((new Coordinate(x + ex, y - 1))); 
		neighbor.add((new Coordinate(x + ex, y + 1))); 
		neighbor.add((new Coordinate(x - 1, y))); // left
		neighbor.add((new Coordinate(x + ex, y + 1))); 
		neighbor.add((new Coordinate(x + 1, y))); // right
		neighbor.add(new Coordinate(x + ex, y - 1));
		
		return neighbor;
	}
	
	private Coordinate getFirstStep(Coordinate start, Coordinate last){
		while(last.parent != null){
			if(last.parent.x == start.x && last.parent.y == start.y){
				Log.d(TAG, "nextstep: "+last.x+ " Y:"+last.y);
				return last;
			}
			last = last.parent;
		}
		return last;
	}
	

	private boolean checkPos(Coordinate pos) {
		int x = pos.x;  int y = pos.y;
		if (x < 0 || x >= gameMap.length || y < 0 || y >= gameMap[0].length) {
			return false;
		}
		return true;
	}

	private boolean isDest(Coordinate pos){
		int x = pos.x;  int y = pos.y;
		if(x == 0 || y == 0 || x == gameMap.length-1 ||
				y == gameMap[0].length-1){
			return true;
		}
		return false;
	}
	
	private Coordinate getCatPos() {
		if (gameMap == null) {
			return null;
		}

		for (int i = 0; i < gameMap.length; i++) {
			for (int j = 0; j < gameMap[0].length; j++) {
				if (gameMap[i][j] == GameConfig.TYPE_CAT) {
					gameMap[i][j] = GameConfig.TYPE_EMPTY;
					return new Coordinate(i, j);
				}
			}
		}
		return null;
	}
}



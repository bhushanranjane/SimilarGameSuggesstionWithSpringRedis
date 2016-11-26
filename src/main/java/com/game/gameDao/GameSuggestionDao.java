package com.game.gameDao;

import java.util.List;

import com.game.gameDto.GameInfo;



public interface GameSuggestionDao 
{
	public boolean isExist(String gname);
	
	public List<GameInfo> getGameByName(String gameName);
	
	public List<GameInfo> gameDetails(String gameName);
	
	public void saveGame(GameInfo gameInfo);
	
}

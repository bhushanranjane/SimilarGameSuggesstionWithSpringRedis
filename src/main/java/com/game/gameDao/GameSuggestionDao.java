package com.game.gameDao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.game.gameDto.GameInfo;


@Repository
public interface GameSuggestionDao 
{
	public boolean isExist(String gname);
	
	public List<GameInfo> getGameByName(String gameName);
	
	public List<GameInfo> gameDetails(String gameName);
	
	public void saveGame(GameInfo gameInfo);
	
}

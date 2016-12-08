/*
  File name:RedisInterface.java
  Created by:Bhushan Ranjane
  Purpose:Methods used for Redis Cache connection
*/
package com.game.gameDao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.game.gameDto.SuggestInfo;

@Repository
public interface RedisInterface {
	
	public void redisData(SuggestInfo info);
	
	public void addData(Set<String> record, String baseGameId, String packageId);
	
	public String toJson(SuggestInfo info);
	
	
}

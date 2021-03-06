/*
  File name:RedisImpl.java
  Created by:Bhushan Ranjane
  Purpose:Perform Redis implementation operation
*/
package com.game.gameDao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.game.gameDto.SuggestInfo;
import com.game.gameModel.PlayStoreDataFetching;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;


public class RedisImpl implements RedisInterface{

	Logger logger=Logger.getLogger("REDISIMPL");
	PlayStoreDataFetching playStoreDataFetching = new PlayStoreDataFetching();	
	final static Jedis redisConnect = new Jedis("redis-10179.c11.us-east-1-2.ec2.cloud.redislabs.com",10179);

	static Gson gson = new Gson();

	public void redisData(SuggestInfo info) {
		
		//Stores the Suggested Data in Key Value Pair
		Map<String, String> similarGames = new HashMap<String, String>();
		
		similarGames.put("Gamename", info.getGameName());
		similarGames.put("PackageName", info.getPackageName());
		similarGames.put("GameUrl", info.getGameUrl());
		similarGames.put("imagurl", info.getImageUrl());
		similarGames.put("PackageId", info.getPackageid());
		similarGames.put("Ratings", info.getGameRating());
		similarGames.put("Cost",info.getGameCost());

		// Store Similar Games to the set
		Set<Map<String, String>> suggestedGames = new HashSet<Map<String, String>>();
		suggestedGames.add(similarGames);

		// redis data with base game as key
		String baseGameId = info.getBaseGameId().substring(0, 3);
		
		
		redisConnect.hset("GameId:-" + baseGameId, "Package Id:-" + info.getPackageid(),
				"Game Suggestion:-" + suggestedGames.toString());
	

	
		List<String> jsonData = redisConnect.hmget("Game Key:" + info.getBaseGameId().substring(0, 3),
				info.getBaseGameId());
		/*System.out.println("json List" + jsonData.get(0));*/

		Set<String> recordSet = new HashSet<String>();
		if (jsonData.get(0) != null) {
			recordSet = gson.fromJson(jsonData.get(0), new TypeToken<Set<String>>() {
			}.getType());
			addData(recordSet, info.getBaseGameId(), info.getPackageid());
		} else {
			addData(recordSet, info.getBaseGameId(), info.getPackageid());
		}
		

	}

	//add data into hash set
	public void addData(Set<String> record, String baseGameId, String packageId) {

		record.add(packageId);
		String packageIdString = null;
		packageIdString = gson.toJson(record);
		
		redisConnect.hset("Game Key:" + baseGameId.substring(0, 3), baseGameId, packageIdString);
	}
	
	public String toJson(SuggestInfo info)
	{
		String data=redisConnect.hget("Game Key:" + info.getBaseGameId().substring(0, 3),
				info.getBaseGameId());
		return data;
		
	}

	
}

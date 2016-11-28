package com.game.gameDao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Repository;

import com.game.gameDto.SuggestInfo;
import com.game.gameModel.PlayStoreDataFetching;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import redis.clients.jedis.Jedis;

@Repository
public class RedisImpl{

	PlayStoreDataFetching playStoreDataFetching = new PlayStoreDataFetching();	
	final static Jedis redisConnect = new Jedis("localhost");
	static Gson gson = new Gson();

	public void redisData(SuggestInfo info) {
		
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

	/*	// Storing related game related package id in json
		Set<String> relatedPackage = new HashSet<String>();
		relatedPackage.add(info.getPackageid());*/

		/*String packagecon = gson.toJson(relatedPackage);*/
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
	public static void addData(Set<String> record, String baseGameId, String packageId) {

		record.add(packageId);
	/*	System.out.println("recod:-" + record.toString());*/
		String packageIdString = null;
		packageIdString = gson.toJson(record);
		redisConnect.hset("Game Key:" + baseGameId.substring(0, 3), baseGameId, packageIdString);
	}

}

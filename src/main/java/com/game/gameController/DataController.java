/*
  File name:DataController.java
  Created by:Bhushan Ranjane
  Purpose:Controls the Gaming operations .
*/
package com.game.gameController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.game.gameDao.GameSuggestionDao;
import com.game.gameDao.GameSuggestionDaoImpl;
import com.game.gameDao.RedisImpl;
import com.game.gameDao.RedisInterface;
import com.game.gameDto.GameInfo;
import com.game.gameDto.SuggestInfo;
import com.game.gameModel.PlayStoreDataFetching;
import com.game.gameModel.PlayStoreGameSuggesstion;
import com.game.gameModel.PlayStoreUrlFetching;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
@EnableWebMvc
@RestController
public class DataController {
	Logger logger = Logger.getLogger("CONTROLLER");

	PlayStoreUrlFetching gameUrl = new PlayStoreUrlFetching();
	PlayStoreDataFetching gameData = new PlayStoreDataFetching();
	PlayStoreGameSuggesstion suggestGame = new PlayStoreGameSuggesstion();
	
	@Autowired
	GameSuggestionDao gameDao;
	
	@Autowired
	RedisInterface redisImpl;

	@RequestMapping(value = "homepage", method = RequestMethod.POST)
	public ModelAndView gameDetails(String gameName) {
		/*GameInfo gameInfo = new GameInfo();*/
		System.out.println("Search Here" + gameName);
		if(gameName.equals("")){
			return new ModelAndView("error");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		if (gameDao.isExist(gameName)) {
			System.out.println("Game exist");

			List<GameInfo> game = gameDao.gameDetails(gameName);
			/* suggestion1 = suggestGame.getGameSuggesstion(game.get(0)); */
			List<SuggestInfo> suggestion = suggestGame.getGameSuggesstion(game.get(0));
			model.put("game", game);
			model.put("suggestion", suggestion);
			return new ModelAndView("gameDetails", "model", model);

		} else {
			String url = gameUrl.findUrl(gameName);
			GameInfo gameInfo = gameData.getPlaystoreData(url);
			gameDao.saveGame(gameInfo);
			List<GameInfo> game = gameDao.getGameByName(gameName);
			List<SuggestInfo> suggestion = suggestGame.getGameSuggesstion(game.get(0));
			model.put("game", game);
			model.put("suggestion", suggestion);
			return new ModelAndView("gameDetails", "model", model);
		}
	}
}

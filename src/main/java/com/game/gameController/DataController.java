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

import com.game.gameDao.GameSuggestionDaoImpl;
import com.game.gameDao.RedisImpl;
import com.game.gameDto.GameInfo;
import com.game.gameDto.SuggestInfo;
import com.game.gameModel.PlayStoreDataFetching;
import com.game.gameModel.PlayStoreGameSuggesstion;
import com.game.gameModel.PlayStoreUrlFetching;
@EnableWebMvc
@RestController
public class DataController {
	Logger logger = Logger.getLogger(DataController.class);

	PlayStoreUrlFetching gameUrl = new PlayStoreUrlFetching();
	PlayStoreDataFetching gameData = new PlayStoreDataFetching();
	PlayStoreGameSuggesstion suggestGame = new PlayStoreGameSuggesstion();
	GameInfo gameInfo = new GameInfo();

	SuggestInfo suggestion1 = new SuggestInfo();
	/* ArrayList<String> gamelist=new ArrayList<String>(); */

	@Autowired
	GameSuggestionDaoImpl gameDao;
	RedisImpl redisImpl;

	@RequestMapping(value = "homepage", method = RequestMethod.POST)
	public ModelAndView gameDetails(String gameName) {
		System.out.println("Search Here" + gameName);
		if(gameName.equals("")){
			return new ModelAndView("error");
		}
		Map<String, Object> model = new HashMap<String, Object>();
		if (gameDao.isExist(gameName.toUpperCase())) {
			System.out.println("Game exist");

			List<GameInfo> game = gameDao.gameDetails(gameName);
			/* suggestion1 = suggestGame.getGameSuggesstion(game.get(0)); */
			List<SuggestInfo> suggestion = suggestGame.getGameSuggesstion(game.get(0));
			model.put("game", game);
			model.put("suggestion", suggestion);
			return new ModelAndView("gameDetails", "model", model);

		} else {
			String url = gameUrl.findUrl(gameName);
			gameInfo = gameData.getPlaystoreData(url);
			gameDao.saveGame(gameInfo);
			List<GameInfo> game = gameDao.getGameByName(gameName);
			List<SuggestInfo> suggestion = suggestGame.getGameSuggesstion(game.get(0));
			model.put("game", game);
			model.put("suggestion", suggestion);
			return new ModelAndView("gameDetails", "model", model);
		}
		/*
		 * else {
		 * 
		 * String url = gameUrl.findUrl(gameName); gameInfo =
		 * gameData.getPlaystoreData(url); gameDao.saveGame(gameInfo);
		 * List<GameInfo> game = gameDao.getGameByName(gameName); for (int i =
		 * 0; i < game.size(); i++) { System.out.println("game list:-" +
		 * game.get(i).getGameName()); } SuggestInfo suggestion1 =
		 * suggestGame.getGameSuggesstion(gameInfo); for (SuggestInfo
		 * suggestInfo : suggestion1) { gameDao.saveSuggestion(suggestInfo);
		 * List<SuggestInfo> suggestion = gameDao.gameSuggest();
		 * model.put("game", game); model.put("suggestion", suggestion);
		 * 
		 * } }
		 */

	}
	@RequestMapping(value="/test",method=RequestMethod.POST)
	public String test(@RequestParam("gameName") String gameName){
		System.out.println("game name"+gameName);
		return gameName;
	}

}

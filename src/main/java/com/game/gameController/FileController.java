package com.game.gameController;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.game.gameDto.GameInfo;
import com.game.gameModel.PlayStoreDataFetching;
import com.game.gameModel.PlayStoreGameSuggesstion;
import com.game.gameModel.PlayStoreUrlFetching;

import au.com.bytecode.opencsv.CSVReader;

@RestController
@EnableWebMvc
@RequestMapping("/controller")
public class FileController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public List<String[]> jsonInfo(@RequestParam("files") MultipartFile mpf, HttpServletResponse response) {

		// Create the object of the class
		PlayStoreUrlFetching playStoreUrl = new PlayStoreUrlFetching();
		PlayStoreDataFetching playStoreData = new PlayStoreDataFetching();
		PlayStoreGameSuggesstion gameSuggestion = new PlayStoreGameSuggesstion();
		List<String[]> gameList = new ArrayList<String[]>();
		
		try {
			@SuppressWarnings("resource")
			CSVReader csvReader = new CSVReader(new FileReader("/home/bridgeit/Documents/GameName.csv"), ',', '\'', 1);
			gameList = csvReader.readAll();

			for (String[] gameName : gameList) {
				String url = playStoreUrl.findUrl(gameName[2]);
				GameInfo gameInfo = playStoreData.getPlaystoreData(url);

			}

		} catch (Exception e) {

		}

		return gameList;
	}
}

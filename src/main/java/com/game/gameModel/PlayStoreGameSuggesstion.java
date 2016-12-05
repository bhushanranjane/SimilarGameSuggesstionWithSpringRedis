/*
  File name:PlayStoreGameSuggesstion.java
  Created by:Bhushan Ranjane
  Purpose:Scrapping of Suggested Games data from Google Play Store .
*/
package com.game.gameModel;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.game.gameDao.RedisImpl;
import com.game.gameDto.GameInfo;
import com.game.gameDto.SuggestInfo;

public class PlayStoreGameSuggesstion {

	Logger logger = Logger.getLogger("SUGGESTION");
	PlayStoreUrlFetching playStoreUrl = new PlayStoreUrlFetching();
	RedisImpl redisImpl = new RedisImpl();

	public List<SuggestInfo> getGameSuggesstion(GameInfo gameInfo) {

		String gameUrl;//Suggested game Url
		String packName;//Suggested game package name
		String image;//Suggested image url
		String flag;//Indicates Paid or unpaid game
		String rating;//Suggested game Rating
		String iurl;//Suggested Game image Url
		String packageId;//Suggested game package id

		ArrayList<String> imageinfo = new ArrayList<String>();//Contains Image Info
		ArrayList<String> games = new ArrayList<String>();//Contains Game Name
		List<SuggestInfo> gameSuggestion=new ArrayList<SuggestInfo>(); //List of suggested Game
	
		try {
			//Url For Similar games Page
			String urlpattern = "https://play.google.com/store/apps/similar?id=";
			String similarUrl = urlpattern.concat(gameInfo.getPackageName());
			Document doc = Jsoup.connect(similarUrl).userAgent("Chrome/50.0.2661.94").timeout(10000).get();

			/******************* Scraping of Suggestion game started here*********************/

			// get the titles of the suggested game
			Elements e = doc.select("div.cards.id-card-list");
			Elements t1 = e.select("[class=details]");
			String gameTitle = t1.select("[class=title]").toString();
			String[] gamename = gameTitle.split("title");

			// get the Ratings of the game
			Elements rate = doc.getElementsByClass("reason-set");
			rating = rate.select("[class=reason-set-star-rating]").toString();
			String grating[] = rating.split("editable-container");

			// get the image url for each game
			iurl = doc.getElementsByClass("cover-inner-align").select("[class=cover-image]").toString();
			String jurl[] = iurl.split("data-cover-small");
			for (int k = 0; k < jurl.length; k++) {
				try {
					imageinfo.add(jurl[k].substring(jurl[k].indexOf("src") + 5, jurl[k].indexOf("aria") - 2));
				} catch (Exception e2) {
					logger.info("Some thing went wrong image url not found");
					/* System.out.println("error in image url"); */
				}

			}

			// add the names of the game into array list
			for (int i = 0; i < gamename.length; i++) {
				if (gamename[i].contains("href"))
					continue;
				try {
					// take a sub String of the String to find game name
					games.add(gamename[i].substring(gamename[i].indexOf(">") + 1, gamename[i].indexOf("<") - 1));

				} catch (Exception e1) {

					logger.info("Suggested Game name not found");
					/* System.out.println("error occured"); */
				}
			}

			// Store the Ratings of all games
			ArrayList<String> gameRating = new ArrayList<String>();

			// add the ratings of the game into array list
			for (int x = 1; x < grating.length; x++) {
				try {
					gameRating.add(grating[x].substring(grating[x].indexOf("label") + 7, grating[x].indexOf(">") - 1));
				} catch (Exception e3) {

					logger.info("Rating for the Game not found");
					System.out.println("Error in ratings");
				}
			}

			// Checks wheather the game is paid or free
			Elements status = doc.getElementsByClass("id-track-impression");
			String charge = status.text();
			String cost = null;
			if (charge.startsWith("₹")) {
				cost = charge.substring(charge.indexOf("₹") + 1, charge.indexOf("Buy") - 1);
				System.out.println("cost " + cost);
				flag = "Paid";
			} else {
				flag = "Free";
			}

			// contains all the details of the game
			for (int j = 0; j < games.size(); j++) {
				
				SuggestInfo suggesstion = new SuggestInfo();
				
				String baseGameId = gameInfo.getPackageId();
			
				gameUrl = playStoreUrl.findUrl(games.get(j));
				packName = gameUrl.substring(gameUrl.indexOf("id=") + 3);
				packageId = Integer.toString(packName.hashCode() & Integer.MAX_VALUE);
				image = imageinfo.get(j);
				// Append http to the image url
				if (image.contains("http") == false) {
					image = ("http:").concat(image.trim());

				}

				suggesstion.setGameName(games.get(j));
				suggesstion.setPackageName(packName);
				suggesstion.setGameUrl(gameUrl);
				suggesstion.setImageUrl(image);
				suggesstion.setGameRating(gameRating.get(j));
				suggesstion.setBaseGameId(baseGameId);
				suggesstion.setPackageid(packageId);

				logger.info(suggesstion.getGameName());
				if (!flag.equals("Free")) {
					suggesstion.setGameCost(cost);
				} else
					suggesstion.setGameCost("Free");
				
				redisImpl.redisData(suggesstion);
				gameSuggestion.add(suggesstion);

			}

		} catch (Exception e) {
			

			logger.info("Url Not Found .Enter Proper Game Name");
		}
		
		return gameSuggestion;

	}

}

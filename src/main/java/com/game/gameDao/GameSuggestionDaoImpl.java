/*
  File name:GameSuggestionDaoImpl.java
  Created by:Bhushan Ranjane
  Purpose:Implements DOA for database operations.
*/
package com.game.gameDao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.game.gameDto.GameInfo;

import redis.clients.jedis.Jedis;




public class GameSuggestionDaoImpl implements GameSuggestionDao {
	Logger logger=Logger.getLogger("GAMESUGGESTIONDAOIMPL");
	/*final static Jedis redisConnect = new Jedis("redis-10179.c11.us-east-1-2.ec2.cloud.redislabs.com",10179);*/
	final static Jedis redisConnect = new Jedis("localhost");
	
	@Autowired
	SessionFactory sessionFactory;
	Session session;

	// Check wheather the game name exist or not
	@Transactional
	public boolean isExist(String gname) {
		try{
		session = sessionFactory.openSession();
		Query query = session.createQuery("from GameInfo where gameName= :gameName");
		query.setParameter("gameName", gname);
		GameInfo gameInfo2 = (GameInfo) query.uniqueResult();
		if (gameInfo2 != null) {
			return true;
		} /*else {
			return false;
		}*/
		}catch(Exception e){
			logger.info("Game Already Exist");
		}
		finally {
			session.close();
			
		}
		return false;
	}
	
	

	// get the game details by the game name
	@Transactional
	public List<GameInfo> getGameByName(String gameName) {
		// Session Started
		
		session = sessionFactory.openSession();
		Query query = session.createQuery("from GameInfo where gameName= :gameName");
		query.setParameter("gameName", gameName);
		// GameInfo gameInfo1=(GameInfo) query.uniqueResult();
		List<GameInfo> list = query.list();

		if (list != null) {

			return list;
		}
		return null;

	}

	@Transactional
	public List<GameInfo> gameDetails(String gameName) {
		session = sessionFactory.openSession();

		Query query = session.createQuery("from GameInfo where gameName=?");
		query.setString(0, gameName);
		List<GameInfo> gameInfo = query.list();
		return gameInfo;

	}

	// save the game details fetched from the play store
	@Transactional
	public void saveGame(GameInfo gameInfo) {
		session = sessionFactory.openSession();
		Transaction tr = session.beginTransaction();
		session.save(gameInfo);
		tr.commit();
		session.close();

	}

	/*public List<String> suggestedGames(SuggestInfo info){
		session=sessionFactory.openSession();
		List<String> suggestionList=redisConnect.hmget("GameId:-" + info.getBaseGameId().substring(0,3),"Package Id:-"+info.getPackageid());
		return suggestionList;
	}
	
	public void saveSuggestion(SuggestInfo suggestInfo)
	{
		session=sessionFactory.openSession();
		Transaction  tr=session.beginTransaction();
		try{
			
			session.save(suggestInfo);
			
		}
		catch(Exception e)
		{
			logger.info("NO suggestion found for the game");
		}
		finally {
			session.close();
			tr.commit();
		}
	}*/

}

/*
  File name:GameInfo.java
  Created by:Bhushan Ranjane
  Purpose:Model Class for Game Information.
*/
package com.game.gameDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table
public class GameInfo {
	
	@NotEmpty
	@Column
	private String gameName;

	@Id
	private String packageId;

	@Column
	private String packageName;

	@Column
	private String gameCategory;
	
	@Column
	private String gameURL;

	@Column
	private String paid;
	
	@Column
	private String imageUrl;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getGameCategory() {
		return gameCategory;
	}

	public void setGameCategory(String gameCategory) {
		this.gameCategory = gameCategory;
	}

	public String getGameURL() {
		return gameURL;
	}

	public void setGameURL(String gameURL) {
		this.gameURL = gameURL;
	}

	public String getPaid() {
		return paid;
	}

	public void setPaid(String paid) {
		this.paid = paid;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}

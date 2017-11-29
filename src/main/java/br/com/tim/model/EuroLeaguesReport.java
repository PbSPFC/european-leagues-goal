package br.com.tim.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import br.com.tim.utils.CommonsConstants;
import br.com.tim.utils.StringUtil;

public class EuroLeaguesReport implements Writable{

	private String gameDate;
	private String clubeMoreGoals;
	private Long highestScore;
	private Long totalGoals;
	
	public EuroLeaguesReport() {
		clean();
	}

	public void clean() {
		this.gameDate = CommonsConstants.EMPTY;
		this.clubeMoreGoals = CommonsConstants.EMPTY;
		this.highestScore = 0L;
		this.totalGoals = 0L;
	}

	@Override
	public void readFields(DataInput in) throws IOException {
		this.gameDate = in.readUTF();
		this.clubeMoreGoals = in.readUTF();
		this.highestScore = in.readLong();
		this.totalGoals = in.readLong();
	}

	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(this.gameDate);
		out.writeUTF(this.clubeMoreGoals);
		out.writeLong(this.highestScore);
		out.writeLong(this.totalGoals);
	}

	public void set(EuroLeaguesKey key, TeamMoreGoals team, EuroLeagueslValue value){
		this.gameDate = key.getGameDate();
		this.clubeMoreGoals = team.getTeam();
		this.highestScore = team.getGoals();
		this.totalGoals = value.getHomeGoals() + value.getAwayGoals();
	}

	public String getGameDate() {
		return gameDate;
	}

	public void setGameDate(String gameDate) {
		this.gameDate = gameDate;
	}

	public String getClubeMoreGoals() {
		return clubeMoreGoals;
	}

	public void setClubeMoreGoals(String clubeMoreGoals) {
		this.clubeMoreGoals = clubeMoreGoals;
	}

	public Long getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(Long highestScore) {
		this.highestScore = highestScore;
	}

	public Long getTotalGoals() {
		return totalGoals;
	}

	public void setTotalGoals(Long totalGoals) {
		this.totalGoals = totalGoals;
	}

	@Override
	public String toString() {
		return StringUtil.getDelimitedString(CommonsConstants.FILE_FIELD_SEPARATOR, this.gameDate, this.clubeMoreGoals, this.highestScore.toString(), this.totalGoals.toString());
	}
	
}

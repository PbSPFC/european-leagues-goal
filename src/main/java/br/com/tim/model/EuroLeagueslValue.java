package br.com.tim.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.Writable;

import br.com.tim.entity.BPL;
import br.com.tim.entity.Bundesliga;
import br.com.tim.entity.LaLiga;
import br.com.tim.entity.LeagueOne;
import br.com.tim.entity.SerieATim;
import br.com.tim.utils.CommonsConstants;

public class EuroLeagueslValue implements Writable{

	private String homeTeam;
	private String awayTeam;
	private Long homeGoals;
	private Long awayGoals;
	
	public EuroLeagueslValue() {
		clean();
	}
	
	public void clean() {
		this.homeTeam = CommonsConstants.EMPTY;
		this.awayTeam = CommonsConstants.EMPTY;
		this.homeGoals = 0L;
		this.awayGoals = 0L;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.homeTeam = input.readUTF();
		this.awayTeam = input.readUTF();
		this.homeGoals = input.readLong();
		this.awayGoals = input.readLong();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(this.homeTeam);
		output.writeUTF(this.awayTeam);
		output.writeLong(this.homeGoals);
		output.writeLong(this.awayGoals);
	}

	public void set(BPL value){
		this.homeTeam = value.homeTeam;
		this.awayTeam = value.awayTeam;
		this.homeGoals = Long.parseLong(value.fullTimeHomeTeamGoals);
		this.awayGoals = Long.parseLong(value.fullTimeAwayTeamGoals);
	}
	
	public void set(Bundesliga value){
		this.homeTeam = value.homeTeam;
		this.awayTeam = value.awayTeam;
		this.homeGoals = Long.parseLong(value.fullTimeHomeTeamGoals);
		this.awayGoals = Long.parseLong(value.fullTimeAwayTeamGoals);
	}
	
	public void set(LaLiga value){
		this.homeTeam = value.homeTeam;
		this.awayTeam = value.awayTeam;
		this.homeGoals = Long.parseLong(value.fullTimeHomeTeamGoals);
		this.awayGoals = Long.parseLong(value.fullTimeAwayTeamGoals);
	}
	
	public void set(LeagueOne value){
		this.homeTeam = value.homeTeam;
		this.awayTeam = value.awayTeam;
		this.homeGoals = Long.parseLong(value.fullTimeHomeTeamGoals);
		this.awayGoals = Long.parseLong(value.fullTimeAwayTeamGoals);
	}
	
	public void set(SerieATim value){
		this.homeTeam = value.homeTeam;
		this.awayTeam = value.awayTeam;
		this.homeGoals = Long.parseLong(value.fullTimeHomeTeamGoals);
		this.awayGoals = Long.parseLong(value.fullTimeAwayTeamGoals);
	}
	
	public void clone(EuroLeagueslValue clone){
		this.homeTeam = clone.homeTeam;
		this.awayTeam = clone.awayTeam;
		this.homeGoals = clone.homeGoals;
		this.awayGoals = clone.awayGoals;
	}
	
	public void sum(EuroLeagueslValue value){
		this.homeGoals += value.getHomeGoals();
		this.awayGoals += value.getAwayGoals();
	}
	
	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Long getHomeGoals() {
		return homeGoals;
	}

	public void setHomeGoals(Long homeGoals) {
		this.homeGoals = homeGoals;
	}

	public Long getAwayGoals() {
		return awayGoals;
	}

	public void setAwayGoals(Long awayGoals) {
		this.awayGoals = awayGoals;
	}
	
}

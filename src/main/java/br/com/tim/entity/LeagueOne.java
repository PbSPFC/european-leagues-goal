package br.com.tim.entity;

public class LeagueOne {

	public String leagueDivision;
	public String matchDate;
	public String homeTeam;
	public String awayTeam;
	public String fullTimeHomeTeamGoals;
	public String fullTimeAwayTeamGoals;
	public String fullTimeResult;
	public String halfTimeHomeTeamGoals;
	public String halfTimeAwayTeamGoals;
	public String halfTimeResult;
	public String homeTeamShots;
	public String awayTeamShots;
	public String homeTeamShotsOnTarget;
	public String awayTeamShotsOnTarget;
	public String homeTeamCorners;
	public String awayTeamCorners;
	public String homeTeamFoulsCommitted;
	public String awayTeamFoulsCommitted;
	public String homeTeamYellowCards;
	public String awayTeamYellowCards;
	public String homeTeamRedCards;
	public String awayTeamRedCards;
	
	@Override
	public String toString() {
		return "LeagueOneGames [leagueDivision=" + leagueDivision + ", matchDate=" + matchDate + ", homeTeam="
				+ homeTeam + ", awayTeam=" + awayTeam + ", fullTimeHomeTeamGoals=" + fullTimeHomeTeamGoals
				+ ", fullTimeAwayTeamGoals=" + fullTimeAwayTeamGoals + ", fullTimeResult=" + fullTimeResult
				+ ", halfTimeHomeTeamGoals=" + halfTimeHomeTeamGoals + ", halfTimeAwayTeamGoals="
				+ halfTimeAwayTeamGoals + ", halfTimeResult=" + halfTimeResult + ", homeTeamShots=" + homeTeamShots
				+ ", awayTeamShots=" + awayTeamShots + ", homeTeamShotsOnTarget=" + homeTeamShotsOnTarget
				+ ", awayTeamShotsOnTarget=" + awayTeamShotsOnTarget + ", homeTeamCorners=" + homeTeamCorners
				+ ", awayTeamCorners=" + awayTeamCorners + ", homeTeamFoulsCommitted=" + homeTeamFoulsCommitted
				+ ", awayTeamFoulsCommitted=" + awayTeamFoulsCommitted + ", homeTeamYellowCards=" + homeTeamYellowCards
				+ ", awayTeamYellowCards=" + awayTeamYellowCards + ", homeTeamRedCards=" + homeTeamRedCards
				+ ", awayTeamRedCards=" + awayTeamRedCards + "]";
	}
	
}

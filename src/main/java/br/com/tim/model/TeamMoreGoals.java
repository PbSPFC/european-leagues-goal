package br.com.tim.model;

import br.com.tim.utils.CommonsConstants;

public class TeamMoreGoals {

	private String team;
	private Long goals;
	
	public TeamMoreGoals(){
		clean();
	}

	public void clean() {
		this.team = CommonsConstants.EMPTY;
		this.goals = 0L;
	}

	public void setTeamWithMoreGoals(EuroLeagueslValue value){
		
		if(this.goals < value.getHomeGoals()){
			this.team = value.getHomeTeam();
			this.goals = value.getHomeGoals();
		} else if (this.goals == value.getHomeGoals()){
			this.team = this.team.equals(CommonsConstants.EMPTY) ? value.getHomeTeam() : this.team + ", " + value.getHomeTeam();
			this.goals = value.getHomeGoals();
		}
		
		if (this.goals < value.getAwayGoals()){
			this.team = value.getAwayTeam();
			this.goals = value.getAwayGoals();
		} else if (this.goals == value.getAwayGoals()){
			this.team = this.team.equals(CommonsConstants.EMPTY) ? value.getAwayTeam() : this.team + ", " + value.getAwayTeam();
			this.goals = value.getAwayGoals();
		}
		
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Long getGoals() {
		return goals;
	}

	public void setGoals(Long goals) {
		this.goals = goals;
	}
	
}

package br.com.tim.exception;

@SuppressWarnings("serial")
public class EuroLeagueException extends Exception{

	public EuroLeagueException(String msg){
		super(msg);
	}
	
	public EuroLeagueException(String msg, Throwable cause){
		super(msg, cause);
	}
	
}

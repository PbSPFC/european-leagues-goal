package br.com.tim.model;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

import com.google.common.collect.ComparisonChain;

import br.com.tim.utils.CommonsConstants;

public class EuroLeaguesKey implements WritableComparable<EuroLeaguesKey>{

	private String gameDate;
	
	public EuroLeaguesKey() {
		clean();
	}
	
	public void clean() {
		this.gameDate = CommonsConstants.EMPTY;
	}

	public void set(String gdate){
		this.gameDate = gdate;
	}

	@Override
	public void readFields(DataInput input) throws IOException {
		this.gameDate = input.readUTF();
	}

	@Override
	public void write(DataOutput output) throws IOException {
		output.writeUTF(this.gameDate);
	}

	@Override
	public int compareTo(EuroLeaguesKey o) {
		return ComparisonChain.start()
				.compare(this.gameDate, o.gameDate)
				.result();
	}

	public String getGameDate() {
		return gameDate;
	}

	@Override
	public String toString() {
		return "BundesligaGoalKey [gameDate=" + gameDate + "]";
	}
	
}

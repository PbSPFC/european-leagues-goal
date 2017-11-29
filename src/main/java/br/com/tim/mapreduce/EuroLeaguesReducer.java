package br.com.tim.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.log4j.Logger;

import br.com.tim.model.EuroLeaguesKey;
import br.com.tim.model.EuroLeaguesReport;
import br.com.tim.model.EuroLeagueslValue;
import br.com.tim.model.TeamMoreGoals;

public class EuroLeaguesReducer extends Reducer<EuroLeaguesKey, EuroLeagueslValue, NullWritable, EuroLeaguesReport>{

	private static Logger LOG = Logger.getLogger(EuroLeaguesReducer.class);
	
	private EuroLeagueslValue outValue;
	private EuroLeaguesReport report;
	private TeamMoreGoals teamMore;
	
	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		LOG.debug("Setting up Reducer...");
		
		this.outValue = new EuroLeagueslValue();
		this.report = new EuroLeaguesReport();
		this.teamMore = new TeamMoreGoals();
	}
	
	@Override
	public void reduce(EuroLeaguesKey key, Iterable<EuroLeagueslValue> values, Context context) throws IOException, InterruptedException {
		
		teamMore.clean();
		outValue.clean();
		report.clean();
		
		for(EuroLeagueslValue val: values) {
			outValue.sum(val);
			teamMore.setTeamWithMoreGoals(val);
			
		}

		report.set(key, teamMore, outValue);
		
		context.write(NullWritable.get(), this.report);		
	}
	
	@Override
	public void cleanup(Context context) throws IOException,
			InterruptedException {
		LOG.debug("Cleaning up reducer...");
		super.cleanup(context);
	}
	
}

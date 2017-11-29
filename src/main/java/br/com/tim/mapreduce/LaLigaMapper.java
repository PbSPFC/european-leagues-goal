package br.com.tim.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import br.com.tim.entity.LaLiga;
import br.com.tim.exception.CommonsException;
import br.com.tim.exception.EuroLeagueException;
import br.com.tim.model.EuroLeaguesKey;
import br.com.tim.model.EuroLeagueslValue;
import br.com.tim.model.util.LaLigaUtility;
import br.com.tim.utils.CommonsConstants;
import br.com.tim.utils.EuroLeagueUtils;

public class LaLigaMapper<T> extends Mapper<T, Text, EuroLeaguesKey, EuroLeagueslValue> {
	private static Logger LOG = Logger.getLogger(LaLigaMapper.class);
	
	private EuroLeaguesKey outKey;
	private EuroLeagueslValue outValue;
	
	@Override
	public void setup(Context context) throws IOException, InterruptedException {
		LOG.debug("Setting up start...");
		this.outKey = new EuroLeaguesKey();
		this.outValue = new EuroLeagueslValue();
	}
	
	@Override
	public void map(T key, Text value, Context context)  throws IOException, InterruptedException {
		if(value.toString().isEmpty()) return;
		try{
			outKey.clean();
			outValue.clean();
			
			LaLiga laliga = LaLigaUtility.parseToObject(value.toString().split(CommonsConstants.FILE_SPLIT_REGEX, -1));
			
			if (null == laliga) throw new EuroLeagueException("ERROR: BPL game object is null.");
			
			//Verificando se clube ou goal são null
			if(laliga.homeTeam == null || laliga.fullTimeHomeTeamGoals == null || laliga.awayTeam == null || laliga.fullTimeAwayTeamGoals == null) return;
			if(laliga.homeTeam .isEmpty() || laliga.fullTimeHomeTeamGoals .isEmpty() || laliga.awayTeam.isEmpty() || laliga.fullTimeAwayTeamGoals.isEmpty()) return;
			
			try{
				outKey.set(EuroLeagueUtils.parseData(laliga.matchDate));
				outValue.set(laliga);
				context.write(outKey, outValue);
			}catch (Exception e) {
				return;
			}
			
		}catch (EuroLeagueException | CommonsException e) {
			LOG.error(e.getMessage(), e);
			throw new IOException(e.getMessage(), e);
		}
	}
	
	@Override
	public void cleanup(Context context) throws IOException, InterruptedException {
		LOG.debug("Cleaning up...");
        super.cleanup(context);
	}
	
}

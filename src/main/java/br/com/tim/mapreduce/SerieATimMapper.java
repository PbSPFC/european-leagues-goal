package br.com.tim.mapreduce;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import br.com.tim.entity.SerieATim;
import br.com.tim.exception.EuroLeagueException;
import br.com.tim.exception.CommonsException;
import br.com.tim.model.EuroLeaguesKey;
import br.com.tim.model.EuroLeagueslValue;
import br.com.tim.model.util.SerieATimUtility;
import br.com.tim.utils.CommonsConstants;
import br.com.tim.utils.EuroLeagueUtils;

public class SerieATimMapper<T> extends Mapper<T, Text, EuroLeaguesKey, EuroLeagueslValue> {
	private static Logger LOG = Logger.getLogger(SerieATimMapper.class);
	
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
			
			SerieATim serieATim = SerieATimUtility.parseToObject(value.toString().split(CommonsConstants.FILE_SPLIT_REGEX, -1));
			
			if (null == serieATim) throw new EuroLeagueException("ERROR: BPL game object is null.");
			
			//Verificando se clube ou goal são null
			if(serieATim.homeTeam == null || serieATim.fullTimeHomeTeamGoals == null || serieATim.awayTeam == null || serieATim.fullTimeAwayTeamGoals == null) return;
			if(serieATim.homeTeam .isEmpty() || serieATim.fullTimeHomeTeamGoals .isEmpty() || serieATim.awayTeam.isEmpty() || serieATim.fullTimeAwayTeamGoals.isEmpty()) return;
			
			try{
				outKey.set(EuroLeagueUtils.parseData(serieATim.matchDate));
				outValue.set(serieATim);
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

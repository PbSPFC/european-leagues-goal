package br.com.tim.utils;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import br.com.tim.exception.EuroLeagueException;

public class EuroLeagueUtils {

//	private static final DateTimeFormatter DTF_YYMMDD = DateTimeFormat.forPattern(CommonsConstants.YYMMDD);
//	private static final DateTimeFormatter DTF_YYYYMMDD = DateTimeFormat.forPattern(EuroLeagueConstants.YYYY_MM_DD);

	public static String formatDate(String dateToParse, DateTimeFormatter inputFormat, DateTimeFormatter outputFormat) {
		if (StringUtils.isEmpty(dateToParse) || dateToParse.length() < 6) return CommonsConstants.EMPTY;

		return LocalDate.parse(dateToParse, inputFormat).toString(outputFormat);
	}
	
	public static void configurationFileValidate(Configuration configuration) throws EuroLeagueException {

		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.REPORT_OUTPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.REPORT_OUTPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.REPORT_PROCESSING_PATH)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.REPORT_PROCESSING_PATH + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.DEFAULT_PROC_DATE)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.DEFAULT_PROC_DATE + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.BPL_INPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.BPL_INPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.BUNDESLIGA_INPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.BUNDESLIGA_INPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.LA_LIGA_INPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.LA_LIGA_INPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.LEAGUE_ONE_INPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.LEAGUE_ONE_INPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.SERIE_A_TIM_INPUT)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.SERIE_A_TIM_INPUT + " parameter is not set. Check out the configuration file.");
		
		if ( StringUtils.isEmpty(configuration.get(EuroLeagueConstants.PARTITION_KEY)) )
			throw new EuroLeagueException("ERROR: " + EuroLeagueConstants.PARTITION_KEY + " parameter is not set. Check out the configuration file.");
	}
	
	public static String parseData(String data){
		
		String[] dataSplit = data.split("/");
		
		if(dataSplit[0].length() < 2) dataSplit[0] = "0" + dataSplit[0];
		if(dataSplit[1].length() < 2) dataSplit[1] = "0" + dataSplit[1];
		//if(dataSplit[2].length() == 2) dataSplit[2] = dataSplit[2].substring(2, 4);
		if(dataSplit[2].length() == 2 && Integer.parseInt(dataSplit[2]) > 50){
			dataSplit[2] = "19" + dataSplit[2];
		} else if (dataSplit[2].length() == 2){
			dataSplit[2] = "20" + dataSplit[2];
		}
		
		data = dataSplit[2] + "/" + dataSplit[1] + "/" + dataSplit[0];
		
		return data;
	}
	
}

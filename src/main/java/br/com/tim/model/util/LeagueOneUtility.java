package br.com.tim.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.tim.entity.LeagueOne;
import br.com.tim.exception.CommonsException;

public class LeagueOneUtility {

	public static final Logger LOG = Logger.getLogger(LeagueOneUtility.class);

	public static final Field[] fields = LeagueOne.class.getDeclaredFields();

	public static LeagueOne parseToObject(String[] columns) throws CommonsException {
		try {
			LeagueOne leagueOneGames = new LeagueOne();
			int counter = 0;
			for (Field field : fields) {
				if (counter == columns.length) {
					break;
				}
				if (!Modifier.isPrivate(field.getModifiers())) {
					field.set(leagueOneGames, !StringUtils.isEmpty(columns[counter]) ? columns[counter].trim() : null);
					counter++;
				}
			}
			return leagueOneGames;
		} catch (Exception e) {
			LOG.error("Parse League One Games ERROR: ");
			LOG.error(columns);
			throw new CommonsException(e.getMessage(), e);
		}

	}
	
}

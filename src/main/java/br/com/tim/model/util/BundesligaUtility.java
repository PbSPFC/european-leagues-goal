package br.com.tim.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.tim.entity.Bundesliga;
import br.com.tim.exception.CommonsException;

public class BundesligaUtility {

	public static final Logger LOG = Logger.getLogger(BundesligaUtility.class);

	public static final Field[] fields = Bundesliga.class.getDeclaredFields();

	public static Bundesliga parseToObject(String[] columns) throws CommonsException {
		try {
			Bundesliga bundesligaGames = new Bundesliga();
			int counter = 0;
			for (Field field : fields) {
				if (counter == columns.length) {
					break;
				}
				if (!Modifier.isPrivate(field.getModifiers())) {
					field.set(bundesligaGames, !StringUtils.isEmpty(columns[counter]) ? columns[counter].trim() : null);
					counter++;
				}
			}
			return bundesligaGames;
		} catch (Exception e) {
			LOG.error("Parse Bundesliga Games ERROR: ");
			LOG.error(columns);
			throw new CommonsException(e.getMessage(), e);
		}

	}
	
}

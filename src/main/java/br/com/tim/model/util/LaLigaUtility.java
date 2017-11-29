package br.com.tim.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.tim.entity.LaLiga;
import br.com.tim.exception.CommonsException;

public class LaLigaUtility {

	public static final Logger LOG = Logger.getLogger(LaLigaUtility.class);

	public static final Field[] fields = LaLiga.class.getDeclaredFields();

	public static LaLiga parseToObject(String[] columns) throws CommonsException {
		try {
			LaLiga laLigaGames = new LaLiga();
			int counter = 0;
			for (Field field : fields) {
				if (counter == columns.length) {
					break;
				}
				if (!Modifier.isPrivate(field.getModifiers())) {
					field.set(laLigaGames, !StringUtils.isEmpty(columns[counter]) ? columns[counter].trim() : null);
					counter++;
				}
			}
			return laLigaGames;
		} catch (Exception e) {
			LOG.error("Parse La Liga Games ERROR: ");
			LOG.error(columns);
			throw new CommonsException(e.getMessage(), e);
		}

	}
	
}

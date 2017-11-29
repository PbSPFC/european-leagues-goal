package br.com.tim.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.tim.entity.SerieATim;
import br.com.tim.exception.CommonsException;

public class SerieATimUtility {

	public static final Logger LOG = Logger.getLogger(SerieATimUtility.class);

	public static final Field[] fields = SerieATim.class.getDeclaredFields();

	public static SerieATim parseToObject(String[] columns) throws CommonsException {
		try {
			SerieATim serieATimGames = new SerieATim();
			int counter = 0;
			for (Field field : fields) {
				if (counter == columns.length) {
					break;
				}
				if (!Modifier.isPrivate(field.getModifiers())) {
					field.set(serieATimGames, !StringUtils.isEmpty(columns[counter]) ? columns[counter].trim() : null);
					counter++;
				}
			}
			return serieATimGames;
		} catch (Exception e) {
			LOG.error("Parse Serie A Tim Games ERROR: ");
			LOG.error(columns);
			throw new CommonsException(e.getMessage(), e);
		}

	}
	
}

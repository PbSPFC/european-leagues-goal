package br.com.tim.model.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.tim.entity.BPL;
import br.com.tim.exception.CommonsException;

public class BPLUtility {

	public static final Logger LOG = Logger.getLogger(BPLUtility.class);

	public static final Field[] fields = BPL.class.getDeclaredFields();

	public static BPL parseToObject(String[] columns) throws CommonsException {
		try {
			BPL bplGames = new BPL();
			int counter = 0;
			for (Field field : fields) {
				if (counter == columns.length) {
					break;
				}
				if (!Modifier.isPrivate(field.getModifiers())) {
					field.set(bplGames, !StringUtils.isEmpty(columns[counter]) ? columns[counter].trim() : null);
					counter++;
				}
			}
			return bplGames;
		} catch (Exception e) {
			LOG.error("Parse Barclay Premier League Games ERROR: ");
			LOG.error(columns);
			throw new CommonsException(e.getMessage(), e);
		}

	}
	
}

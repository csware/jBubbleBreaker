/*
 * Copyright 2008 - 2010 Sven Strickroth <email@cs-ware.de>
 * 
 * This file is part of jBubbleBreaker.
 * 
 * jBubbleBreaker is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as
 * published by the Free Software Foundation.
 * 
 * jBubbleBreaker is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jBubbleBreaker. If not, see <http://www.gnu.org/licenses/>.
 */
package org.jbubblebreaker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractButton;

import org.xnap.commons.i18n.I18n;
import org.xnap.commons.i18n.I18nFactory;

/**
 * Localization class
 * @author Sven Strickroth
 */
public class Localization {
	/**
	 * Stores the ResourceBundle
	 */
	final private static I18n i18n = I18nFactory.getI18n(Localization.class, "org.jbubblebreaker.i18n.Messages");

	/**
	 * Sets the text and the menmoric of an AbstractButton.
	 * It uses the Windows convention: &Game -> G will become the memnoric
	 * @param button
	 * @param translation
	 */
	public static void setMemnoricText(AbstractButton button, String translation) {
		button.setText(translation.replaceAll("&([A-Za-z0-9])", "$1"));
		Matcher m = Pattern.compile(".*&([A-Za-z0-9]).*").matcher(translation);
		if (m.matches()) {
			button.setMnemonic(m.group(1).charAt(0));
		}
	}

	/**
	 * Returns the I18n-object
	 * @return i18n
	 */
	public static I18n getI18n() {
		return i18n;
	}
}

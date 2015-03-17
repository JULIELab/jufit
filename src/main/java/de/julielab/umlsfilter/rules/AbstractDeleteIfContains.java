/**
 * This is JUFIT, the Jena UMLS Filter
 * Copyright (C) 2015 JULIE LAB
 * Authors: Johannes Hellrich and Sven Buechel
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.julielab.umlsfilter.rules;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * Takes a pattern using constructor. method 'apply'
 *
 *
 */
abstract class AbstractDeleteIfContains extends Rule {

	protected Matcher matcher;

	protected AbstractDeleteIfContains(final String ruleName) {
		super(ruleName);
	}

	@Override
	public ArrayList<TermWithSource> applyOnOneTerm(final TermWithSource tws) {
		if (matcher.reset(tws.getTerm()).find()) {
			tws.supress();
			tws.addModifyingRule(ruleName);
		}
		return null;
	}
}

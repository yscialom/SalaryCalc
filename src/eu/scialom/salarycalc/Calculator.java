/*
 * SalaryCalc: android app to help users convert salary from/to many formats.
 * Copyright (C) 2014 Yankel Scialom.
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * 
 * \author Yankel Scialom (YSC) <yankel.scialom@mail.com>
 * \file ./src/eu/scialom/salarycalc/Calculator.java
 */
package eu.scialom.salarycalc;

import android.content.Context;
import android.content.SharedPreferences;

public class Calculator {
	public class Settings {
		public float taxRate = 1f - 0.22f;
		public int hourPerWeek = 35;
		public int monthsPerYear = 12;
		private final float weeksPerMonth = 4.348f;

		private final static String spName = "eu.scialom.salarycalc.settings";

		public void load(Context context) {
			final SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
			this.taxRate = sp.getFloat("taxRate", this.taxRate);
			this.hourPerWeek = sp.getInt("hourPerWeek", this.hourPerWeek);
			this.monthsPerYear = sp.getInt("monthsPerYear", this.monthsPerYear);
		}

		public void save(Context context) {
			final SharedPreferences sp = context.getSharedPreferences(spName, Context.MODE_PRIVATE);
			final SharedPreferences.Editor edit = sp.edit();
			edit.putFloat("taxRate", this.taxRate);
			edit.putInt("hourPerWeek", this.hourPerWeek);
			edit.putInt("monthsPerYear", this.monthsPerYear);
			edit.commit();
		}
	}

	private Settings settings = new Settings();
	private int AnnualBT = 0;

	public int getAnnualAT() {
		return (int) (this.AnnualBT * this.settings.taxRate);
	}

	public int getAnnualBT() {
		return this.AnnualBT;
	}

	/* Getters */

	public float getHourlyAT() {
		return this.getMonthlyAT() / (this.settings.weeksPerMonth * this.settings.hourPerWeek);
	}

	public void getHourlyAT(float value) {
		this.setMonthlyAT((int) (value * this.settings.weeksPerMonth * this.settings.hourPerWeek));
	}

	public float getHourlyBT() {
		return this.getMonthlyBT() / (this.settings.weeksPerMonth * this.settings.hourPerWeek);
	}

	public int getMonthlyAT() {
		return this.getAnnualAT() / this.settings.monthsPerYear;
	}

	public int getMonthlyBT() {
		return this.AnnualBT / this.settings.monthsPerYear;
	}

	public Settings getSettings() {
		return this.settings;
	}

	public void setAnnualAT(int value) {
		this.AnnualBT = (int) (value / this.settings.taxRate);
	}

	/* Setters */

	public void setAnnualBT(int value) {
		this.AnnualBT = value;
	}

	public void setHourlyAT(float value) {
		this.setMonthlyAT((int) (value * this.settings.weeksPerMonth * this.settings.hourPerWeek));
	}

	public void setHourlyBT(float value) {
		this.setMonthlyBT((int) (value * this.settings.weeksPerMonth * this.settings.hourPerWeek));
	}

	public void setMonthlyAT(int value) {
		this.setAnnualAT(value * this.settings.monthsPerYear);
	}

	public void setMonthlyBT(int value) {
		this.AnnualBT = value * this.settings.monthsPerYear;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}
}

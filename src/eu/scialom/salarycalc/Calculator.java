package eu.scialom.salarycalc;

public class Calculator {
	public class Settings {
		public float taxRate = 1f - 0.22f;
		public int hourPerWeek = 35;
		public int monthsPerYear = 12;
		private final float weeksPerMonth = 4.348f;
	}

	private Settings settings;
	private int AnnualBT;

	/* Getters */

	public int getAnnualAT() {
		return (int) (this.AnnualBT * this.settings.taxRate);
	}

	public int getAnnualBT() {
		return this.AnnualBT;
	}

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
		return this.getAnnualAT() * this.settings.monthsPerYear;
	}

	public int getMonthlyBT() {
		return this.AnnualBT * this.settings.monthsPerYear;
	}

	/* Setters */

	public void setAnnualAT(int value) {
		this.AnnualBT = (int) (value / this.settings.taxRate);
	}

	public void setAnnualBT(int value) {
		this.AnnualBT = value;
	}

	public void setHourlyBT(float value) {
		this.setMonthlyBT((int) (value * this.settings.weeksPerMonth * this.settings.hourPerWeek));
	}

	public void setMonthlyAT(int value) {
		this.setAnnualAT(value / this.settings.monthsPerYear);
	}

	public void setMonthlyBT(int value) {
		this.AnnualBT = value / this.settings.monthsPerYear;
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coursework;


public class Date {
    private int day;
    private int month;
    private int year;
    
    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    public void setDay(int day) {
        this.day = day;
    }
    
    public void setMonth(int month) {
        this.month = month;
    }
    
    public void setYear(int year) {
        this.year = year;
    }
    
    public int getDay() {
        return this.day;
    }
    
    public int getMonth() {
        return this.month;
    }
    
    public int getYear() {
        return this.year;
    }
    
    @Override
    public String toString() {
        return "Date [ day = " + this.day + ", month = " + this.month + ", year = " + this.year + "]";
    }
    
    public String getDate() {
	String dayNum = Integer.toString(this.day);
	String monthNum = Integer.toString(this.month);
        if (this.day <= 9) {
            dayNum = "0" + this.day;
        }
	if (this.month <= 9) {
	    monthNum = "0" + this.month;
	}
        return dayNum + "/" + monthNum + "/" + this.year;
    }
    
    /* 
    checks if the date object is within the range of two other dates
    */
    public boolean withinRange(Date start, Date end) {
	if (this.getDate().equals(start.getDate()) || this.greaterThan(start)) {
	    if (this.getDate().equals(end.getDate()) || this.lessThan(end)) {
		return true;
	    }
	}
	return false;
    }
    
    /* 
    checks if the date object is greater than another date
    */
    public boolean greaterThan(Date comparison) {
	if (year > comparison.getYear()) {
	    return true;
	} else if (year == comparison.getYear() && month > comparison.getMonth()) {
	    return true;
	} else if (year == comparison.getYear() && month == comparison.getMonth() && day > comparison.getDay()) {
	    return true;
	}
	return false;
    }
    
    /* 
    checks if the date object is less than another date
    */
    public boolean lessThan(Date comparison) {
	if (year < comparison.getYear()) {
	    return true;
	} else if (year == comparison.getYear() && month < comparison.getMonth()) {
	    return true;
	} else if (year == comparison.getYear() && month == comparison.getMonth() && day < comparison.getDay()) {
	    return true;
	}
	return false;
    }
    
    /* 
    checks if two pairs of dates overlap
    */
    public static boolean overlap(Date start1, Date end1, Date start2, Date end2) {
	if (start2.withinRange(start1, end1) || end2.withinRange(start1, end1)) {
	    return true;
	} else if (start2.lessThan(start1) && end2.greaterThan(end1)) {
	    return true;
	}
	return false;
    }
    
    /* 
    checks if a day, month, and year could be a valid date
    */
    public static boolean couldBeValid (int day, int month, int year) {
	if (day < 1 || day > 31) {
	    return false;
	} else if (month < 1 || month > 12) {
	    return false;
	} else if (year < 2019 || year > 2050) {
	    return false;
	}
	return true;
    }
}

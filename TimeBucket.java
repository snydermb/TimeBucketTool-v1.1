package MRBS.VED;

import java.util.ArrayList;
import java.util.Calendar;

class TimeBucket {
    private Calendar startDate;
    private Calendar endDate;
    private ArrayList<Double> vitals;
    private double max = 0.0d;
    private double min = 0.0d;

    TimeBucket(Calendar start, Calendar end) {
        if (start == null) {
            startDate = null;
        } else {
            startDate = (Calendar)start.clone();
        }
        if (end == null) {
            endDate = null;
        } else {
            endDate = (Calendar)end.clone();
        }
        vitals = new ArrayList<>();
    }
    boolean checkValid(String date) {
        Calendar tmp = TimeBucketTool.stringToCal(date);
        if (startDate == null) {
            return tmp.before(endDate);
        } else if (endDate == null) {
            return tmp.after(startDate);
        } else {
            return (tmp.after(startDate) && tmp.before(endDate));
        }
    }
    void add(String vital) {
        double value = Double.parseDouble(vital);
        if (vitals.size() == 0) {
            min = max = value;
        } else {
            max = Math.max(max, value);
            min = Math.min(min, value);
        }
        vitals.add(value);
    }
    int getN() {
        return vitals.size();
    }
    double getMax() {
        return max;
    }
    double getMin() {
        return min;
    }
    double getMean() {
        double sum = 0.0d;
        if (vitals.size() < 1) {
            return sum;
        } else {
            for (double value : vitals) {
                sum += value;
            }
            return sum/vitals.size();
        }
    }
}
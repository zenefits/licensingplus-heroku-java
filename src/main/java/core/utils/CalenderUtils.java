package core.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by vthiruvengadam on 8/9/16.
 */
public class CalenderUtils {

    public static final Map<String, String> STATE_MAP;
    static {
        STATE_MAP = new HashMap<String, String>();
        STATE_MAP.put("AL", "Alabama");
        STATE_MAP.put("AK", "Alaska");
        STATE_MAP.put("AB", "Alberta");
        STATE_MAP.put("AZ", "Arizona");
        STATE_MAP.put("AR", "Arkansas");
        STATE_MAP.put("BC", "British Columbia");
        STATE_MAP.put("CA", "California");
        STATE_MAP.put("CO", "Colorado");
        STATE_MAP.put("CT", "Connecticut");
        STATE_MAP.put("DE", "Delaware");
        STATE_MAP.put("DC", "District Of Columbia");
        STATE_MAP.put("FL", "Florida");
        STATE_MAP.put("GA", "Georgia");
        STATE_MAP.put("GU", "Guam");
        STATE_MAP.put("HI", "Hawaii");
        STATE_MAP.put("ID", "Idaho");
        STATE_MAP.put("IL", "Illinois");
        STATE_MAP.put("IN", "Indiana");
        STATE_MAP.put("IA", "Iowa");
        STATE_MAP.put("KS", "Kansas");
        STATE_MAP.put("KY", "Kentucky");
        STATE_MAP.put("LA", "Louisiana");
        STATE_MAP.put("ME", "Maine");
        STATE_MAP.put("MB", "Manitoba");
        STATE_MAP.put("MD", "Maryland");
        STATE_MAP.put("MA", "Massachusetts");
        STATE_MAP.put("MI", "Michigan");
        STATE_MAP.put("MN", "Minnesota");
        STATE_MAP.put("MS", "Mississippi");
        STATE_MAP.put("MO", "Missouri");
        STATE_MAP.put("MT", "Montana");
        STATE_MAP.put("NE", "Nebraska");
        STATE_MAP.put("NV", "Nevada");
        STATE_MAP.put("NB", "New Brunswick");
        STATE_MAP.put("NH", "New Hampshire");
        STATE_MAP.put("NJ", "New Jersey");
        STATE_MAP.put("NM", "New Mexico");
        STATE_MAP.put("NY", "New York");
        STATE_MAP.put("NF", "Newfoundland");
        STATE_MAP.put("NC", "North Carolina");
        STATE_MAP.put("ND", "North Dakota");
        STATE_MAP.put("NT", "Northwest Territories");
        STATE_MAP.put("NS", "Nova Scotia");
        STATE_MAP.put("NU", "Nunavut");
        STATE_MAP.put("OH", "Ohio");
        STATE_MAP.put("OK", "Oklahoma");
        STATE_MAP.put("ON", "Ontario");
        STATE_MAP.put("OR", "Oregon");
        STATE_MAP.put("PA", "Pennsylvania");
        STATE_MAP.put("PE", "Prince Edward Island");
        STATE_MAP.put("PR", "Puerto Rico");
        STATE_MAP.put("QC", "Quebec");
        STATE_MAP.put("RI", "Rhode Island");
        STATE_MAP.put("SK", "Saskatchewan");
        STATE_MAP.put("SC", "South Carolina");
        STATE_MAP.put("SD", "South Dakota");
        STATE_MAP.put("TN", "Tennessee");
        STATE_MAP.put("TX", "Texas");
        STATE_MAP.put("UT", "Utah");
        STATE_MAP.put("VT", "Vermont");
        STATE_MAP.put("VI", "Virgin Islands");
        STATE_MAP.put("VA", "Virginia");
        STATE_MAP.put("WA", "Washington");
        STATE_MAP.put("WV", "West Virginia");
        STATE_MAP.put("WI", "Wisconsin");
        STATE_MAP.put("WY", "Wyoming");
        STATE_MAP.put("YT", "Yukon Territory");
    }

    public static void ValidateDate(String aInDate) {
        DateTimeFormatter formatter =
                DateTimeFormat.forPattern("yyyy-MM-dd").withOffsetParsed();
        // This should throw for invalid dates.
        formatter.parseDateTime(aInDate);
    }

    public static boolean isCalenderDaySame(GregorianCalendar aInCal1, GregorianCalendar aInCal2) {
        if((aInCal1 == null)
                && (aInCal2 != null)) {
            return false;
        }

        if((aInCal2 == null)
                && (aInCal1 != null)) {
            return false;
        }

        if((aInCal2 == null)
                && (aInCal1 == null)) {
            return true;
        }

        if(aInCal1.get(Calendar.DAY_OF_MONTH) != aInCal2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }

        if(aInCal1.get(Calendar.MONTH) != aInCal2.get(Calendar.MONTH)) {
            return false;
        }

        if(aInCal1.get(Calendar.YEAR) != aInCal2.get(Calendar.YEAR)) {
            return false;
        }

        return true;
    }

    public static boolean IsWeekEnd(GregorianCalendar aInCal) {

        String lDayName = aInCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());

        if(Objects.equals(lDayName, new String("Sun"))
                || (Objects.equals(lDayName, new String("Sat")))) {
            return true;
        }
        return false;
    }

    public static String toSFDCDateFormat(XMLGregorianCalendar aInCal) {
        if(aInCal == null) {
            return "";
        }
        return aInCal.getYear() + "-" + aInCal.getMonth() + "-" + aInCal.getDay();
    }

    public static boolean isNullOrWhiteSpace(String a) {
        return a == null || (a.length() <= 0) || (a.length() > 0 && a.trim().length() <= 0);
    }

    public static String getFormattedDate(GregorianCalendar aInCal) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String formatted = format1.format(aInCal.getTime());
        return formatted;
    }

    public static GregorianCalendar getCalenderTimeFromString(String aInData) {
        DateTimeFormatter formatter =
                DateTimeFormat.forPattern("yyyy-MM-dd").withOffsetParsed();
        DateTime dateTime = formatter.parseDateTime(aInData);
        GregorianCalendar lCal = dateTime.toGregorianCalendar();
        return lCal;
    }

    public static String getSFDCResponseDateFormat(String aInSFDCDate) {
        GregorianCalendar lCal = getCalenderTimeFromString(aInSFDCDate);
        int lMonth = lCal.get(Calendar.MONTH) + 1;
        return lMonth + "/" + lCal.get(Calendar.DAY_OF_MONTH) + "/" + lCal.get(Calendar.YEAR);
    }

    public static String getDateInXmlDateFormat(String aInDate) {
        String lRetVal = null;
        try {
            GregorianCalendar lCal = CalenderUtils.getCalenderTimeFromString(aInDate);
            XMLGregorianCalendar lXmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(lCal);
            lRetVal = toSFDCDateFormat(lXmlDate);
        }
        catch (Exception ex) {
            System.out.println("Failed to convert date to xml date format " + aInDate);
        }

        return lRetVal;
    }

    public static Map<String, GregorianCalendar> getDatesFromRange(String aInStartDate, String aInEndDate)  throws Exception {

        GregorianCalendar lStartCalDate = getCalenderTimeFromString(aInStartDate);
        GregorianCalendar lEndCalDate = getCalenderTimeFromString(aInEndDate);

        if(lEndCalDate.compareTo(lStartCalDate) < 0) {
            throw new InvalidParameterException("End Date: " + aInEndDate + " is before the start date: " + aInEndDate);
        }

        GregorianCalendar lCurrentDate = lStartCalDate;
        Map<String, GregorianCalendar> lDates = new HashMap<String, GregorianCalendar>();
        int lAddedDates = 0;

        while(true) {
            if(IsWeekEnd(lCurrentDate)) {
                lCurrentDate.add(GregorianCalendar.DATE, 1);
                continue;
            }

            if(lAddedDates > 14) {
                String lMsg = "Difference between dates " + aInStartDate + " end date " + aInEndDate + " cannot be more than 14 days";
                System.out.println(lMsg);
                throw new InvalidParameterException(lMsg);
            }

            if(lCurrentDate.compareTo(lEndCalDate) <= 0) {
                lDates.put(getFormattedDate(lCurrentDate), (GregorianCalendar)lCurrentDate.clone());
                lCurrentDate.add(GregorianCalendar.DATE, 1);
                lAddedDates++;
            }
            else {
                break;
            }
        }

        return lDates;
    }

    public static String getSdfcStateName(String aInCode) {
        if(STATE_MAP.containsKey(aInCode)) {
            return STATE_MAP.get(aInCode);
        }

        return aInCode;
    }

    public static HashMap<String, GregorianCalendar> getLastNDays(int aInN) {

        HashMap<String, GregorianCalendar> lDays = new HashMap<String, GregorianCalendar>();
        GregorianCalendar lCal = (GregorianCalendar) GregorianCalendar.getInstance();
        while(aInN > 0) {

            // Skip Holidays and weekends
            updateCalenderDate(lCal);
            String lFormattedDate = getFormattedDate(lCal);
            lDays.put(lFormattedDate, lCal);

            aInN--;
            lCal = (GregorianCalendar)lCal.clone();
            lCal.add(GregorianCalendar.DATE, -1);
        }
        return lDays;
    }

    public static void updateCalenderDate(GregorianCalendar aInCal) {
        String lDayName = aInCal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
        if(Objects.equals(lDayName, new String("Sun"))){
            aInCal.add(GregorianCalendar.DATE, -2);
        }
        else if(Objects.equals(lDayName, new String("Sat"))) {
            aInCal.add(GregorianCalendar.DATE, -1);
        }
    }
}

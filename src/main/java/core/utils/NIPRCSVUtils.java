package core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;
import core.nipr.LicenseInternal;
import core.nipr.LineOfAuthorityInternal;

public class NIPRCSVUtils {
	
	public static boolean writeNIPRAlerts(String csvFile, Map<String, LicenseInternal> licenses) {
		CSVWriter writer = null;		
		try {
			if(new File(csvFile).isFile()) {
	            System.out.println("The csv file " + csvFile + " already exists");
	            return true;
	        }
			
			writer = new CSVWriter(new FileWriter(csvFile));
			String[] headers = {"NpnNumber", "State", "LicenseNumber",
	                "EffectiveDate", "ExpirationDate", "ClassName",
	                "IsResidentLicense", "IsActive", "LOAName", "IsLOAActive"};
			writer.writeNext(headers);
			for(LicenseInternal license : licenses.values()) {
	            if(license.linesOfAuthority!=null && !license.linesOfAuthority.isEmpty()) {
	                for(LineOfAuthorityInternal loa :license.linesOfAuthority) {
	                    String[] lines = {
	                            CalenderUtils.isNullOrWhiteSpace(license.npnNumber) ? "":license.npnNumber,
	                            CalenderUtils.isNullOrWhiteSpace(license.state) ? "":license.state,
	                            CalenderUtils.isNullOrWhiteSpace(license.licenseNumber) ? "":license.licenseNumber,
	                            CalenderUtils.isNullOrWhiteSpace(license.effectiveDate) ? "":license.effectiveDate,
	                            CalenderUtils.isNullOrWhiteSpace(license.expirationDate) ? "":license.expirationDate,
	                            CalenderUtils.isNullOrWhiteSpace(license.className) ? "":license.className,
	                            license.isResidentLicense.toString(),
	                            license.isActive.toString(),
	                            CalenderUtils.isNullOrWhiteSpace(loa.name) ? "":loa.name,
	                            loa.isActive.toString()};

	                    writer.writeNext(lines);
	                }
	                
	            } else {
	                String[] lines = {
	                            CalenderUtils.isNullOrWhiteSpace(license.npnNumber) ? "":license.npnNumber,
	                            CalenderUtils.isNullOrWhiteSpace(license.state) ? "":license.state,
	                            CalenderUtils.isNullOrWhiteSpace(license.licenseNumber) ? "":license.licenseNumber,
	                            CalenderUtils.isNullOrWhiteSpace(license.effectiveDate) ? "":license.effectiveDate,
	                            CalenderUtils.isNullOrWhiteSpace(license.expirationDate) ? "":license.expirationDate,
	                            CalenderUtils.isNullOrWhiteSpace(license.className) ? "":license.className,
	                            license.isResidentLicense.toString(),
	                            license.isActive.toString(),
	                            "", ""};

	                writer.writeNext(lines);
	            }
	        }
			return true;
			
		} catch (Exception e) {
			System.out.println("[CSVUtils] - writeNIPRAlerts - failed to create CSV for " + csvFile + " due to exception " + e);
			
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("[CSVUtils] - writeNIPRAlerts - unable to close csv writer");
			}
		}	
		
		return false;		
	}
	
	public static boolean writeFile(String aInFilePath, String aInContent) {
        boolean lStatus = false;
        try {
            FileWriter lWriter = new FileWriter(aInFilePath);
            lWriter.append(aInContent);
            lWriter.flush();
            lWriter.close();
            lStatus = true;
        }
        catch (Exception ex) {
            System.out.println("Failed to write file " + aInFilePath + " " + ex.getMessage());
        }
        return lStatus;
    }
	
	public static void main(String[] args) {
		LicenseInternal li = new LicenseInternal();
		li.className = "NON RESIDENT PRODUCER";
		li.effectiveDate = "2016-8-24";
		li.expirationDate = "2019-4-30";
		li.isResidentLicense = false;
		li.isActive = true;
		li.licenseNumber = "002521388";
		li.state = "HI";
		li.npnNumber = "17359172";
		li.niprUpdateDate = "2016-08-21";
		LineOfAuthorityInternal loa1 = new LineOfAuthorityInternal();
		loa1.isActive = true;
		loa1.name = "Life";
		
		LineOfAuthorityInternal loa = new LineOfAuthorityInternal();
		loa.isActive = true;
		loa.name = "DISABILITY (HEALTH),Test1234";
		
		li.linesOfAuthority.add(loa);
		li.linesOfAuthority.add(loa1);
		
		LicenseInternal li1 = new LicenseInternal();
		li1.className = "NON RESIDENT PRODUCER";
		li1.effectiveDate = "2016-8-24";
		li1.expirationDate = "2019-4-30";
		li1.isResidentLicense = false;
		li1.isActive = true;
		li1.licenseNumber = "002521388";
		li1.state = "HI";
		li1.npnNumber = "1735";
		li1.niprUpdateDate = "2016-08-22";
		
		Map<String, LicenseInternal> licenses = new HashMap<String, LicenseInternal>();
		licenses.put("1", li);
		licenses.put("2", li1);
		
		System.out.println(NIPRCSVUtils.writeNIPRAlerts("test.csv", licenses));
	}
}

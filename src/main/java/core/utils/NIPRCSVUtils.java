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
}

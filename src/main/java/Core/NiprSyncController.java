package Core;

import Core.Nipr.LicenseInternal;
import Core.Nipr.NiprSyncStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
public class NiprSyncController {

    @RequestMapping("/")
    public String index() {
        // TODO: Load the HTML FILE and return
        return "Welcome to LicensePlus Nipr Sync Portal";
    }

    @RequestMapping("/getFailedLicenses")
    public List<LicenseInternal> getFailedLicenses(@RequestParam(value="date", defaultValue="") String date) {

        return LicenseDB.getFailedLicensesByDate(date);
    }

    @RequestMapping("/getStatus")
    public Map<String, NiprSyncStatus> getStatus() {
        return LicenseDB.getCurrentStatus();
    }

    @RequestMapping("/getPendingNiprSyncDates")
    public List<String> getPendingNiprSyncDates() {
        Map<String, GregorianCalendar> lNiprSyncDates = LicenseDB.getPendingNiprSyncDates();
        return new ArrayList<String>(lNiprSyncDates.keySet());
    }

    @RequestMapping(value = "/addNiprSyncDate/{date}", method = RequestMethod.POST)
    public void addNiprSyncDate(@PathVariable String date) {
        LicenseDB.addNiprSyncDate(date);
    }

    @RequestMapping(value = "/addNiprSyncDateRange", method = RequestMethod.POST)
    public static void addNiprSyncDateRange (
            @RequestParam(value="startDate", defaultValue="") String startDate,
            @RequestParam(value="endDate", defaultValue="") String endDate) throws Exception {

            LicenseDB.addNiprSyncDateRange(startDate, endDate);
    }

    @RequestMapping(value = "/removeNiprSyncDate/{date}", method = RequestMethod.POST)
    public void removeNiprSyncDate(@PathVariable String date) {
        LicenseDB.removeNiprSyncDate(date);
    }

    @RequestMapping(value = "/triggerResync", method = RequestMethod.POST)
    public void triggerResync() {
        LicenseDB.triggerResync();
    }

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }
}
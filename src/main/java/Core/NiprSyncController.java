package Core;

import Core.Nipr.LicenseInternal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

@RestController
public class NiprSyncController {

    @RequestMapping("/")
    public String index() {
        // TODO: Load the HTML FILE and return
        return "Welcome to LicensePlus Nipr Sync Portal";
    }

    @RequestMapping("/GetFailedLicenses")
    public List<LicenseInternal> GetFailedLicenses(@RequestParam(value="date", defaultValue="") String date) {

        return LicenseDB.GetFailedLicensesByDate(date);
    }

    @RequestMapping("/GetPendingNiprSyncDates")
    public List<String> GetPendingNiprSyncDates() {
        HashMap<String, GregorianCalendar> lNiprSyncDates = LicenseDB.GetPendingNiprSyncDates();
        return new ArrayList<String>(lNiprSyncDates.keySet());
    }

    @RequestMapping(value = "/AddNiprSyncDate/{date}", method = RequestMethod.POST)
    public void AddNiprSyncDate(@PathVariable String date) {
        LicenseDB.AddNiprSyncDate(date);
    }

    @RequestMapping(value = "/RemoveNiprSyncDate/{date}", method = RequestMethod.POST)
    public void RemoveNiprSyncDate(@PathVariable String date) {
        LicenseDB.AddNiprSyncDate(date);
    }

    @RequestMapping(value = "/TriggerResync", method = RequestMethod.POST)
    public void TriggerResync() {
        LicenseDB.TriggerResync();
    }
}
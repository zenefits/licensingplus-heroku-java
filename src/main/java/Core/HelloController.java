package Core;

import Core.Nipr.LicenseInternal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {

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

    @RequestMapping(value = "/updateNiprSyncDate/{date}", method = RequestMethod.POST)
    public void updateNiprSyncDate(@PathVariable String date) {
        LicenseDB.UpdateNiprSyncDates(date);
    }

}
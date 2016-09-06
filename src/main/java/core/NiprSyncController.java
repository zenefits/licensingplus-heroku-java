package core;

import core.nipr.LicenseInternal;
import core.nipr.NiprSyncStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
public class NiprSyncController {

    @RequestMapping("/")
    public void index(HttpServletRequest request, HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", "/login.html");
    }

    @RequestMapping("/getFailedLicenses")
    public List<LicenseInternal> getFailedLicenses(@RequestParam(value="date", defaultValue="") String date,
                                                   HttpServletRequest request, HttpServletResponse response) {
        if(!isAuthorized(request, response)) {
            return null;
        }

        return LicenseDB.getFailedLicensesByDate(date);
    }

    @RequestMapping("/getStatus")
    public Map<String, NiprSyncStatus> getStatus(HttpServletRequest request, HttpServletResponse response) {

        if(!isAuthorized(request, response)) {
            return null;
        }

        return LicenseDB.getCurrentStatus();
    }

    @RequestMapping("/getPendingNiprSyncDates")
    public List<String> getPendingNiprSyncDates(HttpServletRequest request, HttpServletResponse response) {

        if(!isAuthorized(request, response)) {
            return null;
        }

        Map<String, GregorianCalendar> lNiprSyncDates = LicenseDB.getPendingNiprSyncDates();
        return new ArrayList<String>(lNiprSyncDates.keySet());
    }

    @RequestMapping(value = "/addNiprSyncDateRange", method = RequestMethod.POST)
    public void addNiprSyncDateRange (
            @RequestParam(value="startDate", defaultValue="") String startDate,
            @RequestParam(value="endDate", defaultValue="") String endDate,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        if(!isAuthorized(request, response)) {
            return;
        }

        LicenseDB.addNiprSyncDateRange(startDate, endDate);
    }

    @RequestMapping(value = "/removeNiprSyncDate/{date}", method = RequestMethod.POST)
    public void removeNiprSyncDate(@PathVariable String date,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {

        if(!isAuthorized(request, response)) {
            return;
        }

        LicenseDB.removeNiprSyncDate(date);
    }

    @RequestMapping(value = "/triggerResync", method = RequestMethod.POST)
    public void triggerResync(HttpServletRequest request,
                              HttpServletResponse response) {
        if(!isAuthorized(request, response)) {
            return;
        }

        LicenseDB.triggerResync();
    }

    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public void authorize(@RequestBody AuthRequest request, HttpServletResponse response) {
        String s = request.getUsername() + ":" + request.getPassword();
        byte[] encodedBytes = org.apache.tomcat.util.codec.binary.Base64.encodeBase64(s.getBytes());
        String lAuthHeader = "Basic " + new String(encodedBytes);

        if(Configuration.IsAuthenticated(lAuthHeader)) {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            System.out.println("Authentication Failed");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
    
    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
    }

    private boolean isAuthorized(HttpServletRequest request, HttpServletResponse response) {
        String lAuthHeader = request.getHeader("Authorization");
        if(!Configuration.IsAuthenticated(lAuthHeader)) {
            System.out.println("Authentication Failed");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }

        return true;
    }
}

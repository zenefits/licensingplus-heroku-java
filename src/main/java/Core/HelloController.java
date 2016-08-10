package Core;

import Core.Nipr.LicenseInternal;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
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

}
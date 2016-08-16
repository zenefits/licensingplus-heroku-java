package Core;

import java.util.Arrays;

import Core.SalesForce.SalesForceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//please remove all unused classes
// will you be able to provide any documentation and some simple flows?
// Maybe provide a README.md?
public class Application {

    public static void main(String[] args) {

        // Read ENV Variables
        Configuration.LoadParams();

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Launching the reconciler thread");

        Reconciler lReconciler = new Reconciler();
        LicenseDB.SetReconcilerThread(lReconciler);
        lReconciler.start();
    }

}
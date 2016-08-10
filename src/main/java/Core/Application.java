package Core;

import java.util.Arrays;

import Core.SalesForce.SalesForceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Launching the reconciler thread");

        Reconciler lReconciler = new Reconciler();
        lReconciler.start();
    }

}
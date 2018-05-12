package pl.kubakra.flywithus;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("pl.kubakra.flywithus")
public class FlyWithUsApp {

    public static void main(String[] args) {
        SpringApplication.run(FlyWithUsApp.class, args);
    }

}
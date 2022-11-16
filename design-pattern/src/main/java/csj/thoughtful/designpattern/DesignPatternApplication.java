package csj.thoughtful.designpattern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesignPatternApplication {

    public static void main(String[] args) {
        System.out.println("(1*0.3)="+(1*0.3));
        System.out.println((1*0.3)==0.3);//true
        System.out.println("(3*0.1)="+(3*0.1));
        System.out.println((3*0.1)==0.3);//false
        System.out.println("(1*(3*0.1))="+(1*(3*0.1)));
        System.out.println((1*(3*0.1))==0.3);//false

        SpringApplication.run(DesignPatternApplication.class, args);
    }

}

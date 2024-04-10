package com.posta.crm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins="*")
@SpringBootApplication
public class CrmApplication {
    
//     @Autowired
//    private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
                System.out.println("Lanzada");
	}

//    @Override
//    public void run(String... args) throws Exception {
//        String password= "123456";
//        for (int i = 0; i < 4; i++) {
//            String passwordBcrytp= passwordEncoder.encode(password);
//            System.out.println(passwordBcrytp);
//            
//        }
//    }

}

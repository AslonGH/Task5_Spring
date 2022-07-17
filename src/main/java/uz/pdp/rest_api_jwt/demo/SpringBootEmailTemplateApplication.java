/*
package uz.pdp.rest_api_jwt.demo;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import uz.pdp.rest_api_jwt.service.EmailSenderService;
import uz.pdp.rest_api_jwt.service.Mail;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@SpringBootApplication
public class SpringBootEmailTemplateApplication implements ApplicationRunner {

    @Autowired
    private EmailSenderService emailSenderService;

    private static Logger log = LoggerFactory.getLogger(SpringBootEmailTemplateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEmailTemplateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        log.info("START... Sending email");
        Mail mail = new Mail();
        mail.setFrom("yourmailid@email.com");//replace with your desired email
        mail.setMailTo("tomail@email.com");//replace with your desired email
        mail.setSubject("Email with Spring boot and thymeleaf template!");
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Developer!");
        model.put("location", "United States");
        model.put("sign", "Java Developer");
        mail.setProps(model);
        emailService.sendEmail(mail);
        log.info("END... Email sent success");
    }
}
*/

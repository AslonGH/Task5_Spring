/*

package uz.pdp.rest_api_jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.nio.charset.StandardCharsets;

@Configuration
public class ThymeleafTemplateConfig {


        @Bean
        public SpringResourceTemplateResolver htmlTemplateResolver(){

             SpringResourceTemplateResolver emailTemplateResolver = new SpringResourceTemplateResolver();

             // Template papkasidagi html ni return qiladi
            emailTemplateResolver.setPrefix("templates/");
            emailTemplateResolver.setSuffix(".html");
            emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
            emailTemplateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
            return emailTemplateResolver;
        }

          // YUQORIDAGI METHODNI OLIB JÖNATADI
         @Bean
         public SpringTemplateEngine springTemplateEngine() {
                   SpringTemplateEngine templateEngine = new SpringTemplateEngine();
             templateEngine.addTemplateResolver(htmlTemplateResolver());
             return templateEngine;
         }

    }


*/

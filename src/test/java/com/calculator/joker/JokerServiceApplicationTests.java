package com.calculator.joker;

import com.calculator.joker.api.model.ResponseModel;
import com.calculator.joker.api.service.JokerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
@SpringBootConfiguration
public class JokerServiceApplicationTests {

    final String sampleRequest = "{\n" +
            "\t\"roundingValue\": 0.25,\n" +
            "\t\"customers\": [\n" +
            "\t\t{\n" +
            "\t\t\t\"name\":\"Kubra Ozcan\",\n" +
            "\t\t\t\"cost\":\"21\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"name\":\"Ozan Güldali\",\n" +
            "\t\t\t\"cost\":\"36\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"name\":\"Özgür Cetintas\",\n" +
            "\t\t\t\"cost\":\"25\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"name\":\"Murat Ham\",\n" +
            "\t\t\t\"cost\":\"27\"\n" +
            "\t\t},\n" +
            "\t\t{\n" +
            "\t\t\t\"name\":\"Meltem Sen\",\n" +
            "\t\t\t\"cost\":\"16.5\"\n" +
            "\t\t}\n" +
            "\t\t]\n" +
            "}";

    @Test
    public void contextLoads() {

        ResponseModel response = JokerService.getResponse( sampleRequest );

    }

}

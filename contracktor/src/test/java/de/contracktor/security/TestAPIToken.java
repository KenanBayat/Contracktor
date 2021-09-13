package de.contracktor.security;

import de.contracktor.ContracktorApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(value = SpringBootTest.WebEnvironment.MOCK, classes = ContracktorApplication.class)
@AutoConfigureMockMvc
public class TestAPIToken {

}

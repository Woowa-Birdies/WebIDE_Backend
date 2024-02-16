package goorm.woowa.webide.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoConfigTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testConnection(){
        assert mongoTemplate != null;
    }
}

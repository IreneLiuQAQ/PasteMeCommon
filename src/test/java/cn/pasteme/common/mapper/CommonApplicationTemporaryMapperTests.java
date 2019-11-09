package cn.pasteme.common.mapper;

import cn.pasteme.common.entity.TemporaryDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * @author Irene
 * @version 1.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommonApplicationTemporaryMapperTests {
    @Autowired
    private TemporaryMapper temporaryMapper;

    @Test
    @Transactional
    @Rollback
    public void createTest(){
        String key = "sqy sb";
        Long count = temporaryMapper.countAll();

        // create
        {
            TemporaryDO temporaryDO = new TemporaryDO();
            temporaryDO.setKey(key);
            temporaryDO.setLang("plain");
            temporaryDO.setContent("sqy is sunny boy");
            temporaryDO.setClientIp("0.0.0.0");
            temporaryDO.setPassword("password");
            assertEquals(Long.valueOf(1), temporaryMapper.create(temporaryDO));

            key = temporaryDO.getKey();
        }

        assertEquals(Long.valueOf(count + 1), temporaryMapper.countAll());

        // getByKey
        {
            assertNull(temporaryMapper.getByKey("sqy handsome"));
            assertEquals(key, temporaryMapper.getByKey(key).getKey());
        }

        // eraseByKey
        {
            assertNotNull(temporaryMapper.getByKey(key));
            assertEquals(Long.valueOf(1), temporaryMapper.eraseByKey(key));
            assertNull(temporaryMapper.getByKey(key));
        }
    }
}

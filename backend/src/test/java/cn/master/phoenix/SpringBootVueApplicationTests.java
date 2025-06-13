package cn.master.phoenix;

import cn.master.phoenix.config.InfluxUtils;
import cn.master.phoenix.service.SystemUserService;
import com.influxdb.query.FluxTable;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@SpringBootTest
class SpringBootVueApplicationTests {
    @Resource
    SystemUserService systemUserService;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    InfluxUtils influxUtils;


    @Test
    void contextLoads() {
        String query =
                "  |> range(start: -1d)\n" +
                        "  |> filter(fn: (r) => r[\"_measurement\"] == \"jxzy_person_monitor\")\n" +
                        "  |> filter(fn: (r) => r[\"_field\"] == \"date_time\")\n" +
                        "  |> last()\n";
//        InfluxUtils influxUtils = (InfluxUtils) SpringContextUtils.getBean("influxUtils");
        List<FluxTable> tables = influxUtils.getList(query);
        for (FluxTable table : tables) {
            System.out.println(table.getRecords());
        }
    }
}

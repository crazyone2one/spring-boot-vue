package cn.master.phoenix.config;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Created by 11's papa on 2025/5/29
 */
@Component(value = "influxUtils")
public class InfluxUtils {


    @Value("${influx.token:''}")
    private String token;

    @Value("${influx.bucket:''}")
    private String bucket;

    @Value("${influx.url:''}")
    private String url;

    @Value("${influx.org:''}")
    private String org;

    private final InfluxDBClient influxDBClient = InfluxDBClientFactory.create(url, token.toCharArray(), org, bucket);

    public List<FluxTable> getList(String fluxParam) {

        String flux = "from(bucket:\"" + bucket + "\")" + fluxParam;

        QueryApi queryApi = influxDBClient.getQueryApi();

        return queryApi.query(flux);
    }
}

package com.example.demo.util.file;

import io.minio.MinioClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author JingQ on 2017/12/27.
 */
@ComponentScan
public class MinioClientPooledFactory extends BasePooledObjectFactory<MinioClient>  {

    private String ip;
    private String accessKeyId;
    private String accessKeySecret;

    @Override
    public MinioClient create() throws Exception {
        return new MinioClient(ip, accessKeyId, accessKeySecret);
    }

    @Override
    public PooledObject<MinioClient> wrap(MinioClient client) {
        return new DefaultPooledObject<MinioClient>(client);
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}

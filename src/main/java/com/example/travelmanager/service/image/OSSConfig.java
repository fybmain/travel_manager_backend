package com.example.travelmanager.service.image;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class OSSConfig {
    @Getter @Setter
    private static String endpoint = "oss-cn-hangzhou.aliyuncs.com";
    @Getter @Setter
    private static String accessKeyId = "LTAI4FvSuXnyoFnrXnY16aGx";
    @Getter @Setter
    private static String accessKeySecret = "Sb2TxAdGWuX6sX5x63nrjhdxPV1PeB";
    @Getter @Setter
    private static String bucketName = "picturesbed";
}

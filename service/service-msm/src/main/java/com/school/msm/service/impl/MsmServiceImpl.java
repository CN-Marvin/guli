package com.school.msm.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.tea.TeaException;
import com.aliyun.teaopenapi.models.Config;
import com.aliyun.teautil.models.RuntimeOptions;
import com.school.msm.service.MsmService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * 功能描述：短信发送
 *
 * @Package: com.school.msm.service.impl
 * @author: Marvin-zl
 * @date: 2022/6/1 15:53
 */
@Service
public class MsmServiceImpl implements MsmService {



    public static Client createClient(String accessKeyId, String accessKeySecret) throws Exception {
        Config config = new Config()
                // AccessKey ID
                .setAccessKeyId(accessKeyId)
                // AccessKey Secret
                .setAccessKeySecret(accessKeySecret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }

    @Override
    public boolean send(Map<String, Object> param, String phone){
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        try {
            Client client = MsmServiceImpl.createClient("LTAI5tLcD8PnmddQiZFtTwzN", "67yGEqTafg3KNkRmyLue2bEiRhMEUn");
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(phone)
                    .setTemplateParam("{\"code\":\"1234\"}");
            RuntimeOptions runtime = new RuntimeOptions();

            client.sendSmsWithOptions(sendSmsRequest, runtime);
        } catch (TeaException error) {

            com.aliyun.teautil.Common.assertAsString(error.message);
            return false;
        } catch (Exception _error) {
            TeaException error = new TeaException(_error.getMessage(), _error);

            com.aliyun.teautil.Common.assertAsString(error.message);
            return false;
        }
        return true;
    }
}

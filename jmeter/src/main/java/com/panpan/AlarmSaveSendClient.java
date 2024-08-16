package com.panpan;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.logging.log4j.core.util.UuidUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/4/10       create this file
 * </pre>
 */
public class AlarmSaveSendClient extends AbstractJavaSamplerClient {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private AlarmTestBo bo;

    /**
     * 这个方法是用来自定义java方法入参的
     * params.addArgument("num1","");表示入参名字叫num1，默认值为空。
     * 在最后Jmeter界面中就会提示要输入这些参数
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("serverIp", "");
        params.addArgument("serverPort", "");
        return params;
    }

    /**
     * 启动时，需要做的处理
     * 每个线程测试前执行一次，做一些初始化工作
     * 获取输入的参数,赋值给变量,参数也可以在下面的runTest方法中获取,这里是为了展示该方法的作用
     * 构建本次需要发送的数据
     * @param arg0
     */
    @Override
    public void setupTest(JavaSamplerContext arg0) {
        bo = new AlarmTestBo();


        bo.setSessionId(getUUid());

        Random random = new Random();

        int threat = random.nextInt(Config.threatEventIdToTypeIdMap.size());

        Map.Entry<Integer, Integer> eventTypeEntry =
                Config.threatEventIdToTypeIdMap.entrySet().stream().findAny().get();

        bo.setThreatEventId(eventTypeEntry.getKey());
        bo.setThreatTypeId(eventTypeEntry.getValue());

        bo.setAffectedIp("192.168." + random.nextInt(100) + "." + random.nextInt(255));
        bo.setAffectedPort(random.nextInt(50000) + 1);

        bo.setAttackIp("10.1." + random.nextInt(100) + "." + random.nextInt(255));
        bo.setAttackPort(random.nextInt(50000) + 1);

        bo.setAttackStatus(random.nextInt(3));

        bo.setDataDirection(0);

        bo.setTransferProtocols("TCP");

        bo.setApplicationProtocols("2");




        bo.setSendTime(sdf.format(new Date()));

    }


    /**
     * 真正执行逻辑的方法
     *
     * @param arg0
     * @return
     */
    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {

        return null;
    }

    /**
     * 测试结束后调用
     *
     * @param arg0
     */
    @Override
    public void teardownTest(JavaSamplerContext arg0) {

    }


    private IocBo randomIoc() {
        Random random = new Random();

        int iocType = random.nextInt(3);

        IocBo bo = new IocBo();
        bo.setIocType(iocType);

        switch (iocType) {
            case 1 :
        }

    }

    private String createDomain() {
        return null;
    }


    private String createIp() {
        return null;
    }

    private String createUrl() {
        return null;
    }



    /**
     * 获取一个随机数
     * @return
     */
    private String getUUid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}

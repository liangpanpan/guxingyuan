package com.pp.xml;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/8       create this file
 * </pre>
 */
public class XmlObjectConvert {

    public static void main(String[] args) throws JsonProcessingException {
        XmlDataTestBo testBo = new XmlDataTestBo();
        testBo.setTest1("test1");
        testBo.setTest2("test2");

        XmlDataBo xmlDataBo = new XmlDataBo();
        xmlDataBo.setId(1001L);
        xmlDataBo.setName("test");
        xmlDataBo.setTestBo(testBo);

        String xml = JacksonXmlUtil.toXml(xmlDataBo);
        System.out.println(xml);




        List<XMLAccountBo> accountBoList = new ArrayList<>();

        XMLAccountBo accountBo = new XMLAccountBo();
        accountBo.setId("121212");
        accountBo.setSn("123");
        accountBo.setName("111");
        accountBo.setAccId("1");
        accountBo.setDescription("des111");

        EncryptElement encryptElement = new EncryptElement();
        encryptElement.setEncrytValue("true");
        encryptElement.setValue("111");
        accountBo.setEmail(encryptElement);


        accountBoList.add(accountBo);

        accountBo = new XMLAccountBo();
        accountBo.setId("222333");
        accountBo.setSn("22222");
        accountBo.setName("222");
        accountBo.setAccId("2");
        accountBo.setDescription("des222");
        accountBoList.add(accountBo);

        XMLAccountRootBo rootBo = new XMLAccountRootBo();
        rootBo.setXmlAccountBoList(accountBoList);

        System.out.println(JacksonXmlUtil.toXml(rootBo));


        System.out.println("========空信息=========");
        rootBo.setXmlAccountBoList(null);
        System.out.println(JacksonXmlUtil.toXml(rootBo));


    }
}

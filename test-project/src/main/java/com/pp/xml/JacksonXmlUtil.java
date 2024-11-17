package com.pp.xml;

import com.ctc.wstx.api.WstxOutputProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

import javax.xml.stream.XMLOutputFactory;
import java.io.IOException;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/8       create this file
 * </pre>
 */
public class JacksonXmlUtil {

    private static class SingletonHolder {
        private static XmlMapper xmlMapper = new XmlMapper();

        static {
            // 在构造器中进行配置

            // 设置XML声明中使用双引号
            XmlFactory factory = xmlMapper.getFactory();
            XMLOutputFactory xmlOutputFactory = factory.getXMLOutputFactory();
            xmlOutputFactory.setProperty(WstxOutputProperties.P_USE_DOUBLE_QUOTES_IN_XML_DECL, true);

            // 反序列化的时候如果多了其他属性,不抛出异常
            xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            // 设置添加XML声明
            xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        }
    }

    public static XmlMapper getXmlSingleton() {
        XmlMapper mapper = SingletonHolder.xmlMapper;
        return mapper;
    }

    /**
     * 对象转xml
     *
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public static String toXml(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = getXmlSingleton();
        return mapper.writeValueAsString(obj);
    }


    /**
     * xml转为对象
     *
     * @param xml
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T toXmlObject(String xml, Class<T> clazz) throws IOException {
        XmlMapper mapper = getXmlSingleton();
        return mapper.readValue(xml, clazz);
    }

}

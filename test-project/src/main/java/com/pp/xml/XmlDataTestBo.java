package com.pp.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/8       create this file
 * </pre>
 */
@Data
@JacksonXmlRootElement(localName = "node")
public class XmlDataTestBo {

    @JacksonXmlProperty(localName = "test1")
    private String Test1;

    @JacksonXmlProperty(localName = "test2")
    private String Test2;

}

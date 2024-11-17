package com.pp.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.Data;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2024/11/11       create this file
 * </pre>
 */
@Data
public class EncryptElement {

    @JacksonXmlProperty(localName = "encrypt", isAttribute = true)
    private String encrytValue;

    @JacksonXmlText
    private String value;


}

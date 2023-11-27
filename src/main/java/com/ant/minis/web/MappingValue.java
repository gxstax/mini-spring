package com.ant.minis.web;

/**
 * <p>
 *
 * </p>
 *
 * @author GaoXin
 * @since 2023/11/27 18:05
 */
public class MappingValue {
    private String uri;

    private String clz;

    private String method;

    public MappingValue(String uri, String clz, String method) {
        this.uri = uri;
        this.clz = clz;
        this.method = method;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

package com.dragon.jsbridge;

/**
 * Description:
 *
 * @author: guoyongping
 * @date: 2017/3/21 10:40
 */

public class DeviesInfo {

    private String name;
    private String os;
    private String version;

    public DeviesInfo(String name, String os, String version) {
        this.name = name;
        this.os = os;
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DeviesInfo{" + "name='" + name + '\'' + ", os='" + os + '\'' + ", version='" + version + '\'' + '}';
    }
}

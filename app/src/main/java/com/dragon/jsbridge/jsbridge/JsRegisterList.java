package com.dragon.jsbridge.jsbridge;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xunice on 16/7/3.
 */
public class JsRegisterList implements Serializable {

   private List<Action> jsRegisterList;

    public List<Action> getJsRegisterList() {
        return jsRegisterList;
    }

    public void setJsRegisterList(List<Action> jsRegisterList) {
        this.jsRegisterList = jsRegisterList;
    }
}

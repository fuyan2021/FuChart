package com.example.fuchart.bean;

import java.util.List;

public
/**
 * com.example.fuchart.bean
 * FuChart
 * 2022/06/02 
 * Created by fuyan on Admin
 */

class AxisBean {
    private List<Integer> xAxis;
    private List<Integer> yAxis;
    private List<Integer> radios;
    private AxisBean mAxisBean;

//    public static AxisBean newInstance() {
//        if (mAxisBean != null) {
//            return mAxisBean;
//        }
//            return new AxisBean();
//    }

    public AxisBean() {
    }

    public AxisBean(List<Integer> xAxis, List<Integer> yAxis, List<Integer> radios) {
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        this.radios = radios;
    }

    public List<Integer> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<Integer> xAxis) {
        this.xAxis = xAxis;
    }

    public List<Integer> getyAxis() {
        return yAxis;
    }

    public void setyAxis(List<Integer> yAxis) {
        this.yAxis = yAxis;
    }

    public List<Integer> getRadios() {
        return radios;
    }

    public void setRadios(List<Integer> radios) {
        this.radios = radios;
    }
}

package model;

import javax.faces.bean.ManagedBean;

@ManagedBean(name = "formBean")
public class FormBean {

    private int xField = 0;
    public int getxField() {
        return xField;
    }
    public void setxField(int xField) {
        this.xField = xField;
    }

    private double yField = 0;
    public double getyField() {
        return yField;
    }
    public void setyField(double yField) {
        this.yField = yField;
    }

    private boolean rField1 = false;
    public boolean getrField1() {
        return rField1;
    }
    public void setrField1(boolean rField1) {
        this.rField1 = rField1;
    }

    private boolean rField2 = false;
    public boolean getrField2() {
        return rField2;
    }
    public void setrField2(boolean rField2) {
        this.rField2 = rField2;
    }

    private boolean rField3 = false;
    public boolean getrField3() {
        return rField3;
    }
    public void setrField3(boolean rField3) {
        this.rField3 = rField3;
    }

    private boolean rField4 = false;
    public boolean getrField4() {
        return rField4;
    }
    public void setrField4(boolean rField4) {
        this.rField4 = rField4;
    }

    private boolean rField5 = false;
    public boolean getrField5() {
        return rField5;
    }
    public void setrField5(boolean rField5) {
        this.rField5 = rField5;
    }


    public void processForm() {
        // TODO: add behavior
        clearForm();
    }

    private void clearForm() {

        setxField(0);
        setyField(0);
        setrField1(false);
        setrField2(false);
        setrField3(false);
        setrField4(false);
        setrField5(false);
    }
}

package sample;

import javafx.scene.image.Image;

public class Capital {

    private  Image flag;
    private String nameCapital;
    private boolean check;

    public Capital(String nameCapital, Image flag) {
        this.nameCapital = nameCapital;
        this.flag = flag;
    }

    public String getNameCapital() {
        return nameCapital;
    }

    public Image getFlag() {
        return flag;
    }

    public boolean isCheck(){
        return check;
    }
}

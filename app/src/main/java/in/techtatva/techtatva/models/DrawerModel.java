package in.techtatva.techtatva.models;

public class DrawerModel {

    private int icon, text;

    public DrawerModel(){

    }

    public int getText() {
        return text;
    }

    public void setText(int text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

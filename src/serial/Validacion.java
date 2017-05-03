package serial;

import java.io.Serializable;

public class Validacion implements Serializable {

    private boolean check;
    private String type;

    public Validacion(boolean check, String type) {
        this.check = check;
        this.type = type;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
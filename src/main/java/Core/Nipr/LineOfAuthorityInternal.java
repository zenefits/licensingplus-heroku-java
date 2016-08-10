package Core.Nipr;

/**
 * Created by vthiruvengadam on 8/7/16.
 */
public class LineOfAuthorityInternal {

    public String name;

    public boolean isActive;

    public LineOfAuthorityInternal() {
        this.isActive = false;
    }

    @Override
    public String toString() {
        return "LineOfAuthorityInternal{" +
                "Name='" + name + '\'' +
                ", IsActive=" + isActive +
                '}';
    }
}

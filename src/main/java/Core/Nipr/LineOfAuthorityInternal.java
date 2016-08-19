package Core.Nipr;

/**
 * Created by vthiruvengadam on 8/7/16.
 */
public class LineOfAuthorityInternal {

    public String name;

    public Boolean isActive;

    public LineOfAuthorityInternal() {
        this.isActive = false;
    }

    public LineOfAuthorityInternal GetCopy() {
        LineOfAuthorityInternal loa = new LineOfAuthorityInternal();
        loa.isActive = this.isActive;
        loa.name = this.name;
        return loa;
    }

    @Override
    public String toString() {
        return "LineOfAuthorityInternal{" +
                "Name='" + name + '\'' +
                ", IsActive=" + isActive +
                '}';
    }
}

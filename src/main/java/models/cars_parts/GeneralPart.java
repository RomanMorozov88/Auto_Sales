package models.cars_parts;

/**
 * Базовый класс для всех деталей.
 */
public class GeneralPart {

    private String partName;

    public GeneralPart() {
    }

    public GeneralPart(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }
}

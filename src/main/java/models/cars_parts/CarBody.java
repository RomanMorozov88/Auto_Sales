package models.cars_parts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Кузов
 */
public class CarBody extends GeneralPart {

    @JsonProperty("part_name")
    private String partName;

    public CarBody() {
    }

    public CarBody(String partName) {
        this.partName = partName;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof CarBody)) return false;
        CarBody carBody = (CarBody) o;
        return Objects.equals(getPartName(), carBody.getPartName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(partName);
    }
}
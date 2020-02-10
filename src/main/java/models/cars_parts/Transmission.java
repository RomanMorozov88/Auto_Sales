package models.cars_parts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Трансмиссия
 */
public class Transmission extends GeneralPart {

    @JsonProperty("part_name")
    private String partName;

    public Transmission() {
    }

    public Transmission(String partName) {
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
        if (o == null || !(o instanceof Transmission)) return false;
        Transmission that = (Transmission) o;
        return Objects.equals(getPartName(), that.getPartName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(partName);
    }
}
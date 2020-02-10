package models.cars_parts;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Двигатель
 */
public class Engine extends GeneralPart {

    @JsonProperty("part_name")
    private String partName;

    public Engine() {
    }

    public Engine(String partName) {
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
        if (o == null || !(o instanceof Engine)) return false;
        Engine engine = (Engine) o;
        return Objects.equals(getPartName(), engine.getPartName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(partName);
    }
}
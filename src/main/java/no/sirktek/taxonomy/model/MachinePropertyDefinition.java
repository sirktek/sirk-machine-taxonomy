package no.sirktek.taxonomy.model;

/**
 * Machine-specific property definition with type detection
 */
public class MachinePropertyDefinition {

    /**
     * Convert RDF range type to PropertyType enum equivalent for machine taxonomy
     * @param propertyDef the property definition
     * @return the corresponding PropertyType enum value
     */
    public static PropertyType getPropertyType(PropertyDefinition propertyDef) {
        String rangeType = propertyDef.rangeType();
        String name = propertyDef.name();

        if (rangeType == null) {
            return PropertyType.STRING;
        }

        return switch (rangeType) {
            case "http://taxonomy.sirktek.no/machine#EmissionEntry"     -> PropertyType.EMISSION;
            case "http://taxonomy.sirktek.no/machine#ConsistsOfEntry"   -> PropertyType.CONSISTS_OF;
            case "http://taxonomy.sirktek.no/machine#PowerSource"       -> PropertyType.POWER_SOURCE;
            case "http://taxonomy.sirktek.no/machine#OperationalStatus" -> PropertyType.OPERATIONAL_STATUS;
            case "http://www.w3.org/2001/XMLSchema#string" -> {
                if (name != null) {
                    if (name.equals("unit")) yield PropertyType.UNIT;
                    if (name.equals("resourceType")) yield PropertyType.RESOURCE_TYPE;
                }
                yield PropertyType.STRING;
            }
            case "http://www.w3.org/2001/XMLSchema#decimal" -> {
                if (name != null) {
                    if (name.contains("weight")) yield PropertyType.DECIMAL_KG;
                    if (name.contains("volume")) yield PropertyType.DECIMAL_M3;
                    if (name.equals("powerConsumption")) yield PropertyType.DECIMAL_KW;
                    if (name.equals("voltage")) yield PropertyType.DECIMAL_V;
                    if (name.equals("maxSpeed")) yield PropertyType.DECIMAL_RPM;
                    if (name.equals("operatingHours")) yield PropertyType.DECIMAL_HOURS;
                    if (name.contains("length") || name.contains("width") || name.contains("height")
                            || name.contains("Length") || name.contains("Width") || name.contains("Height")
                            || name.contains("Thickness"))
                        yield PropertyType.DECIMAL_CM;
                }
                yield PropertyType.DECIMAL;
            }
            case "http://www.w3.org/2001/XMLSchema#date"    -> PropertyType.DATE;
            case "http://www.w3.org/2001/XMLSchema#boolean" -> PropertyType.BOOLEAN;
            case "http://www.w3.org/2001/XMLSchema#anyURI"  -> PropertyType.URL;
            case "http://www.w3.org/2001/XMLSchema#integer" -> {
                if (name != null && name.equals("yearOfManufacture")) yield PropertyType.YEAR;
                yield PropertyType.INTEGER;
            }
            default -> {
                if (rangeType.contains("Manufacturer") || rangeType.contains("Machine") || rangeType.contains("Model")) {
                    yield PropertyType.CATEGORY;
                }
                if (name != null) {
                    if (name.equals("unit")) yield PropertyType.UNIT;
                    if (name.equals("resourceType")) yield PropertyType.RESOURCE_TYPE;
                }
                yield PropertyType.STRING;
            }
        };
    }

    /**
     * Property types for machine taxonomy
     */
    public enum PropertyType {
        /** String property type */
        STRING,
        /** Decimal property type */
        DECIMAL,
        /** Integer property type */
        INTEGER,
        /** Date property type */
        DATE,
        /** Boolean property type */
        BOOLEAN,
        /** Integer scale 1-5 property type */
        INTEGER_SCALE_1TO5,
        /** Year property type (integer representing a year) */
        YEAR,
        /** Decimal centimeters property type */
        DECIMAL_CM,
        /** Unit property type */
        UNIT,
        /** Decimal kilograms property type */
        DECIMAL_KG,
        /** Decimal square meters property type */
        DECIMAL_M2,
        /** Decimal cubic meters property type */
        DECIMAL_M3,
        /** Decimal kilowatts property type */
        DECIMAL_KW,
        /** Decimal volts property type */
        DECIMAL_V,
        /** Decimal revolutions per minute property type */
        DECIMAL_RPM,
        /** Decimal hours property type */
        DECIMAL_HOURS,
        /** Category property type */
        CATEGORY,
        /** URL property type */
        URL,
        /** Multi-category property type */
        MULTI_CATEGORY,
        /** Email form property type */
        EMAIL_FORM,
        /** Resource type property type */
        RESOURCE_TYPE,
        /** Power source enumeration property type */
        POWER_SOURCE,
        /** Operational status enumeration property type */
        OPERATIONAL_STATUS,
        /** Emission property type */
        EMISSION,
        /** Bill of materials (consists-of) property type */
        CONSISTS_OF
    }
}

package no.sirktek.taxonomy.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static no.sirktek.taxonomy.model.MachinePropertyDefinition.PropertyType;
import static no.sirktek.taxonomy.model.MachinePropertyDefinition.getPropertyType;

class PropertyDefinitionTest {

    @Test
    void shouldBuildPropertyDefinitionWithAllFields() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("weight")
                .englishLabel("Weight (kg)")
                .norwegianLabel("Vekt (kg)")
                .uri("http://taxonomy.sirktek.no/machine#weight")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .domainClass("Machine")
                .description("Weight of the machine")
                .build();

        assertEquals("weight", property.name());
        assertEquals("Weight (kg)", property.englishLabel());
        assertEquals("Vekt (kg)", property.norwegianLabel());
        assertEquals("http://taxonomy.sirktek.no/machine#weight", property.uri());
        assertEquals("http://www.w3.org/2001/XMLSchema#decimal", property.rangeType());
        assertEquals("Machine", property.domainClass());
        assertEquals("Weight of the machine", property.description());
    }

    @Test
    void shouldBuildPropertyDefinitionWithNullFields() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("testProperty")
                .englishLabel("Test Property")
                .uri("http://test.com/property")
                .rangeType("http://www.w3.org/2001/XMLSchema#string")
                .build();

        assertEquals("testProperty", property.name());
        assertEquals("Test Property", property.englishLabel());
        assertNull(property.norwegianLabel());
        assertEquals("http://test.com/property", property.uri());
        assertEquals("http://www.w3.org/2001/XMLSchema#string", property.rangeType());
        assertNull(property.domainClass());
        assertNull(property.description());
    }

    @Test
    void shouldDetectStringPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("color")
                .rangeType("http://www.w3.org/2001/XMLSchema#string")
                .build();

        assertEquals(PropertyType.STRING, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalKgPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("weight")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_KG, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalM3PropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("volume")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_M3, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalCmPropertyType() {
        PropertyDefinition lengthProperty = PropertyDefinition.builder()
                .name("length")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        PropertyDefinition widthProperty = PropertyDefinition.builder()
                .name("width")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        PropertyDefinition heightProperty = PropertyDefinition.builder()
                .name("height")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        PropertyDefinition thicknessProperty = PropertyDefinition.builder()
                .name("maxCuttingThickness")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_CM, getPropertyType(lengthProperty));
        assertEquals(PropertyType.DECIMAL_CM, getPropertyType(widthProperty));
        assertEquals(PropertyType.DECIMAL_CM, getPropertyType(heightProperty));
        assertEquals(PropertyType.DECIMAL_CM, getPropertyType(thicknessProperty));
    }

    @Test
    void shouldDetectDecimalKwPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("powerConsumption")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_KW, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalVoltagePropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("voltage")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_V, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalRpmPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("maxSpeed")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_RPM, getPropertyType(property));
    }

    @Test
    void shouldDetectDecimalHoursPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("operatingHours")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL_HOURS, getPropertyType(property));
    }

    @Test
    void shouldDetectGenericDecimalPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("genericDecimal")
                .rangeType("http://www.w3.org/2001/XMLSchema#decimal")
                .build();

        assertEquals(PropertyType.DECIMAL, getPropertyType(property));
    }

    @Test
    void shouldDetectDatePropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("productionDate")
                .rangeType("http://www.w3.org/2001/XMLSchema#date")
                .build();

        assertEquals(PropertyType.DATE, getPropertyType(property));
    }

    @Test
    void shouldDetectBooleanPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("isAvailable")
                .rangeType("http://www.w3.org/2001/XMLSchema#boolean")
                .build();

        assertEquals(PropertyType.BOOLEAN, getPropertyType(property));
    }

    @Test
    void shouldDetectUrlPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("homepage")
                .rangeType("http://www.w3.org/2001/XMLSchema#anyURI")
                .build();

        assertEquals(PropertyType.URL, getPropertyType(property));
    }

    @Test
    void shouldDetectIntegerPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("numberOfAxes")
                .rangeType("http://www.w3.org/2001/XMLSchema#integer")
                .build();

        assertEquals(PropertyType.INTEGER, getPropertyType(property));
    }

    @Test
    void shouldDetectYearPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("yearOfManufacture")
                .rangeType("http://www.w3.org/2001/XMLSchema#integer")
                .build();

        assertEquals(PropertyType.YEAR, getPropertyType(property));
    }

    @Test
    void shouldDetectCategoryPropertyType() {
        PropertyDefinition manufacturerProperty = PropertyDefinition.builder()
                .name("manufacturer")
                .rangeType("http://taxonomy.sirktek.no/machine#Manufacturer")
                .build();

        PropertyDefinition machineProperty = PropertyDefinition.builder()
                .name("machineType")
                .rangeType("http://taxonomy.sirktek.no/machine#Machine")
                .build();

        PropertyDefinition modelProperty = PropertyDefinition.builder()
                .name("model")
                .rangeType("http://taxonomy.sirktek.no/machine#Model")
                .build();

        assertEquals(PropertyType.CATEGORY, getPropertyType(manufacturerProperty));
        assertEquals(PropertyType.CATEGORY, getPropertyType(machineProperty));
        assertEquals(PropertyType.CATEGORY, getPropertyType(modelProperty));
    }

    @Test
    void shouldDetectPowerSourcePropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("powerSource")
                .rangeType("http://taxonomy.sirktek.no/machine#PowerSource")
                .build();

        assertEquals(PropertyType.POWER_SOURCE, getPropertyType(property));
    }

    @Test
    void shouldDetectOperationalStatusPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("operationalStatus")
                .rangeType("http://taxonomy.sirktek.no/machine#OperationalStatus")
                .build();

        assertEquals(PropertyType.OPERATIONAL_STATUS, getPropertyType(property));
    }

    @Test
    void shouldNotResolveCommonRangeMarkersToMachinePropertyType() {
        // EmissionEntry / ConsistsOfEntry are owned by CommonPropertyDefinition
        // in v3.0 — MachinePropertyDefinition must not recognize them. They
        // don't match the Manufacturer/Machine/Model substring fallback, so
        // they fall through to STRING. The orgadmin backend routes the URI
        // to CommonPropertyDefinition instead.
        PropertyDefinition emissionProp = PropertyDefinition.builder()
                .name("emissionPerUnit")
                .rangeType("http://taxonomy.sirktek.no/common#EmissionEntry")
                .build();
        PropertyDefinition consistsOfProp = PropertyDefinition.builder()
                .name("consistsOf")
                .rangeType("http://taxonomy.sirktek.no/common#ConsistsOfEntry")
                .build();

        assertEquals(PropertyType.STRING, getPropertyType(emissionProp));
        assertEquals(PropertyType.STRING, getPropertyType(consistsOfProp));
    }

    @Test
    void shouldDetectCommonCategoryReferences() {
        // common:Manufacturer and common:Model resolve to CATEGORY via the
        // namespace-agnostic substring fallback, the same as
        // machine:Machine.
        PropertyDefinition manufacturerProp = PropertyDefinition.builder()
                .name("manufacturer")
                .rangeType("http://taxonomy.sirktek.no/common#Manufacturer")
                .build();
        PropertyDefinition modelProp = PropertyDefinition.builder()
                .name("model")
                .rangeType("http://taxonomy.sirktek.no/common#Model")
                .build();
        PropertyDefinition machineProp = PropertyDefinition.builder()
                .name("machineType")
                .rangeType("http://taxonomy.sirktek.no/machine#Machine")
                .build();

        assertEquals(PropertyType.CATEGORY, getPropertyType(manufacturerProp));
        assertEquals(PropertyType.CATEGORY, getPropertyType(modelProp));
        assertEquals(PropertyType.CATEGORY, getPropertyType(machineProp));
    }

    @Test
    void shouldDetectUnitPropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("unit")
                .rangeType("http://www.w3.org/2001/XMLSchema#string")
                .build();

        assertEquals(PropertyType.UNIT, getPropertyType(property));
    }

    @Test
    void shouldDetectResourceTypePropertyType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("resourceType")
                .rangeType("http://www.w3.org/2001/XMLSchema#string")
                .build();

        assertEquals(PropertyType.RESOURCE_TYPE, getPropertyType(property));
    }

    @Test
    void shouldDefaultToStringForUnknownTypes() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("unknownProperty")
                .rangeType("http://unknown.com/type")
                .build();

        assertEquals(PropertyType.STRING, getPropertyType(property));
    }

    @Test
    void shouldHandleNullRangeType() {
        PropertyDefinition property = PropertyDefinition.builder()
                .name("nullRangeProperty")
                .rangeType(null)
                .build();

        assertEquals(PropertyType.STRING, getPropertyType(property));
    }

    @Test
    void shouldTestAllPropertyTypeEnumValues() {
        // v3.0: EMISSION and CONSISTS_OF moved to CommonPropertyDefinition.
        PropertyType[] allTypes = PropertyType.values();

        assertEquals(23, allTypes.length);

        assertNotNull(PropertyType.valueOf("STRING"));
        assertNotNull(PropertyType.valueOf("DECIMAL"));
        assertNotNull(PropertyType.valueOf("INTEGER"));
        assertNotNull(PropertyType.valueOf("DATE"));
        assertNotNull(PropertyType.valueOf("BOOLEAN"));
        assertNotNull(PropertyType.valueOf("INTEGER_SCALE_1TO5"));
        assertNotNull(PropertyType.valueOf("YEAR"));
        assertNotNull(PropertyType.valueOf("DECIMAL_CM"));
        assertNotNull(PropertyType.valueOf("UNIT"));
        assertNotNull(PropertyType.valueOf("DECIMAL_KG"));
        assertNotNull(PropertyType.valueOf("DECIMAL_M2"));
        assertNotNull(PropertyType.valueOf("DECIMAL_M3"));
        assertNotNull(PropertyType.valueOf("DECIMAL_KW"));
        assertNotNull(PropertyType.valueOf("DECIMAL_V"));
        assertNotNull(PropertyType.valueOf("DECIMAL_RPM"));
        assertNotNull(PropertyType.valueOf("DECIMAL_HOURS"));
        assertNotNull(PropertyType.valueOf("CATEGORY"));
        assertNotNull(PropertyType.valueOf("URL"));
        assertNotNull(PropertyType.valueOf("MULTI_CATEGORY"));
        assertNotNull(PropertyType.valueOf("EMAIL_FORM"));
        assertNotNull(PropertyType.valueOf("RESOURCE_TYPE"));
        assertNotNull(PropertyType.valueOf("POWER_SOURCE"));
        assertNotNull(PropertyType.valueOf("OPERATIONAL_STATUS"));
    }
}

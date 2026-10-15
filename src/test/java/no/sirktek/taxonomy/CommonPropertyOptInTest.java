package no.sirktek.taxonomy;

import no.sirktek.taxonomy.model.CategoryInfo;
import no.sirktek.taxonomy.model.PropertyDefinition;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins the machine taxonomy's opt-ins to shared commons properties through the loader:
 * Machine carries the commercial {@code monthlyRent} and no accounting properties.
 */
class CommonPropertyOptInTest {

    private final MachineTaxonomyService taxonomyService = new MachineTaxonomyService();

    @Test
    void machineOptsIntoMonthlyRent() {
        PropertyDefinition rent = machine().properties().stream()
                .filter(p -> "monthlyRent".equals(p.name()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Machine should carry monthlyRent"));

        assertEquals("http://taxonomy.sirktek.no/common#monthlyRent", rent.uri());
        assertEquals("http://www.w3.org/2001/XMLSchema#decimal", rent.rangeType());
        assertEquals("commercial", rent.privateSet());
    }

    @Test
    void machineCarriesSharedPropertiesOnceWithCommonIris() {
        for (String name : List.of("color", "material", "variant", "serialNumber", "model",
                "operationsAndMaintenanceManual", "length", "width", "height", "weight", "volume",
                "manufacturer")) {
            List<PropertyDefinition> matches = machine().properties().stream()
                    .filter(p -> name.equals(p.name()))
                    .toList();

            assertEquals(1, matches.size(), "Machine should carry " + name + " exactly once");
            assertEquals("http://taxonomy.sirktek.no/common#" + name, matches.get(0).uri());
        }
        assertFalse(machine().properties().stream()
                .anyMatch(p -> p.uri().startsWith("http://taxonomy.sirktek.no/machine#")
                        && SHARED.contains(p.name())), "No machine# IRI for shared names");
    }

    private static final List<String> SHARED = List.of("color", "material", "variant",
            "serialNumber", "model", "operationsAndMaintenanceManual", "length", "width", "height",
            "weight", "volume", "manufacturer", "controlChecklist");

    @Test
    void machineHasNoAccountingProperties() {
        List<String> names = machine().properties().stream().map(PropertyDefinition::name).toList();

        for (String accounting : List.of("ledgerAccount", "depreciationGroup", "valuations",
                "bookValue", "taxValue", "wealthTaxValue")) {
            assertFalse(names.contains(accounting), "Machine should not carry " + accounting);
        }
        assertTrue(names.contains("monthlyRent"));
    }

    private CategoryInfo machine() {
        return taxonomyService.getCategoryByClassName("Machine").orElseThrow();
    }
}

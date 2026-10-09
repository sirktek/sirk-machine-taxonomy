package no.sirktek.taxonomy;

import no.sirktek.taxonomy.model.CategoryInfo;
import no.sirktek.taxonomy.model.MachinePropertyDefinition;
import no.sirktek.taxonomy.model.PropertyDefinition;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Machines are control-round points in Vardly, so the Machine root must carry a
 * {@code controlChecklist} property with the same local name and value contract as
 * {@code logistics:controlChecklist} on {@code logistics:ControlPoint}. The category
 * backend validates category property writes against these definitions.
 */
class ControlChecklistPropertyTest {

    private final MachineTaxonomyService taxonomyService = new MachineTaxonomyService();

    @Test
    void machineRootCarriesControlChecklist() {
        Optional<PropertyDefinition> property = findOnMachineRoot("controlChecklist");

        assertTrue(property.isPresent(), "Machine should declare controlChecklist");
        assertEquals("http://taxonomy.sirktek.no/machine#controlChecklist", property.get().uri());
        assertEquals("http://www.w3.org/2001/XMLSchema#string", property.get().rangeType());
        assertEquals("Machine", property.get().domainClass());
        assertEquals("Checklist", property.get().englishLabel());
        assertEquals("Sjekkliste", property.get().norwegianLabel());
    }

    @Test
    void controlChecklistIsAPlainStringProperty() {
        PropertyDefinition property = findOnMachineRoot("controlChecklist").orElseThrow();

        assertEquals(MachinePropertyDefinition.PropertyType.STRING,
                MachinePropertyDefinition.getPropertyType(property));
    }

    private Optional<PropertyDefinition> findOnMachineRoot(String name) {
        CategoryInfo machine = taxonomyService.getCategoryByClassName("Machine").orElseThrow();
        return machine.properties().stream().filter(p -> name.equals(p.name())).findFirst();
    }
}

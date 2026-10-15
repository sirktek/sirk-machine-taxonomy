package no.sirktek.taxonomy;

import no.sirktek.taxonomy.model.CategoryInfo;
import no.sirktek.taxonomy.model.CommonPropertyDefinition;
import no.sirktek.taxonomy.model.PropertyDefinition;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Machines are control-round points in Vardly, so the Machine root must carry a
 * {@code controlChecklist} property: the shared {@code common:controlChecklist} term, ranged
 * {@code common:ChecklistTemplateEntry}, reached by opt-in. The category backend validates
 * category property writes against these definitions.
 */
class ControlChecklistPropertyTest {

    private final MachineTaxonomyService taxonomyService = new MachineTaxonomyService();

    @Test
    void machineRootCarriesControlChecklist() {
        Optional<PropertyDefinition> property = findOnMachineRoot("controlChecklist");

        assertTrue(property.isPresent(), "Machine should declare controlChecklist");
        assertEquals("http://taxonomy.sirktek.no/common#controlChecklist", property.get().uri());
        assertEquals("http://taxonomy.sirktek.no/common#ChecklistTemplateEntry", property.get().rangeType());
                assertEquals("Checklist", property.get().englishLabel());
        assertEquals("Sjekkliste", property.get().norwegianLabel());
    }

    @Test
    void controlChecklistIsATypedChecklistProperty() {
        PropertyDefinition property = findOnMachineRoot("controlChecklist").orElseThrow();

        assertEquals(CommonPropertyDefinition.PropertyType.CHECKLIST,
                CommonPropertyDefinition.getPropertyType(property));
    }

    private Optional<PropertyDefinition> findOnMachineRoot(String name) {
        CategoryInfo machine = taxonomyService.getCategoryByClassName("Machine").orElseThrow();
        return machine.properties().stream().filter(p -> name.equals(p.name())).findFirst();
    }
}

package no.sirktek.taxonomy;

import no.sirktek.taxonomy.model.CategoryInfo;
import no.sirktek.taxonomy.model.TaxonomyTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyServiceTest {

    private MachineTaxonomyService taxonomyService;

    @BeforeEach
    void setUp() {
        taxonomyService = new MachineTaxonomyService();
    }

    @Test
    void shouldLoadBaseTaxonomy() {
        TaxonomyTree taxonomy = taxonomyService.loadBaseTaxonomy();

        assertNotNull(taxonomy);
        assertNotNull(taxonomy.rootCategories());
        assertFalse(taxonomy.rootCategories().isEmpty());
    }

    @Test
    void shouldFindMachineRootCategory() {
        Optional<CategoryInfo> machine = taxonomyService.getCategoryByClassName("Machine");

        assertTrue(machine.isPresent());
        assertEquals("Machine", machine.get().className());
        assertEquals("Machine", machine.get().englishName());
        assertTrue(machine.get().isRoot());
    }

    @Test
    void shouldFindWoodshopMachineCategory() {
        Optional<CategoryInfo> woodshop = taxonomyService.getCategoryByClassName("WoodshopMachine");

        assertTrue(woodshop.isPresent());
        assertEquals("WoodshopMachine", woodshop.get().className());
    }

    @Test
    void shouldFindTextileMachineCategory() {
        Optional<CategoryInfo> textile = taxonomyService.getCategoryByClassName("TextileMachine");

        assertTrue(textile.isPresent());
        assertEquals("TextileMachine", textile.get().className());
    }

    @Test
    void shouldFindCNCMachineSubcategories() {
        assertTrue(taxonomyService.getCategoryByClassName("CNCMachine").isPresent());
        assertTrue(taxonomyService.getCategoryByClassName("TwoAxisCNCMachine").isPresent());
        assertTrue(taxonomyService.getCategoryByClassName("FiveAxisCNCMachine").isPresent());
    }

    @Test
    void shouldFindWoodshopMachineSubcategories() {
        for (String className : new String[]{
                "PanelSaw", "BenchSaw", "SpindleMoulder", "BiscuitJointer", "WideBeltSander",
                "EdgeBander", "EdgeTrimmer", "PlanerThicknesser", "Press", "VacuumLifter", "SprayEquipment"}) {
            assertTrue(taxonomyService.getCategoryByClassName(className).isPresent(),
                    "Expected WoodshopMachine subcategory present: " + className);
        }
    }

    @Test
    void woodshopMachineGroupsContainTheirMembers() {
        // Functional groupings and the leaves reparented under them.
        assertEquals("Saw", taxonomyService.getCategoryByClassName("PanelSaw").orElseThrow().parentClassName());
        assertEquals("Saw", taxonomyService.getCategoryByClassName("BenchSaw").orElseThrow().parentClassName());
        assertEquals("Saw", taxonomyService.getCategoryByClassName("HydraulicSaw").orElseThrow().parentClassName());
        assertEquals("MillingMachine", taxonomyService.getCategoryByClassName("CNCMachine").orElseThrow().parentClassName());
        assertEquals("MillingMachine", taxonomyService.getCategoryByClassName("SpindleMoulder").orElseThrow().parentClassName());
        assertEquals("MillingMachine", taxonomyService.getCategoryByClassName("BiscuitJointer").orElseThrow().parentClassName());
        assertEquals("Sander", taxonomyService.getCategoryByClassName("WideBeltSander").orElseThrow().parentClassName());
        assertEquals("EdgeMachine", taxonomyService.getCategoryByClassName("EdgeBander").orElseThrow().parentClassName());
        assertEquals("EdgeMachine", taxonomyService.getCategoryByClassName("EdgeTrimmer").orElseThrow().parentClassName());
        // Group classes themselves sit under WoodshopMachine.
        for (String group : new String[]{"Saw", "MillingMachine", "Sander", "EdgeMachine", "Press"}) {
            assertEquals("WoodshopMachine", taxonomyService.getCategoryByClassName(group).orElseThrow().parentClassName(),
                    group + " should be a WoodshopMachine group");
        }
    }

    @Test
    void shouldFindPressSubcategories() {
        assertTrue(taxonomyService.getCategoryByClassName("CaseClamp").isPresent());
        assertTrue(taxonomyService.getCategoryByClassName("GluePress").isPresent());
    }

    @Test
    void shouldFindITEquipmentAndSubcategories() {
        assertTrue(taxonomyService.getCategoryByClassName("ITEquipment").isPresent());
        assertTrue(taxonomyService.getCategoryByClassName("PC").isPresent());
        assertTrue(taxonomyService.getCategoryByClassName("Network").isPresent());
    }

    @Test
    void shouldReturnEmptyForNonExistentCategory() {
        Optional<CategoryInfo> nonExistent = taxonomyService.getCategoryByClassName("NonExistent");

        assertFalse(nonExistent.isPresent());
    }

    @Test
    void shouldProvideStats() {
        TaxonomyService.TaxonomyStats stats = taxonomyService.getStats();

        assertTrue(stats.totalCategories() > 0);
        assertTrue(stats.rootCategories() > 0);

        // We expect multiple root categories (Machine, PowerSource, OperationalStatus)
        assertTrue(stats.rootCategories() >= 3);

        // Total categories should be more than root categories due to hierarchy
        assertTrue(stats.totalCategories() > stats.rootCategories());
    }

    @Test
    void shouldCacheTaxonomyAfterFirstLoad() {
        TaxonomyTree first = taxonomyService.loadBaseTaxonomy();
        TaxonomyTree second = taxonomyService.loadBaseTaxonomy();

        // Should be the same instance due to caching
        assertSame(first, second);
    }

    @Test
    void shouldReloadBaseTaxonomy() {
        TaxonomyTree first = taxonomyService.loadBaseTaxonomy();

        TaxonomyTree reloaded = taxonomyService.reloadBaseTaxonomy();

        assertNotNull(reloaded);
        assertNotSame(first, reloaded);

        TaxonomyTree cached = taxonomyService.loadBaseTaxonomy();
        assertSame(reloaded, cached);
    }

    @Test
    void shouldDetectBaseTaxonomyClasses() {
        assertTrue(taxonomyService.isBaseTaxonomyClass("Machine"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("HandheldTool"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("CNCMachine"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("TwoAxisCNCMachine"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("FiveAxisCNCMachine"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("HydraulicSaw"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("SewingMachine"));
        assertTrue(taxonomyService.isBaseTaxonomyClass("FabricCuttingMachine"));
        assertFalse(taxonomyService.isBaseTaxonomyClass("CustomClass"));
        assertFalse(taxonomyService.isBaseTaxonomyClass("NonExistent"));
    }

}

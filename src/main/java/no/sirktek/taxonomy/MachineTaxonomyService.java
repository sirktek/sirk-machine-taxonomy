package no.sirktek.taxonomy;

import no.sirktek.taxonomy.loader.MachineRdfsTaxonomyLoader;

/**
 * Main service for accessing machine taxonomy data
 */
public class MachineTaxonomyService extends TaxonomyService {

    /**
     * Default constructor using machine taxonomy loader
     */
    public MachineTaxonomyService() {
        super(new MachineRdfsTaxonomyLoader());
    }
}

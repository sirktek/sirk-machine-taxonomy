package no.sirktek.taxonomy.loader;

/**
 * Loads the machine taxonomy from RDF-S Turtle files using Apache Jena
 */
public class MachineRdfsTaxonomyLoader extends RdfsTaxonomyLoader {

    private static final String MACHINE_NAMESPACE = "http://taxonomy.sirktek.no/machine#";
    private static final String RESOURCE_PATH = "/taxonomy/machine-base.ttl";

    /**
     * Default constructor
     */
    public MachineRdfsTaxonomyLoader() {
        super();
    }

    @Override
    protected String getNamespace() {
        return MACHINE_NAMESPACE;
    }

    @Override
    protected String getResourcePath() {
        return RESOURCE_PATH;
    }
}

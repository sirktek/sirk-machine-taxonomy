
## Handoff — Group 1

Changed: `pom.xml` (taxonomy-commons 3.14); `machine-base.ttl` (accounting opt-ins and wealthTaxValue
comment replaced by `common:monthlyRent` opt-in; controlChecklist comment rewritten);
`CommonPropertyOptInTest` rewritten on `MachineTaxonomyService` (two tests); `build_tag_and_release.yml`
(Central steps removed, SNAPSHOT message); new `publish_maven_central.yml` (D3); `README.md`
("Commercial properties", "Releases"). TaxonomyServiceTest / PropertyDefinitionTest needed no change.

Verified: tests failed before the TTL edit as expected, then group tests and full `mvnq test` green
(48 tests). Both workflows parse as YAML.

Unverified: `actionlint` not installed; `publish_maven_central.yml` cannot run before merge.
`.settings/org.eclipse.core.resources.prefs` shows as modified (IDE, not touched by me). Nothing committed.

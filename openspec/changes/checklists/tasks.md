# Tasks — checklists (machine-taxonomy slice)

One group, one implementer in a fresh context with this file, design.md and
`specs/machine-control-checklist/spec.md`. Touch only the listed files, run only the listed tests, tick the tasks,
append a short `## Handoff — Group 1` note to `handoff.md` in this directory, and stop when the
tests pass. Maven through `mvnq`; never `clean`. No Java production code.

**Prerequisite:** sirk-taxonomy-commons `checklists` released (its handoff names the version).

Before starting: a GitHub issue; branch `feature/#<id>-checklists`.

Suggested model: haiku.

### Group 1: `controlChecklist` ranges over the checklist marker

Files: `src/main/resources/taxonomy/machine-base.ttl` (only the `machine:controlChecklist` range), `pom.xml`
(only the `taxonomy-commons` version), `src/test/java/no/sirktek/taxonomy/ControlChecklistPropertyTest.java`.
Tests: `mvnq test -Dtest='ControlChecklistPropertyTest'`

- [ ] 1.1 Bump `taxonomy-commons` to the released version.
- [ ] 1.2 Test first: `machineRootCarriesControlChecklist` asserts the range `http://taxonomy.sirktek.no/common#ChecklistTemplateEntry`; rename `controlChecklistIsAPlainStringProperty` to `controlChecklistIsAChecklist` and assert `CommonPropertyDefinition.getPropertyType(property)` = `CHECKLIST` (and, unchanged behaviour, `MachinePropertyDefinition.getPropertyType` = `STRING`). See it fail.
- [ ] 1.3 Change the range to `common:ChecklistTemplateEntry` (design).
- [ ] 1.4 Run the test above, then `mvnq test` once, and `openspec validate checklists --strict`.
      Note the released version in the handoff; sirk-orgadmin-backend bumps to it.

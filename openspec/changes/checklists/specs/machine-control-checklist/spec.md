## ADDED Requirements

### Requirement: controlChecklist is typed as a checklist

`machine:controlChecklist` SHALL have domain `machine:Machine`, range `common:ChecklistTemplateEntry`, and
labels `Checklist`@en and `Sjekkliste`@no, so that `CommonPropertyDefinition.getPropertyType`
returns `CHECKLIST` for it. Its local name SHALL stay `controlChecklist`.

#### Scenario: Property is loaded with the checklist range
- **WHEN** the taxonomy service loads `machine-base.ttl`
- **THEN** `Machine`'s properties include `controlChecklist` with range `http://taxonomy.sirktek.no/common#ChecklistTemplateEntry`, and `CommonPropertyDefinition.getPropertyType` returns `CHECKLIST` for it

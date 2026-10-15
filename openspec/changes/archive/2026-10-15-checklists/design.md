# Design — checklists (machine-taxonomy slice)

## Precedent

This repo already ranges properties over commons classes (machine-base.ttl: `rdfs:range
common:Model`, `common:Manufacturer`), and complex values range over a commons `*Entry` marker
typed by `CommonPropertyDefinition`, not by a domain mapper (logistics-base.ttl:
`common:EmissionEntry`, `common:AllocationEntry`). The checklist follows this exactly. No departure.

## Inventory of everything new

Nothing new in this repo: one changed `rdfs:range`, one dependency version bump, one updated
test. No Java production code changes. The machine mapper's fallback already returns `STRING` for
the new range (its name contains none of the substrings the fallback matches), and orgadmin asks
`CommonPropertyDefinition` first.

## Decision

```turtle
machine:controlChecklist a rdf:Property ;
    ...labels and comment unchanged...
    rdfs:domain machine:Machine ;
    rdfs:range common:ChecklistTemplateEntry .
```

The `#` comment above the declaration is unchanged; it already says a shared commons term is
pending (taxonomy-commons `checklists` proposal, open questions).

## Risks

- **Release order:** taxonomy-commons first, then this repo, then sirk-orgadmin-backend bumps
  both. Shipping this range before orgadmin's Group 3 is harmless: an older orgadmin maps the
  unknown range to `STRING`, as today.

# Accessibility and i18n / l10n

## Accessibility (WCAG 2.2 AA)

| Requirement | Implementation |
|---|---|
| **Perceivable** | Contrast ≥4.5:1; text scaling 400%; captions; no colour-only meaning |
| **Operable** | Full keyboard; focus visible; no keyboard traps; target size ≥24px |
| **Understandable** | Plain language (CEFR B2); consistent nav; error identification |
| **Robust** | Semantic HTML; ARIA; screen-reader tested (VoiceOver/TalkBack) |

## Testing

- **Automated:** `axe-core` in CI on every PR.
- **Manual:** screen-reader walkthroughs of all critical journeys.
- **Audit:** annual third-party accessibility audit.

## Internationalisation (i18n)

- **Externalised strings** (ICU message format).
- **Locale-aware** formatting (currency, dates, numbers).
- **RTL** support (logical properties, mirrored layouts).

## Localisation (l10n)

- **Content** localised per market; compliance text per jurisdiction.
- **Regulatory** disclosures localised and versioned.
- **Pluralisation** and gender rules handled by ICU.

## Support adjustments

- Sign-language video, braille statements, extra time, phone support for accessibility needs.
# Accessibility and i18n / l10n

## Accessibility (WCAG 2.2 AA)

| Requirement | Implementation |
|---|---|
| **Perceivable** | Contrast ≥4.5:1; text scaling 400%; captions/transcripts; no colour-only meaning |
| **Operable** | Full keyboard; focus visible (focus appearance); no keyboard traps; target size ≥24px; dragging alternatives |
| **Understandable** | Plain language (CEFR B2); consistent nav; error identification; redundant entry avoided |
| **Robust** | Semantic HTML; ARIA; screen-reader tested (VoiceOver/TalkBack) |
| **Auth** | Accessible authentication (passkeys); no cognitive-test puzzles |
| **Motion** | Respects `prefers-reduced-motion`; no auto-play >5s |

## Testing

- **Automated:** `axe-core` in CI on every PR.
- **Manual:** screen-reader walkthroughs of all critical journeys; keyboard-only passes.
- **Audit:** annual third-party accessibility audit.

## Internationalisation (i18n)

- **Externalised strings** (ICU message format); **pseudo-localisation** to catch hard-coded text.
- **Locale-aware** formatting (currency, dates, numbers); **fallback locales**.
- **RTL** support (logical properties, mirrored layouts).
- **String freeze** before release; ICU plurals/gender.

## Localisation (l10n)

- **Content** localised per market; compliance text per jurisdiction.
- **Regulatory** disclosures localised and versioned.
- **Formats:** address, phone, date, currency precision/rounding per locale.

## Support adjustments

- Sign-language video, braille statements, extra time, phone support for accessibility needs.
# Component Hierarchy and Design System

## Component hierarchy

```
AppShell
├── AppBar / BottomTabBar
├── RouterOutlet
│   ├── HomeScreen (balance, quick actions, insights)
│   ├── AccountsScreen
│   ├── PaymentsScreen
│   ├── CardsScreen
│   ├── SaveInvestScreen
│   ├── BorrowScreen
│   ├── BusinessScreen
│   ├── InsightsScreen
│   ├── AssistantScreen
│   └── ProfileScreen
└── GlobalOverlays (modals, toasts, alerts)
```

## Design system

- **Foundations:** colour (WCAG AA contrast ≥4.5:1), typography (adjustable), spacing, motion (respects reduced-motion).
- **Components:** buttons, inputs, cards, lists, charts, sheets, alerts — all accessible (ARIA, keyboard, screen-reader).
- **Patterns:** forms, onboarding, empty/loading/error states, confirmation flows.
- **Theming:** light/dark, high-contrast; brand tokens.

## Accessibility (WCAG 2.2 AA)

- Semantic HTML/ARIA on web; accessibility props on native.
- Focus order, keyboard navigation, visible focus.
- Text scaling up to 200–400%; reflow at 400% zoom.
- Captions/audio for multi-sensory.
- Automated `axe-core` checks in CI + manual screen-reader testing.

## Consistency

One design system across mobile, web, and ops console; shared tokens and components.
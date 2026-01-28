# Triage Playbook

This playbook is for maintainers and triage engineers to standardize issue handling, labeling and prioritization.

## Goals
- Provide a consistent first response to new issues.
- Quickly identify bugs, regressions, security vulnerabilities, and feature requests.
- Ensure SLAs for acknowledgement and prioritization.

## Triage Steps
1. Acknowledge within 48 hours: Add a comment thanking the reporter and asking for missing information if needed.
2. Assign an owner: Add an assignee or the `triage` team depending on availability.
3. Add labels:
   - `type/bug` | `type/feature` | `type/docs` | `type/security` (internal only)
   - `severity/critical` | `severity/high` | `severity/medium` | `severity/low`
   - `needs-triage`, `good-first-issue`, `help-wanted`, `duplicate`, `wontfix`
4. Classify priority & milestone: Set `priority/urgent` for hotfixes or add to the next milestone for planned work.
5. Reproduce & gather logs: Ask for steps, environment, and relevant logs or attach failing CI artifacts.
6. Move to appropriate backlog column (project board) and add estimate if known.

## Security Issues
- **DO NOT** encourage or accept public issue threads for security vulnerabilities.
- If someone opens a public issue that looks security-related, politely request they use the private disclosure channel and reference [SECURITY.md](/SECURITY.md).

## Severity Definitions
| Label | Criteria |
| :--- | :--- |
| `sev/critical` | Security breach, data loss, or production outage. |
| `sev/high` | Major feature broken with no workaround. |
| `sev/medium` | Issue with a workaround; minor functional bug. |
| `sev/low` | UI/UX polish, typos, or documentation. |

## PR Triage
- Ensure PRs reference an issue when appropriate and include tests.
- Add `review/` labels and a `reviewer` assignment.
- For hotfix releases, add `cherry-pick` label and link to the release notes.

## Closing Criteria
- Verify fixes via CI and/or reproduction steps.
- Confirm with the original reporter when possible before closing.
- Mark duplicates as such and reference canonical issue.

## Useful Commands for Reporters
When requesting more info, ask the reporter to provide output from:
- **Build Logs:** `./gradlew build --stacktrace`
- **Test Failures:** `./gradlew test --info`
- **Dependency Tree:** `./gradlew dependencies`

## Notes
- Keep this document short and actionable; expand sections if the team adopts more formal SLAs.
- This file is intended for maintainers only and lives under `.github/docs/` to avoid seeing it as an issue template.

#!/usr/bin/env bash
set -euo pipefail

# Only run checks if Java/Kotlin files are staged
STAGED_FILES=$(git diff --cached --name-only --diff-filter=ACMR | grep -E '\.(java|kts)$' || true)
[ -z "$STAGED_FILES" ] && exit 0

echo "🛡️  Running Spotless check on staged files..."
./gradlew spotlessCheck --quiet || {
    echo ""
    echo "❌ Standards enforcement failed!"
    echo "Run './gradlew spotlessApply' to fix formatting, then re-stage your changes."
    exit 1
}

exit 0

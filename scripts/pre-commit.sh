#!/usr/bin/env bash
set -euo pipefail

# Grab staged Java/Kotlin files
STAGED_FILES=$(git diff --cached --name-only --diff-filter=ACMR | grep -E '\.(java|kts)$' || true)

# If no relevant files are staged, exit early
if [ -z "$STAGED_FILES" ]; then
    exit 0
fi

echo "🛡️  Formatting staged files with Spotless (AOSP)..."
# Format automatically
./gradlew spotlessApply --quiet

# Re-stage the files. 
echo "$STAGED_FILES" | xargs -r git add

# Final Verification
echo "🔍 Verifying compilation..."
./gradlew spotlessCheck testClasses --quiet

echo "✅ Standards verified. Committing..."

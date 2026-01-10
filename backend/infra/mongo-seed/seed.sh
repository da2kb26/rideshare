#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
SEED_FILE="$SCRIPT_DIR/seed.js"

# If devcontainer already started Mongo, just seed it directly.
if docker ps --format "{{.Names}}" | grep -q "^sar-mongo$"; then
  echo "Found running Mongo container: sar-mongo"
  docker exec -i sar-mongo mongosh < "$SEED_FILE"
  echo "Done."
  exit 0
fi

# Fallback: start mongo via the devcontainer compose file, then seed.
REPO_ROOT="$(cd "$SCRIPT_DIR/../.." && pwd)"
DEVCOMPOSE="$REPO_ROOT/backend/.devcontainer/docker-compose.yml"

echo "Mongo container not running. Starting via devcontainer compose: $DEVCOMPOSE"
docker compose -f "$DEVCOMPOSE" up -d mongo
docker exec -i sar-mongo mongosh < "$SEED_FILE"
echo "Done."
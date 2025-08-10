# scripts/validate-setup.sh
#!/bin/bash
echo "Validating Claude Code context management setup..."

# Check memory hierarchy
test -f CLAUDE.md && echo "✓ Project memory found" || echo "✗ Missing project memory"
test -f services/user-service/CLAUDE.md && echo "✓ Service memory found" || echo "✗ Missing service memory"

# Check key integrations
grep -q "@docs/" CLAUDE.md && echo "✓ Documentation imports working" || echo "✗ Missing doc imports"

# Validate agents
test -f .claude/agents/database-expert.md && echo "✓ Specialized agents configured" || echo "✗ Missing agents"

echo "Run: claude /demo-onboarding to test the complete workflow"
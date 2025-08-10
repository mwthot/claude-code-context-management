# Claude Code Context Management

This repository demonstrates practical patterns for managing Claude Code in enterprise environments. For detailed explanations, see the [full documentation](claude-code-context-guide.md).

## Quick Start

```bash
# Clone the repository
git clone https://github.com/your-username/claude-code-context-management
cd claude-code-context-management

# (Optional) Set up enterprise-level memory - see Enterprise Setup section below

# Navigate to mock repository

# Initialize Claude Code with project context
claude
```

## Enterprise Setup (Optional)

To demonstrate a full memory hierarchy, including enterprise-level context, you can install the sample enterprise CLAUDE.md file (sample-enterprise-level-memory/CLAUDE.md). This requires administrator/root permissions.

### Linux/WSL
```bash
# Create the directory and copy the enterprise memory file
sudo mkdir -p /etc/claude-code
sudo cp samples/enterprise-CLAUDE.md /etc/claude-code/CLAUDE.md

# Verify installation
ls -la /etc/claude-code/CLAUDE.md
```

### macOS
```bash
# Create the directory and copy the enterprise memory file
sudo mkdir -p "/Library/Application Support/ClaudeCode"
sudo cp samples/enterprise-CLAUDE.md "/Library/Application Support/ClaudeCode/CLAUDE.md"

# Verify installation
ls -la "/Library/Application Support/ClaudeCode/CLAUDE.md"
```

### Windows (PowerShell as Administrator)
```powershell
# Create the directory
New-Item -ItemType Directory -Force -Path "C:\ProgramData\ClaudeCode"

# Copy the enterprise memory file
Copy-Item "samples\enterprise-CLAUDE.md" -Destination "C:\ProgramData\ClaudeCode\CLAUDE.md"

# Verify installation
Get-ChildItem "C:\ProgramData\ClaudeCode\CLAUDE.md"
```

### What's in the Enterprise Memory?

The sample enterprise CLAUDE.md establishes organization-wide standards:
- Security requirements (OWASP compliance, data protection)
- Coding standards (formatting, naming conventions, testing coverage)
- Architectural principles (microservices patterns, API design)
- Compliance requirements (GDPR, SOC2, audit logging)

Once installed, these standards will influence all Claude Code sessions across any project on your machine. 

❗ **IMPORTANT**: Remember to remove the mock `CLAUDE.md` enterprise file when you're done with this demonstration. 

### Testing Enterprise Context

After installation, verify the hierarchy is working:

```bash
claude
> /memory
# You should see the enterprise memory file listed first

> What security standards should I follow?
# Claude should reference your enterprise security requirements
```

### Removing Enterprise Context

To remove enterprise context and test without it:

**Linux/WSL**: `sudo rm /etc/claude-code/CLAUDE.md`  
**macOS**: `sudo rm "/Library/Application Support/ClaudeCode/CLAUDE.md"`  
**Windows**: `Remove-Item "C:\ProgramData\ClaudeCode\CLAUDE.md"` (as Administrator)

## Mock Repository Structure

```
.
├── .claude/                    # Claude Code configurations
│   ├── agents/                 # Specialized subagents
│   │   ├── database-expert.md
│   │   └── security-reviewer.md
│   ├── commands/               # Custom slash commands
│   │   ├── map-dependencies.md
│   │   ├── onboard-developer.md
│   │   └── demo-onboarding.md
│   ├── settings.json           # Project-wide Claude settings
│   └── settings.local.json     # Local developer overrides
│
├── samples/                    # Sample configuration files
│   └── enterprise-CLAUDE.md    # Enterprise-level memory template
│
├── services/                  # Mock microservices architecture
│   ├── user-service/          # Java/Spring Boot service
│   │   ├── .claude/           # Service-specific context
│   ├── order-service/         # Demonstrates cross-service patterns
│   │   ├── .claude/           # Service-specific context
│
├── docs/                      # Architecture documentation
│   ├── architecture-overview.md
│   ├── service-dependencies.md
│   └── development-workflow.md
│
└── CLAUDE.md                  # Project-level memory file
```

## Key Demonstrations

### Memory Hierarchy

Explore how context flows from enterprise to service level:

```bash
# See all loaded memory files (including enterprise if installed)
claude
> /memory

# Test enterprise context influence
> What are our organization's security requirements?
# (Will reference enterprise standards if installed)

# Watch context inheritance
> @services/user-service/CLAUDE.md
> What patterns should I follow?
# (Combines enterprise, project, and service contexts)
```

### Dependency Mapping

Generate a comprehensive dependency map in JSON and Mermaid:

```bash
# Use the custom command
> /map-dependencies

# Results saved to:
# - .claude/dependency-map.json
# - .claude/dependency-diagram.md
```

### Subagent Delegation

See specialized agents in action:

```bash
# Database analysis
> Use the database-expert subagent to analyze the order service schema

# Security review
> Use the security-reviewer subagent to audit the user validation service
```

### Cross-Service Development

Work across service boundaries effectively:

```bash
# Load multiple service contexts
> @services/user-service/src/main/java/domain/User.java
> @services/order-service/src/main/java/service/UserValidationService.java
> How do these services integrate?
```

## Configuration Examples

### Add Your Own Subagent
1. Create `.claude/agents/your-agent.md`
2. Run `/agents` to see it listed
3. Delegate work: `Use the your-agent subagent to...`

### Create a Custom Command
1. Add a file to `.claude/commands/your-command.md`
2. Use it with `/your-command`

## Learn More

- [Claude Code Context Management](/claude-code-context-guide.md) for detailed explanations
- [Claude Code Documentation](https://docs.anthropic.com/en/docs/claude-code)



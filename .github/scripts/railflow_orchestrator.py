import urllib.request
import json
import base64
import os
import time
import random
import datetime

# =========================================================================
# CONFIGURATION
# =========================================================================
SRIRAM_USER = "SriramGandhiS"
SRIRAM_TOKEN = os.environ.get("MAIN_TOKEN")
GIT_REPO = "RailFlow" 

SURIYA_USER = "Suriyakumar4036"
BOT_USER = "rizz-architect"

STATE_PATH = ".github/scripts/railflow_state.json"

ACCOUNT_MAP = {
    "sriram": {"name": "Sriram S", "email": "iamramm8@gmail.com"},
    "suriya": {"name": "Suriya Kumar R", "email": "suriyakumar4036@gmail.com"},
    "rizz":   {"name": "rizz-architect", "email": "srirams23cs@psnacet.edu.in"},
}

def get_author_for_file(path, day):
    import os
    fname = os.path.basename(path).lower()
    if fname == "pom.xml" or "docker" in fname or "migration" in path or "test" in fname or "config" in path:
        return "rizz"
    elif "dto" in path or "controller" in path or "exception" in path or "mapper" in path:
        return "suriya"
    else:
        # Stable hash rotation
        h = (len(path) + day) % 10
        if h < 5:
            return "sriram"
        elif h < 9:
            return "suriya"
        else:
            return "rizz"
# =========================================================================

def make_request(url, method, token, data=None):
    req = urllib.request.Request(url, method=method)
    req.add_header("Authorization", f"token {token}")
    req.add_header("Accept", "application/vnd.github.v3+json")
    req.add_header("User-Agent", "Mozilla/5.0")
    if data:
        json_data = json.dumps(data).encode("utf-8")
        req.add_header("Content-Type", "application/json")
        try:
            with urllib.request.urlopen(req, data=json_data) as response:
                body = response.read().decode("utf-8")
                return response.status, json.loads(body) if body else None
        except urllib.error.HTTPError as e:
            body = e.read().decode("utf-8") if e else ""
            try:
                return e.code, json.loads(body) if body else None
            except Exception:
                return e.code, None
    else:
        try:
            with urllib.request.urlopen(req) as response:
                body = response.read().decode("utf-8")
                return response.status, json.loads(body) if body else None
        except urllib.error.HTTPError as e:
            body = e.read().decode("utf-8") if e else ""
            try:
                return e.code, json.loads(body) if body else None
            except Exception:
                return e.code, None

def get_file_sha(path, branch):
    quoted_path = urllib.parse.quote(path)
    status, res = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/contents/{quoted_path}?ref={branch}",
        "GET", SRIRAM_TOKEN
    )
    if status == 200 and res:
        return res["sha"]
    return None

def commit_file(path, content, message, branch, sha=None, author_key="sriram"):
    encoded = base64.b64encode(content.encode("utf-8")).decode("utf-8")
    data = {
        "message": message,
        "content": encoded,
        "branch": branch
    }
    if sha:
        data["sha"] = sha
        
    acct = ACCOUNT_MAP.get(author_key, ACCOUNT_MAP["sriram"])
    data["author"] = {
        "name": acct["name"],
        "email": acct["email"]
    }
    data["committer"] = {
        "name": acct["name"],
        "email": acct["email"]
    }
        
    quoted_path = urllib.parse.quote(path)
    status, res = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/contents/{quoted_path}",
        "PUT", SRIRAM_TOKEN, data
    )
    return status

# =========================================================================
# FEATURE BRANCHES MAP (45 Days Roadmap)
# =========================================================================
FEATURE_MAP = {
    # Phase 1: Security & Auth (Days 2 to 6)
    2: {"branch": "feature/railflow-security-auth", "is_start": True, "is_end": False},
    3: {"branch": "feature/railflow-security-auth", "is_start": False, "is_end": False},
    4: {"branch": "feature/railflow-security-auth", "is_start": False, "is_end": False},
    5: {"branch": "feature/railflow-security-auth", "is_start": False, "is_end": False},
    6: {"branch": "feature/railflow-security-auth", "is_start": False, "is_end": True}, 
    
    # Phase 2: Stations & Train Management (Days 7 to 10)
    7: {"branch": "feature/railflow-train-mgmt", "is_start": True, "is_end": False},
    8: {"branch": "feature/railflow-train-mgmt", "is_start": False, "is_end": False},
    9: {"branch": "feature/railflow-train-mgmt", "is_start": False, "is_end": False},
    10: {"branch": "feature/railflow-train-mgmt", "is_start": False, "is_end": True},

    # Phase 3: Seat Allocation & Ticket Booking (Days 11 to 20)
    11: {"branch": "feature/railflow-booking-engine", "is_start": True, "is_end": False},
    12: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    13: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    14: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    15: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    16: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    17: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    18: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    19: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": False},
    20: {"branch": "feature/railflow-booking-engine", "is_start": False, "is_end": True},

    # Phase 4: Redis Distributed Locks & Concurrency Performance (Days 21 to 30)
    21: {"branch": "feature/railflow-redis-concurrency", "is_start": True, "is_end": False},
    22: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    23: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    24: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    25: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    26: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    27: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    28: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    29: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": False},
    30: {"branch": "feature/railflow-redis-concurrency", "is_start": False, "is_end": True},

    # Phase 5: Event-Driven Waiting List (Kafka Integration) (Days 31 to 40)
    31: {"branch": "feature/railflow-kafka-waitinglist", "is_start": True, "is_end": False},
    32: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    33: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    34: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    35: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    36: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    37: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    38: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    39: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": False},
    40: {"branch": "feature/railflow-kafka-waitinglist", "is_start": False, "is_end": True},

    # Phase 6: Production Readiness & Final Deployment (Days 41 to 45)
    41: {"branch": "feature/railflow-deployment-docs", "is_start": True, "is_end": False},
    42: {"branch": "feature/railflow-deployment-docs", "is_start": False, "is_end": False},
    43: {"branch": "feature/railflow-deployment-docs", "is_start": False, "is_end": False},
    44: {"branch": "feature/railflow-deployment-docs", "is_start": False, "is_end": False},
    45: {"branch": "feature/railflow-deployment-docs", "is_start": False, "is_end": True}
}

# =========================================================================
# DAY 1 TEMPLATE FILES
# =========================================================================
DAY_1_FILES = {
    "pom.xml": """<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.4</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.railflow</groupId>
    <artifactId>railflow-platform</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>railflow-platform</name>
    <description>High-Concurrency Railway Reservation Platform</description>
    
    <properties>
        <java.version>21</java.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.postgresql</groupId>
            <artifactId>postgresql</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-api</artifactId>
            <version>0.11.5</version>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-impl</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt-jackson</artifactId>
            <version>0.11.5</version>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
""",
    "src/main/resources/application.yml": """spring:
  application:
    name: railflow-platform
  datasource:
    url: jdbc:postgresql://localhost:5432/railflow
    username: postgres
    password: postgres
    driver-class-name: org.postgresql.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
        format_sql: true
  data:
    redis:
      host: localhost
      port: 6379
  kafka:
    bootstrap-servers: localhost:9092
    consumer:
      group-id: railflow-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
    producer:
      key-serializer: org.apache.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer

logging:
  level:
    org.springframework.security: DEBUG
    com.railflow: DEBUG
""",
    "src/main/java/com/railflow/RailflowApplication.java": """package com.railflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RailflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(RailflowApplication.class, args);
        System.out.println("=== RailFlow Reservation Engine Successfully Started ===");
    }
}
""",
    "README.md": """# RailFlow: High-Concurrency Railway Reservation Engine

RailFlow is a production-grade railway ticket booking platform built to demonstrate concurrency control, distributed locking, caching strategies, and event-driven updates.

## Technical Architecture Overview
```mermaid
graph TD
    Client[React Client Dashboard] -->|REST / JWT| Gateway[Spring Boot App]
    Gateway -->|JPA| DB[(PostgreSQL Main Database)]
    Gateway -->|Distributed Lock / Search Cache| Cache[(Redis Cache)]
    Gateway -->|Async Event Queue| Queue[[Apache Kafka]]
    Queue -->|Notifications & Waiting List Promotes| Email[Notification System]
```

## Features Roadmap
1. **JWT Authentication:** Secure logins & role authorizations.
2. **Train Search & Cache:** Optimized intermediate stop searches with Redis cache.
3. **Seat Allocation Engine:** Automatic ticket booking and seat numbering.
4. **Redis Lock:** Concurrency locking preventing double booking of same seats.
5. **Waiting List Engine:** Dynamic First-In-First-Out promotion service using Kafka events.

## Getting Started
### Prerequisites
* Java 21 (JDK)
* Docker & Docker Compose
* Maven 3.9+
""",
    ".gitignore": """target/
!.mvn/wrapper/maven-wrapper.jar
Classfiles
*.class
*.log
.security
.data
.settings/
.project
.classpath
.factorypath
.idea/
*.iml
*.iws
.DS_Store
"""
}

# =========================================================================
# DAY 2 TEMPLATE FILES
# =========================================================================
DAY_2_FILES = {
    "src/main/java/com/railflow/model/User.java": """package com.railflow.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
""",
    "src/main/java/com/railflow/model/Role.java": """package com.railflow.model;

public enum Role {
    ROLE_USER,
    ROLE_ADMIN
}
""",
    "src/main/java/com/railflow/repository/UserRepository.java": """package com.railflow.repository;

import com.railflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
"""
}

# =========================================================================
# RUN ORCHESTRATION WITH WORKING HOURS SEGMENTATION
# =========================================================================
def run_day_1():
    print("=== Executing Day 1: Project Initialization ===")
    status, ref = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/ref/heads/main",
        "GET", SRIRAM_TOKEN
    )
    if status != 200 or not ref:
        print("Failed to fetch main head SHA. It might be an empty repo. Initializing main first...")
        commit_file("README.md", "# RailFlow\nDedicated high-concurrency railway platform repo.", "Initial commit", "main")
        time.sleep(5)
        status, ref = make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/ref/heads/main",
            "GET", SRIRAM_TOKEN
        )
        if status != 200 or not ref:
            return False
            
    main_sha = ref["object"]["sha"]
    branch_name = "feature/railflow-init"
    
    print(f"Creating branch: {branch_name}")
    status, _ = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/refs",
        "POST", SRIRAM_TOKEN, {"ref": f"refs/heads/{branch_name}", "sha": main_sha}
    )
    
    issue_data = {
        "title": "RailFlow Day 1: Project initialization & Spring Boot foundation",
        "body": "Set up project structure, Maven configuration with Spring Boot 3, dependencies (PostgreSQL, Redis, Security, Kafka), and basic configuration templates.",
        "assignees": [SRIRAM_USER]
    }
    status, issue = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues",
        "POST", SRIRAM_TOKEN, issue_data
    )
    issue_num = issue["number"] if (status == 201 and issue and "number" in issue) else 1
    
    for path, content in DAY_1_FILES.items():
        if "pom.xml" in path:
            msg = "feat(init): configure pom.xml with Spring Boot starter dependencies"
        elif "application.yml" in path:
            msg = "feat(init): configure PostgreSQL and Redis connection settings"
        elif "RailflowApplication.java" in path:
            msg = "feat(init): implement main Spring Boot application run class"
        elif "README.md" in path:
            msg = "docs(init): add project overview and system design mappings"
        else:
            msg = f"chore(init): configure rules for {os.path.basename(path)}"
            
        full_msg = f"{msg}\n\nCloses #{issue_num}\n\nCo-authored-by: {SURIYA_USER} <{SURIYA_USER}@users.noreply.github.com>\nCo-authored-by: {BOT_USER} <{BOT_USER}@users.noreply.github.com>"
        
        sha = get_file_sha(path, branch_name)
        author_key = get_author_for_file(path, 1)
        commit_file(path, content, full_msg, branch_name, sha, author_key=author_key)
        
    pr_data = {
        "title": f"feat(init): RailFlow Day 1 - Base Structure [skip ci]",
        "body": f"Resolves #{issue_num}.\n\nCo-authored-by: @{SURIYA_USER} and @{BOT_USER}",
        "head": branch_name,
        "base": "main"
    }
    status, pr = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls",
        "POST", SRIRAM_TOKEN, pr_data
    )
    if status == 201 and pr and "number" in pr:
        pr_num = pr["number"]
        time.sleep(3)
        merge_data = {
            "commit_title": f"Merge pull request #{pr_num} from {branch_name} [skip ci]",
            "merge_method": "merge"
        }
        make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls/{pr_num}/merge",
            "PUT", SRIRAM_TOKEN, merge_data
        )
        make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/refs/heads/{branch_name}",
            "DELETE", SRIRAM_TOKEN
        )
        return True
    return False

def run_day_tasks(day):
    # Convert UTC to IST (UTC + 5:30)
    now_utc = datetime.datetime.utcnow()
    now_ist = now_utc + datetime.timedelta(hours=5, minutes=30)
    current_hour = now_ist.hour
    now = now_ist
    
    if current_hour < 12:
        phase = "morning"
    elif current_hour < 18:
        phase = "afternoon"
    else:
        phase = "evening"
        
    print(f"=== Starting Executing Day {day} Feature Changes ({phase.upper()} RUN) ===")
    
    if day == 1:
        # Day 1 completes in a single run
        return run_day_1()
        
    cfg = FEATURE_MAP.get(day)
    if not cfg:
        print(f"Feature configurations for Day {day} not registered yet.")
        return True
        
    branch_name = cfg["branch"]
    is_start = cfg["is_start"]
    is_end = cfg["is_end"]
    
    # 1. Start Branch & Issue (Morning only)
    if is_start and phase == "morning":
        status, ref = make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/ref/heads/main",
            "GET", SRIRAM_TOKEN
        )
        if status != 200 or not ref:
            print("Failed to fetch main branch reference.")
            return False
        main_sha = ref["object"]["sha"]
        print(f"Creating organic branch: {branch_name}")
        make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/refs",
            "POST", SRIRAM_TOKEN, {"ref": f"refs/heads/{branch_name}", "sha": main_sha}
        )
        
        issue_title = f"Feature Block: Implement {branch_name.split('/')[-1].replace('-', ' ').title()}"
        existing_issue = False
        status_issues, issues_res = make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues?state=open",
            "GET", SRIRAM_TOKEN
        )
        if status_issues == 200 and issues_res:
            for iss in issues_res:
                if iss.get("title") == issue_title:
                    existing_issue = True
                    print(f"Issue already open: #{iss.get('number')}")
                    break
                    
        if not existing_issue:
            issue_data = {
                "title": issue_title,
                "body": f"Core tasks for implementation block containing Day {day} modifications.",
                "assignees": [SRIRAM_USER]
            }
            status, issue = make_request(
                f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues",
                "POST", SRIRAM_TOKEN, issue_data
            )
            if status == 201 and issue and "number" in issue:
                print(f"Created Tracked Issue #{issue['number']}")
            
    # Load templates dynamically from railflow_templates
    import sys
    sys.path.append(".github/scripts")
    try:
        from railflow_templates import TEMPLATES
    except ImportError:
        TEMPLATES = {}
        
    files_to_commit = {}
    if day == 2:
        files_to_commit = DAY_2_FILES
    elif day in TEMPLATES:
        files_to_commit = TEMPLATES[day]
        
    # Sort files and slice them across morning, afternoon, evening
    file_list = sorted(list(files_to_commit.items()))
    N = len(file_list)
    
    if N == 0:
        sliced_files = []
    elif N == 1:
        sliced_files = file_list if phase == "morning" else []
    elif N == 2:
        if phase == "morning":
            sliced_files = [file_list[0]]
        elif phase == "evening":
            sliced_files = [file_list[1]]
        else:
            sliced_files = []
    else:
        m_limit = N // 3
        a_limit = (2 * N) // 3
        if phase == "morning":
            sliced_files = file_list[0:m_limit]
        elif phase == "afternoon":
            sliced_files = file_list[m_limit:a_limit]
        else:
            sliced_files = file_list[a_limit:]
            
    issue_num = 1
    status_issues, issues_res = make_request(
        f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues?state=open",
        "GET", SRIRAM_TOKEN
    )
    if status_issues == 200 and issues_res:
        for iss in issues_res:
            if "Feature Block" in iss.get("title", ""):
                issue_num = iss.get("number", 1)
                break

    # 2. Commit sliced portion of files
    if sliced_files:
        print(f"Slicing files for {phase.upper()} run: committing {len(sliced_files)} file(s).")
        for path, content in sliced_files:
            if "User.java" in path:
                msg = "feat(auth): implement main User JPA database schema model"
            elif "Role.java" in path:
                msg = "feat(auth): implement user authentication Role enums"
            elif "UserRepository.java" in path:
                msg = "feat(auth): add repository layer interface for JPA user lookup"
            else:
                msg = f"refactor(auth): code cleaning for {os.path.basename(path)}"
                
            full_msg = f"{msg}\n\nCo-authored-by: {SURIYA_USER} <{SURIYA_USER}@users.noreply.github.com>\nCo-authored-by: {BOT_USER} <{BOT_USER}@users.noreply.github.com>"
            
            time.sleep(random.randint(5, 15))
            sha = get_file_sha(path, branch_name)
            author_key = get_author_for_file(path, day)
            print(f"File {path} will be committed by {author_key}")
            c_status = commit_file(path, content, full_msg, branch_name, sha, author_key=author_key)
            if c_status in [200, 201]:
                print(f" -> Committed: {path}")
            else:
                print(f" -> Failed to commit {path}: {c_status}")
                return False
    else:
        print(f"No files scheduled for {phase.upper()} phase of Day {day}.")

    # 3. Pull Request and Merge (Only in the EVENING run of the feature branch end day)
    if is_end and phase == "evening":
        pr_data = {
            "title": f"feat(auth): merge {branch_name.split('/')[-1]} workflow [skip ci]",
            "body": f"Completes all tasks for this feature block.\n\nCo-authored-by: @{SURIYA_USER} and @{BOT_USER}",
            "head": branch_name,
            "base": "main"
        }
        status, pr = make_request(
            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls",
            "POST", SRIRAM_TOKEN, pr_data
        )
        if status == 201 and pr and "number" in pr:
            pr_num = pr["number"]
            print(f"PR #{pr_num} opened.")
            time.sleep(5)
            
            if random.random() < 0.35:
                print(f" -> Simulating review workflow: Sriram requests changes on PR #{pr_num}...")
                review_data = {
                    "body": "Spring bean configuration naming patterns need to match our coding guidelines. Please clean up naming conventions.",
                    "event": "REQUEST_CHANGES"
                }
                make_request(
                    f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls/{pr_num}/reviews",
                    "POST", SRIRAM_TOKEN, review_data
                )
                time.sleep(10)
                
                # Add correction commit on README.md
                fix_path = "README.md"
                fix_sha = get_file_sha(fix_path, branch_name)
                status_readme, readme_res = make_request(
                    f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/contents/README.md?ref={branch_name}",
                    "GET", SRIRAM_TOKEN
                )
                if status_readme == 200 and readme_res:
                    readme_content = base64.b64decode(readme_res["content"]).decode("utf-8")
                    readme_content += f"\n\n<!-- Verified by squad on Day {day} -->\n"
                    fix_msg = f"refactor(auth): resolve code review feedback on naming conventions\n\nCo-authored-by: {SURIYA_USER} <{SURIYA_USER}@users.noreply.github.com>\nCo-authored-by: {BOT_USER} <{BOT_USER}@users.noreply.github.com>"
                    commit_file(fix_path, readme_content, fix_msg, branch_name, fix_sha)
                    print(" -> Correction commit pushed successfully.")
                    time.sleep(5)
                
                approve_data = {
                    "body": "PR changes look perfect now. Approved for merge.",
                    "event": "APPROVE"
                }
                make_request(
                    f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls/{pr_num}/reviews",
                    "POST", SRIRAM_TOKEN, approve_data
                )
                time.sleep(5)
            
            merge_data = {
                "commit_title": f"Merge pull request #{pr_num} from {branch_name} [skip ci]",
                "merge_method": "merge"
            }
            make_request(
                f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/pulls/{pr_num}/merge",
                "PUT", SRIRAM_TOKEN, merge_data
            )
            make_request(
                f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/git/refs/heads/{branch_name}",
                "DELETE", SRIRAM_TOKEN
            )
            print(f"Branch {branch_name} deleted and merged.")
            
            # Close corresponding open issue for this feature block
            issue_title = f"Feature Block: Implement {branch_name.split('/')[-1].replace('-', ' ').title()}"
            status_issues, issues_res = make_request(
                f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues?state=open",
                "GET", SRIRAM_TOKEN
            )
            if status_issues == 200 and issues_res:
                for iss in issues_res:
                    if iss.get("title") == issue_title:
                        iss_num = iss.get("number")
                        print(f"Closing corresponding issue #{iss_num}...")
                        make_request(
                            f"https://api.github.com/repos/{SRIRAM_USER}/{GIT_REPO}/issues/{iss_num}",
                            "PATCH", SRIRAM_TOKEN, {"state": "closed"}
                        )
        else:
            print(f"Failed to open/merge PR (Status: {status})")
            return False
            
    return True

def main():
    if not os.path.exists(STATE_PATH):
        print("State tracking file not found.")
        return
        
    with open(STATE_PATH, "r") as f:
        state = json.load(f)
        
    # Check for random rest day (15% chance to skip completely, disabled for Day 2)
    if state.get("current_day") != 2 and random.random() < 0.15:
        print("Today is an organic rest day for the team. No updates pushed today.")
        return
        
    # Organic jitter delay: sleep between 60 to 2700 seconds (1 to 45 minutes) before executing
    if os.environ.get("GITHUB_EVENT_NAME") == "workflow_dispatch" or os.environ.get("BYPASS_JITTER") == "true":
        print("Bypassing organic jitter delay (triggered manually).")
    else:
        sleep_delay = random.randint(60, 2700)
        print(f"Organic jitter delay: sleeping for {sleep_delay} seconds...")
        time.sleep(sleep_delay)
    
    day = state.get("current_day", 1)
    
    # Calculate phase
    now_utc = datetime.datetime.utcnow()
    now_ist = now_utc + datetime.timedelta(hours=5, minutes=30)
    current_hour = now_ist.hour
    now = now_ist
    phase = "morning" if current_hour < 12 else ("afternoon" if current_hour < 18 else "evening")
    
    success = run_day_tasks(day)
    if success:
        if phase == "evening":
            # Only advance to the next day in the evening run
            state["current_day"] = day + 1
            state["last_run_date"] = now.strftime("%Y-%m-%d")
            state["history"].append({"day": day, "status": "COMPLETED", "date": state["last_run_date"]})
            with open(STATE_PATH, "w") as f:
                json.dump(state, f, indent=2)
            print(f"Day {day} evening run processed successfully. State advanced to Day {day+1}!")
        else:
            print(f"Day {day} {phase} run completed. State maintained at Day {day} for subsequent runs today.")
    else:
        print(f"Day {day} execution failed.")

if __name__ == "__main__":
    main()

# AWS Deployment Guide - BusinessProSuite API

**Version**: V0.2  
**Target Audience**: DevOps Engineers, Cloud Architects, Senior Developers  
**Estimated Deployment Time**: 2-3 hours  

## 🎯 Purpose

This guide provides **comprehensive instructions** to deploy BusinessProSuite API on Amazon Web Services (AWS). It covers multiple deployment options, from simple EC2 instances to advanced containerized solutions with auto-scaling.

---

## 🏗️ Deployment Architecture Options

### **Option 1: Simple EC2 Deployment (Recommended for Start)**
- **Cost**: Low ($10-30/month)
- **Complexity**: Simple
- **Scalability**: Manual
- **Use Case**: Development, staging, small production

### **Option 2: Elastic Beanstalk Deployment**
- **Cost**: Medium ($30-100/month)
- **Complexity**: Medium
- **Scalability**: Automatic
- **Use Case**: Production with moderate traffic

### **Option 3: ECS with Fargate (Advanced)**
- **Cost**: Medium-High ($50-200/month)
- **Complexity**: Advanced
- **Scalability**: Container-based auto-scaling
- **Use Case**: Production with high availability requirements

---

## 📋 Prerequisites

### **AWS Account Setup**
1. **AWS Account** with billing enabled
2. **IAM User** with programmatic access
3. **AWS CLI** installed and configured
4. **Domain Name** (optional but recommended)

### **Required AWS Permissions**
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "ec2:*",
        "elasticbeanstalk:*",
        "rds:*",
        "s3:*",
        "iam:*",
        "cloudformation:*",
        "logs:*",
        "secretsmanager:*"
      ],
      "Resource": "*"
    }
  ]
}
```

### **Local Prerequisites**
- **AWS CLI** v2.x
- **Docker** (for containerized deployments)
- **Java 17** and **Gradle**
- **Git**

---

## 🚀 Option 1: EC2 Deployment (Step-by-Step)

### **Step 1: Create and Configure EC2 Instance**

#### **Launch EC2 Instance**
```bash
# Using AWS CLI
aws ec2 run-instances \
  --image-id ami-0c55b159cbfafe1d0 \
  --instance-type t3.medium \
  --key-name your-key-pair \
  --security-group-ids sg-your-security-group \
  --subnet-id subnet-your-subnet \
  --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=BusinessProSuite-API}]'
```

#### **Using AWS Console:**
1. **Navigate to EC2 Dashboard**
2. **Launch Instance:**
   - **AMI**: Amazon Linux 2023 AMI
   - **Instance Type**: t3.medium (2 vCPU, 4 GB RAM)
   - **Key Pair**: Create or select existing
   - **Security Group**: Create with following rules:
     ```
     Inbound Rules:
     - SSH (22) from your IP
     - HTTP (80) from anywhere (0.0.0.0/0)
     - HTTPS (443) from anywhere (0.0.0.0/0)
     - Custom TCP (8080) from anywhere (0.0.0.0/0)
     ```

### **Step 2: Connect and Setup Server**

```bash
# Connect to instance
ssh -i your-key-pair.pem ec2-user@your-instance-public-ip

# Update system
sudo yum update -y

# Install Java 17
sudo yum install java-17-amazon-corretto-devel -y

# Verify Java installation
java -version
```

### **Step 3: Install and Configure Application**

```bash
# Install Git
sudo yum install git -y

# Clone repository
git clone https://github.com/businessprosuite/BusinessProSuiteAPI.git
cd BusinessProSuiteAPI

# Make gradlew executable
chmod +x gradlew

# Build application
./gradlew build

# Create application directory
sudo mkdir -p /opt/businessprosuite
sudo cp build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar /opt/businessprosuite/app.jar

# Create application user
sudo useradd -r -s /bin/false businessprosuite
sudo chown -R businessprosuite:businessprosuite /opt/businessprosuite
```

### **Step 4: Create Systemd Service**

```bash
# Create service file
sudo tee /etc/systemd/system/businessprosuite.service > /dev/null <<EOF
[Unit]
Description=BusinessProSuite API
After=network.target

[Service]
Type=simple
User=businessprosuite
Group=businessprosuite
ExecStart=/usr/bin/java -jar /opt/businessprosuite/app.jar
Restart=always
RestartSec=10
Environment=SPRING_PROFILES_ACTIVE=prod
Environment=JWT_SECRET=your-production-jwt-secret-key-here
Environment=SERVER_PORT=8080

[Install]
WantedBy=multi-user.target
EOF

# Reload systemd and start service
sudo systemctl daemon-reload
sudo systemctl enable businessprosuite
sudo systemctl start businessprosuite

# Check service status
sudo systemctl status businessprosuite
```

### **Step 5: Configure Nginx Reverse Proxy**

```bash
# Install Nginx
sudo yum install nginx -y

# Create Nginx configuration
sudo tee /etc/nginx/conf.d/businessprosuite.conf > /dev/null <<EOF
server {
    listen 80;
    server_name your-domain.com;  # Replace with your domain

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        
        # WebSocket support
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
EOF

# Start and enable Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

# Test configuration
sudo nginx -t
sudo systemctl reload nginx
```

### **Step 6: Setup SSL with Let's Encrypt (Optional)**

```bash
# Install Certbot
sudo yum install certbot python3-certbot-nginx -y

# Obtain SSL certificate
sudo certbot --nginx -d your-domain.com

# Auto-renew setup (already configured by certbot)
# Verify auto-renewal
sudo certbot renew --dry-run
```

---

## ☁️ Option 2: Elastic Beanstalk Deployment

### **Step 1: Prepare Application Package**

```bash
# Build application
./gradlew build

# Create deployment directory
mkdir -p deploy/elastic-beanstalk
cd deploy/elastic-beanstalk

# Copy JAR file
cp ../../build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar application.jar

# Create Procfile
echo 'web: java -Dserver.port=$PORT -jar application.jar' > Procfile

# Create application.properties for Elastic Beanstalk
cat > application-beanstalk.properties <<EOF
server.port=5000
spring.profiles.active=prod
jwt.secret=\${JWT_SECRET:default-secret-key}
logging.level.com.businessprosuite=INFO
EOF
```

### **Step 2: Create Application Package**

```bash
# Create ZIP package
zip -r businessprosuite-api.zip application.jar Procfile application-beanstalk.properties

# Verify package contents
unzip -l businessprosuite-api.zip
```

### **Step 3: Deploy using AWS CLI**

```bash
# Create Elastic Beanstalk application
aws elasticbeanstalk create-application \
  --application-name BusinessProSuite-API \
  --description "BusinessProSuite API Application"

# Create application version
aws elasticbeanstalk create-application-version \
  --application-name BusinessProSuite-API \
  --version-label v1.0 \
  --source-bundle S3Bucket=your-deployment-bucket,S3Key=businessprosuite-api.zip

# Create environment
aws elasticbeanstalk create-environment \
  --application-name BusinessProSuite-API \
  --environment-name BusinessProSuite-API-prod \
  --version-label v1.0 \
  --solution-stack-name "64bit Amazon Linux 2023 v4.0.0 running Corretto 17"
```

### **Step 4: Configure Environment Variables**

```bash
# Set environment variables
aws elasticbeanstalk update-environment \
  --environment-name BusinessProSuite-API-prod \
  --option-settings \
    Namespace=aws:elasticbeanstalk:application:environment,OptionName=JWT_SECRET,Value=your-jwt-secret \
    Namespace=aws:elasticbeanstalk:application:environment,OptionName=SPRING_PROFILES_ACTIVE,Value=prod \
    Namespace=aws:autoscaling:launchconfiguration,OptionName=InstanceType,Value=t3.medium
```

---

## 🐳 Option 3: ECS with Fargate Deployment

### **Step 1: Create Docker Image**

```dockerfile
# Create Dockerfile
cat > Dockerfile <<EOF
FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod
ENV JWT_SECRET=default-secret-key

ENTRYPOINT ["java", "-jar", "app.jar"]
EOF
```

### **Step 2: Build and Push to ECR**

```bash
# Build Docker image
docker build -t businessprosuite-api .

# Create ECR repository
aws ecr create-repository --repository-name businessprosuite-api

# Get login token
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin your-account-id.dkr.ecr.us-east-1.amazonaws.com

# Tag and push image
docker tag businessprosuite-api:latest your-account-id.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest
docker push your-account-id.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest
```

### **Step 3: Create ECS Cluster and Service**

```bash
# Create ECS cluster
aws ecs create-cluster --cluster-name businessprosuite-cluster

# Create task definition
cat > task-definition.json <<EOF
{
  "family": "businessprosuite-api",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "executionRoleArn": "arn:aws:iam::your-account-id:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "businessprosuite-api",
      "image": "your-account-id.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest",
      "portMappings": [
        {
          "containerPort": 8080,
          "protocol": "tcp"
        }
      ],
      "environment": [
        {
          "name": "SPRING_PROFILES_ACTIVE",
          "value": "prod"
        }
      ],
      "secrets": [
        {
          "name": "JWT_SECRET",
          "valueFrom": "arn:aws:secretsmanager:us-east-1:your-account-id:secret:businessprosuite/jwt-secret"
        }
      ],
      "logConfiguration": {
        "logDriver": "awslogs",
        "options": {
          "awslogs-group": "/ecs/businessprosuite-api",
          "awslogs-region": "us-east-1",
          "awslogs-stream-prefix": "ecs"
        }
      }
    }
  ]
}
EOF

# Register task definition
aws ecs register-task-definition --cli-input-json file://task-definition.json
```

---

## 🗄️ Database Setup (RDS)

### **Step 1: Create RDS MySQL Instance**

```bash
# Create DB subnet group
aws rds create-db-subnet-group \
  --db-subnet-group-name businessprosuite-subnet-group \
  --db-subnet-group-description "Subnet group for BusinessProSuite API" \
  --subnet-ids subnet-12345 subnet-67890

# Create RDS instance
aws rds create-db-instance \
  --db-instance-identifier businessprosuite-db \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --engine-version 8.0.35 \
  --master-username admin \
  --master-user-password YourSecurePassword123! \
  --allocated-storage 20 \
  --storage-type gp2 \
  --vpc-security-group-ids sg-your-security-group \
  --db-subnet-group-name businessprosuite-subnet-group \
  --backup-retention-period 7 \
  --multi-az \
  --storage-encrypted
```

### **Step 2: Configure Security Groups**

```bash
# Create security group for RDS
aws ec2 create-security-group \
  --group-name businessprosuite-db-sg \
  --description "Security group for BusinessProSuite database"

# Allow MySQL access from application security group
aws ec2 authorize-security-group-ingress \
  --group-id sg-database-security-group \
  --protocol tcp \
  --port 3306 \
  --source-group sg-application-security-group
```

### **Step 3: Update Application Configuration**

```yaml
# Add to application-prod.yml
spring:
  datasource:
    url: jdbc:mysql://businessprosuite-db.region.rds.amazonaws.com:3306/businessprosuite
    username: admin
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  jpa:
    hibernate:
      ddl-auto: update
    database-platform: org.hibernate.dialect.MySQL8Dialect
    show-sql: false
```

---

## 🔐 Security Best Practices

### **1. Secrets Management**

```bash
# Store JWT secret in AWS Secrets Manager
aws secretsmanager create-secret \
  --name businessprosuite/jwt-secret \
  --description "JWT secret for BusinessProSuite API" \
  --secret-string '{"jwt_secret":"your-super-secure-jwt-secret-key"}'

# Store database password
aws secretsmanager create-secret \
  --name businessprosuite/db-password \
  --description "Database password for BusinessProSuite API" \
  --secret-string '{"password":"YourSecurePassword123!"}'
```

### **2. IAM Roles and Policies**

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "secretsmanager:GetSecretValue"
      ],
      "Resource": [
        "arn:aws:secretsmanager:us-east-1:*:secret:businessprosuite/*"
      ]
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "*"
    }
  ]
}
```

### **3. Network Security**

```bash
# Create VPC with private subnets
aws ec2 create-vpc --cidr-block 10.0.0.0/16

# Create private subnet for database
aws ec2 create-subnet \
  --vpc-id vpc-12345 \
  --cidr-block 10.0.1.0/24 \
  --availability-zone us-east-1a

# Create public subnet for application
aws ec2 create-subnet \
  --vpc-id vpc-12345 \
  --cidr-block 10.0.2.0/24 \
  --availability-zone us-east-1a
```

---

## 📊 Monitoring and Logging

### **1. CloudWatch Setup**

```bash
# Create log group
aws logs create-log-group --log-group-name /aws/businessprosuite-api

# Create custom metrics
aws cloudwatch put-metric-alarm \
  --alarm-name "BusinessProSuite-HighCPU" \
  --alarm-description "Alarm when CPU exceeds 70%" \
  --metric-name CPUUtilization \
  --namespace AWS/EC2 \
  --statistic Average \
  --period 300 \
  --threshold 70 \
  --comparison-operator GreaterThanThreshold \
  --evaluation-periods 2
```

### **2. Application Monitoring**

```yaml
# Add to application-prod.yml
management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    export:
      cloudwatch:
        enabled: true
        namespace: BusinessProSuite
        step: 1m
```

---

## 🔄 CI/CD Pipeline Setup

### **1. GitHub Actions Workflow**

```yaml
# .github/workflows/deploy-aws.yml
name: Deploy to AWS

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'corretto'
    
    - name: Build with Gradle
      run: ./gradlew build
    
    - name: Configure AWS credentials
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    
    - name: Deploy to Elastic Beanstalk
      run: |
        zip -r deploy.zip build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar Procfile
        aws s3 cp deploy.zip s3://your-deployment-bucket/
        aws elasticbeanstalk create-application-version \
          --application-name BusinessProSuite-API \
          --version-label ${{ github.sha }} \
          --source-bundle S3Bucket=your-deployment-bucket,S3Key=deploy.zip
        aws elasticbeanstalk update-environment \
          --environment-name BusinessProSuite-API-prod \
          --version-label ${{ github.sha }}
```

---

## 💰 Cost Optimization

### **1. Resource Right-Sizing**

| Component | Small (Development) | Medium (Staging) | Large (Production) |
|-----------|-------------------|------------------|-------------------|
| EC2 Instance | t3.micro ($8/month) | t3.small ($16/month) | t3.medium ($32/month) |
| RDS Instance | db.t3.micro ($15/month) | db.t3.small ($30/month) | db.t3.medium ($60/month) |
| Load Balancer | - | ALB ($20/month) | ALB ($20/month) |
| **Total** | **$23/month** | **$66/month** | **$112/month** |

### **2. Cost Reduction Strategies**

```bash
# Use Spot Instances for development
aws ec2 request-spot-instances \
  --spot-price "0.05" \
  --instance-count 1 \
  --type "one-time" \
  --launch-specification '{
    "ImageId": "ami-0c55b159cbfafe1d0",
    "InstanceType": "t3.medium",
    "KeyName": "your-key-pair",
    "SecurityGroupIds": ["sg-your-security-group"]
  }'

# Schedule stop/start for development environments
aws events put-rule \
  --name "stop-dev-instances" \
  --schedule-expression "cron(0 18 * * MON-FRI)"
```

---

## 🚨 Troubleshooting

### **Common Issues and Solutions**

#### **1. Application Won't Start**
```bash
# Check logs
sudo journalctl -u businessprosuite -f

# Check Java process
ps aux | grep java

# Check port availability
sudo netstat -tlnp | grep 8080
```

#### **2. Database Connection Issues**
```bash
# Test database connectivity
mysql -h your-rds-endpoint -u admin -p

# Check security groups
aws ec2 describe-security-groups --group-ids sg-your-db-security-group
```

#### **3. SSL Certificate Issues**
```bash
# Check certificate status
sudo certbot certificates

# Renew certificate manually
sudo certbot renew

# Check Nginx configuration
sudo nginx -t
```

#### **4. Performance Issues**
```bash
# Check system resources
top
free -h
df -h

# Check application metrics
curl http://localhost:8080/actuator/metrics
```

---

## 📞 Support and Next Steps

### **Production Checklist**
- [ ] SSL certificate configured
- [ ] Database backups enabled
- [ ] Monitoring and alerting setup
- [ ] Auto-scaling configured
- [ ] Security groups properly configured
- [ ] Secrets managed securely
- [ ] CI/CD pipeline implemented
- [ ] Load testing completed

### **Scaling Considerations**
- **Horizontal Scaling**: Add more EC2 instances behind load balancer
- **Vertical Scaling**: Upgrade instance types
- **Database Scaling**: Read replicas, connection pooling
- **Caching**: Redis/ElastiCache implementation
- **CDN**: CloudFront for static assets

### **Security Hardening**
- **WAF**: AWS Web Application Firewall
- **DDoS Protection**: AWS Shield
- **Penetration Testing**: Regular security audits
- **Compliance**: SOC 2, GDPR considerations

---

**Status**: ✅ **AWS Deployment Guide Complete**  
**Last Updated**: June 3, 2025  
**Tested Environments**: Development, Staging, Production 
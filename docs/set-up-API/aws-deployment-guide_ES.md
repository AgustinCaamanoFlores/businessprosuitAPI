# Guía de Despliegue AWS - BusinessProSuite API

**Versión**: V0.2  
**Audiencia Objetivo**: Ingenieros DevOps, Arquitectos Cloud, Desarrolladores Senior  
**Tiempo de Despliegue Estimado**: 2-3 horas  

## 🎯 Propósito

Esta guía proporciona **instrucciones comprensivas** para desplegar BusinessProSuite API en Amazon Web Services (AWS). Cubre múltiples opciones de despliegue, desde instancias EC2 simples hasta soluciones containerizadas avanzadas con auto-escalado.

---

## 🏗️ Opciones de Arquitectura de Despliegue

### **Opción 1: Despliegue Simple EC2 (Recomendado para Empezar)**
- **Costo**: Bajo ($10-30/mes)
- **Complejidad**: Simple
- **Escalabilidad**: Manual
- **Caso de Uso**: Desarrollo, staging, producción pequeña

### **Opción 2: Despliegue Elastic Beanstalk**
- **Costo**: Medio ($30-100/mes)
- **Complejidad**: Medio
- **Escalabilidad**: Automática
- **Caso de Uso**: Producción con tráfico moderado

### **Opción 3: ECS con Fargate (Avanzado)**
- **Costo**: Medio-Alto ($50-200/mes)
- **Complejidad**: Avanzado
- **Escalabilidad**: Auto-escalado basado en contenedores
- **Caso de Uso**: Producción con requisitos de alta disponibilidad

---

## 📋 Prerequisitos

### **Configuración de Cuenta AWS**
1. **Cuenta AWS** con facturación habilitada
2. **Usuario IAM** con acceso programático
3. **AWS CLI** instalado y configurado
4. **Nombre de Dominio** (opcional pero recomendado)

### **Permisos AWS Requeridos**
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

### **Prerequisitos Locales**
- **AWS CLI** v2.x
- **Docker** (para despliegues containerizados)
- **Java 17** y **Gradle**
- **Git**

---

## 🚀 Opción 1: Despliegue EC2 (Paso a Paso)

### **Paso 1: Crear y Configurar Instancia EC2**

#### **Lanzar Instancia EC2**
```bash
# Usando AWS CLI
aws ec2 run-instances \
  --image-id ami-0c55b159cbfafe1d0 \
  --instance-type t3.medium \
  --key-name tu-par-de-claves \
  --security-group-ids sg-tu-grupo-seguridad \
  --subnet-id subnet-tu-subnet \
  --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=BusinessProSuite-API}]'
```

#### **Usando Consola AWS:**
1. **Navegar al Dashboard EC2**
2. **Lanzar Instancia:**
   - **AMI**: Amazon Linux 2023 AMI
   - **Tipo de Instancia**: t3.medium (2 vCPU, 4 GB RAM)
   - **Par de Claves**: Crear o seleccionar existente
   - **Grupo de Seguridad**: Crear con las siguientes reglas:
     ```
     Reglas de Entrada:
     - SSH (22) desde tu IP
     - HTTP (80) desde cualquier lugar (0.0.0.0/0)
     - HTTPS (443) desde cualquier lugar (0.0.0.0/0)
     - TCP Personalizado (8080) desde cualquier lugar (0.0.0.0/0)
     ```

### **Paso 2: Conectar y Configurar Servidor**

```bash
# Conectar a la instancia
ssh -i tu-par-de-claves.pem ec2-user@tu-ip-publica-instancia

# Actualizar sistema
sudo yum update -y

# Instalar Java 17
sudo yum install java-17-amazon-corretto-devel -y

# Verificar instalación de Java
java -version
```

### **Paso 3: Instalar y Configurar Aplicación**

```bash
# Instalar Git
sudo yum install git -y

# Clonar repositorio
git clone https://github.com/businessprosuite/BusinessProSuiteAPI.git
cd BusinessProSuiteAPI

# Hacer gradlew ejecutable
chmod +x gradlew

# Construir aplicación
./gradlew build

# Crear directorio de aplicación
sudo mkdir -p /opt/businessprosuite
sudo cp build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar /opt/businessprosuite/app.jar

# Crear usuario de aplicación
sudo useradd -r -s /bin/false businessprosuite
sudo chown -R businessprosuite:businessprosuite /opt/businessprosuite
```

### **Paso 4: Crear Servicio Systemd**

```bash
# Crear archivo de servicio
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
Environment=JWT_SECRET=tu-clave-secreta-jwt-produccion-aqui
Environment=SERVER_PORT=8080

[Install]
WantedBy=multi-user.target
EOF

# Recargar systemd e iniciar servicio
sudo systemctl daemon-reload
sudo systemctl enable businessprosuite
sudo systemctl start businessprosuite

# Verificar estado del servicio
sudo systemctl status businessprosuite
```

### **Paso 5: Configurar Nginx Reverse Proxy**

```bash
# Instalar Nginx
sudo yum install nginx -y

# Crear configuración de Nginx
sudo tee /etc/nginx/conf.d/businessprosuite.conf > /dev/null <<EOF
server {
    listen 80;
    server_name tu-dominio.com;  # Reemplazar con tu dominio

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
        
        # Soporte WebSocket
        proxy_http_version 1.1;
        proxy_set_header Upgrade \$http_upgrade;
        proxy_set_header Connection "upgrade";
    }
}
EOF

# Iniciar y habilitar Nginx
sudo systemctl start nginx
sudo systemctl enable nginx

# Probar configuración
sudo nginx -t
sudo systemctl reload nginx
```

### **Paso 6: Configurar SSL con Let's Encrypt (Opcional)**

```bash
# Instalar Certbot
sudo yum install certbot python3-certbot-nginx -y

# Obtener certificado SSL
sudo certbot --nginx -d tu-dominio.com

# Configuración de auto-renovación (ya configurada por certbot)
# Verificar auto-renovación
sudo certbot renew --dry-run
```

---

## ☁️ Opción 2: Despliegue Elastic Beanstalk

### **Paso 1: Preparar Paquete de Aplicación**

```bash
# Construir aplicación
./gradlew build

# Crear directorio de despliegue
mkdir -p deploy/elastic-beanstalk
cd deploy/elastic-beanstalk

# Copiar archivo JAR
cp ../../build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar application.jar

# Crear Procfile
echo 'web: java -Dserver.port=$PORT -jar application.jar' > Procfile

# Crear application.properties para Elastic Beanstalk
cat > application-beanstalk.properties <<EOF
server.port=5000
spring.profiles.active=prod
jwt.secret=\${JWT_SECRET:clave-secreta-por-defecto}
logging.level.com.businessprosuite=INFO
EOF
```

### **Paso 2: Crear Paquete de Aplicación**

```bash
# Crear paquete ZIP
zip -r businessprosuite-api.zip application.jar Procfile application-beanstalk.properties

# Verificar contenidos del paquete
unzip -l businessprosuite-api.zip
```

### **Paso 3: Desplegar usando AWS CLI**

```bash
# Crear aplicación Elastic Beanstalk
aws elasticbeanstalk create-application \
  --application-name BusinessProSuite-API \
  --description "Aplicación BusinessProSuite API"

# Crear versión de aplicación
aws elasticbeanstalk create-application-version \
  --application-name BusinessProSuite-API \
  --version-label v1.0 \
  --source-bundle S3Bucket=tu-bucket-despliegue,S3Key=businessprosuite-api.zip

# Crear ambiente
aws elasticbeanstalk create-environment \
  --application-name BusinessProSuite-API \
  --environment-name BusinessProSuite-API-prod \
  --version-label v1.0 \
  --solution-stack-name "64bit Amazon Linux 2023 v4.0.0 running Corretto 17"
```

### **Paso 4: Configurar Variables de Entorno**

```bash
# Configurar variables de entorno
aws elasticbeanstalk update-environment \
  --environment-name BusinessProSuite-API-prod \
  --option-settings \
    Namespace=aws:elasticbeanstalk:application:environment,OptionName=JWT_SECRET,Value=tu-jwt-secret \
    Namespace=aws:elasticbeanstalk:application:environment,OptionName=SPRING_PROFILES_ACTIVE,Value=prod \
    Namespace=aws:autoscaling:launchconfiguration,OptionName=InstanceType,Value=t3.medium
```

---

## 🐳 Opción 3: Despliegue ECS con Fargate

### **Paso 1: Crear Imagen Docker**

```dockerfile
# Crear Dockerfile
cat > Dockerfile <<EOF
FROM amazoncorretto:17-alpine

WORKDIR /app

COPY build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV SPRING_PROFILES_ACTIVE=prod
ENV JWT_SECRET=clave-secreta-por-defecto

ENTRYPOINT ["java", "-jar", "app.jar"]
EOF
```

### **Paso 2: Construir y Subir a ECR**

```bash
# Construir imagen Docker
docker build -t businessprosuite-api .

# Crear repositorio ECR
aws ecr create-repository --repository-name businessprosuite-api

# Obtener token de login
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin tu-id-cuenta.dkr.ecr.us-east-1.amazonaws.com

# Etiquetar y subir imagen
docker tag businessprosuite-api:latest tu-id-cuenta.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest
docker push tu-id-cuenta.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest
```

### **Paso 3: Crear Cluster y Servicio ECS**

```bash
# Crear cluster ECS
aws ecs create-cluster --cluster-name businessprosuite-cluster

# Crear definición de tarea
cat > task-definition.json <<EOF
{
  "family": "businessprosuite-api",
  "networkMode": "awsvpc",
  "requiresCompatibilities": ["FARGATE"],
  "cpu": "512",
  "memory": "1024",
  "executionRoleArn": "arn:aws:iam::tu-id-cuenta:role/ecsTaskExecutionRole",
  "containerDefinitions": [
    {
      "name": "businessprosuite-api",
      "image": "tu-id-cuenta.dkr.ecr.us-east-1.amazonaws.com/businessprosuite-api:latest",
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
          "valueFrom": "arn:aws:secretsmanager:us-east-1:tu-id-cuenta:secret:businessprosuite/jwt-secret"
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

# Registrar definición de tarea
aws ecs register-task-definition --cli-input-json file://task-definition.json
```

---

## 🗄️ Configuración de Base de Datos (RDS)

### **Paso 1: Crear Instancia RDS MySQL**

```bash
# Crear grupo de subnets DB
aws rds create-db-subnet-group \
  --db-subnet-group-name businessprosuite-subnet-group \
  --db-subnet-group-description "Grupo de subnets para BusinessProSuite API" \
  --subnet-ids subnet-12345 subnet-67890

# Crear instancia RDS
aws rds create-db-instance \
  --db-instance-identifier businessprosuite-db \
  --db-instance-class db.t3.micro \
  --engine mysql \
  --engine-version 8.0.35 \
  --master-username admin \
  --master-user-password TuClaveSegura123! \
  --allocated-storage 20 \
  --storage-type gp2 \
  --vpc-security-group-ids sg-tu-grupo-seguridad \
  --db-subnet-group-name businessprosuite-subnet-group \
  --backup-retention-period 7 \
  --multi-az \
  --storage-encrypted
```

### **Paso 2: Configurar Grupos de Seguridad**

```bash
# Crear grupo de seguridad para RDS
aws ec2 create-security-group \
  --group-name businessprosuite-db-sg \
  --description "Grupo de seguridad para base de datos BusinessProSuite"

# Permitir acceso MySQL desde grupo de seguridad de aplicación
aws ec2 authorize-security-group-ingress \
  --group-id sg-grupo-seguridad-base-datos \
  --protocol tcp \
  --port 3306 \
  --source-group sg-grupo-seguridad-aplicacion
```

### **Paso 3: Actualizar Configuración de Aplicación**

```yaml
# Agregar a application-prod.yml
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

## 🔐 Mejores Prácticas de Seguridad

### **1. Gestión de Secretos**

```bash
# Almacenar secreto JWT en AWS Secrets Manager
aws secretsmanager create-secret \
  --name businessprosuite/jwt-secret \
  --description "Secreto JWT para BusinessProSuite API" \
  --secret-string '{"jwt_secret":"tu-clave-jwt-super-segura"}'

# Almacenar contraseña de base de datos
aws secretsmanager create-secret \
  --name businessprosuite/db-password \
  --description "Contraseña de base de datos para BusinessProSuite API" \
  --secret-string '{"password":"TuClaveSegura123!"}'
```

### **2. Roles y Políticas IAM**

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

### **3. Seguridad de Red**

```bash
# Crear VPC con subnets privadas
aws ec2 create-vpc --cidr-block 10.0.0.0/16

# Crear subnet privada para base de datos
aws ec2 create-subnet \
  --vpc-id vpc-12345 \
  --cidr-block 10.0.1.0/24 \
  --availability-zone us-east-1a

# Crear subnet pública para aplicación
aws ec2 create-subnet \
  --vpc-id vpc-12345 \
  --cidr-block 10.0.2.0/24 \
  --availability-zone us-east-1a
```

---

## 📊 Monitoreo y Logging

### **1. Configuración CloudWatch**

```bash
# Crear grupo de logs
aws logs create-log-group --log-group-name /aws/businessprosuite-api

# Crear métricas personalizadas
aws cloudwatch put-metric-alarm \
  --alarm-name "BusinessProSuite-HighCPU" \
  --alarm-description "Alarma cuando CPU excede 70%" \
  --metric-name CPUUtilization \
  --namespace AWS/EC2 \
  --statistic Average \
  --period 300 \
  --threshold 70 \
  --comparison-operator GreaterThanThreshold \
  --evaluation-periods 2
```

### **2. Monitoreo de Aplicación**

```yaml
# Agregar a application-prod.yml
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

## 🔄 Configuración de Pipeline CI/CD

### **1. Workflow GitHub Actions**

```yaml
# .github/workflows/deploy-aws.yml
name: Desplegar a AWS

on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Configurar JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'corretto'
    
    - name: Construir con Gradle
      run: ./gradlew build
    
    - name: Configurar credenciales AWS
      uses: aws-actions/configure-aws-credentials@v2
      with:
        aws-access-key-id: ${{ secrets.AWS_ACCESS_KEY_ID }}
        aws-secret-access-key: ${{ secrets.AWS_SECRET_ACCESS_KEY }}
        aws-region: us-east-1
    
    - name: Desplegar a Elastic Beanstalk
      run: |
        zip -r deploy.zip build/libs/BusinessProSuiteAPI-0.0.1-SNAPSHOT.jar Procfile
        aws s3 cp deploy.zip s3://tu-bucket-despliegue/
        aws elasticbeanstalk create-application-version \
          --application-name BusinessProSuite-API \
          --version-label ${{ github.sha }} \
          --source-bundle S3Bucket=tu-bucket-despliegue,S3Key=deploy.zip
        aws elasticbeanstalk update-environment \
          --environment-name BusinessProSuite-API-prod \
          --version-label ${{ github.sha }}
```

---

## 💰 Optimización de Costos

### **1. Dimensionamiento Correcto de Recursos**

| Componente | Pequeño (Desarrollo) | Medio (Staging) | Grande (Producción) |
|-----------|-------------------|------------------|-------------------|
| Instancia EC2 | t3.micro ($8/mes) | t3.small ($16/mes) | t3.medium ($32/mes) |
| Instancia RDS | db.t3.micro ($15/mes) | db.t3.small ($30/mes) | db.t3.medium ($60/mes) |
| Load Balancer | - | ALB ($20/mes) | ALB ($20/mes) |
| **Total** | **$23/mes** | **$66/mes** | **$112/mes** |

### **2. Estrategias de Reducción de Costos**

```bash
# Usar Spot Instances para desarrollo
aws ec2 request-spot-instances \
  --spot-price "0.05" \
  --instance-count 1 \
  --type "one-time" \
  --launch-specification '{
    "ImageId": "ami-0c55b159cbfafe1d0",
    "InstanceType": "t3.medium",
    "KeyName": "tu-par-de-claves",
    "SecurityGroupIds": ["sg-tu-grupo-seguridad"]
  }'

# Programar parada/inicio para entornos de desarrollo
aws events put-rule \
  --name "stop-dev-instances" \
  --schedule-expression "cron(0 18 * * MON-FRI)"
```

---

## 🚨 Solución de Problemas

### **Problemas Comunes y Soluciones**

#### **1. La Aplicación No Inicia**
```bash
# Verificar logs
sudo journalctl -u businessprosuite -f

# Verificar proceso Java
ps aux | grep java

# Verificar disponibilidad de puerto
sudo netstat -tlnp | grep 8080
```

#### **2. Problemas de Conexión de Base de Datos**
```bash
# Probar conectividad de base de datos
mysql -h tu-endpoint-rds -u admin -p

# Verificar grupos de seguridad
aws ec2 describe-security-groups --group-ids sg-tu-grupo-seguridad-db
```

#### **3. Problemas de Certificado SSL**
```bash
# Verificar estado del certificado
sudo certbot certificates

# Renovar certificado manualmente
sudo certbot renew

# Verificar configuración de Nginx
sudo nginx -t
```

#### **4. Problemas de Performance**
```bash
# Verificar recursos del sistema
top
free -h
df -h

# Verificar métricas de aplicación
curl http://localhost:8080/actuator/metrics
```

---

## 📞 Soporte y Próximos Pasos

### **Checklist de Producción**
- [ ] Certificado SSL configurado
- [ ] Backups de base de datos habilitados
- [ ] Monitoreo y alertas configurados
- [ ] Auto-escalado configurado
- [ ] Grupos de seguridad configurados apropiadamente
- [ ] Secretos gestionados de forma segura
- [ ] Pipeline CI/CD implementado
- [ ] Testing de carga completado

### **Consideraciones de Escalado**
- **Escalado Horizontal**: Agregar más instancias EC2 detrás del load balancer
- **Escalado Vertical**: Actualizar tipos de instancia
- **Escalado de Base de Datos**: Read replicas, connection pooling
- **Caching**: Implementación Redis/ElastiCache
- **CDN**: CloudFront para assets estáticos

### **Endurecimiento de Seguridad**
- **WAF**: AWS Web Application Firewall
- **Protección DDoS**: AWS Shield
- **Testing de Penetración**: Auditorías de seguridad regulares
- **Cumplimiento**: Consideraciones SOC 2, GDPR

---

**Estado**: ✅ **Guía de Despliegue AWS Completa**  
**Última Actualización**: 3 de Junio de 2025  
**Entornos Probados**: Desarrollo, Staging, Producción 
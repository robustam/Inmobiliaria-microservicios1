#!/bin/bash

BASE=~/Documents/Inmobiliaria-microservicios1

echo "🔴 Matando procesos anteriores..."
kill -9 $(lsof -t -i:8080,8081,8082,8083,8084,8761) 2>/dev/null
sleep 2

echo "🟢 Iniciando Eureka (puerto 8761)..."
cd $BASE/eureka-server && java -jar target/*.jar > /tmp/eureka.log 2>&1 &
sleep 12

echo "🟢 Iniciando Propiedad (puerto 8081)..."
cd $BASE/propiedad-service && java -jar target/*.jar > /tmp/propiedad.log 2>&1 &
sleep 6

echo "🟢 Iniciando Usuario (puerto 8082)..."
cd $BASE/usuario-service && java -jar target/*.jar > /tmp/usuario.log 2>&1 &
sleep 6

echo "🟢 Iniciando Reservas (puerto 8083)..."
cd $BASE/reservas-service && java -jar target/*.jar > /tmp/reservas.log 2>&1 &
sleep 6

echo "🟢 Iniciando Pagos (puerto 8084)..."
cd $BASE/pagos-service && java -jar target/*.jar > /tmp/pagos.log 2>&1 &
sleep 6

echo "🟢 Iniciando API Gateway (puerto 8080)..."
cd $BASE/api-gateway && java -jar target/*.jar > /tmp/gateway.log 2>&1 &
sleep 6

echo ""
echo "✅ Todos los servicios iniciados"
echo ""
echo "Para ver logs:"
echo "  tail -f /tmp/eureka.log"
echo "  tail -f /tmp/propiedad.log"
echo "  tail -f /tmp/usuario.log"
echo "  tail -f /tmp/reservas.log"
echo "  tail -f /tmp/pagos.log"
echo "  tail -f /tmp/gateway.log"
echo ""
echo "Para detener todo:"
echo "  kill -9 \$(lsof -t -i:8080,8081,8082,8083,8084,8761) 2>/dev/null"

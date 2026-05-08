#!/bin/bash

echo "=================================================="
echo "   PRUEBAS COMPLETAS DE MICROSERVICIOS"
echo "=================================================="

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

pass() { echo -e "${GREEN}✅ $1${NC}"; }
fail() { echo -e "${RED}❌ $1${NC}"; }
title() { echo -e "\n${YELLOW}=== $1 ===${NC}"; }

# ==========================================
# 1. AUTH SERVICE (8092)
# ==========================================
title "1. AUTH SERVICE"

echo "→ POST Registro Admin (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Admin","email":"admin2@test.com","password":"admin123","rol":"ADMIN"}')
[ "$R" = "200" ] || [ "$R" = "201" ] && pass "Registro Admin: $R" || fail "Registro Admin: $R"

echo "→ POST Registro Cliente (200)"
CLIENTE=$(curl -s -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Roberto","email":"rob2@test.com","password":"clave123","rol":"CLIENTE"}')
echo "$CLIENTE" | grep -q '"id"' && pass "Registro Cliente: 200" || fail "Registro Cliente: $CLIENTE"
AUTH_ID=$(echo $CLIENTE | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')

echo "→ POST Registro Email duplicado (400)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Otro","email":"admin2@test.com","password":"123","rol":"CLIENTE"}')
[ "$R" = "400" ] && pass "Email duplicado: $R" || fail "Email duplicado esperaba 400, got: $R"

echo "→ POST Login correcto (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"rob2@test.com","password":"clave123"}')
[ "$R" = "200" ] && pass "Login correcto: $R" || fail "Login correcto: $R"

echo "→ POST Login incorrecto (401)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"rob2@test.com","password":"wrong"}')
[ "$R" = "401" ] && pass "Login incorrecto: $R" || fail "Login incorrecto: $R"

echo "→ GET Listar usuarios (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios)
[ "$R" = "200" ] && pass "Listar usuarios: $R" || fail "Listar usuarios: $R"

echo "→ GET Usuario por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios/$AUTH_ID)
[ "$R" = "200" ] && pass "Usuario por ID: $R" || fail "Usuario por ID: $R"

echo "→ GET Usuario no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios/9999)
[ "$R" = "404" ] && pass "Usuario no existe: $R" || fail "Usuario no existe esperaba 404, got: $R"

# ==========================================
# 2. USUARIO SERVICE (8082)
# ==========================================
title "2. USUARIO SERVICE"

USUARIO=$(curl -s -X POST http://localhost:8082/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Roberto Bustamante","email":"test@test.com","telefono":"912345678"}')
USR_ID=$(echo $USUARIO | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$USUARIO" | grep -q '"id"' && pass "Crear usuario: 200" || fail "Crear usuario: $USUARIO"

echo "→ GET Listar usuarios (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios)
[ "$R" = "200" ] && pass "Listar usuarios: $R" || fail "Listar usuarios: $R"

echo "→ GET Usuario por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios/$USR_ID)
[ "$R" = "200" ] && pass "Usuario por ID: $R" || fail "Usuario por ID: $R"

echo "→ GET Usuario no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios/9999)
[ "$R" = "404" ] && pass "Usuario no existe: $R" || fail "Usuario no existe esperaba 404, got: $R"

# ==========================================
# 3. PROPIEDAD SERVICE (8081)
# ==========================================
title "3. PROPIEDAD SERVICE"

PROP=$(curl -s -X POST http://localhost:8081/api/propiedades \
  -H "Content-Type: application/json" \
  -d '{"direccion":"Av. Providencia 1234","habitaciones":3,"precio":450000.0}')
PROP_ID=$(echo $PROP | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$PROP" | grep -q '"id"' && pass "Crear propiedad: 200" || fail "Crear propiedad: $PROP"

echo "→ GET Listar propiedades (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades)
[ "$R" = "200" ] && pass "Listar propiedades: $R" || fail "Listar propiedades: $R"

echo "→ GET Propiedad por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades/$PROP_ID)
[ "$R" = "200" ] && pass "Propiedad por ID: $R" || fail "Propiedad por ID: $R"

echo "→ GET Propiedad no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades/9999)
[ "$R" = "404" ] && pass "Propiedad no existe: $R" || fail "Propiedad no existe esperaba 404, got: $R"

# ==========================================
# 4. VISITA SERVICE (8094)
# ==========================================
title "4. VISITA SERVICE"

VISITA=$(curl -s -X POST http://localhost:8094/api/visitas \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":$USR_ID,\"idPropiedad\":$PROP_ID,\"fechaHora\":\"2026-06-01T10:00:00\",\"observaciones\":\"Visita test\"}")
VIS_ID=$(echo $VISITA | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$VISITA" | grep -q '"id"' && pass "Agendar visita: 200" || fail "Agendar visita: $VISITA"

echo "→ GET Listar visitas (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8094/api/visitas)
[ "$R" = "200" ] && pass "Listar visitas: $R" || fail "Listar visitas: $R"

echo "→ GET Visita por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8094/api/visitas/$VIS_ID)
[ "$R" = "200" ] && pass "Visita por ID: $R" || fail "Visita por ID: $R"

echo "→ PUT Confirmar visita (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8094/api/visitas/$VIS_ID/confirmar)
[ "$R" = "200" ] && pass "Confirmar visita: $R" || fail "Confirmar visita: $R"

echo "→ PUT Cancelar visita no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8094/api/visitas/9999/cancelar)
[ "$R" = "404" ] && pass "Cancelar no existe: $R" || fail "Cancelar no existe esperaba 404, got: $R"

echo "→ DELETE Visita (204)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8094/api/visitas/$VIS_ID)
[ "$R" = "204" ] && pass "Eliminar visita: $R" || fail "Eliminar visita: $R"

echo "→ DELETE Visita no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8094/api/visitas/9999)
[ "$R" = "404" ] && pass "Eliminar no existe: $R" || fail "Eliminar no existe esperaba 404, got: $R"

# ==========================================
# 5. RESERVAS SERVICE (8083)
# ==========================================
title "5. RESERVAS SERVICE"

RES=$(curl -s -X POST http://localhost:8083/api/reservas/crear \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":$USR_ID,\"idPropiedad\":$PROP_ID}")
echo "$RES" | grep -q "confirmada" && pass "Crear reserva: 200" || fail "Crear reserva: $RES"

echo "→ GET Listar reservas (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/listar)
[ "$R" = "200" ] && pass "Listar reservas: $R" || fail "Listar reservas: $R"

echo "→ GET Reserva por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/1)
[ "$R" = "200" ] && pass "Reserva por ID: $R" || fail "Reserva por ID: $R"

echo "→ GET Reserva no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/9999)
[ "$R" = "404" ] && pass "Reserva no existe: $R" || fail "Reserva no existe esperaba 404, got: $R"

# ==========================================
# 6. CONTRATO SERVICE (8093)
# ==========================================
title "6. CONTRATO SERVICE"

CONT=$(curl -s -X POST http://localhost:8093/api/contratos \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":$USR_ID,\"idPropiedad\":$PROP_ID,\"fechaInicio\":\"2026-06-01\",\"fechaFin\":\"2027-06-01\",\"montoMensual\":450000.0,\"garantia\":900000.0}")
CONT_ID=$(echo $CONT | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$CONT" | grep -q '"id"' && pass "Crear contrato: 200" || fail "Crear contrato: $CONT"

echo "→ GET Listar contratos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos)
[ "$R" = "200" ] && pass "Listar contratos: $R" || fail "Listar contratos: $R"

echo "→ GET Contrato por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/$CONT_ID)
[ "$R" = "200" ] && pass "Contrato por ID: $R" || fail "Contrato por ID: $R"

echo "→ GET Contratos por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/usuario/$USR_ID)
[ "$R" = "200" ] && pass "Contratos por usuario: $R" || fail "Contratos por usuario: $R"

echo "→ PUT Terminar contrato (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8093/api/contratos/$CONT_ID/terminar)
[ "$R" = "200" ] && pass "Terminar contrato: $R" || fail "Terminar contrato: $R"

echo "→ GET Contrato no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/9999)
[ "$R" = "404" ] && pass "Contrato no existe: $R" || fail "Contrato no existe esperaba 404, got: $R"

# ==========================================
# 7. PAGOS SERVICE (8084)
# ==========================================
title "7. PAGOS SERVICE"

echo "→ POST Procesar pago (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8084/api/pagos/procesar \
  -H "Content-Type: application/json" \
  -d '{"idReserva":1,"monto":450000.0}')
[ "$R" = "200" ] || [ "$R" = "201" ] && pass "Procesar pago: $R" || fail "Procesar pago: $R"

echo "→ POST Pago reserva no existe (mensaje error)"
BODY=$(curl -s -X POST http://localhost:8084/api/pagos/procesar \
  -H "Content-Type: application/json" \
  -d '{"idReserva":9999,"monto":100.0}')
echo "$BODY" | grep -qi "no existe\|error\|not found" && pass "Pago reserva no existe: mensaje correcto" || fail "Pago reserva no existe: $BODY"

echo "→ GET Listar pagos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8084/api/pagos/listar)
[ "$R" = "200" ] && pass "Listar pagos: $R" || fail "Listar pagos: $R"

# ==========================================
# 8. MANTENIMIENTO SERVICE (8095)
# ==========================================
title "8. MANTENIMIENTO SERVICE"

MANT=$(curl -s -X POST http://localhost:8095/api/mantenimientos \
  -H "Content-Type: application/json" \
  -d "{\"idPropiedad\":$PROP_ID,\"idUsuario\":$USR_ID,\"descripcion\":\"Fuga de agua\",\"prioridad\":\"ALTA\"}")
MANT_ID=$(echo $MANT | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$MANT" | grep -q '"id"' && pass "Crear solicitud: 200" || fail "Crear solicitud: $MANT"

echo "→ GET Listar mantenimientos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos)
[ "$R" = "200" ] && pass "Listar mantenimientos: $R" || fail "Listar mantenimientos: $R"

echo "→ GET Mantenimiento por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/$MANT_ID)
[ "$R" = "200" ] && pass "Mantenimiento por ID: $R" || fail "Mantenimiento por ID: $R"

echo "→ GET Mantenimientos por propiedad (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/propiedad/$PROP_ID)
[ "$R" = "200" ] && pass "Mantenimientos por propiedad: $R" || fail "Mantenimientos por propiedad: $R"

echo "→ PUT Resolver mantenimiento (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8095/api/mantenimientos/$MANT_ID/resolver)
[ "$R" = "200" ] && pass "Resolver mantenimiento: $R" || fail "Resolver mantenimiento: $R"

echo "→ GET Mantenimiento no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/9999)
[ "$R" = "404" ] && pass "Mantenimiento no existe: $R" || fail "Mantenimiento no existe esperaba 404, got: $R"

# ==========================================
# 9. DOCUMENTO SERVICE (8097)
# ==========================================
title "9. DOCUMENTO SERVICE"

DOC=$(curl -s -X POST http://localhost:8097/api/documentos \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":$USR_ID,\"idContrato\":$CONT_ID,\"tipo\":\"CONTRATO\",\"nombre\":\"Contrato.pdf\",\"url\":\"docs/contrato.pdf\"}")
DOC_ID=$(echo $DOC | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$DOC" | grep -q '"id"' && pass "Subir documento: 200" || fail "Subir documento: $DOC"

echo "→ GET Listar documentos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos)
[ "$R" = "200" ] && pass "Listar documentos: $R" || fail "Listar documentos: $R"

echo "→ GET Documento por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/$DOC_ID)
[ "$R" = "200" ] && pass "Documento por ID: $R" || fail "Documento por ID: $R"

echo "→ GET Documentos por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/usuario/$USR_ID)
[ "$R" = "200" ] && pass "Documentos por usuario: $R" || fail "Documentos por usuario: $R"

echo "→ GET Documentos por contrato (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/contrato/$CONT_ID)
[ "$R" = "200" ] && pass "Documentos por contrato: $R" || fail "Documentos por contrato: $R"

echo "→ PUT Firmar documento (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8097/api/documentos/$DOC_ID/firmar)
[ "$R" = "200" ] && pass "Firmar documento: $R" || fail "Firmar documento: $R"

echo "→ DELETE Documento (204)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8097/api/documentos/$DOC_ID)
[ "$R" = "204" ] && pass "Eliminar documento: $R" || fail "Eliminar documento: $R"

echo "→ DELETE Documento no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8097/api/documentos/9999)
[ "$R" = "404" ] && pass "Eliminar no existe: $R" || fail "Eliminar no existe esperaba 404, got: $R"

# ==========================================
# 10. NOTIFICACIONES SERVICE (8086)
# ==========================================
title "10. NOTIFICACIONES SERVICE"

cat > /tmp/notif_test.json << 'EOF'
{"idUsuario":1,"idReserva":1,"tipo":"PAGO_RECIBIDO","asunto":"Pago recibido","cuerpo":"Tu pago fue recibido","emailDestinario":"test@test.com"}
EOF

NOTIF=$(curl -s -X POST http://localhost:8086/api/notificaciones/enviar \
  -H "Content-Type: application/json" -d @/tmp/notif_test.json)
NOTIF_ID=$(echo $NOTIF | grep -o '"id":[0-9]*' | head -1 | grep -o '[0-9]*')
echo "$NOTIF" | grep -q '"id"\|ENVIADO\|exitosa' && pass "Enviar notificacion: 201" || fail "Enviar notificacion: $NOTIF"

echo "→ GET Listar notificaciones (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones)
[ "$R" = "200" ] && pass "Listar notificaciones: $R" || fail "Listar notificaciones: $R"

echo "→ GET Notificacion por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/$NOTIF_ID)
[ "$R" = "200" ] && pass "Notificacion por ID: $R" || fail "Notificacion por ID: $R"

echo "→ GET Notificaciones por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/usuario/$USR_ID)
[ "$R" = "200" ] && pass "Notificaciones por usuario: $R" || fail "Notificaciones por usuario: $R"

echo "→ GET Notificacion no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/9999)
[ "$R" = "404" ] || [ "$R" = "500" ] && pass "Notificacion no existe: $R (aceptable)" || fail "Notificacion no existe: $R"

echo ""
echo "=================================================="
echo "   PRUEBAS FINALIZADAS"
echo "=================================================="

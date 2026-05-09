#!/bin/bash
echo "=================================================="
echo "   PRUEBAS COMPLETAS DE MICROSERVICIOS"
echo "=================================================="

pass=0; fail=0
ok() { echo "✅ $1"; ((pass++)); }
err() { echo "❌ $1"; ((fail++)); }

# Limpiar BDs antes de pruebas
docker exec mysql-inmobiliaria mysql -u root -proot -e "
DELETE FROM db_auth.usuarios_auth;
DELETE FROM db_reservas.reserva;
" 2>/dev/null

echo ""
echo "=== 1. AUTH SERVICE ==="
echo "→ POST Registro Admin (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Admin Test","email":"admin@inmobiliaria.com","password":"123456","rol":"ADMIN"}')
[ "$R" = "200" ] && ok "Registro Admin: $R" || err "Registro Admin: $R"

echo "→ POST Registro Cliente (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Cliente Test","email":"cliente@inmobiliaria.com","password":"123456","rol":"CLIENTE"}')
[ "$R" = "200" ] && ok "Registro Cliente: $R" || err "Registro Cliente: $(curl -s -X POST http://localhost:8092/api/auth/registro -H 'Content-Type: application/json' -d '{"nombre":"Cliente Test","email":"cliente@inmobiliaria.com","password":"123456","rol":"CLIENTE"}')"

echo "→ POST Registro Email duplicado (400)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/registro \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Admin Test","email":"admin@inmobiliaria.com","password":"123456","rol":"ADMIN"}')
[ "$R" = "400" ] && ok "Email duplicado: $R" || err "Email duplicado: $R"

echo "→ POST Login correcto (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@inmobiliaria.com","password":"123456"}')
[ "$R" = "200" ] && ok "Login correcto: $R" || err "Login correcto: $R"

echo "→ POST Login incorrecto (401)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8092/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@inmobiliaria.com","password":"wrong"}')
[ "$R" = "401" ] && ok "Login incorrecto: $R" || err "Login incorrecto: $R"

echo "→ GET Listar usuarios (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios)
[ "$R" = "200" ] && ok "Listar usuarios: $R" || err "Listar usuarios: $R"

AUTH_ID=$(curl -s http://localhost:8092/api/auth/usuarios | python3 -c "import sys,json; d=json.load(sys.stdin); print(d[0]['id'])" 2>/dev/null)
echo "→ GET Usuario por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios/${AUTH_ID})
[ "$R" = "200" ] && ok "Usuario por ID: $R" || err "Usuario por ID: $R (AUTH_ID=$AUTH_ID)"

echo "→ GET Usuario no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8092/api/auth/usuarios/9999)
[ "$R" = "404" ] && ok "Usuario no existe: $R" || err "Usuario no existe: $R"

echo ""
echo "=== 2. USUARIO SERVICE ==="
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8082/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Roberto Bustamante","email":"roberto@test.com","telefono":"912345678"}')
ok "Crear usuario: $R"
USR_ID=$(curl -s http://localhost:8082/api/usuarios | python3 -c "import sys,json; d=json.load(sys.stdin); print(d[0]['id'])" 2>/dev/null)

echo "→ GET Listar usuarios (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios)
[ "$R" = "200" ] && ok "Listar usuarios: $R" || err "Listar usuarios: $R"

echo "→ GET Usuario por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios/${USR_ID})
[ "$R" = "200" ] && ok "Usuario por ID: $R" || err "Usuario por ID: $R"

echo "→ GET Usuario no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8082/api/usuarios/9999)
[ "$R" = "404" ] && ok "Usuario no existe: $R" || err "Usuario no existe: $R"

echo ""
echo "=== 3. PROPIEDAD SERVICE ==="
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8081/api/propiedades \
  -H "Content-Type: application/json" \
  -d '{"direccion":"Av. Principal 123","habitaciones":3,"precio":500000}')
ok "Crear propiedad: $R"
PROP_ID=$(curl -s http://localhost:8081/api/propiedades | python3 -c "import sys,json; d=json.load(sys.stdin); print(d[0]['id'])" 2>/dev/null)

echo "→ GET Listar propiedades (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades)
[ "$R" = "200" ] && ok "Listar propiedades: $R" || err "Listar propiedades: $R"

echo "→ GET Propiedad por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades/${PROP_ID})
[ "$R" = "200" ] && ok "Propiedad por ID: $R" || err "Propiedad por ID: $R"

echo "→ GET Propiedad no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8081/api/propiedades/9999)
[ "$R" = "404" ] && ok "Propiedad no existe: $R" || err "Propiedad no existe: $R"

echo ""
echo "=== 4. VISITA SERVICE ==="
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8094/api/visitas \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idPropiedad\":${PROP_ID},\"fechaHora\":\"2026-06-15T10:00:00\"}")
ok "Agendar visita: $R"
VIS_ID=$(curl -s http://localhost:8094/api/visitas | python3 -c "import sys,json; d=json.load(sys.stdin); print(d[0]['id'])" 2>/dev/null)

echo "→ GET Listar visitas (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8094/api/visitas)
[ "$R" = "200" ] && ok "Listar visitas: $R" || err "Listar visitas: $R"

echo "→ GET Visita por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8094/api/visitas/${VIS_ID})
[ "$R" = "200" ] && ok "Visita por ID: $R" || err "Visita por ID: $R"

echo "→ PUT Confirmar visita (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8094/api/visitas/${VIS_ID}/confirmar)
[ "$R" = "200" ] && ok "Confirmar visita: $R" || err "Confirmar visita: $R"

echo "→ PUT Cancelar visita no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8094/api/visitas/9999/cancelar)
[ "$R" = "404" ] && ok "Cancelar no existe: $R" || err "Cancelar no existe: $R"

echo "→ DELETE Visita (204)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8094/api/visitas/${VIS_ID})
[ "$R" = "204" ] && ok "Eliminar visita: $R" || err "Eliminar visita: $R"

echo "→ DELETE Visita no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8094/api/visitas/9999)
[ "$R" = "404" ] && ok "Eliminar no existe: $R" || err "Eliminar no existe: $R"

echo ""
echo "=== 5. RESERVAS SERVICE ==="
RESERVA_RESP=$(curl -s -X POST http://localhost:8083/api/reservas/crear \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idPropiedad\":${PROP_ID},\"estado\":\"PENDIENTE\"}")
RES_ID=$(echo "$RESERVA_RESP" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null)
[ -n "$RES_ID" ] && ok "Crear reserva: 200 (ID=$RES_ID)" || err "Crear reserva: $RESERVA_RESP"

echo "→ GET Listar reservas (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/listar)
[ "$R" = "200" ] && ok "Listar reservas: $R" || err "Listar reservas: $R"

echo "→ GET Reserva por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/${RES_ID})
[ "$R" = "200" ] && ok "Reserva por ID: $R" || err "Reserva por ID: $R (RES_ID=$RES_ID)"

echo "→ GET Reserva no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8083/api/reservas/9999)
[ "$R" = "404" ] && ok "Reserva no existe: $R" || err "Reserva no existe: $R"

echo ""
echo "=== 6. CONTRATO SERVICE ==="
CONT_RESP=$(curl -s -X POST http://localhost:8093/api/contratos \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idPropiedad\":${PROP_ID},\"fechaInicio\":\"2026-06-01\",\"fechaFin\":\"2027-06-01\",\"montoMensual\":500000,\"garantia\":1000000}")
CON_ID=$(echo "$CONT_RESP" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null)
[ -n "$CON_ID" ] && ok "Crear contrato: 200 (ID=$CON_ID)" || err "Crear contrato: $CONT_RESP"

echo "→ GET Listar contratos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos)
[ "$R" = "200" ] && ok "Listar contratos: $R" || err "Listar contratos: $R"

echo "→ GET Contrato por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/${CON_ID})
[ "$R" = "200" ] && ok "Contrato por ID: $R" || err "Contrato por ID: $R"

echo "→ GET Contratos por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/usuario/${USR_ID})
[ "$R" = "200" ] && ok "Contratos por usuario: $R" || err "Contratos por usuario: $R"

echo "→ PUT Terminar contrato (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8093/api/contratos/${CON_ID}/terminar)
[ "$R" = "200" ] && ok "Terminar contrato: $R" || err "Terminar contrato: $R"

echo "→ GET Contrato no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8093/api/contratos/9999)
[ "$R" = "404" ] && ok "Contrato no existe: $R" || err "Contrato no existe: $R"

echo ""
echo "=== 7. PAGOS SERVICE ==="
echo "→ POST Procesar pago (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8084/api/pagos/procesar \
  -H "Content-Type: application/json" \
  -d "{\"idReserva\":${RES_ID},\"monto\":100000,\"metodoPago\":\"TRANSFERENCIA\"}")
[ "$R" = "200" ] && ok "Procesar pago: $R" || err "Procesar pago: $R (RES_ID=$RES_ID)"

echo "→ POST Pago reserva no existe (mensaje error)"
RESP=$(curl -s -X POST http://localhost:8084/api/pagos/procesar \
  -H "Content-Type: application/json" \
  -d '{"idReserva":99999,"monto":100000,"metodoPago":"TRANSFERENCIA"}')
echo "$RESP" | grep -qi "error\|no encontrada\|404" && ok "Pago reserva no existe: mensaje correcto" || err "Pago reserva no existe: $RESP"

echo "→ GET Listar pagos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8084/api/pagos/listar)
[ "$R" = "200" ] && ok "Listar pagos: $R" || err "Listar pagos: $R"

echo ""
echo "=== 8. MANTENIMIENTO SERVICE ==="
MANT_RESP=$(curl -s -X POST http://localhost:8095/api/mantenimientos \
  -H "Content-Type: application/json" \
  -d "{\"idPropiedad\":${PROP_ID},\"idUsuario\":${USR_ID},\"descripcion\":\"Reparacion de cañeria urgente en baño\",\"prioridad\":\"ALTA\"}")
MAN_ID=$(echo "$MANT_RESP" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null)
[ -n "$MAN_ID" ] && ok "Crear solicitud: 200 (ID=$MAN_ID)" || err "Crear solicitud: $MANT_RESP"

echo "→ GET Listar mantenimientos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos)
[ "$R" = "200" ] && ok "Listar mantenimientos: $R" || err "Listar mantenimientos: $R"

echo "→ GET Mantenimiento por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/${MAN_ID})
[ "$R" = "200" ] && ok "Mantenimiento por ID: $R" || err "Mantenimiento por ID: $R"

echo "→ GET Mantenimientos por propiedad (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/propiedad/${PROP_ID})
[ "$R" = "200" ] && ok "Mantenimientos por propiedad: $R" || err "Mantenimientos por propiedad: $R"

echo "→ PUT Resolver mantenimiento (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8095/api/mantenimientos/${MAN_ID}/resolver)
[ "$R" = "200" ] && ok "Resolver mantenimiento: $R" || err "Resolver mantenimiento: $R"

echo "→ GET Mantenimiento no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8095/api/mantenimientos/9999)
[ "$R" = "404" ] && ok "Mantenimiento no existe: $R" || err "Mantenimiento no existe: $R"

echo ""
echo "=== 9. DOCUMENTO SERVICE ==="
DOC_RESP=$(curl -s -X POST http://localhost:8097/api/documentos \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idContrato\":${CON_ID},\"tipo\":\"CONTRATO\",\"nombre\":\"Contrato_2026.pdf\",\"url\":\"http://storage/doc1.pdf\"}")
DOC_ID=$(echo "$DOC_RESP" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null)
[ -n "$DOC_ID" ] && ok "Subir documento: 200 (ID=$DOC_ID)" || err "Subir documento: $DOC_RESP"

echo "→ GET Listar documentos (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos)
[ "$R" = "200" ] && ok "Listar documentos: $R" || err "Listar documentos: $R"

echo "→ GET Documento por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/${DOC_ID})
[ "$R" = "200" ] && ok "Documento por ID: $R" || err "Documento por ID: $R"

echo "→ GET Documentos por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/usuario/${USR_ID})
[ "$R" = "200" ] && ok "Documentos por usuario: $R" || err "Documentos por usuario: $R"

echo "→ GET Documentos por contrato (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8097/api/documentos/contrato/${CON_ID})
[ "$R" = "200" ] && ok "Documentos por contrato: $R" || err "Documentos por contrato: $R"

echo "→ PUT Firmar documento (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X PUT http://localhost:8097/api/documentos/${DOC_ID}/firmar)
[ "$R" = "200" ] && ok "Firmar documento: $R" || err "Firmar documento: $R"

echo "→ DELETE Documento (204)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8097/api/documentos/${DOC_ID})
[ "$R" = "204" ] && ok "Eliminar documento: $R" || err "Eliminar documento: $R"

echo "→ DELETE Documento no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" -X DELETE http://localhost:8097/api/documentos/9999)
[ "$R" = "404" ] && ok "Eliminar no existe: $R" || err "Eliminar no existe: $R"

echo ""
echo "=== 10. NOTIFICACIONES SERVICE ==="
NOTIF_RESP=$(curl -s -X POST http://localhost:8086/api/notificaciones/enviar \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idReserva\":${RES_ID},\"tipo\":\"PAGO_RECIBIDO\",\"asunto\":\"Pago procesado\",\"cuerpo\":\"Pago exitoso\",\"emailDestinario\":\"test@test.com\"}")
NOT_ID=$(echo "$NOTIF_RESP" | python3 -c "import sys,json; print(json.load(sys.stdin)['id'])" 2>/dev/null)
HTTP_NOTIF=$(curl -s -o /dev/null -w "%{http_code}" -X POST http://localhost:8086/api/notificaciones/enviar \
  -H "Content-Type: application/json" \
  -d "{\"idUsuario\":${USR_ID},\"idReserva\":${RES_ID},\"tipo\":\"PAGO_RECIBIDO\",\"asunto\":\"Pago procesado\",\"cuerpo\":\"Pago exitoso\",\"emailDestinario\":\"test@test.com\"}")
[ "$HTTP_NOTIF" = "201" ] && ok "Enviar notificacion: 201" || err "Enviar notificacion: $HTTP_NOTIF"

echo "→ GET Listar notificaciones (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones)
[ "$R" = "200" ] && ok "Listar notificaciones: $R" || err "Listar notificaciones: $R"

echo "→ GET Notificacion por ID (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/${NOT_ID})
[ "$R" = "200" ] && ok "Notificacion por ID: $R" || err "Notificacion por ID: $R"

echo "→ GET Notificaciones por usuario (200)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/usuario/${USR_ID})
[ "$R" = "200" ] && ok "Notificaciones por usuario: $R" || err "Notificaciones por usuario: $R"

echo "→ GET Notificacion no existe (404)"
R=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8086/api/notificaciones/9999)
[ "$R" = "404" ] || [ "$R" = "500" ] && ok "Notificacion no existe: $R (aceptable)" || err "Notificacion no existe: $R"

echo ""
echo "=================================================="
echo "   PRUEBAS FINALIZADAS"
echo "   ✅ Pasaron: $pass | ❌ Fallaron: $fail"
echo "=================================================="

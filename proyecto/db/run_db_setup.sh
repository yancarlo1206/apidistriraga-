#!/usr/bin/env bash
# run_db_setup.sh
# Script para ejecutar setup_proyecto.sql usando el cliente mariadb/mysql

set -euo pipefail

# Valores por defecto (puedes sobrescribir con variables de entorno)
DB_HOST=${DB_HOST:-127.0.0.1}
DB_PORT=${DB_PORT:-3306}
DB_USER=${DB_USER:-root}
DB_PASS=${DB_PASS:-root}
SQL_FILE="$(dirname "$0")/setup_proyecto.sql"

if [ ! -f "$SQL_FILE" ]; then
  echo "Archivo SQL no encontrado: $SQL_FILE"
  exit 1
fi

# Preferir mariadb client si existe, sino mysql
if command -v mariadb >/dev/null 2>&1; then
  CLIENT="mariadb"
elif command -v mysql >/dev/null 2>&1; then
  CLIENT="mysql"
else
  echo "No se encontró cliente 'mariadb' ni 'mysql' en PATH. Instala uno de ellos."
  exit 1
fi

# Ejecutar el script
if [ -z "${DB_PASS}" ]; then
  # Si DB_PASS está vacío, no usamos -p
  echo "Ejecutando: $CLIENT -h $DB_HOST -P $DB_PORT -u $DB_USER < $SQL_FILE"
  $CLIENT -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" < "$SQL_FILE"
else
  # Usar la opción -p citado para evitar problemas con caracteres especiales
  echo "Ejecutando: $CLIENT -h $DB_HOST -P $DB_PORT -u $DB_USER -p****** < $SQL_FILE"
  if [ "$CLIENT" = "mariadb" ]; then
    mariadb -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASS" < "$SQL_FILE"
  else
    mysql -h "$DB_HOST" -P "$DB_PORT" -u "$DB_USER" -p"$DB_PASS" < "$SQL_FILE"
  fi
fi

echo "Script ejecutado correctamente."

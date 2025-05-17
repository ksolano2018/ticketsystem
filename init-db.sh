#!/bin/bash

# Espera a que SQL Server esté listo
for i in {1..50};
do
    /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -Q "SELECT 1"
    if [ $? -eq 0 ]
    then
        break
    else
        echo "SQL Server not ready yet..."
        sleep 2
    fi
done

# Crea la base de datos
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P $SA_PASSWORD -d master -i /app/src/main/resources/db/setup.sql
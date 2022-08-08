set -e
echo "Dump Codiapp 💾"

database="codiapp"
databaseTemp="codiapp_temp"
dumpFile="codiapp"
server="codiapp-db.cmg11rlcxfi6.us-east-2.rds.amazonaws.com"
servertTo="localhost"
passwordAws="rF6EOxdrY4fzaWl4BtKf"
password="123456"

# PGPASSWORD=$passwordAws pg_dump -U postgres -h $server -d $database > $dumpFile.sql
PGPASSWORD=$password psql -q -U postgres -h $servertTo -c "drop database if exists $databaseTemp;"
PGPASSWORD=$password psql -q -U postgres -h $servertTo -c "create database $databaseTemp;"
PGPASSWORD=$password psql -U postgres -h $servertTo -d $databaseTemp -f $dumpFile.sql
PGPASSWORD=$password psql -U postgres -h $servertTo -c "drop database if exists $database;"
PGPASSWORD=$password psql -U postgres -h $servertTo -c "alter database $databaseTemp rename to $database";

mysqldump --opt --events --routines --triggers --default-character-set=utf8 -u mama --password=chorlito mama_te_canta | gzip -c > /archivos/mama_te_canta/backup/db_mama_te_canta.sql.gz

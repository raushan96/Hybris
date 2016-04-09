echo "Running scripts"

for f in *.sql
do
    res=$(mysql -uhybris -phybris -s -N -e "select count(file_name) from hybris.version where file_name = '$f'")
    if [ $res -eq 0 ]
    then
        echo "Processing $f"
        mysql -uhybris -phybris < $f
        mysql -uhybris -phybris -s -N -e "insert into hybris.version(file_name) values('$f')"
    fi
done

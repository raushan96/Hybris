@ECHO OFF
ECHO Running scripts

SETLOCAL EnableDelayedExpansion

SET mysqlpath=%~s1

FOR  %%X IN (*.sql) DO (
    ECHO "---- Version %%X -----"
    FOR /f  "usebackq delims="  %%a in (`%mysqlpath% -u hybris -phybris -s -N -e "select hybris.fn_check_history('%%X')"`) do set RESULT=%%a
    IF !RESULT! EQU 100 (
        %mysqlpath% -u hybris -phybris < %%X
        %mysqlpath% -u hybris -phybris -s -N -e "insert into hybris.version(file_name) values ('%%X')"
    )
)

ECHO Finished!
PAUSE
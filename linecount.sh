#!/usr/bin/env bash
declare WC_OUTPUT_LINES
IFS=$'\n' \
  WC_OUTPUT_LINES=( $( find . -type f \( -name '*.java' -o -name 'pom.xml' \) -exec wc -l {} \+ ) )
for LINE in ${WC_OUTPUT_LINES[@]}
  do
    declare -i LINE_COUNT
    LINE_COUNT=$( awk '{print $1}' <<< "${LINE}" )
    declare FILE_NAME
    FILE_NAME="$( awk '{print $2}' <<< "${LINE}" )"
    printf '%-4s %s\n' "$(( ${LINE_COUNT} + 1 ))" "${FILE_NAME}"
  done
exit 0
